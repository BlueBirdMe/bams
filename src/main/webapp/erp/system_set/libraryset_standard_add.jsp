<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>新增标准代码</title>
<%
String lid = request.getParameter("lid");
 %>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">添加/编辑标准代码</div>
	<div>
		<table class="inputtable">
			<tr>
				<th>上级名称</th>
				<td>
					<input type="text" class="takeform" id="libupname" readonly="readonly" onclick="getupcode();" linkclear ="libupcode">
					<input type="hidden" id="libupcode">
					&nbsp;&nbsp;<font color="#808080">顶层请留空</font>
				</td>
			</tr>
			<tr>
				<th><em>* </em>名称</th>
				<td>
					<input type="text" id="linname" must="业务名称不能为空" formust="linnamemust" maxlength="15">
					<input type="hidden" id="lincode">
					<label id="linnamemust"></label>
				</td>
			</tr>
			<tr>
				<th><em>* </em>标准代码</th>
				<td>
					<input type="text" id="standcode" must="标准代码不能为空" formust="standcodemust" class="numform">
					<label id="standcodemust"></label>
					&nbsp;&nbsp;<font color="#808080">顶层请设置为 -1</font>
				</td>
			</tr>
			<tr>
				<th>备 注</th>
				<td>
				<textarea id="libdesc" style="height: 100px;"></textarea>
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
<btn:btn onclick="savelib();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存标准代码" />
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
	initInput('helpTitle','您可以新增/编辑系统运行标准代码');
	if(<%=lid%>!=null){//编辑
		dwrSysProcessService.getSysLibraryStandardByPk(<%=lid%>,setpagevalue);
	} 
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"","tree");
}

function setpagevalue(data){
	if(data!=null){
		var tmp =data.resultList[0];
		DWRUtil.setValue("linname",tmp.libraryName);
		DWRUtil.setValue("lincode",tmp.libraryCode);
		if(tmp.upSysLibrary!=null){
			DWRUtil.setValue("libupcode",tmp.upSysLibrary.libraryCode);
			DWRUtil.setValue("libupname",tmp.upSysLibrary.libraryName);
		}
		DWRUtil.setValue("standcode",tmp.libraryStandCode);
		DWRUtil.setValue("libdesc",tmp.libraryDesc);
		
		document.getElementById("libupname").disabled=true;
		document.getElementById("libupname").title ="编辑时不可修改上级";
	}
}
function reclose(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function getupcode(){
	var box = SEL.getLibraryStandard("libupname","libupcode");
	box.show();
}

function savelib(){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "linnamemust";
	warnArr[1] = "standcodemust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
		Btn.close();
		if(<%=lid%>!=null){
			dwrSysProcessService.updateSysLibraryStandard(getLibrary(),updatacallback);
		}else{
			dwrSysProcessService.saveSysLibrayStandard(getLibrary(),methodcallback);
		}
	}
}

function updatacallback(data){
	Btn.open();
	alertmsg(data,"reclose()");
}
function methodcallback(data){
	Btn.open();
	confirmmsgAndTitle("系统标准代码添加成功！是否继续添加","sevent();","继续添加","closePage();","关闭页面");
}

function canSe(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function sevent(){
	//Sys.reload();
	DWRUtil.setValue("linname","");
	DWRUtil.setValue("standcode","");
	DWRUtil.setValue("libdesc","");
}

function getLibrary(){
	var library = new Object();
	if(<%=lid%>!=null){//编辑
		library.primaryKey = <%=lid%>;
		library.libraryCode = DWRUtil.getValue("lincode");
	}
	library.libraryName = DWRUtil.getValue("linname");
	
	var code = DWRUtil.getValue("libupcode");
	if(code.length==0){
		code ="00";
	}
	library.libraryUpcode = code; 
	library.libraryStandCode = DWRUtil.getValue("standcode"); 
	library.libraryDesc = DWRUtil.getValue("libdesc"); 
	return library;
}

</script>
</body>
</html>