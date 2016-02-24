package com.bit.ss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;

/**   
 * @Title: JWCSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  抓取教务处通知
 * @author CCX
 * @date 2015年11月4日 下午4:15:19 
 * @version V1.0   
 */
@Service("JWCNoticeSpider")
public class JWCNoticeSpider extends SpiderSupporter implements ISpiderService {

	public JWCNoticeSpider() {
		super(CODE_JWC_NOTICE, "http://jwc.bit.edu.cn/",
				"body>table>tbody>tr>td:nth-child(3)>table>tbody>tr:nth-child(4) a");
	}

	@Override
	public void crawl() throws Exception {
		// 获取链接目录
		Elements links = (Elements) indexExtractor.extract();
		// 筛选链接
		List<Element> contentLinks = new ArrayList<>();
		// 所有奇数项均为内容项，偶数项为目录项
		for (int i = 0; i < links.size(); i++) {
			if (i % 2 != 0)
				contentLinks.add(links.get(i));
		}

		links = new Elements(contentLinks);
		// 处理抓取每一条信息
		saveEachNotice(links);
		shutdown();
	}

	@Override
	public void saveEachNotice(Elements links) {
//		ContentExtractor extractor = new JWCContentExtractor(null, httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				// 如果存在，则跳过
				if (newsDAO.isExit(title, this.INFO_TYPE))
					continue;

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				href = URL + "/" + href;
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
	// new JWCNoticeSpiderService().crawl();
	// }
}
