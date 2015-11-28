package com.bit.ss.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.ss.util.DateUtil;

/**   
 * @Title: DateHandler.java 
 * @Package com.bit.ss.typehandler 
 * @Description:  日期格式化处理类
 * @author CCX
 * @date 2015年11月1日 上午10:54:39 
 * @version V1.0   
 */
@Component
public class DateHandler extends BaseTypeHandler<Date>{

	@Autowired
	DateUtil dateUtil;
	
	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1, Date arg2, JdbcType arg3) throws SQLException {
		// TODO Auto-generated method stub
		String date = dateUtil.formatDateTime(arg2, DateUtil.DATE_TIME_FORMAT);
		arg0.setString(arg1, date);
	}
	
	@Override
	public Date getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		String dateStr = arg0.getString(arg1);
		Date date = null;
		try {
			date = dateUtil.parse(dateStr, DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public Date getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		String dateStr = arg0.getString(arg1);
		Date date = null;
		try {
			date = dateUtil.parse(dateStr, DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public Date getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		String dateStr = arg0.getString(arg1);
		Date date = null;
		try {
			date = dateUtil.parse(dateStr, DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
}
