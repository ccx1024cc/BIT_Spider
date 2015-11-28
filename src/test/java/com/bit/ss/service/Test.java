package com.bit.ss.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.bit.ss.strategy.ChangePerResponse;

/**
 * @Title: Test.java
 * @Package com.bit.ss.service
 * @Description:
 * @author CCX
 * @date 2015年11月1日 下午1:23:56
 * @version V1.0
 */
public class Test {

	public void test() throws ClientProtocolException, IOException {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);

		CookieStore cookieStore = new BasicCookieStore();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
				.setExpectContinueEnabled(true).build();

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm)
				.setDefaultCookieStore(cookieStore)
				//.setProxy(new HttpHost("127.0.0.1", 8888))  //fiddler proxy
				.setDefaultRequestConfig(defaultRequestConfig).setKeepAliveStrategy(new ChangePerResponse()).build();
		HttpGet get = new HttpGet("https://www.baidu.com");
		CloseableHttpResponse httpResponse = httpclient.execute(get);
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
		Header[] headers = httpResponse.getAllHeaders();
		for (Header head : headers) {
			System.out.println("name : " + head.getName() + " value : " + head.getValue());
		}
		System.out.println("\n");
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies)
			System.out.println("cookie name : " + cookie.getName() + " value : " + cookie.getValue());

		httpResponse.close();
		httpclient.close();
	}

	public static void main(String[] args) {
		try {
			new Test().test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
