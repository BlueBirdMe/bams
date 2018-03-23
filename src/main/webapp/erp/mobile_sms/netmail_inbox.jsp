<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收件箱</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String empId = UtilTool.getEmployeeId(request);
boolean bl=false;
if(ConstWords.mailIsAccpMap.containsKey(empId)){
	bl = ConstWords.mailIsAccpMap.get(empId);
}
String sel = UtilTool.getNetMailOptions(this.getServletContext(),request,"",EnumUtil.Net_Mail_Type.Accp.value);

 %>
<script type="text/javascript">
var wt = null;

window.onload  = function(){
	var sdiv = document.getElementById("serverdiv");
	var sdivload = document.getElementById("serverloaddiv");
	if(<%=bl%>){
		sdiv.style.display="none";
		sdivload.style.display="";
		wt = window.setInterval(function(){
			dwrMailService.getMailAccpIsReady('<%=empId%>',readycallback);
		},1000*5);
	}
}

//查询方法
function queryData(){
	startQuery();
	var inbox = getQueryParam();
	var pager = getPager();
	var sel = document.getElementById("sendMailSel");
	inbox.oaNetmailSetId = sel.options[sel.selectedIndex].text;
	dwrMailService.getNetMailInboxByPager(inbox,pager,queryCallback);
}

function readycallback(data){
	var sdiv = document.getElementById("serverdiv");
	var sdivload = document.getElementById("serverloaddiv");
	if(data==false){
		sdiv.style.display="";
		sdivload.style.display="none";
		queryData();
		if(wt!=null){
			window.clearInterval(wt);
		}
	}else{
		sdiv.style.display="none";
		sdivload.style.display="";
	}
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	var id = obj.value;
	var rowObj= getObjectByPk(id);
	if(rowObj.oaNetmailIsRead ==<%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
   		var ids = new Array();
		ids[0] = id;
		dwrMailService.updateMailReadStatus(ids,queryData);
   	}
   	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/netmail_inbox_detail.jsp?mid='+id+'&sid='+document.getElementById("sendMailSel").value,'800','500');
	box.msgtitle="<b>邮件信息明细查看</b>";
	var butarray = new Array();
	butarray[0] = "ok|replay('"+box.dialogId+"');| 回 复 ";
	butarray[1] = "ok|sendforward('"+box.dialogId+"');| 转 发 ";
	butarray[2] = "cancel";
	box.buttons = butarray;
	box.show(); 
}

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='转发' onclick=\"sendforward("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/package_go.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='回复' onclick=\"replay("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/comments.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function replay(id){
	var url = '<%=contextPath%>/erp/mobile_sms/netmail_back.jsp?mid='+id;
	openMDITab(url);
}

function sendforward(id){
	var url = '<%=contextPath%>/erp/mobile_sms/netmail_forward.jsp?mid='+id;
	openMDITab(url);
}

function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除邮件吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的邮件...");
	}
}

function del(id){
	confirmmsg("确定要删除邮件吗?","delok("+id+")");
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrMailService.deleteOaNetmailInboxByPks(recordsPks,delcallback);
}

function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrMailService.deleteOaNetmailInboxByPks(pks,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}


function repAffix(rowObj){
	var  count=0;
	if(rowObj.oaNetmailInboxAffix!=null&&rowObj.oaNetmailInboxAffix.length>0){
		count = rowObj.oaNetmailInboxAffix.split(",").length;
	}
	return count;
}

function getMailServer(){
	var sdiv = document.getElementById("serverdiv");
	var sdivload = document.getElementById("serverloaddiv");
	var sid = document.getElementById("sendMailSel").value;
	var ids = new Array();
	ids[0] = sid;
	sdiv.style.display="none";
	sdivload.style.display="";
	dwrMailService.getNetMailFormServer(ids,servercallback);
}
function repSender(rowObj){
	var vla=rowObj.oaNetmailInboxSender;
	if(vla!=null&&vla.length>0){
		return vla;
	}else{
		var tf = rowObj.oaNetmailSetFrom;
		return tf.split("@")[0];
	}
}

function servercallback(data){
	var sdiv = document.getElementById("serverdiv");
	var sdivload = document.getElementById("serverloaddiv");
	sdiv.style.display="";
	sdivload.style.display="none";
	if(data.success&&data.resultList.length>0){
		var tmp = data.resultList[0];
		if(tmp>0){
			var str = "收到"+tmp+"封新邮件,请查阅!";
			alertmsg(str,"queryData()");
		}else{
			alertmsg("没有收到新的邮件！");
		}
	}else{
		alertmsg(data);
	}
}

//列显示替换方法
function repleaIsread(rowObj){
	var str="";
	if(rowObj.oaNetmailIsRead ==<%=EnumUtil.OA_SMS_INBOX_ISREAD.one.value%>){
		//str= "<font style='color:blue'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.one.value)%></font>";
		str= "<img src=\"<%=contextPath%>/images/smsisread_.gif\" alt='已读邮件' title='已读邮件'/>";
	}else{
		//str= "<font style='color:red'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.two.value)%></font>";
		str= "<img src=\"<%=contextPath%>/images/smsisread.gif\" alt='未读邮件' title='未读邮件'/>";
	}
	return str;
}
//列显示替换方法
function repleaIsurgent(rowObj){
	var str="";
	if(rowObj.oaNetmailUrgent ==<%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\" title='重要紧急'/><img src=\"<%=contextPath%>/images/jj.png\"  title='重要紧急'/>";
	}else if(rowObj.oaNetmailUrgent ==<%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\"  title='重要'/>";
	}else if(rowObj.oaNetmailUrgent ==<%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
		str= "<img src=\"<%=contextPath%>/images/jj.png\"  title='紧急'/>";
	}else{
	}
	return str;
}


function batchread(){
	if(getAllRecordArray() != false){
		dwrMailService.updateMailReadStatus(getAllRecordArray(),delcallback);
	}else{
		alertmsg("请选择要修改标识的记录...");
	}
}

</script>
</head>
<body>
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" style="background: url('<%=contextPath %>/images/alertimg/content_bg.gif') repeat-x 0 0;">
	<tr height="30px">
	<td nowrap="nowrap" style="padding-right: 15px">
	<div class="formTitle" style="font-size: 13px;color:#1B579D">请先选择邮箱:</div>
	</td>
	<td  align="left" nowrap="nowrap" style="padding-right: 15px;">
	<select style="width: auto;" id="sendMailSel" onchange="queryData();">
		<%=sel %>
	</select>
	</td>
	<td  width="100%" align="left" nowrap="nowrap">
	<div id="serverdiv">
	<btn:btn onclick="getMailServer()" value="收 信 " imgsrc='../../images/mailserver.png'></btn:btn>
	</div>
	<div id="serverloaddiv" style="display: none;">
	<img src='<%=contextPath %>/images/dataload.gif' border='0' style='vertical-align:middle;'/>&nbsp;接收邮件中...
	</div>
	</td>
	</tr>
	<tr>
	<td valign="top" style="HEIGHT: 100%" colspan="3">
	<%
		SysGrid bg = new SysGrid(request);
			
			bg.setTableTitle("收件箱");
			bg.setShowImg(false);
			
			//设置附加信息
			bg.setQueryFunction("queryData"); //查询的方法名
			bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			bg.setDblBundle("primaryKey"); //双击列的绑定的列值
			
			//放入按钮
			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("批量删除", "delbatch()",	"close.png"));
			btnList.add(new SysGridBtnBean("设置已读", "batchread()","058.gif"));
			bg.setBtnList(btnList);
			
			//放入列
			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			sccList.add(new SysColumnControl("oaNetmailIsRead","状态",1,2,1,0));
		    sccList.add(new SysColumnControl("oaNetmailUrgent","重要紧急",1,2,1,0));
			sccList.add(new SysColumnControl("oaNetmailInboxSender","发件人",1,1,1,20));
			sccList.add(new SysColumnControl("oaNetmailInboxTitle","主题",1,1,1,30));
			sccList.add(new SysColumnControl("oaNetmailInboxTime","发件时间",1,2,1,0));
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
			for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("oaNetmailInboxSender".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repSender");
		}
		if("oaNetmailIsRead".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.OA_SMS_INBOX_ISREAD.getSelectAndText("-1,-请选择邮件状态-"));
			select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
			bc.setColumnTypeClass(select);
			
			bc.setColumnReplace("repleaIsread");
		}
		if("oaNetmailUrgent".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("-1,-请选择是否紧急-"));
			select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
			bc.setColumnTypeClass(select);
			
			bc.setColumnReplace("repleaIsurgent");
		}
		if("oaNetmailInboxTime".equalsIgnoreCase(bc.getDataName())){
			DateType date = new DateType();
			bc.setColumnTypeClass(date);
		}
			}
			colList.add(ColumnUtil.getCusterShowColumn("fujian","附件数","repAffix",0,""));
			
			bg.setColumnList(colList);
			 
			//设置列操作对象
			bg.setShowProcess(true);
			bg.setProcessMethodName("createProcessMethod");
			
			//开始创建
			out.print(bg.createTable());
	%>
</td>
</tr>
</table>
</body>
</html>