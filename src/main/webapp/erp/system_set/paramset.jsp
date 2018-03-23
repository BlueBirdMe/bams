<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>参数设置</title>
<%
WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
DwrSysProcessService sysProcessService = (DwrSysProcessService)context.getBean("dwrSysProcessService");
List<SysParam> paramList = sysProcessService.getAllSysParamByCompanyId(this.getServletContext(),request);
%>
<script type="text/javascript">
	window.onload = function(){
		initInput('title',"可以在此对系统运行参数进行设置，将影响整个系统。");
	}
	function saveparam(){
		var tit = document.getElementById("title");
		var tmp;
		<%
		if(paramList!=null&&paramList.size()>0){
			for(int i=0;i<paramList.size();i++){
				SysParam param = paramList.get(i);
				if(param.getParamTypeValue()!=null&&param.getParamTypeValue().length()>0){
					if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.TEXT.value){
		%>
						if(trim(document.getElementById("<%=param.getParamIndex()%>").value).length<<%=param.getParamTypeValue()%>){
							tit.innerHTML = "<%=param.getParamTitle()%>长度不能小于<%=param.getParamTypeValue()%>";
							return;
						}
					<%}else if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.NUM.value){
						String[] vls = param.getParamTypeValue().split("-");
					%>
						tmp = document.getElementById("<%=param.getParamIndex()%>").value;
						if(tmp<<%=vls[0]%>){
							tit.innerHTML = "<%=param.getParamTitle()%>值不能小于<%=vls[0]%>";
							return;
						}
						if(tmp><%=vls[1]%>){
							tit.innerHTML = "<%=param.getParamTitle()%>值不能大于<%=vls[1]%>";
							return;
						}
					<%}%>
		<%
				}
			}
		}
		%>
		<%
		if(paramList!=null&&paramList.size()>0){
		%>
		var array = new Array();
		<%
			for(int i=0;i<paramList.size();i++){
				SysParam param = paramList.get(i);
		%>
			var pa = new Object();
			pa.primaryKey = document.getElementById("<%=param.getParamIndex()%>_pk").value;
			pa.paramValue = document.getElementById("<%=param.getParamIndex()%>").value;
			array[<%=i%>] = pa;
		<%}%>
		dwrSysProcessService.updateSysParams(array,callback);
		<%}else{%>
		alertmsg("不包含任何参数信息!");
		<%}%>
	}
	function callback(data){
		alertmsg("参数设置成功，重新登录生效！","closePage()");
	}
	
	function closePage(){
		closeMDITab();
	}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
	<div class="requdiv">
		<label id="title"></label>
	</div>
	<div class="formTitle">
		参数设置
	</div>
	<div>
	<%
	if(paramList!=null&&paramList.size()>0){
	 %>
	<table class="inputtable">
	<%
	for(int i=0;i<paramList.size();i++){
		SysParam param = paramList.get(i);
	 %>
	<tr>
	<th><em>*&nbsp;</em><%=param.getParamTitle() %></th>
	<td>
	
	<%
	if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.TEXT.value){
	 %>
	<input type="text" id="<%=param.getParamIndex() %>" value="<%=param.getParamValue() %>">
	<%}else if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.NUM.value){ %>
	<input type="text" id="<%=param.getParamIndex() %>" value="<%=param.getParamValue() %>" class="numform">
	<%}else if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.SELECT.value){ %>
	<select id="<%=param.getParamIndex() %>">
	<%
	if(param.getParamTypeValue()!=null&&param.getParamTypeValue().trim().length()>0){
		String[] options = param.getParamTypeValue().trim().split(",");
	 	for(int j=0;j<options.length;j++){
	 		String chk="";
	 		if(options[j].equals(param.getParamValue())){
	 			chk="selected='selected'";
	 		}
	 %>
	 	<option  value="<%=options[j] %>" <%=chk %>><%=options[j] %></option>
	 <%
	 }
	 } %>
	</select>
	<%} %>
	</td>
	<td style="white-space: normal;padding-left: 10px;font-family: 宋体">
	<input type="hidden" id="<%=param.getParamIndex() %>_pk" value="<%=param.getPrimaryKey() %>">
	<%
	if(param.getParamTypeValue()!=null&&param.getParamTypeValue().length()>0){
		if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.TEXT.value){
			out.print("<font color='green'>值长度限制:</font>大于等于"+param.getParamTypeValue()+"<br/>");
		}else if(param.getParamType() == EnumUtil.SYS_PARAM_TYPE.NUM.value){
			out.print("<font color='green'>值范围限制:</font>"+param.getParamTypeValue()+"<br/>");
		}
	}
	%>
	描述:<font color='#808080'><%=param.getParamRemark() %></font>
	</td>
	</tr>
	<%} %>
	</table>
	<%} %>
	</div>
	</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="saveparam();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存系统设置" /></td>
<td style="width: 10px;"></td>
<td>
<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/>
</td>
</tr>
</table>
</body>
</html>