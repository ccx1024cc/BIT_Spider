package com.bit.ss.extractor;

/**   
* @Title: IExtractor.java 
* @Package com.bit.ss.extractor 
* @Description:  抽象提取器,注：每次只针对一个链接进行提取
* @author CCX
* @date 2015年11月8日 上午8:24:49 
* @version V1.0   
*/
public interface Extractor {

	/**
	 * 
	 * @Title: extract 
	 * @Description: 提取所需信息
	 * @param	索引在文档树中的选择器，具体语法参照jsoup官方文档
	 * @return Object    返回类型 
	 * @throws	不可恢复异常
	 */
	public Object extract() throws Exception;
}
