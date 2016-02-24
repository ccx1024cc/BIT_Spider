package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;

/**   
 * @Title: StudentStuff.java 
 * @Package com.bit.ss.service 
 * @Description:  学工事务通知
 * @author CCX
 * @date 2015年11月7日 下午2:33:34 
 * @version V1.0   
 */
@Service("StudentStuffSpider")
public class StudentStuffSpider extends SpiderSupporter {

	public StudentStuffSpider() {
		super(CODE_STUDENT_STUFF, "http://www.bit.edu.cn/tzgg17/wthd132/index.htm", "div.title_rtcon a");
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
	// public static void main(String[] args) throws Exception{
	// new StudentStuffSpiderService().crawl();
	// }
}
