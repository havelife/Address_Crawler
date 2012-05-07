package com.crawler.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Page;

@Component("pageDao") 
public class PageDao extends GenericDao<Page, Long>{
	public PageDao(){
		super();
		this.entityClass = Page.class;
	}
	
	public Page getPageByUrl(String url){
		List<Page> resList = this.findByProperty("url", url);
		if (resList == null || resList.size() == 0) return null;
		if (resList.size() > 1){
			try {
				throw new Exception("duplicated url:" + url + " in table:page");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resList.get(0);
	}
}
