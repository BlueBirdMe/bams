<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识明细</title>
<%
String wid =request.getParameter("wid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOACompanyResourcesService.getWarehouseAndObjByPk(<%=wid%>,setpagevalue);
}
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("warehousename",tmp.oaWareName);
			DWRUtil.setValue("warehousekeyword",tmp.oaKeyword);
			DWRUtil.setValue("waretype",tmp.wareType.oaTypeName);
			var empname = "无";
			if(tmp.oaWareEmployee!=null){
				empname=tmp.oaWareEmployee.hrmEmployeeName
			}
			DWRUtil.setValue("warehouseemp",empname);
			DWRUtil.setValue("warehousetime",tmp.oaWareTime);
			document.getElementById("warehousetext").innerHTML = tmp.oaWareText;
			//附件显示为下载
			Sys.showDownload(tmp.oaWareAcce,"warehouseattachs");
		}
	}
}
function edit(){
    Sys.close();
    window.parent.mainframe.location ="<%=contextPath%>/erp/company_resources/ware_add.jsp";
}
</script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
<div class="detailtitle">知识明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">标&nbsp;题</th>
		<td  id="warehousename" class="detailtabletd" ></td>
		<td class="attachtd" rowspan="5" >
			<div class="attachdiv" >
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="warehouseattachs"></div>				
			</div>
		</td>
	</tr>
	<tr>
		<th>类&nbsp;型</th>
		<td id="waretype"class="detailtabletd"></td>
	</tr>
	<tr>
		<th>发布人</th>
		<td id="warehouseemp"class="detailtabletd" ></td>
	</tr>

	<tr>
		<th>关键字</th>
		<td id="warehousekeyword"class="detailtabletd"></td>
	</tr>
	<tr>
		<th>发布时间</th>
		<td  id="warehousetime"class="detailtabletd"></td>
	</tr>
	<tr>
		<th>内&nbsp;容</th>
		<td colspan="2" id="warehousetext"class="detailtabletd">
		</td>
	
	</tr>

	</table>
</body>
</html>