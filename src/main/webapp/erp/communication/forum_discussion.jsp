<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>论坛讨论</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var forum = new Object();
	var pager = getPager();
	dwrOaCommunicationService.listForums(forum,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

function enterChat(){
	//针对多条记录进行操作
	if(getOneRecordArray() != false){
		var obj=getObjectByPk(getOneRecordArray());
		Sys.load("posts_list.jsp?forumId="+obj.primaryKey,'rightfrm');
	}else{
	    alert("没有选择记录或选择多条记录，请重新选择！");
	}
}

function  enter(pk){
    Sys.load("posts_list.jsp?forumId="+pk,'rightfrm');
}

//双击数据
function dblCallback(obj){
	var t = obj.id;
	var ids = t.split("_"); 
	//alert(ids[1]);	//此处的pk为定义的pk
	Sys.load("posts_list.jsp?forumId="+ids[1],'rightfrm');
}

function getPostCount(rowObj){
   var count = 0; 
   if(rowObj.oaPostsCount != null && rowObj.oaPostsCount != undefined){
        count = rowObj.oaPostsCount;
   }
   return count;
}

function createProcessMethod(rowObj){
    var str="";
	str = "<a href='javascript:void(0)' title='进入' onclick=\"enter('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;&nbsp;";
    return  str;
}
</script>
	</head>
	<body>
<%
	SysGrid nw = new SysGrid(request);
	nw.setTableTitle("论坛交流");
	nw.setIsautoQuery(true);
	nw.setShowImg(false);
	nw.setQueryFunction("queryData"); //查询的方法名
	nw.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	nw.setDblBundle("primaryKey"); //双击列的绑定的列值

	ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("进入", "enterChat()", "set.png"));
	nw.setBtnList(btnList);

	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("oaForumName", "版块名称", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("oaForumText", "版块描述", 1, 2, 2, 50));
	sccList.add(new SysColumnControl("employee.hrmEmployeeName", "创建人", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("oaForumTime", "创建时间", 1, 2, 2, 0));
	//sccList.add(new SysColumnControl("oaCount","帖子数",1,2,2,0));

	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

	colList.add(ColumnUtil.getCusterShowColumn("postcount", "帖子数", "getPostCount", 0, "text-align:center"));

	nw.setColumnList(colList);

    //设置列操作对象
	nw.setShowProcess(true);//默认为false 为true请设置processMethodName
	nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

	//开始创建
	out.print(nw.createTable());
%>
	</body>
</html>
