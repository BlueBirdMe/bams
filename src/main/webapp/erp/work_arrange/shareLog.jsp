<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>共享日志</title>
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var worklog = getQueryParam();
	var pager = getPager();
	
	var hrmEmployee = getQueryParam();
	hrmEmployee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	worklog.hrmEmployee = hrmEmployee;
	
	worklog.oaWorklogDate = getother();	
	dwrWorkArrangeService.listOaShareWorkLog(worklog,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/work_arrange/myLog_detail.jsp?worklogpk='+obj.value,'700','550');
	box.msgtitle="<b>共享日志明细查看</b>";
	box.show();
}
	
function getother(){
	var date = document.getElementById("hrmtest1").value+","+document.getElementById("hrmtest2").value;
	if(document.getElementById("hrmtest1").value == ""){
		date = document.getElementById("hrmtest2").value;
	}else if(document.getElementById("hrmtest2").value == ""){
		date = document.getElementById("hrmtest1").value;
	}
	return date;
}
   //列显示替换方法
function repleaRange(rowObj){
	var str="";
	if(rowObj.oaWorklogRange ==<%=EnumUtil.OA_WORKLOG_RANGE.one.value%>){
		str= "<font style='color:blue'><%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.one.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.two.value)%></font>"
	}
	return str;
}

//部门选择
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
							<div class="div_title">选择部门</div>
							<div class="div_content">
							<jsp:include page="../hrm/departmentxmlshow.jsp" flush="false">
								<jsp:param  name="ischeck" value="true"/>
							</jsp:include>
							</div>
</td>
<td>
					<%
						SysGrid grid = new SysGrid(request, "共享日志列表");
									grid.setShowImg(false);//不显示图片信息.
									grid.setCheckboxOrNum(false);
									//放入按钮
									/*ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
									btnList.add(new SysGridBtnBean("新增","add()","add.png"));
									btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
									btnList.add(new SysGridBtnBean("修改","update()","edit.png"));
									btnList.add(new SysGridBtnBean("查看","info()","info.png"));
									grid.setBtnList(btnList);
									 */
									//放入列
									//ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-类型"));
									ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
									sccList.add(new SysColumnControl("oaWorklogTitle", "日志标题", 1, 1, 1, 30));
									sccList.add(new SysColumnControl("oaWorklogType", "日志类型", 2, 2, 1, 0));
									sccList.add(new SysColumnControl("library.libraryInfoName", "日志类型", 1, 2, 2, 0));
									sccList.add(new SysColumnControl("hrmEmployee.hrmEmployeeName", "所属人", 1, 1, 1, 0));
									sccList.add(new SysColumnControl("oaWorklogDate", "日志日期", 1, 2, 2, 0));
									ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

									//进行高级查询显示处理
									for (int i = 0; i < colList.size(); i++) {
										SysGridColumnBean bc = colList.get(i);
										if (bc.isShowAdvanced() || bc.isShowColumn()) {

											if ("oaWorklogType".equalsIgnoreCase(bc.getDataName())) {
												//设置高级查询显示样式

												SelectType select = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(), request, "-1,-请选择日志类型-", "04"));
												select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
												bc.setColumnTypeClass(select);
												//设置列显示样式
												bc.setColumnStyle("text-align:center;");
											}
										}
									}
									//放入自定义高级查询对象
									String firstdate = UtilWork.getFirstDateOfMonth();
									String enddate = UtilWork.getEndDateOfMonth();
									OtherType other = new OtherType("<input type ='text' class ='Wdate' readonly='readonly' id ='hrmtest1' onClick='WdatePicker()' value='" + firstdate
											+ "' />&nbsp;至&nbsp;<input type ='text' class ='Wdate' id ='hrmtest2' readonly='readonly' onClick='WdatePicker()' value='" + enddate + "' />");//自定义对象
									other.setGetValueMethod("getother()");
									colList.add(ColumnUtil.getCusterAdvancedColumn("oaWorklogDate", "日志日期", other));

									grid.setColumnList(colList);

									//设置附加信息
									grid.setQueryFunction("queryData"); //查询的方法名
									grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
									grid.setDblBundle("primaryKey"); //双击列的绑定的列值

									//开始创建
									out.print(grid.createTable());
					%>
</td>
</tr>
</table>
		
<input type="hidden" id="upcode">
	</body>
</html>