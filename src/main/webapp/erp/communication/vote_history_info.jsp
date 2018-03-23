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
		 %>
		<title>投票历史记录</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var vote = getQueryParam();
	vote.oaVoteStatus = <%=EnumUtil.OA_VOTE_STATUS.END.value%>;
	var pager = getPager();
	dwrOaCommunicationService.listVoteView(vote,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data.message);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
    dwrOaCommunicationService.getOnlyVoteByid(obj.value,setViewPager);
}

function setViewPager(data){
    if(data.success == true){
		if(data.resultList.length > 0){
			var vote = data.resultList[0];
			if(<%=user%> == vote.oaVoteEmp){
				var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+vote.primaryKey,'800','500');
				box.msgtitle="<b>查看投票结果</b>";
				box.show();
			}else{
				if(vote.oaViewType == <%=EnumUtil.OA_VOTE_VIEW_TYPE.PREVIOUS.value%>){
					var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+vote.primaryKey,'800','500');
					box.msgtitle="<b>查看投票结果</b>";
					box.show();
				}else if(vote.oaViewType == <%=EnumUtil.OA_VOTE_VIEW_TYPE.NO.value%>){
				    alertmsg("该投票结果不允许查看！");
				}else{
					var box = new Sys.msgbox('投票结果查看','<%=contextPath%>/erp/communication/vote_view_pager.jsp?voteId='+vote.primaryKey,'800','500');
					box.msgtitle="<b>查看投票结果</b>";
					box.show();
				}
			}
		}else{
		     alertmsg(data.message);
		}
	}else{
	     alertmsg(data.message);
	}
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

</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("投票历史");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    nw.setCheckboxOrNum(false);
    nw.setTableRowSize(20);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaVoteStatus","状态",1,2,2,0));
    sccList.add(new SysColumnControl("oaVoteName","投票名称",1,1,2,0));
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
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
