package com.crawler.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UrlMgrTest {
	private ClassPathXmlApplicationContext ctx;
	UrlMgr urlMgr;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		urlMgr = (UrlMgr)ctx.getBean("urlMgr");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void downloadAllWebPage4OneJobTest(){
		urlMgr.downloadAllWebPage4OneJob(1L);
	}
	
	@Test
	public void getCompletedPageCount4OneJobTest(){
		int count = urlMgr.getCompletedPageCount4OneJob(3L);
		System.out.println(count);
	}
}
