<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外部邮箱联系人</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var person = getQueryParam();
	var pager = getPager();
	dwrMailService.getOaNetmailPersonByPager(person,pager,queryCallback);
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

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}
function delbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除联系人吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的联系人...");
	}
}

function edit(id){
	MoveDiv.show('编辑联系人','<%=contextPath%>/erp/mobile_sms/netmail_emp_add.jsp?pid='+id);
}

function del(id){
	confirmmsg("确定要删除联系人吗?","delok("+id+")");
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrMailService.deletePersonByPks(recordsPks,delcallback);
}

function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrMailService.deletePersonByPks(pks,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

function add(){
	var url = '<%=contextPath%>/erp/mobile_sms/netmail_emp_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
</script>
</head>
<body>
<%
	SysGrid bg = new SysGrid(request);

bg.setTableTitle("邮箱联系人列表");
bg.setShowImg(false);

//设置附加信息
bg.setQueryFunction("queryData"); //查询的方法名
bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey"); //双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("添加联系人", "add()",	"add.png"));
btnList.add(new SysGridBtnBean("批量删除", "delbatch()",	"close.png"));
bg.setBtnList(btnList);

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("oaNetmailEmpname","人员名称",1,1,1,30));
sccList.add(new SysColumnControl("oaNetmailEmpmail","邮箱地址",1,1,1,50));
sccList.add(new SysColumnControl("oaNetmailDate","创建时间",1,2,1,0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("oaNetmailDate".equalsIgnoreCase(bc.getDataName())){
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
	}
}
bg.setColumnList(colList);
 
//设置列操作对象
bg.setShowProcess(true);
bg.setProcessMethodName("createProcessMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>