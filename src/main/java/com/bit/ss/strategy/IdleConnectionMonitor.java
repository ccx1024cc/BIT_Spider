package com.bit.ss.strategy;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * @Title: IdleConnectionMonitor.java
 * @Package com.bit.ss.strategy
 * @Description: 隔一定时间扫描一次，需在主进程中启动进程
 * @author CCX
 * @date 2015年11月1日 下午2:23:34
 * @version V1.0
 */
public class IdleConnectionMonitor extends Thread {

	private final HttpClientConnectionManager cm;
	private boolean shutdown;

	public IdleConnectionMonitor(HttpClientConnectionManager cm) {
		// TODO Auto-generated constructor stub
		this.cm = cm;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		shutdown = false;
		try {
			while (!shutdown) {
				wait(5000); // 每5秒扫描一次
				cm.closeIdleConnections(30, TimeUnit.SECONDS); // 关闭空闲大于30秒的连接
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}

}
