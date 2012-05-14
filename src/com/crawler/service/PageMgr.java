package com.crawler.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.JobDao;
import com.crawler.dao.PageDao;
import com.crawler.dao.UrlDao;
import com.crawler.pojo.Job;
import com.crawler.pojo.Page;
import com.crawler.pojo.Url;
import com.crawler.util.CrawlerConstants;
import com.crawler.util.WebPageUtil;

@Component("pageMgr")
public class PageMgr {
	@Autowired
	private PageDao pageDao;
	@Autowired
	private UrlDao urlDao;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private LogMgr logMgr;
	@Autowired
	private UrlMgr urlMgr;
	
	public void downloadWebPage(Long urlId){
		Url urlObj = urlDao.get(urlId);
		Job job;
		if (urlObj != null && urlObj.getJobid() != null) {
			job = jobDao.get(urlObj.getJobid());
		} else {
			System.out.println("null in PageMgr.downloadWebPage(Long)");
			return;
		}
		if (urlObj.getIscompleted() == 1) {
			System.out.println("url:" + urlObj.getUrl() + ", has already been downloaded.");
			return;
		}
		String content = WebPageUtil.getPageContentByUrl(urlObj.getUrl());
		urlObj.setIsinit(1);
		int iscompleted = (StringUtils.isBlank(content)) ? 0:1;
		urlObj.setIscompleted(iscompleted);
		//update
		urlDao.save(urlObj);
		
		Page page = new Page();
		page.setContent(content);
		page.setCategory(job.getCategory());
		page.setDomain(job.getDomain());
		page.setType(job.getType());
		page.setUrl(urlObj.getUrl());
		page.setIscompleted(iscompleted);
		page.setJobid(urlObj.getJobid());
		//save
		pageDao.save(page);
		//log
		if (CrawlerConstants.LOG_WHEN_DOWNLOAD_WEBPAGE_FOR_ONE_URL) {
			logMgr.logCommentAndOperator("webpage download", "url:"+page.getUrl());
		}
	}
	
	/*
	 * 对于一个特定的url，之前下载过，但是没有抓到内容，这次去重新下载
	 * */
	public void redownloadWebPage(String url){
		if (StringUtil.isBlank(url)){
			System.out.println("input url for redownload() is blank in PageMgr");
			return;
		}
		Url urlObj = urlMgr.getUrlObjByUrl(url);
		if (urlObj == null) {
			System.out.println("url Obj for " + url + " does not exist in table:url.");
			return;
		}
		Page page = getPageObjByPageUrl(url);
		if (page == null) {
			System.out.println("page Obj for " + url + " does not exist in table:page.");
			return;
		}
		
		// start to redownload page
		String content = WebPageUtil.getPageContentByUrl(urlObj.getUrl());
		int iscompleted = (StringUtils.isBlank(content)) ? 0:1;
		urlObj.setIscompleted(iscompleted);
		//update
		urlDao.save(urlObj);
		
		page.setContent(content);
		page.setIscompleted(iscompleted);
		page.setTime(new Date());
		//update
		pageDao.save(page);
	}
	
	public String getPageContentByPageId(Long id){
		Page page = pageDao.get(id);
		if (page == null) return null;
		return page.getContent();
	}
	
	public String getPageContentByPageUrl(String url){
		Page page = pageDao.getPageByUrl(url);
		if (page == null) return null;
		return page.getContent();
	}
	
	/*
	 * 通过url来获取page对象
	 * 
	 */
	public Page getPageObjByPageUrl(String url){
		return pageDao.getPageByUrl(url);
	}
}
