<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<title>查询流程用户组</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var pager = getPager();
	dwrApproveProcessService.listProcessGroupByPager(pager,queryCallback);
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
	str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.id+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.id+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function edit(id){
	MoveDiv.show('编辑流程用户组','<%=contextPath%>/erp/system_set/process_group_add.jsp?id='+id+'');
}

function callback(data){
	alertmsg(data,"queryData()");
}

function del(id){
	confirmmsg("确定要删除此用户组吗?","delok('"+id+"')");
}

function delok(id){
	dwrApproveProcessService.deleteProcessGroupById(id,callback);
}

function create(){
	var url = '<%=contextPath %>/erp/system_set/process_group_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

</script>
</head>
<body>
<%
SysGrid bg =new SysGrid(request);
bg.setTableTitle("流程用户组列表");

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增用户组","create()","add.png"));
bg.setBtnList(btnList);

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("id");		//双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();

sccList.add(new SysColumnControl("id", "流程用户组标识ID",1, 2, 2, 0));
//sccList.add(new SysColumnControl("revision", "REVISION",1, 2, 2, 0));
sccList.add(new SysColumnControl("name", "用户组名称",1, 2, 2, 0));
sccList.add(new SysColumnControl("type", "用户组类型",1, 2, 2, 0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

for(int i=0;i<colList.size();i++){
	
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