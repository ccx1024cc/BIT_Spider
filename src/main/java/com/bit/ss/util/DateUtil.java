package com.bit.ss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**   
 * @Title: DateUtil.java 
 * @Package com.bit.ss.util 
 * @Description:  
 * @author CCX
 * @date 2015年11月1日 上午11:04:25 
 * @version V1.0   
 */
public class DateUtil {

	public static final String MONTH_FORMAT = "yyyy-MM";
	public static final String DATE_FORMAT="yyyy-MM-dd";
	public static final String HOUR_FORMAT="HH:mm:ss";
	public static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String FILE_NAME_FORMAT = "yyyyMMddHHmmss";
	
	/**
	 * 
	 * @Title: formatDateTime 
	 * @Description: 格式化日期
	 * @return String    返回类型 
	 * @throws
	 */
	public String formatDateTime(Date date,String formatStr){
		if(formatStr == null)
			formatStr = DATE_TIME_FORMAT;
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		return df.format(date);
	}
	
	/**
	 * @throws ParseException 
	 * 
	 * @Title: parse 
	 * @Description: 解析日期
	 * @return Date    返回类型 
	 * @throws
	 */
	public Date parse(String date,String formatStr) throws ParseException{
		if(formatStr == null)
			formatStr = DATE_TIME_FORMAT;
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		return df.parse(date);
	}
	
	/**
	 * 
	 * @Title: nDaysAfterOneDate 
	 * @Description: 返回某一天之后n天的日期
	 * @return Date    返回类型 
	 * @throws
	 */
	public Date nDaysAfterOneDate(Date date,int n){
		long time = date.getTime() + ( 24 * 3600 * 1000l ) * n;
		Date dateTarget = (Date)date.clone();
		dateTarget.setTime(time);
		return dateTarget;
	}
	
	/**
	 * 
	 * @Title: nDaysBetweenTwoDays 
	 * @Description: 计算两个日期之间的时间（以天为单位）
	 * @return int    返回类型 
	 * @throws
	 */
	public int nDaysBetweenTwoDays(Date firstDate,Date secondDate){
		int days = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 3600 * 1000));
		return days;
	}
}
