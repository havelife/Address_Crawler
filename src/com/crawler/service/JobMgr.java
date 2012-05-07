package com.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.JobDao;
import com.crawler.pojo.Job;
import com.crawler.vo.UrlSet;

@Component("jobMgr")
public class JobMgr {
	@Autowired
	private JobDao jobDao;
	@Autowired
	private LogMgr logMgr;
	@Autowired
	private UrlMgr urlMgr;
	
	public void createJob(String prefix, String suffix, int step, int startidx, int endidx, String category, String type, String domain){
		Job job = new Job();
		job.setPrefix(prefix);
		job.setSuffix(suffix);
		job.setStep(step);
		job.setStartidx(startidx);
		job.setEndidx(endidx);
		job.setCategory(category);
		job.setType(type);
		job.setDomain(domain);
		jobDao.save(job);
		//log
		logMgr.logCommentAndOperator("job create!", job.getId().toString());
	}
	
	public void generateUrlSetAndSaveInDatabase(Long jobid){
		Job job = jobDao.get(jobid);
		if(job == null) return;
		UrlSet urlSet = new UrlSet(job.getDomain(), job.getPrefix(), job.getSuffix(), job.getStep(), job.getStartidx(), job.getEndidx());
		urlSet.generateUrls();
		if(urlSet.data == null || urlSet.data.size() == 0) return;
		urlMgr.generateUrlsAndSaveInDatabase(urlSet.data, jobid);
	}
}
