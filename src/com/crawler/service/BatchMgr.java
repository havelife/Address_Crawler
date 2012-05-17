package com.crawler.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.util.CrawlerConstants;
import com.crawler.util.FileUtil;

@Component("batchMgr")
public class BatchMgr {
	
	@Autowired
	private JobMgr jobMgr;
	
	/*
	 * 批量的从一个文件去读取，创建job，并下载
	 * ^表示起点，$表示终点
	 * */
	public void batchCreateJob(String filePath){
		String fileContent = FileUtil.getDataFile2StrKeepReturn(filePath, "utf-8");
		if (StringUtils.isBlank(fileContent)) {
			System.out.println("batchCreateJob file:" + filePath + " is blank.");
			return;
		}
		int startIdx = fileContent.indexOf(CrawlerConstants.BATCH_CREATE_JOB_START_REGEX);
		int endIdx = fileContent.indexOf(CrawlerConstants.BATCH_CREATE_JOB_END_REGEX);
		if (startIdx == -1 || endIdx == -1){
			System.out.println("start regex or end regex does not exist in batchCreateJob source file.");
			return;
		}
		//这里有额外的调整 起始点，1是因为substring函数本身特点，2是因为换行符遗留
		fileContent = fileContent.substring(startIdx + 2, endIdx - 1);
		String[] contentArr = fileContent.split(CrawlerConstants.BATCH_FILE_LINE_RETURN);
		if (contentArr == null || contentArr.length == 0) {
			System.err.println("job need to be created is empty");
			return;
		}
		for(String line : contentArr){
			String[] attrArr = line.split(CrawlerConstants.BATCH_ATTRIBUTE_SPLIT_REGEX);
			if (attrArr == null || attrArr.length != CrawlerConstants.BATCH_ATTRIBUTE_SIZE) {
				System.err.println("attribute size wrong in line:" + line);
				return;
			}
			//fetch attributes from file line
			String prefix = attrArr[0].trim();
			String suffix = attrArr[1].trim();
			int step = Integer.parseInt(attrArr[2].trim());
			int startidx = Integer.parseInt(attrArr[3].trim());
			int endidx = Integer.parseInt(attrArr[4].trim());
			String category = attrArr[5].trim();
			String type = attrArr[6].trim();
			String domain = attrArr[7].trim();
			//create job and start to do the whole process
			jobMgr.createJobAndDownloadWholeProcess(prefix, suffix, step, startidx, endidx, category, type, domain);
		}
	}
}
