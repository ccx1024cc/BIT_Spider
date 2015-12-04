package com.bit.ss.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.mapper.NewsMapper;

/**   
 * @Title: NewsDAOImpl.java 
 * @Package com.bit.ss.dao 
 * @Description:  新闻
 * @author CCX
 * @date 2015年11月28日 上午11:41:43 
 * @version V1.0   
 */
@Service
public class NewsDAOImpl implements INewsDAO {

	@Resource
	private NewsMapper newsMapper;

	@Override
	public int saveNews(News news) {
		return newsMapper.save(news);
	}

	@Override
	public boolean isExit(String title, int newsType) {
		boolean isExixt = false;
		if (newsMapper.findTitle(title, newsType) != null)
			isExixt = true;
		return isExixt;
	}
}
