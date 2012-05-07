package com.crawler.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.JobDao;
import com.crawler.dao.LogDao;
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
	
	public void downloadWebPage(Long urlId){
		Url urlObj = urlDao.get(urlId);
		Job job;
		if (urlObj != null && urlObj.getJobid() != null) {
			job = jobDao.get(urlObj.getJobid());
		} else {
			System.out.println("null in PageMgr.downloadWebPage(Long)");
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
}
