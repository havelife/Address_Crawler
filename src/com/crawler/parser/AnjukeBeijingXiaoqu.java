package com.crawler.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;

public class AnjukeBeijingXiaoqu {
	public static final String ALL_SINGLE_SUB_FILE_PATH = "./data/parser/Anjuke/AnjukeBeijingXiaoquAllSingleSub.txt";
	public static final String XIAOQU_WHOLE_FILE_PATH = "./data/parser/Anjuke/AnjukeBeijingXiaoquWhole.txt";
	public static final String SPLIT_REGEX = "$";
	public static final String CITY = "北京";
	public static final String TYPE = "安居客-小区";
	public static final String TYPE_SPAN = "#";
	public static final String PREFIX_TAIL = "/W0QQpZ";
	public static final String CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/Anjuke/AnjukeBeijingXiaoquBatchCreateJobFilePrepare.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AnjukeBeijingXiaoqu().createBatchFile();  
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
			sb.append("\n");
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
		String data = FileUtil.getDataFile2StrKeepReturn(XIAOQU_WHOLE_FILE_PATH, "utf-8");
		String[] lineArr = data.split("\n");
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
	
	@Test
	public void createBatchFile(){
		//用以统计总共有多少个页面
		int totalCount = 0;
		String content = FileUtil.getDataFile2StrKeepReturn(ALL_SINGLE_SUB_FILE_PATH, "utf-8");
		String[] lineArr = content.split("\n");
		StringBuffer sb = new StringBuffer();
		for(String line : lineArr){
			String[] attrArr = line.split(",");
			//form the attributes for the output file
			String prefix = attrArr[0].trim() + PREFIX_TAIL;
			String suffix = "";
			int step = 1;
			int startidx = 1;
			int endidx = Integer.parseInt(attrArr[3].trim());
			String category = CITY;
			String type = TYPE + TYPE_SPAN + attrArr[1] + TYPE_SPAN + attrArr[2];
			String domain = attrArr[0].trim();
			
			//form the final output line
			sb.append(prefix);
			sb.append(",");
			sb.append(suffix);
			sb.append(",");
			sb.append(step);
			sb.append(",");
			sb.append(startidx);
			sb.append(",");
			sb.append(endidx);
			sb.append(",");
			sb.append(category);
			sb.append(",");
			sb.append(type);
			sb.append(",");
			sb.append(domain);
			sb.append("\n");
			
			totalCount += endidx;
			System.out.println(line);
		}
		System.err.println(totalCount);
		FileUtil.writeStr2File(sb.toString(), CREATE_BATCH_FILE_RESULT_PATH);
	}

}
