<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建流程模型</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<%
    String groupId =request.getParameter("id");
%>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处创建流程模型！');
	saveOredit();
    //第一个输入框获取焦点
    document.getElementById("groupId").focus();
}

function  saveOredit(){
     if('<%=groupId%>' != 'null'){
		var groupId = '<%=groupId%>';
		dwrApproveProcessService.getProcessGroupById(groupId,setProcessGroup);
		var btn = document.getElementById("backToList");
	    btn.style.display = "none";
	    
	    //编辑状态 用户组ID不能修改
	    document.getElementById("groupId").readOnly = "true";
	    
	 }else{
	    Btn.hidden("btncancel");
	 }
}

function setProcessGroup(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var group = data.resultList[0];
 			DWRUtil.setValue("groupId",group.id);
 			DWRUtil.setValue("name",group.name);
 			DWRUtil.setValue("type", group.type);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "idMust";
	warnArr[1] = "nameMust";
	warnArr[2] = "typeMust";
	//清空所有信息提示
	warnInit(warnArr);
     var bl = validvalue('helpTitle');
     if(bl){
          Btn.close();
          if('<%=groupId%>' != 'null'){
          	dwrApproveProcessService.updateProcessGroup(getGroupinfo(),saveCallback);
          }else{
          	dwrApproveProcessService.saveProcessGroup(getGroupinfo(),saveCallback);
          }
          
	 }
}

function getGroupinfo(){
	var group = new Object();
	group.id = DWRUtil.getValue("groupId");
	group.name = DWRUtil.getValue("name");
	group.type = DWRUtil.getValue("type");
	return group;
}

function saveCallback(data){
    Btn.open();
    if(data.success){
    	if('<%=groupId%>' != 'null'){	
			alertmsg(data,"reset();");
		}else{
			confirmmsgAndTitle("添加用户组成功！是否想继续添加用户组？","reset();","继续添加","closePage();","关闭页面");
		}
		
	}else{
		alertmsg(data);
	}
}

function returnload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function reset(){
	if('<%=groupId%>' != 'null'){
		returnload();
	}else{
	 	DWRUtil.setValue("groupId","");
		DWRUtil.setValue("name","");
		DWRUtil.setValue("type","");
	 	
	 	document.getElementById("groupId").focus();
	 	refreshMDITab(<%=request.getParameter("tab")%>);
 	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">创建/编辑用户组</div>
    	<form method="post" id="frmname" name="frmname" target="_blank">
	    <table class="inputtable" border="0">
		    <tr>
				<th>ID</th>
				<td><input type="text" id="groupId" must="ID不能为空！" formust="idMust"></td>
				<td><label id="idMust"></label></td>
			</tr>
			<tr>
				<th>用户组名称</th>
				<td><input type="text" id="name" must="用户组名称不能为空！" formust="nameMust"></td>
				<td><label id="nameMust"></label></td>
			</tr>
			<tr>
			    <th>用户组类型</th>
				<td><input type="text" id="type" must="用户组类型不能为空！" formust="typeMust"></td>
				<td><label id="typeMust"></label></td>
			</tr>
	    </table>
	    </form>
	</div>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存部门信息" /></td>
		<td style="width: 20px;"></td>
		<td id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
		<td id="btncancel"><btn:btn onclick="window.parent.MoveDiv.close()" value="取 消 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>