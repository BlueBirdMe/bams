<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
int pwdlen = Integer.parseInt(UtilTool.getSysParamByIndex(request,"erp.user.PwdLen"));
SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
 %>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">用户密码修改</div>
	
	<div>
		<table class="inputtable">
			<tr>
			<th>用户名</th>
			<td width="50%">
			<input id=username type="text" readonly="readonly" style="background-color: transparent;border-width: 0px;border-bottom: 1px solid #99999" value="<%=user.getUserName() %>"/>
			</td>
			</tr>
			<tr>
			<th style=" color: #808080">提示：</th>
			<td>
			<label style=" color: #808080">密码有大小写之分,长度为<font color="red"><%=pwdlen %>~18</font>位。</label>
			</td>
			<td></td>
			</tr>
			<tr>
			<th><em>*</em>旧密码</th>
			<td>
			<input type="password" class="niceform" id="userpwd" maxlength="18" style="ime-mode:disabled;" must="旧密码不能为空" formust="userpwdMust">
			</td>
			<td width="40%"><label id="userpwdMust"></label></td>
			</tr>
			
			<tr>
			<th><em>*</em>新密码</th>
			<td>
			<input type="password" class="niceform" id="newuserpwd" maxlength="18" style="ime-mode:disabled;" must="新密码不能为空" formust="newuserpwdMust">
			</td>
			<td width="40%"><label id="newuserpwdMust"></label></td>
			</tr>
			<tr>
			<th><em>*</em>新密码确认</th>
			<td>
			<input type="password" id="newtwouserpwd" maxlength="18" style="ime-mode:disabled;" must="确认密码不能为空" formust="newtwouserpwdMust">
			</td>
			<td width="40%"><label id="newtwouserpwdMust"></label></td>
			</tr>
			<tr>
			<th><em>*</em>验证码</th>
			<td>
			<input type="text" id="code" name="code" style="width: 65px;ime-mode:disabled;text-align:center; font-size:14px;font-variant: small-caps" class="niceform" maxlength="4"  must="请输入验证码" formust="codeMust"/>
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
	initInput("helpTitle","用户密码修改，您可以在此处您的用户密码。");
}

function updatepassword(){
		var warnArr = new Array();
			warnArr[0] ="userpwdMust";
			warnArr[1] = "newuserpwdMust";
			warnArr[2] = "newtwouserpwdMust";
			warnArr[3] = "codeMust";
		warnInit(warnArr);
	
	var bl = validvalue('helpTitle');
	if(bl){
		var oldpwd = document.getElementById("userpwd").value;
		dwrSysProcessService.vaildSysUserInfoOldPwd(oldpwd,function(data){
			if(!data){
				setMustWarn("userpwdMust","旧密码输入错误。");
			}else{
				var newval = document.getElementById("newuserpwd").value;
				var newtwoval = document.getElementById("newtwouserpwd").value;
				if(newval != newtwoval){
					setMustWarn("newtwouserpwdMust","新密码及确认密码输入不一致。");
				}else if(trim(newval).length<<%=pwdlen%>){
					setMustWarn("newuserpwdMust","密码长度不能小于<%=pwdlen%>位。");
				}else{
					dwrCommonService.validCodeEquals(document.getElementById("code").value,function(data){
					if(!data){
						setMustWarn("codeMust","验证码输入错误。");
						
					}else{
						var sysuser = new Object();
						
						dwrSysProcessService.updateSysUserPassword(oldpwd,newval,updataback);
					}
				});
				}				
			}
		});
	}
}
function updataback(data){
	if(data.success){
		parent.updatepasswordback(data);
	}else{
		document.getElementById("helpTitle").innerHTML ="密码修改失败!";
	}
}
</script>
</body>
</html>