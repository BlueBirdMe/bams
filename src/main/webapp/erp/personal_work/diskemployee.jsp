<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
    <%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>磁盘管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrNetdiskService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var oaNetdisk = getQueryParam();
	oaNetdisk.hrmEmployee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var pager = getPager();
	dwrNetdiskService.listOaNetdisk(oaNetdisk,pager,queryCallback);
}
//回调方法
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
	//MoveDiv.show('查看人员明细(附件区可右键另存为下载)','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value);
	var box = new Sys.msgbox('查看人员明细','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value,800,500);
	box.msgtitle='附件区可右键另存为下载';
	box.show();
}
function add(id){
	if(getAllRecordArray()){
		confirmmsg('批量设置将不能加载人员现有磁盘大小，确定吗?',"addok()");
	}else {
		alertmsg("请选择要修改人...");
	}
}

function addok(){
	MoveDiv.show('设置磁盘大小(大小只能为整数，并且不能小于已使用空间)','<%=contextPath%>/erp/personal_work/disk_update.jsp?ids='+getAllRecordArray());
}

function update(id){
	MoveDiv.show('设置磁盘大小','<%=contextPath%>/erp/personal_work/disk_update.jsp?ids='+id);
}

function createProcessMethod(rowObj){
	var str="";
	str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	return str;
}

//列显示替换方法
function repleaSex(rowObj){
	var str="";
	if(rowObj.hrmEmployeeSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<font style='color:blue'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%></font>"
	}
	return str;
}
//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}
function repleaCol(rowObj){
	var str="";
	rowObj.usedSpace=FormatNumber(rowObj.usedSpace,2);
	var s = rowObj.usedSpace/rowObj.totalSpace;
	var len = 50;
	if(rowObj.totalSpace>=500){
		len=30;
	}
	if(rowObj.totalSpace>=1000){
		len =10;
	}
	var w = <%=UtilTool.getSysParamByIndex(request,"erp.Net.Disk")%>;
	var vl = rowObj.totalSpace/w*len;
	var bc = s*vl;
	str="<div style='width:"+vl+"px;' class='divbgcol'><div style='width:"+bc+"px;' class='divspan4'></div></div>";
	str+="&nbsp;&nbsp;<font style='font:12px;'>"+rowObj.usedSpace+"/"+(rowObj.totalSpace-rowObj.usedSpace)+"</font>";
	return str;
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title">选择部门</div>
<div class="div_content">
<jsp:include page="../hrm/departmentxmlshow.jsp" flush="false">
	<jsp:param name="ischeck" value="true"/>
</jsp:include>
</div>
</td>
<td>
<%
	SysGrid bg =new SysGrid(request);
bg.setTableTitle("部门人员");
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("hrmEmployeeId");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("设置大小","add()","sport_tennis.png"));
bg.setBtnList(btnList);

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("hrmEmployee.hrmEmployeeName","姓名",1,1,2,0));
sccList.add(new SysColumnControl("hrmEmployee.hrmEmployeeCode","工号",1,1,2,0));
sccList.add(new SysColumnControl("hrmEmployee.hrmDepartment.hrmDepName","部门",1,2,2,0));
sccList.add(new SysColumnControl("usedSpace","已使用M/剩余M",1,2,2,0));
sccList.add(new SysColumnControl("totalSpace","总共M",1,2,2,0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("totalSpace".equalsIgnoreCase(bc.getDataName())){
	//列样式
	bc.setColumnStyle("text-align:center;");
		}
		if("usedSpace".equalsIgnoreCase(bc.getDataName())){
	
	bc.setColumnReplace("repleaCol");
	bc.setColumnStyle("text-align:left;padding-left:10px;padding-top:0px;padding-bottom:0px;margin: 0px;");
		}
	}
}

bg.setColumnList(colList);
//设置列操作对象
bg.setShowProcess(true);//默认为false 为true请设置processMethodName
bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

//设置图片显示信息
//bg.setImgShowNum(6);//不指定默认5个
bg.setImgShowUrl("hrmEmployee.hrmEmployeeImageInfoId");//显示img的属性字段，没有填写-1
bg.setImgShowText("hrmEmployee.hrmEmployeeName");
bg.setImgNoDefaultPath(absPath+"/images/noimages/employee.png");//可以不指定，系统采用默认暂无图片
bg.setImgheight("64");//不设置为自动
//开始创建
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
</body>
</html>