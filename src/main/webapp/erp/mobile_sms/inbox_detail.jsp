<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信明细</title>
<%
String oaSmsInboxId =request.getParameter("oaSmsInboxId");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		dwrMoblieSmsService.getOaSmsInboxByPk(<%=oaSmsInboxId%>,setOaSmsInboxinfo);
	}
	
	function setOaSmsInboxinfo(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("oaSmsInboxSenderName",tmp.oaSmsInboxSenderName);
				DWRUtil.setValue("oaSmsInboxSendtime",tmp.oaSmsInboxSendtime);
				var type = "";
				if (tmp.oaSmsType == <%=EnumUtil.OA_SMS_TYPE.one.value%>){
				   type = "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.one.value)%>";
				}else{
				  type = "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.two.value)%>";
				}
				DWRUtil.setValue("oaSmsType",type);
				document.getElementById("oaSmsInboxContent").innerHTML = tmp.oaSmsInboxContent;
				
			}
		}
	}
	function reback(type,id,dialogId){
        try{
			var win = Sys.getfrm();//获取index页面iframe window对象	
			var smsfrm = win.document.getElementById("sms").contentWindow;
			if(type==1){
				smsfrm.sendBack(id);
			}else if(type==2){
				smsfrm.sendSwitch(id)
			}
		}catch(err){}
		
		Sys.close(dialogId);
}
function send(oaSmsSendid){
	dwrMoblieSmsService.sendAgainOaSmsSend(oaSmsSendid,sendAgainback);
}

function sendAgainback(){
	Sys.close();
	alertmsg("重发短信成功","backmethod()");
}

function backmethod(){
	var win = Sys.getfrm();//获取index页面iframe window对象	
	win = win.document.getElementById("sms").contentWindow;
	win.queryData();
}
	
</script>
</head>
<body>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp;可以在右下角点击回复或转发，轻松使用短信系统。</label></div>
<div class="detailtitle">短信明细</div>
<table class="detailtable" align="center">
<tr>
<th width="15%">发件人</th>
<td id="oaSmsInboxSenderName"class="detailtabletd" ></td>

</tr>
<tr>
<th>收件时间</th>
<td id="oaSmsInboxSendtime" class="detailtabletd"></td>
</tr>
<tr>
<th>短信类型</th>
<td id="oaSmsType" colspan="3" class="detailtabletd"></td>
</tr>
<tr>
<th>短信内容</th>
<td colspan="3" id="oaSmsInboxContent" class="detailtabletd" >
</td>
</tr>
</table>

<br/>
</body>
</html>