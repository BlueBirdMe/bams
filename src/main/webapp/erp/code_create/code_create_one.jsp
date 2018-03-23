<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="com.pinhuba.common.code.bean.*" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>POJO和DAO</title>
<script type="text/javascript">
window.onload = function(){
	if('${MsgSession}'!=''){alertmsg('${MsgSession}');}
	useLoadingMassage();
	initInput('helpTitle','您可以在此处生成实体类及对应DAO、配置文件等！');
			
    //第一个输入框获取焦点
    document.getElementById("tables").focus();
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "tablesMust";
	//清空所有信息提示
	warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
    	var obj = document.getElementById("createForm");
     	obj.action = "createPojoAndDao.do";
		obj.submit();
	}
}

function closePage(){
	closeMDITab();
}

function chooseTable(){
	$("#tables").val(getCheckboxValueByName("tableName"));
}

</script>
</head>
<body class="inputcls">
<form id="createForm" method="post">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">请选择数据库表</div>
    <table class="inputtable" style="margin-left:40px;">
		 <tr>
			<td>
				<div style="width:400px;height:325px;overflow:auto;margin-bottom:10px;">
				<table width="100%" cellpadding="5" cellspacing="0" border="1" style="border-collapse: collapse;">
				<c:forEach items="${tableList}" var="t"> 
				<tr>
					<td align="center">
						<input type="checkbox" onchange="chooseTable()" name="tableName" value="${t.name}"/>
					</td>
					<td>${t.name}</td>
					<td>${t.comment}</td>
				</tr>
				</c:forEach>
				</table>
				
				</div>
				<input id="tables" name="tables" readonly="readonly" style="width:400px;" value="${tables}" must="请选择要生成的表" formust="tablesMust"></input>
				<label id="tablesMust"></label>
			</td>
		 </tr>
    </table>
</div>
</form>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value=" 生 成 "/></td>
		<td style="width: 20px;"></td>
		<td id="btncancel"><btn:btn onclick="closePage()" value=" 关 闭 " title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>