package com.crawler.extract;

import java.util.List;

import org.jsoup.select.Elements;

import com.crawler.dao.JobDao;
import com.crawler.pojo.Job;
import com.crawler.util.FileUtil;
import com.crawler.util.ParserUtil;


public class AnjukeRent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String html = FileUtil.getDataFile2Str("./data/extract/Anjuke/AnjukeBeijingRent.txt", "utf-8");
		Elements elems = ParserUtil.parseHtmlStrWithRegex(html, "[id=prop_name_qt_prop_25]");
		if (elems.size() == 0) System.out.println("emles is null");
		System.out.println(elems.html());
		
		
		JobDao jobDao = new JobDao();
		List<Job> list = jobDao.getJobsByCity("绵阳");
		System.out.println(list);
	}
}
