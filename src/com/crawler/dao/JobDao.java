package com.crawler.dao;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Job;

@Component("jobDao") 
public class JobDao extends GenericDao<Job, Long>{
	public JobDao(){
		super();
		this.entityClass = Job.class;
	}
}
