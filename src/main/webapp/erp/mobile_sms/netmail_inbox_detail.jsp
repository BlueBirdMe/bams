<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件明细</title>
<%
String netmailid =request.getParameter("mid");
String setid = request.getParameter("sid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrMailService.getNetmailDetailById(<%=netmailid%>,setMialInboxinfo);
}

function setMialInboxinfo(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("mailempPk",tmp.primaryKey);
			DWRUtil.setValue("mailPerson",tmp.oaNetmailInboxSender+"<"+tmp.oaNetmailSetFrom+">");
			DWRUtil.setValue("mailset",tmp.oaNetmailSetId);
			DWRUtil.setValue("mailtime",tmp.oaNetmailInboxTime);
			DWRUtil.setValue("mailTitle",tmp.oaNetmailInboxTitle);
			var isurgent="";
			var title="";
			if(tmp.oaNetmailUrgent == <%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.one.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" /><img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
			}else if(tmp.oaNetmailUrgent == <%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.two.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" />&nbsp;"+title;
			}else if(tmp.oaNetmailUrgent == <%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.three.value)%>';
			  isurgent = "<img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
			}else{
			  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.four.value)%>';
			  isurgent = title;
			}
			document.getElementById("mailIsurgent").innerHTML = isurgent;
			document.getElementById("mailcontent").innerHTML = tmp.oaNetmailInboxContent;
		   //附件显示为下载
			Sys.showDownload(tmp.oaNetmailInboxAffix,"formsattachs");
			
			if(tmp.oaNetmailReplySign==<%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
				confirmmsg("发件人希望得到你的回执，是否发送?","sendIsReadMessage(true)","sendIsReadMessage(false)",window);
			}
		}
	}
}
	
function sendIsReadMessage(bl){
	dwrMailService.sendRepByMailId(<%=netmailid%>,<%=setid%>,bl);
}	
	
function replay(dialogId){
	var url = "<%=contextPath%>/erp/mobile_sms/netmail_back.jsp?mid=<%=netmailid%>"; 
	openMDITab(url);
	Sys.close(dialogId);
}

function sendforward(dialogId){
	var url = "<%=contextPath%>/erp/mobile_sms/netmail_forward.jsp?mid=<%=netmailid%>"; 
	openMDITab(url);
	Sys.close(dialogId);
}
	
</script>
</head>
<body class="inputdetail">
<input type="hidden" id="mailempPk">
<div class="requdivdetail">
			<label>
				查看帮助:显示邮件的信息，可通过点击附件来进行下载。
			</label>
		</div>
		<div class="detailtitle">
			收件邮件明细
		</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">发件人</th>
	<td id="mailPerson" class="detailtabletd"></td>
	<td class="attachtd" rowspan="4">
		<div class="attachdiv">
			<div class="attachtitle">邮件附件</div>
			<div class="attachdownload" id="formsattachs"></div>
		</div>
	</td>
	</tr>
	<tr>
	  <th>收件邮件</th>
	  <td id="mailset" class="detailtabletd"></td>
	</tr>
	<tr>
	  <th>重要程度</th>
	  <td id="mailIsurgent" class="detailtabletd"></td>
	</tr>
	<tr>
	   <th>收件时间</th>
	   <td  id="mailtime" class="detailtabletd"></td>
	</tr>
	<tr>
	 	<th>邮件主题</th>
	  	<td id="mailTitle" colspan="3" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>邮件内容</th>
		<td colspan="3" id="mailcontent" class="detailtabletd">
		</td>
	</tr>
	</table>
</body>
</html>