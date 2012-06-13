package com.crawler.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.UrlDao;
import com.crawler.pojo.Url;

@Component("urlMgr")
public class UrlMgr {
	@Autowired
	private UrlDao urlDao;
	@Autowired
	private PageMgr pageMgr;
	
	
	public void generateUrlsAndSaveInDatabase(String url, Long jobid){
		if (url == null || url.equals("") || jobid == null) return;
		Url entity = new Url();
		entity.setUrl(url);
		entity.setJobid(jobid);
		urlDao.save(entity);
	}
	
	/**
	 * func store the urls in map<String, String> data. 
	 * */
	public void generateUrlsAndSaveInDatabase(Map<Integer, String> data, Long jobid){
		if(data == null || data.size() == 0 || jobid == null) return;
		for(Map.Entry<Integer, String> entry : data.entrySet()){
			generateUrlsAndSaveInDatabase(entry.getValue(), jobid);
		}
	}
	
	public void downloadAllWebPage4OneJob(Long jobid){
		if (jobid == null) return;
		List<Long> urlIdList = urlDao.getUrlIdList4OneJob(jobid);
		if (urlIdList == null || urlIdList.size() == 0) return;
		for (Long urlId : urlIdList) {
			pageMgr.downloadWebPage(urlId);
		}
	}
	
	/*
	 * 查看一个job，它已经完成了下载的页面的个数 
	 * */
	public int getCompletedPageCount4OneJob(Long jobid){
		if (jobid == null) return 0;
		return urlDao.getCompletedPageCount4OneJob(jobid);
	}
	
	/*
	 * 重新下载 当时没有下载下来的 页面
	 */
	public List<Url> getEmptyUrlList(Long jobid){
		List<Url> resList = urlDao.getEmptyPageUrlList4Redownload(jobid);
		return resList;
	}
	
	/*
	 * 通过url来获取Url对象
	 * 
	 */
	public Url getUrlObjByUrl(String url){
		return urlDao.getUrlObjByUrl(url);
	}
	
	/*
	 * 获得一个job的所有Url对象
	 * */
	public List<Url> getUrlObjs4OneJob(Long jobid){
		return urlDao.getUrlObjs4OneJob(jobid);
	}
}
