package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;
import com.bit.ss.extractor.ContentExtractor;
import com.bit.ss.extractor.SchoolNoticeContentExtractor;
import com.bit.ss.extractor.XCBNoticeContentExtractor;

/**   
 * @Title: DailyStuffSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  生活琐事e
 * @author CCX
 * @date 2015年11月8日 下午4:22:26 
 * @version V1.0   
 */
@Service("DailyStuffSpider")
public class DailyStuffSpider extends SpiderSupporter{
	
	public DailyStuffSpider() {
		super(CODE_DAILY_STUFF, "http://www.bit.edu.cn/tzgg17/qttz/index.htm", "div.title_rtcon a");
	}
	
	@Override
	public void saveEachNotice(Elements links){
		ContentExtractor extractor1 = new SchoolNoticeContentExtractor(null, httpclient);
		ContentExtractor extractor2 = new XCBNoticeContentExtractor(null, httpclient);
		
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title))
					continue;
				
				//处理地址
				if(!href.contains("http"))
					href = handleRelativeUrl(URL, href);

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				
				// 国际交流合作处的通知，eg.
				// href=http://xcb.bit.edu.cn/bgzn/tzgg/114161.htm
				if(href.contains("xcb")){
					extractor2.setUrl(href);
					content.append(extractor2.extract());
				}
				// 校园网本身的通知,eg. http://www.bit.edu.cn/tzgg17/qttz/115626.htm
				else if(href.contains("tzgg17")){
					extractor1.setUrl(href);
					content.append(extractor1.extract());
				}// 待扩展网站
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
	
	//for test
	// public static void main(String[] args) throws Exception {
	// new DailyStuffSpider().crawl();
	// }
}
