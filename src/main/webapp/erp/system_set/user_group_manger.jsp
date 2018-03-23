<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
    <%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编组管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var group = getQueryParam();
	var pager = getPager();
	dwrSysProcessService.listSysUserGroupBypager(group,pager,queryCallback);
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
	var box = new Sys.msgbox('用户编组明细查看','<%=contextPath %>/erp/system_set/user_group_detail.jsp?gid='+obj.value + '&noid=1', '800', '500');
	box.msgtitle = "<b>用户编组明细查看</b>";
	var butarray = new Array();
	butarray[0] = "cancel||关 闭";
	box.buttons = butarray;
	box.show();
}

function repleaDetailCount(rowObj){
	var str=rowObj.detailList.length;
	return str;
}

function processMethod(rowObj){
	var	str="";
	str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除编组吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的编组...");
	}
}

function delbatchok(){
	dwrSysProcessService.deleteSysUserGroupByIds(getAllRecordArray(),callback);
}

function edit(id){
	MoveDiv.show('编辑编组',"<%=contextPath%>/erp/system_set/user_group_add.jsp?gid="+id);
}

function del(id){
	confirmmsg("确定要删除编组吗?","delok("+id+")");
}

function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrSysProcessService.deleteSysUserGroupByIds(ids,callback);
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
bg.setTableTitle("用户编组列表"); 

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("批量删除","delbatch()","close.png"));
bg.setBtnList(btnList);

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"人员编组"));
colList.add(ColumnUtil.getCusterShowColumn("count","用户数","repleaDetailCount",0,"text-align:center"));
bg.setColumnList(colList);

bg.setShowProcess(true);
bg.setProcessMethodName("processMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>