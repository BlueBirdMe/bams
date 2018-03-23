<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书类别明细查看</title>
<%
    String bid = request.getParameter("bid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
      init();
	
}
function init(){
   dwrOfficeResourcesService.getBooktypeByPk(<%=bid%>,setPageValue);
}


function setPageValue(data){
 if(data != null){ 
      if(data.resultList.length>0){
          var tmp = data.resultList[0];
          DWRUtil.setValue("oaBooktypeName",tmp.oaBooktypeName);
          document.getElementById("oaBooktypeRemark").innerHTML = tmp.oaBooktypeRemark;
      }
  }
}
</script>
</head>

<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;图书类别的详细信息查看</label></div>
	<div class="detailtitle">图书类别明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">类别名称</th>
		<td id="oaBooktypeName" class="detailtabletd"></td>
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
	<th width="15%">备&nbsp;&nbsp;注</th>
	<td id ="oaBooktypeRemark" class="detailtabletd"></td>
	</tr>
	
	</table>

</body>
</html>
