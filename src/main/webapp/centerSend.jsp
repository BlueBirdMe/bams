<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="com.pinhuba.core.pojo.SysMethodInfo"%>
<%@page import="java.util.List"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录跳转</title>
<link rel='stylesheet' type='text/css' href='<%=path%>/css/normal.css' />
<script type='text/javascript' src='<%=path%>/js/normalutil.js'></script>
</head>
<body>
	<div style="width:600px;margin:0 auto;border:1px solid #d1d1d1;margin-top:150px;padding:20px;">
		<table border="0" cellpadding="0" cellspacing="5" width="100%" style="padding: 10px;">
			<tr>
				<td align="center" valign="top" width="80">
				<img src="<%=path%>/images/systemmsg.png" border="0" style="margin-right:10px">
				</td>
				<td align="left">
				<label style="color: green;font-size:18px;font-weight: normal;vertical-align:bottom;margin-top: 7px;margin-bottom: 4px;">系统提示</label>
					<ul style="color:#4465A2;line-height: 30px;font-size:14px;">
						<li>系统尚未登录，不能访问。</li>
						<li>系统运行中长时间未操作。</li>
					</ul>
					<hr style="color: #d1d1d1;">
					<p style="padding-left: 15px;color: #666666">
						系统将在：<label style="color: #F38405;padding-left: 5px;padding-right: 5px;" id="second"></label>秒后跳转到登录页面!<br />
						<br />或者点击快速跳转进入登录页面。
					</p>
				</td>
			</tr>
		</table>
		<div style="margin:10px;text-align: right;">
			<input type="button" value="快速跳转 " onclick="reload()" style="line-height: 20px" />
		</div>
	</div>
	<script type="text/javascript">
	var s =10;
	document.getElementById("second").innerHTML = s;
	var t = window.setInterval(function(){
		s--;
		if(s<=0){
			reload();
			return;
		}
		document.getElementById("second").innerHTML = s;
	},1000);
	window.onunload =function(){
		if(t!=null){
			window.clearInterval(t);
		}
	}
	function reload(){
		window.top.location.href ="<%=path%>/login.jsp";
	}
	</script>
</body>
</html>