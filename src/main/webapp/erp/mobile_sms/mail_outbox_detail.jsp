<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发件明细</title>
<%
String mailoutid =request.getParameter("mailoutid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		dwrMailService.getMailOutboxByPk(<%=mailoutid%>,setMailOutboxinfo);
	}
	
	function setMailOutboxinfo(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("mailempPk",tmp.primaryKey);
				DWRUtil.setValue("oaMailSendEmpNames",tmp.oaMailSendEmpNames);
				DWRUtil.setValue("oaMailSendEmpCSNames",tmp.oaMailSendEmpCSNames);
				DWRUtil.setValue("oaMailSendEmpMSNames",tmp.oaMailSendEmpMSNames);
				DWRUtil.setValue("oaMailSendTime",tmp.oaMailSendTime);
				DWRUtil.setValue("oaMailSendTitle",tmp.oaMailSendTitle);
				document.getElementById("oaMailSendContent").innerHTML = tmp.oaMailSendContent;
			   //附件显示为下载
				Sys.showDownload(tmp.oaMailSendAffix,"formsattachs");
			}
		}
	}
	
	
function reback(index,dialogId){
  var mailempPk = document.getElementById("mailempPk").value;
  var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype="+index+"&backtype=2&mailempPk="+mailempPk+"";
  openMDITab(url);
  Sys.close(dialogId);
}

function returnload(){
		Sys.load("<%=contextPath%>/erp/mobile_sms/mail_outbox.jsp",'mail');
	}
	
</script>
</head>
<body class="inputdetail">
<input type="hidden" id="mailempPk">
<div class="requdivdetail">
			<label>
				查看帮助:&nbsp;可通过点击附件来进行下载。
			</label>
  </div>
  <div class="detailtitle">邮件明细</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">收件人</th>
	<td id="oaMailSendEmpNames" class="detailtabletd"></td>
	</tr>
	<tr>
		<th >
			邮件主题
		</th>
		<td id="oaMailSendTitle" class="detailtabletd"></td>
		<td class="attachtd" rowspan="4">
			<div class="attachdiv">
				<div class="attachtitle">
					附件下载
				</div>
				<div class="attachdownload" id="formsattachs"></div>
			</div>
		</td>
	</tr>
	
	<tr>
	  <th>邮件抄送</th>
	  <td id="oaMailSendEmpCSNames" class="detailtabletd"></td>
	</tr>
	<tr>
	  <th>邮件密送</th>
	  <td id="oaMailSendEmpMSNames" class="detailtabletd"></td>
	</tr>
	<tr>
	   <th>发送时间</th>
	   <td id="oaMailSendTime" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>邮件内容</th>
	<td colspan="3" id="oaMailSendContent" class="detailtabletd">
	</td>
	</tr>
	</table>
</body>
</html>