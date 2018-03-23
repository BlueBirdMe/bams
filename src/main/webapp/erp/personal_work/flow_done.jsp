<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<title>已办工作</title>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var pager = getPager();
	dwrPersonalProcessService.getTaskDone(pager, queryCallback);
}

function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alert(data.message);
	}
	endQuery();
}

function handle(handleUrl,businessKey,taskId,taskDefinitionKey) {
	var url = "<%=contextPath%>/erp/"+handleUrl+"?pk="+businessKey+"&taskId="+taskId+"&definitionKey="+taskDefinitionKey+"&tab="+getMDITab();
	openMDITab(url);
}

function showDetail(detailUrl,businessKey, processInstanceId) {
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+businessKey+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url);
	box.show();
}

function createProcessMethod(rowObj) {
	var str = "<a href='javascript:void(0)' title='撤销刚才的操作' onclick=\"withdraw('" + rowObj.historicTaskInstance.id + "')\">撤销</a>";
	return str;
}

function withdraw(taskId){
	confirmmsg("确定要撤销刚才的操作吗?","withdrawok('"+taskId+"')");
}

function withdrawok(taskId){
	dwrPersonalProcessService.withdrawTask(taskId,withdrawCallback); 
}

function withdrawCallback(data){
	alertmsg(data, "queryData()");
}

function repWorkName(rowObj){
	var str = "<a href=\"javascript:void(0);\" onclick=\"showDetail('"+rowObj.config.detailPage+"','"+rowObj.historicProcessInstance.businessKey+"','"+rowObj.historicProcessInstance.id+"');\">"+
				rowObj.workName + "</a>&nbsp;&nbsp;";
	return str;
}

function showDetail(detailUrl,businessKey, processInstanceId) {
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+businessKey+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url);
	box.show();
}

</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
	bg.setTableTitle("已办工作");
	//设置附加信息
	bg.setQueryFunction("queryData");	//查询的方法名
	bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey");		//双击列的绑定的列值

	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("historicTaskInstance.id","编号",1,2,2,0));
	sccList.add(new SysColumnControl("workName","工作文号",1,2,2,0));
	sccList.add(new SysColumnControl("historicTaskInstance.name","工作名称",1,2,2,0));
	sccList.add(new SysColumnControl("instanceStartTime","开始时间",1,2,2,0));
	sccList.add(new SysColumnControl("instanceEndTime","结束时间",1,2,2,0));
	sccList.add(new SysColumnControl("employee.hrmEmployeeName","负责人",1,2,2,0));
	sccList.add(new SysColumnControl("historicTaskInstance.deleteReason","处理结果",1,2,2,0));
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
	
		if("workName".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repWorkName");
			bc.setColumnStyle("text-align:center");
		}
	}
	bg.setColumnList(colList);
	bg.setCheckboxOrNum(false);
	bg.setShowProcess(true);
	bg.setShowImg(false);
	bg.setProcessMethodName("createProcessMethod");
	//开始创建
	out.print(bg.createTable());
%>
</body>
</html>