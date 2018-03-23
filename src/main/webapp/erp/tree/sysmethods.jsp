<%@ page pageEncoding="UTF-8" language="java"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.core.pojo.SysMethodInfo"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String methodcode = request.getParameter("code");
if(methodcode == null||methodcode.length()==0){
	return;
}else{
	WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DwrSysProcessService sysService = (DwrSysProcessService)webAppContext.getBean("dwrSysProcessService");
	List<SysMethodInfo> methodlist = sysService.getSysMethodInfoByUpCode(methodcode);
	if(methodlist!=null&& methodlist.size()>0){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<tree>\n");
		for(int i=0;i<methodlist.size();i++){
			SysMethodInfo md = methodlist.get(i);
			String tmp = "";
			String action ="";
			String icon="";
			if(md.getImageSrc()!=null&&md.getImageSrc().length()>0){
				icon ="icon=\""+request.getContextPath()+"/images/projectimg/"+md.getImageSrc()+"\"";
			}
			
			int row = sysService.getSysMethodInfoByUpCodeCount(md.getPrimaryKey());
			if(row>0){
				tmp ="src=\""+request.getContextPath()+"/erp/tree/sysmethods.jsp?code="+md.getPrimaryKey()+"\"";
			}
			
			action = "action=\"treeclick('"+md.getPrimaryKey()+"','"+md.getMethodInfoName()+"');\"";
		
			//输出树节点
			sb.append("<tree id =\""+md.getPrimaryKey()+"\" text=\""+md.getMethodInfoName()+"\" value=\""+md.getPrimaryKey()+"\" "+tmp+" "+icon+" "+action+"/>\n");
		}
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}
%>
