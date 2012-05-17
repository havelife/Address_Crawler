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
		batchMgr.batchCreateJobAndDownload("./data/batchCreateJobFile.txt");
	}
	
	
	/***********************************************************/
	/*
	 *安居客，北京，小区
	 * */
	@Test
	public void batchCreateJobTestAnjukeBeijingXiaoqu(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeBeijingXiaoquBatchCreateJobFile.txt");
	}
}
