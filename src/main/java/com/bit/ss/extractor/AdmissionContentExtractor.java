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
 * @Title: AdmissionContentExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  本科招生信息网提取器
 * @author CCX
 * @date 2015年11月8日 下午1:32:38 
 * @version V1.0   
 */
public class AdmissionContentExtractor extends ContentExtractor {

	public AdmissionContentExtractor(String url, CloseableHttpClient client) {
		super(url, client);
	}
	
	@Override
	public Object extract() throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse httpResponse = this.httpClient.execute(get);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			httpResponse.close();
			Document html = Jsoup.parse(StringEscapeUtils.unescapeHtml4(entity));

			Elements contents = html.select("div.news_data").first().children();
			// 预处理br
			Elements brs = contents.select("br");
			prehandleBr(brs);
			
			for(Element fragment:contents){
				//table的情况
				if("table".equals(fragment.tagName())){
					prehandleTable(fragment);
					sb.append(handleMybr(handleMytd(generateTextNode(fragment))));
				}
				//非table的情况
				else{
					sb.append(handleMybr(generateTextNode(fragment)));
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
