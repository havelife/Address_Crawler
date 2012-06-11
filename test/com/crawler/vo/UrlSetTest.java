package com.crawler.vo;

import org.junit.Test;

public class UrlSetTest {
	private UrlSet urlSet;
	
	@Test
	public void generateUrls(){
		urlSet = new UrlSet("http://sz.sofang.com", "http://sofang.com/office/rent/&act=all&page=", null, 1, 1, 330);
		urlSet.generateUrls();
		System.out.println(urlSet);
	}
	
	@Test
	public void generateUrls4AnjukeXiaoquOnlyOnePage(){
		urlSet = new UrlSet("http://shenzhen.anjuke.com/community/", "http://shenzhen.anjuke.com/community/list/W0QQpZ1QQp1Z401QQp2Z2266", null, 1, 1, 1);
		urlSet.generateUrls();
		System.out.println(urlSet);
	}
}
