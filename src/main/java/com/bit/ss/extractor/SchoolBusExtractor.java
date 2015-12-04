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
 * @Title: SchoolBusExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  
 * @author CCX
 * @date 2015年12月4日 下午5:06:38 
 * @version V1.0   
 */

public class SchoolBusExtractor extends ContentExtractor {

	public SchoolBusExtractor(String url, CloseableHttpClient client) {
		super(url, client);
	}

	@Override
	public Object extract() throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "gbk");
			httpResponse.close();
			Document html = Jsoup.parse(StringEscapeUtils.unescapeHtml4(entity));

			Elements content = html.select("body>table:nth-of-type(2)>tbody>tr:nth-child(3) div");
			for (Element temp : content) {
				// 判断是否是附件
				sb.append(generateTextNode(temp));
			}
		} catch (Exception e) {
			e.printStackTrace();
			sb.delete(0, sb.length());
		}

		return sb.toString();
	}
}
