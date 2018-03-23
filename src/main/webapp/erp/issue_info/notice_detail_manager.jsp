<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String oid = request.getParameter("oid");
    String noid = request.getParameter("noid");
%>
<script type="text/javascript"	src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<script type="text/javascript">
window.onload = function(){
   	useLoadingMassage();
	dwrOaNewsService.getNoticeByPk(<%=Integer.parseInt(oid)%>,setPageValue);
}

function setPageValue(data){
	 if(data != null){
	      if(data.resultList.length>0){
	          var notice = data.resultList[0];
	          DWRUtil.setValue("noticeName",notice.oaNotiName);
	          
	          var type = "";
	          if(notice.oaNotiType ==<%=EnumUtil.OA_NOTICE_TYPE.GENERAL.value%>){
	              type = "<%=EnumUtil.OA_NOTICE_TYPE.valueOf(EnumUtil.OA_NOTICE_TYPE.GENERAL.value)%>";
	          }else if(notice.oaNotiType ==<%=EnumUtil.OA_NOTICE_TYPE.UNEMERGENCY.value%>){
	              type = "<%=EnumUtil.OA_NOTICE_TYPE
									.valueOf(EnumUtil.OA_NOTICE_TYPE.UNEMERGENCY.value)%>";
	          }else{
	               type = "<img src='<%=contextPath%>/images/jj.png'>&nbsp;<%=EnumUtil.OA_NOTICE_TYPE.valueOf(EnumUtil.OA_NOTICE_TYPE.EMERGENCY.value)%>";
	          }
	          document.getElementById("noticeType").innerHTML = type;
	          
	          DWRUtil.setValue("noticeTime",notice.oaNotiTime);
	          DWRUtil.setValue("noticeObjEmp",notice.empLIst);
	          DWRUtil.setValue("noticeObjDep",notice.depList);
	          
	          if(notice.employee != null){
	               DWRUtil.setValue("noticeEmpName",notice.employee.hrmEmployeeName);
	          }else{
	               DWRUtil.setValue("noticeEmpName","<无>");
	          }
	          
	          var noticeStatus = "<font color='green'><%=EnumUtil.OA_ISSUEINFO_STATUS
									.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>";
	          if(notice.oaNotiStatus == <%=EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value%>){
	                noticeStatus = "<font color='red'><%=EnumUtil.OA_ISSUEINFO_STATUS
									.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>";
	          }
	          document.getElementById("noticeStatus").innerHTML = noticeStatus;
	          
	          if(notice.employee!=null){
	          	document.getElementById("noticeEmpName").innerHTML = notice.employee.hrmEmployeeName;
	          }else{
	          	document.getElementById("noticeEmpName").innerHTML ="&nbsp;";
	          }
	          document.getElementById("noticeText").innerHTML = notice.oaNotiText+"&nbsp;";
	          
	          //附件显示为下载
			  Sys.showDownload(notice.oaNotiAcce,"noticeAcce");
	      }
	 }
}
	
function sendSms(dialogId){
    var url = '<%=contextPath%>/erp/issue_info/notice_sms_send.jsp?oid="+<%=oid%>+"&noid=<%=noid%>';
    openMDITab(url);
	Sys.close(dialogId);
}
</script>
</head>
<body class="inputdetail">
	<div class="requdivdetail">
		<label>
			查看帮助:&nbsp;可通过点击附件来进行下载。
		</label>
	</div>
	<div class="detailtitle">
		通知明细
	</div>
	<table class="detailtable" align="center">
		<tr>
			<th width="15%">
				通知标题
			</th>
			<td id="noticeName" class="detailtabletd"></td>
			<td class="attachtd" rowspan="5">
				<div class="attachdiv">
					<div class="attachtitle">
						附件下载
					</div>
					<div class="attachdownload" id="noticeAcce"></div>
				</div>
			</td>
		</tr>
		<tr>
			<th>
				发布人
			</th>
			<td id="noticeEmpName" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				发布日期
			</th>
			<td id="noticeTime" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				通知类型
			</th>
			<td id="noticeType" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				通知状态
			</th>
			<td id="noticeStatus" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				通知范围(人员)
			</th>
			<td colspan="2" id="noticeObjEmp" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				通知范围(部门)
			</th>
			<td colspan="2" id="noticeObjDep" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>
				通知内容
			</th>
			<td colspan="2" id="noticeText" class="detailtabletd"></td>
		</tr>
	</table>
</body>
</html>
