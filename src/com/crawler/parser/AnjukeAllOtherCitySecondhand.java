package com.crawler.parser;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeAllOtherCitySecondhand {
	public static final String CITY_PINYING = "Guangzhou";
	public static String URL_SUBDOMAIN = "guangzhou";
	public static String URL_SUBCATEGORY = "sale";
	public static final String CITY = "广州";
	
	
	public static final String TYPE = "安居客-二手房";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public static final String ALL_OTHER_CITY_URL = "http://www.anjuke.com/index/";
	public static final String ALL_OTHER_CITY_REGEX = "div[class=cities_boxer]";
	
	public static final String ALL_OTHER_CITY_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + "AllOtherCityWhole.txt";
	
	public static final String TYPE_PINYING = "Secondhand";
	
	public static final String ALL_SINGLE_SUB_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "AllSingleSub.txt";
	public static final String SECONDHAND_WHOLE_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "Whole.txt";
	
	public static final String TYPE_SPAN = "#";
	public static final String CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
	
	
	
	public static ArrayList<Pair> roomNumbuerArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> houseTypeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> areaSizeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> priceRangeArrayList = new ArrayList<Pair>();

	
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
	

	
	public static void writeElements2File(Elements elements, String filePath){
		FileUtil.writeStr2File(writeElements2String(elements), filePath);
	}
	
	/*
	 * 获取比如 朝阳-团结湖 有多少页
	 * http://beijing.anjuke.com/community/chaoyang/tuanjiehu
	 * 8页
	 * */
	public static int getCount(String url){
		Element element = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[class=current]", 0);
		if (element == null ) {
			System.out.println("url:" + url + ", count:0");
			return 0;
		}
		String html = element.html();
		String count = html.substring(html.indexOf("/") + 1);
		return Integer.parseInt(count);
	}
	
	public static String writeElements2String(Elements elements){
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			String href = element.attr("href");
			String word = element.text();
			sb.append(href);
			sb.append(",");
			sb.append(word);
			sb.append("\n");
		}
		return sb.toString();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AnjukeAllOtherCitySecondhand();
		//secondhandSubSingle("http://shenzhen.anjuke.com/sale/futian/,福田");
		//createBatchFileAnjukeShanghaiSecondhand();
//		Element element = ParserUtil.parseUrlWithRegexAndResultIndex("http://beijing.anjuke.com/sale/sanlitun/", "div[class=current]", 0);
	}
}
