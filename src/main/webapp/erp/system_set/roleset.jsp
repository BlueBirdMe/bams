<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色查询</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var role = getQueryParam();
	var pager = getPager();
	dwrSysProcessService.listsysRoleByPager(role,pager,queryCallback);
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
	var box = new Sys.msgbox("角色明细",'<%=contextPath %>/erp/system_set/roleset_detail.jsp?rid='+obj.value,'850','500');
	box.msgtitle="<b>角色明细</b>";
	box.show();
}

function processMethod(rowObj){
	var	str="";
	str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除角色吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的角色...");
	}
}

function delbatchok(){
	dwrSysProcessService.deleteSysRoleByIds(getAllRecordArray(),callback);
}

function edit(id){
	var url = "<%=contextPath%>/erp/system_set/roleset_add.jsp?rid="+id;
	url += "&tab="+getMDITab();
	openMDITab(url);
}

function del(id){
	confirmmsg("确定要删除角色吗?","delok("+id+")");
}

function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrSysProcessService.deleteSysRoleByIds(ids,callback);
}

function callback(data){
	alertmsg(data,"queryData()");
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
bg.setShowView(SysGrid.SHOW_TABLE);
bg.setBorder(1);
bg.setTableTitle("角色列表"); 

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("批量删除","delbatch()","close.png"));
bg.setBtnList(btnList);

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"角色列表"));
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc = colList.get(i);
	if(!bc.getDataName().equalsIgnoreCase("roleName")&&!bc.getDataName().equalsIgnoreCase("roleDesc")){
		bc.setColumnStyle("text-align:center");
	}
}
bg.setColumnList(colList);

bg.setShowProcess(true);
bg.setProcessMethodName("processMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>