package com.crawler.parser;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeAllOtherCitySecondhand {

	public static final String TYPE = "安居客-二手房";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public static final String ALL_OTHER_CITY_URL = "http://www.anjuke.com/index/";
	public static final String ALL_OTHER_CITY_REGEX = "div[class=cities_boxer]";
	
	public static final String ALL_OTHER_CITY_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + "AllOtherCityWhole.txt";
	public static final String TYPE_PINYING = "Secondhand";

	/*
	 * http://www.anjuke.com/index/ 上的所有其它城市，（除去 北上广 还有深圳）
	 * */
	@Test
	public void allOtherCityWhole(){
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(ALL_OTHER_CITY_URL, ALL_OTHER_CITY_REGEX, 0);
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			String href = element.attr("href");
			String word = element.text();
			if ( !(word.equals("北京")||word.equals("上海")||word.equals("广州")||word.equals("深圳")) ) {
				sb.append(href);
				sb.append(",");
				sb.append(word);
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
		FileUtil.writeStr2File(sb.toString(), ALL_OTHER_CITY_FILE_PATH, "utf-8");
	}

	@Test
	public static void createAllOtherCityBatchJobFile(){
		String content = FileUtil.getDataFile2StrKeepReturn(ALL_OTHER_CITY_FILE_PATH, "utf-8");
		String[] lineArr = content.split("\n");
		ArrayList<Pair> cityAndSubdomainList = new ArrayList<Pair>();
		//提取所有的城市和子域名的对应关系
		for (String line : lineArr ) {
			String[] attrArr = line.split(",");
			String href = attrArr[0];
			String city = attrArr[1];
			String subDomain = href.substring(href.lastIndexOf("/") + 1, href.indexOf(".", href.lastIndexOf("/")));
			
			Pair pair = new Pair();
			pair.setKey(city);
			pair.setValue(subDomain);
			cityAndSubdomainList.add(pair);
			System.out.println(city + " : " + subDomain);
		}
		System.out.println("/----------------------------------------------------------------/");
		System.out.println("all cities and their subdomains extraction is completed!");
		System.out.println("/----------------------------------------------------------------/");
		
		
		//根据提取到的对应关系，进行所有城市的batchfile的创建
		for (Pair pair : cityAndSubdomainList){
			AnjukeGenericSecondhand genericSecondhand = new AnjukeGenericSecondhand(pair.getKey(), pair.getValue());
			genericSecondhand.doAllProcessInOneFunction();
			System.out.println("/***************************************************************/");
			System.out.println("city:" + pair.getKey() + ", subdomain:" + pair.getValue() + " is completed!");
			System.out.println("/***************************************************************/");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createAllOtherCityBatchJobFile();
	}
}
