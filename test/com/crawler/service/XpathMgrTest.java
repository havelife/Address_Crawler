package com.crawler.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XpathMgrTest {
	private static ClassPathXmlApplicationContext ctx;
	static XpathMgr xpathMgr;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		xpathMgr = (XpathMgr)ctx.getBean("xpathMgr");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void Test(){

	}
	
	
	/***********************************************************/
	
}
