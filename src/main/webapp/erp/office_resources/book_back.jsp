<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>归还图书</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var bookbr = getQueryParam();
	bookbr.oaBrStatus = <%=EnumUtil.OA_BOOKBR_STATUS.LEND.value%>;
	var pager = getPager();
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

function set(pk){
	confirmmsg("确定要归还此图书吗?","setok("+pk+")");
}
	
function setok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrOfficeResourcesService.giveBackBookByPks(pks,setCallback);
}

function setCallback(data){
	alertmsg(data,"queryData()");
}
	
	
function setObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要将选中的图书归还吗?","setbatchok()");
	}else{
		alertmsg("请选择要归还的图书！");
	}
}
function setbatchok(){
	var recordsPks = getAllRecordArray();
	for(var i=0;i<recordsPks.length;i++){
	    	var obj = getObjectByPk(recordsPks[i]);
	    	if(obj.oaBrStatus == <%=EnumUtil.OA_BOOKBR_STATUS.BACK.value%> ){
	    		alertmsg("包含有已归还数据记录,不能操作!");
	    		return;    	
	    	}
	    }
	dwrOfficeResourcesService.giveBackBookByPks(recordsPks,setCallback);
}
	

function createProcessMethod(rowObj){
    var str = "";
    if(rowObj.oaBrStatus == <%=EnumUtil.OA_BOOKBR_STATUS.LEND.value%>){
        str="<a href='javascript:void(0)' title='还书' onclick=\"set('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/book_previous.png' border='0'/></a>";
    }else{
        str="<a href='javascript:void(0)' title='已归还' ><img src='<%=contextPath%>/images/grid_images/book_previous.png' border='0' style='filter:gray;' /></a>";
    }
	return str;
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

bg.setTableTitle("出借归还");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
 
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("批量还书","setObject()","edit.png"));
bg.setBtnList(btnList);

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
	
	SelectType select  = new SelectType(EnumUtil.OA_BOOKBR_STATUS.getSelectAndText("-1,-请选择图书状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("replaceStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
}

bg.setColumnList(colList);

//设置列操作对象
bg.setShowProcess(true);//默认为false 为true请设置processMethodName
bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

//开始创建
out.print(bg.createTable());
%>
</body>
</html>