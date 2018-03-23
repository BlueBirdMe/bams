<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位明细查看</title>
<%
    String pid = request.getParameter("pid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrHrmEmployeeService.getWorkareaByPk(<%=pid%>,setPageValue);
}
	 
function setPageValue(data){
	if(data != null && data.resultList.length>0){
		var tmp = data.resultList[0];
		DWRUtil.setValue("hrmPostName",tmp.hrmAreaName);
		DWRUtil.setValue("hrmPostEngname",tmp.hrmAreaEngname);
		DWRUtil.setValue("hrmPostUpName",tmp.hrmAreaDesc);
  	}
}
</script>
</head>
<body class="inputdetail">
    <div class="requdivdetail">
		<label>
			查看帮助:&nbsp;工作地区相关信息！
		</label>
	</div>
	<div class="detailtitle">
		工作地区明细
	</div>
	<table class="detailtable">
		<tr>
			<th >地区名称</th>
			<td id="hrmPostName" class="detailtabletd"></td>
			<td class="attachtd" rowspan="5">	
			</td>
		</tr>
		<tr>
			<th >英文名称</th>
			<td id="hrmPostEngname" class="detailtabletd"></td>
		</tr>
		<tr>
			<th >备&nbsp;&nbsp;注</th>
			<td id="hrmPostUpName" class="detailtabletd" colspan="2"></td>
		</tr>
	</table>
<br/>
</body>
</html>
