<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrOaCommunicationService.js"></script>
		<%
		     String useId = UtilTool.getEmployeeId(request);
		     String user = "'"+useId+"'";
		 %>
		<title>投票管理</title>
		<script>
//查询方法
function queryData(){
	startQuery();
	var vote = getQueryParam();

	var id = <%=user%>;
    vote.oaVoteEmp = id;

	var pager = getPager();
	dwrOaCommunicationService.listVote(vote,pager,queryCallback);
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
	var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+obj.value,'800','500');
		box.msgtitle="<b>查看投票结果</b>";
		box.show();
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的投票记录吗?","delRecord()");
	}else{
	    alertmsg("请选择要删除的投票记录");
	}
}

function delRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaCommunicationService.deleteVote(recordsPks,deleteCallback);
}

function del(pk){
    confirmmsg("确定要删除此投票记录吗?","delok("+pk+")");
}

function delok(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrOaCommunicationService.deleteVote(ids,deleteCallback);
}

function deleteCallback(data){
    alertmsg(data,"queryData()");
}

function edit(pk){
    MoveDiv.show('编辑投票','<%=contextPath%>/erp/communication/vote_add.jsp?voteId='+pk+'');
}

function replaceChooseType(rowObj){
    var str="";
	if(rowObj.oaChooseType ==<%=EnumUtil.OA_VOTE_OPTIONS_TYPE.RADIO.value%>){
		str= "<%=EnumUtil.OA_VOTE_OPTIONS_TYPE.valueOf(EnumUtil.OA_VOTE_OPTIONS_TYPE.RADIO.value)%>"
	}else{
		str= "<%=EnumUtil.OA_VOTE_OPTIONS_TYPE.valueOf(EnumUtil.OA_VOTE_OPTIONS_TYPE.CHECKBOX.value)%>"
	}
	return str;
}

function replaceIsAnonymous(rowObj){
    var str="";
	if(rowObj.oaIsAnonymous ==<%=EnumUtil.OA_VOTE_IS_ANONYMOUS.YES.value%>){
		str= "<%=EnumUtil.OA_VOTE_IS_ANONYMOUS.valueOf(EnumUtil.OA_VOTE_IS_ANONYMOUS.YES.value)%>"
	}else{
		str= "<%=EnumUtil.OA_VOTE_IS_ANONYMOUS.valueOf(EnumUtil.OA_VOTE_IS_ANONYMOUS.NO.value)%>"
	}
	return str;
}

function replaceVoteStatus(rowObj){
    var str="";
	if(rowObj.oaVoteStatus ==<%=EnumUtil.OA_VOTE_STATUS.NOSTART.value%>){
		str= "<font style='color:blue'><%=EnumUtil.OA_VOTE_STATUS.valueOf(EnumUtil.OA_VOTE_STATUS.NOSTART.value)%></font>";
	}else if(rowObj.oaVoteStatus ==<%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>){
		str= "<font style='color:green'><%=EnumUtil.OA_VOTE_STATUS.valueOf(EnumUtil.OA_VOTE_STATUS.VOTING.value)%></font>";
	}else{
	    str= "<font style='color:red'><%=EnumUtil.OA_VOTE_STATUS.valueOf(EnumUtil.OA_VOTE_STATUS.END.value)%></font>";
	}
	return str;
}

function setVoteEnd(pk){
    confirmmsg("确定要立即结束此投票吗?","setEndOk("+pk+")");
}

function setEndOk(id){
    var status = "end";
    dwrOaCommunicationService.setVoteStatus(id,status,setCallback);
}

function setVoteReused(pk){
    confirmmsg("确定要重新启动此投票吗?","setReusedOk("+pk+")");
}

function setReusedOk(id){
    var status = "revote";
    dwrOaCommunicationService.setVoteStatus(id,status,setCallback);
}

function setVoteStart(pk){
    confirmmsg("确定要手动启动此投票吗?","setStartOk("+pk+")");
}

function setStartOk(id){
    var status = "start";
    dwrOaCommunicationService.setVoteStatus(id,status,setCallback);
}

function setCallback(data){
    alertmsg(data,"queryData()");
}

function createProcessMethod(rowObj){
	var dtr="";
	if(rowObj.oaVoteStatus ==<%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>){
		dtr= "<a href='javascript:void(0)' title='立即终止' onclick=\"setVoteEnd('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid_.png' border='0'/></a>&nbsp;&nbsp";
	}else if(rowObj.oaVoteStatus ==<%=EnumUtil.OA_VOTE_STATUS.END.value%>){
		dtr="<a href='javascript:void(0)' title='重新启用' onclick=\"setVoteReused('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid.png' border='0'/></a>&nbsp;&nbsp";
	}else{
	    dtr="<a href='javascript:void(0)' title='未开始,启用?' onclick=\"setVoteStart('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>&nbsp;&nbsp";
	}
	
	var str ="";
	if(rowObj.oaVoteStatus  == <%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>){
	    str = "<a href='javascript:void(0)' title='正在投票中，无法编辑' ><img src='<%=contextPath%>/images/grid_images/rowedit_.png' border='0'/></a>&nbsp;"
	}else{
	    str = "<a href='javascript:void(0)' title='编辑投票内容' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;"
	}
	
	var xtr = "";
	if(rowObj.oaVoteStatus  == <%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>){
	    xtr = "<a href='javascript:void(0)' title='删除' ><img src='<%=contextPath%>/images/grid_images/rowdel_.png' border='0'/></a>"
	}else{
	    xtr = "<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	}
	dtr += str;
	dtr += xtr; 
	return dtr;
}
</script>
	</head>
	<body>
<%
	SysGrid nw = new SysGrid(request);
	nw.setTableTitle("投票管理");
	nw.setIsautoQuery(true);
	nw.setShowImg(false);
	nw.setQueryFunction("queryData"); //查询的方法名
	nw.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	nw.setDblBundle("primaryKey"); //双击列的绑定的列值
	nw.setTableRowSize(20);

	ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("批量删除", "deleteObject()", "close.png"));
	nw.setBtnList(btnList);

	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(), "投票列表"));

	//进行高级查询显示处理
	for (int i = 0; i < colList.size(); i++) {
		SysGridColumnBean bc = colList.get(i);
		if (bc.isShowAdvanced() || bc.isShowColumn()) {
	if ("oaVoteStart".equalsIgnoreCase(bc.getDataName())) {
		//高级查询显示
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
		//列样式
		bc.setColumnStyle("text-align:center;");
	}
	if ("oaVoteEnd".equalsIgnoreCase(bc.getDataName())) {
		//高级查询显示
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
		//列样式
		bc.setColumnStyle("text-align:center;");
	}
	if ("oaVoteType".equalsIgnoreCase(bc.getDataName())) {
		//设置高级查询显示样式

		SelectType select = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(), request, "-1,-请选择投票类型-", "22"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);

		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if ("oaChooseType".equalsIgnoreCase(bc.getDataName())) {
		//设置高级查询显示样式

		SelectType select = new SelectType(EnumUtil.OA_VOTE_OPTIONS_TYPE.getSelectAndText("-1,-请选择投票选项类型-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);

		bc.setColumnReplace("replaceChooseType");

		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if ("oaIsAnonymous".equalsIgnoreCase(bc.getDataName())) {
		//设置高级查询显示样式

		SelectType select = new SelectType(EnumUtil.OA_VOTE_IS_ANONYMOUS.getSelectAndText("-1,-请选择是否匿名投票-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);

		bc.setColumnReplace("replaceIsAnonymous");
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if ("oaVoteStatus".equalsIgnoreCase(bc.getDataName())) {
		//设置高级查询显示样式

		SelectType select = new SelectType(EnumUtil.OA_VOTE_STATUS.getSelectAndText("-1,-请选择投票状态类型-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);

		bc.setColumnReplace("replaceVoteStatus");
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
