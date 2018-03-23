<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>用户编组</title>
<%
int row =15;
String gid = request.getParameter("gid");
 %>
</head>
<body class="inputcls">
<input type="hidden" id="employeename">
<input type="hidden" id="userid">
<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。。</label></div>
	<div class="detailtitle">编组明细</div>
	<table class="detailtable" align="center">
	<tr>
	<th width="12%">组名称</th>
	<td id="groupname"></td>
	</tr>
	<tr>
	<th>组描述</th>
	<td id="grouptext"></td>
	</tr>
	<tr>
	<th>组用户</th>
	<td valign="top">
	<table  class='tablerowStyleColor'  cellSpacing='1' cellPadding='2' width='100%' align='center' border='0' id='userlist'>
		<tr>
			<td  class='tableTitle1' style="text-align: center;" >序号</td>
			<td  class='tableTitle1' style="text-align: center;">用户名称</td>
			<td  class='tableTitle1' style="text-align: center;">所在组信息</td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	if(<%=gid%>!=null){
		dwrSysProcessService.getSysUserGroupByPk(<%=gid%>,setpagevalue);
	}
}

function setpagevalue(data){
	if(data!=null){
		var tmp =data.resultList[0];
		DWRUtil.setValue("groupname",tmp.groupName);
		DWRUtil.setValue("grouptext",tmp.groupDecp);
		var userids = "";
		for(var i=0;i<tmp.detailList.length;i++){
			userids+=tmp.detailList[i].userId+",";
		}
		DWRUtil.setValue("userid",userids);
		dwrSysProcessService.getEmployeeNameByUserIds(userids,setemployeenames);
	}
}

function setemployeenames(data){
	if(data!=null&&data.resultList.length>0){
		var names ="";
		for(var i=0;i<data.resultList.length;i++){
			names+=data.resultList[i]+",";
		}
		DWRUtil.setValue("employeename",names);
		getusermsg();
	}
}

function getusermsg(){
	var userid =document.getElementById("userid").value;
	var empname =document.getElementById("employeename").value;
	dwrSysProcessService.getUserGroupDetailList(userid,empname,false,getcallback);
}


function getcallback(data){
	var tab = document.getElementById("userlist");
	if(data.success==true){
		 var rlen = tab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			tab.deleteRow(i);
		}
		if(data.resultList.length > 0){
			for ( var i = 0; i < data.resultList.length; i++) {
				var users = data.resultList[i];
				var otr = tab.insertRow(-1);
		        var td1=document.createElement("td");
		        td1.innerHTML = i+1;
		        var td2=document.createElement("td");
		        td2.innerHTML =users[1];
		        var td3=document.createElement("td");
				td3.innerHTML =users[2];
				
		        otr.appendChild(td1);
		        otr.appendChild(td2);
		        otr.appendChild(td3);
	        }
        }
	}else{
		alertmsg(data);
	}
}
</script>
</body>
</html>