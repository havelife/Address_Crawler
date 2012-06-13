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
	
	/*
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
	*/
	
	/*
	 * 获取上次下载了，但是没有下载到页面内容的url对象List
	 * */
	public List<Url> getEmptyPageUrlList4Redownload(Long jobid){
		String hql = "from Url as u where u.jobid = ? and u.iscompleted = ? and u.isinit = ?";
		List<Url> resList = this.find(hql, jobid, 0, 1);
		return resList;
	}
	
	/*
	 * get Url Object by the url String
	 * */
	public Url getUrlObjByUrl(String url){
		List<Url> resList = this.findByProperty("url", url);
		if (resList == null || resList.size() == 0) {
			return null;
		} else if (resList.size() > 1) {
			try {
				throw new Exception("duplicated url:" + url + " in table:url");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			return resList.get(0);
		}
		
	}
	
	/* 
	 *查看一个job，它已经完成了下载的页面的个数 
	 */
	public int getCompletedPageCount4OneJob(Long jobid){
		String hql = "from Url u where u.iscompleted = ? and u.jobid = ?";
		List<Url> resList = this.find(hql, 1, jobid);
		if (resList == null) {
			return 0;
		} else {
			return resList.size();
		}
	}
	
	/* 
	 *得到一个job的所有Url对象。 
	 */
	public List<Url> getUrlObjs4OneJob(Long jobid){
		String hql = "from Url u where u.jobid = ?";
		List<Url> resList = this.find(hql, jobid);
		return resList;
	}
}
