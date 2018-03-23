<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    String did = request.getParameter("depid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrHrmEmployeeService.getDepartmentByPK(<%=Integer.parseInt(did)%>,setPageValue);
}

function setPageValue(data){
   if(data != null){
      if(data.resultList.length>0){
          var department = data.resultList[0];
          DWRUtil.setValue("departmentName",department.hrmDepName);
          DWRUtil.setValue("departmentCode",department.hrmDepCode);
          DWRUtil.setValue("departmentEnglishName",department.hrmDepEngname);
          
          if(department.employee != null){
             DWRUtil.setValue("departmentManager",department.employee.hrmEmployeeName);
          }else{
             DWRUtil.setValue("departmentManager","<无>");
          }
          
          if(department.parentDepartment != null){
             document.getElementById("upDepartment").innerHTML = department.parentDepartment.hrmDepName;
          }else{
             document.getElementById("upDepartment").innerHTML = "<无>";
          }        
            
          DWRUtil.setValue("departmentText",department.hrmDepDesc);
      }
   }
   
}
</script>
<title>机构明细查看</title>
</head>
<body class="inputdetail">
    <div class="requdivdetail">
		<label>
			查看帮助:&nbsp; 显示组织机构相关信息！。
		</label>
    </div>
    <div class="detailtitle">机构明细</div>
	<table class="detailtable">
		<tr>
			<th width="15%">机构名称</th>
			<td id="departmentName" class="detailtabletd" ></td>
			<td class="attachtd" rowspan="6"></td>
		</tr>
		<tr>
			<th>机构编码</th>
			<td id="departmentCode" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>机构英文名</th>
			<td id="departmentEnglishName" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>上级机构</th>
			<td id="upDepartment" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>机构经理</th> 
			<td id="departmentManager" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>机构描述</th>
			<td colspan="3"  id="departmentText" class="detailtabletd"></td>
		</tr>
	</table>
</body>
</html>
