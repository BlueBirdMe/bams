<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<title>邮件发送</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);

String mid = request.getParameter("mid");
String isedit = "false";
if(mid!=null){
	isedit = "true";
}
String type = request.getParameter("type");//邮件类型 temp来自草稿箱编辑
%>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('title','您可以在此处发送外部及内部邮件！');
	
	if(<%=mid%>!=null){
		<%if(type!=null&&"send".equalsIgnoreCase(type)){%>
			dwrMailService.getNetMailSendDetailById(<%=mid%>,setSendBox);
		<%}else{%>
			dwrMailService.getNetMailSendDetailByIdDetail(<%=mid%>,setSendBox);
		<%}%>
	}
	
	document.getElementById("oaMailSendTitle").focus();
}
	
var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}
<%if(mid!=null){%>
function setSendBox(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("oaMailSendTitle",tmp.oaNetmailSendTitle);
			DWRUtil.setValue("oasendMailAdder",tmp.oaNetmailSendAdders);
			Sys.showEmpNames(tmp.oaNetmailSendEmpids,"oaMailSendEmpNames","oaMailSendEmpids");
			setRadioValueByName("oaMailSendIsurgent",tmp.oaNetmailSendIsurgent);
			
			var sel = document.getElementById("mailName");
			var selopts = sel.options;
			var index = 0;
			for(var i=0;i<selopts.length;i++){
				if(selopts[i].text == tmp.oaNetmailSetFrom){
					index = i;
					break;
				}
			}
			sel.selectedIndex = index;
			
			var repobj = document.getElementById("isreceipt");			
			if(tmp.oaNetmailReceipt == <%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
				repobj.checked = true;
			}else{
				repobj.checked = false;
			}
			<%if(type!=null&&"send".equalsIgnoreCase(type)){%>
				if(tmp.oaNetmailSendAffix!=null && tmp.oaNetmailSendAffix!=undefined && tmp.oaNetmailSendAffix!= "undefined" && tmp.oaNetmailSendAffix.length>0){
					Sys.setFilevalue("oaMailSendAffix",tmp.oaNetmailSendAffix);
				}
			<%}else{%>
				//附件
				if(tmp.oaNetmailSendAffix!=null && tmp.oaNetmailSendAffix!=undefined && tmp.oaNetmailSendAffix!= "undefined" && tmp.oaNetmailSendAffix.length>0){
					dwrCommonService.getAttachmentInfoListToString(tmp.oaNetmailSendAffix,setaccept);
				}
			<%}%>
			//fckvalue临时
			fckvalue = tmp.oaNetmailSendContent;
			if(fck!=null){
				fck.SetHTML(fckvalue);
			}
			
			document.getElementById("oaMailSendTitle").focus();
		}
	}
}

function setaccept(data){
	Sys.setFilevalue("oaMailSendAffix",data);
}
<%}%>

<%if(type!=null&&"temp".equalsIgnoreCase(type)){%>
	function returntemp(){
		Sys.href("<%=contextPath%>/erp/mobile_sms/netmail_temp.jsp");
	}
<%}%>
<%if(type!=null&&"send".equalsIgnoreCase(type)){%>
	function returnsendbox(){
		var files = DWRUtil.getValue("oaMailSendAffix");
		if(files.length>0){
			dwrMailService.deleteCopyFiles(files);
		}
		Sys.href("<%=contextPath%>/erp/mobile_sms/netmail_sendbox.jsp");
	}
<%}%>
function getemployee(){
	<%if(type==null){ %>
		var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids","netmail");
	<%}else{%>
		var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids");	
	<%}%>
	box.show();
}

function getmailadder(){
	<%if(type==null){ %>
		var box = SEL.getNetMailAdder("check","oasendMailAdder","netmail");
	<%}else{%>
		var box = SEL.getNetMailAdder("check","oasendMailAdder");
	<%}%>
	box.show();
}

function addmailemp(){
	var url = '<%=contextPath%>/erp/mobile_sms/netmail_emp_add.jsp?type=1';
	openMDITab(url + "&tab="+getMDITab());
}

function savemail(type){
	var warnArr = new Array();
	warnArr[0] = "oaMailSendTitlemust";
	warnArr[1] = "oasendMailAdderMust";
	warnInit(warnArr);
	var bl = validvalue('title');
	if(bl){
		if(type==2){//存为草稿
			Btn.close(document.getElementById("button_submit"));
			Btn.close(document.getElementById("button_cancel"));
			dwrMailService.saveNetmailSketch(getNetmail(),sketchsavecallback);
		}else{
			var adder = DWRUtil.getValue("oasendMailAdder");
			if(adder==""||adder.length==0){
				 setMustWarn("oasendMailAdderMust","收件地址不能为空。");
				 window.scrollTo(0,0);
				 return false;
			}
			Btn.close(document.getElementById("button_submit"));
			Btn.close(document.getElementById("button_cancel"));
			var chk = document.getElementById("isSaveSendBox").checked;
			dwrMailService.saveNetmailSend(getNetmail(),chk,sendcallback);
		}
	}
}

function sendcallback(data){
	Btn.open(document.getElementById("button_submit"));
	Btn.open(document.getElementById("button_cancel"));
	<%if(type!=null&&"temp".equalsIgnoreCase(type)){%>
		alertmsg(data,"closePage()");
	<%}else{%>
		if(data.success){
			<%if(type==null){ %>
				confirmmsgAndTitle("邮件发送成功！是否想继续写邮件?","cleanup();","继续写邮件","gotoInbox();","返回列表");
			<%}else{%>
				confirmmsgAndTitle("邮件发送成功！是否想继续写邮件?","cleanup();","继续写邮件","closePage();","关闭页面");
			<%}%>
		}else{
			alertmsg(data);
		}
	<%}%>
}

function getNetmail(){
	var send = new Object();
	<%if(mid!=null&&type!=null&&"temp".equalsIgnoreCase(type)){%>
		send.primaryKey = <%=mid%>;
	<%}%>
	send.oaNetmailSendTitle = DWRUtil.getValue("oaMailSendTitle");
	send.oaNetmailSetFrom = DWRUtil.getValue("mailName");
	send.oaNetmailSendAdders = DWRUtil.getValue("oasendMailAdder");
	send.oaNetmailSendEmpids = DWRUtil.getValue("oaMailSendEmpids");
	send.oaNetmailSendEmpNames = DWRUtil.getValue("oaMailSendEmpNames");
	send.oaNetmailSendIsurgent = getRadioValueByName("oaMailSendIsurgent");
	send.oaNetmailSendAffix =  DWRUtil.getValue("oaMailSendAffix");
	var rec = document.getElementById("isreceipt").checked;
	send.oaNetmailReceipt = rec?<%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>:<%=EnumUtil.OA_MAIL_RECEIPT.TWO.value%>;
	send.oaNetmailSendContent = fck.GetXHTML();
	return send;
}

function sketchsavecallback(data){
	Btn.open(document.getElementById("button_submit"));
	Btn.open(document.getElementById("button_cancel"));
	<%if(type!=null&&"temp".equalsIgnoreCase(type)){%>
		alertmsg(data,"returntemp()");
	<%}else{%>
		if(data.success){
			confirmmsgAndTitle("保存草稿成功！是否想继续写邮件?","cleanup();","继续写邮件","gotoSketch();","返回列表");
		}else{
			alertmsg(data);
		}
	<%}%>
}

function cleanup(){
	DWRUtil.setValue("oaMailSendTitle","");
	DWRUtil.setValue("oasendMailAdder","");
	DWRUtil.setValue("oaMailSendEmpids","");
	DWRUtil.setValue("oaMailSendEmpNames","");
	DWRUtil.setValue("oaMailSendAffix","");
	fck.SetHTML("");
	Sys.setFilevalue("oaMailSendAffix", "");
	document.getElementById("isSaveSendBox").checked=true;
	document.getElementById("isreceipt").checked=false;
	document.getElementById("oaMailSendTitle").focus();
}

function gotoSketch(){
	Sys.href("<%=contextPath%>/erp/mobile_sms/netmail_temp.jsp");
}

function gotoInbox(){
	Sys.href("<%=contextPath%>/erp/mobile_sms/netmail_inbox.jsp");
}
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"netmail");
}
</script>
</head>
<body class="inputcls"> 
<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">外部邮件发送</div>
	<div>	
	<table class="inputtable">
	<tr>
		<th></th>
		<td id="oaMailSendTitlemust"  colspan="2"></td>
	</tr>
	<tr>
	   <th width="15%"><em>* </em>邮件主题</th>
	   <td colspan="2"><input type="text" id="oaMailSendTitle" maxlength="50" style="width: 90%" must="邮件主题不能为空！" formust="oaMailSendTitlemust"></td>
	</tr>
	<tr>
		<th>发件邮箱</th>
		<td><select id="mailName"><%=UtilTool.getNetMailOptions(this.getServletContext(),request,"",EnumUtil.Net_Mail_Type.Send.value) %> </select></td>
		<td></td>
	</tr>
	
	<tr>
		<th></th>
		<td id="oasendMailAdderMust"  colspan="2"></td>
	</tr>
	<tr>
		<th width="15%"><em>* </em>收件地址</th>
		<td  colspan="2">
		<textarea style="width: 90%"  id="oasendMailAdder" title="点击选择地址" readonly="readonly" onclick="getmailadder();"></textarea>
		<div style="width: 90%">
		<div style="float: left;padding-left: 5px;color: #808080">如果邮箱地址不规范，系统在发送时自动过滤！</div>
		<div style="float: right;padding-top: 5px">
		<a href="javascript:void(0)" onclick="addmailemp()" style="color: #336699"><img src="<%=contextPath %>/images/grid_images/add.png" border="0" style="vertical-align: bottom;">&nbsp;添加收件地址</a>
		</div>
		</div>
		</td>
	</tr>
	<tr>
		<th width="15%">内部收件人</th>
		<td colspan="2">
		<textarea  style="width: 90%" id="oaMailSendEmpNames" linkclear="oaMailSendEmpids" title="点击选择人员" readonly="readonly" onclick="getemployee();" ></textarea>
		<input type="hidden" id="oaMailSendEmpids" value="">
		</td>
	</tr>	
	<tr>
	  <th width="15%">重要程度</th>
	  <td  colspan="2"><%=UtilTool.getRadioOptionsByEnum((EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("")),"oaMailSendIsurgent")%></td>
	</tr>
	<tr>
		<th>邮件附件</th>
		<td  colspan="2"><file:multifileupload width="90%" acceptTextId="oaMailSendAffix" height="100" saveType="file" edit="<%=isedit %>"></file:multifileupload></td>
	</tr>
	<tr>
		<th>邮件内容</th>
		<td  colspan="2"><FCK:editor instanceName="oaMailSendContent" width="90%" height="400px;"></FCK:editor></td>
	</tr>
	<tr>
		<th></th>
		<td colspan="2">
			<input type="checkbox" id="isSaveSendBox" checked="checked"><label for="isSaveSendBox">写入发件箱</label>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="isreceipt" ><label for="isreceipt">是否要求回执</label>
		</td>
	</tr>
	</table>
	</div>
	<br>
</div>
<br/>
<table>
	<tr>
	<td><btn:btn onclick="savemail(1);" value="立刻发送 " imgsrc="../../images/fileokico.png" title="发送"/></td>
	<td style="width: 10px;"></td>
<%if(type==null){ %>
	<td><btn:btn onclick="savemail(2);" value="保存为草稿 " imgsrc="../../images/png-1718.png" title="保存"/></td>
<%}else if("temp".equalsIgnoreCase(type)){ %>
	<td><btn:btn onclick="closePage();" value="关 闭 "  imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
<%}else if("send".equalsIgnoreCase(type)){  %>
	<td><btn:btn onclick="savemail(2);" value="保存为草稿" imgsrc="../../images/png-1718.png" title="保存"/></td>
	<td style="width: 10px;"></td>
	<td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
<%} %>
	</tr>
</table>
</body>
</html>