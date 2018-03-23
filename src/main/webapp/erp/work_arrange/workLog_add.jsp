<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加编辑页面</title>
<style type="text/css">
	body {
	background-color: #EDF5FA;
}
</style>
<script type="text/javascript">
	window.onload = function(){
		initInput('aaac');
	}
	function saveoredit(){
		var bl = validvalue('aaac');
		var a = document.getElementById("aabc").value;
		if(a==""){
			document.getElementById("aaac").innerHTML = "附件没有上传";
			bl= false;
		}
		if(bl){
			initInput('aaac');
		}
	}
</script>
</head>
<body>

<fieldset>
	<div class="requdiv"><label id="aaac"></label></div>
	<legend>日志</legend>
	<div>
	
	<table class="inputtable">
	<tr>
	<th><em>*</em>日志标题</th>
	<td><input type="text" id="methodname" must="日志标题不能为空" value="请输入日志标题" onfocus="if (value =='请输入日志标题'){value =''}" ></td>
	<th><em>*</em>日志类型</th>
	<td><select must="请选择日志类型"><option value="1">工作日志</option><option value="2">个人日志</option></select></td>
	</tr>
	<tr>
	  <th>日期</th>
	  <td>
	   <input type="text" readonly="readonly" class="Wdate" onClick="WdatePicker()" value="<%=UtilWork.getToday() %>">
	  </td>
	</tr>
	<tr>
	<th><em>*</em>共享部门</th>
	<td colspan="3"><textarea readonly="readonly" onclick="alert('选择参与人')" style="color: #999">双击选择</textarea></td>
	</tr>
	<tr>
	<th><em>*</em>共享人员</th>
	<td colspan="3"><textarea readonly="readonly" onclick="alert('选择参与人')" style="color: #999">双击选择</textarea></td>
	<td>
	</tr>
	<tr>
	<th>附件</th>
	<td style="text-align: left" colspan="3">
	<file:multifileupload width="90%" acceptTextId="aabc" height="100"></file:multifileupload>
	</td>
	</tr>
	<tr>	
	<th>内容</th>
	<td colspan="3">
	<FCK:editor instanceName="descp" height="200" width="90%" toolbarSet="Basic"></FCK:editor>
	</td>
	</tr>
	</table>	
	</div>
</fieldset>
<br/>
<table align="center">
   <tr>
     <td><btn:btn onclick="" value=" 确 定 "></btn:btn></td>
     <td style="width: 10px;"></td>
     <td><btn:btn onclick="javascript:history.go(-1)" value=" 取 消 "></btn:btn></td>
   </tr>
</table>
</body>
</html>