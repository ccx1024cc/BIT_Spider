package com.bit.ss.service;

import java.io.IOException;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.ss.dao.INewsDAO;
import com.bit.ss.extractor.IndexExtractor;
import com.bit.ss.strategy.ChangePerResponse;

/**   
 * @Title: BaseSpiderService.java 
 * @Package com.bit.ss.service 
 * @Description:  爬虫服务基础类
 * @author CCX
 * @date 2015年11月2日 下午4:31:32 
 * @version V1.0   
 */
@Service
public abstract class SpiderSupporter implements ISpiderService {

	@Autowired
	protected INewsDAO newsDAO;
	
	protected PoolingHttpClientConnectionManager cm;
	protected CookieStore cookieStore;
	protected RequestConfig defaultRequestConfig;
	protected CloseableHttpClient httpclient;

	public final int INFO_TYPE; // 抓取信息类型，新闻
	public final String URL; // 主页链接

	protected IndexExtractor indexExtractor; // 索引链接提取器

	public SpiderSupporter(int infoType, String url,String indexSelector) {
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		cookieStore = new BasicCookieStore();
		defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
				.build();
		httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultCookieStore(cookieStore)
				// .setProxy(new HttpHost("127.0.0.1", 8888)) //fiddler proxy
				.setDefaultRequestConfig(defaultRequestConfig).setKeepAliveStrategy(new ChangePerResponse()).build();
		this.INFO_TYPE = infoType;
		this.URL = url; // 索引所在页的地址
		indexExtractor = new IndexExtractor(URL, httpclient, indexSelector);
	}

	/**
	 * 默认只爬第一页链接索引
	 */
	@Override
	public void crawl() throws Exception {
		// 获取链接目录
		Elements links = (Elements) indexExtractor.extract();
		// 处理抓取每一条信息
		saveEachNotice(links);
		shutdown();
	}

	/**
	 * 
	 * @Title: shutdown 
	 * @Description: 关闭客户端
	 * @return void 返回类型
	 * @throws
	 */
	protected void shutdown() throws IOException {
		httpclient.close();
	}

	/**
	 * 
	 * @Title: saveEachNotice 
	 * @Description: 保存链接内每一条信息
	 * @return void    返回类型 
	 */
	public abstract void saveEachNotice(Elements links);

	/**
	 * 
	 * @Title: handleRelativeUrl 
	 * @Description: 处理相对地址
	 * @return String    返回绝对地址
	 * @throws
	 */
	protected String handleRelativeUrl(String currentUrl, String relativeUrl) {
		String temp = currentUrl;
		String tempRelative = relativeUrl;
		// 除去当前目录文件名
		temp = temp.substring(0, temp.lastIndexOf('/'));
		// 除去..造成的目录跳级
		while (tempRelative.charAt(0) == '.' && tempRelative.charAt(1) == '.' && tempRelative.charAt(2) == '/') {
			tempRelative = tempRelative.substring(3);
			temp = temp.substring(0, temp.lastIndexOf('/'));
		}
		return temp + '/' + tempRelative;
	}

}
