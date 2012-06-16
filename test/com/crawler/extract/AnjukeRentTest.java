package com.crawler.extract;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnjukeRentTest {
	private static ClassPathXmlApplicationContext ctx;
	static AnjukeRent anjukeRent;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		anjukeRent = (AnjukeRent)ctx.getBean("anjukeRent");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeBeijingTest(){
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-租房");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeShanghaiTest(){
		anjukeRent.extractAndSaveByCityAndType("上海", "安居客-租房");
	}
	
	/*
	 * 除去北京、上海, 解析剩下的租房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveAllOtherCityRentTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-租房", "北京", "上海");
	}
	
	/*
	 * 解析所有的二手房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveAllOtherCitySecondhandTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-二手房");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeTest4DebugBeijingYayuncun(){
//		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#朝阳#亚运村#90-110平米#二室#普通住宅");
//		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#朝阳#亚运村#110-130平米#二室#普通住宅");
	}
	/*
	 * 解析所有的小区信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveAllOtherCityCommunityTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-小区");
	}
}
