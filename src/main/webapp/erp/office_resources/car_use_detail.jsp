<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆申请明细查看</title>
<%
    String cid = request.getParameter("cid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
   
	  dwrOfficeResourcesService.getCaruseByPk(<%=cid%>,setCaruseinfo);
}
    function setCaruseinfo(data){
	  if(data.success&&data.resultList.length > 0){
 		 var caruse = data.resultList[0];
 		 DWRUtil.setValue("car",caruse.oaCar.oaCarName);
 		 DWRUtil.setValue("applyBegindate",caruse.applyBegindate);
	     DWRUtil.setValue("applyEnddate",caruse.applyEnddate);
	     DWRUtil.setValue("applyNum",caruse.applyNum);
	     DWRUtil.setValue("applyPhone",caruse.applyPhone);
	     DWRUtil.setValue("applyTask",caruse.applyTask);
	     DWRUtil.setValue("applyUser",caruse.applyEmployee.hrmEmployeeName);
	   
 	}else{
 		alertmsg(data.message);
 	}
}
</script>
</head>
<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;查看被使用车辆的详细信息。</label></div>
	<div class="detailtitle">车辆使用明细</div>
	<table class="detailtable" align="center" style="width: 90%">
	<tr>
		<th width="15%">车辆名称</th>
		<td id="car" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">用&nbsp;车&nbsp;人</th>
	<td id="applyUser" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">起始时间</th>
	<td id="applyBegindate" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">结束时间</th>
	<td id="applyEnddate" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">用车人数</th>
	<td id="applyNum" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">联系电话
	</th>
	<td id="applyPhone" class="detailtabletd" colspan="3"></td>
	</tr>
	<tr>
	<th>出车事由</th>
	<td colspan="3"  id="applyTask" class="detailtabletd">
	</td>
	</tr>
	</table>
</body>
</html>
