<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<title>收件箱</title>
<%
     String firstdate=UtilWork.getFirstDateOfMonth();
     String enddate = UtilWork.getEndDateOfMonth();

 %>
<script>
//查询方法
function queryData(){
   startQuery();
   var oaMailEmp = getQueryParam();
   var pager = getPager();
   dwrMailService.listOaMailInbox(oaMailEmp,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function refresh(){
	document.getElementById("oaMailEmpInboxid.oaMailInboxSendName").value="";
	document.getElementById("oaMailEmpInboxid.oaMailInboxTitle").value="";
	setSelectValue("oaMailEmpIsread",-1);
	queryData();
}
	
function deleteObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定将已选择的记录放入已删除吗?","del()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}
	
function del(){
	var recordsPks = getAllRecordArray();
	dwrMailService.deleteOaMailInboxByPks(recordsPks,setcallback);
}

function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrMailService.deleteOaMailInboxByPks(ids,setcallback);
}
	
function setcallback(data){
	alertmsg(data,"queryData()");
}

function setMailRead(){
    if(getAllRecordArray() != false){
		confirmmsg("确定将已选择的记录设置已读吗?","setMailed()");
	}else{
		alertmsg("请选择要设置的记录...");
	}
}
   
function setMailed(){
    var records = getAllRecordArray();
    dwrMailService.setMailReaded(records,setCallback);
}   
   
function setCallback(data){
    alertmsg(data,"queryData()");
}   
   
  //双击数据
function dblCallback(obj){
	 var rowObj= getObjectByPk(obj.value);
    //设置已读
    if(rowObj.oaMailEmpIsread ==<%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
   		dwrMailService.setMailinboxIsread(obj.value,getMailinboxinfo);
   	}
   	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/mail_inbox_detail.jsp?mailempid='+obj.value,'800','500');
	box.msgtitle="<b>邮件信息明细查看</b>";
	var butarray = new Array();
	butarray[0] = "ok|reback(1,'"+box.dialogId+"');| 回 复 ";
	butarray[1] = "ok|reback(2,'"+box.dialogId+"');| 全部回复 ";
	butarray[2] = "ok|reback(4,'"+box.dialogId+"');| 转 发 ";
	butarray[3] = "cancel";
	box.buttons = butarray;
	box.show();
}

function getMailinboxinfo(data){
  queryData();
}

function regBack(pk){
     var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype=1&backtype=1&mailempPk="+pk;
     openMDITab(url);
}

function  regAllBack(pk){
     var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype=2&backtype=1&mailempPk="+pk;
     openMDITab(url);
}

function zhuanfa(pk){
    var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype=4&backtype=1&mailempPk="+pk;
    openMDITab(url);
}

function createMethod(rowObj){
     var str = "<a href='javascript:void(0)' title='回复' onclick=\"regBack('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/comment.png' border='0'/></a>&nbsp;&nbsp;";
         str += "<a href='javascript:void(0)' title='全部回复' onclick=\"regAllBack('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/comments.png' border='0'/></a>&nbsp;&nbsp;";
         str += "<a href='javascript:void(0)' title='转发' onclick=\"zhuanfa('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/package_go.png' border='0'/></a>&nbsp;&nbsp;";
         str += "<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
} 
	
//列显示替换方法
function repleaIsread(rowObj){
	var str="";
	if(rowObj.oaMailEmpIsread ==<%=EnumUtil.OA_SMS_INBOX_ISREAD.one.value%>){
		//str= "<font style='color:blue'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.one.value)%></font>"
		str= "<img src=\"<%=contextPath%>/images/smsisread_.gif\" alt='已读邮件' title='已读邮件'/>";
	}else{
		//str= "<font style='color:red'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.two.value)%></font>"
		str= "<img src=\"<%=contextPath%>/images/smsisread.gif\" alt='未读邮件' title='未读邮件'/>"
	}
	return str;
}

//列显示替换方法
function repleaIsurgent(rowObj){
	var str="";
	if(rowObj.oaMailEmpInboxid.oaMailInboxIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\" title='重要紧急'/><img src=\"<%=contextPath%>/images/jj.png\"  title='重要紧急'/>";
	}else if(rowObj.oaMailEmpInboxid.oaMailInboxIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\"  title='重要'/>";
	}else if(rowObj.oaMailEmpInboxid.oaMailInboxIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
		str= "<img src=\"<%=contextPath%>/images/jj.png\"  title='紧急'/>";
	}else{
	}
	return str;
}


//附件数
function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaMailEmpInboxid.oaMailInboxAffix!=null&& rowObj.oaMailEmpInboxid.oaMailInboxAffix != undefined && rowObj.oaMailEmpInboxid.oaMailInboxAffix != "undefined"&& rowObj.oaMailEmpInboxid.oaMailInboxAffix.length>0){
		var cs = rowObj.oaMailEmpInboxid.oaMailInboxAffix.split(",");
		count = cs.length;
	}
	return count;
}


</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("收件箱");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    btnList.add(new SysGridBtnBean("设置已读","setMailRead()","058.gif"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaMailEmpIsread","状态",1,2,1,0));
    sccList.add(new SysColumnControl("oaMailEmpInboxid.oaMailInboxIsurgent","重要紧急",1,2,1,0));
    sccList.add(new SysColumnControl("oaMailEmpInboxid.oaMailInboxTitle","邮件主题",1,1,1,15));
    sccList.add(new SysColumnControl("oaMailEmpInboxid.oaMailInboxSendName","发件人",1,1,1,0));
    
    sccList.add(new SysColumnControl("oaMailEmpInboxid.oaMailInboxIntime","收件时间",1,2,1,0));
  
    sccList.add(new SysColumnControl("oaMailEmpInboxid.oaMailInboxAffix","附件数",2,2,2,0));
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
		
		if("oaMailEmpIsread".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_SMS_INBOX_ISREAD.getSelectAndText("-1,-请选择邮件状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaIsread");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaMailEmpInboxid.oaMailInboxIsurgent".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("-1,-请选择重要紧急-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaIsurgent");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaMailEmpInboxid.oaMailInboxIntime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType(true,UtilWork.getToday());
	bc.setColumnTypeClass(date);
		}
	
   }
    colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
    nw.setColumnList(colList);
    nw.setShowProcess(true);
    nw.setProcessMethodName("createMethod");
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
