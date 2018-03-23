<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>创建角色</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String uid = request.getParameter("uid");
String show = request.getParameter("show");
SessionUser sessionUser = (SessionUser)LoginContext.getSessionValueByLogin(request);
List<SysMethodInfo> companyMethods = sessionUser.getCompanyMethodsList();
Set<String> methods=UtilTool.getSysUserMethodAllByUid(this.getServletContext(),request,Integer.parseInt(uid),show);

 %>
</head>
<body  class="inputdetail">
<div class="requdivdetail">
	<label>
		<%
		if (show==null||show.length()==0) {
		 %>
			查看帮助:&nbsp;用户附加权限(不包含绑定在角色上的权限)。
		<%}else{ %>
			查看帮助:&nbsp;用户所有权限(包含绑定在角色上的权限)。
		<%} %>
	</label>
</div>
	<div class="detailtitle">用户信息</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">用户信息:</th>
	<td id="username" class="detailtabletd"></td>
	<th  width="15%">用户角色:</th>
	<td id="userrole"  class="detailtabletd"></td>
	</tr>
	<tr>
	<th>部&nbsp;门:</th>
	<td id="deptname" class="detailtabletd"></td>
	<th>部门角色:</th>
	<td id="deptrole" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>主岗位:</th>
	<td id="mainpostname" class="detailtabletd"></td>
	<th>主岗位角色:</th>
	<td id="mainpostrole" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>兼职岗位:</th>
	<td id="partpostname" class="detailtabletd"></td>
	<th>兼职岗位角色:</th>
	<td id="partpostrole" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>所在组:</th>
	<td id="groupname" class="detailtabletd"></td>
	<th>所在组角色:</th>
	<td id="grouprole" class="detailtabletd"></td>
	</tr>
	<tr>
	</table>
	</br>
		<center><div class="linediv"></div></center>
	</br>
	<div class="detailtitle">权限列表</div>
	<div style="text-align: center;">
	<%
	if(companyMethods!=null&&companyMethods.size()>0){
	 %>
	<DIV class="tabdiv" style="width: 100%;margin: 5px;overflow: hidden;" id="tabdiv2" >
		<UL class="tags">
		<%
	for(int i=0;i<companyMethods.size();i++){
		SysMethodInfo sysMethod =companyMethods.get(i);
		String tmpck="";
		if(UtilTool.sysMethodIsCheckByMethodSet(sysMethod.getPrimaryKey(),methods)){
			tmpck="checked ='checked'";
		}
	 %>
		<LI><A onClick="tab2.selectTag(this);" href="javascript:void(0)" style="padding-top: 4px;">
		<input type="checkbox" <%=tmpck %> id="<%=sysMethod.getMethodSign() %>" name="ck_<%=sysMethod.getMethodSign()%>" value="<%=sysMethod.getPrimaryKey() %>" disabled="disabled"><%=sysMethod.getMethodInfoName() %>
		</A></LI>
		<%} %>
		</UL>
		<DIV class="tagContentdiv" style="overflow: hidden;width: 99%">
			<%
			for(int i=0;i<companyMethods.size();i++){
			SysMethodInfo sysMethod =companyMethods.get(i);
			 %>
			<DIV class="tagContent" id="tag<%=i %>" style="overflow: hidden;">
			<%
			List<SysMethodInfo> levelist1 = UtilTool.getSysMethodMapByMethodSet(this.getServletContext(),request,sysMethod.getPrimaryKey(),methods,EnumUtil.SYS_METHOD_LEVEL.ONE.value);
			List<SysMethodInfo> levelist2 = UtilTool.getSysMethodMapByMethodSet(this.getServletContext(),request,sysMethod.getPrimaryKey(),methods,EnumUtil.SYS_METHOD_LEVEL.TWO.value);
			List<SysMethodInfo> levelist3 = UtilTool.getSysMethodMapByMethodSet(this.getServletContext(),request,sysMethod.getPrimaryKey(),methods,EnumUtil.SYS_METHOD_LEVEL.THREE.value);
			 if(levelist1!=null&&levelist1.size()>0){
			 %>
			<table width="99%" cellpadding="4" cellspacing="0" border="0"  style="line-height: 20px;" >
			<%
			for(int j=0;j<levelist1.size();j++){
			SysMethodInfo levlMethod =levelist1.get(j);
			String chk1 ="";
			if(levlMethod.isIschecked()){chk1="checked ='checked'";}
			%>
			<tr>
				<td style="width: 12%;border-bottom: 1px dotted #d4d4d4;" nowrap="nowrap">
				<input type="checkbox"  <%=chk1 %> value="<%=levlMethod.getPrimaryKey() %>" upvalue ="<%=levlMethod.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign()%>" disabled="disabled" id="chk_<%=levlMethod.getPrimaryKey() %>"><label for="chk_<%=levlMethod.getPrimaryKey() %>"><%=levlMethod.getMethodInfoName() %></label>
				</td>
				<td style="border-bottom: 1px dotted #d4d4d4;">
				<table width="100%" cellpadding="4" cellspacing="1" border="0"  style="line-height: 20px;" >
				 <%for(int a=0;a<levelist2.size();a++){ 
				 	SysMethodInfo levlMethod2 =levelist2.get(a);
					if(levlMethod2.getLevelUnit().trim().equals(levlMethod.getPrimaryKey())){
						String chk2 ="";
						if(levlMethod2.isIschecked()){chk2="checked ='checked'";}
				 	%>
				 	<tr>
				 	<td style="width: 12%" nowrap="nowrap">
						<input type="checkbox" <%=chk2 %> value="<%=levlMethod2.getPrimaryKey() %>" upvalue ="<%=levlMethod2.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign() %>" disabled="disabled" id="chk_<%=levlMethod2.getPrimaryKey() %>"><label for="chk_<%=levlMethod2.getPrimaryKey() %>"><%=levlMethod2.getMethodInfoName() %></label>
					</td>
					<td>
					<table cellpadding="3" cellspacing="1" border="0"  style="line-height: 20px;" >
					<%
					int td = 0;
					for(int b=0;b<levelist3.size();b++){ 
				 	SysMethodInfo levlMethod3 =levelist3.get(b);
					if(levlMethod3.getLevelUnit().trim().equals(levlMethod2.getPrimaryKey())){
						String chk3="";
						if(levlMethod3.isIschecked()){chk3="checked ='checked'";}
				 	%>
				 	<%
				 	if(td%5==0){
				 	 %>
				 	<tr>
				 	<%} %>
				 	<td nowrap="nowrap" >
						<input type="checkbox" <%=chk3 %>  value="<%=levlMethod3.getPrimaryKey() %>" upvalue ="<%=levlMethod3.getLevelUnit() %>" name="ck_<%=sysMethod.getMethodSign() %>" disabled="disabled" id="chk_<%=levlMethod3.getPrimaryKey() %>"><label for="chk_<%=levlMethod3.getPrimaryKey() %>"><%=levlMethod3.getMethodInfoName() %></label>
					</td>
				 	<%
					td++;
					if (td > 0 && td % 5 == 0) {
					 %>
					 </tr>
					 <% } %>
				 	<%}
				 	} %>
				 	<!-- </tr> -->
				 	</table>
				 	</td>
				 	</tr>
				 <%}
				 } %>
				 </table>
				  </td>
			</tr>
			<%} %>
			</table>
			<%} %>
			</DIV>
			<%} %>
		</DIV>
	</DIV>
	<%} %>
	</div>

<br/>
<script type="text/javascript">
var tab2 = new SysTab('<%=contextPath%>',null,"tabdiv2");
window.onload=function(){
	useLoadingMassage();
	dwrSysProcessService.getUserMethodsInfoByUid(<%=uid%>,setpagevalue);
}
function setpagevalue(data){
	if(data!=null&&data.resultList.length>0){
		var tmp=data.resultList[0];
		document.getElementById("username").innerHTML = tmp.employeeName==null?"":tmp.employeeName;
		document.getElementById("userrole").innerHTML = tmp.employeeRole==null?"":tmp.employeeRole;
		document.getElementById("deptname").innerHTML = tmp.deptName==null?"":tmp.deptName;
		document.getElementById("deptrole").innerHTML = tmp.deptRole==null?"":tmp.deptRole;
		document.getElementById("mainpostname").innerHTML = tmp.mainPostName==null?"":tmp.mainPostName;
		document.getElementById("mainpostrole").innerHTML = tmp.mainPostRole==null?"":tmp.mainPostRole;
		document.getElementById("partpostname").innerHTML = tmp.partPostName==null?"":tmp.partPostName;
		document.getElementById("partpostrole").innerHTML = tmp.partPostRole==null?"":tmp.partPostRole;
		document.getElementById("groupname").innerHTML = tmp.groupName==null?"":tmp.groupName;
		document.getElementById("grouprole").innerHTML = tmp.groupRole==null?"":tmp.groupRole;
	}
}

</script>
</body>
</html>