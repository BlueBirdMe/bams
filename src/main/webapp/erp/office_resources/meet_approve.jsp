<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>会议审批</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var meetapply = getQueryParam();
	var pager = getPager();
	
	dwrOfficeResourcesService.listMeetapplys(meetapply,pager,queryCallback);
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
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/meetapply_detail.jsp?nid='+obj.value,'750','550');
		box.msgtitle="<b>会议申请明细列表</b>";
		box.show();
	}


function setedit(id,status){

  dwrOfficeResourcesService.endAndStarMeetapplyByPks(id,status,setcallback);
}

function setcallback(data){
	alertmsg(data,"queryData()");
}

function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='会议审批' onclick=\"setedit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";

	return str;

	
}

//列显示替换方法
function repleaStatus(rowObj){
	var str="";
	if(rowObj.oaMeetapplyStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value%>){
		str= "<font style='color:#cccc'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value)%></font>"
	}else if(rowObj.oaMeetapplyStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value%>) {
		str= "<font style='color:blue'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value)%></font>"
	}else{
	   str= "<font style='color:red'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value)%></font>"
	}
	return str;
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
bg.setTableTitle("会议审批");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("oaMeetapplyName","会议名称",1,1,1,20));
sccList.add(new SysColumnControl("oaMeetapplyType","会议类型",2,2,1,0));
sccList.add(new SysColumnControl("library.libraryInfoName","会议类型",1,2,2,0));
sccList.add(new SysColumnControl("oaMeetapplyEmp","申请人",1,1,1,50));
sccList.add(new SysColumnControl("oaMeetapplySummary","会议纪要人",1,1,1,50));
sccList.add(new SysColumnControl("oaMeetapplyStar","开始时间",1,1,1,50));
sccList.add(new SysColumnControl("oaMeetapplyEnd","结束时间",1,2,2,50)); 
sccList.add(new SysColumnControl("oaMeetapplyStatus","会议状态",1,2,2,50)); 
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaMeetapplyStar".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	
		}
		
		if("oaMeetapplyType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择会议类型-","12"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
		if("oaMeetapplyStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_MEETAPPLY_STATUS.getSelectAndText("-1,-请选择会议状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
}

bg.setColumnList(colList);
bg.setShowProcess(true);
bg.setProcessMethodName("createMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>