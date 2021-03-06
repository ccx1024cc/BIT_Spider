package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;

/**   
 * @Title: ScienceStudyNoticeSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  学术研究相关通知
 * @author CCX
 * @date 2015年11月8日 下午2:02:33 
 * @version V1.0   
 */
@Service("ScienceStudyNoticeSpider")
public class ScienceStudyNoticeSpider extends SpiderSupporter {

	public ScienceStudyNoticeSpider() {
		super(CODE_SCIENCE_STUDY, "http://www.bit.edu.cn/tzgg17/kyxs/index.htm", "div.title_rtcon a");
	}

	@Override
	public void saveEachNotice(Elements links) {
//		ContentExtractor extractor = new SchoolNoticeContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title, this.INFO_TYPE))
					continue;

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				href = handleRelativeUrl(URL, href);
				// extractor.setUrl(href);
				// content.append(extractor.extract());

				News news = new News(title, new Date(), INFO_TYPE, href, content.toString());
				newsDAO.saveNews(news);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// for test
	// public static void main(String[] args) throws Exception {
	// new ScienceStudyNoticeSpider().crawl();
	// }
}
