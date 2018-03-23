<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>常用代码生成</title>
<%
	String imgUrl = contextPath+"/images/workflowimg/";
	String jspUrl = contextPath+"/erp/code_create/";
	
	//定义代码生成相关信息二维数组，名称、图标、URL
	String[][] arr={
   		{"数据库设计",imgUrl+"java.png",jspUrl+"listTables.do"},
   		{"POJO和DAO",imgUrl+"java.png",jspUrl+"createPojoAndDao.do"},
   		{"DWR和SERVICE",imgUrl+"java2.png",jspUrl+"code_create_two.jsp"},
   		{"生成页面",imgUrl+"jsp.png",jspUrl+"code_create_three.jsp"}
   	};
%>
</head>
<body style="overflow: hidden;">
	<div class="requdiv">
	<label>共有 <%= arr.length %> 个代码工具，点击图标开始生成。</label>
	</div>
	
	<% for (int i = 0; i < arr.length; i++){ %> 
		<div style="padding:30px;float:left;text-align:center;">
			<a href="javascript:void(0);" onclick="openMDITab('<%= arr[i][2] %>');" title="<%= arr[i][0] %>">
				<img src="<%=arr[i][1]%>" border="0" width="64" height="64"/>
				<p><%= arr[i][0] %></p>
			</a>
		</div>
	<%} %>
</body>
</html>