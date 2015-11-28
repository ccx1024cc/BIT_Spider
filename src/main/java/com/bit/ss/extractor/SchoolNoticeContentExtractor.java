package com.bit.ss.extractor;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**   
 * @Title: SchoolNoticeContentExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  校官网中通知内容提取器
 * @author CCX
 * @date 2015年11月8日 上午10:44:27 
 * @version V1.0   
 */
public class SchoolNoticeContentExtractor extends ContentExtractor {
	
	public SchoolNoticeContentExtractor(String url, CloseableHttpClient client) {
		super(url, client);
	}

	@Override
	public Object extract() throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			httpResponse.close();
			Document html = Jsoup.parse(StringEscapeUtils.unescapeHtml4(entity));

			Element content = html.select("div.art_zw").first();
			// 预处理br
			Elements brs = content.select("br");
			prehandleBr(brs);

			// 提取正文
			List<Node> children = content.childNodes();
			for (Node fragment : children) {
				if (fragment instanceof Element) {
					Element temp = (Element) fragment;
					// 判断是否有图片节点
					if (!temp.select("img").isEmpty()) {
						Elements imgs = temp.select("img");
						for (Element img : imgs) {
							prehandleImg(img, url);
						}
					}
					sb.append(handleMyImg(handleMybr(generateTextNode(temp))));
				}
				// 直接文本节点
				else if (fragment instanceof TextNode) {
					TextNode temp = (TextNode) fragment;
					sb.append(generateTextNode(temp));
				}
			}

			// 提取附件
			Elements attachments = html.select("div.list_rtfujian a");
			for (Element attachment : attachments) {
				sb.append(attachHandle(attachment, url));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.delete(0, sb.length());
		}

		return sb.toString();
	}
	
	
}
