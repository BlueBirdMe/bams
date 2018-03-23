<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增组织机构</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<%
    String departmentpk =request.getParameter("departmentpk");
%>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处添加您想新增的部门！');
	saveOredit();
			

    //第一个输入框获取焦点
    document.getElementById("hrmDepCode").focus();
}

function getDepartmentinfo(){
	var department = new Object();
   	if(<%=departmentpk%> != null){
      department.primaryKey = <%=departmentpk%>;
    }
	department.hrmDepCode = DWRUtil.getValue("hrmDepCode");
	department.hrmDepName = DWRUtil.getValue("hrmDepName");
	department.hrmDepEngname = DWRUtil.getValue("hrmDepEngname");
	department.hrmDepUpid = DWRUtil.getValue("hrmDepartmentId");
	department.hrmDepDesc = DWRUtil.getValue("hrmDepDesc");
	return department;
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "hrmDepNameMust";
	warnArr[1] = "hrmDepDescMsg";
	//清空所有信息提示
	warnInit(warnArr);
     var bl = validvalue('helpTitle');
     if(bl){
     	  
   		  var tmp = DWRUtil.getValue("hrmDepDesc");
   		  if(tmp.length>200){
   		  	setMustWarn("hrmDepDescMsg","部门描述不能大于200个字符!");
   		  	return;
   		  }
          Btn.close();
          if(<%=departmentpk%> != null){
          		var oldid = DWRUtil.getValue("oldDepUpId");
	           dwrHrmEmployeeService.updateDepartment(getDepartmentinfo(),oldid,updateCallback);
	  
	      }else{   
	           dwrHrmEmployeeService.saveDepartment(getDepartmentinfo(),saveCallback);
	      }
	 }
}

function conNewDep(){
    document.getElementById("hrmDepName").value = "";
}
	
function saveCallback(data){
    Btn.open();
    if(data.success){
		confirmmsgAndTitle("添加部门成功！是否想继续添加部门？","reset();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}

//修改成功后处理
function updateCallback(data){
	Btn.open();
	if(data.success){
    	window.parent.tree.reload();
		alertmsg(data,"reset();");
	}else{
		alertmsg(data);
	}
}

function returnload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function reset(){
	if(<%=departmentpk%>!=null){
		returnload();
	}else{
		DWRUtil.setValue("hrmDepCode","");
	 	DWRUtil.setValue("hrmDepName","");
	 	DWRUtil.setValue("hrmDepEngname","");
	 	DWRUtil.setValue("hrmDeptext","");
	 	DWRUtil.setValue("hrmDepartmentId","");
	 	DWRUtil.setValue("hrmDepDesc","");
	 	document.getElementById("hrmDepCode").focus();
 	}
}

function treeclick(code,text){
	document.getElementById("hrmDeptext").value = text;
	document.getElementById("hrmDepartmentId").value = code;
}

function  saveOredit(){
     if(<%=departmentpk%> != null){
		var primaryKey = <%=departmentpk%>;
		dwrHrmEmployeeService.getDepartmentByPK(primaryKey,setDepartmentinfo);
		
		var btn = document.getElementById("backToList");
	    btn.style.display = "none";
	 }else{
	    Btn.hidden("btncancel");
	 }
}

function setDepartmentinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var department = data.resultList[0];
 			DWRUtil.setValue("hrmDepCode",department.hrmDepCode);
 			DWRUtil.setValue("hrmDepName",department.hrmDepName);
 			DWRUtil.setValue("hrmDepEngname",department.hrmDepEngname);
 			var depName= "";
 			var depId = "";
 			if(department.parentDepartment != null){
 			    depName = department.parentDepartment.hrmDepName;
 			    depId = department.parentDepartment.primaryKey;
 			}
 			DWRUtil.setValue("hrmDeptext",depName);
 			DWRUtil.setValue("hrmDepartmentId", depId);
 			DWRUtil.setValue("hrmDepDesc",department.hrmDepDesc);
 			DWRUtil.setValue("oldDepUpId",department.hrmDepUpid);
 			
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function getupcode(){
	if(<%=departmentpk%> != null){
		var box = SEL.getDeptIds("radio","hrmDeptext","hrmDepartmentId","processloadfrm");
		box.show();
	}else{
		var box = SEL.getDeptIds("radio","hrmDeptext","hrmDepartmentId");
		box.show();
	}
}

function closePage(){
	closeMDITab();
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">新增/编辑部门</div>
	    <table class="inputtable" border="0">
		    <tr>
				<th width="20%">&nbsp;&nbsp;部门编码</th>
				<td width="40%" style="text-align: left;"><input type="text" id="hrmDepCode" value="" style="width:90%;" maxlength="10" ></td>
				<td width="40%"></td>
			</tr>
			<tr>
				<th width="20%"><em>*</em>&nbsp;&nbsp;部门名称</th>
				<td width="40%" style="text-align: left;"><input type="text" id="hrmDepName" must="部门名称不能为空！" formust="hrmDepNameMust" value="" style="width:90%;" maxlength="20" ></td>
				<td width="40%"><label id="hrmDepNameMust"></label></td>
			</tr>
			<tr>
				<th width="20%">&nbsp;&nbsp;部门英文名</th>
				<td width="40%" style="text-align: left;"><input type="text" id="hrmDepEngname" value="" style="width:90%;" maxlength="50" ></td>
				<td width="40%"></td>
			</tr>
			<tr>
			<th>上级部门</th>
			<td>
			     <input type="text" class="takeform" id="hrmDeptext" linkclear="hrmDepartmentId" readonly="readonly" title="点击选择部门" onclick="getupcode();">
			     <input type="hidden" id="hrmDepartmentId" value=""><input type="hidden" id="oldDepUpId" value="">
			</td>
			</tr>
			<tr>
				<th></th>
				<td id="hrmDepDescMsg"></td>
			</tr>
			<tr>
			     <th>部门描述</th>
			     <td colspan="3"><textarea id="hrmDepDesc" style="width:50%;"></textarea></td>
			</tr>
	    </table>
	</div>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存部门信息" /></td>
		<td style="width: 20px;"></td>
		<td id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
		<td id="btncancel"><btn:btn onclick="window.parent.MoveDiv.close()" value="取 消 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>