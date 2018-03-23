<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrOADesktopService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.core.pojo.OaNotice"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.core.pojo.OaNotebook"%>
<%@page import="com.pinhuba.core.pojo.HrmTimedrecord"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String type = request.getParameter("type");
boolean shownotebook = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notes.value);
boolean timershow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Timer.value);

WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOADesktopService dekService = (DwrOADesktopService)webcontext.getBean("dwrOADesktopService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
int row = Integer.parseInt(min);
str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(shownotebook){
	str.append("<Notes><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notes.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaNotebook> list = dekService.getOaNoteBookListByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaNotebook tmp = list.get(i);
		str.append("<li class='ullicss2' style='text-indent: 3px;'><a href='javascript:void(0)' onclick='showNoteBook("+tmp.getPrimaryKey()+")'  class='lia'  title='"+tmp.getOaNotebookContext()+"'>"+tmp.getOaNotebookContext()+"</a></li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss2'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Notes>\n");
}
if(timershow){
	str.append("<Timer><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Timer.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<HrmTimedrecord> list = dekService.getHrmTimerecord(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		HrmTimedrecord tmp = list.get(i);
		String time = "";
		if(tmp.getTimedType() == EnumUtil.TIMED_TYPE.Vaild.value){
			time = "<label style='color:blue;padding-right:3px'>"+EnumUtil.TIMED_TYPE.valueOf(tmp.getTimedType())+" "+tmp.getTimedDate()+"</label>";
		}else{
			time ="<label style='color:blue;padding-right:3px'>"+tmp.getTimedDate()+"</label>";
		}
		str.append("<li class='ullicss2' style='text-indent: 3px;padding:3px'>"+time+"<a href='javascript:void(0)' class='lia'  title='"+tmp.getTimedDescription()+"'>"+tmp.getTimedDescription()+"</a></li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss2'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Timer>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
