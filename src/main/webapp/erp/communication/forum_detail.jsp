<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论坛版块明细查看</title>
<%
    String forumId = request.getParameter("forumId");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>   
<script type="text/javascript">
     window.onload = function(){
		dwrOaCommunicationService.getForumByid(<%=Integer.parseInt(forumId)%>,setPageValue);
	}
	function setPageValue(data){
	 if(data != null){
	      if(data.resultList.length>0){
	          var forum = data.resultList[0];
	          DWRUtil.setValue("forumName",forum.oaForumName);
	          DWRUtil.setValue("createTime",forum.oaForumTime);
	          DWRUtil.setValue("forumAdmin",forum.forumAdminName);
	         
	          if(forum.employee!=null){
	          	document.getElementById("createMan").innerHTML = forum.employee.hrmEmployeeName;
	          }else{
	          	document.getElementById("createMan").innerHTML ="&nbsp;";
	          }
	          document.getElementById("forumText").innerHTML = forum.oaForumText;
	          
	          //放入图片
	          var face = document.getElementById("oaForumImage");
				  face.src+="&imgId="+forum.oaForumImage;
	      }
	  }
	}
</script>
</head>
  <body class="inputdetail">
    <div class="requdivdetail"><label>查看帮助:&nbsp;您可在此查看版块明细。</label></div>
	<div class="detailtitle">论坛版块明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">版块名称</th>
		<td id="forumName" class="detailtabletd"></td>
		<td ></td>
		<td rowspan="4" align="center">
		<file:imgshow  id="oaForumImage" width="128" ></file:imgshow>
		</td>
	</tr>
	<tr>
		<th>版 主</th>
		<td id="forumAdmin" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>创建人</th>
		<td id="createMan" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>创建时间</th>
		<td id="createTime" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>版块描述</th>
		<td colspan="3"  id="forumText" class="detailtabletd">
		</td>
	</tr>
	</table>
</body>
</html>
