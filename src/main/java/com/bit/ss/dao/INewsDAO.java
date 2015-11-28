package com.bit.ss.dao;

import com.bit.ss.domain.News;

/**   
 * @Title: INewsDAO.java 
 * @Package com.bit.ss.dao 
 * @Description:  新闻
 * @author CCX
 * @date 2015年11月28日 上午11:42:16 
 * @version V1.0   
 */
public interface INewsDAO {

	/**
	 * 
	 * @Title: saveNews 
	 * @Description: 保存新闻
	 * @return int    返回类型 
	 * @throws
	 */
	public int saveNews(News news);
	
	/**
	 * 
	 * @Title: isExit 
	 * @Description: 根据标题判断是否存在该条新闻
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isExit(String title);
}
