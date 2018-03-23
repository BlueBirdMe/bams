<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>期刊明细</title>
<%
String jid =request.getParameter("jid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOACompanyResourcesService.getJournalsMangerObjByPk(<%=jid%>,setpagevalue);
}
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("jourtype",tmp.journalsType.journalsTypeName);
			DWRUtil.setValue("jourpress",tmp.journalsType.journalsTypePress);
			
			DWRUtil.setValue("jourcount",tmp.journalsCount);
			DWRUtil.setValue("jourcode",tmp.journalsCode);
			document.getElementById("jourtext").innerHTML = tmp.journalsContext;
			//图片显示
			var face = document.getElementById("jourface");
			face.src+="&imgId="+tmp.journalsFace;
			//附件显示为下载
			Sys.showDownload(tmp.journalsAffix,"jourattachs");
		}
	}
}
</script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
<div class="detailtitle">期刊明细</div>
	<table class="detailtable" align="center">
	<tr>
		<td width="15%" rowspan="4">
		<file:imgshow  id="jourface"></file:imgshow>
		</td>
		<th>名&nbsp;称</th>
		<td id="jourtype" class="detailtabletd"></td>
			<td class="attachtd" rowspan="4">
				<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="jourattachs"></div>				
			</div>
		</td>
	</tr>
	<tr>
		<th>期&nbsp;数</th>
		<td id="jourcount"  class="detailtabletd"></td>
	</tr>
	<tr>
		<th>期刊号</th>
		<td id="jourcode"  class="detailtabletd"></td>
	</tr>
	<tr>
		<th>出版社</th>
		<td id="jourpress" class="detailtabletd"></td>
	</tr>
	<tr>
	<td></td>
		<th>期刊描述</th>
		<td  id="jourtext" class="detailtabletd" colspan="2">
		</td>
		<td></td>
	</tr>
	</table>
</body>
</html>