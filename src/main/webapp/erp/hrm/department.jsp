<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<title>机构管理</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var department = getQueryParam();
	
	var coder = document.getElementById("upcode").value;
    if(coder != null && coder.length>0){
    	department.hrmDepUpid = coder;
  	}	
  	
	var pager = getPager();
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/department_detail.jsp?depid='+obj.value,'750','450');
		box.msgtitle="<b>查看组织机构明细</b>";
		box.show();	
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的机构吗?","delDep()");
	}else{
	   alertmsg("请选择要删除的机构！");
	}
}

function delDep(){
	var recordsPks = getAllRecordArray();
	dwrHrmEmployeeService.deleteDepartmentById(recordsPks,delcallback);
}

function delcallback(data){
   tree.reload();
   alertmsg(data,"queryData()");
}

function del(pk){
	confirmmsg("确定要删除机构吗?","delok("+pk+")");
}

function delok(id){
	var ids = new Array();
	ids[0] = id;
    dwrHrmEmployeeService.deleteDepartmentById(ids,delcallback);
}

function edit(id){
   MoveDiv.show('编辑组织机构','<%=contextPath%>/erp/hrm/department_add.jsp?departmentpk='+id+'');
}

function createProcessMethod(rowObj){
	var str="";
	str= "<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='负责人设置' onclick=\"set('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/seta.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	var coder = document.getElementById("upcode").value;
    if(coder != null && coder.length>0){
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='向上移动' onclick=\"treeshowmove('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_UP.value%>)\"><img src='<%=contextPath%>/images/grid_images/treeup.jpg' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='向下移动' onclick=\"treeshowmove('"+rowObj.primaryKey+"',<%=EnumUtil.Tree_MOVE_Type.MOVE_DOWN.value%>)\"><img src='<%=contextPath%>/images/grid_images/treedown.jpg' border='0'/></a>";
	}
	return str;
}

function set(id){
	var box = new Sys.msgbox('负责人设置',"<%=contextPath%>/erp/select_takepage/select_empbydeptid.jsp?deptid="+id+"&textid=tmptxt",'700','450');
	box.msgtitle="<b>机构人员信息</b>";
	var butarray =new Array();
	butarray[0] = "ok|clickok('"+box.dialogId+"','undefined','setok()');";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
	deptid = id;
}

function treeshowmove(id,type){
	dwrHrmEmployeeService.treeMoveShowRow(id,type,movecallback);
}

function movecallback(data){
	alertmsg(data,"moveok()");
}

function moveok(){
	tree.reload();
	queryData();
}
function setok(){
	var str = document.getElementById("tmptxt").value;
	if(deptid!=null&&str.length>0){
		dwrHrmEmployeeService.updateDepartmentMangerById(deptid,str,setcallback);
	}
}

function setcallback(data){
	document.getElementById("tmptxt").value="";
	alertmsg(data,"queryData()");
}

function repcolor(rowObj){
	var str="";
	if(rowObj.employee==null || rowObj.employee.hrmEmployeeName==null){
		str ="<font color='red'><无></font>";
	}else{
		str = "<font color='green'/>"+rowObj.employee.hrmEmployeeName+"</font>";
	}
	return str;
}

//机构树选择
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
		SysGrid bg = new SysGrid(request);

		//设置高度及标题
		bg.setTableHeight("100%");//可以不指定,默认为100%
		bg.setTableTitle("机构管理");
		bg.setShowImg(false);

		//设置附加信息
		bg.setQueryFunction("queryData"); //查询的方法名
		bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
		bg.setDblBundle("primaryKey"); //双击列的绑定的列值

		//放入按钮
		ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
		btnList
				.add(new SysGridBtnBean("批量删除", "deleteObject()",
						"close.png"));
		bg.setBtnList(btnList);

		//放入列
		ArrayList<SysGridColumnBean> colList = UtilTool
				.getGridColumnList(UtilTool.getColumnShow(this
						.getServletContext(), "部门列表"));

		//进行高级查询显示处理
		for (int i = 0; i < colList.size(); i++) {
			SysGridColumnBean bc = colList.get(i);
			if (bc.isShowAdvanced() || bc.isShowColumn()) {
                                  if("employee.hrmEmployeeName".equalsIgnoreCase(bc.getDataName())){
	                  bc.setColumnReplace("repcolor");
	                  bc.setColumnStyle("text-align:center");
                 }
			}
		}
		bg.setColumnList(colList);
		
		
		
		//设置列操作对象
		bg.setShowProcess(true);//默认为false 为true请设置processMethodName
		bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

		//开始创建
		out.print(bg.createTable());
	%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
<input type="hidden" id="tmptxt">
</body>
</html>