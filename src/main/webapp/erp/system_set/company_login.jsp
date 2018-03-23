<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
SessionUser usermsg = (SessionUser)LoginContext.getSessionValueByLogin(request);
long cid = usermsg.getCompanyId();
String isedit = "false";
int clogin = -1;

String titname="";
String titenname="";
SysConfig sysconfig =usermsg.getSysconfig();
if(sysconfig!=null){
	titname = sysconfig.getProjectName();
	titenname = sysconfig.getProjectEgName();
}
SysCompanyInfo company=usermsg.getCompanyInfo();
if(company!=null){
	if(company.getCompanyInfoLogin()!=null&&company.getCompanyInfoLogin()>0){
		clogin = company.getCompanyInfoLogin();
		isedit = "true";
	}
	if(company.getCompanyInfoTitle()!=null&&company.getCompanyInfoTitle().trim().length()>0){
		titname = company.getCompanyInfoTitle();
	}
	if(company.getCompanyInfoEnTitle()!=null&&company.getCompanyInfoEnTitle().trim().length()>0){
		titenname = company.getCompanyInfoEnTitle();
	}
}
 %>
<title>系统显示</title>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput('helpTitle', "您可以在此可以上传修改系统左上角公司标志及系统显示标题！");
		dwrSysProcessService.getSysCompanyInfoByPk(<%=cid%>,setpagevalue);
		dwrCommonService.getImageInfoListToString(<%=clogin%>,setImageaccept);
	}
	function setpagevalue(data){
 		if(data.success&&data.resultList.length>0){
 			var company = data.resultList[0];
 			DWRUtil.setValue("companyname",company.companyInfoName);
 			DWRUtil.setValue("companyshortname",company.companyInfoShortname);
 		}
 	}
 	function setImageaccept(data){
		Sys.setFilevalue("companyinfologin",data);
	}
	
	function saveCompanyInfo(){
		var warnArr = new Array();
		warnArr[0] = "companyinfologinMust";
		//清空所有信息提示
		warnInit(warnArr);
		
		var tit = DWRUtil.getValue("companyinfotitle");
		var entit = DWRUtil.getValue("companyinfoentitle");
		
		var imgp = DWRUtil.getValue("companyinfologin");
		if(imgp.length==0){
			setMustWarn("companyinfologinMust","请上传公司标志！"); 
			return false;
		}
		dwrSysProcessService.saveCompanyInfoImage(tit,entit,imgp,savecallback);
	}

	
	function repload(){
		if (self != top){
			window.top.location = "<%=request.getContextPath()+"/login.jsp"%>";	
		}
	}
	
	function savecallback(data){
		if(data.success){
			confirmmsg("信息修改成功，重新登录后才生效!是否重新登录?","repload()",null);
		}else{
			alertmsg(data);
		}
	}
	function closePage(){
		closeMDITab();
	}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">修改公司系统显示</div>
		<div>
		<table  class="inputtable" border="0" style="width: 95%">
			<tr>
			<th width="15%">公司名称</th>
			<td id="companyname" class="detailtabletd"></td>
			</tr>
			<tr>
			<th width="15%">公司简称</th>
			<td id="companyshortname" class="detailtabletd"></td>
			</tr>
			<tr>
			<th width="15%">系统标题</th>
			<td>
			<input type="text" maxlength="50" id="companyinfotitle" value="<%=titname %>" style="width: 70%"/>
			</td>
			</tr>
			<tr>
			<th width="15%">系统英文标题</th>
			<td>
			<input type="text" maxlength="90" id="companyinfoentitle" value="<%=titenname %>" style="ime-mode:disabled; width: 70%"/>
			</td>
			</tr>
			<tr>
			<th width="15%" valign="top"  style="padding-top: 10px"><em> *</em>公司标志(Logo)</th>
			<td align="left" style="padding-top: 10px">
			<%String tmpp = Base64.getBase64FromString(this.getServletContext().getRealPath("/images/syslogin.png")); %>
			<file:imgupload width="372" acceptTextId="companyinfologin" height="192" edit="<%=isedit %>" alt="公司logo" defaultImg="<%=tmpp %>"></file:imgupload>
			<label id="companyinfologinMust"></label>
			<ul style="line-height: 24px;margin-left: 14px">
			<li>图片格式建议为PNG扩展名，背景透明。</li>
			<li>图片大小：高度60px;宽度自适应。</li>
			</ul>
			</td>
			</tr>
		</table>
		</div>
	</div>
	<br/>
	<table align="center">
<tr>
<td>
<btn:btn onclick="saveCompanyInfo();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存系统信息" /><td style="width: 10px;"></td>
<td>
<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/>
</td>
</tr>
</table>

</body>
</html>