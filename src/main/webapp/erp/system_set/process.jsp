<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<title>流程部署</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var pager = getPager();
	dwrApproveProcessService.listSysApproveProcessByPager(pager,queryCallback);
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
	str+="<a href='javascript:void(0)' title='设置' onclick=\"config('"+rowObj.processDefinition.id+"')\"><img src='<%=contextPath%>/images/grid_images/wrench.png' width='12' border='0'/></a>&nbsp;&nbsp;";
	if(rowObj.processDefinition.suspensionState == <%=EnumUtil.SUSPENSION_STATE.ACTIVE.value%>){
		str+="<a href='javascript:void(0)' title='挂起' onclick=\"setvalid('"+rowObj.processDefinition.id+"')\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0'/></a>&nbsp;&nbsp;";
	}else{
		str+="<a href='javascript:void(0)' title='激活' onclick=\"setvalid('"+rowObj.processDefinition.id+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0' /></a>&nbsp;&nbsp;";
	}
	
	str+="<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.processDefinition.deploymentId+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}

function config(id){
	var url = "<%=contextPath%>/erp/system_set/process_config.jsp?id="+id+"&tab="+getMDITab(); 
	openMDITab(url);
}

function callback(data){
	alertmsg(data,"queryData()");
}
function setvalid(id){
	confirmmsg("确定进行此操作吗?","setok('"+id+"')");
}

function setok(id){
	dwrApproveProcessService.setApproveProcessActionById(id,callback);
}

function del(id){
	confirmmsg("确定要删除该流程吗?","delok('"+id+"')");
}

function delok(id){
	dwrApproveProcessService.deleteApproveProcessActionById(id,callback);
}

function deploy(){
	window.location = "<%=contextPath %>/erp/system_set/process_add.jsp";
}


function repleaResourceName(rowObj){
	var url = "<%=contextPath %>/processResource.do?type=xml&pid="+rowObj.processDefinition.id;
	var str = "<a target='_blank' href='"+url+"'>"+rowObj.processDefinition.resourceName+"</a>";
	return str;
}

function repleaDiagramResourceName(rowObj){
	var str = "<a href=\"javascript:void(0);\" onclick=\"showResource('"+rowObj.processDefinition.id+"');\">"+rowObj.processDefinition.diagramResourceName+"</a>";
	return str;
}

function showResource(processDefinitionId,resourceType){
	var box = new Sys.msgbox('流程图查看','<%=contextPath %>/processResource.do?type=image&pid='+processDefinitionId,750,500);
	box.show();
}


function repleaSuspensionState(rowObj){
	var	str="";
	if(rowObj.processDefinition.suspensionState == <%=EnumUtil.SUSPENSION_STATE.ACTIVE.value%>){
		str = "<font color='green'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.ACTIVE.value)%></font>";
	}else if(rowObj.processDefinition.suspensionState == <%=EnumUtil.SUSPENSION_STATE.SUSPENDED.value%>){
		str = "<font color='red'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.SUSPENDED.value)%></font>";
	}
	return str;
}


</script>
</head>
<body>
<%
SysGrid bg =new SysGrid(request);
bg.setTableTitle("流程部署列表");

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("部署流程","deploy()","add.png"));
bg.setBtnList(btnList);
bg.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("processDefinition.id");		//双击列的绑定的列值



//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();

sccList.add(new SysColumnControl("processDefinition.id", "流程定义ID",1, 2, 2, 0));
sccList.add(new SysColumnControl("processDefinition.deploymentId", "部署ID",1, 2, 2, 0));
sccList.add(new SysColumnControl("processDefinition.name", "名称",1, 2, 2, 0));
sccList.add(new SysColumnControl("processDefinition.key", "流程KEY",1, 2, 2, 0));
sccList.add(new SysColumnControl("processDefinition.version", "版本",1, 2, 2, 0));
sccList.add(new SysColumnControl("deploymentTime", "部署时间",1, 2, 2, 0));
sccList.add(new SysColumnControl("resourceName", "XML",1, 2, 2, 0));
sccList.add(new SysColumnControl("diagramResourceName", "流程图",1, 2, 2, 0));
sccList.add(new SysColumnControl("suspensionState", "是否挂起",1, 2, 2, 0));
sccList.add(new SysColumnControl("config.processType.typeName", "流程分类",1, 2, 2, 0));


ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc= colList.get(i);
	if("resourceName".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repleaResourceName");
		bc.setColumnStyle("text-align:center");
	}
	if("diagramResourceName".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repleaDiagramResourceName");
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