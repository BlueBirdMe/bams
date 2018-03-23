<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司短信</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">

window.onload=function(){
	Sys.load('inbox.jsp','sms');
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
		    <div class="leftbut" onclick="Sys.load('inbox.jsp','sms');" title="收件箱">
			<img src="<%=contextPath %>/images/pagemethodimg/email1.png"/>
			<div>收件箱</div>
			</div>
			<div class="leftbut" onclick="Sys.load('send_sms.jsp','sms');" title="发送短信">
			<img src="<%=contextPath %>/images/pagemethodimg/07151_16.png"/>
			<div>发送短信</div>
			</div>
			<div class="leftbut" onclick="Sys.load('outbox.jsp','sms');" title="发件箱">
			<img src="<%=contextPath %>/images/pagemethodimg/email_open.png"/>
			<div>发件箱</div>
			</div>
		</div>
		</div>
</td>
<td>
	<iframe  frameborder="0"  height="100%" scrolling="auto" id="sms" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>