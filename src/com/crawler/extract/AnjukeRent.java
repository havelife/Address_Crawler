package com.crawler.extract;

import java.util.List;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.crawler.dao.JobDao;
import com.crawler.pojo.Job;
import com.crawler.pojo.Url;
import com.crawler.service.JobMgr;
import com.crawler.service.PageMgr;
import com.crawler.service.UrlMgr;
import com.crawler.util.FileUtil;
import com.crawler.util.ListUtil;
import com.crawler.util.ParserUtil;


public class AnjukeRent {
	@Autowired
	private JobMgr jobMgr;
	@Autowired
	private UrlMgr urlMgr;
	@Autowired
	private PageMgr pageMgr;
	
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
	
	public void getAllUrlsByCityAndType(String city, String type){
		List<Job> jobList = jobMgr.getJobsByCityAndType(city, type);
		if (ListUtil.isBlank(jobList)) {
			System.err.println("the result is null for city: " + city + ", type: " + type);
			return;
		}
		for(Job job : jobList){
			List<Url> urlList = urlMgr.getUrlObjs4OneJob(job.getId());
		}
	}
}
