<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>新增操作提示</title>
<%
String lid = request.getParameter("lid");
 %>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">添加/编辑操作提示</div>
	<div>
		<table class="inputtable">
			<tr>
				<th><em>* </em>功能名称</th>
				<td>
					<input type="text" id="methodName" class="takeform" readonly="readonly"  must="请选择功能" formust="methodMust" linkclear="methodId" onclick="getSysMethodInfo()">
					<input type="hidden" id="methodId" >
					<label id="methodMust"></label>
				</td>
			</tr>
			<tr>
				<th>图标文件名</th>
				<td>
					<input type="text" id="helpImg" readonly="readonly" class="takeform" onclick="getImg()">
					&nbsp;&nbsp;<font color="#808080">注意图标路径默认为：/images/grid_images/</font>
				</td>
			</tr>
		
			<tr>
				<th><em>* </em>操作提示</th>
				<td>
				<textarea id="helpDesc" style="height: 100px;"></textarea>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<label id="helpDescMust"></label>
				</td>
			</tr>
		</table>
	</div>
	<br>
</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="savelib();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存操作提示" />
</td>
<td style="width: 15px;"></td>
<td id="backbtn">
	<td>
	<%if (lid == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
</td>
</tr>
</table>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以新增/编辑系统操作提示');
	if(<%=lid%>!=null){//编辑
		dwrSysProcessService.getSysMethodHelpByPk(<%=lid%>,setpagevalue);
	} 
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function setpagevalue(data){
	if(data!=null){
		var tmp =data.resultList[0];
		if(tmp.methodInfo!=null){
			DWRUtil.setValue("methodName",tmp.methodInfo.methodInfoName);
			DWRUtil.setValue("methodId",tmp.methodInfo.primaryKey);
		}
		DWRUtil.setValue("helpImg",tmp.helpImg);
		DWRUtil.setValue("helpDesc",tmp.helpDesc);
	}
}

function reclose(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function getSysMethodInfo(){
	if(<%=lid%>!=null){
		var box = SEL.getSysMethodInfoIds("radio","methodName","methodId","processloadfrm");
	}else{
		var box = SEL.getSysMethodInfoIds("radio","methodName","methodId");
	}	
   box.show();
}

function getImg(){
	if(<%=lid%>!=null){
		var box = SEL.getImg("grid_images","helpImg","processloadfrm");
	}else{
		var box = SEL.getImg("grid_images","helpImg");
	}	
	box.show();
}


function savelib(){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "methodMust";
	warnArr[1] = "helpDescMust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
		if(DWRUtil.getValue("helpDesc").length == 0){
			setMustWarn("helpDescMust","请填写操作提示内容。");
			return false;
		}
		
		Btn.close();
		if(<%=lid%>!=null){
			dwrSysProcessService.updateSysMethodHelp(getSysMethodHelp(),updatacallback);
		}else{
			dwrSysProcessService.saveSysMethodHelp(getSysMethodHelp(),methodcallback);
		}
	}
}

function updatacallback(data){
	Btn.open();
	alertmsg(data,"reclose()");
}
function methodcallback(data){
	Btn.open();
	confirmmsgAndTitle("系统操作提示添加成功！是否继续添加","sevent();","继续添加","closePage();","关闭页面");
}

function canSe(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function sevent(){
	//Sys.reload();
	DWRUtil.setValue("helpImg","");
	DWRUtil.setValue("helpDesc","");
}

function getSysMethodHelp(){
	var help = new Object();
	if(<%=lid%>!=null){//编辑
		help.primaryKey = <%=lid%>;
	}
	help.methodId = DWRUtil.getValue("methodId"); 
	help.helpImg = DWRUtil.getValue("helpImg"); 
	help.helpDesc = DWRUtil.getValue("helpDesc"); 
	return help;
}

</script>
</body>
</html>