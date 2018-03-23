<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<title>在线人员</title>
<script type="text/javascript">
var t = null;
window.onload = function(){
	t = window.setInterval("queryData()",1000*60*2);
}
	
window.onunload = function(){
	if(t!=null){
		window.clearInterval(t);
	}
}

function queryData(){
	startQuery();
	var employee = getQueryParam();
	employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var bl = document.getElementById("onlinechk").checked;
	var pager = getPager();
	dwrSysProcessService.listOnline(employee,bl,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}

function sendMSG(employeepk){
	var url = "<%=contextPath%>/erp/personal_work/online_sms_send.jsp?employeepk='"+employeepk+"'";
	openMDITab(url);
}

function sendEmail(employeepk){     
	var url = "<%=contextPath%>/erp/personal_work/online_email_send.jsp?employeepk='"+employeepk+"'";
	openMDITab(url);
}

function createProcessMethod(rowObj){
	var str="";
	
	str += "<a href='javascript:void(0)' title='发送短信' onclick=\"sendMSG('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/send_msg.png' height='12'  border='0'/></a>&nbsp;&nbsp;";
	str += "<a href='javascript:void(0)' title='发送邮件' onclick=\"sendEmail('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/send_mail.png' height='12' border='0'/></a>"; 
	return str;
}

function replaceLine(rowObj){
    var str = "";
    if(rowObj.isOnLine == 1){
         str = "<img src='<%=contextPath%>/images/grid_images/online.png' border='0' title='在线'/>";
    }else{
         str = "<img src='<%=contextPath%>/images/grid_images/online_.png' border='0' title='离线'/>";
    }
    return str;
}
//列显示性别替换方法
function repleaSex(rowObj){
	var str="";
	if(rowObj.employeeSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%>"
	}else{
		str= "<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%>"
	}
	return str;
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}

function repimgtxt(rowObj){
	var str = "";
    if(rowObj.isOnLine == 1){
         str = "<img src='<%=contextPath%>/images/grid_images/online.png' border='0' title='在线' style='vertical-align: middle;'/>&nbsp;"+rowObj.employeeName;
    }else{
         str = "<img src='<%=contextPath%>/images/grid_images/online_.png' border='0' title='离线' style='vertical-align: middle;'/>&nbsp;"+rowObj.employeeName;
    }
    return str;
}

//双击数据
function dblCallback(obj){
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/personal_work/person_detail.jsp?employeepk='+obj.value,'800','500');
		box.msgtitle="<b>查看人员信息明细</b>";
		var butarray = new Array();
		butarray[0] = "ok|sendsms('"+box.dialogId+"');|发送短信";
		butarray[1] = "ok|sendEmail('"+box.dialogId+"');|发送邮件";
		butarray[2] = "cancel";
		box.buttons = butarray;
		box.show();
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
							SysGrid nw =new SysGrid(request);
						    nw.setTableTitle("在线人员");
						    nw.setIsautoQuery(true);
						    nw.setDefaultShow(SysGrid.DEFAULT_SHOWIMAGE);
						    nw.setCheckboxOrNum(false);
						    nw.setQueryFunction("queryData");	//查询的方法名
						    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
						    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
								    
							ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"在线人员"));
							CheckBoxType chb = new CheckBoxType("1","只显示在线人员");
							chb.setCustomerFunction(new String[]{"onclick=\"queryData();\""});
							colList.add(ColumnUtil.getCusterAdvancedColumn("onlinechk","",chb));
						    //进行高级查询显示处理
							for(int i=0;i<colList.size();i++){
							SysGridColumnBean bc =colList.get(i);	
								if("hrmEmployeeSex".equalsIgnoreCase(bc.getDataName())){
							//设置高级查询显示样式
							SelectType select  = new SelectType(EnumUtil.HRM_EMPLOYEE_SEX.getSelectAndText("-1,-请选择人员性别-"));
							select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
							bc.setColumnTypeClass(select);
								}
								if("employeeSex".equalsIgnoreCase(bc.getDataName())){
							//设置列显示样式
							bc.setColumnReplace("repleaSex");
							bc.setColumnStyle("text-align:center;");
								}
								if("isOnLine".equalsIgnoreCase(bc.getDataName())){
							//设置高级查询显示样式
							bc.setShowName("是否在线");
							bc.setColumnReplace("replaceLine");
							
							//设置列显示样式
							bc.setColumnStyle("text-align:center;margin: 0;padding: 0;");
								}
						   }
						   
						   
							//设置列操作对象
							nw.setShowProcess(true);//默认为false 为true请设置processMethodName
							nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象    
						    nw.setColumnList(colList);
						    nw.setImgShowUrl("imageId");//显示img的属性字段，没有填写-1
						    nw.setImgShowMethod(true);
							nw.setImgShowText("repimgtxt");
							nw.setImgNoDefaultPath(absPath+"/images/noimages/employee.png");//可以不指定，系统采用默认暂无图片
							nw.setImgheight("64");//不设置为自动
						    nw.setImgShowTextLen(0);
						    //开始创建
						    out.print(nw.createTable());
						%>
</td>
</tr>
</table>
		
		<input type="hidden" id="upcode">
</body>
</html>
