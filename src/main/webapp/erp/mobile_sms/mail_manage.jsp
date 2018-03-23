<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司邮件</title>
<script type='text/javascript' src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
	window.onload = function() {
		Sys.load('mail_inbox.jsp', 'mail');
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
						<div class="leftbut" onclick="Sys.load('mail_inbox.jsp','mail');" title="收件箱">
							<img src="<%=contextPath%>/images/pagemethodimg/png-0108.png" />
							<div>收件箱</div>
						</div>
						<div class="leftbut" onclick="Sys.load('mail_send.jsp','mail');" title="写邮件">
							<img src="<%=contextPath%>/images/pagemethodimg/email_add1.png" />
							<div>写邮件</div>
						</div>
						<div class="leftbut" onclick="Sys.load('mail_outbox.jsp','mail');" title="发件箱">
							<img src="<%=contextPath%>/images/pagemethodimg/png-1480.png" />
							<div>发件箱</div>
						</div>
						<div class="leftbut" onclick="Sys.load('mail_draftbox.jsp','mail');" title="草稿箱">
							<img src="<%=contextPath%>/images/pagemethodimg/png-0570.png" />
							<div>草稿箱</div>
						</div>
						<div class="leftbut" onclick="Sys.load('mail_delbox.jsp','mail');" title="回收站">
							<img src="<%=contextPath%>/images/pagemethodimg/png-0539.png" />
							<div>回收站</div>
						</div>
					</div>
				</div>
			</td>
			<td><iframe frameborder="0" height="100%" scrolling="auto" marginheight="3" id="mail" width="100%"></iframe></td>
		</tr>
	</table>

</body>
</html>