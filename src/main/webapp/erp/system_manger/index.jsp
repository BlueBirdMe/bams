<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%
SessionUser sessionUser = (SessionUser)LoginContext.getSessionValueByLogin(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>BAMS后台</title>
		<link rel='stylesheet' type='text/css' href='<%=contextPath%>/js/tabs/tabs.css' />
		<script type="text/javascript" src="<%=contextPath%>/js/tabs/tabs.js"></script>
		<link type='text/css' rel='stylesheet' href='<%=contextPath%>/css/xtree.css' />
		<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xtree.js' charset='UTF-8'></script>
		<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xloadtree.js' charset='UTF-8'></script>
		<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xmlextras.js' charset='UTF-8'></script>
		<script type='text/javascript'>webFXTreeConfig.setImagePath('<%=contextPath%>/js/treeJs/images/default/');</script>
		<script type="text/javascript">
		window.onload = function(){
			MDIOpen("<%=contextPath %>/erp/system_manger/sys_method.jsp");
		}
		</script>
	</head>

	<body>
		<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
		<tr>
		<td height="55" style="background:url('<%=contextPath %>/images/top-5.png') repeat-x;">
			<!-- 首页顶部开始 -->
			<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
				<tr>
					<td style="padding-left:5px;padding-right:5px" valign="center">
						<img border="0" alt="公司logo" src="<%=contextPath %>/images/syslogin.png" style="height:35px;">
					</td>
					<td valign="top" width="100%" align="right">
						<%@include file="../index_top.jsp"%> 
					</td>
				</tr>
			</table>
			<!-- 首页顶部结束 -->
		</td>
		</tr>
		<tr>
			<td>
			<!-- 首页中间开始 -->
			<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
				<tr>
					<td id="split_l" style="width:200px;">
						<div class="div_title">功能菜单</div>
						<div class="div_content">
						<script type="text/javascript">
							function treeclick(url){
								openMDITab(url);
							}
						
							var tree = new WebFXTree("BAMS");
							tree.add(new WebFXLoadTreeItem("系统综合管理", "index_menu.xml"));
							document.write(tree);
							setTimeout(function(){autoExpand();},500);
						</script>
						<style>
						.webfx-tree-item a{
							font-family:微软雅黑;
							font-size:14px;
							letter-spacing:0.5px;
						}
						</style>
						</div>
					</td>
					<td>
				        <iframe height="100%" width="100%" 
				            marginHeight=0 frameBorder=0 
				            name="mainframe" id="mainframe" marginWidth=0 scrolling=no></iframe>
					</td>
				</tr>
			</table>
			<!-- 首页中间结束 -->
			</td>
		</tr>
		
		<tr>
		<td height="30" style="background:#054376;padding-left:10px;">
			<font color="#ffffff">
			(c)2008-2014 PINHUBA BAMS Version 2.0
			</font>
		</td>
		</tr>
		</table>
	</body>
</html>
