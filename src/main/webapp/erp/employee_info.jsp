<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request); 
HrmPost mainPost=user.getMainPost();
if(mainPost==null){
	mainPost = new HrmPost();
}


HrmEmployee empinfo =  user.getEmployeeInfo();
List<HrmPost> partPosts=user.getPartPosts();
String Posts ="";
for(HrmPost post : partPosts){
	if(post!=null&&post.getHrmPostName()!=null){
   		Posts += post.getHrmPostName()+",";
   	}
}

 %>
</head>
<body class="inputdetail">

	<div class="requdivdetail"><label>小贴士：&nbsp;您可以在此查看自己的用户信息</label></div>

	<div class="detailtitle">个人信息明细</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">员工编号</th>
	<td id="hrmEmployeeCode" class="detailtabletd"><%=empinfo.getHrmEmployeeCode()==null?"":empinfo.getHrmEmployeeCode()%></td>
	<td rowspan="5"></th>
	<td  rowspan="5">
	<file:imgshow  id="hrmEmployeeImageInfoId" width="135" imageId="<%=empinfo.getHrmEmployeeImageInfoId()==null?-1:empinfo.getHrmEmployeeImageInfoId() %>"></file:imgshow>
	</td>
	</tr>
	<tr>
	<th>姓名</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeName()==null?"":empinfo.getHrmEmployeeName()%></td>
	</tr>
	<tr>
	<th>英文名</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeEngname()==null?"":empinfo.getHrmEmployeeEngname()%></td>
	</tr>
	<tr>
	<th>性别</th>
	<td class="detailtabletd"><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(Integer.parseInt(empinfo.getHrmEmployeeSex()==null?"1":empinfo.getHrmEmployeeSex()))%></td>
	</tr>
	<tr>
	<th>出生日期</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeBirthday()==null?"":empinfo.getHrmEmployeeBirthday()%></td>
	</tr>
	<tr>
	<th>身份证号码</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeIdentitycard()==null?"":empinfo.getHrmEmployeeIdentitycard()%></td>
	<th>移动电话</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeMobileTele()==null?"":empinfo.getHrmEmployeeMobileTele()%></td>
	</tr>
	<tr>
	<th>部门</th>
	<td class="detailtabletd"><%=user.getEmployeeDeptName()==null?"":user.getEmployeeDeptName() %></td>
	<th>工作电话</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeWorkTele()==null?"":empinfo.getHrmEmployeeWorkTele()%></td>
	</tr>
	<tr>
	<th>工作岗位</th>
	<td class="detailtabletd"><%=mainPost.getHrmPostName()==null?"":mainPost.getHrmPostName() %></td>
	<th>入职日期</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeInTime()==null?"":empinfo.getHrmEmployeeInTime()%></td>
	</tr>
	<tr>
	<th>兼职岗位</th>
	<td class="detailtabletd"><%=Posts%> </td>
	<th>转正日期</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeWorkTime()==null?"":empinfo.getHrmEmployeeWorkTime()%></td>
	</tr>
	<tr>	
	<th>用工形式</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeePatternId()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong(empinfo.getHrmEmployeePatternId())).getLibraryInfoName()%></td>
	</tr>
	<tr>
	<th>民族</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeNationality()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong( empinfo.getHrmEmployeeNationality())).getLibraryInfoName()%></td>
	<th>政治面貌</th>
	
	<td class="detailtabletd"><%=empinfo.getHrmEmployeePolitics()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong( empinfo.getHrmEmployeePolitics())).getLibraryInfoName()%></td>
	</tr>
	<tr>
	<th>血型</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeBloodType()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong( empinfo.getHrmEmployeeBloodType())).getLibraryInfoName()%></td>
	<th>学历</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeDegree()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong( empinfo.getHrmEmployeeDegree())).getLibraryInfoName()%></td>
	</tr>
	<tr>
	
	<th>体重</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeWeight()==null?"":empinfo.getHrmEmployeeWeight()+"kg"%></td>
	<th>身高</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeHeight()==null?"":empinfo.getHrmEmployeeHeight()+"cm"%></td>
	</tr>
	<tr>
	<th>毕业学校</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeSchool()==null?"":empinfo.getHrmEmployeeSchool()%></td>
	<th>专业</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeProfessional()==null?"":empinfo.getHrmEmployeeProfessional()%></td>
	</tr>
	<tr>
	<th>籍贯</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeHometown()==null?"":empinfo.getHrmEmployeeHometown()%></td>
	<th>家庭电话</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeHousePhone()==null?"":empinfo.getHrmEmployeeHousePhone()%></td>
	</tr>
	<tr>
	<th>家庭地址</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeHouseAddress()==null?"":empinfo.getHrmEmployeeHouseAddress()%></td>
	<th>婚姻状况</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeMarriage()==null?"":UtilTool.getLibraryInfoByPk(this.getServletContext(),request, Long.parseLong( empinfo.getHrmEmployeeMarriage())).getLibraryInfoName()%></td>
	</tr>
	<tr>
	<th>邮箱地址</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeEmail()==null?"":empinfo.getHrmEmployeeEmail()%></td>
	<th>紧急联系人</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeUrgentPreson()==null?"":empinfo.getHrmEmployeeUrgentPreson()%></td>
	</tr>
	<tr>
	<th>紧急联系电话</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeUrgentPhone()==null?"":empinfo.getHrmEmployeeUrgentPhone()%></td>
	<th>当前住址</th>
	<td class="detailtabletd"><%=empinfo.getHrmEmployeeAdderss()==null?"":empinfo.getHrmEmployeeAdderss()%></td>
	</tr>
	
	</table>
	<br/>

</body>
</html>