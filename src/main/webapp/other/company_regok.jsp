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
<%@page import="com.pinhuba.core.pojo.SysCompanyInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
boolean bl =Boolean.parseBoolean(SystemConfig.getParam("erp.sys.LoginIsview"));
if(!bl){
	return;
}

Object companyObj = request.getAttribute(ConstWords.TempStringMsg);
if(companyObj==null){
	return;
}

SysCompanyInfo cinfo = (SysCompanyInfo)companyObj;

String path = request.getContextPath();
WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)context.getBean("dwrSysProcessService");
String ptname = "品互网络基础应用管理平台";
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
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="966" align="center" height="70">
<tr height="70">
<td width="110">
<img src="<%=path %>/images/login_title.png" border="0" height="45"/>
</td>
<td align="left"><font face="幼圆" style="font-size:22px;"  color="#666666"><strong><%=ptname %></strong></font></td>
<td align="right" style="color:#999999;padding-right: 15px;">
<a href="javascript:void(0);" class="tit">帮助</a>&nbsp;&nbsp;
</td>
</tr>
</table>
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
		<div id="regokdiv" style="padding: 20px;font-family: Microsoft YaHei, 宋体, Segoe UI, verdana, arial;">
		 <table width="730" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td width="64" align="left" valign="top" rowspan="2">
                    <img src="<%=path %>/images/regimages/regokimg.png" alt="注册成功">
                </td>
                <td  valign="middle" align="left" width="*">
                    <h1 style="color: #4465A2;font-size:18px;font-weight: normal;vertical-align:bottom;margin-top: 7px;margin-bottom: 4px;">您已经注册成功</h1>
                </td>
            </tr>
             <tr>
                <td>
                    <div style="border-bottom: #B6BCC6 1px solid;"></div>
                </td>
            </tr>
             <tr>
            <td>
              &nbsp;
            </td>
            <td>
              <h2 style="font-size:15px;font-weight: normal;margin-top: 20px;margin-bottom: 1px;color: green">欢迎您:<label id="regokname" style="color: #182300"><%=cinfo.getCompanyInfoName() %></label></h2>
              <ul style="line-height: 30px;color: #333333;font-size: 13px;">
				<li>我们会尽快与您联系，完成系统运行的初始化工作。</li>
				<li>系统初始化完成后会尽快以传真或快递方式邮寄系统用户信息。</li>
				<li>如还有其他疑问，可访问<a href="http://www.pinhuba.com" target="_blank" style="color: rgb(19,112,171)">官方网站</a>获取更多信息。</li>
			  </ul>
            </td>
          </tr>
         <tr>
              <td>
                 &nbsp;
              </td>
              <td>
                 <div style="border-bottom: #B6BCC6 1px solid;"></div>
              </td>
          </tr>
          <tr>
              <td>
                 &nbsp;
              </td>
              <td align="right" style="padding-right: 5px">
                <input type="button" value=" 关闭 " style="width:6em;" onclick="window.close();">
              </td>
          </tr>
         </table>
		
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
<%@ include file="../Copyright.jsp" %>
</body>
</html>