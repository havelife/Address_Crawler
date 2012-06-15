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
	public void extractAndSaveAllOtherCityTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-租房", "北京", "上海");
	}
}
