<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>列表字段管理</title>
<script>
function queryData(){
	startQuery();
	var column = getQueryParam();
	var pager = getPager();
	dwrSysProcessService.listSysColumnControl(column,pager,queryCallback);
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
	   confirmmsg("确定要删除选中的字段吗?","deleColumn()");
	}else{
	   alertmsg("请选择要删除的字段！");
	}
}

function deleColumn(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.deleteColumnControlById(recordsPks,deleback);
}

function deleback(data){
   alertmsg(data,"queryData()");
}

function edit(id){
    MoveDiv.show('编辑字段','<%=contextPath%>/erp/system_manger/column_control_add.jsp?cid='+id);
}

function del(pk){
    confirmmsg("确定要删除字段吗?","delok("+pk+")");
}

function delok(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrSysProcessService.deleteColumnControlById(ids,deleback);
}

function createProcessMethod(rowObj){
	var str="";
	if(rowObj.isShow ==<%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
		str= "<a href='javascript:void(0)' title='不显示' onclick=\"setColumnStatus("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0' /></a>&nbsp;&nbsp;"
	}else{
		str="<a href='javascript:void(0)' title='显示' onclick=\"setColumnStatus("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>&nbsp;&nbsp;";
	}
	
	str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit1.png' border='0'/></a>&nbsp;&nbsp;";
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>&nbsp;&nbsp;"; 
	str += "<a href='javascript:void(0)' title='向上移动' onclick=\"setPriority('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_UP.value%>)\"><img src='<%=contextPath%>/images/grid_images/treeup.jpg' border='0'/></a>&nbsp;&nbsp;";
	str += "<a href='javascript:void(0)' title='向下移动' onclick=\"setPriority('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value%>)\"><img src='<%=contextPath%>/images/grid_images/treedown.jpg' border='0'/></a>";

	return str;
}

function setPriority(id, flag){
	dwrSysProcessService.setPriority(id,flag,callback);
}

function checkStatus(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要设置选中的字段吗?","setStatus()");
	}else{
	   alertmsg("请你选择要设置的字段！");
	}
}

function setStatus(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.setColumnInfo(recordsPks,callback);
}

function setColumnStatus(pk){
    confirmmsg("确定要设置此字段状态吗?","setStatusok("+pk+")");
}

function setStatusok(pk){
    var ids = new Array();
	ids[0] = pk;
    dwrSysProcessService.setColumnInfo(ids,callback);
}

function callback(data){
    alertmsg(data,"queryData()");
}

function replaceShow(rowObj){
	var str = "";
    if(rowObj.isShow == <%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
    	str = "<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.EDIT.value)%>";
	}else{
		str = "<font color='red'><%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.No_EDIT.value)%></font>";
	}
    return str;
}

function replaceShowSimple(rowObj){
	var str = "";
    if(rowObj.isshowSimple == <%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
    	str = "<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.EDIT.value)%>";
	}else{
		str = "<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.No_EDIT.value)%>";
	}
    return str;
}

function replaceShowAdvanced(rowObj){
	var str = "";
    if(rowObj.isshowAdvanced == <%=EnumUtil.SYS_ISEDIT.EDIT.value%>){
    	str = "<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.EDIT.value)%>";
	}else{
		str = "<%=EnumUtil.SYS_ISEDIT.valueOf(EnumUtil.SYS_ISEDIT.No_EDIT.value)%>";
	}
    return str;
}

function addColumn(){
	var url = '<%=contextPath%>/erp/system_manger/column_control_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("列表字段管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增字段","addColumn()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close1.png"));
    btnList.add(new SysGridBtnBean("显示/不显示","checkStatus()","set1.png"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("tableName","列表名称",1,1,2,0));
    sccList.add(new SysColumnControl("columnName","字段名",1,2,2,0));
    sccList.add(new SysColumnControl("priority","排序",1,2,2,0));
    sccList.add(new SysColumnControl("columnCode","字段代码",1,2,2,0));
    sccList.add(new SysColumnControl("isShow","是否显示",1,2,2,0));
    sccList.add(new SysColumnControl("isshowSimple","是否简单查询",1,2,2,0));
    sccList.add(new SysColumnControl("isshowAdvanced","是否高级查询",1,2,2,0));
    sccList.add(new SysColumnControl("columnStrcount","显示字数",1,2,2,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
    
    //进行高级查询显示处理
    for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if(bc.isShowAdvanced()||bc.isShowColumn()){
			if("isShow".equalsIgnoreCase(bc.getDataName())){
				bc.setColumnReplace("replaceShow");
				bc.setColumnStyle("text-align:center;");
			}
			if("isshowSimple".equalsIgnoreCase(bc.getDataName())){
				bc.setColumnReplace("replaceShowSimple");
				bc.setColumnStyle("text-align:center;");
			}
			if("isshowAdvanced".equalsIgnoreCase(bc.getDataName())){
				bc.setColumnReplace("replaceShowAdvanced");
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
