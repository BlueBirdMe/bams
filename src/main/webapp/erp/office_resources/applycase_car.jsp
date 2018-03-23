<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>车辆使用情况</title>
<script>
//查询方法
function queryData() {
	startQuery();
	var caruse = getQueryParam();
	var pager = getPager();
	dwrOfficeResourcesService.listCaruses(caruse, pager, queryCallback);
}
function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alert(data.message);
	}
	endQuery();
} //双击数据
function dblCallback(obj) {
	//MoveDiv.show('明细查看','<%=contextPath%>/erp/office_resources/car_use_detail.jsp?cid='+obj.value);
	var box = new Sys.msgbox('明细查看', '<%=contextPath%>/erp/office_resources/car_use_detail.jsp?cid=' + obj.value, '700', '500');
	box.msgtitle = "<b>车辆使用明细列表</b>";
	box.show();
}

function edit(id) {
	var obj = getObjectByPk(id);
	var str = "employee[" + obj.primaryKey + "]:\n\n\thrmEmployeeName = " + obj.hrmEmployeeName + "\n\n\thrmEmployeeInTime = " + obj.hrmEmployeeInTime + "\n\n";
	alert(str);
}


function delbatchok() {
	var recordsPks = getAllRecordArray();
	dwrOfficeResourcesService.deleteCarusesByPks(recordsPks, delcallback);
}
function createProcessMethod(rowObj) {
	var str ="";
	if(rowObj.oaCarStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value%>){
	str = "<a href='javascript:void(0)' title='编辑' onclick=\"update('" + rowObj.primaryKey + "')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('" + rowObj.primaryKey + "')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
}else if(rowObj.oaCarStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value%>) {
	str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='车辆使用中不能编辑'><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0' style='filter:gray'/></a>";
		str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='车辆使用中不能删除' ><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0' style='filter:gray'/></a>";
}else{
        str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='车辆使用中不能编辑'><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0' style='filter:gray'/></a>";
		str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='车辆使用中不能删除' ><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0' style='filter:gray'/></a>";
}
	
	return str;
}
function del(pk) {
	confirmmsg("确定要删除记录吗?", "delok(" + pk + ")");
}
function repleaStatus(rowObj){
var str="";
if(rowObj.oaCarStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value%>){
	str= "<font style='color:#cccc'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value)%></font>"
}else if(rowObj.oaCarStatus ==<%=EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value%>) {
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
//设置高度及标题
bg.setTableTitle("车辆使用");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
bg.setCheckboxOrNum(false);

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
   //	 btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
 	 bg.setBtnList(btnList);
//放入列
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
     sccList.add(new SysColumnControl("applyEmployee.hrmEmployeeName","用车人",1,1,1,20));
     sccList.add(new SysColumnControl("oaCar.oaCarName","车辆名称",1,1,1,50));
     sccList.add(new SysColumnControl("applyBegindate","用车时间",1,2,1,50));
     sccList.add(new SysColumnControl("applyEnddate","还车时间",1,2,1,50));
     sccList.add(new SysColumnControl("applyNum","用车人数",1,2,2,50));
     sccList.add(new SysColumnControl("oaCarStatus","使用状态",1,2,2,50));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("applyBegindate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	date.setDateFmt("yyyy-MM-dd HH:mm");
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
	bc.setColumnStyle("text-align:center;");
		}	
	if("applyEnddate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	date.setDateFmt("yyyy-MM-dd HH:mm");
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaCarStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_MEETAPPLY_STATUS.getSelectAndText("-1,-请选择状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repleaStatus");	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("applyNum".equalsIgnoreCase(bc.getDataName())){
	bc.setColumnStyle("text-align:center;");
		}
	
	}
}

bg.setColumnList(colList);
//设置列操作对象
bg.setShowProcess(false);//默认为false 为true请设置processMethodName
//bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象
//开始创建
out.print(bg.createTable());
%>

</body>
</html>