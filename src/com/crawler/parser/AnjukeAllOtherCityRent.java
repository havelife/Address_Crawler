package com.crawler.parser;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeAllOtherCityRent {

	public static final String TYPE = "安居客-二手房";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public static final String ALL_OTHER_CITY_URL = "http://www.anjuke.com/index/";
	public static final String ALL_OTHER_CITY_REGEX = "div[class=cities_boxer]";
	
	public static final String ALL_OTHER_CITY_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + "AllOtherCityWhole.txt";
	public static final String TYPE_PINYING = "Rent";
	
	public static ArrayList<Pair> cityAndSubdomainList;

	/*
	 * http://www.anjuke.com/index/ 上的所有其它城市，（除去 北京 上海）  但是 包括 广州 深圳
	 * */
	@Test
	public void allOtherCityWhole(){
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(ALL_OTHER_CITY_URL, ALL_OTHER_CITY_REGEX, 0);
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			String href = element.attr("href");
			String word = element.text();
			if ( !(word.equals("北京")||word.equals("上海"))) {
				sb.append(href);
				sb.append(",");
				sb.append(word);
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
		FileUtil.writeStr2File(sb.toString(), ALL_OTHER_CITY_FILE_PATH, "utf-8");
	}
	
	//提取所有的城市和子域名的对应关系
	public static void initCityAndSubdomainList(){
		String content = FileUtil.getDataFile2StrKeepReturn(ALL_OTHER_CITY_FILE_PATH, "utf-8");
		String[] lineArr = content.split("\n");
		cityAndSubdomainList = new ArrayList<Pair>();
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
	}
	
	public static void createAllOtherCityBatchJobFile(){
		//提取所有的城市和子域名的对应关系
		initCityAndSubdomainList();
		
		//根据提取到的对应关系，进行所有城市的batchfile的创建
		for (Pair pair : cityAndSubdomainList){
			AnjukeGenericRent genericRent = new AnjukeGenericRent(pair.getKey(), pair.getValue());
			genericRent.doAllProcessInOneFunction();
			System.out.println("/***************************************************************/");
			System.out.println("city:" + pair.getKey() + ", subdomain:" + pair.getValue() + " is completed!");
			System.out.println("/***************************************************************/");
		}
	}
	
	/*
	 * 将前面产生的Prepare的BatchJob文件 添加上起始标志，并且重新生成新的文件，（不含Prepare的后缀了）
	 * */
	public static void createAllOtherCityBatchJobReadyFile(){
		//提取所有的城市和子域名的对应关系
		initCityAndSubdomainList();
		
		for (Pair pair : cityAndSubdomainList) {
			String URL_SUBDOMAIN = pair.getValue();
			String CITY_PINYING = URL_SUBDOMAIN.toUpperCase().charAt(0) + URL_SUBDOMAIN.substring(1);
			String BATCH_FILE_PREPARE_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
			String BATCH_FILE_READY_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFile.txt";
			
			String fileContent = FileUtil.getDataFile2StrKeepReturn(BATCH_FILE_PREPARE_PATH, "utf-8");
			fileContent = "^" + "\n" + fileContent + "$" + "\n";
			FileUtil.writeStr2File(fileContent, BATCH_FILE_READY_PATH, "utf-8");
			System.out.println(BATCH_FILE_READY_PATH + " is completed!");
		}
	}
	
	public static ArrayList<String> getBatchJobFilePath4AllOtherCitySecondhand(){
		//提取所有的城市和子域名的对应关系
		initCityAndSubdomainList();
		ArrayList<String> filePathList = new ArrayList<String>();
		for (Pair pair : cityAndSubdomainList) {
			String URL_SUBDOMAIN = pair.getValue();
			String CITY_PINYING = URL_SUBDOMAIN.toUpperCase().charAt(0) + URL_SUBDOMAIN.substring(1);
			String BATCH_FILE_READY_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFile.txt";
			filePathList.add(BATCH_FILE_READY_PATH);
		}
		return filePathList;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createAllOtherCityBatchJobFile();
	}
}
