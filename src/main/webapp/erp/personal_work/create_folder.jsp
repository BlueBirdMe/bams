<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
</script>
<title>创建文件夹</title>
<%
   String pid=request.getParameter("pid");
 %>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput('title');
	
	}
	function save(){
	var folder=new Object();
	alert(<%=pid%>);
     
	if(<%=pid%>==""|| <%=pid%>==null){
	   folder.pid="-1";
	}
	alert(folder.pid);
	folder.folderName = DWRUtil.getValue("folderName");
	}

</script>
</head>

<body>
<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>创建文件夹</legend>
	<div>
	<table class="inputtable">
	<tr>
	<th width="12%"><em>* </em>文件夹名称</th>
	<td>
	<input id="folderName" must="请输入文件夹名称" size="30">
	</td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<btn:btn onclick="save()"></btn:btn>
</td>
<td style="width: 15px;"></td>

</tr>
</table>
</body>
</html>