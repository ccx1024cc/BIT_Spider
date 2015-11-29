package com.bit.ss.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Title: News.java
 * @Package com.bit.ss.domain
 * @Description: 新闻信息
 * @author CCX
 * @date 2015年10月15日 下午2:47:30
 * @version V1.0
 */
@XmlRootElement
public class News {

	private int id;
	private String title;
	private String content;
	private Date pubTime;
	private int type; // 0：招生信息:，1：就业信息，2：招聘信息，3：教务处信息，4：通告，5：热点新闻
	private String url;

	public News() {
	}

	public News(String title, Date pubTime, int type, String url,String content) {
		this.title = title;
		this.pubTime = pubTime;
		this.type = type;
		this.url = url;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
