package com.bit.ss.extractor;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**   
 * @Title: JWCContentExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  教务处内容提取器
 * @author CCX
 * @date 2015年11月8日 下午12:56:32 
 * @version V1.0   
 */
public class JWCContentExtractor extends ContentExtractor {

	public JWCContentExtractor(String url, CloseableHttpClient client) {
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

			Elements content = html.select("body>table:nth-of-type(2)>tbody>tr:nth-child(3) p");
			for (Element temp : content) {
				// 判断是否是附件
				if (!temp.select("a").isEmpty()) {
					Elements attachments = temp.select("a");
					for (Element attachment : attachments) {
						// 判断是否是外部链接
						if (attachment.attr("href").contains("http") || attachment.attr("href").contains("https")
								|| attachment.attr("href").contains("mailto"))
							continue;
						String attachResult = attachHandle(attachment, url);
						if (attachResult != null)
							sb.append(attachResult);
					}
				}
				// 正文情况
				else {
					sb.append(generateTextNode(temp));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.delete(0, sb.length());
		}

		return sb.toString();
	}
}
