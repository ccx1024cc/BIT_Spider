package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.ContentExtractor;
import com.bit.ss.extractor.GRDNoticeContentExtractor;

/**   
 * @Title: PostGraduateEnrollSpiderService.java 
 * @Package com.bit.ss.service 
 * @Description:  研究生招生信息
 * @author CCX
 * @date 2015年11月6日 下午5:39:01 
 * @version V1.0   
 */
@Service("PostGraduateEnrollSpider")
public class PostGraduateEnrollSpider extends SpiderSupporter {

	private static final String baseUrl = "http://grd.bit.edu.cn/zsgz";

	public PostGraduateEnrollSpider() {
		super(CODE_POSTGRADUATE_ENROLL, "http://grd.bit.edu.cn/zsgz/index.htm", "div.list05 li>a");
	}

	@Override
	public void saveEachNotice(Elements links){
		ContentExtractor extractor = new GRDNoticeContentExtractor(null, httpclient);
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

	// // for test
	// public static void main(String[] args) throws Exception {
	// new PostGraduateEnrollSpiderService().crawl();
	// }
}
