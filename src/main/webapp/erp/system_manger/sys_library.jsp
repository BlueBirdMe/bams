<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统字典</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var syslib = getQueryParam();
	//取得树值
	if(syslib.libraryInfoUpcode==null||syslib.libraryInfoUpcode==""){
		syslib.libraryInfoUpcode = document.getElementById("upcode").value;
	}
	var pager = getPager();
	dwrSysProcessService.getSysLibraryInfoListByPager(syslib,pager,queryCallback);
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
}
function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","del()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}

function del(){
	var recordsPks = getAllRecordArray();
	dwrSysProcessService.deleteSysLibraryInfoByPks(recordsPks,setcallback);
}

function edit(id){
	Sys.load('<%=contextPath%>/erp/system_manger/sys_library_add.jsp?lid='+id);
}

function repIsEdit(rowObj){
	var ed = rowObj.libraryInfoIsedit;
	var str="";
	if(ed == <%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
		str ="<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.EDIT.value)%>";
	}else{
		str ="<font color='red'><%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.No_EDIT.value)%></font>";
	}
	return str;
}
function add(){
	Sys.href('<%=contextPath%>/erp/system_manger/sys_library_add.jsp');
}
function repisvalid(rowObj){
	var ac = rowObj.libraryInfoIsvalid;
	
	var str ="";
	if(ac == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str ="<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%>";
	}else{
		str ="<font color='red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}
	return str;
}
function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	if(rowObj.libraryInfoIsedit==<%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为不可编辑' onclick=\"setedit('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISEDIT.No_EDIT.value%>)\"><img src='<%=contextPath%>/images/grid_images/set.png' border='0' width='13' height='13'/></a>";
	}else{
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为可编辑' onclick=\"setedit('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISEDIT.EDIT.value%>)\"><img src='<%=contextPath%>/images/grid_images/set.png' border='0' width='13' height='13' style='filter:gray;'/></a>";
	}
	
	if(rowObj.libraryInfoIsvalid == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为无效' onclick=\"setvalid('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.No_Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid.png' border='0'/></a>";
	}else{
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为有效' onclick=\"setvalid('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid.png' border='0' style='filter:gray;'/></a>";
	}
	return str;
}


function setedit(pk,sta){
	dwrSysProcessService.setSysLibraryInfoBypk(pk,sta,1,setcallback);
}

function setvalid(pk,sta){
	dwrSysProcessService.setSysLibraryInfoBypk(pk,sta,2,setcallback);
}

function setcallback(data){
	alertmsg(data,"queryData()");
}

function setbatch(a){
	var pks = getAllRecordArray();
	if( pks!= false){
		dwrSysProcessService.setSysLibraryInfoByPks(pks,a,setcallback);
	}else{
		alertmsg("请选择要设置的记录...");
	}
	
}

</script>
</head>
<body >
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title">选择上级</div>
<div class="div_content">
<input type="hidden" id="treejs" value="treeclick">
<jsp:include page="sys_library_tree.jsp" flush="false"></jsp:include>
</div>
</td>
<td>
<%
	SysGrid grid = new SysGrid(request,"业务字典列表");
grid.setShowImg(false);//不显示图片信息
//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增","add()","add.png"));
btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
btnList.add(new SysGridBtnBean("有效性","setbatch(2)","valid.png"));
btnList.add(new SysGridBtnBean("是否可编辑","setbatch(1)","set.png"));
grid.setBtnList(btnList);

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"业务字典列表"));


//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("libraryInfoIsedit".equalsIgnoreCase(bc.getDataName())){
		SelectType select = new SelectType(EnumUtil.SYS_ISEDIT.getSelectAndText("-1,-请选择是否允许编辑-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		bc.setColumnReplace("repIsEdit");
		bc.setColumnStyle("text-align:center;");
	}
	if("libraryInfoIsvalid".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式
		SelectType select  = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择是否有效-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		bc.setColumnReplace("repisvalid");
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
}

grid.setColumnList(colList);

grid.setShowProcess(true);
grid.setProcessMethodName("createMethod");

//设置附加信息
grid.setQueryFunction("queryData");	//查询的方法名
grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
grid.setDblBundle("primaryKey");		//双击列的绑定的列值

//开始创建
out.print(grid.createTable());
%>
</td>
</tr>
</table>
</body>
</html>