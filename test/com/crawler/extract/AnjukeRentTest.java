package com.crawler.extract;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnjukeRentTest {
	private static ClassPathXmlApplicationContext ctx;
	static AnjukeRent anjukeRent;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		anjukeRent = (AnjukeRent)ctx.getBean("anjukeRent");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("test completed!");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeBeijingTest(){
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-租房");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeShanghaiTest(){
		anjukeRent.extractAndSaveByCityAndType("上海", "安居客-租房");
	}
	
	/*
	 * 除去北京、上海, 解析剩下的租房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveAllOtherCityRentTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-租房", "北京", "上海");
	}
	
	/*
	 * 解析所有的二手房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveAllOtherCitySecondhandTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-二手房");
	}
	
	
	/*
	 * 解析北京 崇文的二手房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveBeijingChongweimenSecondhandTest(){
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#崇文#"); 
	}
	
	/*
	 * 解析北京 剩下的二手房信息，以及除去北京的其他城市，并且存入address表
	 * */
	@Test
	public void extractAndSaveBeijingTheRestSecondhandTest(){
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#宣武#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#石景山#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#昌平#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#通州#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#大兴#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#顺义#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#房山#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#门头沟#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#延庆#");
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#周边#");
		
		System.err.println("Beijing Finished!**********************************************************");
		anjukeRent.extractAndSaveAllOtherCity("安居客-二手房", "北京");
	}
	
	
	/*
	 * 解析南京 浦口的二手房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveNanjingPukouSecondhandTest(){
		anjukeRent.extractAndSaveByCityAndType("南京", "安居客-二手房#浦口#"); 
	}
	
	/*
	 * 解析南京 崇文的二手房信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveNanjingTheRestSecondhandTest(){
		anjukeRent.extractAndSaveByCityAndType("南京", "安居客-二手房#栖霞#");
		anjukeRent.extractAndSaveByCityAndType("南京", "安居客-二手房#六合#");

		System.err.println("Nanjing Finished!**********************************************************");
		
	}
	
	/*
	 * 解析除去北京...南京等城市(共18个城市)，剩下的城市的二手房信息，并且存入address表
	 * 
	 * 剩下的城市:
	 * [无锡, 济南, 青岛, 宁波, 南昌, 福州, 合肥, 常州, 烟台, 扬州, 徐州, 南通, 泰州, 绍兴, 佛山, 长沙, 东莞, 海口, 
	 *  珠海, 中山, 厦门, 南宁, 三亚, 惠州, 泉州, 桂林, 成都, 重庆, 武汉, 郑州, 西安, 昆明, 贵阳, 兰州, 洛阳, 银川]
	 * */
	@Test
	public void extractAndSaveExceptBeijingToNanjingSecondhandTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-二手房", "北京", "上海", "广州", "深圳", "天津", "大连", "石家庄", "哈尔滨", "沈阳", "太原", "长春", "潍坊", "呼和浩特", "威海", "保定", "杭州", "苏州", "南京");
		System.err.println("the rest Finished!**********************************************************");
	}
	/*
	 * 解析所有的二手房信息，并且存入address表
	 * 
	 * （后面跑失败了，就是没有城市都跑完，中间电脑总是停下来了）
	 * */
	@Test
	public void extractAndSaveAllOtherCitySecondhandExceptBeijingTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-二手房", "北京");
	}
	
	@Test
	public void extractAndSaveByCityAndTypeTest4DebugBeijingYayuncun(){
//		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#朝阳#亚运村#90-110平米#二室#普通住宅");
//		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-二手房#朝阳#亚运村#110-130平米#二室#普通住宅");
	}
	
	
	/*------------------------------------------------------------------*/
	
	
	/*
	 * 解析所有的小区信息，并且存入address表
	 * 实际上并未执行过这个方法。因为北京、上海两个城市和其他的城市是分开执行的
	 * */
	@Test
	public void extractAndSaveAllOtherCityCommunityTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-小区");
	}
	
	/*
	 * 解析上海的小区信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveShanghaiCommunityTest(){
		anjukeRent.extractAndSaveByCityAndType("上海", "安居客-小区");
	}
	
	/*
	 * 解析北京的小区信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveBeijingCommunityTest(){
		anjukeRent.extractAndSaveByCityAndType("北京", "安居客-小区");
	}
	
	/*
	 * 解析除去北京、上海其他所有城市的小区信息，并且存入address表
	 * */
	@Test
	public void extractAndSaveExceptBeijingShanghaiCommunityTest(){
		anjukeRent.extractAndSaveAllOtherCity("安居客-小区", "北京", "上海");
	}
}
