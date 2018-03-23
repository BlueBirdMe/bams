<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>图书管理</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var book = getQueryParam();
	var pager = getPager();
	dwrOfficeResourcesService.listBooks(book,pager,queryCallback);
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
	//MoveDiv.show('明细查看','<%=contextPath%>/erp/office_resources/book_detail.jsp?bid='+obj.value);
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/book_detail.jsp?bid='+obj.value,'800','500');
		box.msgtitle="<b>图书信息明细列表</b>";
		box.show();
}

function add(){
	var url = '<%=contextPath%>/erp/office_resources/book_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function edit(pk){
	MoveDiv.show('编辑图书','<%=contextPath%>/erp/office_resources/book_add.jsp?bookpk='+pk);
}

function del(pk){
	confirmmsg("确定要删除此图书信息吗?","delok("+pk+")");
}
	
function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrOfficeResourcesService.deleteBooksByPks(pks,delcallback);
}
	
function delcallback(data){
	alertmsg(data,"queryData()");
}

function deleteObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除所选图书信息吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的图书信息！");
	}
}
function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrOfficeResourcesService.deleteBooksByPks(recordsPks,delcallback);
}

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}


function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaBookAcce!=null&&rowObj.oaBookAcce != undefined&& rowObj.oaBookAcce != "undefined"&&rowObj.oaBookAcce.length>0){
		var cs = rowObj.oaBookAcce.split(",");
		count = cs.length;
	}
	return count;
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);

bg.setTableTitle("图书信息");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增图书","add()","add.png"));
btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
bg.setBtnList(btnList);

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"图书列表"));

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
	
		if("oaBookType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getBookTypeInfoList(this.getServletContext(),request,"-1,-请选择图书类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaBookDep".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getDepInfoList(this.getServletContext(),request,"-1,-请选择所属部门-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
	}
}
colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));

bg.setColumnList(colList);

//设置列操作对象
bg.setShowProcess(true);//默认为false 为true请设置processMethodName
bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

//开始创建
out.print(bg.createTable());
%>
</body>
</html>