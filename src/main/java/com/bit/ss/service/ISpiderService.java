package com.bit.ss.service;

/**   
 * @Title: ISpiderService.java 
 * @Package com.bit.ss.service 
 * @Description:  爬虫接口
 * @author CCX
 * @date 2015年11月4日 下午4:30:40 
 * @version V1.0   
 */
public interface ISpiderService {

	/*
	 * 消息种类代码
	 * 0	新闻快讯
	 * 1	教务处通知
	 * 2	校方通知
	 * 3	招聘就业
	 * 4	本科招生
	 * 5	研究生招生
	 * 6	学工事务
	 * 7	讲座预告
	 * 8	教育教学
	 * 9	学术研究
	 * 10	网络通告
	 * 11	行政办公
	 * 12	人事公告
	 * 13	外事交流
	 * 14	生活琐事
	 * 15	班车信息
	 */
	public final static int CODE_INDEX_NEWS = 0;
	public final static int CODE_JWC_NOTICE = 1;
	public final static int CODE_CAMPUS_NOTICE = 2;
	public final static int CODE_JOB_NOTICE = 3;
	public final static int CODE_UNDERGRADUATE_ENROLL = 4;
	public final static int CODE_POSTGRADUATE_ENROLL = 5;
	public final static int CODE_STUDENT_STUFF = 6;
	public final static int CODE_LECTURE = 7;
	public final static int CODE_EDUCATION = 8;
	public final static int CODE_SCIENCE_STUDY = 9;
	public final static int CODE_NETWORK = 10;
	public final static int CODE_OFFICE_WORK = 11;
	public final static int CODE_HUMAN_RESOURCE = 12;
	public final static int CODE_INTERNATIONAL_COM = 13;
	public final static int CODE_DAILY_STUFF = 14;
	public final static int CODE_SCHOOL_BUS = 15;

	/**
	 * 
	 * @Title: crawl 
	 * @Description: 抓取信息
	 * @return void
	 * @throws 不可恢复异常
	 */
	public void crawl() throws Exception;
}
