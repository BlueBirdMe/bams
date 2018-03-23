<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrMoblieSmsService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
function gotoPage(type){
	var url = "";
	if(type == 1){
		url = "<%=contextPath %>/erp/mobile_sms/sms_manage.jsp";
	}else if(type == 2){
		url = "<%=contextPath %>/erp/mobile_sms/mail_manage.jsp";
	}else if(type == 3){
		url = "<%=contextPath %>/erp/personal_work/flow_todo.jsp";
	}else if(type == 4){
		url = "<%=contextPath %>/erp/personal_work/timed_manager.jsp";
	}else if(type == 5){
		url = "<%=contextPath %>/erp/center.jsp";
	}else if(type == 6){
		url = "<%=contextPath %>/erp/desktop/desktop_set.jsp";
	}else if(type == 7){
		url = "<%=contextPath %>/erp/desktop/shortcut.jsp";
	}
	
	else{
		url = "<%=contextPath %>/erp/center.jsp";
	}
	
	MDIOpen(url);
}
</script>
<table cellpadding="0" cellspacing="0" border="0" height="20">
<tr>

<%if(sessionUser.getUserInfo().getUserType() == EnumUtil.SYS_USER_TYPE.DEFAULT.value){%>
<td width="25" valign="bottom"><img src="<%=contextPath %>/images/mydesktop.png" title='办公桌面' onclick="gotoPage(5)" style="cursor: pointer;height:16px;">
</td>
<td width="25" valign="bottom"><img src="<%=contextPath %>/images/mydesktopset.png" title='桌面设置' onclick="gotoPage(6)" style="cursor: pointer;height:16px;">
</td>
<td width="25" valign="bottom"><img src="<%=contextPath %>/images/shortcut.png" title='快捷菜单' onclick="gotoPage(7)" style="cursor: pointer;height:16px;">
</td>
<%} %>

<td width="25" valign="bottom"><img src="<%=contextPath %>/images/bottom_online.png" title='在线人员' onmouseout="hiddenmsgbox();" onmouseover="selectcmsgbox('online',this);" id="onlineimg" style="cursor: pointer;">
</td>
<td width="25"  valign="bottom"><img src="<%=contextPath %>/images/bottom_sms.png"  id="smsimg" title="即时短信" style="cursor: pointer;" ondblclick="gotoPage(1)">
<a id="smsaudio" href="<%=contextPath %>/audio/sms.wav"></a>
</td>
<td width="25"  valign="bottom"><img src="<%=contextPath %>/images/bottom_mail.png" id="mailimg" title="邮件(E-Mail)" style="cursor: pointer;" ondblclick="gotoPage(2)">
<a id="mailaudio" href="<%=contextPath %>/audio/mail.wav"></a>
</td>
<td width="25"  valign="bottom"><img src="<%=contextPath %>/images/bottom_approve.png" id="approveimg" title="待办工作" style="cursor: pointer;" ondblclick="gotoPage(3)">
<a id="approveaudio" href="<%=contextPath %>/audio/approve.wav"></a>
</td>
<td width="25"  valign="bottom"><img src="<%=contextPath %>/images/bottom_timer.png" id="timerimg" title="定时提醒" style="cursor: pointer;" ondblclick="gotoPage(4)">
<a id="timeraudio" href="<%=contextPath %>/audio/timer.wav"></a>
</td>
</tr>
</table>
<%@include file="tooltips.jsp" %>