<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看图片</title>
</head>
<body style="margin:0px;padding:0px;">
<img src="<%=request.getContextPath()%>/download.do?fileId=<%=request.getParameter("fileId")%>"></img>
</body>
</html>