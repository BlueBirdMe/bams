<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>便签明细查看</title>
<%
    String aid = request.getParameter("aid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script> 
  <script type="text/javascript">
     window.onload = function(){
     	useLoadingMassage();
		dwrPersonalOfficeService.getNotebookById(<%=Integer.parseInt(aid)%>,setPageValue);
	}
	function setPageValue(data){
	var effectiveDate ="";
	
	
	 if(data != null){
	      if(data.resultList.length>0){
	          var oaNotebook = data.resultList[0];
	          DWRUtil.setValue("notebookCreattime",oaNotebook.oaNotebookCreattime);
	          DWRUtil.setValue("notebookContext",oaNotebook.oaNotebookContext);
	      }
	  }
	}
</script>
</head>
  <body class="inputdetail">
   <div class="requdivdetail"><label>查看帮助:&nbsp;便签明细查看</label></div>
	<div class="detailtitle">便签明细</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">添加时间</th>
	<td id="notebookCreattime"  class="detailtabletd"></td>
	</tr>
	
	<tr>
	<th>便签内容</th>
	<td id="notebookContext" class="detailtabletd">
	</td>
	</tr>
	</table>
<br/>

</body>
</html>