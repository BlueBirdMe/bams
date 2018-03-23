<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类型明细</title>
<%
String wid = request.getParameter("wid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOACompanyResourcesService.getWareTypeByPk(<%=wid%>,setpagevalue);
	
}
function setpagevalue(data){
	if(data!=null){
		var type =data.resultList[0];
		
		DWRUtil.setValue("warename",type.oaTypeName);
		DWRUtil.setValue("wareremark",type.oaTypeText);
		if(type.premCount>0){
			dwrOACompanyResourcesService.getWareTypeRangeListBytypePk(<%=wid%>,setemployeeids);
		}else{
			document.getElementById("wareempname").innerHTML = "无限制";
		}
	}
}
	
function setemployeeids(data){
	if(data.success == true){
		var names ="";
		if(data.resultList.length>0){
			for(var i=0;i<data.resultList.length;i++){
				if(data.resultList[i].hrmEmployee!=null){
					names+=data.resultList[i].hrmEmployee.hrmEmployeeName+"&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
		}
		document.getElementById("wareempname").innerHTML = names;
	}
}
</script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;添加了新的类型，可以对知识和表格进行分组管理。</label></div>
<div class="detailtitle">类型明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">类型名称</th>
		<td id="warename" class="detailtabletd"></td>
	</tr>
	
	<tr>
		<th>有权查阅人员</th>
		<td id="wareempname" class="detailtabletd" ></td>
	</tr>
	<tr>
		<th>类型说明</th>
		<td id="wareremark" class="detailtabletd" ></td>
	</tr>
	</table>
</body>
</html>