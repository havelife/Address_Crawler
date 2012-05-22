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
	
	/***********************************************************/
	/*
	 *安居客，北京，二手房
	 * */
	@Test
	public void batchCreateJobTestAnjukeBeijingSecondhand(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeBeijingSecondhandBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，北京，租房
	 * */
	@Test
	public void batchCreateJobTestAnjukeBeijingRent(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeBeijingRentBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，上海，小区
	 * */
	@Test
	public void batchCreateJobTestAnjukeShanghaiXiaoqu(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeShanghaiXiaoquBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，上海，二手房
	 * */
	@Test
	public void batchCreateJobTestAnjukeShanghaiSecondhand(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeShanghaiSecondhandBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，上海，租房
	 * */
	@Test
	public void batchCreateJobTestAnjukeShanghaiRent(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeShanghaiRentBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，深圳，二手房
	 * */
	@Test
	public void batchCreateJobTestAnjukeShenzhenSecondhand(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeShenzhenSecondhandBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，广州，二手房
	 * */
	@Test
	public void batchCreateJobTestAnjukeGuangzhouSecondhand(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/AnjukeGuangzhouSecondhandBatchCreateJobFile.txt");
	}
	
	/***********************************************************/
	/*
	 *安居客，广州 and 深圳，二手房
	 * */
	@Test
	public void batchCreateJobTestAnjukeGuangzhouAndShenzhenSecondhand(){
		batchCreateJobTestAnjukeGuangzhouSecondhand();
		batchCreateJobTestAnjukeShenzhenSecondhand();
	}
	
	/***********************************************************/
	/*
	 *安居客，广州，二手房
	 * */
	@Test
	public void testEmpty(){
		batchMgr.batchCreateJobAndDownload("./data/batch/Anjuke/Empty.txt");
	}
	
}
