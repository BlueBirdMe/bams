<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String aid = request.getParameter("aid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script> 
  <script type="text/javascript">
     window.onload = function(){
		dwrSysProcessService.getSysHelpByPk(<%=Integer.parseInt(aid)%>,setPageValue);
	}
	function setPageValue(data){
	var effectiveDate ="";
	
	
	 if(data != null){
	      if(data.resultList.length>0){
	          var sysHelp = data.resultList[0];
	          if(sysHelp.methodInfo!=null){
	          	DWRUtil.setValue("methodname",sysHelp.methodInfo.methodInfoName);
	          }
	          DWRUtil.setValue("findsign",sysHelp.findSign);
	          DWRUtil.setValue("helpTitle",sysHelp.helpTitle);	        
	          DWRUtil.setValue("helpDate",sysHelp.helpDate);	          
	          DWRUtil.setValue("helpKeyword",sysHelp.helpKeyword);
	          document.getElementById("helpContext").innerHTML = sysHelp.helpContext;
	          
	      }
	  }
	}
</script>
</head>
  <body>
   <fieldset>
	<legend>帮助明细</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">所属模块</th>
	<td id="methodname"></td>
	<th width="15%">检索标识</th>
	<td id="findsign"></td>
	</tr>
	<tr>
	<th width="15%">帮助标题</th>
	<td id="helpTitle" colspan="3"></td>
	</tr>
	<tr>
	<th>关键字</th>
	<td id="helpKeyword"  colspan="3"></td>
	</tr>
	
	<tr>
	<th>发布时间</th>
	<td id="helpDate"  colspan="3"></td>
	</tr>
	<tr>
	<th>帮助内容</th>
	<td colspan="3"  id="helpContext">
	</td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>

<br/>
</body>
</html>