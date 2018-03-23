<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统异常明细查看</title>
<%
    String aid = request.getParameter("aid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script> 
  <script type="text/javascript">
     window.onload = function(){
		dwrSysProcessService.getSysExceptionByPk(<%=Integer.parseInt(aid)%>,setPageValue);
	}
	function setPageValue(data){
	var effectiveDate ="";
	
	
	 if(data != null){
	      if(data.resultList.length>0){
	          var sysExcp = data.resultList[0];
	          DWRUtil.setValue("userName",sysExcp.userInfo.userName);
	          DWRUtil.setValue("companyName",sysExcp.companyInfo.companyInfoName);
	          DWRUtil.setValue("companyCode",sysExcp.companyInfo.companyInfoCode);
	          DWRUtil.setValue("exceptionClass",sysExcp.exceptionClass);	        
	          DWRUtil.setValue("exceptionMsg",sysExcp.exceptionMsg);	          
	          DWRUtil.setValue("exceptionDate",sysExcp.exceptionDate);
	          DWRUtil.setValue("processName",sysExcp.processName);
	          DWRUtil.setValue("processDate",sysExcp.processDate);
	          if(sysExcp.processContext==null) {
	             document.getElementById("processContext").innerHTML = "";
	          } else {
	             document.getElementById("processContext").innerHTML = sysExcp.processContext;
	          }
	          document.getElementById("exceptionContext").innerHTML = sysExcp.exceptionContext;
	          //附件显示为下载
			 
	      }
	  }
	}
</script>
</head>
  <body>
   <fieldset>
	<legend>异常明细</legend>
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
	<th>处理人</th>
	<td id="processName" colspan="3"></td>
	</tr>
	<tr>
	<th>处理日期</th>
	<td id="processDate" colspan="3"></td>
	</tr>
	<tr>
	<th>处理原因</th>
	<td id="processContext" colspan="3"></td>
	</tr>
	
	</table>
	</div>
</fieldset>
<br/>

<br/>
</body>
</html>