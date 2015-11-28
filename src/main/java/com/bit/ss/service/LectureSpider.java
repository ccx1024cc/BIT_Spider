package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.ContentExtractor;
import com.bit.ss.extractor.SchoolNoticeContentExtractor;

/**   
 * @Title: LectureSpiderService.java 
 * @Package com.bit.ss.service 
 * @Description:  讲座预告消息爬虫
 * @author CCX
 * @date 2015年11月7日 下午2:54:53 
 * @version V1.0   
 */
@Service("LectureSpider")
public class LectureSpider extends SpiderSupporter{

	public LectureSpider() {
		super(CODE_LECTURE, "http://www.bit.edu.cn/tzgg17/jzyg2/index.htm", "div.title_rtcon a");
	}

	@Override
	public void saveEachNotice(Elements links){
		ContentExtractor extractor = new SchoolNoticeContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title))
					continue;

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				href = handleRelativeUrl(URL, href);
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
