<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrPersonalProcessService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.module.TaskTodoBean"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="com.pinhuba.core.pojo.SysProcessConfig"%>
<%@page import="org.activiti.engine.runtime.ProcessInstance"%>
<%@page import="org.activiti.engine.task.Task"%>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean showapprove = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Approve.value);
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrPersonalProcessService service = (DwrPersonalProcessService)webcontext.getBean("dwrPersonalProcessService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
Integer row = Integer.parseInt(min);

str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(showapprove){
	str.append("<Approve><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Approve.value);
	
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	
	List<TaskTodoBean> list = service.listTaskTodo(this.getServletContext(),request,row);
	for(int i=0;i<list.size();i++){
		tmpsize++;
		TaskTodoBean bean = list.get(i);
		ProcessInstance p = bean.getProcessInstance();
		SysProcessConfig c = bean.getConfig();
		Task t = bean.getTask();
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='80'>流水号 "+t.getProcessInstanceId()+"</td>");
		str.append("<td><a class='lia' href=\"javascript:showDetail('"+c.getDetailPage()+"','"+p.getBusinessKey()+"','"+p.getId()+"');\">"+ bean.getWorkName() + "</a></td>");
		
		if(bean.getTask().getAssignee() == null)
			str.append("<td><a class='lia' href=\"javascript:claim('" + t.getId() + "')\">签收</a></td>");
		else
			str.append("<td onclick='hidebtn(this)'><a class='lia' href=\"javascript:handle('"+ c.getHandlePage() + "','" + p.getBusinessKey() + "','" + t.getId() + "','" + t.getTaskDefinitionKey() + "')\">办理</a></td>");
		
		str.append("</tr></table>");
		str.append("</li>");
	}
	
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	
	str.append("</ul>");
	str.append("]]></Approve>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>