<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<title>发件箱</title>
<script>
//查询方法
function queryData(){
    startQuery();
	var oaSmsSend = getQueryParam();
    var pager = getPager();
	dwrMoblieSmsService.listOaSmsSend(oaSmsSend,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
	
//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/mobile_sms/outbox_detail.jsp?oaSmsSendid='+obj.value+'&noid=1','800','500');
	box.msgtitle="<b>查看短信明细</b>";
	var butarray = new Array();
	   butarray[0] = "ok|reback(1,"+obj.value+",'"+box.dialogId+"');| 重 发 ";
	   butarray[1] = "ok|reback(2,"+obj.value+",'"+box.dialogId+"');| 转 发 ";
	butarray[2] = "cancel|| 关  闭";
	box.buttons = butarray;
	box.show();
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
	dwrMoblieSmsService.deleteOaSmsSendByPks(recordsPks,setcallback);
}
	
function setcallback(data){
	alertmsg(data);
	queryData();
}
	
function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrMoblieSmsService.deleteOaSmsSendByPks(ids,setcallback);
}


function sendAgain(oaSmsSendid){
    confirmmsg("确定要重发短信吗?","send('"+oaSmsSendid+"')");
}

function send(oaSmsSendid){
	 dwrMoblieSmsService.sendAgainOaSmsSend(oaSmsSendid,sendAgainback);
}

function sendAgainback(data){
  	 alertmsg(data,"queryData()");
}

function sendSwitch(oaSmsSendid){
	var url = "<%=contextPath%>/erp/mobile_sms/send_sms.jsp?oaSmsSendid="+oaSmsSendid+"&type=2";
	openMDITab(url);
}
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

function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='重发' onclick=\"sendAgain('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/chongfa.gif' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='转发' onclick=\"sendSwitch('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/package_go.png' border='0' width='13' height='13'/></a>";
		 str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0' /></a>";
	return str;
}  

</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("发件箱(默认显示为当月记录)");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaSmsSendAcpempName","收件人",1,1,1,15));
    sccList.add(new SysColumnControl("oaSmsSendTime","发送时间",1,2,1,0));
    sccList.add(new SysColumnControl("oaSmsSendContent","短信内容",1,1,2,20));
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
		if("oaSmsSendTime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType(true,UtilWork.getToday());
	bc.setColumnTypeClass(date);
		}
	}
	
   }
   /*
   String firstdate=UtilWork.getFirstDateOfMonth();
     String enddate = UtilWork.getEndDateOfMonth(); 
     OtherType other = new OtherType("<input type ='text' class ='Wdate' readonly='readonly' id ='hrmtest1' onClick='WdatePicker()' value='"+firstdate+"' />&nbsp;至&nbsp;<input type ='text' class ='Wdate' id ='hrmtest2' readonly='readonly' onClick='WdatePicker()' value='"+enddate+"' />");//自定义对象
     other.setGetValueMethod("getother()");
     colList.add(ColumnUtil.getCusterAdvancedColumn("oaSmsSendTime","发送日期",other));
    */
    nw.setColumnList(colList);
    nw.setShowProcess(true);
    nw.setProcessMethodName("createMethod");
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
