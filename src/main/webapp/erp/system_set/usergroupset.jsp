<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户编组</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/system_set/user_group_add.jsp";
loadarray[1] = "<%=contextPath %>/erp/system_set/user_group_manger.jsp";

function rightload(index){
	var url = loadarray[index];
	Sys.load(url,"usergroupfrm");
}

window.onload = function(){
	rightload(0);
}

</script>
</head>
<body style="overflow: hidden;">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l" style="width:130px;">
<div class="div_title">选择操作</div>
<div class="div_content">
<div class="div_leftmethod">
	<div class="leftbut" onclick="rightload(0);" title="创建编组">
	<img src="<%=contextPath %>/images/pagemethodimg/addusergroup.png"/>
	<div>创建编组</div>
	</div>
	<div class="leftbut" onclick="rightload(1);" title="管理编组">
	<img src="<%=contextPath %>/images/pagemethodimg/setusergroup.png"/>
	<div>管理编组</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="1" id="usergroupfrm" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>