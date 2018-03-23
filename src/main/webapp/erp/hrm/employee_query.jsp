<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人事管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var employee = getQueryParam();
	employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var pager = getPager();
	dwrHrmEmployeeService.listEmployees(employee,pager,queryCallback);
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
	//MoveDiv.show('查看人员信息明细','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value);
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value,'750','550');
		box.msgtitle="<b>查看人员信息明细</b>";
		box.show();
}

//列显示替换方法
function repleaSex(rowObj){
	var str="";
	if(rowObj.hrmEmployeeSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<font style='color:blue'><%=EnumUtil.HRM_EMPLOYEE_SEX
							.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.HRM_EMPLOYEE_SEX
							.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%></font>"
	}
	return str;
}

//部门树选择
function treeclick(code){
    
	document.getElementById("upcode").value =code;
	queryData();
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
	<div class="div_title">选择机构</div>
	<div class="div_content">
	<jsp:include page="departmentxmlshow.jsp" flush="false">
		<jsp:param name="ischeck" value="true"/>
	</jsp:include>
	</div>
</td>
<td>
<%
SysGrid bg = new SysGrid(request);
bg.setTableTitle("部门人员");

//设置附加信息
bg.setQueryFunction("queryData"); //查询的方法名
bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey"); //双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("hrmEmployeeName", "姓名", 1, 1, 1,
		0));
sccList.add(new SysColumnControl("hrmEmployeeCode", "工号", 1, 1, 1,
		0));
sccList.add(new SysColumnControl("hrmDepartment.hrmDepName", "部门",
		1, 2, 2, 0));
sccList
		.add(new SysColumnControl("hrmEmployeeSex", "性别", 1, 2, 1,
				0));
sccList.add(new SysColumnControl("hrmEmployeeBirthday", "出生日期", 1,
		2, 1, 0));
sccList.add(new SysColumnControl("hrmEmployeeInTime", "入职日期", 1, 2,
		1, 0));
ArrayList<SysGridColumnBean> colList = UtilTool
		.getGridColumnList(sccList);
//进行高级查询显示处理
for (int i = 0; i < colList.size(); i++) {
	SysGridColumnBean bc = colList.get(i);
	if (bc.isShowAdvanced() || bc.isShowColumn()) {
		if ("hrmEmployeeInTime".equalsIgnoreCase(bc.getDataName())) {
			//高级查询显示
			DateType date = new DateType();
			bc.setColumnTypeClass(date);
			//列样式
			bc.setColumnStyle("padding-left:15px;");
		}
		if ("hrmEmployeeBirthday"
				.equalsIgnoreCase(bc.getDataName())) {
			//高级查询显示
			DateType date = new DateType();
			bc.setColumnTypeClass(date);
			//列样式
			bc.setColumnStyle("padding-left:15px;");
		}
		if ("hrmEmployeeCode"
				.equalsIgnoreCase(bc.getDataName())) {
			//列样式
			bc.setColumnStyle("padding-left:15px;text-align: left");
		}
		if ("hrmEmployeeSex".equalsIgnoreCase(bc.getDataName())) {
			//设置高级查询显示样式

			SelectType select = new SelectType(
					EnumUtil.HRM_EMPLOYEE_SEX
							.getSelectAndText("-1,-请选择人员性别-"));
			select
					.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);

			bc.setColumnReplace("repleaSex");

			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
	}
}

bg.setColumnList(colList);

//设置图片显示信息
//bg.setImgShowNum(6);//不指定默认5个
bg.setImgShowUrl("hrmEmployeeImageInfoId");//显示img的属性字段，没有填写-1
bg.setImgShowText("hrmEmployeeName");
bg.setImgNoDefaultPath(absPath + "/images/noimages/other.png");//可以不指定，系统采用默认暂无图片
bg.setImgheight("128");//不设置为自动
//开始创建
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
</body>
</html>