package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.ContentExtractor;
import com.bit.ss.extractor.XWWNewsContentExtractor;

/**
 * @Title: NewsSpider.java
 * @Package com.bit.ss.service
 * @Description:抓取校园网主页新闻// http://www.bit.edu.cn/
 * @author CCX
 * @date 2015年11月2日 上午10:13:56
 * @version V1.0
 */

@Service("IndexNewsSpider")
public class IndexNewsSpider extends SpiderSupporter {

	public IndexNewsSpider() {
		super(CODE_INDEX_NEWS, "http://www.bit.edu.cn", "div#main1 a");
	}

	@Override
	public void saveEachNotice(Elements links) {
		ContentExtractor extractor = new XWWNewsContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				// Element ele = newsLinks.first();
				String title = link.text();
				String href = link.attr("href");
				if (!href.contains("http"))
					href = URL + "/" + href;

				// 如果存在，则跳过
				if (newsDAO.isExit(title, this.INFO_TYPE))
					continue;

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				// 根据通知来源的不同采用不同的提取方法
				// 校园网子网（新闻网),eg
				// http://www.bit.edu.cn/xww/zhxw/118490.htm
				if (href.contains("xww")) {
					extractor.setUrl(href);
					content.append(extractor.extract());
				}
				// 待扩展网站
				else {
					System.out.println("unknown website:" + href);
				}

				News news = new News(title, new Date(), INFO_TYPE, href, content.toString());
				newsDAO.saveNews(news);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// for test
	// public static void main(String[] args) throws Exception {
	// new IndexNewsSpiderService().crawl();
	// }

}
