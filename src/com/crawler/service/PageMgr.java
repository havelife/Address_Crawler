package com.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.PageDao;
import com.crawler.pojo.Page;

@Component("pageMgr")
public class PageMgr {
	@Autowired
	private PageDao pageDao;
	
	public void save(){
		Page p = new Page();
		p.setCategory("xxx");
		p.setUrl("www.xxx.com");
		pageDao.save(p);
		System.out.println("finished!");
	}
}
