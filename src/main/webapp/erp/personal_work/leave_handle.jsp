<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%
	String tab = request.getParameter("tab");    
	String leaveId = request.getParameter("pk");
    String taskId = request.getParameter("taskId");
    String definitionKey = request.getParameter("definitionKey");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假办理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','请假流程办理 [ <%=EnumUtil.LEAVE_TASK.getName(definitionKey) %> ] ');
	initPageAndButton();
}

//根据流程步骤初始化办理页面
function initPageAndButton(){
	var definitionKey = '<%=definitionKey%>';

	var deptLeaderAudit = '<%=EnumUtil.LEAVE_TASK.DEPT_LEADER_AUDIT.key%>';
	var modifyApply = '<%=EnumUtil.LEAVE_TASK.SUBMIT_OR_MODIFY.key%>';
	var hrAudit = '<%=EnumUtil.LEAVE_TASK.HR_AUDIT.key%>';
	
	if(definitionKey == deptLeaderAudit){
		setLeaveDetail();
		Btn.show("btnagree");
		Btn.show("btnback");
	}else if(definitionKey == modifyApply){
		$("#leaveDetail").hide();
		$("#modifyApply").show();
		setLeaveDetailForModify();
		Btn.show("btnsubmit");
	}else if(definitionKey == hrAudit){
		setLeaveDetail();
		Btn.show("btnhragree");
		Btn.show("btnhrback");
	}
}

function setLeaveDetail(){
	dwrPersonalProcessService.getOaLeaverByPk(<%=leaveId%>,setLeave);
}

function setLeaveDetailForModify(){
	dwrPersonalProcessService.getOaLeaverByPk(<%=leaveId%>,setLeaveinfo);
}

function setLeave(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var leave = data.resultList[0];
 			DWRUtil.setValue("applyuser",leave.applyEmployee.hrmEmployeeName);
 			DWRUtil.setValue("leavetype",leave.library.libraryInfoName);
 			DWRUtil.setValue("applydata",leave.applydata);
 			DWRUtil.setValue("startdata",leave.startdata);
 			DWRUtil.setValue("enddata", leave.enddata);
 			DWRUtil.setValue("leavereason",leave.leavereason);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function setLeaveinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var leave = data.resultList[0];
 			setSelectValue("leavetypeForModify",leave.leavetype);
 			DWRUtil.setValue("startdataForModify",leave.startdata);
 			DWRUtil.setValue("enddataForModify", leave.enddata);
 			DWRUtil.setValue("leavereasonForModify",leave.leavereason);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function submit() {
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "startdatamust";
	warnArr[1] = "enddatamust";
	warnArr[2] = "leavereasonmust";
	warnInit(warnArr);//清空所有信息提示
	var applyTxt = DWRUtil.getValue("txt");
	if(applyTxt == ""){
		setMustWarn("txtMust","请输入批注信息!");
   		return;
	}

	if(DWRUtil.getValue("leavereasonForModify") == ""){
		setMustWarn("leavereasonmust","请假原因不能为空。");
		return;
	}
	if(DWRUtil.getValue("startdataForModify") == ""){
		setMustWarn("startdatamust","请输入开始时间!");
		return;
	}
	if(DWRUtil.getValue("enddataForModify") == ""){
		setMustWarn("enddatamust","请输入结束时间!");
		return;
	}
	
	dwrPersonalProcessService.completeLeaveTaskForApplyer(<%=taskId%>,applyTxt,getLeaveinfo(),callback);
}

function getLeaveinfo() {
	var oaleave = new Object();
	oaleave.primaryKey = <%=leaveId %>;
	oaleave.leavetype = DWRUtil.getValue("leavetypeForModify");
	oaleave.startdata = DWRUtil.getValue("startdataForModify");
	oaleave.enddata = DWRUtil.getValue("enddataForModify");
	oaleave.leavereason = DWRUtil.getValue("leavereasonForModify");
	return oaleave;
}

function callback(data){
	if(data.success){
		alertmsg(data,"closePage();");
	}else{
		alertmsg(data);
	}
}

function closePage(){
	closeMDITab(<%=tab%>);
}

function leaderCheck(isPass){
	var deptLeaderTxt = DWRUtil.getValue("txt");
	if(deptLeaderTxt == ""){
		setMustWarn("txtMust","请输入批注信息!");
   		return;
	}else{
		var warnArr = new Array();
		warnArr[0] = "txtMust";
		warnInit(warnArr);
		dwrPersonalProcessService.completeLeaveTaskForDeptLeader(<%=taskId%>,isPass,deptLeaderTxt,callback);
	}
}

function hrCheck(isPass){
	var hrTxt = DWRUtil.getValue("txt");
	if(hrTxt == ""){
		setMustWarn("txtMust","请输入批注信息!");
   		return;
	}else{
		var warnArr = new Array();
		warnArr[0] = "txtMust";
		warnInit(warnArr);
		dwrPersonalProcessService.completeLeaveTaskForHr(<%=taskId%>,isPass,hrTxt,callback);
	}
}

function callback(data){
	if(data.success){
		alertmsg(data,"closePage();");
	}else{
		alertmsg(data);
	}
}

</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	
	<div id="leaveDetail">
    <div class="formTitle">请假详情</div>
	    <table class="detailtable" style="width:90%;">
			<tr>
				<th>请假人</th>
				<td id="applyuser" class="detailtabletd" ></td>
				<th>请假类型</th>
				<td id="leavetype" class="detailtabletd" ></td>
			</tr>
			<tr>
				<th>申请时间</th>
				<td id="applydata" class="detailtabletd"></td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th>开始时间</th>
				<td id="startdata" class="detailtabletd"></td>
				<th>结束时间</th>
				<td id="enddata" class="detailtabletd" ></td>
			</tr>
			<tr>
				<th>请假事由</th>
				<td colspan="3"  id="leavereason" class="detailtabletd"></td>
			</tr>
		</table>
	</div>	
		
	<!-- 调整申请 -->
	<div id="modifyApply" style="display: none">
	<div class="formTitle">调整申请</div>
	<table class="inputtable">
		<tr>
			<th>请假类型</th>
			<td>
			<select id="leavetypeForModify" >
				<%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"23") %>
			</select>
			</td>
		</tr>
	
		<tr>
			<th><em>*</em>&nbsp;请假时间</th>
			<td><input id="startdataForModify" type="text" readonly="readonly" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'enddataForModify\')}'})">
			&nbsp;至
			<input id="enddataForModify" type="text" readonly="readonly" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startdataForModify\')}'})">
			&nbsp;&nbsp;&nbsp;&nbsp;<label id="startdatamust"></label><label id="enddatamust"></label></td>
		</tr>
		
		<tr>	
			<th><em>*</em>&nbsp;请假事由</th>
			<td colspan="3"><textarea style="width:50%;" id ="leavereasonForModify"></textarea></td>
		</tr>
		<tr><th></th><td><label id="leavereasonmust"></label></td></tr>
	</table>
	</div>
	
	<!-- 批注信息区域 -->
	<div>
		<div class="formTitle">批注信息</div>	
		<table class="inputtable">
		<tr>
			<th></th>
			<td colspan="3">
				<textarea style="width:50%;" id='txt'></textarea>
			</td>
		</tr>
		<tr>
			<th></th>
			<td colspan="3"><label id='txtMust'></label></td>
		</tr>
		</table>
	</div>
</div>
	
<table align="center">
	<tr>
		<td>
			<input type="button" onclick="submit();" value="提交" id="btnsubmit" style="display:none;"/>
			<input type="button" onclick="leaderCheck('true');" value="同意" id="btnagree" style="display:none;"/>
			<input type="button" onclick="hrCheck('true');" value="同意 " id="btnhragree" style="display:none;"/>
			<input type="button" onclick="leaderCheck('false');" value="驳回 " id="btnback" style="display:none;"/>
			<input type="button" onclick="hrCheck('false');" value="驳回" id="btnhrback" style="display:none;"/>
			<input type="button" onclick="closePage();" value="关闭"/>
		</td>
	</tr>
</table>
</body>
</html>