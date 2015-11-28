package com.bit.ss.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.bit.ss.util.DateUtil;

/**   
 * @Title: ContentExtractor.java 
 * @Package com.bit.ss.extractor 
 * @Description:  内容提取器
 * @author CCX
 * @date 2015年11月8日 上午9:26:48 
 * @version V1.0   
 */
public abstract class ContentExtractor implements Extractor {

	protected String url;// 内容地址地址
	protected final CloseableHttpClient httpClient;

	public final String PIC_SAVE_LOCATION = "C:\\Users\\CCX\\Desktop";// 图片保存地址
	public final String ATTACHMENT_SAVE_LOCATION = "C:\\Users\\CCX\\Desktop"; // 附件保存地址
	public final String PIC_REQUEST_BASE_URL = "http://localhost:8080/BIT_Spider/WEB-INF/resources/newsPics"; // 请求图片的基地址
	public final String ATTACHMENT_REQUEST_BASE_URL = "http://localhost:8080/BIT_Spider/WEB-INF/resources/newsAttachments"; // 请求附件的基地址

	public ContentExtractor(String url, CloseableHttpClient client) {
		this.url = url;
		this.httpClient = client;
	}

	/**
	 * 
	 * @Title: generateFileName 
	 * @Description: 生成文件名
	 * @return String    返回类型 
	 * @throws
	 */
	protected String generateFileName(String suffix) {
		StringBuilder sb = new StringBuilder();
		Calendar now = Calendar.getInstance();
		sb.append(new DateUtil().formatDateTime(now.getTime(), DateUtil.FILE_NAME_FORMAT));
		sb.append(UUID.randomUUID().toString());
		sb.append("." + suffix);
		return sb.toString();
	}

	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 * @Title: downLoad 
	 * @Description: 下载函数
	 * @return String    返回类型 
	 * @throws
	 */
	protected void downLoad(String url, String path) throws ClientProtocolException, IOException {

		HttpGet get = new HttpGet(url);
		CloseableHttpResponse httpResponse = httpClient.execute(get);
		byte[] pic = EntityUtils.toByteArray(httpResponse.getEntity());
		File target = new File(path);
		FileOutputStream out = new FileOutputStream(target);
		out.write(pic);
		out.close();
		httpResponse.close();
	}

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

	/**
	 * 
	 * @Title: attachHandle 
	 * @Description: 单个附件节点处理
	 * @return String    返回类型 
	 * @throws
	 */
	protected String attachHandle(Element attachment, String currentUrl) {
		if (attachment.attr("href") == "")
			return null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<attach ");
			String attachLocation;
			// 判断是否是相对地址
			if (attachment.attr("href").contains("http"))
				attachLocation = attachment.attr("href");
			else
				attachLocation = handleRelativeUrl(currentUrl, attachment.attr("href"));
			String fileName = generateFileName(attachLocation.substring(attachLocation.lastIndexOf('.') + 1));
			downLoad(attachLocation, ATTACHMENT_SAVE_LOCATION + File.separator + fileName);
			sb.append("href=" + ATTACHMENT_REQUEST_BASE_URL + "/" + fileName + " />");
			sb.append(attachment.text());
			sb.append("</attach>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.delete(0, sb.length());
		}

		return sb.toString();
	}

	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 * @Title: tagImgHandle 
	 * @Description: 处理图片节点信息
	 * @return   返回类型 
	 * @throws
	 */
	protected void prehandleImg(Element img, String currentUrl) {
		try {
			String imgLocation = null;
			if (!img.attr("src").contains("http"))
				imgLocation = handleRelativeUrl(currentUrl, img.attr("src"));
			else
				imgLocation = img.attr("src");
			String fileName = generateFileName(imgLocation.substring(imgLocation.lastIndexOf('.') + 1));
			downLoad(imgLocation, PIC_SAVE_LOCATION + File.separator + fileName);
			img.text("myimgStart" + PIC_REQUEST_BASE_URL + "/" + fileName + "myimgEnd");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: prehandleBr 
	 * @Description: 预处理br标签，为其加上内容<mybr/>，从而转化为字符串后，可以正则替换为'\n'
	 * @return void    返回类型 
	 * @throws
	 */
	protected void prehandleBr(Elements brs) {
		for (Element br : brs) {
			br.text("<mybr/>");
		}
	}

	/**
	 * 
	 * @Title: prehandleTable 
	 * @Description: 预处理table标签，为每一行最后一个元素<mytd1/>或<mytd2/>
	 * @return void    
	 * @throws
	 */
	protected void prehandleTable(Element table) {
		Elements lastTds = table.select("tr>td:nth-last-child(1)");
		for (Element td : lastTds) {
			// 最后一行的最后一个td元素
			if (td.parent().nextElementSibling() == null)
				td.text(td.text() + "<mytd1/>");
			// 非最后一行的最后一个td元素
			else
				td.text(td.text() + "<mytd2/>");
		}
	}

	/**
	 * 
	 * @Title: prehandleLi 
	 * @Description: 预处理li标签，为每个元素<myli1/>或<myli2/>
	 * @return void    返回类型 
	 * @throws
	 */
	protected void prehandleLi(Elements lis) {
		for (Element li : lis) {
			// 最后一行的最后一个li元素
			if (li.nextElementSibling() == null)
				li.text(li.text() + "<myli1/>");
			// 最后一行的最后一个li元素
			else
				li.text(li.text() + "<myli2/>");
		}
	}

	/**
	 * 
	 * @Title: generateTextNode 
	 * @Description: 产生文本标签
	 * @return String    返回类型 
	 * @throws
	 */
	protected String generateTextNode(Node node) {
		StringBuilder sb = new StringBuilder();
		if (node instanceof Element) {
			Element temp = (Element) node;
			if (!"".equals(temp.text().trim()) && !" ".equals(temp.text().trim())) {
				sb.append("<text>");
				sb.append(temp.text().trim());
				sb.append("</text>");
			}
		} else if (node instanceof TextNode) {
			TextNode temp = (TextNode) node;
			if (!"".equals(temp.text().trim()) && !" ".equals(temp.text().trim())) {
				sb.append("<text>");
				sb.append(temp.text().trim());
				sb.append("</text>");
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: generateTitleNode 
	 * @Description: 产生标题标签
	 * @return String    返回类型 
	 * @throws
	 */
	protected String generateTitleNode(Node node) {
		StringBuilder sb = new StringBuilder();
		if (node instanceof Element) {
			Element temp = (Element) node;
			if (!"".equals(temp.text().trim()) && !" ".equals(temp.text().trim())) {
				sb.append("<title>");
				sb.append(temp.text().trim());
				sb.append("</title>");
			}
		} else if (node instanceof TextNode) {
			TextNode temp = (TextNode) node;
			if (!"".equals(temp.text().trim()) && !" ".equals(temp.text().trim())) {
				sb.append("<title>");
				sb.append(temp.text().trim());
				sb.append("</title>");
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: handleMybr 
	 * @Description: 将mybr标签替换为换行符
	 * @return String    返回类型 
	 * @throws
	 */
	protected String handleMybr(String str) {
		return str.replaceAll("<mybr/>", "\n");
	}

	/**
	 * 
	 * @Title: handleMytd 
	 * @Description: 将mytd标签替换为换行符
	 * @return String    返回类型 
	 * @throws
	 */
	protected String handleMytd(String str) {
		return str.replaceAll("<mytd1/>", "\n").replaceAll("<mytd2/> ", "\n");
	}

	/**
	 * 
	 * @Title: handleMyLi 
	 * @Description: 将myli标签替换为换行符
	 * @return String    返回类型 
	 * @throws
	 */
	protected String handleMyLi(String str) {
		return str.replaceAll("<myli1/>", "\n").replaceAll("<myli2/> ", "\n");
	}

	protected String handleMyImg(String str) {
		return str.replaceAll("myimgStart", "<img>").replaceAll("myimgEnd", "</img>");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
