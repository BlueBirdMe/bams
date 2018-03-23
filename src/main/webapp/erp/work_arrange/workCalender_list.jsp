<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的日程</title>
		<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var oaCalender = getQueryParam();
	var pager = getPager();
	dwrWorkArrangeService.getOaCalenderList(oaCalender,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
	
function update(calenderpk){
	MoveDiv.show('编辑',"<%=contextPath%>/erp/work_arrange/workCalender_add.jsp?calenderpk='"+calenderpk+"'");
}

function del(id){
	confirmmsg("确定要删除记录吗?","delok('"+id+"')");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrWorkArrangeService.deleteOaCalenderByPks(ids,setcallback);
}
	
function setcallback(data){
	alertmsg(data,"queryData()");
}
	
function deletebatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrWorkArrangeService.deleteOaCalenderByPks(recordsPks,setcallback);
}
	function complete(pk){
	dwrWorkArrangeService.completeOaCalenderByPks(pk,setcallback);
}
	
//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/work_arrange/workCalender_detail.jsp?calenderpk='+obj.value,'800','500');
	box.msgtitle="<b>工作安排明细查看</b>";
	box.show();
}
   
//列显示替换方法
function repleaLevel(rowObj){
	var str="";
	if(rowObj.oaCalenderLevel ==<%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
		str= "<img title ='重要/紧急' src='<%=contextPath%>/images/grid_images/zyjj.png' border='0'/>" ;
	}else if(rowObj.oaCalenderLevel ==<%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
		str= "<img title ='重要/不紧急' src='<%=contextPath%>/images/grid_images/zybjj.png' border='0'/>" ;
	}else if(rowObj.oaCalenderLevel ==<%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
		str= "<img title ='不重要/紧急' src='<%=contextPath%>/images/grid_images/bzyjj.png' border='0'/>" ;
	}else{
		str= "<img title ='不重要/不紧急' src='<%=contextPath%>/images/grid_images/bzybjj.png' border='0'/>" ;
	}
	return str;
}

function repleaStatus(rowObj){
	var str="";
	if(rowObj.oaCalenderStatus ==<%=EnumUtil.OA_CALENDER_STATUS.one.value%>){
		str= "<font style='color:#00BD00'><%=EnumUtil.OA_CALENDER_STATUS.valueOf(EnumUtil.OA_CALENDER_STATUS.one.value)%></font>"
	}else{
		str= "<font style='color:#EC6907'><%=EnumUtil.OA_CALENDER_STATUS.valueOf(EnumUtil.OA_CALENDER_STATUS.two.value)%></font>"
	}
	return str;
}

function createMethod(rowObj){
	var str="";
	if(rowObj.oaCalenderStatus ==<%=EnumUtil.OA_CALENDER_STATUS.one.value%>){
		str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='重新启用' onclick=\"complete('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/tick_.png' border='0' width='13' height='13'/></a>";
	}else{
		str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='完成工作' onclick=\"complete('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/tick.png' border='0' width='13' height='13'/></a>";
	}
	return str;
}
</script>
</head>
<body>
<%
SysGrid grid = new SysGrid(request, "我的日程列表(默认显示当月日程)");
grid.setShowImg(false);//不显示图片信息.
//放入按钮
ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();

btnList.add(new SysGridBtnBean("批量删除", "deletebatch()", "close.png"));
grid.setBtnList(btnList);
//放入列

ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("oaCalenderLevel", "日程等级", 1, 2, 1, 0));
sccList.add(new SysColumnControl("oaCalenderType", "日程类型", 2, 2, 1, 0));
sccList.add(new SysColumnControl("library.libraryInfoName", "日程类型", 1, 2, 2, 0));
sccList.add(new SysColumnControl("oaCalenderStart", "开始时间", 1, 2, 1, 0));
sccList.add(new SysColumnControl("oaCalenderEnd", "结束时间", 1, 2, 2, 0));
sccList.add(new SysColumnControl("oaCalenderStatus", "日程状态", 1, 2, 1, 0));
sccList.add(new SysColumnControl("oaCalenderContent", "日程内容", 1, 1, 2, 20));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for (int i = 0; i < colList.size(); i++) {
	SysGridColumnBean bc = colList.get(i);
	if (bc.isShowAdvanced() || bc.isShowColumn()) {

		if ("oaCalenderLevel".equalsIgnoreCase(bc.getDataName())) {
			//设置高级查询显示样式

			SelectType select = new SelectType(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("-1,-请选择日程等级-"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
			bc.setColumnReplace("repleaLevel");
			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
		if ("oaCalenderStatus".equalsIgnoreCase(bc.getDataName())) {
			//设置高级查询显示样式

			SelectType select = new SelectType(EnumUtil.OA_CALENDER_STATUS.getSelectAndText("-1,-请选择日程状态-"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
			bc.setColumnReplace("repleaStatus");
			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
		if ("oaCalenderType".equalsIgnoreCase(bc.getDataName())) {
			//设置高级查询显示样式

			SelectType select = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(), request, "-1,-请选择日程类型-", "10"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
		if ("oaCalenderStart".equalsIgnoreCase(bc.getDataName())) {
			//设置高级查询显示样式

			DateType date = new DateType();
			bc.setColumnTypeClass(date);
			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
	}
}

grid.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));

grid.setColumnList(colList);
grid.setShowProcess(true);
grid.setProcessMethodName("createMethod");
//设置附加信息
grid.setQueryFunction("queryData"); //查询的方法名
grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
grid.setDblBundle("primaryKey"); //双击列的绑定的列值

//开始创建
out.print(grid.createTable());
%>

	</body>
</html>