package com.crawler.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Job implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455836473310197217L;
	
	private Long id;
	private String prefix;
	private String suffix;
	private int step;
	private int startidx;
	private int endidx;
	private String category;
	private String type;
	private String domain;
	private int count;
	private int completecount;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getStartidx() {
		return startidx;
	}
	public void setStartidx(int startidx) {
		this.startidx = startidx;
	}
	public int getEndidx() {
		return endidx;
	}
	public void setEndidx(int endidx) {
		this.endidx = endidx;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCompletecount() {
		return completecount;
	}
	public void setCompletecount(int completecount) {
		this.completecount = completecount;
	}

}
