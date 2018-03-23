<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<%
	String type = request.getParameter("type");
System.out.println(type);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>出借归还</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var bookbr = getQueryParam();
	var pager = getPager();
	bookbr.oaBrStatus = '<%=type %>';
	dwrOfficeResourcesService.listBookbrs(bookbr,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/book_br_detail.jsp?bid='+obj.value,'800','500');
	box.msgtitle="<b>出借归还明细列表</b>";
	box.show();
}

//列显示替换方法
function replaceStatus(rowObj){
	var str="";
	if(rowObj.oaBrStatus ==<%=EnumUtil.OA_BOOKBR_STATUS.LEND.value%>){
		str= "<font style='color:red'><%=EnumUtil.OA_BOOKBR_STATUS.valueOf(EnumUtil.OA_BOOKBR_STATUS.LEND.value)%></font>"
	}else{
		str= "<font style='color:blue'><%=EnumUtil.OA_BOOKBR_STATUS.valueOf(EnumUtil.OA_BOOKBR_STATUS.BACK.value)%></font>"
	}
	return str;
}

</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);

bg.setTableTitle("借出/归还");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
 bg.setCheckboxOrNum(false);
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"图书借阅列表"));

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
	   if("oaBrRdate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
		if("oaBrBdate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
		if("oaBrStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	/*SelectType select  = new SelectType(EnumUtil.OA_BOOKBR_STATUS.getSelectAndText("-1,-请选择图书状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);*/
	
	bc.setColumnReplace("replaceStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
}

bg.setColumnList(colList);

//开始创建
out.print(bg.createTable());
%>
</body>
</html>