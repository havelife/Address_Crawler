package com.crawler.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.LogDao;
import com.crawler.pojo.Log;

@Component("logMgr")
public class LogMgr {
	@Autowired
	private LogDao logDao;
	
	public void save(){
		Log log = new Log();
		log.setComment("xxx");
		//log.setTime(new Date());
		logDao.save(log);
	}
	
	public void logCommentAndOperator(String comment, String operator){
		Log log = new Log();
		log.setComment(comment);
		log.setOperator(operator);
		logDao.save(log);
	}
}
