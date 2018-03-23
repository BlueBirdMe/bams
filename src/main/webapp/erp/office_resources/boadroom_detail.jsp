<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议室明细查看</title>
<%
    String nid = request.getParameter("nid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
   init();
	
}
function init(){
   dwrOfficeResourcesService.getBoadroomByPk(<%=nid%>,setPageValue);
}


function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var tmp = data.resultList[0];
          DWRUtil.setValue("oaBoardroomName",tmp.oaBoardroomName);
          DWRUtil.setValue("oaBoardroomCapacity",tmp.oaBoardroomCapacity);
          DWRUtil.setValue("oaBoardroomAddress",tmp.oaBoardroomAddress);
          document.getElementById("oaBoardroomDescribe").innerHTML = tmp.oaBoardroomDescribe;
      }
  }
}
</script>
</head>
<body class="inputdetail">

	<div class="requdivdetail"><label>查看帮助:&nbsp;查看会议室的详细信息。</label></div>
	<div class="detailtitle">会议室明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">会议室名称</th>
		<td id="oaBoardroomName" class="detailtabletd" ></td>
		<td class="attachtd" rowspan="5">
		<!--
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="noticeAcce"></div>				
			</div>
		-->
		</td>
	</tr>
	<tr>
		<th width="15%">可容纳人数</th>
		<td id="oaBoardroomCapacity"  class="detailtabletd"></td>
	</tr>
	<tr>
		<th width="15%">会议室地址</th>
		<td id="oaBoardroomAddress"  class="detailtabletd"></td>
	</tr>
	<tr>
		<th>会议室描述</th>
		<td colspan="2"  id="oaBoardroomDescribe" class="detailtabletd" >
	</td>
	</tr>
	
	</table>
</body>

</html>
