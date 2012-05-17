package com.crawler.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchMgrTest {
	private static ClassPathXmlApplicationContext ctx;
	static BatchMgr batchMgr;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		batchMgr = (BatchMgr)ctx.getBean("batchMgr");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void batchCreateJobTest(){
		batchMgr.batchCreateJob("./data/batchCreateJobFile.txt");
	}
	
	
	/***********************************************************/
	
}
