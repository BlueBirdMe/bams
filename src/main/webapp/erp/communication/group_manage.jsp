<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>分组管理</title>
		<script>
//查询方法
function queryData(){
	startQuery();
	var group = getQueryParam();
	var pager = getPager();
	dwrOaCommunicationService.listChatGroup(group,pager,queryCallback);
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
	Sys.load('<%=contextPath%>/erp/communication/communication_view.jsp?groupId='+obj.value);
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
	    confirmmsg("确定要删除选中的通讯分组吗?","delRecord()");	
	}else{
	    alertmsg("请选择要删除的通讯分组！");
	}
}

function delRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaCommunicationService.deleteGroupById(recordsPks,deleCallback);
}

function deleCallback(data){
    alertmsg(data,"queryData()");
}

function del(id){
    confirmmsg("确定要删除此通讯分组吗？","delok("+id+")");
}

function delok(id){
    var ids = new Array();
    ids[0] = id;
    dwrOaCommunicationService.deleteGroupById(ids,deleCallback);
}

function add(){
	var url = '<%=contextPath%>/erp/communication/group_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function edit(id){
	MoveDiv.show('编辑','<%=contextPath%>/erp/communication/group_add.jsp?groupId='+id);
}

function getCommunicationCount(rowObj){
   var count = 0; 
   if(rowObj.communicationCount != null && rowObj.communicationCount != undefined){
        count = rowObj.communicationCount;
   }
   return count;
}

function createProcessMethod(rowObj){
	var str="";
	str= "<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;&nbsp;"
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	return str;
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("分组管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增分组","add()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    
    nw.setBtnList(btnList);
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"通讯录分组"));
   
    colList.add(ColumnUtil.getCusterShowColumn("filecount","通讯记录数量","getCommunicationCount",0,"text-align:center"));
   
    nw.setColumnList(colList);
    
    //设置列操作对象
    nw.setShowProcess(true);//默认为false 为true请设置processMethodName
    nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象   
    
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
