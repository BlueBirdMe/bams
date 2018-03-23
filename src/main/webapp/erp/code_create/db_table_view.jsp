<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="db_common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表详情</title>
</head>
<body>
<table cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc">
	<tr>
		<td colspan="9">
			表名：${tableName}&nbsp;&nbsp;
			<input type="button" onclick="location.href='db_field_add.jsp?tableName=${tableName}'" value="新增字段"></input>
			<input type="button" onclick="location.href='listTables.do'" value="返回"></input>
		</td>
	</tr>
	<tr>
		<th align="center">字段名</th>
		<th align="center">字段类型</th>
		<th align="center">默认值</th>
		<th align="center">中文名称</th>
		<th align="center">简单查询</th>
		<th align="center">高级查询</th>
		<th align="center">组件类型</th>
		<th align="center">必填</th>
		<th align="center">操作</th>
	</tr>

	<c:forEach items="${fields}" var="f">
	<tr>
		<td>
			<a href="editField.do?tableName=${tableName}&fieldName=${f.name}">
				${f.name}
			</a> 
		</td>
		<td>${f.type}(${f.size})</td>
		<td>${f.defaultValue}</td>
		<td>${f.remark}</td>
		<td align="center"><c:if test="${f.showQuery}">✔</c:if></td>
		<td align="center"><c:if test="${f.showAdvanced}">✔</c:if></td>
		<td>${f.componentTypeName}</td>
		<td align="center"><c:if test="${f.must}">✔</c:if></td>
		<td align="center">
			<a href="javascript:if(confirm('确定要删除吗？')) {location.href='deleteField.do?tableName=${tableName}&fieldName=${f.name}'}">×</a> 
		</td>
	</tr>
	</c:forEach>
</table>

</body>
</html>