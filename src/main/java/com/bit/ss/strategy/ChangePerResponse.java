package com.bit.ss.strategy;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 * @Title: ChangePerResponse.java
 * @Package com.bit.ss.strategy
 * @Description: 每次收到返回包，重新设定连接时间
 * @author CCX
 * @date 2015年11月1日 下午2:11:12
 * @version V1.0
 */
public class ChangePerResponse implements ConnectionKeepAliveStrategy {

	@Override
	public long getKeepAliveDuration(HttpResponse arg0, HttpContext arg1) {
		// TODO Auto-generated method stub
		HeaderElementIterator it = new BasicHeaderElementIterator(arg0.headerIterator(HTTP.CONN_KEEP_ALIVE));
		while (it.hasNext()) {
			HeaderElement he = it.nextElement();
			String param = he.getName();
			String value = he.getValue();
			if (value != null && param.equalsIgnoreCase("timeout")) {
				try {
					return Long.parseLong(value) * 1000;
				} catch (NumberFormatException ignore) {
				}
			}
		}
		// 如果不知明时间则默认只保存10秒连接
		return 5 * 1000;
	}
}
