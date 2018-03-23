<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="java.net.*" %>
<%@ include file="../common.jsp" %>
<%
	String path = URLEncoder.encode(request.getParameter("path"), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body class="inputcls">
<form action="<%=contextPath %>/erp/netdisk/uploadFiles.do?path=<%=path %>" method="post" enctype="multipart/form-data">
	<table cellpadding="0" cellspacing="5" border="0">
		<tr>
			<td>
				<input type="file" name="file1" style="width:300px">
			</td>
		</tr>
		<tr>
			<td>
				<input type="file" name="file2" style="width:300px">
			</td>
		</tr>
		<tr>
			<td>
				<input type="file" name="file3" style="width:300px">
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="上传文件"/>&nbsp;&nbsp;<font class="tip">可以选择多个文件一起上传</font>
			</td>
		</tr>
	</table>
</form>
</body>
</html>

