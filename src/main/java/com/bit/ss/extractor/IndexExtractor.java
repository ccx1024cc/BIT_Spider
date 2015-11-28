package com.bit.ss.extractor;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**   
 * @Title: IndexExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  索引提取器
 * @author CCX
 * @date 2015年11月8日 上午9:24:53 
 * @version V1.0   
 */
public class IndexExtractor implements Extractor {

	protected String url;// 索引地址
	protected final CloseableHttpClient httpClient;
	protected String cssSelector; // 选择式，具体语法可参照jsoup官网

	public IndexExtractor(String url, CloseableHttpClient client, String cssSelector) {
		this.url = url;
		this.httpClient = client;
		this.cssSelector = cssSelector;
	}

	@Override
	public Object extract() throws Exception {
		// TODO Auto-generated method stub
		Elements links = null;
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(get);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(url + " connected false\n");
		} else {
			String httpEntity = null;
			//教务处的编码方式为gbk方式
			if (url.contains("jwc"))
				httpEntity = EntityUtils.toString(response.getEntity(), "gbk");
			else
				httpEntity = EntityUtils.toString(response.getEntity(), "UTF-8");
			Document html1 = Jsoup.parse(StringEscapeUtils.unescapeHtml4(httpEntity));
			links = html1.select(cssSelector);
		}
		response.close();
		return links;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCssSelector() {
		return cssSelector;
	}

	public void setCssSelector(String cssSelector) {
		this.cssSelector = cssSelector;
	}

}
