<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>纪要明细</title>
<%
String sid =request.getParameter("sid");
String userId = UtilTool.getEmployeeId(request);
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		dwrOfficeResourcesService.getSummaryByPk(<%=sid%>,setpagevalue);
	}
	function setpagevalue(data){
		if(data!=null){
			if(data.resultList.length>0){
				var summary = data.resultList[0];
				DWRUtil.setValue("oaSummaryName",summary.oaSummaryName);
				DWRUtil.setValue("oaSummaryDate",summary.oaSummaryDate);
				DWRUtil.setValue("oaSummaryReader",summary.summaryReaderName);
				DWRUtil.setValue("oaSummaryNeirong",summary.oaSummaryNeirong);
			}
		}
		//附件显示为下载
		Sys.showDownload(summary.oaSummaryContent,"oaSummaryContent");
	}
</script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;查看会议召开的详细信息以及会议纪要的明细等。</label></div>
      <div class="detailtitle">纪要明细</div>
		<table class="detailtable" align="center">
			<tr>
				<th width="15%">会议名称</th>
				<td id="oaSummaryName" class="detailtabletd"></td>
				<td class="attachtd" rowspan="3">
				<div class="attachdiv">
					<div class="attachtitle"></div>
					<div class="attachdownload" id="oaSummaryContent"></div>
				</div>
				</td>
			</tr>
			<tr>
				<th>录入时间</th>
				<td id="oaSummaryDate" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>指定读者</th>
				<td id="oaSummaryReader" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>纪要内容</th>
			<td id="oaSummaryNeirong" class="detailtabletd" colspan="2">
			</td>
			</tr>
		</table>
</body>
</html>