<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信明细</title>
<%
String mailempid =request.getParameter("mailempid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		dwrMailService.getMailInboxByMailEmpPk(<%=mailempid%>,setMialInboxinfo);
	}
	
	function setMialInboxinfo(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("mailempPk",tmp.primaryKey);
				DWRUtil.setValue("oaMailInboxSendName",tmp.oaMailEmpInboxid.oaMailInboxSendName);
				DWRUtil.setValue("oaMailInboxSendtime",tmp.oaMailEmpInboxid.oaMailInboxSendtime);
				DWRUtil.setValue("oaMailInboxEmpNames",tmp.oaMailEmpInboxid.oaMailInboxEmpNames);
				DWRUtil.setValue("oaMailInboxEmpCSNames",tmp.oaMailEmpInboxid.oaMailInboxEmpCSNames);
				DWRUtil.setValue("oaMailInboxTitle",tmp.oaMailEmpInboxid.oaMailInboxTitle);
				var isurgent="";
				var title="";
				if(tmp.oaMailEmpInboxid.oaMailInboxIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
				  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.one.value)%>';
				  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" /><img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
				}else if(tmp.oaMailEmpInboxid.oaMailInboxIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
				  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.two.value)%>';
				  isurgent = "<img src=\"<%=contextPath%>/images/zy.png\" />&nbsp;"+title;
				}else if(tmp.oaMailEmpInboxid.oaMailInboxIsurgent == <%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
				  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.three.value)%>';
				  isurgent = "<img src=\"<%=contextPath%>/images/jj.png\" />&nbsp;"+title;
				}else{
				  title = '<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.four.value)%>';
				  isurgent = title;
				}
				document.getElementById("oaMailInboxIsurgent").innerHTML = isurgent;
				document.getElementById("oaMailInboxContent").innerHTML = tmp.oaMailEmpInboxid.oaMailInboxContent;
			   //附件显示为下载
				Sys.showDownload(tmp.oaMailEmpInboxid.oaMailInboxAffix,"formsattachs");
			}
		}
	}
	
	
function reback(index,dialogId){
	var mailempPk = document.getElementById("mailempPk").value;
	var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype="+index+"&backtype=1&mailempPk="+mailempPk+"";
	openMDITab(url);
	Sys.close(dialogId);
}

function returnload(){
	Sys.load("<%=contextPath%>/erp/mobile_sms/mail_inbox.jsp",'mail');
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
	<div class="detailtitle">
		邮件明细
	</div>
<table class="detailtable">
<tr>
<th width="15%">
	发件人
</th>
<td id="oaMailInboxSendName" class="detailtabletd"></td>
<td class="attachtd" rowspan="5">
	<div class="attachdiv">
		<div class="attachtitle">
			附件下载
		</div>
		<div class="attachdownload" id="formsattachs"></div>
	</div>
</td>
</tr>
<tr>
  <th>重要程度</th>
  <td id="oaMailInboxIsurgent" class="detailtabletd"></td>
</tr>
<tr>
 	<th>邮件主题</th>
  	<td id="oaMailInboxTitle" class="detailtabletd"></td>
</tr>
<tr>
<th>邮件抄送</th>
<td id="oaMailInboxEmpCSNames" class="detailtabletd"></td>
</tr>
<tr>
   <th>收件时间</th>
   <td id="oaMailInboxSendtime" class="detailtabletd"></td>
</tr>
<tr>
<th>邮件内容</th>
	<td colspan="3" id="oaMailInboxContent" class="detailtabletd">
	</td>
</tr>
</table>
<br/>
</body>
</html>