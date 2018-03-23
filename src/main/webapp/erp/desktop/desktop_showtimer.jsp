<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
WebApplicationContext webcontext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
DwrMoblieSmsService mobService = (DwrMoblieSmsService)webcontext.getBean("dwrMoblieSmsService");
List<HrmTimedrecord> timerList = mobService.getSchTimerByEmpId(this.getServletContext(),request);

 %>
<title>定时提醒</title>
</head>
<body class="inputdetail">
<%
for(int i=0;i<timerList.size();i++){
	HrmTimedrecord timer = timerList.get(i);
	String type = timer.getTimedDate();
	if(timer.getTimedType() == EnumUtil.TIMED_TYPE.Vaild.value){
		type = EnumUtil.TIMED_TYPE.valueOf(timer.getTimedType())+"&nbsp;"+timer.getTimedDate();
	}
%>
<fieldset>
	<legend><strong>提醒时间：<%=type%></strong></legend>
	<div style="padding: 10px;text-align: left;color: red;font-weight: bold;font-size: 14px;">
	<img src="<%=request.getContextPath()%>/images/jj.png"/>&nbsp;&nbsp;<%=timer.getTimedDescription() %>
	</div>
</fieldset>
<br/>
<%} %>
<%mobService.clearSchTimer(this.getServletContext(),request); %>
</body>
</html>