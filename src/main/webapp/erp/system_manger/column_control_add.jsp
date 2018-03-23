<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增列表字段</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
    String cid = request.getParameter("cid");
%>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处添加您想新增的列表字段！');
	saveOredit();
}

function getColumnControl(){
	var column = new Object();
   	if(<%=cid%> != null){
   		column.primaryKey = <%=cid%>;
    }
   	column.tableName = DWRUtil.getValue("tableName");
   	column.columnName = DWRUtil.getValue("columnName");
   	column.columnCode = DWRUtil.getValue("columnCode");
   	column.isShow = getRadioValueByName("isShow");
   	column.isshowSimple = getRadioValueByName("isshowSimple");
   	column.isshowAdvanced = getRadioValueByName("isshowAdvanced");
   	column.columnStrcount = DWRUtil.getValue("columnStrcount");
   	column.priority = DWRUtil.getValue("priority");
	return column;
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "tableNameMust";
	warnArr[1] = "columnCodeMust";
	warnArr[2] = "columnStrcountMust";
	warnArr[3] = "priorityMust";
	//清空所有信息提示
	warnInit(warnArr);
     var bl = validvalue('helpTitle');
     if(bl){
        Btn.close();
        dwrSysProcessService.saveColumnControl(getColumnControl(),saveCallback);
	 }
}

function saveCallback(data){
    Btn.open();
	if(data.success){
	    if(<%=cid%> != null){
	    	alertmsg(data,"reset();");
	    }else{
	    	confirmmsgAndTitle("添加列表字段成功！是否想继续添加列表字段？","reset();","继续添加","closePage();","关闭页面");
    	}
	}else{
		alertmsg(data);
    }
}

function returnload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function reset(){
	if(<%=cid%>!=null){
		returnload();
	}else{
		Sys.reload();
 	}
}

function saveOredit(){
     if(<%=cid%> != null){
		dwrSysProcessService.getColumnControlByPK(<%=cid%>,setInfo);
		var btn = document.getElementById("backToList");
	    btn.style.display = "none";
	 }else{
	    Btn.hidden("btncancel");
	 }
}

function setInfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var column = data.resultList[0];
 			DWRUtil.setValue("tableName",column.tableName);
 			DWRUtil.setValue("columnName",column.columnName);
 			DWRUtil.setValue("columnCode",column.columnCode);
 			setRadioValueByName("isShow",column.isShow);
 			setRadioValueByName("isshowSimple",column.isshowSimple);
 			setRadioValueByName("isshowAdvanced",column.isshowAdvanced);
 			DWRUtil.setValue("columnStrcount", column.columnStrcount);
 			DWRUtil.setValue("priority", column.priority);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">新增/编辑列表字段</div>
	    <table class="inputtable" border="0">
		    <tr>
				<th>列表名称</th>
				<td>
					<input type="text" id="tableName"  must="列表名称不能为空！" formust="tableNameMust">
					<label id="tableNameMust"></label>
				</td>
			</tr>
		    <tr>
				<th>字段名称</th>
				<td>
					<input type="text" id="columnName">
				</td>
			</tr>
		    <tr>
				<th>字段代码</th>
				<td>
					<input type="text" id="columnCode"  must="字段代码不能为空！" formust="columnCodeMust">
					<label id="columnCodeMust"></label>
				</td>
			</tr>
		    <tr>
				<th>是否显示</th>
				<td>
					<%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_ISEDIT.getSelectAndText(""),"isShow")%>
				</td>
			</tr>
		    <tr>
				<th>是否简单查询</th>
				<td>
					<%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_ISEDIT.getSelectAndText(""),"isshowSimple")%>
				</td>
			</tr>
		    <tr>
				<th>是否高级查询</th>
				<td>
					<%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_ISEDIT.getSelectAndText(""),"isshowAdvanced")%>
				</td>
			</tr>
			<tr>
				<th>显示字数</th>
				<td>
					<input type="text" value="0" id="columnStrcount"  must="字数不能为空！" formust="columnStrcountMust" class="numform">
					<label id="columnStrcountMust"></label>
					<font color="#808080">默认为0，不限制字数</font>
				</td>
			</tr>
			<tr>
				<th>排序</th>
				<td>
					<input type="text" id="priority"  must="排序不能为空！" formust="priorityMust" class="numform">
					<label id="priorityMust"></label>
				</td>
			</tr>
	    </table>
	</div>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存字段信息" /></td>
		<td style="width: 20px;"></td>
		<td id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
		<td id="btncancel"><btn:btn onclick="window.parent.MoveDiv.close()" value="取 消 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>