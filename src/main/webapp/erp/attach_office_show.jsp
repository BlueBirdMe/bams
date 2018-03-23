<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<%
String fileId = request.getParameter("fileId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var TANGER_OCX_OBJ; //控件对象

window.onload = function() {
	TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
	TANGER_OCX_OBJ.OpenFromURL ("<%=contextPath%>/download.do?fileId=<%=fileId%>");
	TANGER_OCX_OBJ.SetReadOnly(true);
	TANGER_OCX_OBJ.Menubar=false;
}
</script>
</head>
<body>
<script type="text/javascript" src="<%=contextPath%>/ntko/ntkoofficecontrol.js"></script>
</body>
</html>