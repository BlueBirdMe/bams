<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrOADesktopService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.core.pojo.OaMailEmp"%>
<%@page import="com.pinhuba.core.pojo.OaNetmailInbox"%>
<%@page import="com.pinhuba.core.pojo.OaNetmailSet"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean showmail = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Mail.value);
String mailType = request.getParameter("mailtype");
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOADesktopService dekService = (DwrOADesktopService)webcontext.getBean("dwrOADesktopService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
int row = Integer.parseInt(min);
str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(showmail&&mailType!=null){
	str.append("<MAIL><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Mail.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	int mt = Integer.parseInt(mailType);
	if(mt==1){//内部邮件
		List<OaMailEmp> list = dekService.getOaMailByEmpId(this.getServletContext(),request,row);
		tmpsize = list.size();
		for(int i=0;i<list.size();i++){
			OaMailEmp tmp = list.get(i);
			if(tmp.getOaMailEmpIsread()==EnumUtil.OA_SMS_INBOX_ISREAD.one.value){
				str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread_.gif) no-repeat 2px middel;' title='已读  收件时间："+tmp.getOaMailEmpInboxid().getOaMailInboxIntime()+"'><a href='javascript:void(0)' onclick='showOaMail("+tmp.getPrimaryKey()+","+tmp.getOaMailEmpIsread()+")'  class='lia'  title='"+tmp.getOaMailEmpInboxid().getOaMailInboxIntime().substring(0,16)+" "+tmp.getOaMailEmpInboxid().getOaMailInboxSendName()+": "+tmp.getOaMailEmpInboxid().getOaMailInboxTitle()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaMailEmpInboxid().getOaMailInboxSendName()+"</label>"+tmp.getOaMailEmpInboxid().getOaMailInboxTitle()+"</a></li>");
			}else{
				str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread.gif) no-repeat 2px middel;font-weight: bold;' title='未读  收件时间："+tmp.getOaMailEmpInboxid().getOaMailInboxIntime()+"'><a href='javascript:void(0)' onclick='showOaMail("+tmp.getPrimaryKey()+","+tmp.getOaMailEmpIsread()+")'  class='lia'  title='"+tmp.getOaMailEmpInboxid().getOaMailInboxIntime().substring(0,16)+" "+tmp.getOaMailEmpInboxid().getOaMailInboxSendName()+": "+tmp.getOaMailEmpInboxid().getOaMailInboxTitle()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaMailEmpInboxid().getOaMailInboxSendName()+"</label>"+tmp.getOaMailEmpInboxid().getOaMailInboxTitle()+"</a></li>");
			}
		}
	}else if(mt==2){//外部邮件
		OaNetmailSet mailSet = UtilTool.getNetMailByDefault(this.getServletContext(),request);
		if(mailSet!=null){
			List<OaNetmailInbox> list = dekService.getOaNetMailByEmpId(this.getServletContext(),request,row,mailSet.getOaNetmailFrom());
			tmpsize = list.size();
			for(int i=0;i<list.size();i++){
				OaNetmailInbox tmp = list.get(i);
				if(tmp.getOaNetmailIsRead()==EnumUtil.OA_SMS_INBOX_ISREAD.one.value){
					str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread_.gif) no-repeat 2px middel;' title='已读  收件时间："+tmp.getOaNetmailInboxTime()+"'><a href='javascript:void(0)' onclick='showOaNetMail("+tmp.getPrimaryKey()+","+tmp.getOaNetmailIsRead()+","+mailSet.getPrimaryKey()+")'  class='lia'  title='"+tmp.getOaNetmailInboxTime().substring(0,16)+" "+tmp.getOaNetmailInboxSender()+": "+tmp.getOaNetmailInboxTitle()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaNetmailInboxSender()+"</label>"+tmp.getOaNetmailInboxTitle()+"</a></li>");
				}else{
					str.append("<li class='ullicss' style='text-indent: 15px;background: url("+request.getContextPath()+"/images/smsisread.gif) no-repeat 2px middel;font-weight: bold;' title='未读  收件时间："+tmp.getOaNetmailInboxTime()+"'><a href='javascript:void(0)' onclick='showOaNetMail("+tmp.getPrimaryKey()+","+tmp.getOaNetmailIsRead()+","+mailSet.getPrimaryKey()+")'  class='lia'  title='"+tmp.getOaNetmailInboxTime().substring(0,16)+" "+tmp.getOaNetmailInboxSender()+": "+tmp.getOaNetmailInboxTitle()+"'><label style='padding-right:5px;padding-left:5px;color:#336699'>"+tmp.getOaNetmailInboxSender()+"</label>"+tmp.getOaNetmailInboxTitle()+"</a></li>");
				}
			}
		}else{
			tmpsize = 1;
			str.append("<li class='ullicss' style='color:red'>外部邮箱尚未设置，请进入信息交流-外部邮箱进行设置!</li>");
		}
		
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'><br/></li>");
		}
	}
	
	str.append("</ul>");
	str.append("]]></MAIL>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
