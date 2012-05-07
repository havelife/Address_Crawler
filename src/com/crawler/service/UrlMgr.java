package com.crawler.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.UrlDao;
import com.crawler.pojo.Url;

@Component("urlMgr")
public class UrlMgr {
	@Autowired
	private UrlDao urlDao;
	
	
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
}
