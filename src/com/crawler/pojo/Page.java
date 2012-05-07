package com.crawler.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.hibernate.annotations.Entity;

@Entity
public class Page implements Serializable{ //
	/**
	 * 
	 */
	private static final long serialVersionUID = 8386789868508303882L;
	
	private Long id;
	private String url;
	private String content;
	private String category;
	private int iscompleted;
	private String type;
	private Date time;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getIscompleted() {
		return iscompleted;
	}
	public void setIscompleted(int iscompleted) {
		this.iscompleted = iscompleted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
