package com.crawler.parser;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;

public class AnjukeBeijing {
	public static final String ALL_SINGLE_SUB_FILE_PATH = "./data/parser/Anjuke/AnjukeBeijingXiaoquAllSingleSub.txt";
	public static final String XIAOQU_WHOLE_FILE_PATH = "./data/parser/Anjuke/AnjukeBeijingXiaoquWhole.txt";
	public static final String SPLIT_REGEX = "$";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AnjukeBeijing().xiaoquSub();  
	}
	
	/*
	 * 北京安居客，小区，北京总的各个地域，（朝阳 海淀 丰台 等等）
	 * */
	@Test
	public void xiaoquWhole(){
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex("http://beijing.anjuke.com/community/", "div[class=box boxline]", 0);
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			String href = element.attr("href");
			String word = element.text();
			sb.append(href);
			sb.append(",");
			sb.append(word);
			sb.append("\n" + SPLIT_REGEX); // 分页不不干净，只要加了$
		}
		FileUtil.writeStr2File(sb.toString(), XIAOQU_WHOLE_FILE_PATH, "utf-8");
	}
	
	/*
	 * 北京安居客，小区， 某个单个小区，比如 单个的朝阳
	 * */

	public String xiaoquSubSingle(String line){
		//prepare data
		String[] strArr = line.split(",");
		String url = strArr[0];
		String district = strArr[1];
			
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[class=box]", 0);
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		String result = writeElements2String(elements, district);
		System.err.println(result);
		return result;
	}
	
	/*
	 * 北京安居客，小区，下面的分区块的信息，比如 朝阳下的 团结湖，燕莎等等
	 * 
	 * 这个方法是把所有的subSingle串起来的
	 * */
	@Test
	public void xiaoquSub(){
		String data = FileUtil.getDataFile2Str(XIAOQU_WHOLE_FILE_PATH, "utf-8");
		String[] lineArr = StringUtils.split(data, SPLIT_REGEX);//data.split("$"); 国双教的，具体原因不清楚
		StringBuffer sb = new StringBuffer();
		for (String line : lineArr){
			sb.append(xiaoquSubSingle(line));
		}
		FileUtil.writeStr2File(sb.toString(), ALL_SINGLE_SUB_FILE_PATH);
	}
	
	public static void writeElements2File(Elements elements, String filePath){
		FileUtil.writeStr2File(writeElements2String(elements), filePath);
	}
	
	public static String writeElements2String(Elements elements, String addPrefix){
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			String href = element.attr("href");
			String word = element.text();
			//把'全部' 这行跳过
			if (!word.equals("全部")){
				sb.append(href);
				sb.append(",");
				sb.append(addPrefix);
				sb.append(",");
				sb.append(word);
				sb.append(",");
				sb.append(getCount(href));
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	/*
	 * 获取比如 朝阳-团结湖 有多少页
	 * http://beijing.anjuke.com/community/chaoyang/tuanjiehu
	 * 8页
	 * */
	public static int getCount(String url){
		Element element = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[class=current]", 0);
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
	
	public void createBatchFile(){
		String content = FileUtil.getDataFile2Str("", "utf-8");
	}

}
