package com.crawler.dao;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Log;

@Component("logDao") 
public class LogDao extends GenericDao<Log, Long>{
	public LogDao(){
		super();
		this.entityClass = Log.class;
	}
}
