package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.AdmissionContentExtractor;
import com.bit.ss.extractor.ContentExtractor;

/**   
 * @Title: UnderGraduateEnrollSpiderService.java 
 * @Package com.bit.ss.service 
 * @Description:  本科生招生信息
 * @author CCX
 * @date 2015年11月6日 下午5:20:20 
 * @version V1.0   
 */
@Service("UnderGraduateEnrollSpider")
public class UnderGraduateEnrollSpider extends SpiderSupporter {

	private static final String baseUrl = "http://admission.bit.edu.cn";

	public UnderGraduateEnrollSpider() {
		super(CODE_UNDERGRADUATE_ENROLL, "http://admission.bit.edu.cn/home/zhaosheng0.html", "ul.news_list a");
	}

	@Override
	public void saveEachNotice(Elements links){
		ContentExtractor extractor = new AdmissionContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = baseUrl + "/" + link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title))
					continue;

				// 如果不存在，对通知内容进行提取
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

	// for test
	// public static void main(String[] args) throws Exception {
	// new UnderGraduateEnrollSpiderService().crawl();
	// }
}
