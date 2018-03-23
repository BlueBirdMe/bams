<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员明细</title>
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
		    DWRUtil.setValue("hrmEmployeeBirthday",employee.hrmEmployeeBirthday);
		    
		    var sexName ="";
		    if(employee.hrmEmployeeSex == <%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		       sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%>';
		    }else{
		      sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%>';
		    }
		    
		    DWRUtil.setValue("hrmEmployeeSex",sexName);
		    if(employee.hrmEmployeeMarriageName != null){
		    	DWRUtil.setValue("hrmEmployeeMarriage",employee.hrmEmployeeMarriageName.libraryInfoName);
		    }
		    
		    if(employee.hrmEmployeePoliticsName != null){
		    	DWRUtil.setValue("hrmEmployeePolitics",employee.hrmEmployeePoliticsName.libraryInfoName);
		    }
		    
		    if(employee.hrmEmployeeNationalityName != null){
		    	DWRUtil.setValue("hrmEmployeeNationality",employee.hrmEmployeeNationalityName.libraryInfoName);
		    }
		    
		    if(employee.hrmEmployeeBloodTypeName != null){
		    	DWRUtil.setValue("hrmEmployeeBloodType",employee.hrmEmployeeBloodTypeName.libraryInfoName);
		    }
		    
		    DWRUtil.setValue("hrmEmployeeHeight",employee.hrmEmployeeHeight);
		    DWRUtil.setValue("hrmEmployeeWeight",employee.hrmEmployeeWeight);
		    DWRUtil.setValue("hrmEmployeeSchool",employee.hrmEmployeeSchool);
		    DWRUtil.setValue("hrmEmployeeProfessional",employee.hrmEmployeeProfessional);
		    
		    if(employee.hrmEmployeeDegreeName != null){
		    	DWRUtil.setValue("hrmEmployeeDegree",employee.hrmEmployeeDegreeName.libraryInfoName);
		    }
		    
		    DWRUtil.setValue("hrmEmployeeIdentitycard",employee.hrmEmployeeIdentitycard);
		    DWRUtil.setValue("hrmEmployeeHometown",employee.hrmEmployeeHometown);
		    DWRUtil.setValue("hrmEmployeeHousePhone",employee.hrmEmployeeHousePhone);
		    DWRUtil.setValue("hrmEmployeeHouseAddress",employee.hrmEmployeeHouseAddress);
		    DWRUtil.setValue("hrmEmployeeMobileTele",employee.hrmEmployeeMobileTele);
		    DWRUtil.setValue("hrmEmployeeWorkTele",employee.hrmEmployeeWorkTele);
		    DWRUtil.setValue("hrmEmployeeInTime",employee.hrmEmployeeInTime);
		    
		    if(employee.hrmEmployeePatternName != null){
		    	DWRUtil.setValue("hrmEmployeePatternId",employee.hrmEmployeePatternName.libraryInfoName); 
		    }
		    
		    if(employee.hrmEmployeeWorkarea != null){
		    	DWRUtil.setValue("hrmEmployeeWorkareaid",employee.hrmEmployeeWorkarea.hrmAreaName); 
		    }
		    
		    
		    DWRUtil.setValue("hrmEmployeeEmail",employee.hrmEmployeeEmail);
		    DWRUtil.setValue("hrmEmployeeUrgentPreson",employee.hrmEmployeeUrgentPreson);
		    DWRUtil.setValue("hrmEmployeeUrgentPhone",employee.hrmEmployeeUrgentPhone);
		    DWRUtil.setValue("hrmEmployeeAdderss",employee.hrmEmployeeAdderss);
		    DWRUtil.setValue("hrmEmployeeDeptext",employee.hrmDepartment.hrmDepName);
		    DWRUtil.setValue("hrmEmployeePostIdtext",employee.hrmEmployeePost.hrmPostName);
            DWRUtil.setValue("hrmEmployeeWorkTime",employee.hrmEmployeeWorkTime);
		    DWRUtil.setValue("hrmPartPosttext",employee.hrmPartPostName);
		    if(employee.hrmEmployeeAppendid != null){
		    	document.getElementById("hrmEmployeeAppendid").innerHTML = employee.hrmEmployeeAppendid;
		    }

             //附件显示为下载
            if(employee.hrmOtherAttachmen != null && employee.hrmOtherAttachmen.length>0){
			     Sys.showDownload(employee.hrmOtherAttachmen,"hrmOtherAttachmen");
			}
			//照片显示
			var face = document.getElementById("hrmEmployeeImageInfoId");
			face.src+="&imgId="+employee.hrmEmployeeImageInfoId;
 		}
 	}
}

function sendsms(){
	Sys.close();
	window.parent.mainframe.location ="<%=contextPath%>/erp/personal_work/online_sms_send.jsp?employeepk='<%=employeepk%>'";
}



function sendEmail(){
	Sys.close();
	window.parent.mainframe.location ="<%=contextPath%>/erp/personal_work/online_email_send.jsp?employeepk='<%=employeepk%>'";
}


</script>
</head>
<body class="inputdetail">
    <div class="requdivdetail">
			<label>
				查看帮助:&nbsp; 显示人员相关信息，点击附件可下载附件！
			</label>
    </div>
    <div class="detailtitle">
 			人员信息
    </div>
	<table class="detailtable">
	<tr>
		<th width="15%">员工编号</th>
		<td id="hrmEmployeeCode" class="detailtabletd" width="25%"></td>
		<th rowspan="5"></th>
		<td rowspan="5" style="text-align: center;"><file:imgshow  id="hrmEmployeeImageInfoId" width="120"></file:imgshow></td>
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
		<th>出生日期</th>
		<td id="hrmEmployeeBirthday" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>身份证号码</th>
		<td id="hrmEmployeeIdentitycard" class="detailtabletd"></td>
		<th rowspan="5" style="text-align: right;padding-bottom: 65px;"></th>
		<td class="attachtd" rowspan="5">
			<div class="attachdiv">
				<div class="attachtitle">
					附件下载
				</div>
				<div class="attachdownload" id="hrmOtherAttachmen"></div>
			</div>
		</td>
	</tr>
	<tr>
		<th>部&nbsp;&nbsp;门</th>
		<td id="hrmEmployeeDeptext" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>工作岗位</th>
		<td id="hrmEmployeePostIdtext" class="detailtabletd"></td>
		
	</tr>
	<tr>
		<th>兼职岗位</th>
		<td id="hrmPartPosttext" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>工作地区</th>
		<td id="hrmEmployeeWorkareaid" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>用工形式</th>
		<td id="hrmEmployeePatternId" class="detailtabletd"></td>
		<th>入职日期</th>
		<td id="hrmEmployeeInTime" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>移动电话</th>
		<td id="hrmEmployeeMobileTele" class="detailtabletd"></td>
		<th>转正日期</th>
		<td id="hrmEmployeeWorkTime" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>工作电话</th>
		<td id="hrmEmployeeWorkTele" class="detailtabletd"></td>
		<th>当前住址</th>
		<td id="hrmEmployeeAdderss" class="detailtabletd"></td>
		
	</tr>
	<tr>
		<th>民&nbsp;&nbsp;族</th>
		<td id="hrmEmployeeNationality" class="detailtabletd"></td>
		<th>政治面貌</th>
		<td id="hrmEmployeePolitics" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>血&nbsp;&nbsp;型</th>
		<td id="hrmEmployeeBloodType" class="detailtabletd"></td>
		<th>学&nbsp;&nbsp;历</th>
		<td id="hrmEmployeeDegree" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>体&nbsp;&nbsp;重</th>
		<td id="hrmEmployeeWeight" class="detailtabletd"></td>
		<th>身&nbsp;&nbsp;高</th>
		<td id="hrmEmployeeHeight" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>毕业学校</th>
		<td id="hrmEmployeeSchool" class="detailtabletd"></td>
		<th>专&nbsp;&nbsp;业</th>
		<td id="hrmEmployeeProfessional" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>籍&nbsp;&nbsp;贯</th>
		<td id="hrmEmployeeHometown" class="detailtabletd"></td>
		<th>家庭电话</th>
		<td id="hrmEmployeeHousePhone" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>家庭地址</th>
		<td id="hrmEmployeeHouseAddress" class="detailtabletd"></td>
		<th>婚姻状况</th>
		<td id="hrmEmployeeMarriage" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>邮箱地址</th>
		<td id="hrmEmployeeEmail" class="detailtabletd"></td>
		<th>紧急联系人</th>
		<td id="hrmEmployeeUrgentPreson" class="detailtabletd"></td>
	</tr>
	<tr>
		<th>紧急联系电话</th>
		<td id="hrmEmployeeUrgentPhone" class="detailtabletd"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<th>内&nbsp;&nbsp;容</th>
		<td colspan="3" id="hrmEmployeeAppendid" class="detailtabletd">
	</td>
	</tr>
	</table>
</body>
</html>