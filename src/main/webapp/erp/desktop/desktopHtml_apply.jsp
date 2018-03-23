<%@page import="com.pinhuba.core.pojo.SysProcessConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrPersonalProcessService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.common.module.HistoricProcessInstanceBean"%>
<%@page import="com.pinhuba.core.pojo.SysProcessConfig"%>
<%@page import="org.activiti.engine.history.HistoricProcessInstance"%>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean showapply = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Apply.value);
String applyType = request.getParameter("applyType");
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrPersonalProcessService service = (DwrPersonalProcessService)webcontext.getBean("dwrPersonalProcessService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
int row = Integer.parseInt(min);
str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(showapply){
	str.append("<APPLY><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Apply.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	
	List<HistoricProcessInstanceBean> list = service.listHistoricProcessUnfinished(this.getServletContext(), request, applyType, row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		HistoricProcessInstanceBean bean = list.get(i);
		
		HistoricProcessInstance h = bean.getHistoricProcessInstance();
		SysProcessConfig c = bean.getConfig();
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='80'>流水号 "+bean.getHistoricProcessInstance().getId()+"</td>");
		str.append("<td><a class='lia' href=\"javascript:showDetail('"+c.getDetailPage()+"','"+h.getBusinessKey()+"','"+h.getId()+"');\">"+bean.getWorkName()+"</a></td>");
		str.append("</tr></table>");
		str.append("</li>");
	}
	
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'><br/></li>");
		}
	}
	
	str.append("</ul>");
	str.append("]]></APPLY>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
