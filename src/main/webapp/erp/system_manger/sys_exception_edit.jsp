<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<title>处理异常界面</title>
<%
	String sysExceptionPk = request.getParameter("sysExceptionPk");
    SessionUser sUser = (SessionUser) LoginContext.getSessionValueByLogin(request);
    String userName = sUser.getUserName();
%>
<style type="text/css">
	body {
	background-color: #fefefe;
}
</style>
<script type="text/javascript">
window.onload = function(){
	initInput('title');
	if(<%=sysExceptionPk%> != null){
	    dwrSysProcessService.getSysExceptionByPk(<%=sysExceptionPk%>,setPageValue);
	}
}

var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}


function setPageValue(data){
	 if(data.success && data.resultList.length>0){
          var sysExcp = data.resultList[0];
          
          DWRUtil.setValue("userName",sysExcp.userInfo.userName);
          DWRUtil.setValue("companyName",sysExcp.companyInfo.companyInfoName);
          DWRUtil.setValue("companyCode",sysExcp.companyInfo.companyInfoCode);
          DWRUtil.setValue("exceptionClass",sysExcp.exceptionClass);	        
          DWRUtil.setValue("exceptionMsg",sysExcp.exceptionMsg);	          
          DWRUtil.setValue("exceptionDate",sysExcp.exceptionDate);
          DWRUtil.setValue("exceptionContext",sysExcp.exceptionContext);
          DWRUtil.setValue("processName","<%=userName %>");
          DWRUtil.setValue("processContext",sysExcp.processContext);
	  }
}


function getSysExceptionInfo(){
	var sysExcp = new Object();
   	if(<%=sysExceptionPk%> != null){
      sysExcp.primaryKey = <%=sysExceptionPk%>;
    }
	sysExcp.processName = DWRUtil.getValue("processName");
	
	sysExcp.processContext = fck.GetXHTML();

	return sysExcp;
}
function reload(){
    Sys.load('<%=contextPath%>/erp/system_manger/sys_exception.jsp');
}

function save(){
    var bl = validvalue('title');
	if(bl){
    	 if(fck.GetXHTML().length < 10){
     		document.getElementById("title").innerHTML="异常原因必须大于10个字符！";
         	return;
         }
         dwrSysProcessService.updateSysException(getSysExceptionInfo(),saveCallback);
    }
	
}
    
function saveCallback(data){
    alertmsg(data,"reback()");
}	

function reback(){
	if(<%=sysExceptionPk%> != null){
	     reload();
    }else{
         DWRUtil.setValue("processName","");
         fck.SetHTML("");
    }
}


</script>
</head>
<body>

<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>处理系统异常</legend>
	<div>
	
	<table class="inputtable">

	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">用户名称</th>
	<td id="userName" colspan="3"></td>
	</tr>
	<tr>
	<th>公司名称</th>
	<td id="companyName" colspan="3"></td>
	</tr>
	<tr>
	<th>公司代码</th>
	<td id="companyCode" colspan="3"></td>
	</tr>
	<tr>
	<th>异常类型</th>
	<td id="exceptionClass" colspan="3"></td>
	</tr>
	
	<tr>
	<th>异常消息</th>
	<td id="exceptionMsg"></td>
	</tr>
	
	<tr>
	<th>异常日期</th>
	<td id="exceptionDate"></td>
	</tr>
	<tr>
	<th>异常内容</th>
	<td colspan="3"  id="exceptionContext">
	</td>
	</tr>
	
	<tr>
	<th><em>* </em>处理人</th>
	<td colspan="3" style="text-align: left;">
	<input type="text" id="processName" must="处理人不能为空" value="" style="width: 90%" maxlength="50"></td>
	</tr>
	
	<tr>	
	<th><em>* </em>处理原因</th>
	<td style="text-align: left" colspan="3">
	<FCK:editor instanceName="processContext" height="280" width="90%" ></FCK:editor>
	</td>
	</tr>
	
	</table>
	</div>
</fieldset>
<br/>
<br/>
<br/>
<center>
<table>
<tr>
<td><btn:btn onclick="save();" value=" 确  定 " /></td>
<td style="width: 20px;"></td>
<td ><btn:btn onclick="reload();" value=" 返  回 "/></td>
</tr>
</table>
</center>
</body>
</html>