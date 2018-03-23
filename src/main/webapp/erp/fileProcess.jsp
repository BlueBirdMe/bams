<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.file.FileTool"%>
<%@page import="com.pinhuba.common.util.security.Base64"%>
<%
//处理删除文件请求
String path = request.getParameter("filepath");
String[] files = path.split(",");
String[] newFiles =new String[files.length];
for(int i=0;i<files.length;i++){
	newFiles[i]= Base64.getStringFromBase64(files[i].trim());
}
boolean bl = FileTool.deleteFiles(newFiles);
out.print(bl);
%>