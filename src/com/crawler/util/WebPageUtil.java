package com.crawler.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageUtil {
	
	public static String getPageContentByUrl(String url){
		if (StringUtils.isBlank(url)){
			System.err.println("WebPageUtil.getPageContentByUrl: url is blank");
		}
		try {
			Document doc;
			doc = Jsoup.connect(url).timeout(80000).get();
			//doc.
			return doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPageContentByUrlWithReferer(String url){
		HttpClient client = new HttpClient();
        //璁剧疆浠ｇ悊鏈嶅姟鍣ㄥ湴鍧�拰绔彛        
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        //浣跨敤GET鏂规硶锛屽鏋滄湇鍔″櫒闇�閫氳繃HTTPS杩炴帴锛岄偅鍙渶瑕佸皢涓嬮潰URL涓殑http鎹㈡垚https
        HttpMethod method = new GetMethod(url);
        method.setRequestHeader("Referer", url);
        //浣跨敤POST鏂规硶
        //HttpMethod method = new PostMethod("http://java.sun.com");
        try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //鎵撳嵃鏈嶅姟鍣ㄨ繑鍥炵殑鐘舵�
//        System.out.println(method.getStatusLine());
        //鎵撳嵃杩斿洖鐨勪俊鎭�
        String res = null;
		try {
			res = method.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //閲婃斁杩炴帴
        method.releaseConnection();
        return res;
	}
	
	public static String getPageContentByUrlWithReferer(String url, String encoding){
		HttpClient client = new HttpClient();
        //璁剧疆浠ｇ悊鏈嶅姟鍣ㄥ湴鍧�拰绔彛        
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        //浣跨敤GET鏂规硶锛屽鏋滄湇鍔″櫒闇�閫氳繃HTTPS杩炴帴锛岄偅鍙渶瑕佸皢涓嬮潰URL涓殑http鎹㈡垚https
        HttpMethod method = new GetMethod(url);
        method.setRequestHeader("Referer", url);
        //浣跨敤POST鏂规硶
        //HttpMethod method = new PostMethod("http://java.sun.com");
        try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //鎵撳嵃鏈嶅姟鍣ㄨ繑鍥炵殑鐘舵�
//        System.out.println(method.getStatusLine());
        //鎵撳嵃杩斿洖鐨勪俊鎭�
        String res = null;
		try {
			res = new String(method.getResponseBodyAsString().getBytes(encoding));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //閲婃斁杩炴帴
        method.releaseConnection();
        return res;
	}
	
	public static String getPageContentByUrlWithRefererAndRandomTime(String url, int seconds){
		try {
			Thread.sleep(1000 * RandomUtil.getOne2XRandomNum(seconds));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPageContentByUrlWithReferer(url);
	}
	
	public static String getPageContentByUrlWithRefererAndRandomTime(String url, int seconds, String encoding){
		try {
			Thread.sleep(1000 * RandomUtil.getOne2XRandomNum(seconds));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPageContentByUrlWithReferer(url, encoding);
	}
	
	//for test
	public static void main(String[] args){
		
		String pageContent;
		//http://house.focus.cn/housemarket/house_search/index.php?page=153
		pageContent = WebPageUtil.getPageContentByUrl("http://soufun.com/house/%B1%B1%BE%A9_________________1308_.htm");  
		
		FileUtil.writeStr2File(pageContent, "./data/pagecontentSoufunBeijing.txt");
		System.out.println(pageContent);
		/*
		try {
		for(int i=0; i<10; i++){
			Thread.sleep(3000);
			System.out.println(i);
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
