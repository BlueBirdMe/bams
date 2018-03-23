<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="db_common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑字段</title>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/jquery-1.7.2.min.js'></script>
<script type="text/javascript">
$(function(){
	
	checkFieldType();
	
	$("#type").change(function(){
		$("#size").attr("disabled",false);
		$("#defaultValue").attr("disabled",false);
		
		checkFieldType();
	});
	
})

function checkFieldType(){
	
	var fieldType = $("#type").val();
	
	var arr = ["BLOB", "CLOB", "NCLOB", "TEXT", "LONGTEXT"];//字段长度、默认值都不用填
	
	if(arr.indexOf(fieldType) != -1){
		$("#size").val("").attr("disabled","disabled");
		$("#defaultValue").val("").attr("disabled","disabled");
	}
	
	var arr2 = ["DOUBLE"];//字段长度不用填
	
	if(arr2.indexOf(fieldType) != -1){
		$("#size").val("").attr("disabled","disabled");
	}
	
}

</script>
</head>
<body>
<form action="updateField.do" method="post">
<table cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc">
<tr>
	<td colspan="2">
		表名：${tableName}&nbsp;&nbsp;
		<input type="hidden" value="${tableName}" name="tableName"/>
		<input type="submit" value="提交"/>
		<input type="button" onclick="location.href='viewTable.do?name=${tableName}'" value="返回"/>
	</td>
</tr>
<tr>
	<td>字段名</td>
	<td>
		<input type="text" name="name" readonly value="${field.name}"/>
	</td>
</tr>
<tr>
	<td>字段类型</td>
	<td><select name="type" id="type">${field.fieldTypesAsHtml}</select></td>
</tr>
<tr>
	<td>字段长度</td>
	<td><input type="text" name="size" id="size" size="5" value="${field.size}"></td>
</tr>
<tr>	
	<td>默认值</td>
	<td><input type="text" name="defaultValue" id="defaultValue" size="5" value="${field.defaultValue}"></td>
</tr>
<tr>	
	<td>中文名称</td>
	<td><input type="text" name="remark" size="10" value="${field.remark}"></td>
</tr>
<tr>	
	<td>简单查询</td>
	<td>
	<select name="showQuery">
		<option value="false">否</option>
		<option <c:if test="${field.showQuery}">selected</c:if> value="true">是</option>
	</select>
	</td>
</tr>
<tr>	
	<td>高级查询</td>
	<td>
	<select name="showAdvanced">
		<option value="false">否</option>
		<option <c:if test="${field.showAdvanced}">selected</c:if> value="true">是</option>
	</select>
	</td>
</tr>
<tr>	
	<td>组件类型</td>
	<td>
	<select name="componentType">
		${field.componentTypesAsHtml}
	</select>
	</td>
</tr>
<tr>	
	<td>必填</td>
	<td>
	<select name="must">
		<option value="false">否</option>
		<option <c:if test="${field.must}">selected</c:if> value="true">是</option>
	</select>
	</td>
</tr>
</table>
</form>
<%@ include file="db_attention.jsp"%>
</body>
</html>