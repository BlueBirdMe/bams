<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成页面</title>
<script type="text/javascript">

window.onload = function(){
	if('${MsgSession}'!=''){alertmsg('${MsgSession}');}
	useLoadingMassage();
	initInput('helpTitle','您可以在此处生成新增/编辑页及列表页！');
			
    //第一个输入框获取焦点
    document.getElementById("pojoClass").focus();
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "pojoClassMust";
	warnArr[0] = "pojoShortNameMust";
	warnArr[1] = "pojoNameMust";
	warnArr[2] = "dwrNameMust";
	//清空所有信息提示
	warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
    	var obj = document.getElementById("createForm");
     	obj.action = "createPage.do";
		obj.submit();
	}
}

function closePage(){
	closeMDITab();
}
</script>
</head>
<body class="inputcls">
<form id="createForm" method="post">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">设置java实体类名称</div>
	    <table class="inputtable" border="0">
			<tr>
				<th><em>*</em>pojo类名</th>
				<td>
					<input type="text" style="width:220px;" id="pojoClass" name="pojoClass"  must="pojo类名不能为空！" formust="pojoClassMust" value="${pojoClass}">
					<label id="pojoClassMust"><font color="#808080">编译过的实体类名称，生产POJO后拷到项目中，重启web服务器编译</font></label>
				</td>
			</tr>
			<tr>
				<th><em>*</em>pojo简短名</th>
				<td>
					<input type="text" style="width:220px;" id="pojoShortName" name="pojoShortName"  must="pojo简短名不能为空！" formust="pojoShortNameMust" value="${pojoShortName}">
					<label id="pojoShortNameMust"><font color="#808080">和类名相似、全为小写的单词，多个单词也可以用"_"下划线间隔</font></label>
				</td>
			</tr>
			<tr>
				<th><em>*</em>pojo中文名称</th>
				<td>
					<input type="text" style="width:220px;" id="pojoName" name="pojoName"  must="pojo中文名称不能为空！" formust="pojoNameMust" value="${pojoName}">
					<label id="pojoNameMust"><font color="#808080">中文描述</font></label>
				</td>
			</tr>
			<tr>
				<th><em>*</em>dwr名称</th>
				<td>
					<input type="text" style="width:220px;" id="dwrName" name="dwrName"  must="dwr名称不能为空！" formust="dwrNameMust" value="${dwrName}">
					<label id="dwrNameMust"><font color="#808080">已经生成的，在jsp页面使用的dwr名称</font></label>
				</td>
			</tr>
			<tr>
				<th>新增、编辑、明细页显示列数</th>
				<td>
					<input type="text" style="width:50px;" class="numform" name="columnCount" value="${columnCount}">
					<label><font color="#808080">默认值为2，可选值为1、2、3</font></label>
				</td>
			</tr>
			<tr>
				<th>页面所在文件夹名称</th>
				<td>
					<input type="text" style="width:220px;" name="folderName" value="${folderName}">
					<label><font color="#808080">请填写位于"erp"目录下的文件夹名称</font></label>
				</td>
			</tr>
			<tr>
				<th>树形结构</th>
				<td>
					<select name="istree">
						<option value="false">否</option>
						<option value="true">是</option>
					</select>
				</td>
			</tr>
	    </table>
	</div>
</form>
<table align="center">
	<tr>
		<td><btn:btn onclick="save();" value=" 生 成 "/></td>
		<td style="width: 20px;"></td>
		<td id="btncancel"><btn:btn onclick="closePage()" value=" 关 闭 " title="关闭当前页面"/></td>
	</tr>
</table>
</body>
</html>