package com.bit.ss.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bit.ss.domain.News;

/**   
 * @Title: Test.java 
 * @Package com.bit.ss.dao 
 * @Description:  
 * @author CCX
 * @date 2015年11月29日 下午3:00:55 
 * @version V1.0   
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDAO {

	@Autowired
	private INewsDAO newsDAO;

	@Test
	public void testInsertNews() {
		News news = new News();
		news.setContent("This is for test");
		news.setPubTime(new Date());
		news.setTitle("测试三");
		news.setType(-1);
		news.setUrl("http://localhost:8080/....");
		newsDAO.saveNews(news);
		System.out.println("news.id: " + news.getId());
	}

	@Test
	public void testSelectNewsTitle() {
		System.out.println("title:测试一     isExit:" + newsDAO.isExit("测试一", -1));
		System.out.println("title:测试二     isExit:" + newsDAO.isExit("测试二", -1));
	}
}
