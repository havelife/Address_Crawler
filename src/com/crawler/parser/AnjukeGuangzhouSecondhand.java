package com.crawler.parser;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeGuangzhouSecondhand {
	public static final String CITY_PINYING = "Guangzhou";
	public static String URL_SUBDOMAIN = "guangzhou";
	public static String URL_SUBCATEGORY = "sale";
	public static final String CITY = "广州";
	
	
	public static final String TYPE = "安居客-二手房";
	public static final String WHOLE_URL = "http://" + URL_SUBDOMAIN + ".anjuke.com/" + URL_SUBCATEGORY + "/";
	public static final String TYPE_PINYING = "Secondhand";
	public static final String SITE_NAME_PINYING = "Anjuke";
	public static final String ALL_SINGLE_SUB_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "AllSingleSub.txt";
	public static final String SECONDHAND_WHOLE_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "Whole.txt";
	
	public static final String TYPE_SPAN = "#";
	public static final String CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
	
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
		
		p11.setValue("b41");
		p12.setValue("b43");
		p13.setValue("b49");
		p14.setValue("b50");
		p15.setValue("b51");
		p16.setValue("b52");
		
		roomNumbuerArrayList.add(p11);
		roomNumbuerArrayList.add(p12);
		roomNumbuerArrayList.add(p13);
		roomNumbuerArrayList.add(p14);
		roomNumbuerArrayList.add(p15);
		roomNumbuerArrayList.add(p16);
		
		//设置类型
		p21.setKey("公寓");
		p22.setKey("普通住宅");
		p23.setKey("别墅");
		p24.setKey("其它");
		
		p21.setValue("t17");
		p22.setValue("t18");
		p23.setValue("t19");
		p24.setValue("t20");
		
		houseTypeArrayList.add(p21);
		houseTypeArrayList.add(p22);
		houseTypeArrayList.add(p23);
		houseTypeArrayList.add(p24);
		
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
		
		p31.setValue("a35");
		p32.setValue("a36");
		p33.setValue("a37");
		p34.setValue("a38");
		p35.setValue("a39");
		p36.setValue("a40");
		p37.setValue("a41");
		p38.setValue("a42");
		p39.setValue("a43");
		p30.setValue("a44");
		
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
		
		//设置价格
		p41.setKey("30万元以下");
		p42.setKey("30-50万");
		p43.setKey("50-80万");
		p44.setKey("80-100万");
		p45.setKey("100-120万");
		p46.setKey("120-150万");
		p47.setKey("150-200万");
		p48.setKey("200-250万");
		p49.setKey("250-300万");
		p50.setKey("300-500万");
		p51.setKey("500万以上");
		
		p41.setValue("m87");
		p42.setValue("m88");
		p43.setValue("m39");
		p44.setValue("m40");
		p45.setValue("m41");
		p46.setValue("m42");
		p47.setValue("m43");
		p48.setValue("m44");
		p49.setValue("m45");
		p50.setValue("m46");
		p51.setValue("m89");
		
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
					int roomNumbuerOriginCount = count, roomNumbuerTotal = 0;
					for (Pair roomNumberPair : roomNumbuerArrayList) {
						String url = href + roomNumberPair.getValue();
						count = getCount(url);
						roomNumbuerTotal += count;
						if (count > 0){
							if (count > 100){
								int houseTypeOriginCount = count, houseTypeTotal = 0;
								for (Pair houseTypePair : houseTypeArrayList) {
									url = href + roomNumberPair.getValue() + "-" + houseTypePair.getValue();
									count = getCount(url);
									houseTypeTotal += count;
									if (count > 0){
										if (count > 100){
											int areaSizeOriginCount = count, areaSizeTotal = 0;
											for (Pair areaSizePair : areaSizeArrayList) {
												url = href + areaSizePair.getValue() + "-" + roomNumberPair.getValue() + "-" + houseTypePair.getValue();
												count = getCount(url);
//												System.out.println("before, areaSizeTotal:" + areaSizeTotal + ", add count:" + count + ", at url:" + url);
												areaSizeTotal += count;
												if (count > 0) {
													if (count > 100){
														int priceRangeOriginCount = count, priceRangeTotal = 0;
														for (Pair priceRangePair : priceRangeArrayList) {
															url = href + areaSizePair.getValue() + "-" + roomNumberPair.getValue() + "-" + priceRangePair.getValue() + "-" + houseTypePair.getValue();
															count = getCount(url);
															priceRangeTotal += count;
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
														}//end of for (Pair priceRangePair : priceRangeArrayList) 
														if (priceRangeTotal < priceRangeOriginCount) {
															System.err.println(roomNumberPair.getKey() + "#" + houseTypePair.getKey() + "#" + areaSizePair.getKey() + "#ERROR!!!!   " + "priceRangeTotal:" + priceRangeTotal + ", priceRangeOriginCount:" + priceRangeOriginCount);
															return "";
														} else {
															System.out.println(roomNumberPair.getKey() + "#" + houseTypePair.getKey() + "#" + areaSizePair.getKey() + "#priceRangeTotal:" + priceRangeTotal + ", priceRangeOriginCount:" + priceRangeOriginCount);
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
											}// end of for (Pair areaSizePair : areaSizeArrayList)
											if (areaSizeTotal < areaSizeOriginCount) {
												System.err.println(roomNumberPair.getKey() + "#" + houseTypePair.getKey() + "#ERROR!!!!   " + "areaSizeTotal:" + areaSizeTotal + ", areaSizeOriginCount:" + areaSizeOriginCount);
												return "";
											} else {
												System.out.println(roomNumberPair.getKey() + "#" + houseTypePair.getKey() + "#areaSizeTotal:" + areaSizeTotal + ", areaSizeOriginCount:" + areaSizeOriginCount);
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
								}// end of for (Pair houseTypePair : houseTypeArrayList)
								if (houseTypeTotal < houseTypeOriginCount) {
									System.err.println(roomNumberPair.getKey() + "#ERROR!!!!   " + "houseTypeTotal:" + houseTypeTotal + ", houseTypeOriginCount:" + houseTypeOriginCount);
									return "";
								} else {
									System.out.println(roomNumberPair.getKey() + "#houseTypeTotal:" + houseTypeTotal + ", houseTypeOriginCount:" + houseTypeOriginCount);
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
					}// end of for (Pair roomNumberPair : roomNumbuerArrayList)
					if (roomNumbuerTotal < roomNumbuerOriginCount) {
						System.err.println(word + "#ERROR!!!!   " + "roomNumbuerTotal:" + roomNumbuerTotal + ", roomNumbuerOriginCount:" + roomNumbuerOriginCount);
						return "";
					} else {
						System.out.println(word + "#roomNumbuerTotal:" + roomNumbuerTotal + ", roomNumbuerOriginCount:" + roomNumbuerOriginCount);
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
	
	@Test
	public void createBatchFileAnjukeGuangzhouSecondhand(){
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
			else if (prefix.indexOf("a", prefix.lastIndexOf("/")) == -1 && prefix.indexOf("b", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("m", prefix.lastIndexOf("/")) == -1 && prefix.indexOf("t", prefix.lastIndexOf("/")) == -1) {  
				prefix += "-p";
				suffix = "";
			}
			//CASE 3
			//http://beijing.anjuke.com/sale/beiyuan/b17-t7
			//http://beijing.anjuke.com/sale/beiyuan/b17-p2-t7
			else if (prefix.indexOf("a", prefix.lastIndexOf("/")) == -1 && prefix.indexOf("b", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("m", prefix.lastIndexOf("/")) == -1 && prefix.indexOf("t", prefix.lastIndexOf("/")) != -1) {
				int idx = url.lastIndexOf("-");
				prefix = url.substring(0, idx) + "-p";
				suffix = url.substring(idx);
			}
			//CASE 4
			//http://beijing.anjuke.com/sale/beiyuan/a15-b17-t9
			//http://beijing.anjuke.com/sale/beiyuan/a15-b17-p2-t9
			else if (prefix.indexOf("a", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("b", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("m", prefix.lastIndexOf("/")) == -1 && prefix.indexOf("t", prefix.lastIndexOf("/")) != -1) {
				int idx = url.lastIndexOf("-");
				prefix = url.substring(0, idx) + "-p";
				suffix = url.substring(idx);
			}
			//CASE 5
			//http://beijing.anjuke.com/sale/chaoyanggongyuan/a19-b23-m23-t7
			//http://beijing.anjuke.com/sale/chaoyanggongyuan/a19-b23-m23-p2-t7
			else if (prefix.indexOf("a", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("b", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("m", prefix.lastIndexOf("/")) != -1 && prefix.indexOf("t", prefix.lastIndexOf("/")) != -1) {
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
		new AnjukeGuangzhouSecondhand().createBatchFileAnjukeGuangzhouSecondhand();
		//secondhandSubSingle("http://shenzhen.anjuke.com/sale/futian/,福田");
		//createBatchFileAnjukeShanghaiSecondhand();
//		Element element = ParserUtil.parseUrlWithRegexAndResultIndex("http://beijing.anjuke.com/sale/sanlitun/", "div[class=current]", 0);
	}
}
