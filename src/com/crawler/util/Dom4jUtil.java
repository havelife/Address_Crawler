package com.crawler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Dom4jUtil {
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		String filePath = "./data/extract/Anjuke/AnjukeBeijingRent.txt";
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new FileInputStream(new File(filePath)));

		String xpath ="//*[@id='prop_name_qt_prop_1']";     //查询属性type='ondDelete'的composite
		//List<Element> composites = document.selectNodes(xpath);
		//System.out.println(composites.toString());
	}
}
