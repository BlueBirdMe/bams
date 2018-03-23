<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位明细查看</title>
<%
    String pid = request.getParameter("pid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
      dwrHrmEmployeeService.getPostByPk(<%=pid%>,setPageValue);
}
	
	
function setPageValue(data){
	if(data != null && data.resultList.length>0){
		var tmp = data.resultList[0];
		DWRUtil.setValue("hrmPostName",tmp.hrmPostName);
		DWRUtil.setValue("hrmPostEngname",tmp.hrmPostEngname);
		
		if(tmp.hrmUpPost!=null){
 			DWRUtil.setValue("hrmPostUpName",tmp.hrmUpPost.hrmPostName);
		}
		
		if(tmp.mangerEmployee!=null){
			DWRUtil.setValue("hrmPostMang",tmp.mangerEmployee.hrmEmployeeName);
		}
		
		DWRUtil.setValue("hrmPostDesc",tmp.hrmPostDesc);
  	}
}
</script>
</head>
  <body class="inputdetail">
		<div class="requdivdetail">
			<label>
				查看帮助:&nbsp;显示岗位相关基本信息！
			</label>
		</div>
		<div class="detailtitle">
			岗位明细
		</div>
		<table class="detailtable">
			<tr>
				<th width="15%">
					岗位名称
				</th>
				<td id="hrmPostName" class="detailtabletd"></td>
				<td class="attachtd" rowspan="4"></td>
			</tr>
			<tr>
				<th>
					岗位英文名称
				</th>
				<td id="hrmPostEngname" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>
					上级岗位
				</th>
				<td id="hrmPostUpName" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>
					岗位负责人
				</th>
				<td id="hrmPostMang" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>
					岗位描述
				</th>
				<td id="hrmPostDesc" class="detailtabletd" colspan="2">
				</td>
			</tr>
		</table>
		<br />
	</body>
</html>
