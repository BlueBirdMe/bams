<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
		<title>异常管理</title>
		<script>
function queryData(){
	startQuery();
	var exception = getQueryParam();
	
	var pager = getPager();
	dwrSysProcessService.listSysException(exception,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/system_manger/sys_exception_detail.jsp?aid='+obj.value,'750','550');
		box.msgtitle="<b>查看异常记录明细</b>";
		box.show();
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
	   confirmmsg("确定要删除选中的异常吗?","deleSysException()");
	}else{
	   alertmsg("请选择要删除的异常！");
	}
}
function deleSysException(){
    var recordsPks = getAllRecordArray();

   dwrSysProcessService.delectSysExceptionsByid(recordsPks,deleback);
}
function deleback(data){
   alertmsg(data,"queryData()");
}
function edit(id){
    Sys.load('<%=contextPath%>/erp/system_manger/sys_exception_edit.jsp?sysExceptionPk='+id);
}
function del(pk){
    confirmmsg("确定要删除异常吗?","delok("+pk+")");
}
function delok(pk){
    var ids = new Array();
    ids[0] = pk;
  
    dwrSysProcessService.delectSysExceptionsByid(ids,deleback);
}
function createProcessMethod(rowObj){
	var str="";
	
	if(rowObj.exceptionStatus == <%=EnumUtil.SYS_EXCEPTION_STATUS.Vaild.value%>){
	   str += "<a href='javascript:void(0)' title='处理' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;";
	} else {
	   str += "<a href='javascript:void(0)' title='已处理'><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0' style='filter:gray;'/></a>&nbsp;";
	}
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	return str;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.exceptionStatus ==<%=EnumUtil.SYS_EXCEPTION_STATUS.Vaild.value%>){
		str= "<font style='color:red'><%=EnumUtil.SYS_EXCEPTION_STATUS.valueOf(EnumUtil.SYS_EXCEPTION_STATUS.Vaild.value)%></font>"
	}else{
		str= "<font style='color:green'><%=EnumUtil.SYS_EXCEPTION_STATUS.valueOf(EnumUtil.SYS_EXCEPTION_STATUS.No_Vaild.value)%></font>"
	}
	return str;
}
function checkStatus(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要设置选中的异常吗?","setStatus()");
	}else{
	   alertmsg("请你选择要设置的异常！");
	}
}


</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("异常管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
 
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
		    
		    
   ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"异常管理"));
    
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("exceptionDate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
		
		if("exceptionStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.SYS_EXCEPTION_STATUS.getSelectAndText("-1,-请选择要查询的异常状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("replaceStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
	}
   }
   
   
//设置列操作对象
nw.setShowProcess(true);//默认为false 为true请设置processMethodName
nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象    
   
    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
