<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出差明细</title>
<%
String pk = request.getParameter("pk");
String processInstanceId = request.getParameter("processInstanceId");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
</head>
<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;在此可以查看出差申请明细和审批流程。</label></div>
	<div class="detailtitle">出差明细</div>
	<table class="detailtable" align="center" style="width: 90%;">
		<tr>
			<th width="15%">申请人</th>
			<td id="trsvelAppalyuser" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>出差地点</th>
			<td id="trsvelArea" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>开始时间</th>
			<td id="trsvelBegindata" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>结束时间</th>
			<td id="trsvelEnddata" class="detailtabletd"></td>
		</tr>
		<tr>
		<th>出差事由</th>
		<td id="trsvelCause" class="detailtabletd"></td>
		</tr>
	</table>
	<br/>
	<div class="detailtitle">审批流程</div>
	<jsp:include page="flow_detail.jsp" flush="false">
		<jsp:param name="id" value="<%=processInstanceId %>"/> 
	</jsp:include>

<script type="text/javascript">
dwrPersonalProcessService.getOaTrsvelByPk(<%=pk %>, getoaTrsvel);

function getoaTrsvel(data) {
	if (data != null) {
		if (data.resultList.length > 0) {
			var tmp = data.resultList[0];
			var appname="";
			if(tmp.applyEmployee!=null){
				appname =tmp.applyEmployee.hrmEmployeeName;
			}
			DWRUtil.setValue("trsvelAppalyuser", appname);
			DWRUtil.setValue("trsvelBegindata", tmp.trsvelBegindata);
			DWRUtil.setValue("trsvelEnddata", tmp.trsvelEnddata);
			DWRUtil.setValue("trsvelArea", tmp.trsvelArea);
			DWRUtil.setValue("trsvelCause", tmp.trsvelCause);
		}
	}
}
</script>
</body>
</html>