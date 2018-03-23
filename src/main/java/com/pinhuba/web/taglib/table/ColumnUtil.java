package com.pinhuba.web.taglib.table;

import com.pinhuba.web.taglib.table.cloumntype.AbscolumnType;
import com.pinhuba.web.taglib.table.cloumntype.TextType;

public class ColumnUtil {

	private static String columnCustomer="customer_"; 
	
	/**
	 * 自定义显示列
	 * 
	 * @param columnSign
	 *            列标识
	 * @param showName
	 *            显示名称
	 * @param columnReplace
	 *            显示js方法（只要名称）
	 * @param columnStrCount
	 *            截断长度 0标识不截断
	 * @param columnStyle
	 *            显示样式 没有请写入null
	 * @return
	 */
	public static SysGridColumnBean getCusterShowColumn(String columnSign, String showName, String columnReplace, int columnStrCount, String columnStyle) {
		SysGridColumnBean bean = new SysGridColumnBean();
		bean.setDataName(columnCustomer+columnSign);
		bean.setShowName(showName);
		bean.setShowColumn(true);
		bean.setShowAdvanced(false);
		bean.setShowQuerySelsect(false);
		bean.setColumnTypeClass(new TextType());
		bean.setColumnReplace(columnReplace);
		bean.setColumnStrCount(columnStrCount);
		bean.setColumnToObject(false);
		columnStyle = columnStyle == null ? "" : columnStyle;
		bean.setColumnStyle(columnStyle);
		return bean;
	}

	/**
	 * 自定义不封装到对象的高级查询
	 * 
	 * @param dataName
	 * @param showName
	 * @param columnType
	 * @return
	 */
	public static SysGridColumnBean getCusterAdvancedColumn(String dataName, String showName, AbscolumnType columnType) {
		SysGridColumnBean bean = new SysGridColumnBean();
		bean.setDataName(dataName);
		bean.setShowName(showName);
		bean.setShowColumn(false);
		bean.setShowAdvanced(true);
		bean.setShowQuerySelsect(false);
		if (columnType == null) {
			columnType = new TextType();
		}
		bean.setColumnTypeClass(columnType);
		bean.setColumnReplace("null");
		bean.setColumnStrCount(0);
		bean.setColumnToObject(false);
		return bean;
	}

}
