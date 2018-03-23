<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>公司明细</title>
<style type="text/css" media="print">
	.noprint{
		display: none;
	}
</style>
<%
String type = request.getParameter("type");
String cid = request.getParameter("cid");
//该公司选择的系统模块
WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
List<SysMethodInfo> methodsList = sysProcessService.getCompanyMethodsByPk(this.getServletContext(),request,Long.parseLong(cid));

SysUserInfo userinfo = sysProcessService.getSysUserInfoByCompanyId(this.getServletContext(),request,Integer.parseInt(cid));
%>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		dwrSysProcessService.getSysCompanyInfoByPk(<%=cid%>,setpagevalue);
	}
	
	function setpagevalue(data){
 		if(data.success&&data.resultList.length>0){
 			var company = data.resultList[0];
 			DWRUtil.setValue("companyname",company.companyInfoName);
 			DWRUtil.setValue("companyshortname",company.companyInfoShortname);
 			DWRUtil.setValue("companyarea",company.provinceCode+"-"+company.districtCode+"-"+company.companyArea);
 			var adder=company.companyInfoAdder;
 			if(company.companyInfoPost!=null&&company.companyInfoPost.length>0){
 				adder+=" ["+company.companyInfoPost+"]";
 			}
 			DWRUtil.setValue("companyadderandpost",adder);
 			var emp = company.companyInfoEmployee;
 			if(company.companyInfoEmployeePosition!=null&&company.companyInfoEmployeePosition.length>0){
 				emp+=" ["+company.companyInfoEmployeePosition+"]";
 			}
 			DWRUtil.setValue("companyemployeeandjob",emp);
 			DWRUtil.setValue("companyphone",company.companyInfoPhone);
 			DWRUtil.setValue("companyfax",company.companyInfoFax);
 			if(company.companyInfoEmail!=null&&company.companyInfoEmail.length>0){
 				document.getElementById("companymail").innerHTML ="<a href='mailto:"+company.companyInfoEmail+"' title='点击发送邮件' style='color:#336699'>"+company.companyInfoEmail+"</a>";
 			}
 			DWRUtil.setValue("companyregdate",company.companyInfoRegDate);
 			DWRUtil.setValue("companymangerdate",company.companyInfoLastDate);
 			if(company.companyInfoStatus == <%=EnumUtil.SYS_COMPANY_STATUS.TAKE.value%>){
 				document.getElementById("companymsg").style.display="";
 				document.getElementById("companyuser").style.display="";
	 			DWRUtil.setValue("companycode",company.companyInfoCode);
	 			DWRUtil.setValue("companydate",company.companyInfoSdate+" 至 "+company.companyInfoEdate);
	 			var uc = company.companyInfoUsercount+"个";
	 			if(company.companyInfoUsercount==0){
	 				uc ="不限制";
	 			}
	 			DWRUtil.setValue("companyusercount",uc);
	 			var wc = company.companyInfoWarehousecount+"个";
	 			if(company.companyInfoWarehousecount==0){
	 				wc ="不限制";
	 			}
	 			DWRUtil.setValue("companywarecount",wc);
 			}
 		}else{
 			alertmsg(data);
 		}
 	}
 	
 	function returnload(){
 		window.location.href = "<%=contextPath%>/erp/system_manger/sys_company_process.jsp?type=<%=type%>";
 	}
</script>
</head>
<body>
<fieldset>
	<legend>公司信息</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th>公司名称</th>
	<td id="companyname"></td>
	<th>简称</th>
	<td id="companyshortname"></td>
	</tr>
	<tr>
	<th>所在省市</th>
	<td  id="companyarea"></td>
	<th>详细地址[邮编]</th>
	<td id="companyadderandpost"></td>
	</tr>
	<tr>
	<th>联系人[职务]</th>
	<td id="companyemployeeandjob"></td>
	<th>联系电话</th>
	<td id="companyphone"></td>
	</tr>
	<tr>
	<th>传真</th>
	<td id="companyfax"></td>
	<th>E-Mail</th>
	<td id="companymail"></td>
	</tr>
	<tr>
	<th>注册日期</th>
	<td id="companyregdate"></td>
	<th>管理公司确认日期</th>
	<td id="companymangerdate"></td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>
<fieldset>
	<legend>系统模块</legend>
	<div>
	<table border="0" cellpadding="0" cellspacing="0" height="40" style="line-height: 32px;">
	<tr>
	<%
	String imgpath ="";
	String name ="";
	if(methodsList!=null && methodsList.size()>0){
		for(int i=0;i<methodsList.size();i++){
			SysMethodInfo sysMethod =methodsList.get(i);
			imgpath = sysMethod.getImageSrc();
			name = sysMethod.getMethodInfoName();
	 %>
	 
	 <td>&nbsp;</td>
	<td style="padding:5px; padding-left: 10px;">
	<div style="border:1px solid #cccccc; width:33px; height:32px; margin:3px;overflow: hidden;">
	<img src="<%=request.getContextPath()+"/images/projectimg/"+imgpath %>" border="0" />
	</div>
	</td>
	<td style="padding-left: 5px;padding-right: 20px;">
	<%=name %>
	</td>
	<%}}%>
	</tr>
	</table>
	</div>
	</fieldset>
	<br/>
<fieldset style="display:none;" id="companymsg">
		<legend>系统信息</legend>
		<div>
		<table class="detailtable" align="center">
		<tr>
		<th>公司编码</th>
		<td id="companycode" colspan="3"></td>
		<td style="color:#666666" nowrap="nowrap">公司登录系统的标识码。</td>
		</tr>
		<tr>
		<th>有效期</th>
		<td  id="companydate" colspan="3"></td>
		<td style="color:#666666" nowrap="nowrap">公司使用系统的时间范围。</td>
		</tr>
		<tr>
		<th>用户数限制</th>
		<td id="companyusercount"></td>
		<th>仓库数限制</th>
		<td id="companywarecount"></td>
		<td style="color:#666666;" nowrap="nowrap">没有购买资产管理系统该限制无效。</td>
		</tr>
		<tr>
		<th>附加说明</th>
		<td id="companytext" colspan="3"></td>
		<td style="color:#666666" nowrap="nowrap"></td>
		</tr>
		</table>
		</div>
</fieldset>
<br/>
<%
String un="";
String pw ="";
if(userinfo!=null){
	un=userinfo.getUserName();
	pw = Base64.getStringFromBase64(userinfo.getUserpassword());
}
 %>
<fieldset style="display:none;" id="companyuser">
		<legend>超级用户</legend>
		<div>
		<table class="detailtable" align="center">
		<tr>
		<th>用户名</th>
		<td id="username">
		 <%=un %>
		</td>
		<td nowrap="nowrap">
		<ul style="color: #666666;margin-top: 0;margin-bottom: 0">
			<li>此用户为公司系统超级管理员。</li>
			<li>只能做基础数据初始化操作（部门、人员、岗位、用户、权限等设置）。</li>
			<li>此用户包含购买系统的所有功能，但不能做具体的业务操作。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th>用户密码</th>
		<td  id="userpwd">
		<%=pw %>
		</td>
		<td nowrap="nowrap">
		<ul style="color: #666666;margin-top: 0;margin-bottom: 0">
			<li>此密码为登录系统的初始密码。</li>
			<li>请尽快登录系统修改密码，初始化数据完成并封存。</li>
		</ul>
		</td>
		</tr>
		</table>
		</div>
</fieldset>
<br/>
<table align="center" class="noprint">
<tr>
<td>
<btn:btn onclick="print()" value=" 打 印 "></btn:btn>
</td>
<td style="width: 15px;"></td>
<td>
<btn:btn onclick="returnload();" value=" 返 回 "></btn:btn>
</td>
</tr>
</table>
 
</body>
</html>