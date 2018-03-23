<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrCommonService"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@ include file="common.jsp"%>
<%
String fileId = request.getParameter("fileId");
WebApplicationContext webcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrCommonService service = (DwrCommonService)webcontext.getBean("dwrCommonService");
SysAttachmentInfo attach = service.getAttachmentInfoByPk(this.getServletContext(), request, Long.valueOf(fileId));
String fileName = attach.getAttachmentName();
String filePath = Base64.getStringFromBase64(attach.getAttachmentFilename());
String text = FileUtils.readFileToString(new File(filePath), "utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><%=fileName %></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="width:100%;height:100%;overflow:hidden;margin:0px;padding:0px;">
<textarea style="width:100%;height:100%;">
<%=text %>
</textarea>
</body>
</html>


