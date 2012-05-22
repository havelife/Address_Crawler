package com.crawler.util;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserUtil {
	public static final int DivStartIndex = 1;
	public static final int DivEndIndex = 25;
	public static void main(String[] args) {
		Element elem = parseUrlWithRegexAndResultIndex("http://beijing.anjuke.com/community/", "div[class=box boxline]", 0);
		Elements elements = parseElementWithRegex(elem, "a");
		System.out.println(elements);
	}

	public static Elements parseUrlWithRegex(String url, String regex){
		try {

			Document doc = Jsoup.connect(url).timeout(10000).get();
			Elements elems = doc.select(regex);
			return elems;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Element parseUrlWithRegexAndResultIndex(String url, String regex, int index){
		Elements elems = parseUrlWithRegex(url, regex);
		if (elems == null){
			System.out.println("null result for parseUrlWithRegexAndResultIndex(), url:" + url +", regex:" + regex);
			return null;
		}
		if (elems.size() < index + 1){
			System.out.println("input index:" + index + " is beyond result size:" + elems.size());
			return null;
		}
		return elems.get(index);
	}
	
	public static Elements parseElementWithRegex(Element element, String regex){
		Elements elements = element.select(regex);
		return elements;
	}
	
	public static Element parseElementWithRegexAndResultIndex(Element element, String regex, int index){
		Elements elements = element.select(regex);
		if (elements.size() < index){
			System.out.println("input index:" + index + " is beyond result size:" + elements.size());
			return null;
		}
		return elements.get(index);
	}
	
	/********************TEST**************************/

	public void parseFile() {
		try {
			File input = new File("./data/add_beijing_daxin.txt"); //input.html
			Document doc = Jsoup.parse(input, "UTF-8");
			// 提取出所有的编号
			Elements codes = doc.body().select("td[title^=IA] > a[href^=javascript:view]");
			System.out.println(codes);
			System.out.println("------------------");
			System.out.println(codes.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void parseString() {
		String html = "<html><head><title>blog</title></head><body onload='test()'><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		System.out.println(doc);
		Elements es = doc.body().getAllElements();
		System.out.println(es.attr("onload"));
		System.out.println(es.select("p"));
	}
}
