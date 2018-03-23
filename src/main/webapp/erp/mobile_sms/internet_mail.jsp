<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外网邮箱</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
var urls = new Array();
urls[0] = "netmail_inbox.jsp";
urls[1] = "netmail_send.jsp";
urls[2] = "netmail_sendbox.jsp";
urls[3] = "netmail_temp.jsp";
urls[4] = "netmail_emp.jsp";
urls[5] = "netmail_set.jsp";

window.onload=function(){
	winopen(0);
}

function callback(data,index,type){
	if(data>0){
		Sys.load(urls[index],'netmail');
	}else{
		var str="发送";
		if(type == <%=EnumUtil.Net_Mail_Type.Accp.value%>){
			str="接收";
		}
		alertmsg("尚未设置"+str+"邮箱,系统将进入邮箱设置功能!","autoload()");
	}
}

function winopen(index){
	var type = <%=EnumUtil.Net_Mail_Type.Accp.value%>;
	if(index>0&&index<4){
		type = <%=EnumUtil.Net_Mail_Type.Send.value%>;
	}
	if(index<4){
		dwrMailService.getOaNetMailSetListByCount(type,function(data){
			callback(data,index,type);
		});
	}else{
		Sys.load(urls[index],'netmail');
	}
}

function autoload(){
	Sys.load(urls[5],'netmail');
	setMethodSel(6);
}
</script>
</head>
<body style="overflow: hidden;">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l" style="width:130px;">
<div class="div_title">选择操作</div>
<div class="div_content">
<div class="div_leftmethod">
    <div class="leftbut" onclick="winopen(0)" title="收件箱">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_inbox.png"/>
	<div>收件箱</div>
	</div>
	<div class="leftbut" onclick="winopen(1)" title="写邮件">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_send.png"/>
	<div>写邮件</div>
	</div>
	<div class="leftbut" onclick="winopen(2)" title="发件箱">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_sendbox.png"/>
	<div>发件箱</div>
	</div>
	<div class="leftbut" onclick="winopen(3)" title="草稿箱">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_temp.png"/>
	<div>草稿箱</div>
	</div>
	<div class="leftbut" onclick="winopen(4)" title="联系人">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_emp.png"/>
	<div>联系人</div>
	</div>
	<div class="leftbut" onclick="winopen(5)" title="邮箱设置">
	<img src="<%=contextPath %>/images/pagemethodimg/netmail_set.png"/>
	<div>邮箱设置</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="3" name="netmail" id="netmail" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>