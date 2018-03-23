<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限查询</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var userinfo = getQueryParam();
	userinfo.upcode = document.getElementById("upcode").value;
	var pager = getPager();
	dwrSysProcessService.getUserMethodsByPager(userinfo,pager,queryCallback);
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
	var box = new Sys.msgbox('用户权限明细信息','<%=contextPath %>/erp/system_set/premmset_detail.jsp?uid='+obj.value,800,500);
	box.msgtitle='用户附加权限明细信息(不包含角色权限)';
	box.show();
}

function showall(id){
	var box = new Sys.msgbox('用户权限明细信息','<%=contextPath %>/erp/system_set/premmset_detail.jsp?show=all&uid='+id,800,500);
	box.msgtitle='显示用户所有权限信息(包含角色权限)';
	box.show();
}


//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}

function processMethod(rowObj){
	var	str="";
	str+="<a href='javascript:void(0)' title='查看所有权限' onclick=\"showall('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>";
	return str;
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
	<jsp:param name="ischeck" value="true"/>
</jsp:include>
</div>
</td>
<td>
<%
	SysGrid bg =new SysGrid(request);
bg.setShowView(SysGrid.SHOW_TABLE);
bg.setTableTitle("用户列表");
bg.setCheckboxOrNum(false);

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"权限设置"));
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc= colList.get(i);
	if("employeeRole".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
	if("deptRole".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
	if("mainPostRole".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
	if("partPostRole".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
	if("groupRole".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
	if("userMethods".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:center");
	}
}
bg.setColumnList(colList);

ArrayList<SysGridTitleBean> helplist = new ArrayList<SysGridTitleBean>();
helplist.add(new SysGridTitleBean("","双击查询权限信息，不包含角色权限"));
helplist.add(new SysGridTitleBean("","查询包含角色权限的信息请点击&nbsp;<img src='"+contextPath+"/images/grid_images/rowinfo.png' border='0'/>&nbsp;图标"));
bg.setHelpList(helplist);

bg.setShowProcess(true);
bg.setProcessMethodName("processMethod");

//开始创建
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
</body>
</html>