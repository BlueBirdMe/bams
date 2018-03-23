<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var post = getQueryParam();
	post.hrmPostUpid = document.getElementById("postcode").value;
	var pager = getPager();
	dwrHrmEmployeeService.listPostByPager(post,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/post_detail.jsp?pid='+obj.value,'750','550');
		box.msgtitle="<b>查看岗位信息明细</b>";
		box.show();
}

</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
	<div class="div_title">
		选择岗位
	</div>
	<div class="div_content">
	<jsp:include page="post_tree.jsp" flush="false"></jsp:include>
	</div>
</td>
<td>
<%
	SysGrid bg = new SysGrid(request);

	bg.setTableTitle("工作岗位列表");
	bg.setShowImg(false);

	//设置附加信息
	bg.setQueryFunction("queryData"); //查询的方法名
	bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey"); //双击列的绑定的列值
	bg.setCheckboxOrNum(false);

	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool
			.getGridColumnList(UtilTool.getColumnShow(this
					.getServletContext(), "工作岗位"));

	bg.setColumnList(colList);

	//开始创建
	out.print(bg.createTable());
%>
</td>
</tr>
</table>
</body>
</html>