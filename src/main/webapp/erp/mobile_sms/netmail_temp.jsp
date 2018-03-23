<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>草稿箱</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
 %>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var sendbox = getQueryParam();
	var pager = getPager();
	var sel = document.getElementById("sendMailSel");
	sendbox.oaNetmailSetFrom = sel.options[sel.selectedIndex].text;
	sendbox.oaNetmailSendType = <%=EnumUtil.Net_Mail_SendBox_Type.Sketch.value%>;
	dwrMailService.getNetMailListByPager(sendbox,pager,queryCallback);
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
	var box = new Sys.msgbox('草稿明细查看','<%=contextPath%>/erp/mobile_sms/netmail_send_detail.jsp?mid='+obj.value+'&sid='+document.getElementById("sendMailSel").value,'800','500');
	box.msgtitle="<b>草稿邮件信息明细查看</b>";
	box.show();
}

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}
function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除该邮件吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的邮件...");
	}
}

function edit(id){
	var url = '<%=contextPath%>/erp/mobile_sms/netmail_send.jsp?mid='+id+'&type=temp';
	openMDITab(url + "&tab="+getMDITab());
}

function del(id){
	confirmmsg("确定要删除邮件吗?","delok("+id+")");
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrMailService.deleteMailTempByPks(recordsPks,delcallback);
}

function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrMailService.deleteMailTempByPks(pks,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}


function repAffix(rowObj){
	var  count=0;
	if(rowObj.oaNetmailSendAffix!=null&&rowObj.oaNetmailSendAffix.length>0){
		count = rowObj.oaNetmailSendAffix.split(",").length;
	}
	return count;
}

//列显示替换方法
function repleaIsurgent(rowObj){
	var str="";
	if(rowObj.oaNetmailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\" title='重要紧急'/><img src=\"<%=contextPath%>/images/jj.png\"  title='重要紧急'/>";
	}else if(rowObj.oaNetmailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\"  title='重要'/>";
	}else if(rowObj.oaNetmailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
		str= "<img src=\"<%=contextPath%>/images/jj.png\"  title='紧急'/>";
	}else{
	}
	return str;
}

function repleaReceipt(rowObj){
	var str="";
	if(rowObj.oaNetmailReceipt ==<%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
		str= "<font color='blue'>是</font>";
	}else{
		str= "否";
	}
	return str;
}
</script>
</head>
<body>
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" style="background: url('<%=contextPath %>/images/alertimg/content_bg.gif') repeat-x 0 0;">
	<tr height="30px">
	<td nowrap="nowrap" style="padding-right: 30px">
	<div class="formTitle" style="font-size: 13px;color:#1B579D">请先选择邮箱:</div>
	</td>
	<td  align="left" width="100%">
	<select style="width: auto;" id="sendMailSel" onchange="queryData();">
		<%=UtilTool.getNetMailOptions(this.getServletContext(),request,"",EnumUtil.Net_Mail_Type.Send.value) %>
	</select>
	</td>
	</tr>
	<tr>
	<td valign="top" style="HEIGHT: 100%" colspan="3">
	<%
		SysGrid bg = new SysGrid(request);
			
			bg.setTableTitle("草稿箱");
			bg.setShowImg(false);
			
			//设置附加信息
			bg.setQueryFunction("queryData"); //查询的方法名
			bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			bg.setDblBundle("primaryKey"); //双击列的绑定的列值
			
			//放入按钮
			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("批量删除", "delbatch()",	"close.png"));
			bg.setBtnList(btnList);
			
			//放入列
			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
		    sccList.add(new SysColumnControl("oaNetmailSendIsurgent","重要紧急",1,2,1,0));
			sccList.add(new SysColumnControl("oaNetmailSendAdders","外部收件人",1,2,2,15));
			sccList.add(new SysColumnControl("oaNetmailSendEmpNames","内部收件人",1,2,2,15));
			sccList.add(new SysColumnControl("oaNetmailSendTitle","主题",1,1,1,30));
			sccList.add(new SysColumnControl("oaNetmailSendTime","时间",1,2,1,0));
		    sccList.add(new SysColumnControl("oaNetmailReceipt","要求回执",1,2,2,0));
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
			for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("oaNetmailSendTime".equalsIgnoreCase(bc.getDataName())){
			DateType date = new DateType();
			bc.setColumnTypeClass(date);
		}
		if("oaNetmailSendIsurgent".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("-1,-请选择是否紧急-"));
			select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
			bc.setColumnTypeClass(select);
			
			bc.setColumnReplace("repleaIsurgent");
		}
		if("oaNetmailReceipt".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repleaReceipt");
			bc.setColumnStyle("text-align:center;");
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