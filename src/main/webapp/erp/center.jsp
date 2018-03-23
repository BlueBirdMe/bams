<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type='text/css' rel='stylesheet'  href='<%=contextPath %>/js/movediv/movediv.css'/>
<script type='text/javascript' src='<%=contextPath %>/js/movediv/movediv.js'></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
boolean noticeshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notice.value);
boolean postsshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Post.value);
boolean notesshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notepad.value);
boolean voteshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Vote.value);
boolean schshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Schedule.value);
boolean approveshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Approve.value);
boolean applyshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Apply.value);
boolean smsshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Sms.value);
boolean mailshow = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Mail.value);
boolean showweather = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Weather.value);
boolean shownotebook = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Notes.value);
boolean showRegular = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Regular.value);
boolean showTimer = UtilTool.isShowDeskTop(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Timer.value);


String isopen = SystemConfig.getParam("erp.center.weather");
boolean bl = "true".equalsIgnoreCase(isopen);
String wurl = SystemConfig.getParam("erp.weather.url");
String defaultmore = SystemConfig.getParam("erp.weather.defaultmore");
String more = SystemConfig.getParam("erp.weather.more");
if(bl){
	OaDesktopSet depset = UtilTool.getDeskTopByType(this.getServletContext(),request,EnumUtil.OA_DESKTOP_TYPE.Weather.value);
	if(depset != null && depset.getOaDesktopValue() != null && depset.getOaDesktopValue().length()>0){
		String[] tmps = depset.getOaDesktopValue().split(",");
		if(tmps.length>1){
			wurl+="?id="+tmps[1]+"T";
			more+=tmps[1]+".shtml";
		}else{
			more = defaultmore;
		}
	}else{
		more = defaultmore;
	}
}
%>
<title>办公桌面</title>
<script type="text/javascript">
window.onload = function(){
	CoolDrag.init('container','<%=user.getCompanyId()%>','<%=user.getUserInfo().getPrimaryKey()%>');
	<%if(bl && showweather){ %>
		window.setTimeout("document.getElementById('weatherfrm').src ='<%=wurl%>'",0);
	<%}%>

	window.setTimeout("loadxml()",1000);
	window.setTimeout("loadxml_task()",1000);//待办工作
	window.setTimeout("loadxml_apply()",1000);//我的申请
	window.setTimeout("loadxml_other()",1500);
	window.setTimeout("loadxml_ref()",1500);
	window.setTimeout("loadxml_sms()",1500);
	window.setTimeout("loadxml_mail(1,'mailSystem')",1500);
}

function loadxml(){
 	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml.jsp",
	function(str){
		if(str.getElementsByTagName("Notice").length>0){
			document.getElementById("noticediv").innerHTML =  str.getElementsByTagName("Notice")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Posts").length>0){
			document.getElementById("postcontent").innerHTML =  str.getElementsByTagName("Posts")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Note").length>0){
			document.getElementById("notecontent").innerHTML =  str.getElementsByTagName("Note")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Vote").length>0){
			document.getElementById("votecontent").innerHTML =  str.getElementsByTagName("Vote")[0].childNodes[0].nodeValue;
		}
	}, "xml");
}

function loadxml_other(){
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_other.jsp",
    function(str){
		if(str.getElementsByTagName("Vote").length>0){
			document.getElementById("votecontent").innerHTML =  str.getElementsByTagName("Vote")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Schedule").length>0){
			document.getElementById("schcontent").innerHTML =  str.getElementsByTagName("Schedule")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Regul").length>0){
			document.getElementById("regularContent").innerHTML = str.getElementsByTagName("Regul")[0].childNodes[0].nodeValue;
		}
  	 }, "xml");
}

function loadxml_ref(){
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_refsh.jsp",
    function(str){
		if(str.getElementsByTagName("Notes").length>0){
			document.getElementById("notescontent").innerHTML =  str.getElementsByTagName("Notes")[0].childNodes[0].nodeValue;
		}
		if(str.getElementsByTagName("Timer").length>0){
			document.getElementById("timercontent").innerHTML =  str.getElementsByTagName("Timer")[0].childNodes[0].nodeValue;
		}
  	}, "xml");
}

function loadxml_sms(){
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_sms.jsp",
    function(str){
		<%if(smsshow){%>
		if(str.getElementsByTagName("SMS").length>0){
			document.getElementById("smscontent").innerHTML =  str.getElementsByTagName("SMS")[0].childNodes[0].nodeValue;
		}
		<%}%>
  	 }, "xml");
}

function loadxml_mail(type,objid){
	document.getElementById("mailType").value = type;
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_mail.jsp?mailtype="+type,
    function(str){
		<%if(mailshow){%>
		if(str.getElementsByTagName("MAIL").length>0){
			var obj = document.getElementById(objid);
			if(obj!=null){
				obj.innerHTML =  str.getElementsByTagName("MAIL")[0].childNodes[0].nodeValue;
			}
		}
		<%}%>
  	 }, "xml");
}

function loadxml_task(){
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_approve.jsp",
    function(str){
		<%if(approveshow){%>
		if(str.getElementsByTagName("Approve").length>0){
			document.getElementById("approvecontent").innerHTML =  str.getElementsByTagName("Approve")[0].childNodes[0].nodeValue;
		}
	    <%}%>
  	}, "xml");
}


function loadxml_apply(){
	jQuery.get("<%=contextPath%>/erp/desktop/desktopHtml_apply.jsp",
    function(str){
		<%if(applyshow){%>
		if(str.getElementsByTagName("APPLY").length>0){
			 document.getElementById("applycontent").innerHTML = str.getElementsByTagName("APPLY")[0].childNodes[0].nodeValue;
		}
		<%}%>
  	 }, "xml");
}

function hidebtn(obj){
	obj.innerHTML = "";
}

function claim(taskId){
	dwrPersonalProcessService.claimTask(taskId,function(data){loadxml_task();alertmsg(data);});
}

function handle(handleUrl,businessKey,taskId,taskDefinitionKey) {
	var url = "<%=contextPath%>/erp/"+handleUrl+"?pk="+businessKey+"&taskId="+taskId+"&definitionKey="+taskDefinitionKey+"&tab="+getMDITab();
	openMDITab(url);
}

function showDetail(detailUrl, id, processInstanceId){
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+id+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url);
	box.show();
}

function showVote(id){
	 var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+id, '800','500');
     box.show();
}

function showRegular(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/company_resources/regulations_detail.jsp?rid='+id,'800','500');
	box.show();
}

function showOaSms(id,isread){
	if(isread == <%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
		dwrMoblieSmsService.setOaSmsInboxIsread(id,function(data){loadxml_sms();});
	}
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/inbox_detail.jsp?oaSmsInboxId='+id,'800','500');
	box.show();
}

function showOaMail(id,isread){
	if(isread == <%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
		dwrMailService.setMailinboxIsread(id,function(data){loadxml_mail(1,'mailSystem');});
	}
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/mail_inbox_detail.jsp?mailempid='+id,'800','500');
	box.show();
}

function showOaNetMail(id,isread,sid){
	if(isread == <%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
		var ids = new Array();
		ids[0] = id;
		dwrMailService.updateMailReadStatus(ids,function(data){loadxml_mail(2,'mailNet');});
	}
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/netmail_inbox_detail.jsp?mid='+id+'&sid='+sid,'800','550');
	box.show();
}

function showNotice(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/notice_detail.jsp?oid='+id,'800','500');
	box.show();
}

function showPosts(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/announcement_detail.jsp?aid='+id,'800','500');
	box.show();
}

function showNotepd(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/adversaria_detail.jsp?did='+id,'800','500');
	box.show();
}

//邮件
function moremail(){
	var type = document.getElementById("mailType").value
	var url="";
	if(type==1){
		url="<%=contextPath %>/erp/mobile_sms/mail_manage.jsp";
	}else if(type==2){
		url="<%=request.getContextPath()%>/erp/mobile_sms/internet_mail.jsp";
	}
	openMDITab(url);
}

function toupiao(id){
	var box = new Sys.msgbox('参与投票','<%=contextPath%>/erp/communication/emp_voting_page.jsp?choose=1&voteId='+id,'800','500');
	var butarray = new Array();
	butarray[0] = "ok|votingByDesk('"+box.dialogId+"')| 投 票 ";
	butarray[1] = "cancel|| 取 消 ";
	box.buttons = butarray;
	box.show();
}

function showNoteBook(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/personal_work/oa_notebook_detail.jsp?aid='+id,'800','500');
	box.show();
}

function addnotebook(){
	var box = new Sys.msgbox('添加便签','<%=contextPath%>/erp/personal_work/oa_notebook_add.jsp?type=1','700','400');
	var butarray = new Array();
	butarray[0] = "ok|savedesktop('"+box.dialogId+"','undefined','loadxml_ref()');";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
}

function addtimer(){
	var box = new Sys.msgbox('添加提醒','<%=contextPath%>/erp/personal_work/timed_add.jsp?type=1','800','500');
	var butarray = new Array();
	butarray[0] = "ok|savedesktop('"+box.dialogId+"','undefined','loadxml_ref()');";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
}

</script>
<style type="text/css">
	button { 
		position: relative;
		border: 0; 
		padding: 0;
		cursor: pointer;
		overflow: visible; /* removes extra side padding in IE */
		margin-bottom: 10px
	}
	
	
	button span { 
		position: relative;
		display: block; 
		white-space: nowrap;
		width: 100px;
	}

	button.submitBtn { 
		padding: 0 8px 0 0; 
		margin-right:20px; 
		font-size:13px; 
		text-align: center; 
		background: transparent url(<%=contextPath%>/images/btn_desk_right.png) no-repeat right top; 
	}
		
	button.submitBtn span {
		padding-left: 18px;
		padding-right:10px;
		padding-top:12px;
		height:40px; 
		background: transparent url(<%=contextPath%>/images/btn_desk_left.png) no-repeat left top; 
		color:#054477;
	}
</style>
</head>
<body>
<input type="hidden" id="mailType" value="">
<table width="100%" cellpadding="0" cellspacing="0">
<tr><td>
<div id="container">
	<div id="leftcontainer" class="leftcontainer">
		<%if(postsshow){ %>
		<div class="dragLayer" id="postwin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/tab_go.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Post.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/personal_work/company_anno.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="postcontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		<%if(noticeshow){ %>
		<div class="dragLayer" id="noticewin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/sounda.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Notice.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/personal_work/company_notice.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="noticediv">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		<%if(voteshow){ %>
		<div class="dragLayer" id="votewin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/plugin.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Vote.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/communication/vote_total_pager.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="votecontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		<%if(notesshow){ %>
		<div class="dragLayer" id="notewin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/page_component.gif') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Notepad.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/personal_work/company_adver.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="notecontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
	</div>
	<div id="middlecontainer" class="middlecontainer">
		<%if(approveshow){ %>
		<div class="dragLayer" id="approvewin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/computer.gif') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Approve.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/personal_work/flow_todo.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="approvecontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
	
		<%if(applyshow){ %>
		<div class="dragLayer" id="applywin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/apply.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Apply.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/personal_work/flow_query.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="applycontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		
		<%if(schshow){ %>
		<div class="dragLayer" id="schwin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/clock.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Schedule.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/work_arrange/workCalender.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="schcontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		<%if(smsshow){ %>
		<div class="dragLayer" id="smswin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/phone.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Sms.value) %>
					&nbsp;<span class="more" title="更多" onclick="openMDITab('<%=contextPath %>/erp/mobile_sms/sms_manage.jsp')">
					</span>
				</div>
			</div>
			<div class="content" id="smscontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
			</div>
		</div>
		<%} %>
		<%if(mailshow){ %>
		<div class="dragLayer" id="mailwin">
			<div class="dragHeader">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/email.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Mail.value) %>
					&nbsp;<span class="more" title="更多" onclick="moremail()">
					</span>
				</div>
			</div>
			<div class="content">
				<DIV class="tabdiv" style="height: 100%;width:98%;" id="mailtab">
				<UL class="tags">
					<LI><A onClick="tabmail.selectTag(this);loadxml_mail(1,'mailSystem');" href="javascript:void(0)">公司邮件</A></LI>
					<LI><A onClick="tabmail.selectTag(this);loadxml_mail(2,'mailNet');" href="javascript:void(0)">外网邮件</A></LI>
				</UL>
				<DIV class="tagContentdiv">
					<DIV class="tagContent" id="mailSystem">
					<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
					</DIV>
					<DIV class="tagContent" id="mailNet">
					<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0" ></p></div>
					</DIV>
				</DIV>
				</div>
			</div>
		</div>
		<%} %>
	</div>
	<div style="clear:both;"></div>
</div>
</td>
<td valign="top" width="240">
	<%if(bl && showweather){ %>
		<div class="dragLayer" id="weather">
			<div class="dragHeader2">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/weatherimg.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Weather.value) %>
				</div>
				<div style="width:55px;float: right;padding-top:5px;">
					<a title="更多" href="javascript:void(0);"  onclick="openMDITab('<%=more %>')">
						<img src="<%=contextPath %>/js/movediv/move_more_.gif" border="0"></img>
					</a>
				</div>
			</div>
			<div class="content" style="height:20px;width:190px;">
				<iframe frameborder="0" height="20" width="190"  scrolling="no" marginheight="0" id="weatherfrm"></iframe>
			</div>
		</div>
		<%} %>
		
		<%if(showTimer){ %>
		<div class="dragLayer" id="timer">
			<div class="dragHeader2">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/stimer.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Timer.value) %>
				</div>
				<div style="width:65px;float: right;">
					<span class="add" title="添加提醒" onclick="addtimer()">+</span>
					
					<a title="更多" href="javascript:void(0);"  onclick="openMDITab('<%=contextPath %>/erp/personal_work/timed_query_valid.jsp')">
						<img src="<%=contextPath %>/js/movediv/move_more_.gif" border="0"></img>
					</a>
				</div>
			</div>
			<div class="content" id="timercontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0"></p></div>
			</div>
		</div>
		<%} %>
		
		
		<%if(shownotebook){ %>
		<div class="dragLayer" id="notes">
			<div class="dragHeader2">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/notebook.png') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Notes.value) %>
				</div>
				<div style="width:65px;float: right;">
					<span class="add" title="添加便签" onclick="addnotebook()">+</span>
					
					<a title="更多" href="javascript:void(0);"  onclick="openMDITab('<%=contextPath %>/erp/personal_work/notebook.jsp')">
						<img src="<%=contextPath %>/js/movediv/move_more_.gif" border="0"></img>
					</a>
				</div>
			</div>
			<div class="content" id="notescontent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0"></p></div>
			</div>
		</div>
		<%} %>
		
		
		<%if(showRegular){ %>
		<div class="dragLayer" id="weather">
			<div class="dragHeader2">
				<div class="dragleft" style="background: url('<%=contextPath %>/images/projectimg/icon_monitor_mac.gif') 3px no-repeat;">
					<%=EnumUtil.OA_DESKTOP_TYPE.valueOf(EnumUtil.OA_DESKTOP_TYPE.Regular.value) %>
				</div>
				<div style="width:55px;float: right;padding-top:5px;">
					<a title="更多" href="javascript:void(0);"  onclick="openMDITab('<%=contextPath %>/erp/company_resources/regulations_query.jsp')">
						<img src="<%=contextPath %>/js/movediv/move_more_.gif" border="0"></img>
					</a>
				</div>
			</div>
			<div class="content" id="regularContent">
				<div style='padding: 1px;text-align: center;'><p><img style="vertical-align: middle;" src ="<%=contextPath %>/images/loading2.gif" border="0"></p></div>
			</div>
		</div>
		<%} %>
</td>
</tr>
</table>
<script type="text/javascript">
<%if(mailshow){%>
var tabmail =new SysTab('<%=contextPath%>',1,"mailtab");
<%}%>
</script>
</body>
</html>