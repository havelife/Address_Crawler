package com.crawler.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.crawler.pojo.Address;



public class addressDaoTest {
	private static ClassPathXmlApplicationContext ctx;
	static AddressDao addressDao;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		addressDao = (AddressDao)ctx.getBean("addressDao");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void saveTest(){
		Address address = new Address();
		address.setName("安华里");
		address.setAddr("北");
		address.setCity("1");
		address.setComment("2");
		address.setHouseCondition("3");
		address.setDistrict("4");
		address.setCommunity("5");
		address.setPrice("6");
		address.setType("7");
		address.setWebsite("8");
		
		addressDao.save(address);
	}
}
