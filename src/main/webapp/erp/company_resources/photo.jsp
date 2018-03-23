<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相片明细</title>
<%
String pid =request.getParameter("imgId");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	
	document.getElementById("photo").src+="&imgId=" + <%=pid%>;
}
</script>
</head>
<body class="inputdetail" >
<br/>
<center>
<file:imgshow id="photo" title="右键另存为可以保存图片" alt="右键另存为可以保存图片"></file:imgshow>
</center>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<btn:btn onclick="window.close();"  value=" 关 闭 "></btn:btn>
</td>
</tr>
</table>
	
</body>
</html>