<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户有效性设置</title>
<link type='text/css' rel='stylesheet' href='<%=contextPath%>/css/xtree.css' />
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/map.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xtree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xloadtree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxTreeItem.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xmlextras.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxXLoadTree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioTreeItem.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioXLoadTree.js' charset='UTF-8'></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
webFXTreeConfig.setImagePath('<%=contextPath%>/js/treeJs/images/default/');

function setliandong(obj){
	webFXTreeConfig.setCascadeCheck(obj.checked); 
}

var tree = new WebFXLoadTree("选择部门","<%=request.getContextPath()%>/erp/tree/departmenttree.jsp?fid=00&ischeck=true");

function getCheckedIds(){
	document.getElementById("upcode").value = getCheckValues();
	document.getElementById("hrmEmployeeDepidTree").value = getCheckTexts();
	queryData();
}

//查询方法
function queryData(){
	startQuery();
	var userinfo = getQueryParam();
	if(userinfo.employee != null){
	    userinfo.employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	}
	var pager = getPager();
	dwrSysProcessService.listSysUserInfo(userinfo,pager,queryCallback);
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
}

function repleaValid(rowObj){
	var str="";
	if(rowObj.userAction == <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>){
		str= "<font style='color:red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}else{
		str= "<font style='color:green'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%></font>";
	}
	return str;
}

function createMethod(rowObj){
	var str="";
	if(rowObj.userAction == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str+="<a href='javascript:void(0)' title='设置为无效' onclick=\"setvalidone('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.No_Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0'/></a>";
	}else{
		str+="<a href='javascript:void(0)' title='设置为有效' onclick=\"setvalidone('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>";
	}
	return str
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}

function setvaild(action){
	if(getAllRecordArray() != false){
		confirmmsg("确定要更改用户有效性吗?","setaction("+action+")");
	}else{
		alertmsg("请选择要更改的记录...");
	}
}

function setvalidone(id,action){
	confirmmsg("确定要更改用户有效性吗?","setactionone("+id+","+action+")");
}

function setactionone(id,action){
	var ids = new Array();
	ids[0] = id;
	dwrSysProcessService.setSysUserInfoIsAction(ids,action,callback);
}

function setaction(action){
	dwrSysProcessService.setSysUserInfoIsAction(getAllRecordArray(),0,callback);
}
function callback(data){
	alertmsg(data,"queryData()");
}

function showTree(obj){
	var l = $(obj).offset().left;
	var t = $(obj).offset().top;
	$("#treediv").css("left",l).css("top",t).show();  
}

function hideTree(){
	$("#treediv").hide();
}
</script>
</head>
<body>
<div id="treediv" onmouseleave="hideTree();">
	<input type="checkbox" id="lidong" onchange="setliandong(this)" checked="checked">
	<label for="lidong" style="color: #336699">选择上级自动选中下级</label>
	<script type="text/javascript">
	document.write(tree);
	</script>
</div>
<input type="hidden" id="upcode">
<%
SysGrid bg =new SysGrid(request);
bg.setShowView(SysGrid.SHOW_TABLE);
bg.setTableTitle("用户列表"); 

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("有效/无效设置","setvaild()","set1.png"));
bg.setBtnList(btnList);

//放入列
ArrayList<SysColumnControl> sccList = (ArrayList<SysColumnControl>)UtilTool.getColumnShow(this.getServletContext(),"用户管理");
sccList.add(new SysColumnControl("hrmEmployeeDepidTree", "选择部门", 2, 2,1, 0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("userAction".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式

		SelectType select  = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择是否有效-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
		bc.setColumnReplace("repleaValid");
		
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if("hrmEmployeeDepidTree".equalsIgnoreCase(bc.getDataName())){
		OtherType oType = new OtherType("<input type=\"text\" id=\"hrmEmployeeDepidTree\" readonly=\"readonly\" class=\"treeform\" onclick=\"showTree(this);\"/>");
		bc.setColumnToObject(false);
		bc.setColumnTypeClass(oType);
	}
}

bg.setColumnList(colList);

bg.setShowProcess(true);
bg.setProcessMethodName("createMethod");
//开始创建
out.print(bg.createTable());
%>
</body>
</html>