<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增类型</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<% String id=request.getParameter("fid"); %>
<script type="text/javascript">
window.onload = function() {
	useLoadingMassage();
	initInput('title', "添加知识类型,人员不填写,表示全部人员都可以查看");
	document.getElementById("warename").focus();
	if ( <%=id %>!=null) {
		dwrOACompanyResourcesService.getWareTypeByPk( <%=id %>, setpagevalue);
	}
}

function save() {
	var bl = validvalue('title');
	if (bl) {
		var empid = DWRUtil.getValue("wareempid");
		var ware = getWareType();
		dwrOACompanyResourcesService.saveWareType(empid, ware, savecallback);
		Btn.close();
	}
}
function savecallback(data) {
	Btn.open();
	if ( <%=id %>!=null) {
		alertmsg(data, "reclose()");
	} else {
		if (data.success) {
			confirmmsgAndTitle("添加知识类型成功！是否想继续添加?","sevent();","继续添加", "closePage();","关闭页面");
		} else {
			alertmsg(data);
		}
	}
}
function reclose() {
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function sevent() {
	DWRUtil.setValue("warename", "");
	DWRUtil.setValue("wareremark", "");
	DWRUtil.setValue("wareempname", "");
	DWRUtil.setValue("wareempid", "");
	document.getElementById("warename").focus();
	refreshMDITab(<%=request.getParameter("tab")%>);
}
function getWareType() {
	var ware = new Object();
	if ( <%=id %>!=null) {
		ware.primaryKey = <%=id %>;
	}
	ware.oaTypeName = DWRUtil.getValue("warename");
	ware.oaTypeText = DWRUtil.getValue("wareremark");
	ware.formsorware = <%=EnumUtil.OA_TYPE.WARW.value %>;
	return ware;
}
function setpagevalue(data) {
	if (data != null) {
		var type = data.resultList[0];
		DWRUtil.setValue("typeid", type.primaryKey);
		DWRUtil.setValue("warename", type.oaTypeName);
		DWRUtil.setValue("wareremark", type.oaTypeText);
	}
}
function getEmployeeIds() {
	if(<%=id%> == null){
		var box = SEL.getEmployeeIds("check", "wareempname", "wareempid");
	}else{
		var box = SEL.getEmployeeIds("check", "wareempname", "wareempid","processloadfrm");
	}
	box.show();
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

</script>
</head>
<body class="inputcls">
	<input type="hidden" id="typeid">
	<div class="formDetail">
		<div class="requdiv"><label id="title"></label></div>
			<div class="formTitle">新增/编辑类型</div>

		<div>
		<table class="inputtable">
		<tr>
		<th  width="10%"><em>* </em>类型名称</th>
			<td><input type="text" id="warename" must="类型名称不能为空" formust="warenamemust" maxlength="25" style="width: 40%">
			<label id="warenamemust"></label>
			</td>
		</tr>
		<tr>
			<th>查看人员</th>
			<td>
				<textarea id="wareempname" style="color: #666" readonly="readonly" linkclear="wareempid" 
				onclick="getEmployeeIds();" title="点击选择人员[不填写表示全部]"></textarea>
				<br/>
				<input type="hidden" id="wareempid">
			</td>
		</tr>
		<tr>
			<th>类型说明</th>
			<td>
			<textarea id="wareremark" style="height: 150"></textarea>
			</td>
		</tr>
		</table>
	</div>
		<br/>
		<br/>
</div>
	<br/>
		<table align="center">
		<tr>
	   		<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
	    	<td style="width: 10px;"></td>
			<td>
			<%if (id == null){ %>
			<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%}else{ %>
			<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%} %>
			</td>
		</tr>
		</table>
</body>
</html>