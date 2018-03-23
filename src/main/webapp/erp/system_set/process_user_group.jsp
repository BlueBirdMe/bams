<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置用户组</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrApproveProcessService.js"></script>
<%
    String empId =request.getParameter("id");
%>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处设置用户所属用户组！');
	dwrHrmEmployeeService.getEmployeeByPK('<%=empId%>',setEmployeeinfo);
	dwrApproveProcessService.getMembership('<%=empId%>',setProcessUserGroup);
}

function setEmployeeinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var employee = data.resultList[0];
 			DWRUtil.setValue("name",employee.hrmEmployeeName);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function setProcessUserGroup(data){
    setCheckboxValueByName("groupIds",data);
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "groupMust";
	warnInit(warnArr);
     var bl = validvalue('helpTitle');
     if(bl){
          var cbValue = getCheckboxValueByName("groupIds");
          if(cbValue == ""){
          	setMustWarn("groupMust","请选择相关用户组！");
          	return false;
          }
		  Btn.close();
          dwrApproveProcessService.saveMembership('<%=empId%>',cbValue.split(","),saveCallback);
	 }
}

function saveCallback(data){
    Btn.open();
    if(data.success){
		alertmsg(data,"returnload();");
	}else{
		alertmsg(data);
	}
}

function returnload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}


</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">设置用户组</div>
	    <table class="inputtable" border="0">
	    	<tr>
				<th>用户姓名</th>
				<td><label id="name"></label></td>
			</tr>
	    
		    <tr>
				<th>选择用户所属组</th>
				<td>
					<%=UtilTool.getProcessGroups(this.getServletContext(),request,"groupIds") %>
					<label id="groupMust"></label>
				</td>
			</tr>
	    </table>
	</div>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存部门信息" /></td>
		<td style="width: 20px;"></td>
		<td id="btncancel"><btn:btn onclick="window.parent.MoveDiv.close()" value="取 消 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>