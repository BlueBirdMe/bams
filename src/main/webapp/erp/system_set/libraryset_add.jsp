<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>业务字典</title>
<%
String lid = request.getParameter("lid");
 %>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">添加/编辑业务字典</div>
	<div>
		<table class="inputtable">
			<tr>
				<th><em>* </em>上级名称</th>
				<td><input type="text" class="takeform" id="libupname"  readonly="readonly" title="点击选择上级名称" onclick="getupcode();" linkclear ="libupcode" must="必须指定上级名称" formust="libupnamemust">
				<input type="hidden" id="libupcode">&nbsp;&nbsp;<label id="libupnamemust"></label>
				</td>
			</tr>
			<tr>
				<th><em>* </em>业务名称</th>
				<td><input type="text" id="linname" must="业务名称不能为空" formust="linnamemust" maxlength="15">
				<input type="hidden" id="lincode">&nbsp;&nbsp;<label id="linnamemust"></label>
				</td>
			</tr>
			<tr>
				<th>英文名称</th>
				<td colspan="3">
				<input type="text" id="linenname" maxlength="40">
				</td>
			</tr>
			<tr>
				<th></th>
				<td id="libremarkMsg"></td>
			</tr>
			<tr>
				<th>备 注</th>
				<td  colspan="3">
				<textarea id="libremark" style="height: 100px;"></textarea>
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
<btn:btn onclick="savelib();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存数据字典" />
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
</td>
</tr>
</table>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以新增系统运行业务字典');
	if(<%=lid%>!=null){//编辑
		dwrSysProcessService.getSysLibraryInfoByPk(<%=lid%>,setpagevalue);
	} 
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"","tree");
}

function setpagevalue(data){
	if(data!=null){
		var tmp =data.resultList[0];
		DWRUtil.setValue("linname",tmp.libraryInfoName);
		DWRUtil.setValue("linenname",tmp.libraryInfoEngname);
		DWRUtil.setValue("lincode",tmp.libraryInfoCode);
		if(tmp.upSysLibraryInfo!=null){
			DWRUtil.setValue("libupcode",tmp.upSysLibraryInfo.libraryInfoCode);
			DWRUtil.setValue("libupname",tmp.upSysLibraryInfo.libraryInfoName);
		}
		DWRUtil.setValue("libremark",tmp.libraryInfoDesc);
		
		document.getElementById("libupname").disabled=true;
		document.getElementById("libupname").title ="编辑时不可修改上级";
	}
}
function reclose(){
	window.parent.MoveDiv.close();
		window.parent.queryData();
}
function getupcode(){
	var box = SEL.getLibraryIds("libupname","libupcode");
	box.show();
}

function savelib(){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "libupnamemust";
	warnArr[1] = "linnamemust";
	warnArr[2] = "libremarkMsg";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
		var library = getLibrary();
		
		var tmp = DWRUtil.getValue("libremark");
		if(tmp.length>200){
			setMustWarn("libremarkMsg","备注不能超过200个字符！");
			return;
		}
		Btn.close();
		if(<%=lid%>!=null){
			dwrSysProcessService.updateSysLibraryInfo(library,updatacallback);
		}else{
			dwrSysProcessService.saveSysLibrayInfo(library,methodcallback);
		}
	}
}

function updatacallback(data){
	Btn.open();
	alertmsg(data,"reclose()");
}
function methodcallback(data){
	Btn.open();
	confirmmsgAndTitle("系统业务字典添加成功！是否继续添加","sevent();","继续添加","closePage();","关闭页面");
}

function canSe(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function sevent(){
	DWRUtil.setValue("linname","");
	DWRUtil.setValue("linenname","");
	DWRUtil.setValue("libupcode","");
	DWRUtil.setValue("libupname","");
	DWRUtil.setValue("libremark","");
}

function getLibrary(){
	var library = new Object();
	if(<%=lid%>!=null){//编辑
		library.primaryKey = <%=lid%>;
		library.libraryInfoCode = DWRUtil.getValue("lincode");
	}
	library.libraryInfoName = DWRUtil.getValue("linname");
	library.libraryInfoEngname = DWRUtil.getValue("linenname");
	library.libraryInfoUpcode = DWRUtil.getValue("libupcode"); 
	library.libraryInfoIsedit = <%=EnumUtil.SYS_ISEDIT.EDIT.value%>;
	library.libraryInfoIsvalid = <%=EnumUtil.SYS_ISACTION.Vaild.value%>;
	library.libraryInfoDesc = DWRUtil.getValue("libremark");
	return library;
}

</script>
</body>
</html>