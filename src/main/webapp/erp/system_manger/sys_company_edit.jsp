<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script  type='text/javascript'   src='<%=contextPath%>/js/pcasunzip.js'></script>
<title>公司信息调整</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String type = request.getParameter("type");
String cid = request.getParameter("cid");
//该公司选择的系统模块
WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
List<SysMethodInfo> companymethodsList = sysProcessService.getCompanyMethodsByPk(this.getServletContext(),request,Long.parseLong(cid));
List<SysMethodInfo> methodsList = (List<SysMethodInfo>)application.getAttribute(ConstWords.ServletContext_Method);
String trialday = SystemConfig.getParam("erp.company.trialDate");
String officalyear = SystemConfig.getParam("erp.company.officialDate");
%>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput('title');
		dwrSysProcessService.getSysCompanyInfoByPk(<%=cid%>,setpagevalue);
	}
	var sel1="";
	var sel2="";
	var sel3="";
	function setpagevalue(data){
 		if(data.success&&data.resultList.length>0){
 			var company = data.resultList[0];
 			DWRUtil.setValue("companyname",company.companyInfoName);
 			DWRUtil.setValue("companyjcname",company.companyInfoShortname);
 			sel1 = company.provinceCode;
 			sel2 = company.districtCode;
 			sel3 = company.companyArea;
 			new PCAS("province", "city", "area",sel1,sel2,sel3);
 			DWRUtil.setValue("companyadder",company.companyInfoAdder);
 			DWRUtil.setValue("companypost",company.companyInfoPost);
 			DWRUtil.setValue("companyperson",company.companyInfoEmployee);
 			DWRUtil.setValue("companyjob",company.companyInfoEmployeePosition);
 			DWRUtil.setValue("companyphone",company.companyInfoPhone);
 			DWRUtil.setValue("companyfax",company.companyInfoFax);
 			DWRUtil.setValue("companymail",company.companyInfoEmail);
 			setSelectValue("companytype",company.companyInfoType);
 			DWRUtil.setValue("companycode",company.companyInfoCode);
 			DWRUtil.setValue("sdate",company.companyInfoSdate);
 			DWRUtil.setValue("edate",company.companyInfoEdate);
 			DWRUtil.setValue("usercount",company.companyInfoUsercount);
 			DWRUtil.setValue("warecount",company.companyInfoWarehousecount);
 			DWRUtil.setValue("companytext",company.companyInfoContext);
 		}else{
 			alertmsg(data);
 		}
 	}
 	
 	function returnload(){
 		window.location.href = "<%=contextPath%>/erp/system_manger/sys_company_process.jsp?type=<%=type%>";
 	}
 	
 	function getcompany(){
	 	var company = new Object();
	 	company.primaryKey = <%=cid%>;
		company.provinceCode =DWRUtil.getValue("province"); 	
		company.districtCode =DWRUtil.getValue("city");
		company.companyArea =DWRUtil.getValue("area");
		company.companyInfoName =DWRUtil.getValue("companyname");
		company.companyInfoShortname =DWRUtil.getValue("companyjcname");
		company.companyInfoAdder =DWRUtil.getValue("companyadder");
		company.companyInfoPost =DWRUtil.getValue("companypost");
		company.companyInfoEmployee =DWRUtil.getValue("companyperson");
		company.companyInfoEmployeePosition =DWRUtil.getValue("companyjob");
		company.companyInfoEmail =DWRUtil.getValue("companymail");
		company.companyInfoPhone =DWRUtil.getValue("companyphone");
		company.companyInfoFax =DWRUtil.getValue("companyfax");
		company.companyInfoCode = DWRUtil.getValue("companycode");
		company.companyInfoType = DWRUtil.getValue("companytype");
		company.companyInfoSdate = DWRUtil.getValue("sdate");
		company.companyInfoEdate = DWRUtil.getValue("edate");
		company.companyInfoUsercount = DWRUtil.getValue("usercount");
		company.companyInfoWarehousecount = DWRUtil.getValue("warecount");
		company.companyInfoContext = DWRUtil.getValue("companytext");
	 	return company;
	 }
	 
	 function savecompanyinfo(){
	 	var cname = document.getElementById("companyname");
	 	var cjcname = document.getElementById("companyjcname");
	 	var cadder =document.getElementById("companyadder");
	 	var cperson =document.getElementById("companyperson");
	 	var cphone = document.getElementById("companyphone");
	 	var cfax = document.getElementById("companyfax");
	 	var ccode = document.getElementById("companycode");
	 	var csdate =document.getElementById("sdate");
	 	var cedate =document.getElementById("edate");
	 	var cucount =document.getElementById("usercount");
	 	var cwcount =document.getElementById("warecount");
	 	var tit = document.getElementById("title");
	 	
	 	if(cname.value.length==0){
	 		tit.innerHTML="请输入公司名称";
	 		window.scrollTo(0,0);
	 		cname.focus();
			return;
	 	}else if(cjcname.value.length<4){
	 		tit.innerHTML="公司名称不能少于4个字符";
	 		window.scrollTo(0,0);
	 		cname.focus();
			return;
	 	}
	 	if(cjcname.value.length==0){
	 		tit.innerHTML="请输入公司简称";
	 		window.scrollTo(0,0);
	 		cjcname.focus();
			return;
	 	}else if(cjcname.value.length<4){
	 		tit.innerHTML="公司简称不能少于4个字符";
	 		window.scrollTo(0,0);
	 		cjcname.focus();
			return;
	 	}
	 	if(cadder.value.length==0){
	 		tit.innerHTML="请输入公司详细地址";
	 		window.scrollTo(0,0);
	 		cadder.focus();
			return;
	 	}
	 	if(cperson.value.length==0){
	 		tit.innerHTML="请输入联系人";
	 		window.scrollTo(0,0);
	 		cperson.focus();
			return;
	 	}
	 	if(cphone.value.length==0){
	 		tit.innerHTML="请输入联系电话";
	 		window.scrollTo(0,0);
	 		cphone.focus();
			return;
	 	}
	 	if(cfax.value.length==0){
	 		tit.innerHTML="请输入传真";
	 		window.scrollTo(0,0);
	 		cfax.focus();
			return;
	 	}
	 	//验证公司码长度
		if(ccode.value.length<4){
			tit.innerHTML = "公司码长度不能小于4位!";
			window.scrollTo(0,0);
			ccode.focus();
			return;
		}
		//验证有效期
		try{
			var sd = csdate.value;
			var ed = cedate.value;
			var cd = new Date();
			sd = sd.replace(/-/g,"/");
			ed = ed.replace(/-/g,"/");
			var sde = new Date(sd);
			var ede = new Date(ed);
			if(sde>ede){
				tit.innerHTML ="开始不能大于结束日期";
				window.scrollTo(0,0);
				csdate.focus();
				return;
			}
		}catch(e){
			tit.innerHTML ="有效期格式错误";
			window.scrollTo(0,0);
			return;
		}
		//验证用户数量
		if(!isNumber(cucount.value)){
			tit.innerHTML ="用户数量只能为数字";
			window.scrollTo(0,0);
			cucount.focus();
			return;
		}
		//验证仓库数量
		if(!isNumber(cwcount.value)){
			tit.innerHTML ="仓库数量只能为数字";
			window.scrollTo(0,0);
			cwcount.focus();
			return;
		}
		//验证公司码是否重复
		dwrSysProcessService.isHashCompanyInfoCodeByEdit(ccode.value,<%=cid%>,function(data){
			if(data){
				tit.innerHTML = "公司码已存在!";
				window.scrollTo(0,0);
				ccode.focus();
				return;
			}else{
				initInput('title');
				var company =  getcompany();
				var methods = getCheckedValues("methodchk");
				dwrSysProcessService.editSysCompanyInfo(company,methods,callback);
			}
		});
	 }
	 function callback(data){
	 	alertmsg(data,"returnload()");
	 }
</script>
</head>
<body>
<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>公司信息</legend>
	<div>
	<table border="0" style="line-height: 32px;" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>公司名称</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyname"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">长度不能小于4个字符。<label style="padding-left: 5px;color: red" id="companyname_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>公司简称</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyjcname"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">长度不能小于4个字符。<label style="padding-left: 5px;color: red" id="companyjcname_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>所在省市</td>
		<td nowrap="nowrap" colspan="2" style="padding-left: 10px;">
		<div class="selectdiv"  style="float: left;margin-right: 5px;"><SELECT name=province id="province"></SELECT></div>
        <div class="selectdiv"  style="float: left;margin-right: 5px;"><SELECT name=city id="city"></SELECT></div>
        <div class="selectdiv"  style="float: left"><SELECT name=area id="area"></SELECT></div>
		</td>
		</tr>
		<tr>
		<td colspan="2">
		<table border="0" style="width: 100%" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>详细地址</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 312px;" id="companyadder">	</td>
		<td style="width: 50px;padding-right: 10px;" align="right">邮编</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 50px;" id="companypost" class="numform" maxlength="6"/></td>
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
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 180px;" id="companyperson"></td>
		<td style="width: 100px;padding-right: 10px;" align="right">职务</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 160px" id="companyjob"/></td>
		</tr>
		</table>
		</td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;"><label style="padding-left: 5px;color: red"  id="companyperson_tit"></label> </td>
		</tr>
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>联系电话</td>
		<td style="padding-left: 10px;"><input type="text" style="width: 465px;" id="companyphone"></td>
		<td style="padding-left: 10px; color: #808080;font-size: 12px;">多个号码以','号隔开。<label style="padding-left: 5px;color: red"  id="companyphone_tit"></label> </td>
		</tr>
		<tr>
		<td colspan="2">
		<table border="0" style="width: 100%" cellpadding="0" cellspacing="0">
		<tr>
		<td style="width: 150px;padding-right: 10px;" align="right"><em>*</em>传真</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 180px;" id="companyfax"></td>
		<td style="width: 100px;padding-right: 10px;" align="right">E-Mail</td>
		<td style="padding-left: 10px;" align="left"><input type="text" style="width: 160px" id="companymail"/></td>
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
	<legend style="font-family: '宋体';color:#D0410A ">系统信息</legend>
	<div>
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
				}else if(UtilTool.isCheckedCompanyMethods(sysMethod,companymethodsList)){
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
		<legend>运行信息</legend>
		<div>
		<table class="inputtable">
		<tr>
		<th><em>*</em>公司编码</th>
		<td>
		<input type="text" id="companycode"  must="公司编码不能为空" onkeyup="changeupper(this)" maxlength="10">
		<img src="<%=contextPath %>/images/createcode.gif" title="依据公司简称生成公司编码"  alt="依据公司简称生成公司编码" style="cursor: pointer;vertical-align: bottom;" onclick="createcode()">
		</td>
		<td valign="bottom">
		<ul style="color: #808080;list-style: square;margin-top: 0;margin-bottom: 0">
			<li>公司编码是公司在系统中的唯一标识，不能重复。</li>
			<li>公司编码长度为<font color="red">4~10</font>位。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th><em>*</em>公司类型</th>
		<td>
		<select id="companytype" must="请选择公司类型">
		<%=UtilTool.getSelectOptionsByEnum(EnumUtil.SYS_COMPANY_TYPE_SEL.getSelectAndText("")) %>
		</select>
		</td>
		<td>
		<ul style="color: #808080;list-style: square;margin-top: 0;margin-bottom: 0">
			<li>试用公司默认有效期为 <font color="red"><%=trialday %></font> 天。</li>
			<li>正式公司默认有效期为 <font color="red"><%=officalyear %></font> 月。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th><em>*</em>有效期</th>
		<td>
		<input class="Wdate" type="text" id="sdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,minDate:'%y-%M-%d'})"/>
		&nbsp;至&nbsp;	<input class="Wdate" type="text" id="edate" onFocus="WdatePicker({isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'sdate\',{d:1});}'})"/>
		</td>
		<td>
		<ul style="color: #808080;list-style: square;margin-top: 0;margin-bottom: 0">
			<li>有效期为该公司能登录系统的开始及截止时间。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th><em>*</em>用户数量</th>
		<td>
		<input type="text" class="numform" value="0" maxlength="3" id="usercount">
		</td>
		<td>
		<ul style="color: #808080;list-style: square;margin-top: 0;margin-bottom: 0">
			<li>允许该公司使用的最大用户数量，0表示不限制。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th><em>*</em>仓库数量</th>
		<td>
		<input type="text" class="numform" value="0" maxlength="2" id="warecount">
		</td>
		<td>
		<ul style="color: #808080;list-style: square;margin-top: 0;margin-bottom: 0">
			<li>允许该公司在使用资产管理系统中最大仓库数量，0表示不限制。</li>
		</ul>
		</td>
		</tr>
		<tr>
		<th>附加说明</th>
		<td colspan="2">
		<textarea id="companytext"></textarea>
		</td>
		</tr>
		</table>
		</div>
</fieldset>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="savecompanyinfo()"></btn:btn>
</td>
<td style="width: 15px;"></td>
<td>
<btn:btn onclick="returnload();" value=" 返 回 "></btn:btn>
</td>
</tr>
</table>
 <script type="text/javascript">
	function changeupper(obj){
		obj.value = (obj.value).toUpperCase();
	}
	function createcode(){
	 	dwrSysProcessService.getSysCompanyInfoCodeByShortName(<%=cid%>,setcode);
	}
	
	function setcode(data){
		document.getElementById("companycode").value = data.toUpperCase();
	}
 </script>
</body>
</html>