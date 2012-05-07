package com.crawler.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobMgrTest {
	private ClassPathXmlApplicationContext ctx;
	JobMgr jobMgr;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jobMgr = (JobMgr)ctx.getBean("jobMgr");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void createJobTest(){
		jobMgr.createJob("http://bj.esf.focus.cn/ershoufang/j0m0h0jz0zx0cx0bq0p", "px0nd0jd0bx0pic0q0b0xl0zd0xq0st3s0tx1/", 1, 1, 10, "Beijing", "MaiFangErShou", "http://bj.esf.focus.cn");
	}
	
	@Test
	public void encodingTest(){
		System.out.println("北京");
	}
	
	@Test
	public void generateUrlSetAndSaveInDatabase4OneJobTest(){
		jobMgr.generateUrlSetAndSaveInDatabase4OneJob(1L);
	}
	
	@Test
	public void downloadAllWebPage4OneJobAndUpdateStatisticsTest(){
		jobMgr.downloadAllWebPage4OneJobAndUpdateStatistics(1L);
	}
}
