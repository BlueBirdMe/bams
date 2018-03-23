<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>图书信息</title>
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/book_detail.jsp?bid='+obj.value,'800','500');
	box.msgtitle="<b>图书信息明细列表</b>";
	box.show();
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
bg.setCheckboxOrNum(false);

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

//开始创建
out.print(bg.createTable());
%>
</body>
</html>