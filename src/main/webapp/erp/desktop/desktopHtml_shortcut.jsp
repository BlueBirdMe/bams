<%@page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.pinhuba.core.pojo.SysMethodInfo"%>
<%@page import="com.pinhuba.core.pojo.SysMethodShortcut"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)webcontext.getBean("dwrSysProcessService");
StringBuffer str = new StringBuffer();
str.append("<ul style='clear:both;'>");
List<SysMethodShortcut> list = service.listAllSysMethodShortcut(this.getServletContext(), request);
for(int i=0;i<list.size();i++){
	SysMethodShortcut shortcut = list.get(i);
    SysMethodInfo method = shortcut.getMethod();
    if(method != null){
	    String methodName = method.getMethodInfoName();
	    
	    String onclick = "";
	    if(StringUtils.isNotBlank(method.getMethodUri())){
	    	onclick = "onclick=\"openMDITab(\'"+method.getMethodRealUri(method.getPrimaryKey())+"\')\"";
	    }
	    
	    if(shortcut.getAutoOpen() == EnumUtil.SYS_ISEDIT.EDIT.value){
	    	methodName = methodName + "&nbsp;*";
	    }
	    
		str.append("<li id='"+shortcut.getPrimaryKey()+"' style='float:left;width:125px;'>");
		str.append("<a href=\"javascript:void(0);\" style=\"background: url(\'"+request.getContextPath()+"/images/projectimg/"+method.getImageSrc()+"\') 4px 5px no-repeat;\" class=\"deskbtn\" "+onclick+">"+methodName+"</a>");
		str.append("</li>");
    }
}
str.append("</ul>");
response.setContentType("text/html; charset=UTF-8");
PrintWriter pw = response.getWriter();
pw.print(str.toString());
pw.flush();
pw.close();
%>
