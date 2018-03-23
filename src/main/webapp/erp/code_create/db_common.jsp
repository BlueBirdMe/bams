<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import="java.util.*" %>
<%@page import="com.pinhuba.common.code.bean.*" %>
<%@page import="com.pinhuba.common.code.util.*" %>

<%
String contextPath = request.getContextPath();
%>

<style>
body{font-family:微软雅黑;font-size:12px;}
a{text-decoration: none; }
.tb{font-size:12px;border-collapse: collapse;}
.tb th{
	background:url(<%=contextPath%>/images/grid_images/wbg.gif) repeat-x;
	white-space: nowrap;
}
</style>

