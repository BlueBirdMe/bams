<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrOADesktopService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaDesktopSet"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.core.pojo.OaNotebook"%>
<%@page import="com.pinhuba.core.pojo.OaVote"%>
<%@page import="com.pinhuba.core.pojo.OaCalender"%>
<%@page import="com.pinhuba.core.pojo.OaRegulations"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String type = request.getParameter("type");
boolean shownotebook = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notes.value);
boolean voteshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Vote.value);
boolean schshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Schedule.value);
boolean timershow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Timer.value);
boolean showRegular = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Regular.value);

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
		str.append("<li class='ullicss' style='text-indent: 3px;'><a href='javascript:void(0)' onclick='showNoteBook("+tmp.getPrimaryKey()+")'  class='lia'  title='"+tmp.getOaNotebookContext()+"'>"+tmp.getOaNotebookContext()+"</a></li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Notes>\n");
}

if(showRegular){
	str.append("<Regul><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Regular.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaRegulations> list = dekService.getOaRegulationsByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaRegulations tmp = list.get(i);
		str.append("<li class='ullicss2' style='text-indent: 3px;'><a  href='javascript:void(0)' onclick='showRegular("+tmp.getPrimaryKey()+")'   class='lia'  title='"+tmp.getOaRegulationsTitle()+"'>"+tmp.getOaRegulationsTitle()+"</a></li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss2'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Regul>\n");
}

if(voteshow){
	str.append("<Vote><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Vote.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaVote> list = dekService.getOaVoteByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaVote tmp = list.get(i);
		
		str.append("<li class='ullicss' style='text-indent: 3px;'>");
		str.append("<table width='100%'><tr>");
		str.append("<td width='13px'>");
		if(tmp.getVoteCount()==0){
			str.append("<a href='javascript:void(0)' onclick='toupiao("+tmp.getPrimaryKey()+")' title='点击投票' style='padding-left:5px;padding-right:10px'><img border='0' style='vertical-align: middle;' alt='投票' src ='"+request.getContextPath()+"/images/grid_images/toupiao.png'/></a>");
		}else{
			str.append("<a href='javascript:void(0)' title='已投票，不能重复投票' style='padding-left:5px;padding-right:10px'><img border='0' style='vertical-align: middle;filter:gray;' alt='已投票，不能重复投票' src ='"+request.getContextPath()+"/images/grid_images/toupiao.png' /></a>");
		}
		str.append("</td>");
		str.append("<td align='left'>");
		str.append("<a href='javascript:void(0)' onclick='showVote("+tmp.getPrimaryKey()+")'  class='lia' title='"+tmp.getRecordDate().substring(0,16)+" "+tmp.getEmployee().getHrmEmployeeName()+":"+tmp.getOaVoteName()+"'>");
		str.append(tmp.getOaVoteName()+"</a></td>");
		str.append("<td align='right' nowrap='nowrap'><font color='#A0A0A0'>"+tmp.getEmployee().getHrmEmployeeName()+" "+tmp.getRecordDate().substring(5,10)+"</font></td></tr></table>");
		str.append("</li>");
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Vote>\n");	
}
if(schshow){
	str.append("<Schedule><![CDATA[");
	str.append("<ul class='ulcss'>");
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Schedule.value);
	int tmpsize = 0;
	if(depset!=null){
		row = Integer.parseInt(depset.getOaDesktopValue()==null?min:depset.getOaDesktopValue());
		if(row<Integer.parseInt(min)){
			row = Integer.parseInt(min);
		}else if(row>Integer.parseInt(max)){
			row = Integer.parseInt(max);
		}
	}
	List<OaCalender> list = dekService.getOaCalenderByEmpId(this.getServletContext(),request,row);
	tmpsize = list.size();
	for(int i=0;i<list.size();i++){
		OaCalender tmp = list.get(i);
		String tstr = "<font color='#005597' style='padding-left:3px;'>"+tmp.getOaCalenderStart().substring(5, 16)+"--"+tmp.getOaCalenderEnd().substring(5, 16)+"</font><font color='#476074' style='padding-left:3px;'>"+tmp.getLibrary().getLibraryInfoName()+":</font><font color='#0E75B7' style='padding-left:3px;'>"+tmp.getOaCalenderContent()+"</font>";
		if(tmp.getOaCalenderLevel()==EnumUtil.OA_CALENDER_LEVEL.one.value){
			str.append("<li class='ullicss'  style='text-indent: 15px;background: url("+request.getContextPath()+"/images/grid_images/zyjj.png) no-repeat 2px middel;' title='重要/紧急'><span class='lia' title='"+tmp.getOaCalenderContent()+"'>"+tstr+"</span></li>");
		}else if(tmp.getOaCalenderLevel()==EnumUtil.OA_CALENDER_LEVEL.two.value){
			str.append("<li class='ullicss'  style='text-indent: 15px;background: url("+request.getContextPath()+"/images/grid_images/zybjj.png) no-repeat 2px middel;' title='重要/不紧急'><span class='lia' title='"+tmp.getOaCalenderContent()+"'>"+tstr+"</span></li>");
		}else if(tmp.getOaCalenderLevel()==EnumUtil.OA_CALENDER_LEVEL.three.value){
			str.append("<li class='ullicss'  style='text-indent: 15px;background: url("+request.getContextPath()+"/images/grid_images/bzyjj.png) no-repeat 2px middel;' title='不重要/紧急'><span class='lia' title='"+tmp.getOaCalenderContent()+"'>"+tstr+"</span></li>");
		}else{
			str.append("<li class='ullicss'  style='text-indent: 15px;background: url("+request.getContextPath()+"/images/grid_images/bzybjj.png) no-repeat 2px middel;' title='不重要/不紧急'><span class='lia' title='"+tmp.getOaCalenderContent()+"'>"+tstr+"</span></li>");
		}
	}
	if(tmpsize<Integer.parseInt(min)){
		int tp=Integer.parseInt(min)-tmpsize;
		for(int j=0;j<tp;j++){
			str.append("<li class='ullicss'></li>");
		}
	}
	str.append("</ul>");
	str.append("]]></Schedule>\n");	
}
str.append("</DESKTOP>");
UtilTool.writeTextXml(response,str.toString());
%>
