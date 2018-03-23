<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
		
		<title>操作日志</title>
		<script>

function queryData(){
	startQuery();
	var syslog = getQueryParam();
	var pager = getPager();

	dwrSysProcessService.listSysLog(syslog,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

function deleteObject(){
	//针对多条记录进行操作
	
	   confirmmsg("确定要删除所有日志吗?","deleSysMsg()");
	
}
function deleSysMsg(){
   
   dwrSysProcessService.deleteSysLog(deleback);
}
function deleback(data){
   alertmsg(data,"queryData()");
}

</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("操作日志");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setCheckboxOrNum(false);
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("删除全部日志","deleteObject()","close.png"));
 
    nw.setBtnList(btnList);
  
   ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"操作日志"));
    
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("logDate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
	}
   }
   
   
//设置列操作对象
//nw.setShowProcess(true);//默认为false 为true请设置processMethodName
//nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象    
   
    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
