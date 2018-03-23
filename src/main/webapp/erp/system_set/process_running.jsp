<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<title>运行中流程</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var pager = getPager();
	dwrApproveProcessService.listProcessRunningByPager(pager,queryCallback);
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
	var	str="";
	if(rowObj.processInstance.suspensionState == <%=EnumUtil.SUSPENSION_STATE.ACTIVE.value%>){
		str+="<a href='javascript:void(0)' title='挂起' onclick=\"setvalid('suspend','"+rowObj.processInstance.id+"')\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0'/></a>";
	}else{
		str+="<a href='javascript:void(0)' title='激活' onclick=\"setvalid('active','"+rowObj.processInstance.id+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0' /></a>";
	}
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.processInstance.id+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	
	return str;
}

function del(id){
	confirmmsg("确定要删除此流程吗?","delok('"+id+"')");
}

function delok(id){
	dwrApproveProcessService.deleteProcessInstanceById(id,callback);
}


function callback(data){
	alertmsg(data,"queryData()");
}

function setvalid(state,id){
	confirmmsg("确定进行此操作吗?","setok('"+state+"','"+id+"')");
}

function setok(state,id){
	dwrApproveProcessService.setSuspensionState(state,id,callback);
}

function repleaSuspensionState(rowObj){
	var	str="";
	if(rowObj.processInstance.suspensionState == <%=EnumUtil.SUSPENSION_STATE.ACTIVE.value%>){
		str = "<font color='green'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.ACTIVE.value)%></font>";
	}else if(rowObj.processInstance.suspensionState == <%=EnumUtil.SUSPENSION_STATE.SUSPENDED.value%>){
		str = "<font color='red'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.SUSPENDED.value)%></font>";
	}
	return str;
}
function repleaNodeName(rowObj){
	var str = "<a href=\"javascript:void(0);\" title=\"点击查看流程图\" onclick=\"showProcessTrace('"+rowObj.processInstance.id+"');\">"+rowObj.nodeName+"</a>";
	return str;
}

function showProcessTrace(instanceId){
	var box = new Sys.msgbox('流程追踪','<%=contextPath %>/processTrace.do?id='+instanceId,750,500);
	box.msgtitle="<b>此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点</b>";
	box.show();
}

</script>
</head>
<body>
<%
SysGrid bg =new SysGrid(request);
bg.setTableTitle("运行中的流程列表");

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("id");		//双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();

sccList.add(new SysColumnControl("processInstance.id", "执行ID",1, 2, 2, 0));
sccList.add(new SysColumnControl("processInstance.processInstanceId", "流程实例ID",1, 2, 2, 0));
sccList.add(new SysColumnControl("processInstance.processDefinitionId", "流程定义ID",1, 2, 2, 0));
sccList.add(new SysColumnControl("nowNodeName", "当前节点",1, 2, 2, 0));
sccList.add(new SysColumnControl("suspensionState", "是否挂起",1, 2, 2, 0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc= colList.get(i);
	if("nowNodeName".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repleaNodeName");
		bc.setColumnStyle("text-align:center");
	}
	
	if("suspensionState".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repleaSuspensionState");
		bc.setColumnStyle("text-align:center");
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