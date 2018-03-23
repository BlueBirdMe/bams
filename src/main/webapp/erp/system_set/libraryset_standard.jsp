<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标准代码</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var syslib = getQueryParam();
	syslib.libraryUpcode = document.getElementById("upcode").value;//取得树值
	var pager = getPager();
	dwrSysProcessService.getSysLibraryStandardListByPager(syslib,pager,queryCallback);
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

function deleteRecords(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
	   confirmmsg("确定要删除选中的记录吗?","deleColumn()");
	}else{
	   alertmsg("请选择要删除的记录！");
	}
}

function deleColumn(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.deleteSysLibraryStandardByPks(recordsPks,setcallback);
}

function deleteObject(id){
	confirmmsg("确定要删除记录吗?","del("+id+")");
}

function del(id){
	var recordsPks = new Array();
	recordsPks[0] = id;
	dwrSysProcessService.deleteSysLibraryStandardByPks(recordsPks,setcallback);
}

function edit(id){
	MoveDiv.show('编辑标准代码','<%=contextPath%>/erp/system_set/libraryset_standard_add.jsp?lid='+id);
}

function add(){
	var url = '<%=contextPath%>/erp/system_set/libraryset_standard_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function createMethod(rowObj){
	var	str="";
		str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteObject('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function setcallback(data){
    tree.reload();
	alertmsg(data,"queryData()");
}

</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title">选择上级</div>
<div class="div_content">
<input type="hidden" id="treejs" value="treeclick">
<jsp:include page="libraryset_standard_tree.jsp" flush="false"></jsp:include>
</div>
</td>
<td>
<%
SysGrid grid = new SysGrid(request,"标准代码列表");
grid.setShowImg(false);//不显示图片信息
//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增代码","add()","add.png"));
btnList.add(new SysGridBtnBean("批量删除","deleteRecords()","close1.png"));
grid.setBtnList(btnList);

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("libraryCode","编号",1,1,2,0));
sccList.add(new SysColumnControl("libraryName","名称",1,2,2,0));
sccList.add(new SysColumnControl("libraryUpcode","上级编号",1,2,2,0));
sccList.add(new SysColumnControl("libraryStandCode","标准代码",1,2,2,0));
sccList.add(new SysColumnControl("libraryDesc","备注",1,1,2,25));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
    
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