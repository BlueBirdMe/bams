<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
 %>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">用户名修改</div>

	
	<div>
		<table class="inputtable">
			<tr>
			<th>原用户名</th>
			<td width="50%">
			<input id=oldusername type="text" readonly="readonly" style="background-color: transparent;border-width: 0px;border-bottom: 1px solid #99999" value="<%=user.getUserName() %>"/>
			</td>
			</tr>
			<tr>
			<th><em>*</em>新用户名</th>
			<td>
			<input type="text"  id="newusername" maxlength="50" style="ime-mode:disabled;" must="新的用户名不能为空" formust="onewusernameMust">
			</td>
			<td width="40%"><label id="onewusernameMust"></label></td>
			</tr>
			<tr>
			<th><em>*</em>密码确认</th>
			<td>
			<input type="password" class="niceform" id="userpwd" maxlength="18" style="ime-mode:disabled;" must="密码不能为空" formust="userpwdMust" >
			</td>
			<td width="40%"><label id="userpwdMust"></label></td>
			</tr>
			<tr>
			<th><em>*</em>验证码</th>
			<td>
			<input type="text" id="code" name="code" style="width: 65px;ime-mode:disabled;text-align:center; font-size:14px;font-variant: small-caps" class="niceform" maxlength="4" must="验证码不能为空" formust="codeMust"/>
			&nbsp;&nbsp;<img border=0  src="<%=request.getContextPath() %>/validcode.do" onclick="changecode(this)"  title="点击切换验证码" style="vertical-align: text-bottom;cursor: pointer;" alt="点击重新加载">
			</td>
			<td width="40%"><label id="codeMust"></label></td>
			</tr>
		</table>
	</div>
</div>
<br/>
<script type="text/javascript">
window.onload = function(){
	initInput("helpTitle","修改用户，您可以在此处修改您的登录名。");
}

function updateName(){
 		var warnArr = new Array();
		warnArr[0] ="onewusernameMust";
		warnArr[1] ="userpwdMust";
		warnArr[2] ="codeMust";
		warnInit(warnArr);
		
	
	var bl = validvalue('helpTitle');
	
	if(bl){
		var oldpwd = document.getElementById("userpwd").value;
		dwrSysProcessService.vaildSysUserInfoOldPwd(oldpwd,function(data){
			if(!data){
				setMustWarn("userpwdMust","密码输入错误!");
			}else{
				dwrCommonService.validCodeEquals(document.getElementById("code").value,function(data){
					if(!data){
						setMustWarn("codeMust","验证码输入错误!");
					}else{
						var name = document.getElementById("newusername").value;
						dwrSysProcessService.vaildSysUserInfoByUserNameEdit(name,function(data){
							if(data>0){
							 setMustWarn("onewusernameMust","用户名已存在!");
								
							}else{
								var sysuser = new Object();
								dwrSysProcessService.updateSysUserName(oldpwd,name,updataback);
							}
						});
					}
				});		
			}
		});
	}
}
function updataback(data){
	if(data.success){
		parent.updatenamedback(data);
	}else{
		document.getElementById("helpTitle").innerHTML ="用户名修改失败!";
	}
}
</script>
</body>
</html>