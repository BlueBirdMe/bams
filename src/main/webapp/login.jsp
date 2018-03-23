<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.core.pojo.SysMsg"%>
<%@page import="com.pinhuba.common.util.UtilWork"%>
<%@page import="com.pinhuba.core.pojo.SysConfig"%>
<%
String path = request.getContextPath();
boolean bl =Boolean.parseBoolean(SystemConfig.getParam("erp.sys.LoginIsview"));//是否显示注册
boolean blCode =Boolean.parseBoolean(SystemConfig.getParam("erp.sys.CodeIsview"));//是否显示验证码
String msg = request.getAttribute(ConstWords.TempStringMsg)==null?"":(String)request.getAttribute(ConstWords.TempStringMsg);
String ptname = "吉林电信人才管理平台";
WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)context.getBean("dwrSysProcessService");
SysConfig indexconfing = service.getSysconfigByCode(ConstWords.getProjectCode());
if(indexconfing != null){
	ptname = indexconfing.getProjectName();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=ptname %></title>
<link rel='Shortcut Icon' href='<%=path %>/favicon.ico' />
<link rel='Bookmark' href='<%=path %>/favicon.ico' />
<link rel='stylesheet' type='text/css' href='<%=path %>/css/login.css' />
<script type='text/javascript' src="<%=path %>/js/cookie.js"></script>
<script type='text/javascript' src='<%=path %>/js/login.js'></script>
<script type='text/javascript' src='<%=path %>/js/jquery-1.7.2.min.js'></script>
<script type='text/javascript' src='<%=path %>/js/jquery.hotkeys.js'></script>
<script type="text/javascript">
function addFavorite(url,title){
    var fav_url = url;
	var fav_title = title;
	if (document.all && window.external){
	  window.external.AddFavorite(fav_url,fav_title);
	}else if (window.sidebar){
	  window.sidebar.addPanel(fav_title,fav_url,"");
	}else{
	  alert("浏览器不支持，请手动加入收藏夹");
	}
}
</script>
</head>

<body style="height: 100%;background-image:url(<%=path %>/images/login_bg.png);background-repeat: repeat-x;background-color: #7eb9e9">
<form method="post" name="erpfrm" action="<%=path %>/login.do">
<table border="0" cellpadding="0" cellspacing="0" align="center"  width="923">
<tr height="88">
	<td width="527" background="images/1.png"></td>
	<td width="320" background="images/2.png"></td>
	<td width="76" background="images/3.png"></td>
</tr>
<tr height="73">
	<td width="527" background="images/4.png" valign="top" nowrap="nowrap">
		<div style="font-size:28px;text-indent:120px;color:#247bce;font-weight:bold;margin-top:8px"><%=ptname%></div>
	</td>
	<td width="320" valign="top" background="images/6.png" rowspan="2" style="background-color: #ffffff;background-repeat: no-repeat;background-position: right bottom;">
		<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" height="30">
		<tr>
		<td align="right">
		<a href="javascript:void(0);" onclick="this.style.behavior='url(#default#homepage)';this.setHomePage(window.location);" class="tit" title="设置<%=ptname %>为浏览器首页">设为首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0);" onclick="addFavorite(window.location, '<%=ptname %>');"  class="tit" title="将<%=ptname %>加入收藏夹">加入收藏</a>
		</td>
		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" height="30">
		<tr>
			<td valign="bottom"><div id="msgbox" style="color: red;padding-left: 15px;font-size: 13px;"></div></td>
		</tr>
		</table>
		
		<table border="0" cellpadding="0" cellspacing="0" width="100%" height="240" style="padding-left: 18px">
		<tr>
		<td style="color:#167FC8" width="40">公司码</td>
		<td style="padding-left:10px;" nowrap>
		<input type="text" class="niceform" id="companycode" name="companycode" style="ime-mode:disabled; font-size:14px;font-variant: small-caps" value="CHINANET"/>&nbsp;&nbsp;<a href="#" title="什么是公司编码"><img src="images/whycompany.gif" border="0"/></a></td>
		</tr>
		<tr>
		<td style="color:#167FC8">用户名</td>
		<td style="padding-left:10px;" nowrap><input type="text" value="dxj" class="niceform" id="username" name="username" style="ime-mode:disabled;background:url('<%=request.getContextPath() %>/images/userimg.gif') no-repeat 1px;padding-left:20px; font-size:12px;width:155px;"/></td>
		</tr>
		<tr>
		<td style="color:#167FC8">密码</td>
		<td style="padding-left:10px;position:relative;" nowrap>
			<div id="tipsdiv" style="padding:4px;background-color:#fefefe;border:1px solid #aa540b;position:absolute;z-index:1;display:none;left:200px;">
				<table border='0' cellpadding='3' cellspacing='0'>
					<tr>
						<td rowspan='2'>
						<img src ="<%=path %>/images/tipsimg.png" border='0'/>
						</td>
						<td style ='padding-left:3px;'  nowrap='nowrap'>大写锁定键<font color='#003366'>[CAPS LOCK]</font>被按下</td>
					</tr>
					<tr><td style ='padding-left:3px;'nowrap='nowrap'>请注意密码大小写</td></tr>
				</table>
			</div>
			
			<input type="password" value="111111" class="niceform" id="userpwd" name="userpwd" onkeypress="detectCapsLock();" onblur="hiddenTipsDiv();"  style="background:url('<%=request.getContextPath() %>/images/userpwdimg.gif') no-repeat 1px;padding-left:20px;font-size:12px;width:155px;"/>
		</td>
		</tr>
		
		<%if(blCode==true){ %>
		<tr>
		<td style="color:#167FC8;">验证码</td>
		<td style="padding-left:10px;" nowrap>
		<input type="text" id="code" name="code" class="niceform" maxlength="4" style="width: 90px;ime-mode:disabled;text-align:center; font-size:14px;font-variant: small-caps"/>
		&nbsp;&nbsp;<img border=0  src="<%=request.getContextPath() %>/images/codeload.gif" onclick="changecode(this)"  title="点击切换验证码" style="vertical-align:bottom;cursor: pointer;" alt="点击重新加载" id="codeimg">
		</td>
		</tr>
		<%} %>
		
		<tr>
		<td></td>
		<td style="padding-left:10px;padding-top: 5px;">
		<input type="checkbox"  id="cmcode" name="cmcode" style="vertical-align: middle;"/><label for="cmcode" style="color:#666666; padding-right:10px;">记住公司码</label>
		<input type="checkbox"  id="usname" name="usname" style="vertical-align: middle;"/><label for="usname" style="color:#666666; padding-right:10px;">记住用户名</label>
		</td>
		</tr>
		<tr>
		<td></td>
		<td style="padding-top:10px; padding-left:10px;" valign="top">
		<div id="loginbtndiv">
		<input type="button" class="btn" value=" 登  录 " onclick="loginCheck('<%=path %>');"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<%if(bl==true){ %>
		<input type="button" class="btn_reg" value=" 马上注册 " onclick="register()"/>
		<%}else{ %>
		<input type="button" class="btn_reg" value="清  空" onclick="clearinput()"/>
		<%} %>
		</div>
		<div id="loginloadingdiv" style="display: none;color: #006699;font-size: 13px;height: 36px">
			<img src ="<%=path %>/images/loginloading.gif" border="0" style="vertical-align: middle;">&nbsp;&nbsp;&nbsp;&nbsp;系统登录中,请稍候...
		</div>
		</td>
		</tr>
		</table>
	</td>
	<td width="76" background="images/5.png"></td>
</tr>
<tr height="315">
	<td width="527" background="images/7.png"></td>
	<td width="76" background="images/8.png"></td>
</tr>
<tr height="51">
	<td width="527" background="images/9.png"></td>
	<td width="320" background="images/10.png"></td>
	<td width="76" background="images/11.png"></td>
</tr>
</table>

</form>
<center>
<table>
<tr>
<td style="color:#ffffff;font-size: 12px;" align="center">版权所有: 企业信息化部 ChinaNet (c)2018-2099</td>
</tr>
<tr>
<td style="color:#ffffff;font-size: 12px;" align="center">网址：<a href="http://www.189.cn/jl/">http://www.189.cn/jl/</a></td>
</tr>
</table>
</center>
<script type="text/javascript">
if('<%=msg%>'!=''){
	document.getElementById("loginbtndiv").style.display="block";
	document.getElementById("loginloadingdiv").style.display="none";
	document.getElementById("msgbox").innerHTML = '<%=msg%>';
}
function changecode(obj){
	if(obj != null)
		obj.src = "<%=path %>/validcode.do?t="+Math.random();
}

function register(){
	window.open('<%=path%>/other/company_reg.jsp');
}
</script>
</body>
</html>