package com.crawler.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Url;

@Component("urlDao") 
public class UrlDao extends GenericDao<Url, Long>{
	public UrlDao(){
		super();
		this.entityClass = Url.class;
	}
	
	
	public List<Long> getUrlIdList4OneJob(Long jobid){
		String sql = "select id from url where jobid=" + jobid;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.LONG);
		@SuppressWarnings("unchecked")
		List<Long> resList = query.list();
		if (resList == null || resList.size() == 0){
			return null;
		} else {
			return resList;
		}
	}
	
	public int getCompleteCount4OneJob(Long jobid){
		String sql = "select count(*) as completecount from url where jobid=" + jobid + " and iscompleted=1";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.addScalar("completecount", Hibernate.INTEGER);
		@SuppressWarnings("unchecked")
		List<Integer> resList = query.list();
		if (resList == null || resList.size() == 0){
			return 0;
		} else if (resList.size() > 1) {
			try {
				throw new Exception("abnormal result in UrlDao.getCompleteCount4OneJob");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		} else {
			return resList.get(0);
		}
	}
}
