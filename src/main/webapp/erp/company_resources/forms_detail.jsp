<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表格明细</title>
<%
String fid =request.getParameter("fid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
	window.onload = function(){
	    useLoadingMassage();
		dwrOACompanyResourcesService.getFormsAndObjByPk(<%=fid%>,setpagevalue);
	}
	function setpagevalue(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("formsname",tmp.oaFormName);
				DWRUtil.setValue("formstype",tmp.wareType.oaTypeName);
				var empname = "无";
				if(tmp.formEmployee!=null){
					empname=tmp.formEmployee.hrmEmployeeName
				}
				DWRUtil.setValue("formsemp",empname);
				DWRUtil.setValue("formstime",tmp.oaFormTime);
				document.getElementById("formstext").innerHTML = tmp.oaFormText;
				//附件显示为下载
				Sys.showDownload(tmp.oaFormAcce,"formsattachs");
			}
		}
	}
</script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
	<div class="detailtitle">表格明细</div>

	<table class="detailtable" align="center">
	<tr>
	<th width="15%">表格名称</th>
	<td id="formsname" class="detailtabletd" width="35%"></td>
	<td class="attachtd" rowspan="4"colspan="2">
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="formsattachs"></div>				
			</div>
		</td>
	</tr>
	<tr>
	<th>表格类型</th>
	<td id="formstype" class="detailtabletd" ></td>
	</tr>
	<tr>
		<th>创建人</th>
	<td id="formsemp" class="detailtabletd" ></td>
	</tr>
	<tr>
	<th>创建时间</th>
	<td id="formstime" class="detailtabletd" ></td>
	</tr>
	<tr>
	<th>表格内容</th>
	<td colspan="3" id="formstext" class="detailtabletd">
	</td>
	</tr>
	</table>
</body>
</html>