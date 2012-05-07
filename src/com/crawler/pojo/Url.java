package com.crawler.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.hibernate.annotations.Entity;

@Entity
public class Url implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8549813495976245162L;
	
	private Long id;
	private String url;
	private int isinit;
	private int iscompleted;
	private Long jobid;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsinit() {
		return isinit;
	}
	public void setIsinit(int isinit) {
		this.isinit = isinit;
	}
	public int getIscompleted() {
		return iscompleted;
	}
	public void setIscompleted(int iscompleted) {
		this.iscompleted = iscompleted;
	}
	public Long getJobid() {
		return jobid;
	}
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
}
