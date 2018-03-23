<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志明细</title>
<%
String worklogpk =request.getParameter("worklogpk");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrWorkArrangeService.js"></script>
<script type="text/javascript">
	window.onload = function(){
    	useLoadingMassage();
		dwrWorkArrangeService.getWorklogByPk(<%=worklogpk%>,setWorkloginfo);
	}
	
	function setWorkloginfo(data){
     if(data.success == true){
 		if(data.resultList.length > 0){
 			var worklog = data.resultList[0];

 			DWRUtil.setValue("oaWorklogTitle",worklog.oaWorklogTitle);
 			DWRUtil.setValue("oaWorklogDate",worklog.oaWorklogDate);
 			DWRUtil.setValue("oaWorklogDeps",worklog.oaWorklogDepsName);
 			DWRUtil.setValue("oaWorklogEmps",worklog.oaWorklogEmpsName);
 			DWRUtil.setValue("oaWorklogType",worklog.library.libraryInfoName);
 			var range="";
 			if(worklog.oaWorklogRange == <%=EnumUtil.OA_WORKLOG_RANGE.one.value%>){
 			  range = '<%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.one.value)%>';
 			}else{
 			  range = '<%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.two.value)%>';
 			}
 			DWRUtil.setValue("oaWorklogRange",range);
 			document.getElementById("oaWorklogContent").innerHTML = worklog.oaWorklogContent;
 			
			//附件显示为下载
				if(worklog.oaWorklogAnnexid!=null && worklog.oaWorklogAnnexid.length>0){
				    Sys.showDownload(worklog.oaWorklogAnnexid,"oaWorklogAnnexid");
				}		
 		}
 	}
}

</script>
</head>
<body class="inputdetail">
<div class="requdivdetail">
	<label>
		查看帮助:&nbsp;可通过点击附件来进行下载。
	</label>
</div>
	<div class="detailtitle">日志明细</div>
		<table class="detailtable" align="center">
			<tr>
			<th width="15%">日志标题</th>
			<td id="oaWorklogTitle" class="detailtabletd"></td>
				<td class="attachtd" rowspan="5">
					<div class="attachdiv">
						<div class="attachtitle">
							附件下载
						</div>
						<div class="attachdownload" id="oaWorklogAnnexid"></div>
					</div>
				</td>
			</tr>
			<tr>
			<th>日志日期</th>
			<td id="oaWorklogDate" class="detailtabletd"></td>
			</tr>
			<tr>
			<th>日志类型</th>
			<td id="oaWorklogType" class="detailtabletd"></td>
			</tr>
			<tr>
			<th>是否共享</th>
			<td id="oaWorklogRange" class="detailtabletd"></td>
			</tr>
			<tr>
			<th></th>
			<td ></td>
			</tr>
			<tr>
			<th>共享范围(部门)</th>
			<td colspan="3" id="oaWorklogDeps" class="detailtabletd">
			</td>
			</tr>
			<tr>
			<th>共享范围(人员)</th>
			<td colspan="3" id="oaWorklogEmps" class="detailtabletd">
			</td>
			</tr>
			<tr>
			<th>日志内容</th>
			<td colspan="3" id="oaWorklogContent" class="detailtabletd">
			</td>
			</tr>
		</table>
</body>
</html>