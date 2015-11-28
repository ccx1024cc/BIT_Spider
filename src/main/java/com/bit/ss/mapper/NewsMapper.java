package com.bit.ss.mapper;

import com.bit.ss.domain.News;

/**   
 * @Title: NewsMapper.java 
 * @Package com.bit.ss.mapper 
 * @Description:  新闻映射器
 * @author CCX
 * @date 2015年11月28日 下午12:11:13 
 * @version V1.0   
 */
public interface NewsMapper {

	/**
	 * 
	 * @Title: save 
	 * @Description: 保存单条新闻
	 * @return int    返回类型 
	 * @throws
	 */
	public int save(News news);
	
	/**
	 * 
	 * @Title: findTitle 
	 * @Description: 判断该标题是否存在，如果不存在则返回空
	 * @return String    返回类型 
	 * @throws
	 */
	public String findTitle(String title);
}
