<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作地区设置</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">	
//查询方法
function queryData(){
	startQuery();
	var workarea = getQueryParam();
	var pager = getPager();
	dwrHrmEmployeeService.listWorkarea(workarea,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/workAddress_detail.jsp?pid='+obj.value,'700','400');
	box.msgtitle="<b>工作地区明细查看</b>";
	box.show();
}
</script>
</head>
<body>
	<%
		SysGrid grid = new SysGrid(request,"工作地区列表");
		grid.setShowImg(false);//不显示图片信息.
		grid.setCheckboxOrNum(false);

		ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
		
	    sccList.add(new SysColumnControl("hrmAreaName","地区名称",1,1,2,0));
	    sccList.add(new SysColumnControl("hrmAreaEngname","英文名称",1,1,2,0));
	    sccList.add(new SysColumnControl("hrmAreaDesc","备注",1,2,2,20));
	    
	    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
		grid.setColumnList(colList);
		
		//设置附加信息
		grid.setQueryFunction("queryData");	//查询的方法名
		grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
		grid.setDblBundle("primaryKey");	//双击列的绑定的列值
		
		//开始创建
		out.print(grid.createTable());
	%>		
</body>
</html>