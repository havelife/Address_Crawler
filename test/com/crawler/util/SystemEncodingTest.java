package com.crawler.util;

import org.junit.Test;

public class SystemEncodingTest {
	
	@Test
	public void systemEncodingTest(){
		String   encoding   =   System.getProperty("file.encoding");
		System.out.println(encoding);
	}
}
