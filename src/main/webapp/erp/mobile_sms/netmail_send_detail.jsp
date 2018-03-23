<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发件及草稿邮件明细</title>
<%
String netmailid =request.getParameter("mid");
String setid = request.getParameter("sid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrMailService.getNetMailSendDetailByIdDetail(<%=netmailid%>,setSendBox);
}

function setSendBox(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("mailfrom",tmp.oaNetmailSetFrom);
			var repsign="";
			if(tmp.oaNetmailReceipt == <%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
				repsign = "<%=EnumUtil.OA_MAIL_RECEIPT.valueOf(EnumUtil.OA_MAIL_RECEIPT.ONE.value)%>";
			}else{
				repsign = "<%=EnumUtil.OA_MAIL_RECEIPT.valueOf(EnumUtil.OA_MAIL_RECEIPT.TWO.value)%>";
			}
			DWRUtil.setValue("mailrepsign",repsign);
			DWRUtil.setValue("mailset",tmp.oaNetmailSendTime);
			DWRUtil.setValue("mailsendtime",tmp.oaNetmailSendTime);
			DWRUtil.setValue("mailtitle",tmp.oaNetmailSendTitle);
			var isurgent="";
			var title="";
			if(tmp.oaNetmailSendIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.one.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" /><img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
			}else if(tmp.oaNetmailSendIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.two.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" />&nbsp;"+title;
			}else if(tmp.oaNetmailSendIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.three.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
			}else{
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.four.value)%>';
			  isurgent = title;
			}
			document.getElementById("mailIsurgent").innerHTML = isurgent;
			DWRUtil.setValue("mailsendadder",tmp.oaNetmailSendAdders);
			Sys.showEmpNames(tmp.oaNetmailSendEmpids,"mailsendemp");
			
			document.getElementById("mailcontent").innerHTML = tmp.oaNetmailSendContent;
		   //附件显示为下载
			Sys.showDownload(tmp.oaNetmailSendAffix,"mailaffix");
		}
	}
}
function sendsign(dialogId){
  var url = '<%=contextPath%>/erp/mobile_sms/netmail_send.jsp?mid=<%=netmailid%>&type=send';
  openMDITab(url);
  Sys.close(dialogId);
}
</script>
</head>
<body class="inputdetail">
<input type="hidden" id="mailPk">
<div class="requdivdetail">
			<label>
				查看帮助:&nbsp;可通过点击附件来进行下载。
			</label>
		</div>
		<div class="detailtitle">
			发件邮件明细
		</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">发件邮件</th>
	<td id="mailfrom" class="detailtabletd"></td>
	<td class="attachtd" rowspan="4">
		<div class="attachdiv">
			<div class="attachtitle">邮件附件</div>
			<div class="attachdownload" id="mailaffix"></div>
		</div>
	</td>
	</tr>
	<tr>
	  <th>重要程度</th>
	  <td id="mailIsurgent" class="detailtabletd"></td>
	</tr>
	<tr>
	 	<th>阅读回执</th>
	  	<td id="mailrepsign" class="detailtabletd"></td>
	</tr>
	<tr>
	  <th>发送(保存)时间</th>
	  <td id="mailsendtime" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>邮件主题</th>
		<td colspan="3" id="mailtitle" class="detailtabletd"></td>
	</tr>
	<tr>
	   <th>外部收件人</th>
	   <td colspan="3" id="mailsendadder" class="detailtabletd"></td>
	</tr>
	<tr>
	   <th>内部收件人</th>
	   <td colspan="3" id="mailsendemp" class="detailtabletd"></td>
	</tr>
	<tr>
	   <th>邮件内容</th>
	   <td colspan="3" id="mailcontent" class="detailtabletd"></td>
	</tr>
	</table>
</body>
</html>