<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位人员拾取</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<%
String postid = request.getParameter("postid");
String textid = request.getParameter("textid");
%>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var pager = getPager();
	dwrHrmEmployeeService.getHrmEmployeeListByMainPostId(<%=postid%>,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

function clickok(myfrmname,method){
    var win = Sys.getfrm();//获取index页面iframe window对象
    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
    	win = win.document.getElementById(myfrmname).contentWindow;	
    }
    if(getAllRecordArray() != false){
    	win.document.getElementById('<%=textid%>').value = getAllRecordArray()[0];
    }else{
    	alertmsg("请选择相应人员...",null,null,window);
    	return;
    }
	Sys.close();
	eval("win."+method);
}
function dblCallback(obj){}
</script>
</head>
<body>

<%
	SysGrid bg =new SysGrid(request);

//设置高度及标题
bg.setTableHeight("100%");//可以不指定,默认为100%
bg.setTableTitle("岗位人员");
bg.setBorder(1);
bg.setShowImg(false);
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值
bg.setCheckboxOrNum(false);
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("hrmEmployeeName","姓名",1,1,2,0));
sccList.add(new SysColumnControl("hrmEmployeeCode","工号",1,2,2,0));
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

bg.setColumnList(colList);
//开始创建
out.print(bg.createTable());
%>
</body>
</html>