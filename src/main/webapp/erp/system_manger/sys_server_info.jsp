<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@page import="java.util.Properties"%>
<%@page import="java.lang.Runtime"%>
<%@page import="java.lang.System"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务器信息</title>
</head>
<body>
	<%
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;
	%>

	<fieldset>
		<legend>服务器信息</legend>
		<div>
			<table class="detailtable" align="center">
				<tr>
					<th width="15%">已用内存</th>
					<td><%=usedMemory/1024/1024%> M</td>
					<th width="15%">剩余内存</th>
					<td><%=useableMemory/1024/1024%> M</td>
				</tr>
				<tr>
					<th width="15%">最大内存</th>
					<td><%=maxMemory/1024/1024%> M</td>
					<th width="15%"></th>
					<td></td>
				</tr>
				<tr>
					<th width="15%">操作系统版本</th>
					<td><%=props.getProperty("os.name")%> <%=props.getProperty("os.version")%></td>
					<th width="15%">操作系统类型</th>
					<td><%=props.getProperty("os.arch")%> <%=props.getProperty("sun.arch.data.model")%>位</td>
				</tr>

				<tr>
					<th width="15%">用户、目录、临时目录</th>
					<td><%=props.getProperty("user.name")%> <%=props.getProperty("user.dir")%> <%=props.getProperty("java.io.tmpdir")%></td>
					<th width="15%"></th>
					<td></td>
				</tr>

				<tr>
					<th width="15%">JAVA运行环境</th>
					<td><%=props.getProperty("java.runtime.name")%> <%=props.getProperty("java.runtime.version")%></td>
					<th width="15%">JAVA虚拟机</th>
					<td><%=props.getProperty("java.vm.name")%> <%=props.getProperty("java.vm.version")%></td>
				</tr>
			</table>
		</div>
	</fieldset>

</body>
</html>