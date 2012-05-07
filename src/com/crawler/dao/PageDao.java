package com.crawler.dao;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Page;

@Component("pageDao") 
public class PageDao extends GenericDao<Page, Long>{
	public PageDao(){
		super();
		this.entityClass = Page.class;
	}
}
