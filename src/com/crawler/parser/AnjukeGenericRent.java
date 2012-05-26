package com.crawler.parser;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;
import com.crawler.vo.Pair;

public class AnjukeGenericRent {
	public static String URL_SUBDOMAIN; //= "tianjin";
	public static String CITY; // = "天津";
	
	public final String CITY_PINYING;
	public final String URL_SUBCATEGORY;
	public final String TYPE;
	public final String TYPE_PINYING;
	public final String SITE_NAME_PINYING;
	public final String WHOLE_URL;
	public final String ALL_SINGLE_SUB_FILE_PATH;
	public final String RENT_WHOLE_FILE_PATH;
	
	public final String TYPE_SPAN;
	public final String CREATE_BATCH_FILE_RESULT_PATH;
	public final String CREATE_BATCH_FILE_RESULT_BAK_PATH;
	
	public final String WHOLE_SELECT_REGEX;
	
	public static ArrayList<Pair> roomNumbuerArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> houseTypeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> areaSizeArrayList = new ArrayList<Pair>();
	public static ArrayList<Pair> priceRangeArrayList = new ArrayList<Pair>();
	
	public AnjukeGenericRent(String city, String subDomain){
		CITY = city; // = "天津";
		URL_SUBDOMAIN = subDomain; //= "tianjin";
		
		
		CITY_PINYING = URL_SUBDOMAIN.toUpperCase().charAt(0) + URL_SUBDOMAIN.substring(1);
		URL_SUBCATEGORY = "rental";
		TYPE = "安居客-租房";
		TYPE_PINYING = "Rent";
		SITE_NAME_PINYING = "Anjuke";
		WHOLE_URL = "http://" + URL_SUBDOMAIN + ".anjuke.com/" + URL_SUBCATEGORY + "/";
		ALL_SINGLE_SUB_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "AllSingleSub.txt";
		RENT_WHOLE_FILE_PATH = "./data/parser/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "Whole.txt";
		
		TYPE_SPAN = "#";
		CREATE_BATCH_FILE_RESULT_PATH = "./data/batch/" + SITE_NAME_PINYING + "/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFilePrepare.txt";
	    CREATE_BATCH_FILE_RESULT_BAK_PATH = "./data/batch/" + SITE_NAME_PINYING + "/bak/" + SITE_NAME_PINYING + CITY_PINYING + TYPE_PINYING + "BatchCreateJobFile";
		
		WHOLE_SELECT_REGEX = "div[id=apf_id_10_areacontainer]";
	}
	
	@Test
	public void doAllProcessInOneFunction() {
		//step.1  就是下载一个城市下面大的区的url
		rentWhole();
		
		//step.2 就是生成所有大城市下面大的区的小块的url组合
		rentSub();
		
		//step.3 生成那个batchJob的文件
		createBatchFileAnjukeGenericRent();
	}
	
	public void setCity(String city){
		CITY = city;
	}
	
	public void setUrlSubDomain(String urlSubDomain){
		URL_SUBDOMAIN = urlSubDomain;
	}
	
	/**
	 * 这个就是对于一个城市下的一个区域，它还要根据房型、面积、售价这些划分。每个页面上有这样的一个头。
	 * 分析这个，就是相当于把原来我们写死的那个静态代码块，让它可以灵活的根据特定城市的页面，自己进行加载。
	 * 这样就不需要人工的，根据不同的城市，进行手动修改。
	 * 这样便于后续的整个的自动化。
	 * */
	@Test
	public void parseCategory4OneDistrict(){
		String PARSE_CATEGORY_URL = WHOLE_URL;
		String ROOM_NUMBUER_SELECT_REGEX =  "div[id=panel_apf_id_10]";
		
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(PARSE_CATEGORY_URL, ROOM_NUMBUER_SELECT_REGEX, 0);
		Elements elements = elem.children();
		if (elements.size() != 5){
			try {
				throw new Exception("error in parseCategory4OneDistrict(), elements size: " + elements.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		//房型：一室 二室 三室 四室 五室 五室以上
		parseOneCategory(elements.get(3), roomNumbuerArrayList);
		//类型：公寓 普通住宅 别墅 其它
		parseOneCategory(elements.get(4), houseTypeArrayList);
		//面积：50平米以下 50-70平米 70-90平米 90-120平米 120-150平米 150-200平米 200-300平米 300平米以上
		parseOneCategory(elements.get(2), areaSizeArrayList);
		//售价：50万以下 50-80万 80-100万 100-120万 120-150万 150-200万 200-300万 300-500万 500万以上
		parseOneCategory(elements.get(1), priceRangeArrayList);
	}
	
	public void parseOneCategory(Element elem, ArrayList<Pair> list) {
		list.clear();
		Elements elements = ParserUtil.parseElementWithRegex(elem, "a");
		StringBuffer sb = new StringBuffer();
		for (Element element : elements){
			//获取url，和区域名称
			String href = element.attr("href");
			String word = element.text();
			//新建pair，并设置
			Pair pair = new Pair();
			pair.setKey(word);
			pair.setValue(href.substring(href.lastIndexOf("/")+1));
			//添加进list
			list.add(pair);
			//System.out.println(pair);
			//这个都是为了打印用了
			sb.append(href);
			sb.append(",");
			sb.append(word);
			sb.append("\n");
		}
		System.out.println(sb.toString());
		System.out.println(list);
	}
	
	/*
	 * 上海安居客，租房，上海总的各个地域，（浦东 闵行 徐汇 等等）
	 * */
	@Test
	public void rentWhole(){
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
		FileUtil.writeStr2File(sb.toString(), RENT_WHOLE_FILE_PATH, "utf-8");
	}
	
	/*
	 * 上海安居客，租房， 某个单个小区，比如 单个的浦东
	 * */

	public String rentSubSingle(String line){
		//prepare data
		String[] strArr = line.split(",");
		String url = strArr[0];
		String district = strArr[1];
			
		Element elem = ParserUtil.parseUrlWithRegexAndResultIndex(url, "div[id=apf_id_10_blockcontainer]", 0);
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
	 * 上海安居客，租房，下面的分区块的信息，subSingle的串起来
	 * 
	 * 这个方法是把所有的subSingle串起来的
	 * */
	@Test
	public void rentSub(){
		// 解析那个类别的头，预先设置一些静态的list（主要是为了把每个类别降低到100个分页以内）
		parseCategory4OneDistrict();
		
		String data = FileUtil.getDataFile2StrKeepReturn(RENT_WHOLE_FILE_PATH, "utf-8");
		String[] lineArr = StringUtils.split(data, "\n");
		StringBuffer sb = new StringBuffer();
		for (String line : lineArr){
			sb.append(rentSubSingle(line));
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
	public void createBatchFileAnjukeGenericRent(){
		//用以统计总共有多少个页面
		int totalCount = 0;
		String content = FileUtil.getDataFile2StrKeepReturn(ALL_SINGLE_SUB_FILE_PATH, "utf-8");
		String[] lineArr = content.split("\n");
		StringBuffer sb = new StringBuffer();
		for(String line : lineArr){
			if (!StringUtils.isBlank(line)){
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
		}
		System.out.println("##########" + totalCount);
		FileUtil.writeStr2File(sb.toString(), CREATE_BATCH_FILE_RESULT_PATH);
		FileUtil.writeStr2File(sb.toString(), CREATE_BATCH_FILE_RESULT_BAK_PATH + "_" + totalCount + ".txt");
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String city = "广州";
		String subDomain = "guangzhou";
		AnjukeGenericRent rent = new AnjukeGenericRent(city, subDomain);
		//rent.rentWhole();
		rent.doAllProcessInOneFunction();
//		new AnjukeGenericSecondhand().createBatchFileAnjukeGuangzhouSecondhand();
	}
}
