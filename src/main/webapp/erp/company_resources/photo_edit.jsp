<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相片明细</title>
<%
String pid =request.getParameter("pid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
   useLoadingMassage();
	initInput('title');
	dwrOACompanyResourcesService.getPhotoByPk(<%=pid%>,setpagevalue);
}
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("photoname",tmp.photoName);
			DWRUtil.setValue("photosdesc",tmp.photoDesc);
		}
	}
}

	var myfrmname =null;
function savephoto(){
	var bl = validvalue('title');
	if(bl){
		var ph =new Object();
		ph.primaryKey =<%=pid%>;
		ph.photoName = DWRUtil.getValue("photoname");
		ph.photoDesc = DWRUtil.getValue("photosdesc");
		dwrOACompanyResourcesService.updatePhoto(ph,savecallback);
		Btn.close();
	}
}

function savecallback(data){
 	Btn.open();
	alertmsg(data,"reclose()");
}

function reclose(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
		<div class="formTitle">编辑相片信息</div>
	<div>
	<table class="inputtable">
		<tr>
			<th width="15%"><em>*</em>相片名称</th>
			<td>
			<input type="text" id="photoname" must="请输入相片名称" style="width: 90%;">
			</td>
		</tr>
		<tr>
			<th>相片描述</th>
			<td>
			<textarea id="photosdesc"></textarea>
			</td>
		</tr>
	</table>
	</div>
</div>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<btn:btn onclick="savephoto();"></btn:btn>
		</td>
		<td style="width: 15px;"></td>
		<td id="cancelbtn">
		<btn:cancel onclick="window.parent.MoveDiv.close()"></btn:cancel>
		</td>
	</tr>
	</table>
</body>
</html>