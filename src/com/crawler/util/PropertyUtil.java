package com.crawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	public static Properties properties;
	public static InputStream inputStream;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printAll("application.properties");
	}
	
	public static void loadPropertyFile(String propertiesFilePath){
		properties = new Properties();
		inputStream = ClassLoader.getSystemResourceAsStream(propertiesFilePath);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getValue(String propertiesFilePath, String key) {
		loadPropertyFile(propertiesFilePath);
		return properties.getProperty(key);
	}
	
	public static void printAll(String propertiesFilePath){
		loadPropertyFile(propertiesFilePath);
		for(java.util.Map.Entry<Object, Object> entry : properties.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
