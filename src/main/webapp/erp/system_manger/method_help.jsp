<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>操作提示管理</title>
<script>
function queryData(){
	startQuery();
	var methodHelp = getQueryParam();
	var pager = getPager();
	dwrSysProcessService.listSysMethodHelpByPager(methodHelp,pager,queryCallback);
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
	
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
	   confirmmsg("确定要删除选中的操作提示吗?","dele()");
	}else{
	   alertmsg("请选择要删除的操作提示！");
	}
}

function dele(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.deleteMethodHelpByIds(recordsPks,deleback);
}

function deleback(data){
   alertmsg(data,"queryData()");
}

function edit(id){
    MoveDiv.show('编辑操作提示','<%=contextPath%>/erp/system_manger/method_help_add.jsp?lid='+id);
}

function del(pk){
    confirmmsg("确定要删除该操作提示吗?","delok("+pk+")");
}

function delok(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrSysProcessService.deleteMethodHelpByIds(ids,deleback);
}

function createProcessMethod(rowObj){
	var str="";
	str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit1.png' border='0'/></a>&nbsp;&nbsp;";
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	return str;
}

function replaceHelpImg(rowObj){
	var str = "<无>";
	if(isNotBlank(rowObj.helpImg)){
		var imgUrl = "<%=contextPath%>/images/grid_images/"+rowObj.helpImg;
		str = "<img src='"+imgUrl+"' border='0' height='12'/>";
	}
    return str;
}

function add(){
	var url = '<%=contextPath%>/erp/system_manger/method_help_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("操作提示管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增","add()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close1.png"));
    nw.setBtnList(btnList);
    nw.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("methodInfo.methodInfoName","绑定功能名称",1,2,2,0));
    sccList.add(new SysColumnControl("methodName","功能名称",2,1,2,0));
    sccList.add(new SysColumnControl("helpImg","图标",1,2,2,0));
    sccList.add(new SysColumnControl("helpDesc","操作提示内容",1,2,2,60));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
    
    //进行高级查询显示处理
    for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if(bc.isShowAdvanced()||bc.isShowColumn()){
			if("helpImg".equalsIgnoreCase(bc.getDataName())){
				bc.setColumnReplace("replaceHelpImg");
				bc.setColumnStyle("text-align:center;");
			}
		}
    }
   
    //设置列操作对象
    nw.setShowProcess(true);//默认为false 为true请设置processMethodName
    nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象   
    nw.setProcessPosition("left");
    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
</body>
</html>
