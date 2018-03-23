	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String boadroompk =request.getParameter("id");
 %>
<title>添加会议室</title>
<style type="text/css">
body {
	background-color: #fefefe;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	initInput("helpTitle","添加会议室，您可以在此处添加新的会议室，给员工申请使用。");
	saveOrEdit();
	//第一个输入框获取焦点
	document.getElementById("roomName").focus();
}

function saveOrEdit(){
	if(<%=boadroompk%> != null){
		var boadroomPk = <%=boadroompk%>;
		dwrOfficeResourcesService.getBoadroomByPk(boadroomPk,setpagevalue);
	}
}

function setpagevalue(data){
	if(data!=null){
		var boadroom =data.resultList[0];
		DWRUtil.setValue("oaBoardroomId",boadroom.primaryKey);
		DWRUtil.setValue("roomName",boadroom.oaBoardroomName);
		DWRUtil.setValue("roomCapacity",boadroom.oaBoardroomCapacity);
		DWRUtil.setValue("roomDescribe",boadroom.oaBoardroomDescribe);
		DWRUtil.setValue("roomAddress",boadroom.oaBoardroomAddress);
	}
}
function save(){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "roomNameMust";
	warnArr[1] = "roomCapacityMust";
	warnArr[2] = "roomAddressMust";
	roomDescribemust
	warnArr[3] = "roomDescribemust";
	//清空所有信息提示
	warnInit(warnArr);
	
	var bl = validvalue('helpTitle');
	if(bl){
		if(document.getElementById("roomDescribe").value.length>1000){
			 setMustWarn("roomDescribemust","会议室描述字数，不能大于1000！");
		}
		var boadroom =getboardroom();
		Btn.close();
		dwrOfficeResourcesService.saveBoadroom(boadroom,savecallback);
	}
}

function getboardroom(){
	var boadroom = new Object();
	boadroom.primaryKey = DWRUtil.getValue("oaBoardroomId");
	boadroom.oaBoardroomName = DWRUtil.getValue("roomName");
	boadroom.oaBoardroomCapacity = DWRUtil.getValue("roomCapacity");
	boadroom.oaBoardroomAddress = DWRUtil.getValue("roomAddress");
	boadroom.oaBoardroomDescribe = DWRUtil.getValue("roomDescribe");
	return boadroom;
}
	
function savecallback(data){
	Btn.open();
	if(<%=boadroompk%> != null){
		alertmsg(data,"reSet();");
	} else {
		if(data.success){
			confirmmsgAndTitle("添加会议室成功！是否想继续添加？","reSet();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}

//继续添加 
function reSet(){
    if(<%=boadroompk%> != null){
          reload();
    }else{
          DWRUtil.setValue("roomName","");
          DWRUtil.setValue("roomCapacity","");
          DWRUtil.setValue("roomAddress",""); 
          DWRUtil.setValue("hrmDepid","");
          DWRUtil.setValue("roomDescribe","");
		  //第一个输入框获取焦点
		  document.getElementById("roomName").focus();
		  refreshMDITab(<%=request.getParameter("tab")%>);
    }   
}

//返回会议室列表
function backToBoadroomList(){
	 window.parent.MoveDiv.close();
	 window.parent.queryData();
}

function reload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle" style="font: bold;font-size:12px;color:black"></label></div>
<input type="hidden" id="oaBoardroomId">
<div class="formTitle">
	<font size="2"><strong>会议室内容</strong></font>
</div>
		<table class="inputtable" border="0">
		<tr>
	<th width="15%"><em>* </em>会议室名称</th>
	<td width="40%" style="text-align: left;"><input type="text" id="roomName" must="会议室名称不能为空"  formust="roomNameMust" style="width:90%;" maxlength="50" ></td>
	<td width="40%"><label id="roomNameMust"></label></td>
	</tr>
	<tr>
	<th><em>* </em>可容纳人数</th><td ><input type="text" id="roomCapacity" must="可容纳人数不能为空" maxlength="3" class="numform" formust="roomCapacityMust" ></td>
	<td width="40%"><label id="roomCapacityMust"></label></td>
	</tr>
	<tr>
	<th><em>* </em>会议室地址</th>
	<td style="text-align: left;"><input type="text" id="roomAddress" must="地址不能为空" formust="roomAddressMust" style="width:90%;" maxlength="50" ></td>
	<td width="40%"><label id="roomAddressMust"></label></td>
	</tr>
	<tr>
		<th>
		</th>
		<td>
		<label id="roomDescribemust"></label>
		</td>
	</tr>
	<tr>
	<th>会议室描述</th><td style="text-align: left" colspan="3">
	<textarea id="roomDescribe" style="height: 100"></textarea>
	</td>
	</tr>
		</table>
		<br/>
	</div>
<br/>
<table align="center">
   <tr>
     <td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
     <td style="width: 10px;"></td>
	<td>
	<%if (boadroompk == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
   </tr>
</table>
</body>
</html>