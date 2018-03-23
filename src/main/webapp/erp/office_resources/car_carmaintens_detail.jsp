<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆申请明细查看</title>
<%
    String cid = request.getParameter("pk");
    
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
   	
	  dwrOfficeResourcesService.getCarmaintenByPk(<%=cid%>,setCaruseinfo);
}
function setCaruseinfo(data){
	  if(data.success&&data.resultList.length > 0){
 		 var caruse = data.resultList[0];
 		 DWRUtil.setValue("applyEmployee",caruse.applyEmployee.hrmEmployeeName);
 		 DWRUtil.setValue("maintainDate",caruse.maintainDate);
	     DWRUtil.setValue("maintainMoney",caruse.maintainMoney);
	     DWRUtil.setValue("maintainAppendnews",caruse.maintainAppendnews);
            DWRUtil.setValue("maintiantype",caruse.libraryName);   
 	}else{
 		alertmsg(data);
 	}
}
</script>
</head>
  <body>
   <fieldset>
	<legend>维护车辆明细</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">操作人</th>
	<td id="applyEmployee"></td>
		<th width="15%">维修金额</th>
	<td id="maintainMoney"></td>
	</tr>
	<tr>
	<th width="15%">维修类型</th>
	<td id="maintiantype"></td>
		<th width="15%">维修时间</th>
	<td id="maintainDate"></td>
	
	</tr>
	<tr>
	<th width="15%">维修原因
	</th>
	<td id="maintainAppendnews" colspan="3"></td>
	</tr>
	
	</table>
	</div>
</fieldset>
</body>
</html>
