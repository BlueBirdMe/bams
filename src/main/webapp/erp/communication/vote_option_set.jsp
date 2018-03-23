<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投票选项设置界面</title>
<style type="text/css">
	body {
	background-color: #fefefe;
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
	<legend>投票选项设置</legend>
	<div>
	
	<table class="inputtable">
	<tr>
	<th><em>*</em>选项名称</th>
	<td style="text-align: left;"><input type="text" id="methodname" must="选项名称不能为空" value="请输入选项名称" onfocus="if (value =='请输入选项名称'){value =''}" ></td> 
	</tr>
	</table>
	</div>
</fieldset>
<br/>
<br/>
<br/>
</body>
</html>