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
		jobMgr.createJob("http://bj.esf.focus.cn/ershoufang/j0m0h0jz0zx0cx0bq0p", "px0nd0jd0bx0pic0q0b0xl0zd0xq0st3s0tx1/", 1, 1, 10, "Beijing", "MaiFangErShou", "http://soufun.com");
	}
	
	
	/***********************************************************/
	
	/*
	 *安居客, 上海, 小区 
	 */
	@Test
	public void createJobAndDownloadWholeProcess_AnjukeShanghaiXiaoqu(){
		jobMgr.createJobAndDownloadWholeProcess("http://shanghai.anjuke.com/community/W0QQpZ", "", 1, 1, 101, "上海", "安居客-小区", "http://shanghai.anjuke.com/community/");
	}
	
	/***********************************************************/
	
	/*
	 *安居客, 北京, 小区 
	 */
	@Test
	public void createJobAndDownloadWholeProcess_AnjukeBeijingXiaoqu(){
		jobMgr.createJobAndDownloadWholeProcess("http://beijing.anjuke.com/community/W0QQpZ", "", 1, 1, 101, "北京", "安居客-小区", "http://beijing.anjuke.com/community/");
	}
	 
	
	/***********************************************************/
	
	/*
	 *搜房网，北京，新房 
	 */
	@Test
	public void createJobTestSoufunBeijingXinfang(){
		jobMgr.createJob("http://soufun.com/house/%B1%B1%BE%A9_________________", "_.htm", 1, 1, 1308, "北京", "搜房网-新房", "http://bj.esf.focus.cn");
	}
	
	@Test
	public void generateUrlSetAndSaveInDatabase4OneJobTestSoufunBeijingXinfang(){
		jobMgr.generateUrlSetAndSaveInDatabase4OneJob(2L);
	}
	
	@Test
	public void downloadAllWebPage4OneJobAndUpdateStatisticsTestSoufunBeijingXinfang(){
		jobMgr.downloadAllWebPage4OneJobAndUpdateStatistics(2L);
	}
	
	/***********************************************************/
	
	/*
	 *搜房网，上海，新房 
	 */
	@Test
	public void createJobTestSoufunShanghaiXinfang(){
		jobMgr.createJob("http://newhouse.sh.soufun.com/house/%C9%CF%BA%A3_________________", "_.htm", 1, 1, 1833, "上海", "搜房网-新房", "http://newhouse.sh.soufun.com");
	}
	
	@Test
	public void generateUrlSetAndSaveInDatabase4OneJobTestSoufunShanghaiXinfang(){
		jobMgr.generateUrlSetAndSaveInDatabase4OneJob(3L);
	}
	
	@Test
	public void downloadAllWebPage4OneJobAndUpdateStatisticsTestSoufunShanghaiXinfang(){
		jobMgr.downloadAllWebPage4OneJobAndUpdateStatistics(3L);
	}
	
	@Test
	public void redownloadEmptyPageTestSoufunShanghaiXinfang(){
		jobMgr.redownloadEmptyPage(3L);
	}
	
	/***********************************************************/
	
	@Test
	public void generateUrlSetAndSaveInDatabase4OneJobTest(){
		jobMgr.generateUrlSetAndSaveInDatabase4OneJob(1L);
	}
	
	@Test
	public void downloadAllWebPage4OneJobAndUpdateStatisticsTest(){
		jobMgr.downloadAllWebPage4OneJobAndUpdateStatistics(1L);
	}
}
