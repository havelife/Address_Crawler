package com.crawler.dao;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Url;

@Component("urlDao") 
public class UrlDao extends GenericDao<Url, Long>{
	public UrlDao(){
		super();
		this.entityClass = Url.class;
	}
}
