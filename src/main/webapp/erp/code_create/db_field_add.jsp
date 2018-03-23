<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="db_common.jsp"%>
<%
String tableName = request.getParameter("tableName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增字段</title>
<script type="text/javascript">
	function addRow(){
		
		var otr = document.getElementById("fieldTable").insertRow(-1);
		
		var td13 = document.createElement("td");
		td13.innerHTML = "<a href=\"#\" onclick=\"deltablerow('fieldTable');\">[×]</a>";
	
		var td1 = document.createElement("td");
		td1.innerHTML = "<input type='text' name='name'></input>";
	
		var td2 = document.createElement("td");
		td2.innerHTML = "<select name='type'><%= Util.getFieldTypesAsHtml(null) %></select>";
		
		var td3 = document.createElement("td");
		td3.innerHTML = "<input type='text' name='size' size='5'>";
		
		var td6 = document.createElement("td");
		td6.innerHTML = "<input type='text' name='defaultValue' size='5'>";
	
		var td8 = document.createElement("td");
		td8.innerHTML = "<input type='text' name='remark' size='10'></input>";
	 
		var td9 = document.createElement("td");
		td9.innerHTML = "<select name='showQuery'><option value='false'>否</option><option value='true'>是</option></select>";
	 
		var td10 = document.createElement("td");
		td10.innerHTML = "<select name='showAdvanced'><option value='false'>否</option><option value='true'>是</option></select>";
	 
		var td11 = document.createElement("td");
		td11.innerHTML = "<select name='componentType'><%= Util.getComponentTypesAsHtml(null) %></select>";
	 
		var td12 = document.createElement("td");
		td12.innerHTML = "<select name='must'><option value='false'>否</option><option value='true'>是</option></select>";
		
		otr.appendChild(td13);
		otr.appendChild(td1);
		otr.appendChild(td2);
		otr.appendChild(td3);
		otr.appendChild(td6);
		otr.appendChild(td8);
		otr.appendChild(td9);
		otr.appendChild(td10);
		otr.appendChild(td11);
		otr.appendChild(td12);
	}
	
	function deltablerow(tableId){
		var tab = document.getElementById(tableId);
		var rIndex = event.srcElement.parentElement.parentElement.rowIndex;
		tab.deleteRow(rIndex);
	}	
</script>

</head>
<body>
<form action="saveField.do" method="post">
<table cellpadding="5" cellspacing="0" border="1"  class="tb" bordercolor="#bdbcbc" id="fieldTable">
<tr>
	<td colspan="10">
		表名：<%=tableName%>&nbsp;&nbsp;
		<input type="hidden" value="<%=tableName %>" name="tableName"/>
		<input type="submit" value="提交"/>
		<input type="button" onclick="location.href='viewTable.do?name=<%=tableName%>'" value="返回"/>
	</td>
</tr>
<tr>
	<th><a href="javascript:addRow();">[+]</a></th>
	<th align="center">字段名</th>
	<th align="center">字段类型</th>
	<th align="center">字段长度</th>
	<th align="center">默认值</th>
	<th align="center">中文名称</th>
	<th align="center">简单查询</th>
	<th align="center">高级查询</th>
	<th align="center">组件类型</th>
	<th align="center">必填</th>
</tr>
<tr>
	<td><a href="#" title="删除" onclick="deltablerow('fieldTable');">[×]</a></td>
	<td><input type="text" name="name" value=""/></td>
	<td><select name="type"><%= Util.getFieldTypesAsHtml(null) %></select></td>
	<td><input type="text" name="size" size="5" value=""></td>
	<td><input type="text" name="defaultValue" size="5" value=""></td>
	<td><input type="text" name="remark" size="10"></td>
	<td>
		<select name="showQuery">
			<option value="false">否</option>
			<option value="true">是</option>
		</select>
	</td>
	<td>
		<select name="showAdvanced">
			<option value="false">否</option>
			<option value="true">是</option>
		</select>
	</td>
	<td>
		<select name="componentType">
			<%= Util.getComponentTypesAsHtml(null) %>
		</select>
	</td>
	<td>
		<select name="must">
			<option value="false">否</option>
			<option value="true">是</option>
		</select>
	</td>
</tr>
</table>
</form>
<%@ include file="db_attention.jsp"%>
</body>
</html>