<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.core.pojo.SysMethodInfo"%>
<%@page import="com.pinhuba.common.util.EnumUtil"%>
<%@page import="com.pinhuba.common.util.file.FileTool"%>
<%@page import="com.pinhuba.core.pojo.SysConfig"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
boolean bl =Boolean.parseBoolean(SystemConfig.getParam("erp.sys.LoginIsview"));
if(!bl){
	return;
}
String codemsg=request.getAttribute(ConstWords.TempStringMsg)==null?"":(String)request.getAttribute(ConstWords.TempStringMsg);
String path = request.getContextPath();
List<SysMethodInfo> methodsList = (List<SysMethodInfo>)application.getAttribute(ConstWords.ServletContext_Method);
WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)context.getBean("dwrSysProcessService");
String ptname = "企业管理平台";
SysConfig indexconfing = service.getSysconfigByCode(ConstWords.getProjectCode());
if(indexconfing!=null){
	ptname = indexconfing.getProjectName();
}
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=ptname %>-公司注册</title>
<link rel='Shortcut Icon' href='<%=path %>/favicon.ico' />
<link rel='Bookmark' href='<%=path %>/favicon.ico' />
<link rel='stylesheet' type='text/css' href='<%=path %>/css/normal.css' />
<script  type='text/javascript'   src='<%=path %>/js/normalutil.js'></script>
<link rel='stylesheet' type='text/css' href='<%=path %>/css/login.css' />
<link type='text/css' rel='stylesheet' href='<%=path %>/css/formstyle.css' />
<script  type='text/javascript'   src='<%=path %>/js/formjs.js'></script>
<script type='text/javascript'>formStylePath.setImagePath('<%=path %>/images/');</script>
<script  type='text/javascript'   src='<%=path %>/js/syspanel.js'></script>
<script type='text/javascript'>panelStylePath.setImagePath('<%=path%>/images/grid_images/');</script>
<script  type='text/javascript'   src='<%=path %>/js/pcasunzip.js'></script>
<script  type='text/javascript'   src='<%=path %>/dwr/engine.js'></script>
<script  type='text/javascript'   src='<%=path %>/dwr/util.js'></script>
<script  type='text/javascript'   src='<%=path %>/dwr/interface/dwrCommonService.js'></script>
<script type="text/javascript" src="<%=path %>/dwr/interface/dwrSysProcessService.js"></script>
<style type="text/css">
	th,td{
		font-size: 13px;
	}
	.regbtn{
		width:142px;
		height:32px;
		border:0px;
		margin:0px;
		cursor:pointer;
		background: url("<%=path%>/images/regimages/reg_finish_btn.gif") no-repeat;
		color: #4d2f00;
		line-height: 32px;
		font-size: 14px;
		font-weight: bold;
		font-family: 宋体;
	}
	.regbtn_hover{
		width:142px;
		height:32px;
		border:0px;
		margin:0px;
		cursor:pointer;
		background: url("<%=path%>/images/regimages/reg_finish_btn_.gif") no-repeat;
		color: #4d2f00;
		line-height: 32px;
		font-size: 14px;
		font-weight: bold;
		font-family: 宋体;
	}
</style>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="966" align="center" height="70">
<tr height="70">
<td width="110">
<img src="<%=path %>/images/login_title.png" border="0" height="45"/>
</td>
<td align="left" ><font face="幼圆" style="font-size:22px;"  color="#666666" ><strong><%=ptname %></strong></font></td>
<td align="right" style="color:#999999;padding-right: 15px;">
</td>
</tr>
</table>
<form method="post" name="companyfrm" action="<%=path %>/companyreg.do">
<input type="hidden" name="methods" id="methods">
<table border="0" cellpadding="0" cellspacing="0" width="966" align="center">
<tr height="57">
<td width="9" background="<%=path %>/images/regimages/reg_title1.jpg"></td>
<td width="944" background="<%=path %>/images/regimages/reg_title2.jpg"  style="line-height: 57px;text-indent: 45px;">
<div style="font-size: 16px;font-family: Microsoft YaHei, 宋体, Segoe UI, verdana, arial;color: #fefefe">欢迎注册品互网络基础应用管理平台！请填写相关申请信息或者访问<a href="http://www.pinhuba.com" target="_blank" class="regtit">官方网站</a>获取更多信息。</div>
</td>
<td width="13" background="<%=path %>/images/regimages/reg_title3.jpg"></td>
</tr>
<tr>
<td width="9" background="<%=path %>/images/regimages/reg_left.jpg"></td>
<td width="944">
		<div id="regtddiv">
		<div style="margin: 5px;text-align: right;color: #666666;font-family: 宋体">注意：带有&nbsp;<font color="red">*</font>&nbsp;号的必须填写</div>
		<fieldset>
		<legend style="font-family: '宋体';color:#007BBB ">公司信息(*号位必填项)</legend>
		<div>
		<table border="0" style="line-height: 32px;" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>公司名称</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyname" name="companyname" value="<%=request.getParameter("companyname")==null?"":request.getParameter("companyname") %>"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">长度不能小于4个字符。<label style="padding-left: 5px;color: red" id="companyname_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>公司简称</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyjcname" name="companyjcname" value="<%=request.getParameter("companyjcname")==null?"":request.getParameter("companyjcname") %>"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">长度不能小于4个字符。<label style="padding-left: 5px;color: red" id="companyjcname_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>所在省市</td>
		<td nowrap="nowrap" colspan="2" style="padding-left: 10px;">
		<div class="selectdiv"  style="float: left;margin-right: 5px;"><SELECT name="province" id="province"></SELECT></div>
        <div class="selectdiv"  style="float: left;margin-right: 5px;"><SELECT name="city" id="city"></SELECT></div>
        <div class="selectdiv"  style="float: left"><SELECT name="area" id="area"></SELECT></div>
		</td>
		</tr>
		<tr>
		<td colspan="2">
		<table border="0" style="width: 100%" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>详细地址</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 312px;" id="companyadder" name="companyadder" value="<%=request.getParameter("companyadder")==null?"":request.getParameter("companyadder") %>">	</td>
		<td style="width: 50px;padding-right: 10px;" align="right">邮编</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 50px;" id="companypost" class="numform" maxlength="6" name="companypost" value="<%=request.getParameter("companypost")==null?"":request.getParameter("companypost") %>"/></td>
		</tr>
		</table>
		</td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">用于邮寄公司注册后反馈信息。<label  style="padding-left: 5px;color: red"  id="companyadder_tit"></label> </td>
		</tr>
		<tr>
		<td colspan="2">
		<table border="0" style="width: 100%" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>联系人</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 180px;" id="companyperson" name="companyperson"  value="<%=request.getParameter("companyperson")==null?"":request.getParameter("companyperson") %>"></td>
		<td style="width: 100px;padding-right: 10px;" align="right">职务</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 160px" id="companyjob" name="companyjob" value="<%=request.getParameter("companyjob")==null?"":request.getParameter("companyjob") %>"/></td>
		</tr>
		</table>
		</td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;"><label style="padding-left: 5px;color: red"  id="companyperson_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>联系电话</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyphone" name="companyphone" value="<%=request.getParameter("companyphone")==null?"":request.getParameter("companyphone") %>"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">多个号码以','号隔开。<label style="padding-left: 5px;color: red"  id="companyphone_tit"></label> </td>
		</tr>
		<tr>
		<td colspan="2">
		<table border="0" style="width: 100%" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>传真</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 180px;" id="companyfax" name="companyfax" value="<%=request.getParameter("companyfax")==null?"":request.getParameter("companyfax") %>"></td>
		<td style="width: 100px;padding-right: 10px;" align="right">E-Mail</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 160px" id="companymail" name="companymail" value="<%=request.getParameter("companymail")==null?"":request.getParameter("companymail") %>"/></td>
		</tr>
		</table>
		</td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">用于传真公司注册后反馈信息。<label style="padding-left: 5px;color: red"  id="companyfax_tit"></label> </td>
		</tr>
		</table>
		</div>
		</fieldset>
		<br/>
		<fieldset>
		<legend  style="font-family: '宋体';color:#D0410A ">系统信息</legend>
		<div style="overflow: hidden;">
		<table border="0" cellpadding="0" cellspacing="0" height="40" style="line-height: 32px;">
		<%
		String msign ="";
		String mid="";
		String msg ="";
		String imgpath ="";
		String tit ="";
		String name ="";
		String color="";
		if(methodsList!=null && methodsList.size()>0){
			for(int i=0;i<methodsList.size();i++){
				SysMethodInfo sysMethod =methodsList.get(i);
				name = sysMethod.getMethodInfoName();
				msg =sysMethod.getMethodMsg();
				if(sysMethod.getIsDefault()!=null&&sysMethod.getIsDefault() == ConstWords.CurrentProject){
					msign="checked='checked'";
				}else{
					msign ="";
				}
				if(sysMethod.getIsAction() == EnumUtil.SYS_ISACTION.Vaild.value){
					imgpath = sysMethod.getImageSrc();
					tit = sysMethod.getMethodInfoName();
					if(sysMethod.getIsDefault()!=null&&sysMethod.getIsDefault() == ConstWords.CurrentProject){
						mid="disabled='disabled'";
						color="color:#19427D";
					}else{
						mid="";
						color ="";
					}
				}else{
					String tempstr = sysMethod.getImageSrc();
					imgpath = FileTool.getRepFileName(tempstr,"_black");
					tit = "未启用项目："+sysMethod.getMethodInfoName();
					mid="disabled='disabled'";
					color="color:#666666";
				}
		 %>
		 <tr>
		 <td style="width: 150px;padding-right: 10px;" align="right"></td>
		<td style="padding-left: 10px;padding-right: 10px;" align="left">
		<div style="border:1px solid #cccccc; width:32px; height:32px; margin:3px;overflow: hidden;">
		<a href="javascript:void(0);" title="<%=tit %>"><img src="<%=request.getContextPath()+"/images/projectimg/"+imgpath %>" border="0" id="<%=sysMethod.getMethodSign()%>" value="<%=sysMethod.getMethodSign() %>"/></a>
		</div>
		</td>
		<td style="padding-left: 10px;width: 150px">
		<input type="checkbox" name="methodchk" value="<%=sysMethod.getPrimaryKey() %>" id="<%=sysMethod.getMethodSign() %>_chk" <%=msign+" "+mid %> title="<%=tit %>"><label for="<%=sysMethod.getMethodSign() %>_chk" title="<%=tit %>" style="<%=color %>"><%=name %></label>
		</td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;"><%=msg %></td>
		</tr>
		<%}} %>
		</table>
		</div>
		</fieldset>
		<br/>
		
		<fieldset>
		<legend style="font-family: '宋体';color:#007BBB ">验证信息及条款</legend>
		<div>
		<table border="0" style="line-height: 32px;" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"></td>
		<td style="padding-left: 10px;" colspan="2">
		<img border=0  src="<%=request.getContextPath() %>/validcode.do?sid=companyregcode"  title="点击切换验证码" style="vertical-align: text-bottom;cursor: pointer;" alt="点击重新加载" id="codeimg">
		&nbsp;&nbsp;<a href="javascript:void(0)" onclick="changecode2()" style="color: green">看不清楚，更换一张</a>
		<input type="hidden" name="validcodeName" value="companyregcode">
		</td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>验证码</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 265px;font-size:14px;ime-mode:disabled;font-variant: small-caps" id="validcode" maxlength="4" name="validcode"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">请输入上面的字符。<label style="padding-left: 5px;color: red"  id="validcode_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"></td>
		<td style="padding-left: 10px;" colspan="2">
		<input type="checkbox" id="tiaokuan" checked="checked" onclick="changesubbtn(this)"><label for="tiaokuan">我已阅读并接受“<a href="#" style="color: #1457B5">服务条款</a>”</label>
		</td>
		</table>
		<div style="height: 80px;text-align: center;vertical-align: middle;">
		<input id="subbtn"  type="button" value="完成注册" onclick="subcompanyreg()" style="position: relative;top: 30%" class="regbtn" onmouseover="this.className='regbtn_hover'" onmouseout="this.className='regbtn'">
		</div>
		</div>
		</fieldset>
		<br/>
		</div>
</td>
<td width="13" background="<%=path %>/images/regimages/reg_right.jpg"></td>
</tr>
<tr height="16">
<td width="9" background="<%=path %>/images/regimages/reg_bottom1.jpg"></td>
<td width="944" background="<%=path %>/images/regimages/reg_bottom2.jpg"></td>
<td width="13" background="<%=path %>/images/regimages/reg_bottom3.jpg"></td>
</tr>
</table>
</form>
<%@ include file="../Copyright.jsp" %>
<br/>
<script type="text/javascript">
 new PCAS("province", "city", "area");
 function changecode2(){
 	var obj = document.getElementById("codeimg");
 	obj.src = "<%=path%>/validcode.do?sid=companyregcode&t="+Math.random();
 }
 
 function changesubbtn(obj){
 	var sbtn = document.getElementById("subbtn");
 	if(obj.checked){
 		sbtn.disabled=false;
 	}else{
 		sbtn.disabled = true;
 	}
 }

 function subcompanyreg(){
 	var cname = document.getElementById("companyname");
 	var cjcname = document.getElementById("companyjcname");
 	var cadder =document.getElementById("companyadder");
 	var cperson =document.getElementById("companyperson");
 	var cphone = document.getElementById("companyphone");
 	var cfax = document.getElementById("companyfax");
 	var ccode = document.getElementById("validcode");
 	if(cname.value.length==0){
 		document.getElementById("companyname_tit").innerHTML="请输入公司名称";
 		cname.focus();
		return;
 	}else if(cname.value.length<4){
 		document.getElementById("companyname_tit").innerHTML="公司名称不能少于4个字符";
 		cname.focus();
		return;
 	}else{
 		document.getElementById("companyname_tit").innerHTML="";
 	}
 	if(cjcname.value.length==0){
 		document.getElementById("companyjcname_tit").innerHTML="请输入公司简称";
 		cjcname.focus();
		return;
 	}else if(cjcname.value.length<4){
 		document.getElementById("companyjcname_tit").innerHTML="公司简称不能少于4个字符";
 		cjcname.focus();
		return;
 	}else{
 		document.getElementById("companyjcname_tit").innerHTML="";
 	}
 	if(cadder.value.length==0){
 		document.getElementById("companyadder_tit").innerHTML="请输入公司详细地址";
 		cadder.focus();
		return;
 	}else{
 		document.getElementById("companyadder_tit").innerHTML="";
 	}
 	if(cperson.value.length==0){
 		document.getElementById("companyperson_tit").innerHTML="请输入联系人";
 		cperson.focus();
		return;
 	}else{
 		document.getElementById("companyperson_tit").innerHTML="";
 	}
 	if(cphone.value.length==0){
 		document.getElementById("companyphone_tit").innerHTML="请输入联系电话";
 		cphone.focus();
		return;
 	}else{
 		document.getElementById("companyphone_tit").innerHTML="";
 	}
 	if(cfax.value.length==0){
 		document.getElementById("companyfax_tit").innerHTML="请输入传真";
 		cfax.focus();
		return;
 	}else{
 		document.getElementById("companyfax_tit").innerHTML="";
 	}
 	if(ccode.value.length == 0){
		document.getElementById("validcode_tit").innerHTML = "请输入验证码!";
		ccode.focus();
		return;
	}else{
		document.getElementById("validcode_tit").innerHTML = "";
	}
	document.getElementById("methods").value = getCheckedValues("methodchk");
	document.companyfrm.submit();
 }
 
if('<%=codemsg%>'!=''){
	document.getElementById("validcode_tit").innerHTML = '<%=codemsg%>';
	document.getElementById("validcode").focus();
}
 
</script>
</body>
</html>