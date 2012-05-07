package com.crawler.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PageMgrTest {
	private ClassPathXmlApplicationContext ctx;
	PageMgr pageMgr;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		pageMgr = (PageMgr)ctx.getBean("pageMgr");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	
	@Test
	public void getPageContentByPageUrlTest(){
		String content = pageMgr.getPageContentByPageUrl("http://bj.esf.focus.cn/ershoufang/j0m0h0jz0zx0cx0bq0p1px0nd0jd0bx0pic0q0b0xl0zd0xq0st3s0tx1/");
		System.out.println(content);
	}
}
