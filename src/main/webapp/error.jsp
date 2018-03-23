<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.core.pojo.SysMethodInfo"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.core.iservice.ISysProcessService"%>
<%@page import="com.pinhuba.common.module.SessionUser"%>
<%@page import="com.pinhuba.common.util.LoginContext"%>
<%@page import="com.pinhuba.core.pojo.SysException"%>
<%@page import="com.pinhuba.common.util.UtilWork"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%
if(exception != null){
WebApplicationContext webAppContext = 
		WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
ISysProcessService sysProcessService = 
		(ISysProcessService)webAppContext.getBean("sysProcessService");

SessionUser sessionUser =(SessionUser)LoginContext.getSessionValueByLogin(request);
SysException sException =new SysException();
int userId =-1;
int companyId= -1;
if(sessionUser != null){
	userId = Integer.parseInt(sessionUser.getUserInfo().getPrimaryKey()+"");
	companyId = Integer.parseInt(sessionUser.getCompanyId()+"");
}
sException.setUserId(userId);
sException.setCompanyId(companyId);
sException.setExceptionClass(exception.getClass().getName());
sException.setExceptionDate(UtilWork.getNowTime());
sException.setExceptionMsg(exception.getMessage());
StringBuffer sb =new StringBuffer(); 
for(int i=0;i<exception.getStackTrace().length;i++){
	sb.append(exception.getStackTrace()[i]);
}
sException.setExceptionContext(sb.toString());
sException.setExceptionStatus(EnumUtil.SYS_EXCEPTION_STATUS.Vaild.value);
sysProcessService.saveSysException(sException);
}
String path = UtilTool.getProjectPath(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误页面</title>
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/css/normal.css' />
</head>
<body>
	<div style="width:600px;margin:0 auto;border:1px solid #d1d1d1;margin-top:150px;padding:20px;">
		<table cellSpacing="5" width="90%" align="center" border="0" cepadding="0" style="line-height: 25px;">
			<tr>
				<td valign="top" align="middle"><img border="0" id="errimg" src="<%=request.getContextPath()+"/images/404.png" %>">
				<td>
				<td>
					<h1 style="color: red;font-size:20px;">操作错误</h1> 
					<label style="color: #001150"> 
					HTTP 错误 404/500：<br />
					您正在搜索的页面可能暂时不可用,也可能您的访问权限不够, <br /> 
					或者您在系统的认证已经过期，无法继续使用系统。 
					</label>
					<hr style="color:#c1c1c1;">
					<p>
						☉ <b>请尝试以下操作：</b>
					</p>
					<ul>
						<li>确保登陆并且有访问该页面的权限成功。</li>
						<li>确保操作条件或内容的拼写和格式正确无误。</li>
						<li>如果操作出现未知错误，请与网站管理员联系。</li>
						<li>建议你尝试： 
						<a href="javascript:void(0);" onclick="window.history.go(-1);"> 
						<font color="green">返回</font> 
						</a> 
						&nbsp;&nbsp;或&nbsp;&nbsp; 
						<a href="javascript:void(0);" onclick="forw();"> 
						<font color="green">重新登录</font> 
						</a>
						</li>
					</ul>
					<hr style="color:#c1c1c1;">
					<p>
						☉ 如果您对系统有任何疑问、建议，请联系管理员 : <a href="mailto:service@pinhuba.com"> <font color="#336699">service@pinhuba.com</font> </a>
					</p></td>
			</tr>
		</table>
	</div>

	<script type="text/javascript">
    function forw(){
		window.top.location.href="<%=path%>";
    }
	</script>
</body>
</html>