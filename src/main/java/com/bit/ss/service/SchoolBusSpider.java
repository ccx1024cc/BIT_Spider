package com.bit.ss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bit.ss.domain.News;

/**   
 * @Title: SchoolBusSpider.java 
 * @Package com.bit.ss.service 
 * @Description:  抽取校车信息
 * @author CCX
 * @date 2015年12月4日 下午5:13:43 
 * @version V1.0   
 */
@Service("SchoolBusSpider")
public class SchoolBusSpider extends SpiderSupporter implements ISpiderService {

	public SchoolBusSpider() {
		super(CODE_SCHOOL_BUS, "http://jwc.bit.edu.cn/plus/list.php?tid=1",
				"body>table>tbody>tr>td:nth-child(3)>table>tbody>tr:nth-child(3) a");
	}

	@Override
	public void crawl() throws Exception {
		// 获取链接目录
		Elements links = (Elements) indexExtractor.extract();
		// 筛选链接
		List<Element> contentLinks = new ArrayList<>();
		// 所有奇数项均为内容项，偶数项为目录项
		for (int i = 0; i < links.size(); i++) {
			// 抓取含有关键字“班车”的链接
			if (links.get(i).text().contains("班车"))
				contentLinks.add(links.get(i));
		}

		links = new Elements(contentLinks);
		// 处理抓取每一条信息
		saveEachNotice(links);
		shutdown();
	}

	@Override
	public void saveEachNotice(Elements links) {
//		 ContentExtractor extractor = new SchoolBusExtractor(null,
//		 httpclient);
		for (Element link : links) {
			try {
				String title = link.text();
				String href = link.attr("href");

				 // 如果存在，则跳过
				 if (newsDAO.isExit(title, this.INFO_TYPE))
				 continue;

				// 如果不存在，对通知内容进行提取
				StringBuilder content = new StringBuilder();
				href = "http://jwc.bit.edu.cn" + href;
				// extractor.setUrl(href);
				// content.append(extractor.extract());

				News news = new News(title, new Date(), INFO_TYPE, href, content.toString());
				newsDAO.saveNews(news);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws Exception {
		new SchoolBusSpider().crawl();
	}
}
