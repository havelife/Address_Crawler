package com.crawler.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.crawler.pojo.Job;



public class jobDaoTest {
	private static ClassPathXmlApplicationContext ctx;
	static JobDao jobDao;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jobDao = (JobDao)ctx.getBean("jobDao");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void getJobTest(){
		List<Job> list = jobDao.getJobsByCity("绵阳");
//		list = jobDao.getJobsByCityAndType("北京", "安居客-二手房");
		list = jobDao.getJobsByCityAndTypeSpecific("北京", "安居客-二手房#丰台#方庄#150-200平米#二室#普通住宅");
		System.out.println(list);
	}

}
