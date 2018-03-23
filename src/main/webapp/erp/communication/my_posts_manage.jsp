 <%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>已发帖子</title>
		<%
		    String userId = UtilTool.getEmployeeId(request);
		    String userid = "'"+userId+"'";
		 %>
<script>
//查询方法
function queryData(){
	startQuery();
	var posts = getQueryParam();
    var usid = <%=userid%>;
    posts.oaPostEmp = usid;
	var pager = getPager();
	dwrOaCommunicationService.listPosts(posts,pager,queryCallback);
}
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

//发帖
function add(){
	var url = '<%=contextPath%>/erp/communication/posts_add.jsp?flag=true';
	openMDITab(url + "&tab="+getMDITab());
}
//双击数据
function dblCallback(obj){
	var t = obj.id;
	var ids = t.split("_"); 
	var url = "communication/view_posts_reg.jsp?postsId="+ids[1];
	openMDITab(url);
}

function replaceType(rowObj){
    var str="";
	if(rowObj.oaIsBoutique ==<%=EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value%>){
		str= "<font style='color:red'><%=EnumUtil.OA_POSTS_IS_BOUTIQUE.valueOf(EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value)%></font>";
	}else{
		str= "<font style='color:#8E8E8E'><%=EnumUtil.OA_POSTS_IS_BOUTIQUE.valueOf(EnumUtil.OA_POSTS_IS_BOUTIQUE.UNBOUTIQUE.value)%></font>";
	}
	return str;
}
</script>
	</head>
	<body>
<%
	SysGrid nw = new SysGrid(request);
	nw.setTableTitle("我的帖子");
	nw.setIsautoQuery(true);
	nw.setShowImg(false);
	nw.setQueryFunction("queryData"); //查询的方法名
	nw.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	nw.setDblBundle("primaryKey"); //双击列的绑定的列值
	nw.setCheckboxOrNum(false);

	ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("发布新帖", "add()", "add.png"));

	nw.setBtnList(btnList);

	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(), "帖子列表"));

	nw.setColumnList(colList);

	//进行高级查询显示处理
	for (int i = 0; i < colList.size(); i++) {
		SysGridColumnBean bc = colList.get(i);
		if (bc.isShowAdvanced() || bc.isShowColumn()) {
	if("oaPostTime".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		    }
		   if("oaPostForum".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getForumsInfoList(this.getServletContext(),request,"-1,-请选择所属版块-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		    }
		    if("oaIsBoutique".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_POSTS_IS_BOUTIQUE.getSelectAndText("-1,-请选择类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("replaceType");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		    }
		    if("oaPostName".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnStyle("text-align:left;");
		    }
		}
	}

	//开始创建
	out.print(nw.createTable());
%>
	</body>
</html>
