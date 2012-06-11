package com.crawler.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;

public class AnjukeGenericXiaoqu {
	public static String CITY_PINYING;
	public static String CITY;
	public static String XIAO_QU_URL_SUBDOMAIN;
	
	public static final String WHOLE_SELECT_REGEX_1 = "div[class=box boxline]";
	public static final String WHOLE_SELECT_REGEX_2 = "div[id=apf_id_10_areacontainer]";
	public static final String SUB_SINGLE_SELECT_REGEX_1 = "div[class=box]";
	public static final String SUB_SINGLE_SELECT_REGEX_2 = "div[id=apf_id_10_blockcontainer]";
	
	public static final String TYPE_PINYING = "Xiaoqu";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public String ALL_SINGLE_SUB_FILE_PATH;
	public String XIAOQU_WHOLE_FILE_PATH;
	public static final String TYPE = "安居客-小区";
	public static final String TYPE_SPAN = "#";
	public String CREATE_BATCH_FILE_RESULT_PATH;
	public String WHOLE_URL;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AnjukeGenericXiaoqu xiaoQu = new AnjukeGenericXiaoqu("青岛", "qd");
		//xiaoQu.xiaoquWhole();
		//xiaoQu.xiaoquSubSingle("http://qd.anjuke.com/community/W0QQpZ1QQp1Z3644,城阳区");
		//xiaoQu.xiaoquSub();
		
		xiaoQu.doWholeProcessInOneFunction();
	}
	
	public AnjukeGenericXiaoqu() {
		super();
	}
	
	public AnjukeGenericXiaoqu(String city, String subDomain) {
		// TODO Auto-generated constructor stub
		super();
		this.CITY = city;
		this.XIAO_QU_URL_SUBDOMAIN = subDomain;
		this.CITY_PINYING = XIAO_QU_URL_SUBDOMAIN.toUpperCase().charAt(0) + XIAO_QU_URL_SUBDOMAIN.substring(1);
		
		ALL_SINGLE_SUB_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "AllSingleSub.txt";
		XIAOQU_WHOLE_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "Whole.txt";
		CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
		WHOLE_URL = "http://" + XIAO_QU_URL_SUBDOMAIN + ".anjuke.com/community/";
	}

	
	/*
	 * 上海安居客，小区，上海总的各个地域，（浦东 闵行 徐汇 等等）
	 * */
	@Test
	public void xiaoquWhole(){
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(WHOLE_URL, WHOLE_SELECT_REGEX_1, 0);
		// 第一种无效，就试试第二种regex
		if (elem == null){
			elem = ParserUtil.parseUrlWithRegexAndResultIndex(WHOLE_URL, WHOLE_SELECT_REGEX_2, 0);
		}
		// 说明两种regex找下来都找不到
		if (elem == null){
			System.out.println("error in xiaoqu whole, maybe the select regex is wrong. please check it.");
			return;
		}
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
	 * 上海安居客，小区， 某个单个小区，比如 单个的浦东，把浦东下面的小区块都结合起来，就比如陆家嘴，川沙，唐镇
	 * */

	public String xiaoquSubSingle(String line){
		//prepare data
		String[] strArr = line.split(",");
		String url = strArr[0];
		String district = strArr[1];
			
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(url, SUB_SINGLE_SELECT_REGEX_1, 0);
		if (elem == null) {
			elem = ParserUtil.parseUrlWithRegexAndResultIndex(url, SUB_SINGLE_SELECT_REGEX_2, 0);
		}
		// 说明两种regex找下来都找不到
		if (elem == null){
			System.out.println("error in xiaoqu SubSingle, maybe the select regex is wrong. please check it.");
			try {
				throw new Exception("xiaoqu SubSingle select regex error");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		String result = writeElements2String(elements, district);
		System.err.println(result);
		return result;
	}
	
	/*
	 * 上海安居客，小区，下面的分区块的信息，把浦东、闵行等下的所有小区块都汇总到一起
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
			int count = getCount(href);
			
			
			if (count > 0){
				String prefix;
				if (count == 1){
					prefix = href;
				} else {
					prefix = getPrefix(href);
				}
				//把'全部' 这行跳过
				if (!word.equals("全部")){
					sb.append(href);
					sb.append(",");
					sb.append(addPrefix);
					sb.append(",");
					sb.append(word);
					sb.append(",");
					sb.append(count);
					sb.append(",");
					sb.append(prefix);
					sb.append("\n");
				}
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
	
	public static String getPrefix(String url){
		Element element = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[class=pagelink nextpage]", 0);
		if (element == null) {
			System.err.println("empty page when call getPrefix() in AnjukeGenericXiaoqu, url:" + url);
			return "";
		}
		element = ParserUtil.parseElementWithRegexAndResultIndex(element, "a", 0);
		String href = element.attr("href");
		href = href.substring(0, href.length() - 1);
		return href;
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
			System.out.println(line);
			String prefix = attrArr[4].trim();
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
	
	@Test
	public void doWholeProcessInOneFunction(){
		//step1, 把一个城市的几个大的区划，下载下来。
		xiaoquWhole();
		//step2, 把各个大的区划下的小区，全部下载下来，合并成一个文件
		xiaoquSub();
		//step3, 生成最终的batchJob文件
		createBatchFile();
	}

}
