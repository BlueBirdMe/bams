<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="java.net.*;"%>

<%
String path = URLEncoder.encode(request.getParameter("path"), "UTF-8");
String isShare = request.getParameter("isShare");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body style="margin:0px;padding:0px;">
<img src='createMiniPic.do?path=<%=path %>&isShare=<%=isShare%>'/>
</body>