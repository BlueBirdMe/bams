<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>添加用户</title>
<%
//从配置文件加载初始密码及密码长度限制
String initpwd = UtilTool.getSysParamByIndex(request,"erp.user.initPwd");
int pwdlen = Integer.parseInt(UtilTool.getSysParamByIndex(request,"erp.user.PwdLen"));
 %>
 
 <script type="text/javascript">

window.onload = function(){
	initInput('title',"欢迎使用添加用户");
}

function createusername(){
	var empid =document.getElementById("employeeid").value;
	if(empid.length==0){
		initInput('title');
		document.getElementById("empchk").checked = false;
		return;
	}
	//检测人员是否已存在
	dwrSysProcessService.vaildSysUserInfoByEmpId(empid,validback);
}

function validusername(obj){
	var val = obj.value;
	if(val.length==0){
		return;
	}
	dwrSysProcessService.vaildSysUserInfoByUserName(val,validuserback);
}

function validuserback(data){
	if(data>0){
	  setMustWarn("employeenamemust", "该人员已注册为用户!");
		document.getElementById("userchk").checked = true;
	}else{
		initInput('title');
		document.getElementById("userchk").checked = false;
	}
}

function validback(data){
	if(data>0){
	    setMustWarn("employeenamemust", "该人员已注册为用户!");
		document.getElementById("empchk").checked = true;
	}else{
		document.getElementById("empchk").checked = false;
		createusernameback();
	}
}

function createusernameback(){
	var emp = document.getElementById("employeename").value;
	if(emp.length==0){
		return;
	}
	if(document.getElementById("forempname").checked){
		dwrCommonService.getPinYinByString(emp,setvalue);
	}	
}

function setvalue(data){
	document.getElementById("username").value = data;
}
function getupcode(){
	var box = SEL.getEmployeeIds("radio","employeename","employeeid","userfrm","createusername();");
	box.show();
}

function saveuser(){
	var warnArr = new Array();
	warnArr[0]="usernamemust";
	warnInit(warnArr); //验证常用组件
	var bl = validvalue('title');
	if(bl){
	validback();
	validuserback();
		if(document.getElementById("empchk").checked){
			return;
		}
		if(document.getElementById("userchk").checked){
			return;
		}
			
		var user = getuser();
		
		if(trim(user.userpassword).length<<%=pwdlen%>){
			setMustWarn("userpwdmust", "密码长度不能小于 <%=pwdlen%> 位");
			return;
		}
			
		dwrSysProcessService.saveSysUser(user,methodcallback);
	}
}

function backToNewsList() {
	Sys.href('<%=contextPath%>/erp/system_set/userset.jsp');
}

function methodcallback(data){
	alertmsg(data,"sevent()");
}

function sevent(){
	DWRUtil.setValue("employeename","");
	DWRUtil.setValue("employeeid","");
	DWRUtil.setValue("username","");
	DWRUtil.setValue("userpwd","");
}

function getuser(){
	var user = new Object();
	user.userName = DWRUtil.getValue("username");
	user.userpassword = DWRUtil.getValue("userpwd");
	user.hrmEmployeeId = DWRUtil.getValue("employeeid"); 
	user.userType = <%=EnumUtil.SYS_USER_TYPE.DEFAULT.value%>;
	user.userAction = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
	return user;
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">添加用户</div>
	<div>
	<table class="inputtable">
	<tr>
	<th><em>*&nbsp;</em>人员名称</th>
	<td><input type="text" class="takeform" id="employeename" readonly="readonly"  title="点击选择人员名称" onclick="getupcode();" linkclear ="employeeid" formust="employeenamemust" must="必须选择人员信息">
	<input type="hidden" id="employeeid"><input type="checkbox" id="empchk" class="nonice" style="visibility: hidden">
	</td>
	<td align="left">
	<label  id="employeenamemust" ></label>
	</td>
	</tr>
	<tr>
	<th></th>
	<td>
	<input type="checkbox" id="forempname" checked="checked" onclick="createusername()"><label for="forempname">依据人员名称生成用户名</label>
	</td>
	</tr>
	<tr>
	<th><em>*&nbsp;</em>用户名</th>
	<td>
	<input type="text" id="username" maxlength="50" style="ime-mode:disabled;" formust="usernamemust" must="请输入用户名" onchange="validusername(this);">
	<input type="checkbox" id="userchk" class="nonice" style="visibility: hidden">
	</td>
	<td><label id="usernamemust"></label> </td>
	</tr>
	<tr>
	<th><em>*&nbsp;</em>初始密码</th>
	<td>
	<input type="text" id="userpwd" maxlength="18" style="ime-mode:disabled;" must="请输入密码" formust="userpwdmust" value="<%=initpwd %>">
	</td>
	<td><label  id="userpwdmust"></label></td>
	</tr>
	</table>
	<br>
	</div>
</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="saveuser();" value="保 存 " imgsrc="../../images/png-1718.png" title="添加用户" />
</td>
</tr>
</table>
</body>
</html>