<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户综合查询</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
String textid = request.getParameter("textid");
String valueid = request.getParameter("valueid");
String treetype = request.getParameter("treetype");
 %>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var userinfo = getQueryParam();
	userinfo.employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var pager = getPager();
	dwrSysProcessService.listSysUserInfo(userinfo,pager,queryCallback);
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

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}
function userclick(myfrmname) {
	var win = Sys.getfrm(); //获取index页面iframe window对象	
	if (myfrmname != null && myfrmname != "undefined" && myfrmname != undefined) {
		var myfrmnames = myfrmname.split("@@");
    	if(myfrmnames.length>1){
    		for(var i=0;i<myfrmnames.length;i++){
    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
    		}
    	}else{
    		win = win.document.getElementById(myfrmname).contentWindow;
    	}
	}
	var textid = win.document.getElementById("<%=textid%>");
	var valueid = win.document.getElementById("<%=valueid%>");
	var treetype = '<%=treetype%>';
	if (treetype == "radio") {
		if (getOneRecordArray() != false) {
			var obj = getObjectByPk(getOneRecordArray());
			textid.value = obj.employee.hrmEmployeeName;
			valueid.value = obj.primaryKey;
		}else{
	    	alertmsg("请选择相应数据记录...");
	    	return;
		}
	} else {
		var objs = getRowsObject();
		if(objs.length==0){
	    	alertmsg("请选择相应数据记录...");
	    	return;
		}
		var value = "";
		var text = "";
		for (var i = 0; i < objs.length; i++) {
			value += objs[i].primaryKey + ",";
			text += objs[i].employee.hrmEmployeeName + ",";
		}
		if (valueid.value == "" || valueid.value == null) {
			valueid.value = value;
			textid.value = text;
		} else { //去除重复
			var tmps = removerepeat(valueid.value + value, textid.value + text) ;
			textid.value = tmps[1];
			valueid.value = tmps[0];
		}
	}
}

function userclickcustomer(dialogId,myfrmname,method){
	userclick(myfrmname);
	if(method != null && method.length > 0){
		var win = Sys.getfrm();//获取index页面iframe window对象
		var myfrmnames;
		if (myfrmname != null && myfrmname != "undefined" && myfrmname != undefined) {
		    myfrmnames = myfrmname.split("@@");
	    	if(myfrmnames.length>1){
	    		for(var i=0;i<myfrmnames.length;i++){
	    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
	    		}
	    	}else{
	    		win = win.document.getElementById(myfrmname).contentWindow;
	    	}
    	}
		eval("win."+method);
	}
	Sys.close(dialogId);
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title">选择部门</div>
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
if ("radio".equalsIgnoreCase(treetype)){
  bg.setCheckboxOrNum(false);
}
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"用户管理"));
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("userpassword".equalsIgnoreCase(bc.getDataName())){
		bc.setShowColumn(false);
		bc.setShowAdvanced(false);
		bc.setShowQuerySelsect(false);
	}
}
//进行高级查询显示处理
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

//开始创建
out.print(bg.createTable());
%>
</td>
</tr>
</table>
<input type="hidden" id="upcode">
</body>
</html>