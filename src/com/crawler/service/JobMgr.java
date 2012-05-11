package com.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.JobDao;
import com.crawler.pojo.Job;
import com.crawler.pojo.Url;
import com.crawler.util.CrawlerConstants;
import com.crawler.util.ListUtil;
import com.crawler.vo.UrlSet;

@Component("jobMgr")
public class JobMgr {
	@Autowired
	private JobDao jobDao;
	@Autowired
	private LogMgr logMgr;
	@Autowired
	private UrlMgr urlMgr;
	@Autowired
	private PageMgr pageMgr;
	
	
	/*
	 * 创建一个job，在job表中添加一条记录
	 * */
	public Job createJob(String prefix, String suffix, int step, int startidx, int endidx, String category, String type, String domain){
		Job job = new Job();
		job.setPrefix(prefix);
		job.setSuffix(suffix);
		job.setStep(step);
		job.setStartidx(startidx);
		job.setEndidx(endidx);
		job.setCategory(category);
		job.setType(type);
		job.setDomain(domain);
		job.setCount();
		//save
		job = jobDao.save(job);
		//log
		logMgr.logCommentAndOperator("job create!", job.getId().toString());
		
		return job;
	}
	
	/*
	 * 对于这个job，将他所有的url生成，并且存储在url表中。
	 * 
	 * */
	public boolean generateUrlSetAndSaveInDatabase4OneJob(Long jobid){
		Job job = jobDao.get(jobid);
		if(job == null) return false;
		UrlSet urlSet = new UrlSet(job.getDomain(), job.getPrefix(), job.getSuffix(), job.getStep(), job.getStartidx(), job.getEndidx());
		urlSet.generateUrls();
		if(urlSet.data == null || urlSet.data.size() == 0) return false;
		urlMgr.generateUrlsAndSaveInDatabase(urlSet.data, jobid);
		return true;
	}
	
	/*
	 * 
	 * 根据你这个job产生的url表中的数据，去逐一去下载，最后在job表中更新总共下载多少条url的统计数据
	 * */
	public boolean downloadAllWebPage4OneJobAndUpdateStatistics(Long jobid){
		if (jobid == null) return false;
		if (CrawlerConstants.LOG_JOB_START) logMgr.logCommentAndOperator("job start to download webpage", jobid.toString());
		urlMgr.downloadAllWebPage4OneJob(jobid);
		Job job = jobDao.get(jobid);
		if (job == null) return false;
		int completecount = urlMgr.getCompleteCount4OneJob(jobid);
		job.setCompletecount(completecount);
		jobDao.save(job);
		if (CrawlerConstants.LOG_JOB_COMPLETE) logMgr.logCommentAndOperator("job completed download webpage. " + job.getCompletecount() + "/" + job.getCount() , jobid.toString());
		System.out.println("job completed download webpage. " + job.getCompletecount() + "/" + job.getCount());
		return true;
	}
	
	/*
	 * 包括整个全过程
	 * 1.创建job，并且存入job表
	 * 2.产生url，并且存入url表
	 * 3.下载所有页面，并且更新统计数据，并且存入job表
	 * 4.检验是否还有没有下载下来的页面，如有，去重新下载那些未下载页面。
	 * */
	public void createJobAndDownloadWholeProcess(String prefix, String suffix, int step, int startidx, int endidx, String category, String type, String domain){
		//1. create new job!
		Job job = createJob(prefix, suffix, step, startidx, endidx, category, type, domain);
		if (job == null) {
			System.out.println("job create failed!");
			return;
		}
		
		boolean result = false;
		//2. generate urls
		result = generateUrlSetAndSaveInDatabase4OneJob(job.getId());
		if(result) {
			System.out.println("urls generated successfully！");
		} else {
			System.out.println("urls generated failed!");
		}
		
		//3. download all webpages and update statistics
		result = downloadAllWebPage4OneJobAndUpdateStatistics(job.getId());
		if(result) {
			System.out.println("webpages download successfully!");
		} else {
			System.out.println("webpages download failed!");
		}
		
		//4. redownload empty pages
		if(!isAllCompleted(job.getId())){
			redownloadEmptyPage(job.getId());
		}
	}
	
	/*
	 * 对于一个job，是否所有的webpage都被下载完
	 * */
	public boolean isAllCompleted(Long jobid){
		Job job = jobDao.get(jobid);
		return (job.getCompletecount() == job.getCount());
	}
	
	/*
	 * 对于第一次下载的时候，错过的，没有下载下来的空页面，重新进行下载
	 * */
	public boolean redownloadEmptyPage(Long jobid){
		boolean needRedownload = !isAllCompleted(jobid);
		if (!needRedownload) {
			System.out.println("pages already downloaded. no need to redownload.");
			return true;
		} else {
			List<Url> urlList = urlMgr.getEmptyUrlList(jobid);
			if (ListUtil.isBlank(urlList)) {
				System.out.println("data in job and url table does not match for empty pages.");
				return false;
			}
			for(Url url : urlList){
				pageMgr.redownloadWebPage(url.getUrl());
			}
			return true;
		}
	}
}
