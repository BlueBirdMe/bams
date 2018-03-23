<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线人员详情</title>
<%
	String employeepk =request.getParameter("employeepk");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrHrmEmployeeService.getEmployeeByPK('<%=employeepk%>',setEmployeeinfo);
}
	
function setEmployeeinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var employee = data.resultList[0];
 			DWRUtil.setValue("hrmEmployeeName",employee.hrmEmployeeName);
		    DWRUtil.setValue("hrmEmployeeEngname",employee.hrmEmployeeEngname);
		    DWRUtil.setValue("hrmEmployeeCode",employee.hrmEmployeeCode);
		    
		    var sexName ="";
		    if(employee.hrmEmployeeSex == <%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		       sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%>';
		    }else{
		      sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%>';
		    }
		    
		    DWRUtil.setValue("hrmEmployeeSex",sexName);
		    DWRUtil.setValue("hrmEmployeeWorkareaid",employee.hrmEmployeeWorkarea.hrmAreaName);
		    DWRUtil.setValue("hrmEmployeeDeptext",employee.hrmDepartment.hrmDepName);
		    DWRUtil.setValue("hrmEmployeePostIdtext",employee.hrmEmployeePost.hrmPostName);
		    DWRUtil.setValue("hrmPartPosttext",employee.hrmPartPostName);

         
			//照片显示
			var face = document.getElementById("hrmEmployeeImageInfoId");
			face.src+="&imgId="+employee.hrmEmployeeImageInfoId;
 		}
 	}
}

function sendsms(dialogId){
	var url = "<%=contextPath%>/erp/personal_work/online_sms_send.jsp?employeepk='<%=employeepk%>'";
	openMDITab(url);
	Sys.close(dialogId);
}

function sendEmail(dialogId){
	var url = "<%=contextPath%>/erp/personal_work/online_email_send.jsp?employeepk='<%=employeepk%>'";
	openMDITab(url);
	Sys.close(dialogId);
}


</script>
</head>
<body class="inputcls">
    <div class="requdivdetail">
			<label>
				查看帮助:&nbsp; 显示人员相关信息，点击附件可下载附件！
			</label>
    </div>
    <div class="detailtitle">
 			人员信息
    </div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">员工编号</th>
		<td id="hrmEmployeeCode" class="detailtabletd" width="25%"></td>
		<th rowspan="5" style="text-align: right;padding-top: 65px;"></th>
		<td  rowspan="5" style="text-align: center;padding-right:180px;"><file:imgshow  id="hrmEmployeeImageInfoId" width="120"></file:imgshow></td>
	</tr>
	<tr>
		<th>姓&nbsp;&nbsp;名</th>
		<td id="hrmEmployeeName" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>英&nbsp;文&nbsp;名</th>
		<td id="hrmEmployeeEngname" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>性&nbsp;别</th>
		<td id="hrmEmployeeSex" class="detailtabletd"></td>
	</tr>
	
	<tr>
		<th>部&nbsp;&nbsp;门</th>
		<td id="hrmEmployeeDeptext" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>工作地区</th>
		<td id="hrmEmployeeWorkareaid" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>工作岗位</th>
		<td id="hrmEmployeePostIdtext" class="detailtabletd"></td>
		
	</tr>
	<tr>
		<th>兼职岗位</th>
		<td id="hrmPartPosttext" class="detailtabletd"></td>
	</tr>
	</table>
</body>
</html>