package com.crawler.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Job;

@Component("jobDao") 
public class JobDao extends GenericDao<Job, Long>{
	public JobDao(){
		super();
		this.entityClass = Job.class;
	}
	
	public List<Job> getJobsByCity(String city){
		String hql = "from Job j where j.category = ?";
		return this.find(hql, city);
	}
	
	public List<Job> getJobsByCityAndType(String city, String type){
		String hql = "from Job j where j.category = ? and j.type like '" + type + "%'";
		return this.find(hql, city);
	}
	
	public List<Job> getJobsByCityAndTypeSpecific(String city, String type){
		String hql = "from Job j where j.category = ? and j.type = ?";
		return this.find(hql, city, type);
	}
}
