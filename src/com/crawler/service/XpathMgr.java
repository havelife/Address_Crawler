package com.crawler.service;

import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;

@Component("xpathMgr")
public class XpathMgr {
	public static void main(String args[]) {
	    try {
	      XPathFactory factory = XPathFactory.newInstance();
	    } catch (Exception e) {
	      System.err.println("Uh oh...looks like you don't have the version " +
	        "of JAXP with XPath support. Better upgrade to Java 5 or greater.");
	    }
	    System.out.println("Successfully loaded XPath factory. Things look good.");
	  }
}
