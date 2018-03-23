<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<title>历史流程</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var pager = getPager();
	var bean = getQueryParam();
	dwrApproveProcessService.listProcessHistoryByPager(bean,pager,queryCallback);
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
	//alert(obj.value);
}

function createMethod(rowObj){
	var	str = "<a href=\"javascript:void(0);\" onclick=\"showDetail('"+rowObj.config.detailPage+"','"+rowObj.historicProcessInstance.businessKey+"','"+rowObj.historicProcessInstance.id+"');\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>";
		str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.historicProcessInstance.id+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function showDetail(detailUrl, id, processInstanceId){
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+id+"&processInstanceId="+processInstanceId;
	var box = box = new Sys.msgbox('明细查看', url);
	box.show();
}

function del(id){
	confirmmsg("确定要删除此流程吗?","delok('"+id+"')");
}

function delok(id){
	dwrApproveProcessService.deleteHistoricProcessInstanceById(id,callback);
}


function callback(data){
	alertmsg(data,"queryData()");
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

function repProcessName(rowObj){
	var str = "<a href=\"javascript:void(0);\" onclick=\"showDetail('"+rowObj.processDefinition.key+"','"+rowObj.historicProcessInstance.businessKey+"','"+rowObj.historicProcessInstance.id+"');\">"+rowObj.processDefinition.name+"</a>";
	return str;
}

</script>
</head>
<body>
<%
SysGrid bg =new SysGrid(request);
bg.setTableTitle("历史流程列表");

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("id");		//双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();

sccList.add(new SysColumnControl("historicProcessInstance.id","流水号",1,2,2,0));
sccList.add(new SysColumnControl("id","流水号",2,1,2,0));
sccList.add(new SysColumnControl("key","流程类型",2,2,1,0));
sccList.add(new SysColumnControl("processDefinition.name","流程名称",1,2,2,0));
sccList.add(new SysColumnControl("employee.hrmEmployeeName","发起人",1,2,2,0));
sccList.add(new SysColumnControl("instanceStartTime","流程开始时间",1,2,2,0));
sccList.add(new SysColumnControl("instanceEndTime","流程结束时间",1,2,2,0));
sccList.add(new SysColumnControl("processStatus","流程状态",1,2,1,0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc= colList.get(i);
	if("processStatus".equalsIgnoreCase(bc.getDataName())){
		SelectType select = new SelectType(EnumUtil.PROCESS_STATUS.getSelectAndText("-1,请选择流程状态"));
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
	
}
bg.setColumnList(colList);


bg.setShowImg(false);
bg.setCheckboxOrNum(false);
bg.setShowProcess(true);
bg.setProcessMethodName("createMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>