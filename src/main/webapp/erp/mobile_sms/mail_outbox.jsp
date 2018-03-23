<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<title>发件箱</title>
<script>
//查询方法
function queryData(){
    startQuery();
	var oaMailSend = getQueryParam();
	oaMailSend.oaMailSendTime = getother();
  	var pager = getPager();
	dwrMailService.listOaMailSend(oaMailSend,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
	
function deleteObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","del()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}
	
function del(){
	var recordsPks = getAllRecordArray();
	dwrMailService.deleteOaMailSendByPks(recordsPks,setcallback);
}
	
function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrMailService.deleteOaMailSendByPks(ids,setcallback);
}

function setcallback(data){
	alertmsg(data,"queryData()");
}

  //双击数据
function dblCallback(obj){
   var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/mail_outbox_detail.jsp?mailoutid='+obj.value,'800','500');
	box.msgtitle="<b>邮件信息明细查看</b>";
	var butarray = new Array();
	butarray[0] = "ok|reback(3,'"+box.dialogId+"');| 重 发 ";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
}

function createMethod(rowObj){
     var str = "<a href='javascript:void(0)' title='重发' onclick=\"reSend('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/chongfa.gif' border='0'/></a>&nbsp;&nbsp;";
         str += "<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
} 

function reSend(pk){
   var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype=3&backtype=2&mailempPk="+pk;
   openMDITab(url);
}

function getother(){
	  var a = document.getElementById("hrmtest1").value+","+document.getElementById("hrmtest2").value;
	  return a;
   }
   

function getAcceCount(rowObj){
		var count =0 ;
		if(rowObj.oaMailSendAffix!=null&&rowObj.oaMailSendAffix != undefined&& rowObj.oaMailSendAffix != "undefined"&&rowObj.oaMailSendAffix.length>0){
			var cs = rowObj.oaMailSendAffix.split(",");
			count = cs.length;
		}
		return count;
	}

//列显示替换方法
function repleaIsurgent(rowObj){
	var str="";
	if(rowObj.oaMailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\" title='重要紧急'/><img src=\"<%=contextPath%>/images/jj.png\"  title='重要紧急'/>";
	}else if(rowObj.oaMailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
		str= "<img src=\"<%=contextPath%>/images/zy.png\"  title='重要'/>";
	}else if(rowObj.oaMailSendIsurgent ==<%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
		str= "<img src=\"<%=contextPath%>/images/jj.png\"  title='紧急'/>";
	}else{
	}
	return str;
}

function repleaReceipt(rowObj){
	var str="";
	if(rowObj.oaMailInboxReceipt ==<%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
		str= "<font color='blue'>是</font>";
	}else{
		str= "否";
	}
	return str;
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("发件箱");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaMailSendIsurgent","重要紧急",1,2,1,0));
    sccList.add(new SysColumnControl("oaMailSendEmpNames","收件人",1,1,1,15));
 	sccList.add(new SysColumnControl("oaMailSendTitle","邮件主题",1,1,1,15));
    sccList.add(new SysColumnControl("oaMailSendTime","发送时间",1,2,2,0));
    sccList.add(new SysColumnControl("oaMailSendAffix","附件数",2,2,2,0));
    sccList.add(new SysColumnControl("oaMailInboxReceipt","要求回执",1,2,2,0));
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
 
 	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		
		if("oaMailSendIsurgent".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	SelectType select  = new SelectType(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("-1,-请选择重要紧急-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaIsurgent");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaMailInboxReceipt".equalsIgnoreCase(bc.getDataName())){
	bc.setColumnReplace("repleaReceipt");
	bc.setColumnStyle("text-align:center;");
		}
	
  	}
   
  	 String firstdate=UtilWork.getFirstDateOfMonth();
     String enddate = UtilWork.getEndDateOfMonth(); 
     OtherType other = new OtherType("<input type ='text' class ='Wdate' readonly='readonly' id ='hrmtest1' onClick='WdatePicker()' value='"+firstdate+"' />&nbsp;至&nbsp;<input type ='text' class ='Wdate' id ='hrmtest2' readonly='readonly' onClick='WdatePicker()' value='"+enddate+"' />");//自定义对象
     other.setGetValueMethod("getother()");
     colList.add(ColumnUtil.getCusterAdvancedColumn("oaMailSendTime","发送日期",other));
  	 colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
  	 nw.setColumnList(colList);
  	 nw.setShowProcess(true);
  	 nw.setProcessMethodName("createMethod");

    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
