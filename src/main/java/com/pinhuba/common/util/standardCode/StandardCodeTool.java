package com.pinhuba.common.util.standardCode;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.core.pojo.SysLibraryStandard;
import com.pinhuba.web.controller.dwr.DwrSysProcessService;

/**
 * 标准代码工具类
 * @author jc
 */
public class StandardCodeTool {

	/**
	 * 加载标准代码为select jsp页面直接使用
	 * @param context
	 * @param request
	 * @param andtxt 追加的字符串
	 * @param code 上级编码
	 * @return
	 */
	public static String getSelectOptions(ServletContext context, HttpServletRequest request, String andtxt, String code) {
		String tmp = getLibraryInfoList(context, request, andtxt, code);
		return getSelectOptions(tmp);
	}
	
	/**
	 * 
	 * @param context
	 * @param request
	 * @param andtxt 追加的字符串
	 * @param code 上级编码
	 * @return
	 */
	public static String getLibraryInfoList(ServletContext context, HttpServletRequest request, String andtxt, String code) {
		String tmp = "";
		if (andtxt != null && andtxt.length() > 0) {
			tmp = andtxt + "|";
		}
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(context);
		DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
		List<SysLibraryStandard> list = sysProcessService.listDownSysLibraryStandardByCodeAll(context, request, code);
		for (SysLibraryStandard standard : list) {
			String libraryName = addIndention(standard.getLibraryName(),standard.getLibraryCode());
			tmp += standard.getLibraryStandCode() + "," + libraryName + "|";
		}
		if (tmp != null && tmp.length() > 0)
			return tmp.substring(0, tmp.length() - 1);
		else
			return tmp;
	}
	
	/**
	 * 更加code增加缩进
	 * @param libraryName
	 * @param libraryCode
	 * @return
	 */
	private static String addIndention(String libraryName, String libraryCode) {
		String str = libraryName;
		String indention = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//缩进
		for(int i = 3; i <= libraryCode.length()/2; i++ ){
			str = indention + str;
		}
		return str;
	}

	/**
	 * 将字符串转为select
	 * @param str
	 * @return
	 */
	public static String getSelectOptions(String str) {
		StringBuffer tmp = new StringBuffer();
		if (str != null && str.length() > 0) {
			String[] options = str.split("\\|");
			for (int j = 0; j < options.length; j++) {
				String[] tmpstr = options[j].split(",");
				String ck = "";
				tmp.append("<option value ='" + tmpstr[0] + "' " + ck + ">" + tmpstr[1] + "</option>\n");
			}
		}
		return tmp.toString();
	}
}
