<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<title>收件箱</title>
<script>
//查询方法
function queryData(){
   startQuery();
	var oaSmsInbox = getQueryParam();
    var pager = getPager();
	dwrMoblieSmsService.listOaSmsInbox(oaSmsInbox,pager,queryCallback);
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
	dwrMoblieSmsService.deleteOaSmsInboxByPks(recordsPks,setcallback);
}
	
function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}

function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrMoblieSmsService.deleteOaSmsInboxByPks(ids,setcallback);
}
	
function setcallback(data){
	alertmsg(data,"queryData()");
}

   //双击数据
function dblCallback(obj){
	 var rowObj= getObjectByPk(obj.value);
	    //设置已读
	    if(rowObj.oaSmsInboxIsread == <%=EnumUtil.OA_SMS_INBOX_ISREAD.two.value%>){
	    	dwrMoblieSmsService.setOaSmsInboxIsread(obj.value,setDbCallback);
	    }
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/inbox_detail.jsp?oaSmsInboxId='+obj.value+'&noid=1','800','500');
		box.msgtitle="<b>查看短信明细</b>";
		var butarray = new Array()
		if(rowObj.oaSmsType ==<%=EnumUtil.OA_SMS_TYPE.one.value%>){
			butarray[0] = "ok|reback(1,"+obj.value+",'"+box.dialogId+"');| 回  复 ";
	  		butarray[1] = "ok|reback(2,"+obj.value+",'"+box.dialogId+"');| 转  发 ";
	  		butarray[2] = "cancel|| 关  闭";
	   }else{
	   		butarray[0] = "cancel|| 关  闭";
	   }
		box.buttons = butarray;
		box.show();
}

function setDbCallback(){
	queryData();
}

//设置已读
function oaSmsInboxIsread(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要将选中的短信改为已读么?","oaSmsInbox()");
	}else{
		alertmsg("请选择短信。。。");
	}
 
}
function oaSmsInbox(){
	var recordsPks = getAllRecordArray();
	dwrMoblieSmsService.setOaSmsInboxIsread(recordsPks,getOaSmsInboxinfo);
}

function getOaSmsInboxinfo(data){
	alertmsg(data);
  	queryData();
} 

function sendBack(oaSmsInboxid){
	//设置已读
	dwrMoblieSmsService.setOaSmsInboxIsread(oaSmsInboxid,setDbCallback);
	var url = "<%=contextPath%>/erp/mobile_sms/send_sms.jsp?oaSmsInboxbackid="+oaSmsInboxid+"&type=1";
	openMDITab(url);
}

function sendSwitch(oaSmsInboxid){
	//设置已读
	dwrMoblieSmsService.setOaSmsInboxIsread(oaSmsInboxid,setDbCallback);
	var url = "<%=contextPath%>/erp/mobile_sms/send_sms.jsp?oaSmsInboxid="+oaSmsInboxid+"&type=1";
	openMDITab(url);
} 

function createMethod(rowObj){
	var str="";
	if(rowObj.oaSmsType ==<%=EnumUtil.OA_SMS_TYPE.one.value%>){
		str+="<a href='javascript:void(0)' title='回复' onclick=\"sendBack('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/comment.png' border='0'/></a>";
    	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='转发' onclick=\"sendSwitch('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/package_go.png' border='0' width='13' height='13'/></a>";
    }else{
    	str+="<a href='javascript:void(0)' title='系统通知不能回复'><img src='<%=contextPath%>/images/grid_images/comment.png' border='0' style='filter:gray;'/></a>";
    	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='系统通知不能转发' ><img src='<%=contextPath%>/images/grid_images/package_go.png' border='0' width='13' height='13' style='filter:gray;'/></a>";
    }
    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
} 
</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("收件箱(默认显示为当月记录)");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    btnList.add(new SysGridBtnBean("设置已读","oaSmsInboxIsread()","058.gif"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaSmsInboxIsread","状态",1,2,1,0));
    sccList.add(new SysColumnControl("oaSmsInboxSenderName","发件人",1,1,1,15));
    sccList.add(new SysColumnControl("oaSmsInboxSendtime","收件时间",1,2,1,0));
    sccList.add(new SysColumnControl("oaSmsInboxContent","短信内容",1,1,1,25));
    sccList.add(new SysColumnControl("oaSmsType","短信类型",1,2,1,0));
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		
		if("oaSmsType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_SMS_TYPE.getSelectAndText("-1,-请选择短信类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaType");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaSmsInboxIsread".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_SMS_INBOX_ISREAD.getSelectAndText("-1,-请选择短信状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaIsread");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaSmsInboxSendtime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType(true,UtilWork.getToday());
	bc.setColumnTypeClass(date);
		}
	}
	
   }
   /**
   String firstdate=UtilWork.getFirstDateOfMonth();
     String enddate = UtilWork.getEndDateOfMonth(); 
     OtherType other = new OtherType("<input type ='text' class ='Wdate' readonly='readonly' id ='hrmtest1' onClick='WdatePicker()' value='"+firstdate+"' />&nbsp;至&nbsp;<input type ='text' class ='Wdate' id ='hrmtest2' readonly='readonly' onClick='WdatePicker()' value='"+enddate+"' />");//自定义对象
     other.setGetValueMethod("getother()");
     
     colList.add(ColumnUtil.getCusterAdvancedColumn("oaSmsInboxSendtime","发送日期",other));
   */
    nw.setColumnList(colList);
    nw.setShowProcess(true);
    nw.setProcessMethodName("createMethod");
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
<script type="text/javascript">
//列显示替换方法
function repleaType(rowObj){
	var str="";
	if(rowObj.oaSmsType ==<%=EnumUtil.OA_SMS_TYPE.one.value%>){
		str= "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.one.value)%>"
	}else{
		str= "<%=EnumUtil.OA_SMS_TYPE.valueOf(EnumUtil.OA_SMS_TYPE.two.value)%>"
	}
	return str;
}

function repleaIsread(rowObj){
	var str="";
	if(rowObj.oaSmsInboxIsread ==<%=EnumUtil.OA_SMS_INBOX_ISREAD.one.value%>){
		//str= "<font style='color:blue'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.one.value)%></font>"
		str= "<img src=\"<%=contextPath%>/images/smsisread_.gif\" alt='已读短信' title='已读短信'/>"
	}else{
		//str= "<font style='color:red'><%=EnumUtil.OA_SMS_INBOX_ISREAD.valueOf(EnumUtil.OA_SMS_INBOX_ISREAD.two.value)%></font>"
		str= "<img src=\"<%=contextPath%>/images/smsisread.gif\" alt='未读短信' title='未读短信'/>"
	}
	return str;
}	
</script>
</html>
