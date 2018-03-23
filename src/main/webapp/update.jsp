<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%
StringBuffer sb = new StringBuffer();
sb.append("<versionInfo>"); 
sb.append("<versionName>BAMS 安卓版V1.0</versionName>"); 
sb.append("<versionCode>2</versionCode>"); 
sb.append("<versionDescription>1、版本更新功能演示;2、版本更新内容一;3、版本更新内容二</versionDescription>"); 
sb.append("<versionURL>http://www.pinhuba.com/u/cms/www/BAMS.apk</versionURL>"); 
sb.append("</versionInfo>"); 
response.setContentType("text/xml; charset=UTF-8");
PrintWriter pw = response.getWriter();
pw.print(sb.toString().replaceAll("&", "&amp;"));
pw.flush();
pw.close();
%>