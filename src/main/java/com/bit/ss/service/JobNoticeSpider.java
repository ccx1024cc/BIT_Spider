package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.ContentExtractor;
import com.bit.ss.extractor.IndexExtractor;
import com.bit.ss.extractor.JobContentExtractor;

/**   
 * @Title: JobNoticeSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  就业信息爬虫
 * @author CCX
 * @date 2015年11月6日 下午4:14:11 
 * @version V1.0   
 */
@Service("JobNoticeSpider")
public class JobNoticeSpider extends SpiderSupporter{

	// 基地址
	private static final String baseUrl = "http://job.bit.edu.cn/";

	public JobNoticeSpider() {
		super(CODE_JOB_NOTICE, "http://job.bit.edu.cn/main/news/jobs.html", "ul.news_list a");
	}

	/**
	 * 由于需要抓取前两页的所有链接做索引，所以需要重写默认的爬虫方法
	 */
	@Override
	public void crawl() throws Exception {
		Elements links = new Elements();
		Elements temp1 = new Elements(); // 首页链接
		Elements temp2 = new Elements(); // 第二页链接
		// 由于招聘信息可能会在短时间内激增，所以抓取前两页的所有招聘链接
		try {
			// 首页链接
			temp1 = (Elements) new IndexExtractor("http://job.bit.edu.cn/main/news/jobs.html", httpclient,
					"ul.news_list a").extract();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 第二页链接
			String url2 = "http://job.bit.edu.cn/main/news/jobs.html?"
					+ "tx_news_pi1%5B%40widget_0%5D%5BcurrentPage%5D=2" + "&cHash=c9444f950c3f3c2e5136347467d7206f";
			temp2 = (Elements) new IndexExtractor(url2, httpclient, "ul.news_list a").extract();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (temp1.isEmpty() && temp2.isEmpty())
			throw new Exception(URL + " connected false");

		links.addAll(temp1);
		links.addAll(temp2);
		saveEachNotice(links);
		shutdown();
	}

	@Override
	public void saveEachNotice(Elements links){
		ContentExtractor extractor = new JobContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = baseUrl + link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title))
					continue;

				StringBuilder content = new StringBuilder();
				extractor.setUrl(href);
				content.append(extractor.extract());

				News news = new News(title, new Date(), INFO_TYPE, href, content.toString());
				newsDAO.saveNews(news);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
