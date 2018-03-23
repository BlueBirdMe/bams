<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信明细</title>
<%
String oaSmsSendid =request.getParameter("oaSmsSendid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrMoblieSmsService.getOaSmsSendByPk(<%=oaSmsSendid%>,setOaSmsSendinfo);
}

function setOaSmsSendinfo(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("oaSmsSendAcpempName",tmp.oaSmsSendAcpempName);
			DWRUtil.setValue("oaSmsSendTime",tmp.oaSmsSendTime);
			
			var type = "";
			if (tmp.oaSmsType == <%=EnumUtil.OA_SMS_TYPE.one.value%>){
				type = "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.one.value)%>";
			}else{
				type = "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.two.value)%>";
			}
			
			DWRUtil.setValue("oaSmsType",type);
			document.getElementById("oaSmsSendContent").innerHTML = tmp.oaSmsSendContent;
		}
	}
}

var did;

function reback(type,id,dialogId){
	did = dialogId;
	if(type==1){
		send(id);
	}else if(type==2){
		var url = '<%=contextPath%>/erp/mobile_sms/send_sms.jsp?oaSmsSendid='+id+'&type=2'+'&sta=1';
		openMDITab(url);
		Sys.close(did);
	}
}

function send(oaSmsSendid){
	dwrMoblieSmsService.sendAgainOaSmsSend(oaSmsSendid,sendAgainback);
}

function sendAgainback(){
	alertmsg("重发短信成功","backmethod()");
}

function backmethod(){
	var win = Sys.getfrm();//获取index页面iframe window对象	
	win = win.document.getElementById("sms").contentWindow;
	win.queryData();
	Sys.close(did);
}

</script>
</head>
<body class="inputcls">
<div class="requdivdetail"><label>查看帮助:&nbsp;可以在右下角点击重发或转发，轻松使用短信系统。</label></div>
	<div class="detailtitle">短信明细</div>
		<table class="detailtable" align="center">
		<tr>
			<th width="15%">收件人</th>
			<td id="oaSmsSendAcpempName" class="detailtabletd" ></td>
		</tr>
		<tr>
			<th>发送时间</th>
			<td id="oaSmsSendTime" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>短信类型</th>
			<td id="oaSmsType"  class="detailtabletd"></td>
		</tr>
		<tr>
			<th>短信内容</th>
			<td colspan="3" id="oaSmsSendContent" class="detailtabletd"></td>
		</tr>
		</table>
</body>
</html>