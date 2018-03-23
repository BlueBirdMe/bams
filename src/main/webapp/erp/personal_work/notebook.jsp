<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script>
<title>个人便签</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var Notebook = getQueryParam();
	var pager = getPager();
	dwrPersonalOfficeService.listNotebook(Notebook,pager,queryCallback);
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
	if(getAllRecordArray() != false){
	   confirmmsg("确定要删除选中的个人便签吗?","deleNotebook()");
	}else{
	   alertmsg("请选择要删除的个人便签！");
	}
}

function deleNotebook(){
    var recordsPks = getAllRecordArray();
    dwrPersonalOfficeService.deleteNotebookById(recordsPks,deleback);
}

function deleback(data){
   alertmsg(data,"queryData()");
}

function add(){
	var url = '<%=contextPath%>/erp/personal_work/oa_notebook_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function del(pk){
    confirmmsg("确定要删除个人便签吗?","delok("+pk+")");
}

function delok(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrPersonalOfficeService.deleteNotebookById(ids,deleback);
}

function createProcessMethod(rowObj){
	var str="";
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	return str;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.msgIsEffective ==<%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str= "<font style='color:green'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>"
	}
	return str;
}

function callback(data){
    alertmsg(data,"queryData()");
}

function dblCallback(obj){
	var box = new Sys.msgbox('便签明细','<%=contextPath%>/erp/personal_work/oa_notebook_detail.jsp?aid='+obj.value,'800','500');
	box.msgtitle="<b>个人便签明细</b>";		
	box.show();
}
</script>
	</head>
	<body>

<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("个人便签");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增便签","add()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    nw.setBtnList(btnList);
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"个人便签"));
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("oaNotebookCreattime".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("text-align:center;");
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
