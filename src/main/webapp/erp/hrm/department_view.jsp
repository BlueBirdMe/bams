<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<title>机构查看</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var department = getQueryParam();
	var coder = document.getElementById("upcode").value;
	    if(coder != null ){
	    department.hrmDepUpid = coder;
        }	
	var pager = getPager();
	//不封装对象集合（按顺序组合)
	var c =getCustomerParam();
	//alert(c);
	dwrHrmEmployeeService.listDepartments(department,pager,queryCallback);
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
    //MoveDiv.show('查看组织机构明细','<%=contextPath%>/erp/hrm/department_detail.jsp?depid='+obj.value);
    var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/department_detail.jsp?depid='+obj.value,'750','550');
		box.msgtitle="<b>查看组织机构明细</b>";
		box.show();
}

//列显示替换方法
function repleaDep(rowObj){
	var str="";
	if(rowObj.hrmEmployeeSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<font style='color:blue'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%></font>"
	}
	return str;
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
	<div class="div_title">选择机构</div>
	<div class="div_content">
	<jsp:include page="departmentxmlshow.jsp" flush="false"></jsp:include>
	</div>
</td>
<td>	
<%
	SysGrid bg =new SysGrid(request);
					
	//设置高度及标题
	bg.setTableHeight("100%");//可以不指定,默认为100%
	
	bg.setTableTitle("机构查看");
	bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
	 
	//设置附加信息
	bg.setQueryFunction("queryData");	//查询的方法名
	bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey");		//双击列的绑定的列值
	bg.setCheckboxOrNum(false);
	
	//放入按钮
	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
	bg.setBtnList(btnList);
	
	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"部门列表"));
	
	bg.setColumnList(colList);
	
	//开始创建
	out.print(bg.createTable());
%>
</td>
</tr>
</table>
	<input type="hidden" id="upcode">
</body>
</html>