<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
 <%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<%
		      String useId = UtilTool.getEmployeeId(request);
		      String user = "'"+useId+"'";
		      long depId = UtilTool.getDeptId(request);
		 %>
		<title>投票管理</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var vote = getQueryParam();
	vote.oaVoteStatus = <%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>;
	var pager = getPager();
	dwrOaCommunicationService.listVoteView(vote,pager,queryCallback);
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
     var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+obj.value, '750','550');
     box.msgtitle="<b>查看投票结果</b>";
     box.show();
}

//进行投票
function viewInfo(pk){
   MoveDiv.show('进行投票','<%=contextPath%>/erp/communication/emp_voting_page.jsp?voteId='+pk);
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

function createProcessMethod(rowObj){
	var dtr="";
	if(rowObj.voteCount == 0){
	     dtr = "<a href='javascript:void(0)' title='投票' onclick=\"viewInfo('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/toupiao.png' border='0'/></a>";
	}else{
	     dtr = "<a href='javascript:void(0)' title='已投票，不能投票' ><img src='<%=contextPath%>/images/grid_images/toupiao.png' border='0' style='filter:gray;'/></a>";
	}
	return dtr;
}

</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("投票进行中");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    nw.setCheckboxOrNum(false);
    nw.setTableRowSize(20);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaVoteStatus","状态",1,2,2,0));
    sccList.add(new SysColumnControl("oaVoteName","投票名称",1,1,2,30));
    sccList.add(new SysColumnControl("employee.hrmEmployeeName","发起人",1,1,2,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaVoteStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_VOTE_STATUS.getSelectAndText("-1,-请选择投票状态类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("replaceVoteStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaVoteName".equalsIgnoreCase(bc.getDataName())){
	bc.setColumnStyle("text-align:left;");
		}
	}
   }
    
    nw.setColumnList(colList);
    
    //设置列操作对象
	nw.setShowProcess(true);//默认为false 为true请设置processMethodName
	nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
