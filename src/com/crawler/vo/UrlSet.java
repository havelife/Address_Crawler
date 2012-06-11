package com.crawler.vo;

import java.util.HashMap;
import java.util.Map;

import com.crawler.util.CrawlerConstants;
import com.crawler.util.PropertyUtil;

public class UrlSet {
	private int count;
	private String domain;
	private String prefix;
	private String suffix;
	private int step;
	private int startIdx;
	private int endIdx;
	public Map<Integer, String> data;
	
	public UrlSet(String domain, String prefix, String suffix, int step, int startIdx, int endIdx){
		this.count = (endIdx - startIdx) / step + 1;
		this.domain = domain;
		this.prefix = prefix;
		this.suffix = (suffix == null) ? "" : suffix;
		this.step = step;
		this.startIdx = startIdx;
		this.endIdx = endIdx;
		this.data = new HashMap<Integer, String>();
	}
	
	public void generateUrls(){
		if ("open".equals(PropertyUtil.getValue("application.properties", "deal.with.anjuke.xiaoqu.only.has.one.page")) && count == 1 && prefix.contains("anjuke.com")){
			//是安居客，小区，并且页数为1页的情况
			String url = prefix;
			data.put((startIdx + 0 * step), url);
			if(CrawlerConstants.IS_PRINT_GENERATE_URLS) System.out.println(url);
		} else {
			//不是（安居客，小区，并且页数为1页的情况）
			for(int i=0; i<=(endIdx-startIdx)/step; i++){
				String url = prefix + (startIdx + i * step) + suffix;
				data.put((startIdx + i * step), url);
				if(CrawlerConstants.IS_PRINT_GENERATE_URLS) System.out.println(url);
			}
		}
	}
	
	@Override
	public String toString() {
		return "UrlSet [count=" + count + ", domain=" + domain + ", prefix="
				+ prefix + ", suffix=" + suffix + ", step=" + step
				+ ", startIdx=" + startIdx + ", endIdx=" + endIdx + ", \n data="
				+ data + "]";
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public int getStartIdx() {
		return startIdx;
	}

	public void setStartIdx(int startIdx) {
		this.startIdx = startIdx;
	}

	public int getEndIdx() {
		return endIdx;
	}

	public void setEndIdx(int endIdx) {
		this.endIdx = endIdx;
	}
}
