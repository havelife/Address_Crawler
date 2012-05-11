package com.crawler.util;

import java.util.List;


public class ListUtil {
	public static boolean isBlank(List list){
		return (list == null || list.size() == 0);
	}
}
