<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.OaNetmailSet"%>
<%
int timer = Integer.parseInt(UtilTool.getSysParamByIndex(request,"erp.msg.callTime"))*60*1000;

//默认外部邮箱
OaNetmailSet mailSet = UtilTool.getNetMailByDefault(this.getServletContext(),request);
String setId="";
long setpk = 0;
if(mailSet!=null){
	setId = mailSet.getOaNetmailFrom();
	setpk = mailSet.getPrimaryKey();
}
 %>
<script type="text/javascript">
var calltimer = <%=timer%>;
var setDefaultId ='<%=setId.trim()%>';
var setDefaultPk = <%=setpk%>;
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/dwrPersonalProcessService.js"></script>
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath() %>/js/tooltips/tips.css' />
<script  type='text/javascript'   src='<%=request.getContextPath() %>/js/tooltips/tips.js'></script>

<div id="audiodiv"></div>
<!-- 信息提示 -->
<div id="msgboxtitdiv" style="display: none;" class="msgBoxDivClass">
<table cellpadding="0" cellspacing="0" border="0" width="100%" id="msgboxtitTable">
<tr height="25px">
<td style="width:4px;background: url('<%=request.getContextPath() %>/images/title_bg_left.gif') no-repeat;"></td>
<td style="background: url('<%=request.getContextPath() %>/images/title_bg_center.gif') repeat-x" nowrap="nowrap">
<img border="0" id="msgboxtitimg" style="margin-left: 3px;vertical-align: middle;"/>
<label id="boxtitlediv" class="boxdivtitleCalss">系统消息</label>
</td>
<td style="background: url('<%=request.getContextPath() %>/images/title_bg_right.gif') no-repeat;width:4px"></td>
</tr>
<tr>
<td  style="background: url('<%=request.getContextPath() %>/images/win_l.gif') repeat-y left;width:4px"></td>
<td class="contentbox" id="boxdivcontenttd">
<div id="boxdivcontent" class="boxdivcontentClass"></div>
<div style="clear:both"></div>
<div id="boxdivprocess" class="boxdivprocessClass">
<a href="javascript:void(0)" onclick="closemsgboxDivTips();" id="cancelshanshuo" style="float: left;">取消闪烁</a>
<a href="javascript:void(0)" id="showallmsgbox" style="float: right;">查 看</a>
</div>
</td>
<td style="background: url('<%=request.getContextPath() %>/images/win_r.gif') repeat-y right;width:4px"></td>
</tr>
<tr height="10px">
<td style="background: url('<%=request.getContextPath() %>/images/win_lb.gif') no-repeat 0 top;width:4px"></td>
<td style="background: url('<%=request.getContextPath() %>/images/win_b.gif') repeat-x 0 top">
<td style="background: url('<%=request.getContextPath() %>/images/win_rb.gif') no-repeat 0 top;width:4px"></td>
</tr>
</table>
</div>

<div id="onlineDesktopDiv" class="onlinedesktopdivClass" style="display: none;" >
	<div class="onlinetitlediv">
		<div style="background: url('<%=request.getContextPath() %>/images/bottom_online.png') 3px no-repeat;float: left;text-indent: 24px">
		在线人员
		</div>
		<div style="float: right;">
			<span style="cursor: pointer;padding-right:3px" title="关闭" onclick="hiddenlineDiv()">×</span>
		</div>
	</div>
	<div class="onlinetipscontent" style="overflow: hidden;">
	<iframe  frameborder="0"  height="100%" scrolling="no" marginheight="0" width="99%" id="onlinefrm"></iframe>
	</div>
</div>

<script type="text/javascript">
	function showSms(){
		dwrMoblieSmsService.resetSms();
		var url = "<%=request.getContextPath()%>/erp/mobile_sms/sms_manage.jsp";
		MDIOpen(url);
	}
	
	function showApprove(){
		var url = "<%=request.getContextPath()%>/erp/personal_work/flow_todo.jsp";
		MDIOpen(url);
	}
	
	function showSchDetail(){
		var box = new Sys.msgbox("定时提醒","<%=request.getContextPath()%>/erp/desktop/desktop_showtimer.jsp",'700','400');
		box.msgtitle="<b>定时提醒详细信息</b>";
		box.show();
	}
	
	function showMailDetail(type){
		var url ="";
		if(type==1){
			url="<%=request.getContextPath()%>/erp/mobile_sms/mail_manage.jsp";
		}else if(type==2){
			url="<%=request.getContextPath()%>/erp/mobile_sms/internet_mail.jsp";
		}
		hiddenmsgbox();
		cancelMail(mailimg);
		MDIOpen(url);
	}
</script>
