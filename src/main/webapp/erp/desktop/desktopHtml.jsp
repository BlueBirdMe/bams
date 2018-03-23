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
<%@page import="com.pinhuba.core.pojo.OaAnnouncement"%>
<%@page import="com.pinhuba.core.pojo.OaAdversaria"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean noticeshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notice.value);
boolean postsshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Post.value);
boolean noteshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notepad.value);
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOADesktopService dekService = (DwrOADesktopService)webcontext.getBean("dwrOADesktopService");
String min = SystemConfig.getParam("erp.desktop.showMinRow");
String max = SystemConfig.getParam("erp.desktop.showMaxRow");
StringBuffer str = new StringBuffer();
int row = Integer.parseInt(min);
str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
str.append("<DESKTOP>\n");
if(noticeshow){
	str.append("<Notice><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notice.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaNotice> list = dekService.getOaNoticeListByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaNotice tmp = list.get(i);
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='13px'>");
		if(tmp.getOaNotiType()==EnumUtil.OA_NOTICE_TYPE.EMERGENCY.value){
			str.append("<img src='"+request.getContextPath()+"/images/jj.png' title='紧急通知'>");
		}
		str.append("</td>");
		str.append("<td align='left'>");
		str.append("<a href='javascript:void(0)' onclick='showNotice("+tmp.getPrimaryKey()+")'  class='lia' title='"+tmp.getOaNotiTime().substring(0,16)+" "+tmp.getEmployee().getHrmEmployeeName()+":"+tmp.getOaNotiName()+"'>");
		str.append(tmp.getOaNotiName()+"</a></td>");
		str.append("<td align='right' nowrap='nowrap'><font color='#A0A0A0'>"+tmp.getEmployee().getHrmEmployeeName()+" "+tmp.getOaNotiTime().substring(5,10)+"</font></td></tr></table>");
		str.append("</li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Notice>\n");
}
if(postsshow){
	str.append("<Posts><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Post.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaAnnouncement> list = dekService.getOaAnnouncementBydesk(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaAnnouncement tmp = list.get(i);
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='13px'>");
		if(tmp.getOaAnnoLevel()==EnumUtil.OA_NEWS_ISTOP.YES.value){
			str.append("<img src='"+request.getContextPath()+"/images/lve1.gif' title='重要公告'>");
		}
		str.append("</td>");
		str.append("<td align='left'>");
		str.append("<a href='javascript:void(0)' onclick='showPosts("+tmp.getPrimaryKey()+")'  class='lia' title='"+tmp.getOaAnnoTime().substring(0,16)+" "+tmp.getEmployee().getHrmEmployeeName()+":"+tmp.getOaAnnoName()+"'>");
		str.append(tmp.getOaAnnoName()+"</a></td>");
		str.append("<td align='right' nowrap='nowrap'><font color='#A0A0A0'>"+tmp.getEmployee().getHrmEmployeeName()+" "+tmp.getOaAnnoTime().substring(5,10)+"</font></td></tr></table>");
		str.append("</li>");
		
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Posts>\n");
}
if(noteshow){
	str.append("<Note><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notepad.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaAdversaria> list = dekService.getOaAdversariaBydesk(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaAdversaria tmp = list.get(i);
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='13px'>");
		if(tmp.getOaAdverLevel()==EnumUtil.OA_NEWS_ISTOP.YES.value){
			str.append("<img src='"+request.getContextPath()+"/images/lve1.gif' title='重要公司记事'>");
		}
		str.append("</td>");
		str.append("<td align='left'>");
		str.append("<a href='javascript:void(0)' onclick='showNotepd("+tmp.getPrimaryKey()+")'  class='lia' title='"+tmp.getOaAdverTime().substring(0,16)+" "+tmp.getEmployee().getHrmEmployeeName()+":"+tmp.getOaAdverTitle()+"'>");
		str.append(tmp.getOaAdverTitle()+"</a></td>");
		str.append("<td align='right' nowrap='nowrap'><font color='#A0A0A0'>"+tmp.getEmployee().getHrmEmployeeName()+" "+tmp.getOaAdverTime().substring(5,10)+"</font></td></tr></table>");
		str.append("</li>");
		
		
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Note>\n");
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
