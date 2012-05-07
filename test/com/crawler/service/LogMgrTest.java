package com.crawler.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LogMgrTest {
	private ClassPathXmlApplicationContext ctx;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void logCommentAndOperatorTest(){
		LogMgr logMgr = (LogMgr)ctx.getBean("logMgr");
		logMgr.logCommentAndOperator("www", "我是");
	}
}
