<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrOADesktopService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.core.pojo.OaSmsInbox"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean showsms = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Sms.value);

WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOADesktopService dekService = (DwrOADesktopService)webcontext.getBean("dwrOADesktopService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
int row = Integer.parseInt(min);
str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(showsms){
	str.append("<SMS><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Sms.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaSmsInbox> list = dekService.getOaSmsByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaSmsInbox tmp = list.get(i);
		if(tmp.getOaSmsInboxIsread()==EnumUtil.OA_SMS_INBOX_ISREAD.one.value){
			str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread_.gif) no-repeat 2px middel;' title='已读  收件时间："+tmp.getOaSmsInboxSendtime()+"'><a href='javascript:void(0)' onclick='showOaSms("+tmp.getPrimaryKey()+","+tmp.getOaSmsInboxIsread()+")'  class='lia'  title='"+tmp.getOaSmsInboxSendtime().substring(0,16)+" "+tmp.getOaSmsInboxSenderName()+": "+tmp.getOaSmsInboxContent()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaSmsInboxSenderName()+"</label>"+tmp.getOaSmsInboxContent()+"</a></li>");
		}else{
			str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread.gif) no-repeat 2px middel;font-weight: bold;' title='未读  收件时间："+tmp.getOaSmsInboxSendtime()+"'><a href='javascript:void(0)' onclick='showOaSms("+tmp.getPrimaryKey()+","+tmp.getOaSmsInboxIsread()+")'  class='lia'  title='"+tmp.getOaSmsInboxSendtime().substring(0,16)+" "+tmp.getOaSmsInboxSenderName()+": "+tmp.getOaSmsInboxContent()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaSmsInboxSenderName()+"</label>"+tmp.getOaSmsInboxContent()+"</a></li>");
		}
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></SMS>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
