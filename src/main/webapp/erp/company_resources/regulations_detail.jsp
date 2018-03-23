<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String rid =request.getParameter("rid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOACompanyResourcesService.getOaRegulationsAndObj(<%=rid%>,setpagevalue);
}
var fckvalue="";
var fck;
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("regultitle",tmp.oaRegulationsTitle);
			DWRUtil.setValue("regultype",tmp.regulationsType.libraryInfoName);
			DWRUtil.setValue("regulstarttime",tmp.regulatStratTime);
			DWRUtil.setValue("regulcreatetime",tmp.oaRegulationsTime);
			if(tmp.regulationsEmployee!=null){
				DWRUtil.setValue("regulcreateemp",tmp.regulationsEmployee.hrmEmployeeName);
			}
			document.getElementById("regultext").innerHTML = tmp.regulatContext;
			//附件
			Sys.showDownload(tmp.oaRegulationsAttachs,"regulfiles");
		}
	}
}
</script>
<title>规章制度详细</title>
</head>
<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
		<div class="detailtitle">规章制度详细信息</div>
			<table class="detailtable" align="center">
				<tr>
					<th width="15%">规章标题</th>
					<td id="regultitle" class="detailtabletd"></td>
					<td class="attachtd" rowspan="5">
						<div class="attachdiv">
							<div class="attachtitle">附件下载</div>
							<div class="attachdownload" id="regulfiles"></div>				
						</div>
					</td>
				</tr>
				
				<tr>
					<th>规章类型</th>
					<td id="regultype" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>创建人</th>
					<td id="regulcreateemp" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>生效日期</th>
					<td id="regulstarttime" class="detailtabletd"></td>
				</tr>
				
				<tr>
					<th>创建日期</th>
					<td id="regulcreatetime" class="detailtabletd"></td>
				</tr>
			
				<tr>
					<th>规章内容</th>
					<td colspan="3" id="regultext" class="detailtabletd"></td>
				</tr>
			</table>
</body>
</html>