<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
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
String type = request.getParameter("type");
String cid = request.getParameter("cid");
//该公司选择的系统模块
WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService sysProcessService = (DwrSysProcessService) webAppContext.getBean("dwrSysProcessService");
List<SysMethodInfo> methodsList = sysProcessService.getCompanyMethodsByPk(this.getServletContext(),request,Long.parseLong(cid));
String trialday = SystemConfig.getParam("erp.company.trialDate");
String officalyear = SystemConfig.getParam("erp.company.officialDate");
GregorianCalendar calendar=new GregorianCalendar();
int year=calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH);
int day = calendar.get(Calendar.DAY_OF_MONTH);
 %>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput('title');
		changeDate(document.getElementById("companytype"));
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
 		}else{
 			alertmsg(data);
 		}
 	}
 	function getServerDate(m,d){
	  	var year = document.getElementById('year').value;
	  	var month = document.getElementById('month').value;
	  	var date = document.getElementById('day').value;
	  	var sdate=new Date();
	  	sdate.setYear(year);
	  	var nm = parseInt(month)+parseInt(m);
	  	sdate.setMonth(nm);
	  	var nd = parseInt(date)+parseInt(d);
	  	sdate.setDate(nd);
	  	return sdate;
  	}
  	function getToDayStr(m,d){
		var now = getServerDate(m,d);
		var year = now.getYear();
		var month = now.getMonth()+1;
		var date = now.getDate();
		var timeValue = "";
		timeValue += year + "-";
		timeValue += ((month < 10) ? "0" : "") + month + "-";
		timeValue += ((date < 10) ? "0" : "") + date;
		return timeValue;
	}
	 	
 	function changeDate(obj){
 		var sobj = document.getElementById("sdate");
 		var eobj = document.getElementById("edate");
 		if(obj.value == <%=EnumUtil.SYS_COMPANY_TYPE_SEL.TRIAL.value%>){
 			sobj.value = getToDayStr(0,0);
			eobj.value = getToDayStr(0,<%=trialday%>);
 		}else if(obj.value == <%=EnumUtil.SYS_COMPANY_TYPE_SEL.OFFICIAL.value%>){
 			sobj.value = getToDayStr(0,0);
			eobj.value = getToDayStr(<%=officalyear%>,0);
 		}
 	}
 
	function createcode(){
	 	dwrSysProcessService.getSysCompanyInfoCodeByShortName(<%=cid%>,setcode);
	}
	
	function setcode(data){
		document.getElementById("companycode").value = data.toUpperCase();
	}
	
	function changeupper(obj){
		obj.value = (obj.value).toUpperCase();
	}
	
	function saveprocess(){
		var bl = validvalue('title');
		var tit= document.getElementById("title");
		if(bl){
			var company = getcompany();
			//验证公司码长度
			if(company.companyInfoCode.length<4){
				tit.innerHTML = "公司码长度不能小于4位!";
				return;
			}
			//验证有效期
			try{
				var sd = company.companyInfoSdate;
				var ed = company.companyInfoEdate;
				var cd = new Date();
				sd = sd.replace(/-/g,"/");
				ed = ed.replace(/-/g,"/");
				var sde = new Date(sd);
				var ede = new Date(ed);
				if(sde>ede){
					tit.innerHTML ="开始不能大于结束日期";
					return;
				}
				if(ede<=cd){
					tit.innerHTML ="结束日期不能小于等于当天日期";
					return;
				}
			}catch(e){
				tit.innerHTML ="有效期格式错误";
				return;
			}
			//验证用户数量
			if(!isNumber(company.companyInfoUsercount)){
				tit.innerHTML ="用户数量只能为数字";
				return;
			}
			//验证仓库数量
			if(!isNumber(company.companyInfoWarehousecount)){
				tit.innerHTML ="仓库数量只能为数字";
				return;
			}
			//验证公司码是否重复
			dwrSysProcessService.isHashCompanyInfoCode(company.companyInfoCode,function(data){
				if(data){
					tit.innerHTML = "公司码已存在!";
					return;
				}else{
					dwrSysProcessService.updateSysCompanyInfo(company,callback);
				}
			});
		}
	}
	
	function callback(data){
		if(data.success){
			confirmmsg("公司初始化成功,是否打印传真或邮寄信息?","companyprint()","returnload()");
		}else{
			alertmsg(data);
		}
	}
	function companyprint(){
		Sys.href("<%=contextPath%>/erp/system_manger/sys_company_detail.jsp?type=<%=type%>&cid=<%=cid%>");
	}
	
	function getcompany(){
		var company = new Object();
		company.primaryKey = <%=cid%>;
		company.companyInfoCode = DWRUtil.getValue("companycode");
		company.companyInfoType = DWRUtil.getValue("companytype");
		company.companyInfoSdate = DWRUtil.getValue("sdate");
		company.companyInfoEdate = DWRUtil.getValue("edate");
		company.companyInfoUsercount = DWRUtil.getValue("usercount");
		company.companyInfoWarehousecount = DWRUtil.getValue("warecount");
		company.companyInfoContext = DWRUtil.getValue("companytext");
		return company;
	}
	
	function returnload(){
		Sys.href("<%=contextPath%>/erp/system_manger/sys_company_process.jsp?type=<%=type%>");
	}
	
</script>
<title>申请公司处理</title>
</head>
<body>
<input type="hidden" id="year" value="<%=year%>">
<input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="day" value="<%=day%>">
	<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>申请公司信息</legend>
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
		</table>
	</div>
	</fieldset>
	<br/>
	<fieldset>
	<legend  style="font-family: '宋体';color:#D0410A ">系统模块</legend>
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
	
	<fieldset>
		<legend>运行初始化</legend>
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
		<select id="companytype" must="请选择公司类型" onchange="changeDate(this)">
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
<btn:btn onclick="saveprocess();"></btn:btn>
</td>
<td style="width: 15px;"></td>
<td>
<btn:btn onclick="returnload();" value=" 返 回 "></btn:btn>
</td>
</tr>
</table>
</body>
</html>