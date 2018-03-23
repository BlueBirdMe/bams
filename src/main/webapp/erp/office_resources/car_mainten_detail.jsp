<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆维护明细查看</title>
<%
    String cid = request.getParameter("cid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
      init();
	
}
function init(){
   dwrOfficeResourcesService.getCarmaintenByPk(<%=cid%>,setPageValue);
}


function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var tmp = data.resultList[0];
          DWRUtil.setValue("oaMaintenCards",tmp.oaMaintenCards);
          DWRUtil.setValue("oaMaintenName",tmp.oaMaintenName);
          DWRUtil.setValue("oaMaintenEmp",tmp.oaMaintenEmp);
          DWRUtil.setValue("oaMaintenDate",tmp.oaMaintenDate);
          DWRUtil.setValue("oaMaintenType",tmp.oaMaintenType);
          DWRUtil.setValue("oaMaintenCharge",tmp.oaMaintenCharge);
          document.getElementById("oaMaintenCause").innerHTML = tmp.oaMaintenCause;
          document.getElementById("oaMaintenRemark").innerHTML = tmp.oaMaintenRemark;
      }
  }
}
</script>
</head>
  <body>
   <fieldset>
	<legend>车辆维护明细</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">车牌号</th>
	<td id="oaMaintenCards"></td>
	<th width="15%">车辆名称</th>
	<td id="oaMaintenName"></td>
	</tr>
	<tr>
	<th width="15%">维护人</th>
	<td id="oaMaintenEmp"></td>
	<th width="15%">维护时间</th>
	<td id="oaMaintenDate"></td>
	</tr>
	<tr>
	<th width="15%">维护类型</th>
	<td id="oaMaintenType"></td>
	<th width="15%">维护费用</th>
	<td id=oaMaintenCharge></td>
	</tr>
	<tr>
	<th>维护原因</th>
	<td colspan="3"  id="oaMaintenCause">
	</td>
	</tr>
	<tr>
	<th>备  注</th>
	<td colspan="3"  id="oaMaintenRemark">
	</td>
	</tr>
	</table>
	</div>
</fieldset>
</body>
</html>
