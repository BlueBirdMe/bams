<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<title>工作查询</title>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var bean = getQueryParam();
	var pager = getPager();
	dwrPersonalProcessService.findHistoryTasks(bean, pager, queryCallback);
}

function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
		showQueryParam();
	} else {
		alert(data.message);
	}
	endQuery();
}

function dblCallback(obj) {
	
}

function repProcessStatus(rowObj){
	var	str="";
	if(rowObj.instanceEndTime == null){
		str = "<font color='red'><%=EnumUtil.PROCESS_STATUS.valueOf(EnumUtil.PROCESS_STATUS.DOING.value)%></font>";
	}else{
		str = "<font color='green'><%=EnumUtil.PROCESS_STATUS.valueOf(EnumUtil.PROCESS_STATUS.FINISH.value)%></font>";
	}
	return str;
}

function repWorkName(rowObj){
	var str = "<a href=\"javascript:void(0);\" onclick=\"showDetail('"+rowObj.config.detailPage+"','"+rowObj.historicProcessInstance.businessKey+"','"+rowObj.historicProcessInstance.id+"');\">"+rowObj.workName+"</a>";
	return str;
}

function showDetail(detailUrl, id, processInstanceId){
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+id+"&processInstanceId="+processInstanceId;
	var box = box = new Sys.msgbox('明细查看', url);
	box.show();
}

function repEmployeeName(obj){
	return "<a href=\"javascript:showApplyer('" + obj.employee.primaryKey + "');\">" + obj.employee.hrmEmployeeName + "</a>";
}

function showApplyer(id){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+id,'800','500');
	box.show();
}

</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
	bg.setTableTitle("工作查询");
	//设置附加信息
	bg.setQueryFunction("queryData");	//查询的方法名
	bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey");		//双击列的绑定的列值
	
	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("historicProcessInstance.id","流水号",1,2,2,0));
	sccList.add(new SysColumnControl("id","流水号",2,1,1,0));
	sccList.add(new SysColumnControl("key","流程类型",2,2,1,0));
	sccList.add(new SysColumnControl("scope","流程范围",2,2,1,0));
	sccList.add(new SysColumnControl("workName","工作名称/文号",1,2,2,0));
	sccList.add(new SysColumnControl("employee.hrmEmployeeName","发起人",1,2,2,0));
	sccList.add(new SysColumnControl("instanceStartTime","流程开始时间",1,2,2,0));
	sccList.add(new SysColumnControl("instanceEndTime","流程结束时间",1,2,2,0));
	sccList.add(new SysColumnControl("processStatus","流程状态",1,2,1,0));
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		
		if("workName".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repWorkName");
			bc.setColumnStyle("text-align:center");
		}
		
		if("employee.hrmEmployeeName".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repEmployeeName");
			bc.setColumnStyle("text-align:center");
		}
		
		if("processStatus".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.PROCESS_STATUS.getSelectAndText("-1,-请选择流程状态-"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
			bc.setColumnReplace("repProcessStatus");
			bc.setColumnStyle("text-align:center");
		}
		
		if("key".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(UtilTool.getProcessTypeString(this.getServletContext(), request, "-1,-请选择流程类型-"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
		}
		
		if("scope".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.WORKFLOW_SCOPE.getSelectAndText("-1,-请选择流程范围-"));
			select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
			bc.setColumnTypeClass(select);
		}
	}
	bg.setColumnList(colList);
	bg.setShowProcess(false);
	bg.setShowImg(false);
	bg.setCheckboxOrNum(false);
	bg.setProcessMethodName("createProcessMethod");
	//开始创建
	out.print(bg.createTable());
%>
</body>
</html>