package com.crawler.parser;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeShanghaiSecondhand {
	public static final String CITY_PINYING = "Shanghai";
	public static final String TYPE_PINYING = "Secondhand";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public static final String ALL_SINGLE_SUB_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "AllSingleSub.txt";
	public static final String SECONDHAND_WHOLE_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "Whole.txt";
	public static final String CITY = "上海";
	public static final String TYPE = "安居客-二手房";
	public static final String TYPE_SPAN = "#";
	public static final String CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
	public static final String WHOLE_URL = "http://shanghai.anjuke.com/sale/";
	public static final String WHOLE_SELECT_REGEX = "div[id=apf_id_13_areacontainer]";
	
	public static ArrayList<Pair> roomNumbuerArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> houseTypeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> areaSizeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> priceRangeArrayList = new ArrayList<Pair>();
	
	
	static {
		
		Pair p11 = new Pair();
		Pair p12 = new Pair();
		Pair p13 = new Pair();
		Pair p14 = new Pair();
		Pair p15 = new Pair();
		Pair p16 = new Pair();
		
		Pair p21 = new Pair();
		Pair p22 = new Pair();
		Pair p23 = new Pair();
		Pair p24 = new Pair();
		Pair p25 = new Pair();
		Pair p26 = new Pair();
		
		Pair p31 = new Pair();
		Pair p32 = new Pair();
		Pair p33 = new Pair();
		Pair p34 = new Pair();
		Pair p35 = new Pair();
		Pair p36 = new Pair();
		Pair p37 = new Pair();
		Pair p38 = new Pair();
		Pair p39 = new Pair();
		Pair p30 = new Pair();
		
		Pair p41 = new Pair();
		Pair p42 = new Pair();
		Pair p43 = new Pair();
		Pair p44 = new Pair();
		Pair p45 = new Pair();
		Pair p46 = new Pair();
		Pair p47 = new Pair();
		Pair p48 = new Pair();
		Pair p49 = new Pair();
		Pair p50 = new Pair();
		Pair p51 = new Pair();
		
		
		//设置房型
		p11.setKey("一室");
		p12.setKey("二室");
		p13.setKey("三室");
		p14.setKey("四室");
		p15.setKey("五室");
		p16.setKey("五室以上");
		
		p11.setValue("b15");
		p12.setValue("b17");
		p13.setValue("b23");
		p14.setValue("b24");
		p15.setValue("b25");
		p16.setValue("b26");
		
		//设置类型
		p21.setKey("四合院");
		p22.setKey("公寓");
		p23.setKey("普通住宅");
		p24.setKey("别墅");
		p25.setKey("平房");
		p26.setKey("其它");
		
		p21.setValue("t11");
		p22.setValue("t7");
		p23.setValue("t9");
		p24.setValue("t8");
		p25.setValue("t12");
		p26.setValue("t10");
		
		//设置面积
		p31.setKey("50平米以下");
		p32.setKey("50-70平米");
		p33.setKey("70-90平米");
		p34.setKey("90-110平米");
		p35.setKey("110-130平米");
		p36.setKey("130-150平米");
		p37.setKey("150-200平米");
		p38.setKey("200-300平米");
		p39.setKey("300-500平米");
		p30.setKey("500平米以上");
		
		
		p31.setValue("a13");
		p32.setValue("a14");
		p33.setValue("a15");
		p34.setValue("a16");
		p35.setValue("a17");
		p36.setValue("a18");
		p37.setValue("a19");
		p38.setValue("a20");
		p39.setValue("a21");
		p30.setValue("a22");
		
		//设置价格
		p41.setKey("50万元以下");
		p42.setKey("50-80万");
		p43.setKey("80-100万");
		p44.setKey("100-120万");
		p45.setKey("120-150万");
		p46.setKey("150-200万");
		p47.setKey("200-250万");
		p48.setKey("250-300万");
		p49.setKey("300-500万");
		p50.setKey("500-1000万");
		p51.setKey("1000万以上");
		
		
		p41.setValue("m14");
		p42.setValue("m15");
		p43.setValue("m16");
		p44.setValue("m17");
		p45.setValue("m18");
		p46.setValue("m19");
		p47.setValue("m20");
		p48.setValue("m21");
		p49.setValue("m22");
		p50.setValue("m23");
		p51.setValue("m24");
		
		
		roomNumbuerArrayList.add(p11);
		roomNumbuerArrayList.add(p12);
		roomNumbuerArrayList.add(p13);
		roomNumbuerArrayList.add(p14);
		roomNumbuerArrayList.add(p15);
		roomNumbuerArrayList.add(p16);
		
		houseTypeArrayList.add(p21);
		houseTypeArrayList.add(p22);
		houseTypeArrayList.add(p23);
		houseTypeArrayList.add(p24);
		houseTypeArrayList.add(p25);
		houseTypeArrayList.add(p26);
		
		areaSizeArrayList.add(p31);
		areaSizeArrayList.add(p32);
		areaSizeArrayList.add(p33);
		areaSizeArrayList.add(p34);
		areaSizeArrayList.add(p35);
		areaSizeArrayList.add(p36);
		areaSizeArrayList.add(p37);
		areaSizeArrayList.add(p38);
		areaSizeArrayList.add(p39);
		areaSizeArrayList.add(p30);
		
		priceRangeArrayList.add(p41);
		priceRangeArrayList.add(p42);
		priceRangeArrayList.add(p43);
		priceRangeArrayList.add(p44);
		priceRangeArrayList.add(p45);
		priceRangeArrayList.add(p46);
		priceRangeArrayList.add(p47);
		priceRangeArrayList.add(p48);
		priceRangeArrayList.add(p49);
		priceRangeArrayList.add(p50);
		priceRangeArrayList.add(p51);
	}

	
	/*
	 * 上海安居客，二手房，上海总的各个地域，（浦东 闵行 徐汇 等等）
	 * */
	@Test
	public void secondhandWhole(){
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(WHOLE_URL, WHOLE_SELECT_REGEX, 0);
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
		FileUtil.writeStr2File(sb.toString(), SECONDHAND_WHOLE_FILE_PATH, "utf-8");
	}
	
	/*
	 * 上海安居客，二手房， 某个单个小区，比如 单个的浦东
	 * */

	public String secondhandSubSingle(String line){
		//prepare data
		String[] strArr = line.split(",");
		String url = strArr[0];
		String district = strArr[1];
			
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[id=apf_id_13_blockcontainer]", 0);
		//可能会空，像怀柔就没有
		if (elem == null ){
			return "";
		}
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		String result = writeElements2String(elements, district);
		System.err.println(result);
		return result;
	}
	
	/*
	 * 上海安居客，二手房，下面的分区块的信息，subSingle的串起来
	 * 
	 * 这个方法是把所有的subSingle串起来的
	 * */
	@Test
	public void secondhandSub(){
		String data = FileUtil.getDataFile2StrKeepReturn(SECONDHAND_WHOLE_FILE_PATH, "utf-8");
		String[] lineArr = StringUtils.split(data, "\n");
		StringBuffer sb = new StringBuffer();
		for (String line : lineArr){
			sb.append(secondhandSubSingle(line));
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
			
			System.out.print(word + " : ");
			int count = getCount(href);
			System.out.println(count);
			
			if (count > 0){
				if (count > 100){
					for (Pair roomNumberPair : roomNumbuerArrayList) {
						String url = href + roomNumberPair.getValue();
						count = getCount(url);
						if (count > 0){
							if (count > 100){
								for (Pair houseTypePair : houseTypeArrayList) {
									url = href + roomNumberPair.getValue() + "-" + houseTypePair.getValue();
									count = getCount(url);
									if (count > 0){
										if (count > 100){
											for (Pair areaSizePair : areaSizeArrayList) {
												url = href + areaSizePair.getValue() + "-" + roomNumberPair.getValue() + "-" + houseTypePair.getValue();
												count = getCount(url);
												if (count > 0) {
													if (count > 100){
														
														for (Pair priceRangePair : priceRangeArrayList) {
															url = href + areaSizePair.getValue() + "-" + roomNumberPair.getValue() + "-" + priceRangePair.getValue() + "-" + houseTypePair.getValue();
															count = getCount(url);
															
															if (count > 0){
																//这里是指 比如 '朝阳,甘露园,一室,普通住宅,70-90平米, xxx万,1' 即六级划分才小于100
																//组装数据
																sb.append(url);
																sb.append(",");
																sb.append(addPrefix);
																sb.append(",");
																// 如果到了这里还是大于100，那实在是没有办法，在记录条数时写100，但是在这个文字注解部分可以把真实的页数标注一下
																if (count > 100) {
																	sb.append(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + priceRangePair.getKey() + "#" + houseTypePair.getKey() + "#" + count);
																	sb.append(",");
																	sb.append(100);
																} else {
																	sb.append(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + priceRangePair.getKey() + "#" + houseTypePair.getKey());
																	sb.append(",");
																	sb.append(count);
																}
																sb.append("\n");
																
																//log
																if (count > 100) {
																	//超过100的，用红色打印，着重表示一下，比较醒目
																	System.err.println(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + priceRangePair.getKey() + "#" + houseTypePair.getKey() + " : " + count);
																} else {
																	System.out.println(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + priceRangePair.getKey() + "#" + houseTypePair.getKey() + " : " + count);
																}
															}
														}
														
													} else {
														
														//这里是指 比如 '朝阳,甘露园,一室,普通住宅,70-90平米,1' 即五级划分才小于100
														//组装数据
														sb.append(url);
														sb.append(",");
														sb.append(addPrefix);
														sb.append(",");
														sb.append(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + houseTypePair.getKey());
														sb.append(",");
														sb.append(count);
														sb.append("\n");
														
														//log
														System.out.println(word + "#" + areaSizePair.getKey() + "#" + roomNumberPair.getKey()+ "#" + houseTypePair.getKey() + " : " + count);
													}
												}
											}
										} else {
											//这里是指 比如 '朝阳,甘露园,一室,普通住宅,14' 即四级划分才小于100
											//组装数据
											sb.append(url);
											sb.append(",");
											sb.append(addPrefix);
											sb.append(",");
											sb.append(word + "#" + roomNumberPair.getKey()+ "#" + houseTypePair.getKey());
											sb.append(",");
											sb.append(count);
											sb.append("\n");
											
											//log
											System.out.println(word + "#" + roomNumberPair.getKey()+ "#" + houseTypePair.getKey() + " : " + count);
										}
									}
								}
							} else {
								//这里是指 比如 '朝阳,甘露园,一室,16' 即三级划分才小于100
								//组装数据
								sb.append(url);
								sb.append(",");
								sb.append(addPrefix);
								sb.append(",");
								sb.append(word + "#" + roomNumberPair.getKey());
								sb.append(",");
								sb.append(count);
								sb.append("\n");
								
								//log
								System.out.println(word + "#" + roomNumberPair.getKey() + " : " + count);
							}
						}
					}
				} else {
					//这里是指 比如 '朝阳,甘露园,32' 即两级划分就小于100了
					//组装数据
					sb.append(href);
					sb.append(",");
					sb.append(addPrefix);
					sb.append(",");
					sb.append(word);
					sb.append(",");
					sb.append(count);
					sb.append("\n");
					
					//log
					System.out.println(word + " : " + count);
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
		if (element == null ) {
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
	
	@Test
	public void createBatchFileAnjukeShanghaiSecondhand(){
		//用以统计总共有多少个页面
		int totalCount = 0;
		String content = FileUtil.getDataFile2StrKeepReturn(ALL_SINGLE_SUB_FILE_PATH, "utf-8");
		String[] lineArr = content.split("\n");
		StringBuffer sb = new StringBuffer();
		for(String line : lineArr){
			String[] attrArr = line.split(",");
			//form the attributes for the output file
			String prefix = attrArr[0].trim();
			String suffix = "";
			String url = prefix;
			//这里要分四种情况来拼接这个prefix和suffix
			
			//CASE 1
			//http://beijing.anjuke.com/sale/anhuiqiao/
			//http://beijing.anjuke.com/sale/anhuiqiao/p2
			if (prefix.lastIndexOf("/") + 1 == prefix.length()) {
				prefix += "p";
				suffix = "";
			}
			//CASE 2
			//http://beijing.anjuke.com/sale/aolinpikegongyuan/b24
			//http://beijing.anjuke.com/sale/aolinpikegongyuan/b24-p2
			if (prefix.lastIndexOf("/") + 4 == prefix.length()) {
				prefix += "-p";
				suffix = "";
			}
			//CASE 3
			//http://beijing.anjuke.com/sale/beiyuan/b17-t7
			//http://beijing.anjuke.com/sale/beiyuan/b17-p2-t7
			if (prefix.lastIndexOf("/") + 5 == prefix.lastIndexOf("t")) {
				int idx = url.lastIndexOf("-");
				prefix = url.substring(0, idx) + "-p";
				suffix = url.substring(idx);
			}
			//CASE 4
			//http://beijing.anjuke.com/sale/beiyuan/a15-b17-t9
			//http://beijing.anjuke.com/sale/beiyuan/a15-b17-p2-t9
			if (prefix.lastIndexOf("/") + 9 == prefix.lastIndexOf("t")) {
				int idx = url.lastIndexOf("-");
				prefix = url.substring(0, idx) + "-p";
				suffix = url.substring(idx);
			}
			//CASE 5
			//http://beijing.anjuke.com/sale/chaoyanggongyuan/a19-b23-m23-t7
			//http://beijing.anjuke.com/sale/chaoyanggongyuan/a19-b23-m23-p2-t7
			if (prefix.lastIndexOf("/") + 13 == prefix.lastIndexOf("t")) {
				int idx = url.lastIndexOf("-");
				prefix = url.substring(0, idx) + "-p";
				suffix = url.substring(idx);
			}
			
			
			
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
		System.out.println("##########" + totalCount);
		FileUtil.writeStr2File(sb.toString(), CREATE_BATCH_FILE_RESULT_PATH);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AnjukeShanghaiSecondhand().secondhandSubSingle("http://shanghai.anjuke.com/sale/pudong/,浦东");
//		Element element = ParserUtil.parseUrlWithRegexAndResultIndex("http://beijing.anjuke.com/sale/sanlitun/", "div[class=current]", 0);
	}
}
