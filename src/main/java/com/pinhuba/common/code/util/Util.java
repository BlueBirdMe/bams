package com.pinhuba.common.code.util;

import java.io.InputStream;

import com.pinhuba.common.code.bean.DbConfig;
import com.pinhuba.common.code.database.DatabaseHandler;
import com.pinhuba.common.code.database.MysqlHandler;
import com.pinhuba.common.code.database.OracleHandler;
import com.pinhuba.common.code.service.DbDesignService;
import com.pinhuba.common.code.service.MysqlDesignService;
import com.pinhuba.common.code.service.OracleDesignService;

public class Util {
	
	/**
	 * 通过判断url获得对应的数据库处理对象
	 * @param url
	 * @return
	 */
	public static DatabaseHandler getHandler() {
		
		DbConfig c = DbConfig.getInstance();
		
		if (c.getUrl().indexOf("mysql") != -1) {
			return new MysqlHandler();
		} else if (c.getUrl().indexOf("oracle") != -1) {
			return new OracleHandler();
		}
		return null;
	}
	
	/**
	 * 通过判断url获得对应的数据库处理对象
	 * @param url
	 * @return
	 */
	public static DbDesignService getDesignService() {
		
		DbConfig c = DbConfig.getInstance();
		
		if (c.getUrl().indexOf("mysql") != -1) {
			return new MysqlDesignService();
		} else if (c.getUrl().indexOf("oracle") != -1) {
			return new OracleDesignService();
		}
		return null;
	}
	
	

	// 第一个字母小写
	public static String lowerCaseFirstLetter(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	// 第一个字母大写
	public static String upperCaseFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 打开目录
	 * 
	 * @param dir
	 */
	public static void openExplorer(String dir) {
		Runtime run = Runtime.getRuntime();
		try {
			// run.exec("cmd /k shutdown -s -t 3600");
			Process process = run.exec("cmd.exe /c start " + dir);
			InputStream in = process.getInputStream();
			while (in.read() != -1) {
				System.out.println(in.read());
			}
			in.close();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final int TRUE = 1;
	public static final int FALSE = 2;
	public static final int COUNT = 5;// 属性数量

	public static boolean tmpChange(String tmp) {
		if (Integer.valueOf(tmp) == TRUE)
			return true;
		else
			return false;
	}

	public static int boolean2int(String tmp) {
		if (tmp != null && Boolean.valueOf(tmp)) {
			return TRUE;
		}
		return FALSE;
	}

	public static int boolean2int(Boolean tmp) {
		if (tmp != null && tmp) {
			return TRUE;
		}
		return FALSE;
	}

	public static String getComponentTypeName(Integer type) {
		if (type == null)
			return "";
		if (type == 1)
			return "普通文本框";
		if (type == 2)
			return "数字框";
		if (type == 3)
			return "金额框";
		if (type == 4)
			return "日期框";
		if (type == 5)
			return "下拉框";
		if (type == 6)
			return "单选框";
		if (type == 7)
			return "多选框";
		if (type == 8)
			return "弹出单选框";
		if (type == 9)
			return "弹出多选框";
		if (type == 10)
			return "文本域";
		if (type == 11)
			return "FCK富文本";
		if (type == 12)
			return "上传图片组件";
		if (type == 13)
			return "上传附件组件";
		return "";
	}

	public static String getFieldTypesAsHtml(String type) {
		if (type == null || type.equals(""))
			type = "VARCHAR";
		StringBuffer buffer = new StringBuffer();
		buffer.append("<optgroup label='MYSQL'>");
		buffer.append("<option value='VARCHAR' " + (type.equalsIgnoreCase("VARCHAR") ? "selected" : "") + ">VARCHAR</option>");
		buffer.append("<option value='INT' " + (type.equalsIgnoreCase("INT") ? "selected" : "") + ">INT</option>");
		buffer.append("<option value='DOUBLE' " + (type.equalsIgnoreCase("DOUBLE") ? "selected" : "") + ">DOUBLE</option>");
		buffer.append("<option value='TEXT' " + (type.equalsIgnoreCase("TEXT") ? "selected" : "") + ">TEXT</option>");
		buffer.append("<option value='LONGTEXT' " + (type.equalsIgnoreCase("LONGTEXT") ? "selected" : "") + ">LONGTEXT</option>");
		buffer.append("</optgroup>");
		buffer.append("<optgroup label='ORACLE'>");
		buffer.append("<option value='VARCHAR2' " + (type.equalsIgnoreCase("VARCHAR2") ? "selected" : "") + ">VARCHAR2</option>");
		buffer.append("<option value='NUMBER' " + (type.equalsIgnoreCase("NUMBER") ? "selected" : "") + ">NUMBER</option>");
		buffer.append("<option value='CLOB' " + (type.equalsIgnoreCase("CLOB") ? "selected" : "") + ">CLOB</option>");
		buffer.append("<option value='BLOB' " + (type.equalsIgnoreCase("BLOB") ? "selected" : "") + ">BLOB</option>");
		buffer.append("<option value='NVARCHAR2' " + (type.equalsIgnoreCase("NVARCHAR2") ? "selected" : "") + ">NVARCHAR2</option>");
		buffer.append("<option value='NCLOB' " + (type.equalsIgnoreCase("NCLOB") ? "selected" : "") + ">NCLOB</option>");
		buffer.append("</optgroup>");
		return buffer.toString();
	}

	public static String getComponentTypesAsHtml(Integer type) {
		if (type == null)
			type = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("<option value='1' " + (type == 1 ? "selected" : "") + ">普通文本框</option>");
		buffer.append("<option value='2' " + (type == 2 ? "selected" : "") + ">数字框</option>");
		buffer.append("<option value='3' " + (type == 3 ? "selected" : "") + ">金额框</option>");
		buffer.append("<option value='4' " + (type == 4 ? "selected" : "") + ">日期框</option>");
		buffer.append("<option value='5' " + (type == 5 ? "selected" : "") + ">下拉框</option>");
		buffer.append("<option value='6' " + (type == 6 ? "selected" : "") + ">单选框</option>");
		buffer.append("<option value='7' " + (type == 7 ? "selected" : "") + ">多选框</option>");
		buffer.append("<option value='8' " + (type == 8 ? "selected" : "") + ">弹出单选框</option>");
		buffer.append("<option value='9' " + (type == 9 ? "selected" : "") + ">弹出多选框</option>");
		buffer.append("<option value='10' " + (type == 10 ? "selected" : "") + ">文本域</option>");
		buffer.append("<option value='11' " + (type == 11 ? "selected" : "") + ">FCK富文本</option>");
		buffer.append("<option value='12' " + (type == 12 ? "selected" : "") + ">上传图片组件</option>");
		buffer.append("<option value='13' " + (type == 13 ? "selected" : "") + ">上传附件组件</option>");
		return buffer.toString();
	}
}
