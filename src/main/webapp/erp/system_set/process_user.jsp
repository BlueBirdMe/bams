<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程用户编组</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var userinfo = getQueryParam();
	var pager = getPager();
	var depid = document.getElementById("upcode").value;
	dwrApproveProcessService.listSysUserForProcess(userinfo,depid,pager,queryCallback);
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
}

function repleaValid(rowObj){
	var str="";
	if(rowObj.userAction == <%=EnumUtil.SYS_ISACTION.No_Vaild.value%>){
		str= "<font style='color:red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}else{
		str= "<font style='color:green'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%></font>";
	}
	return str;
}

function createMethod(rowObj){
	var str="<a href='javascript:void(0)' title='用户编组' onclick=\"setgroup('"+rowObj.hrmEmployeeId+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	return str
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}


function setgroup(id){
	MoveDiv.show('设置用户组','<%=contextPath%>/erp/system_set/process_user_group.jsp?id='+id+'');
}

</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title"">选择部门</div>
<div class="div_content">
<jsp:include page="../hrm/departmentxmlshow.jsp" flush="false">
	<jsp:param name="ischeck" value="true"/>
</jsp:include>
</div>
</td>
<td>
<%
SysGrid bg =new SysGrid(request);
bg.setShowView(SysGrid.SHOW_TABLE);
bg.setTableTitle("用户列表"); 

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();

sccList.add(new SysColumnControl("employee.hrmEmployeeName", "姓名",1, 2, 2, 0));
sccList.add(new SysColumnControl("employee.hrmDepartment.hrmDepName", "部门名称 ",1, 2, 2, 0));
sccList.add(new SysColumnControl("processGroup", "所属流程用户组",1, 2, 2, 0));
sccList.add(new SysColumnControl("userAction", "是否有效",1, 2, 2, 0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("userAction".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式

		SelectType select  = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择是否有效-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
		bc.setColumnReplace("repleaValid");
		
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
}

bg.setColumnList(colList);
bg.setCheckboxOrNum(false);
bg.setShowProcess(true);
bg.setProcessMethodName("createMethod");
//开始创建
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
</body>
</html>