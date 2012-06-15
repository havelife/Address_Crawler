package com.crawler.extract;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.JobDao;
import com.crawler.pojo.Address;
import com.crawler.pojo.Job;
import com.crawler.pojo.Url;
import com.crawler.service.AddressMgr;
import com.crawler.service.JobMgr;
import com.crawler.service.PageMgr;
import com.crawler.service.UrlMgr;
import com.crawler.util.FileUtil;
import com.crawler.util.ListUtil;
import com.crawler.util.ParserUtil;
import com.crawler.util.PropertyUtil;

@Component("anjukeRent")
public class AnjukeRent {
	@Autowired
	private JobMgr jobMgr;
	@Autowired
	private UrlMgr urlMgr;
	@Autowired
	private PageMgr pageMgr;
	@Autowired
	private AddressMgr addressMgr;
	//constants
	public static final int MAX_ITEM_NUMBERS_IN_ONE_PAGE = 25;
	public static final int MAX_ITEM_NUMBERS_IN_ONE_PAGE_ANJUKE_SHANGHAI_COMMUNITY = 10;
	public static final String CONNECTOR_BETWEEN_WEBSITE_AND_TYPE = "-";
	//variables
	public static String PRINT_ANJUKE_RENT_EXTRACT_PROCESS = PropertyUtil.getValue("application.properties", "print.anjuke.rent.extract.process");
	public static String EXTRACT_PROCESS_GET_ADDRESS_SAVE_TO_DB = PropertyUtil.getValue("application.properties", "extract.process.get.address.save.to.db");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String html = FileUtil.getDataFile2Str("./data/extract/Anjuke/AnjukeShanghaiCommunity.txt", "utf-8");
		new AnjukeRent().getAddressAndSave4CommunityShanghai(html, "","","","","");
		//extractAndSaveAllOtherCity("安居客-二手房", "北京"); //getAddressAndSave(html, "", "", "", "", "");
	}
	
	/*
	 * 参数type: 安居客-租房
	 * 函数内的houseType: 租房
	 * */
	public void extractAndSaveByCityAndType(String city, String type){
		List<Job> jobList = jobMgr.getJobsByCityAndType(city, type);
		if (ListUtil.isBlank(jobList)) {
			System.err.println("in table:job, the result is null for city: " + city + ", type: " + type);
			return;
		}
		
		for(Job job : jobList){
			//做一些设置address的准备工作
			//"安居客-租房#朝阳#朝阳公园#二室#普通住宅";
			String typeStrInJob = job.getType();
			System.out.println(typeStrInJob);
			String website = typeStrInJob.substring(0, typeStrInJob.indexOf(CONNECTOR_BETWEEN_WEBSITE_AND_TYPE));
			String houseType = typeStrInJob.substring(typeStrInJob.indexOf(CONNECTOR_BETWEEN_WEBSITE_AND_TYPE) + 1, typeStrInJob.indexOf("#"));
			String[] strArray = typeStrInJob.split("#");
			if (strArray.length < 3){
				System.err.println("error occour in splitting:" + typeStrInJob);
				return;
			}
			String district = strArray[1];
			String community = strArray[2];

			//判断是否要打印中间的解析过程
			if ("open".equals(PRINT_ANJUKE_RENT_EXTRACT_PROCESS)) {
				System.out.println("website:" + website);
				System.out.println("houseType:" + houseType);
				System.out.println("district:" + district);
				System.out.println("commnity:" + community);
			}
			
			List<Url> urlList = urlMgr.getUrlObjs4OneJob(job.getId());
			if (ListUtil.isBlank(urlList)) {
				System.err.println("in table:url, the result is null for jobid: " + job.getId());
				return;
			}
			for (Url url : urlList) {
				String html = pageMgr.getPageContentByPageUrl(url.getUrl());
				getAddressAndSave4RentOrSecondhand(html, community, district, city, website, houseType);
			}
		}
	}
	
	public void getAddressAndSave4RentOrSecondhand(String html, String community, String district, String city, String website, String houseType){
		if (StringUtils.isBlank(html)){
			System.err.println("input html is blank in getAddressAndSave4RentOrSecondhand()");
			return;
		}
				
		for (int i=0; i < MAX_ITEM_NUMBERS_IN_ONE_PAGE; i++) {
			Element element = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_" + (i + 1) + "]", 0);
			//这页并没有25个数据项，所以没有了，就停止向下循环
			if (element == null) {
				break;
			}
			Element addrElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "address", 0);
			String[] addrArray = addrElem.text().split("\\s+  ");
			Element conditionElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_detail_qt_prop_" + (i + 1) + "]", 0);
			String houseCondition = conditionElem.text();
			Element commentElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_name_qt_prop_" + (i + 1) + "]", 0);
			String comment = commentElem.text();
			Element priceElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_price_qt_prop_" + (i + 1) + "]", 0);
			String price = priceElem.text();

			Address address = new Address();
			address.setName(addrArray[0]);
			address.setAddr(addrArray[1]);
			address.setCommunity(community);
			address.setDistrict(district);
			address.setCity(city);
			address.setHouseCondition(houseCondition);
			address.setComment(comment);
			address.setPrice(price);
			address.setWebsite(website);
			address.setType(houseType);
			
			//判断是否要打印中间的解析过程
			if ("open".equals(PRINT_ANJUKE_RENT_EXTRACT_PROCESS)) {
				System.out.println(addrArray[0] + "#" + addrArray[1]);
				System.out.println(conditionElem.text());
				System.out.println(comment);
				System.out.println(price);
				System.out.println(address);
			}
			//存入数据库
			if ("open".equals(EXTRACT_PROCESS_GET_ADDRESS_SAVE_TO_DB)) {
				addressMgr.save(address);
			}
		}
	}
	
	public void getAddressAndSave4Community(String html, String community, String district, String city, String website, String houseType){
		if (StringUtils.isBlank(html)){
			System.err.println("input html is blank in getAddressAndSave4Community()");
			return;
		}
				
		for (int i=0; i < MAX_ITEM_NUMBERS_IN_ONE_PAGE; i++) {
			int index = i * 2 + 13;
			Element element = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=apf_id_" + (index) + "]", 0);
			//这页并没有25个数据项，所以没有了，就停止向下循环
			if (element == null) {
				break;
			}
			
			Element titleElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=comm_name_qt_apf_id_" + (index) + "]", 0);
			
			Element addrElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "address", 0);
			String[] addrArray = addrElem.text().split("\\s+  ");
			Element conditionElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=comm_sale_qt_apf_id_" + (index) + "]", 0);
			String houseCondition = conditionElem.text();
//			Element commentElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_name_qt_prop_" + (i + 1) + "]", 0);
//			String comment = commentElem.text();
//			Element priceElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_price_qt_prop_" + (i + 1) + "]", 0);
//			String price = priceElem.text();

			Address address = new Address();
			address.setName(addrArray[0]);
//			address.setAddr(addrArray[1]);
			address.setCommunity(community);
			address.setDistrict(district);
			address.setCity(city);
//			address.setHouseCondition(houseCondition);
//			address.setComment(comment);
//			address.setPrice(price);
			address.setWebsite(website);
			address.setType(houseType);
			
			//判断是否要打印中间的解析过程
			if ("open".equals(PRINT_ANJUKE_RENT_EXTRACT_PROCESS)) {
				System.out.println(addrElem.text());
				System.out.println(titleElem.text());
//				System.out.println(addrArray[0] + "#" + addrArray[1]);
				System.out.println(conditionElem.text());
//				System.out.println(comment);
//				System.out.println(price);
//				System.out.println(address);
			}
			//存入数据库
			if ("open".equals(EXTRACT_PROCESS_GET_ADDRESS_SAVE_TO_DB)) {
				addressMgr.save(address);
			}
		}
	}
	
	public void getAddressAndSave4CommunityShanghai(String html, String community, String district, String city, String website, String houseType){
		if (StringUtils.isBlank(html)){
			System.err.println("input html is blank in getAddressAndSave4CommunityShanghai()");
			return;
		}
				
		for (int i=0; i < MAX_ITEM_NUMBERS_IN_ONE_PAGE_ANJUKE_SHANGHAI_COMMUNITY; i++) {
			int index = i; //i * 2 + 13;
			Element element = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "div[id=apf_html_id_" + (index) + "]", 0);
			//这页并没有25个数据项，所以没有了，就停止向下循环
			if (element == null) {
				break;
			}
			
			Element titleElem = ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=comm_name_qt_apf_html_id_" + (index) + "]", 0);
			Element addrElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "address", 0);
			String addrStr = addrElem.text().replace("地图", "").trim();
			Element conditionElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "p[class=date]", 0);//ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=comm_sale_qt_apf_id_" + (index) + "]", 0);
			String houseCondition = conditionElem.text();
			Element commentElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "p[class=house]", 0);//ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_name_qt_prop_" + (i + 1) + "]", 0);
			String comment = commentElem.text();
			Element priceElem = ParserUtil.parseElementWithRegexAndResultIndex(element, "div[class=price]", 0);//ParserUtil.parseHtmlStrWithRegexAndResultIndex(html, "[id=prop_price_qt_prop_" + (i + 1) + "]", 0);
			String price = priceElem.text();

			Address address = new Address();
			address.setName(titleElem.text());
			address.setAddr(addrStr);
			address.setCommunity(community);
			address.setDistrict(district);
			address.setCity(city);
			address.setHouseCondition(houseCondition);
			address.setComment(comment);
			address.setPrice(price);
			address.setWebsite(website);
			address.setType(houseType);
			
			//判断是否要打印中间的解析过程
			if ("open".equals(PRINT_ANJUKE_RENT_EXTRACT_PROCESS)) {
				System.out.println("title:" + titleElem.text());
				System.out.println("addr:" + addrStr);
				System.out.println("condition:" + houseCondition);
				System.out.println("comment:" + comment);
				System.out.println("price:" + price);
				System.out.println(address);
				System.out.println();
			}
			//存入数据库
			if ("open".equals(EXTRACT_PROCESS_GET_ADDRESS_SAVE_TO_DB)) {
				addressMgr.save(address);
			}
		}
	}
	
	/*
	 * 排除一些城市（北京、上海）
	 * 去下载解析，租房。
	 * */
	public void extractAndSaveAllOtherCity(String type, String... exceptCities){
		List<String> cityList = jobMgr.getAllCities4OneJobLimitedByType(type);
		for (String city : exceptCities) {
			cityList.remove(city);
		}
		
		if (ListUtil.isBlank(cityList)){
			System.err.println("cityList is blank in extractAndSaveAllOtherCity()");
			return;
		}
		System.out.println(cityList);
		for (String city : cityList){
			System.out.println("*****************start to work on:" + city + "#" + type);
			extractAndSaveByCityAndType(city, type);
		}
	}
}
