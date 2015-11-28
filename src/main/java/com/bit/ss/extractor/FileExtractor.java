package com.bit.ss.extractor;

import java.io.File;

import org.apache.http.impl.client.CloseableHttpClient;

/**   
 * @Title: FileExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  非树形文件内容提取器(eg.流式文件)
 * @author CCX
 * @date 2015年11月8日 下午2:30:49 
 * @version V1.0   
 */
public class FileExtractor extends ContentExtractor {

	private String title;// 文件标题

	public FileExtractor(String url, CloseableHttpClient client) {
		super(url, client);
	}

	@Override
	public Object extract() throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<stream ");
			String fileName = generateFileName(url.substring(url.lastIndexOf('.') + 1));
			downLoad(url, ATTACHMENT_SAVE_LOCATION + File.separator + fileName);
			sb.append("href=" + ATTACHMENT_REQUEST_BASE_URL + "/" + fileName + " />");
			sb.append(title);
			sb.append("</stream>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.delete(0, sb.length());
		}

		return sb.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
