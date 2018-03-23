<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="db_common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据库表</title>
</head>
<body>
${db.name}&nbsp;&nbsp;${db.version }<br/>
${db.driverName }&nbsp;&nbsp;${db.driverVersion }
<table>
<tr>
<td>

<div style="width:500px;height:428px;overflow:auto;">
<table width="100%" cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc">
<tr>
  <td colspan="4" align="center">
  	项目共 ${fn:length(tables)}张表
  </td>
</tr>
<tr>
  <th align="center">表名</th>
  <th align="center">描述</th>
  <th align="center">记录数</th>
  <th align="center">操作</th>
</tr>
<c:forEach items="${tables}" var="t">  
<tr>
	<td><a href="viewTable.do?name=${t.name}">${t.name}</a></td>
	<td>${t.comment}</td>
	<td align="center">${t.rows}</td>
	<td align="center">
		<a href="javascript:if(confirm('确定要删除吗？')) {location.href='deleteTable.do?name=${t.name}'}">
			×
		</a>
	</td>
</tr>
</c:forEach>
</table>
</div>
</td>
<td valign="top" style="padding-left:20px;">

<form action="createTable.do" method="post">
	<table cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc">
		<tr>
		  <td align="center" colspan="2">
		  	创建表
		  </td>
		</tr>
		<tr>
		  <td>表名</td>
		  <td>
		  <input type="text" name="name"></input>
		  </td>
		</tr>
		<tr>
		  <td>描述</td>
		  <td>
		  <input type="text" name="comment"></input>
		  </td>
		</tr>
		<tr>
		  <td colspan="2"><input type="submit" value="创建"></input></td>
		</tr>
	</table>
</form>


<form action="updateTable.do" method="post">
	<table cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc" style="margin-top:20px;">
		<tr>
		  <td align="center" colspan="2">
		  	修改表
		  </td>
		</tr>
		<tr>
		  <td>旧表名</td>
		  <td>
		  <input type="text" name="oldName"></input>
		  </td>
		</tr>
		<tr>
		  <td>新表名</td>
		  <td>
		  <input type="text" name="name"></input>
		  </td>
		</tr>
		<tr>
		  <td>新表描述</td>
		  <td>
		  <input type="text" name="comment"></input>
		  </td>
		</tr>
		<tr>
		  <td colspan="2"><input type="submit" value="修改"></input></td>
		</tr>
	</table>
</form>


</tr>
</table>
</body>
</html>