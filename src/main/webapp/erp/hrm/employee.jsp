<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var employee = getQueryParam();
	employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var pager = getPager();
	dwrHrmEmployeeService.listAllEmployees(employee,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
		showQueryParam();
	}else{
		alert(data.message);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value,'800','500');
	box.msgtitle="<b>查看人员信息明细</b>";
	box.show();
}

function update(employeepk){
	openMDITab("<%=contextPath%>/erp/hrm/employee_add.jsp?employeepk="+employeepk+"&tab="+getMDITab());
}

function separation(id){
	confirmmsg("确定要将人员设置为离职吗?","SeparationOk('"+id+"')");
}

function SeparationOk(id){
	var ids = new Array();
	ids[0] = id;
	dwrHrmEmployeeService.setSeparationByIds(ids,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

function deletebatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要批量设置离职吗?","delbatchok()");
	}else{
		alertmsg("请选择要设置离职的人员...");
	}
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrHrmEmployeeService.setPinYinMaByIds(recordsPks,delcallback);
}

function createProcessMethod(rowObj){
	var str="";
	if(rowObj.hrmEmployeeStatus == <%=EnumUtil.HRM_EMPLOYEE_STATUS.Separation.value%>){
	    str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置拼音码' onclick=\"setPYM('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/setpym.png' border='0'/></a>";
	    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='不能设置离职'\"><img src='<%=contextPath%>/images/grid_images/seta_.png' border='0'/></a>";
	}else{
	    str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置拼音码' onclick=\"setPYM('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/setpym.png' border='0' /></a>";
	    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置离职' onclick=\"separation('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/seta.png' border='0'/></a>";
	}
	return str;
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

function repAction(rowObj){
	var str="";
	if(rowObj.hrmEmployeeActive == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str ="<font color='green'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%></font>";
	}else{
		str ="<font color='red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}
	return str;
}

//部门树选择
function treeclick(code){
    
	document.getElementById("upcode").value =code;
	queryData();
}

function setPYM(id){
	confirmmsg("确定要设置拼音码?","setPYMOK('"+id+"')");
}

function setPYMOK(id){
	var ids = new Array();
	ids[0] = id;
	dwrHrmEmployeeService.setPinYinMaByIds(ids,delcallback);
}

function setBatchPYM(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要批量设置拼音码吗?","setBatchPYMOK()");
	}else{
		alertmsg("请选择要设置拼音码的人员...");
	}
}

function setBatchPYMOK(){
	var ids = getAllRecordArray();
	dwrHrmEmployeeService.setPinYinMaByIds(ids,delcallback);
}

//导出
function exportEmployee(){
	confirmmsg("确定要导出列表中的人员信息吗?","exportok()");
}

function exportok(){
	useLoadingMassage();
	var employee = getQueryParam();
	location.href= "<%=contextPath %>/exportEmployee.do?hrmEmployeeSex="+employee.hrmEmployeeSex;
}

/*
 * 打印报表的方法
*/
function print(){
	if(getAllRecordArray() != false){	
		if(getAllRecordArray().length > 1){
		   alertmsg("每次只能选择一条数据打印！");
		   return;
		}
		openMDITab('<%=contextPath%>/erp/report_show.jsp?raq=employee.raq&macrozgh=\\\'' + getAllRecordArray()[0] + '\\\'');
	} else {
		alertmsg("请选择要打印的人员...");
	}
}
</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
		<tr>
			<td id="split_l">
				<div class="div_title">选择部门</div> 
				<div class="div_content">
				<jsp:include page="departmentxmlshow.jsp" flush="false">
					<jsp:param name="ischeck" value="true" />
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

					//放入按钮
					ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
					btnList.add(new SysGridBtnBean("批量设置离职", "deletebatch()", "close.png"));
					btnList.add(new SysGridBtnBean("批量设置名字拼音码", "setBatchPYM()", "pinyinmabatch.png"));
					btnList.add(new SysGridBtnBean("导出EXCEL", "exportEmployee()", "exp_excel.gif"));
					btnList.add(new SysGridBtnBean("打印信息", "print()", "printimg.png"));
					bg.setBtnList(btnList);

					bg.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));

					//放入列
					ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
					sccList.add(new SysColumnControl("hrmEmployeeName", "姓名", 1, 1, 1, 0));
					sccList.add(new SysColumnControl("hrmEmployeeCode", "工号", 1, 1, 1, 0));
					sccList.add(new SysColumnControl("hrmDepartment.hrmDepName", "部门", 1, 2, 2, 0));
					sccList.add(new SysColumnControl("hrmEmployeeSex", "性别", 1, 2, 1, 0));
					sccList.add(new SysColumnControl("hrmEmployeeBirthday", "出生日期", 1, 2, 1, 0));
					sccList.add(new SysColumnControl("hrmEmployeeInTime", "入职日期", 1, 2, 1, 0));
					sccList.add(new SysColumnControl("hrmEmployeeActive", "员工状态", 1, 2, 1, 0));
					ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
					//进行高级查询显示处理
					for (int i = 0; i < colList.size(); i++) {
						SysGridColumnBean bc = colList.get(i);
						if (bc.isShowAdvanced() || bc.isShowColumn()) {
							if ("hrmEmployeeInTime".equalsIgnoreCase(bc.getDataName())) {
								DateType date = new DateType();
								bc.setColumnTypeClass(date);
								bc.setColumnStyle("padding-left:15px;");
							}
							if ("hrmEmployeeBirthday".equalsIgnoreCase(bc.getDataName())) {
								DateType date = new DateType();
								bc.setColumnTypeClass(date);
								bc.setColumnStyle("padding-left:15px;");
							}
							if ("hrmEmployeeCode".equalsIgnoreCase(bc.getDataName())) {
								bc.setColumnStyle("padding-left:15px;text-align: left");
							}
							if ("hrmEmployeeSex".equalsIgnoreCase(bc.getDataName())) {
								SelectType select = new SelectType(EnumUtil.HRM_EMPLOYEE_SEX.getSelectAndText("-1,-请选择人员性别-"));
								select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
								bc.setColumnTypeClass(select);
								bc.setColumnReplace("repleaSex");
								bc.setColumnStyle("text-align:center;");
							}

							if ("hrmEmployeeActive".equalsIgnoreCase(bc.getDataName())) {
								SelectType select = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择是否有效-"));
								select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
								bc.setColumnTypeClass(select);

								bc.setColumnReplace("repAction");
							}
						}
					}

					bg.setColumnList(colList);
					//设置列操作对象
					bg.setShowProcess(true);//默认为false 为true请设置processMethodName
					bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

					//设置图片显示信息
					//bg.setImgShowNum(6);//不指定默认5个
					bg.setImgShowUrl("hrmEmployeeImageInfoId");//显示img的属性字段，没有填写-1
					bg.setImgShowText("hrmEmployeeName");
					bg.setImgNoDefaultPath(absPath + "/images/noimages/employee.png");//可以不指定，系统采用默认暂无图片
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