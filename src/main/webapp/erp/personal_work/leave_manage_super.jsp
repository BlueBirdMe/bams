<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<title>请假管理</title>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var leave = getQueryParam();
	var pager = getPager();
	dwrPersonalProcessService.listAllOaLeaver(leave, pager, queryCallback);
}

function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alert(data.message);
	}
	endQuery();
}

function handle(leaveId,taskId,taskDefinitionKey,taskName) {
	window.location = "<%=contextPath%>/erp/personal_work/flow_handle_leave.jsp?leaveId="+leaveId+"&taskId="+taskId+"&definitionKey="+taskDefinitionKey+"&name="+encodeURI(taskName) ;
}

function showDetail(primaryKey, processInstanceId) {
	var url = "<%=contextPath%>/erp/personal_work/flow_detail_leave.jsp?pk="+primaryKey+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url, '800', '500');
	box.msgtitle = "<b>请假申请明细列表</b>";
	box.show();
}

function createProcessMethod(rowObj) {
	var str="";
	if(rowObj.status == 0){
	    str += "<a href='javascript:void(0)' title='查看详情'><img src='<%=contextPath%>/images/grid_images/rowinfo_.png' border='0'/></a>";
    }else{
		str += "<a href='javascript:void(0)' title='查看详情' onclick=\"detail('"+rowObj.primaryKey+"','"+rowObj.processInstanceId+"')\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>";
	}
	str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	
    return str;
}

function edit(pk){
	var url = "<%=contextPath%>/erp/personal_work/leave_add.jsp?pk="+pk+"&tab="+getMDITab();
	openMDITab(url);
}

function del(pk){
    confirmmsg("确定要删除请假信息吗?","delok('"+pk+"')");
}
function delok(pk){
    var pks = new Array();
    pks[0] = pk;
    dwrPersonalProcessService.deleteOaLeaverByPksSuper(pks,delCallback);
}
function delCallback(data){
    alertmsg(data,"queryData()");
}
function delbatch(){
    if(getAllRecordArray() != false){
        confirmmsg("确定要删除请假信息吗?","delbatchok()");
    }else{
        alertmsg("请选择要删除的请假信息...");
    }
}
function delbatchok(){
    var pks = getAllRecordArray();
    dwrPersonalProcessService.deleteOaLeaverByPksSuper(pks,delCallback);
}

function detail(primaryKey, processInstanceId) {
	var url = "<%=contextPath%>/erp/personal_work/leave_detail.jsp?pk="+primaryKey+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url);
	box.show();
}

function repleaStatus(rowObj){
	var	str="";
	if(rowObj.status == 0){
		str = "<font color='brown'>草稿</font>";
	}else if(rowObj.status == 1){
		str = "<font color='red'>申请中</font>";
	}else if(rowObj.status == 2){
		str = "<font color='green'>申请结束</font>";
	}
	return str;
}


	
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
	bg.setTableTitle("请假管理");
	//设置附加信息
	bg.setQueryFunction("queryData");	//查询的方法名
	bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey");		//双击列的绑定的列值
	
	//放入按钮
	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
	//btnList.add(new SysGridBtnBean("请假申请","add()","add.png"));
	btnList.add(new SysGridBtnBean("批量删除","delbatch()","close.png"));
	bg.setBtnList(btnList);

	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("applydata","申请时间",1,2,2,0));
	sccList.add(new SysColumnControl("applyEmployee.hrmEmployeeName","申请人",1,2,2,0));
	sccList.add(new SysColumnControl("library.libraryInfoName","请假类型",1,2,2,0));
	sccList.add(new SysColumnControl("startdata","请假开始时间",1,2,2,0));
	sccList.add(new SysColumnControl("enddata","请假结束时间",1,2,2,0));
	sccList.add(new SysColumnControl("leavereason","请假事由",1,2,2,20));
	sccList.add(new SysColumnControl("status","申请状态",1,2,2,0));
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("status".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repleaStatus");
			bc.setColumnStyle("text-align:center");
		}
	}
	bg.setColumnList(colList);
	bg.setCheckboxOrNum(true);
	bg.setShowProcess(true);
	bg.setShowImg(false);
	bg.setProcessMethodName("createProcessMethod");
	//开始创建
	out.print(bg.createTable());
%>
</body>
</html>