package com.bit.ss.service;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;

/**   
 * @Title: OfficeWorkSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  行政办公信息
 * @author CCX
 * @date 2015年11月8日 下午3:47:44 
 * @version V1.0   
 */
@Service("OfficeWorkSpider")
public class OfficeWorkSpider extends SpiderSupporter {

	public OfficeWorkSpider() {
		super(CODE_OFFICE_WORK, "http://www.bit.edu.cn/tzgg17/ggxx/index.htm", "div.title_rtcon a");
	}

	@Override
	public void saveEachNotice(Elements links) {
//		ContentExtractor extractor1 = new SchoolNoticeContentExtractor(null, httpclient);
//		ContentExtractor extractor2 = new GJJLHZCNoticeContentExtractor(null, httpclient);
//		ContentExtractor extractor3 = new GRDNoticeContentExtractor(null, httpclient);
//		ContentExtractor extractor4 = new XCBNoticeContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title, this.INFO_TYPE))
					continue;

				// 处理地址
				if (!href.contains("http"))
					href = handleRelativeUrl(URL, href);

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				// // 根据通知来源的不同采用不同的提取方法
				// // 校园网本身的通知,eg. href=../jzyg2/118607.htm
				// if (href.contains("tzgg17")) {
				// extractor1.setUrl(href);
				// content.append(extractor1.extract());
				// }
				// // 国际交流合作处的通知，eg.
				// //
				// href=http://www.bit.edu.cn/gbxxgk/gbgljg/gjjlhzc/xxgg/118602.htm
				// else if (href.contains("gbxxgk")) {
				// extractor2.setUrl(href);
				// content.append(extractor2.extract());
				// }
				// // 研究生院通知,eg.
				// // http://grd.bit.edu.cn/gjjl/zxdtxx/66819.htm
				// else if (href.contains("grd.bit.edu.cn")) {
				// extractor3.setUrl(href);
				// content.append(extractor3.extract());
				// }
				// // 党委宣传部通知,eg.
				// // http://xcb.bit.edu.cn/bgzn/tzgg/117332.htm
				// else if (href.contains("xcb.bit.edu.cn")) {
				// extractor4.setUrl(href);
				// content.append(extractor4.extract());
				// }
				// // 待扩展网站
				// else {
				// System.out.println("unknown website:" + href);
				// }

				News news = new News(title, new Date(), INFO_TYPE, href, content.toString());
				newsDAO.saveNews(news);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// for test
	// public static void main(String[] args) throws Exception {
	// new OfficeWorkSpider().crawl();
	// }
}
