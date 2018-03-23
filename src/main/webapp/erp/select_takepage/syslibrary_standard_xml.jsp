<%@ page pageEncoding="UTF-8" language="java"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.core.pojo.SysLibraryStandard"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String upcode = request.getParameter("upcode");
if(upcode == null||upcode.length()==0){
	return;
}else{
	WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DwrSysProcessService sysService = (DwrSysProcessService)webAppContext.getBean("dwrSysProcessService");
	SysLibraryStandard info = new SysLibraryStandard();
	info.setLibraryUpcode(upcode);
	List<SysLibraryStandard> libraylist = sysService.listDownSysLibraryStandardByCode(request,upcode);
	if(libraylist!=null&& libraylist.size()>0){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<tree>\n");
		for(int i=0;i<libraylist.size();i++){
			SysLibraryStandard lbr = libraylist.get(i);
			String tmp = "";
			int row = sysService.listDownSysLibraryStandardByCodeCount(request,lbr.getLibraryCode());
			if(row>0){
				tmp ="src=\""+request.getContextPath()+"/erp/select_takepage/syslibrary_standard_xml.jsp?upcode="+lbr.getLibraryCode()+"\"";
			}
			//输出树节点
			sb.append("<tree type='radio' id =\""+lbr.getPrimaryKey()+"\" text=\""+lbr.getLibraryName()+"\" value=\""+lbr.getLibraryCode()+"\" "+tmp+"/>\n");
		}
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}
%>
