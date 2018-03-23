<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的会议</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l" style="width:130px;">
<div class="div_title">选择操作</div>
<div class="div_content">
<div class="div_leftmethod">
	<div class="leftbut" onclick="rightload(0)" >
	<img src="<%=contextPath %>/images/pagemethodimg/sc0909291_6.png"/>
	<div>待参加会议</div>
	</div>
	<div class="leftbut" onclick="rightload(1)" >
	<img src="<%=contextPath %>/images/pagemethodimg/sc0909291_7.png"/>
	<div>已参加会议</div>
	</div>
	<div class="leftbut" onclick="rightload(2)" >
	<img src="<%=contextPath %>/images/pagemethodimg/sc0909291_3.png"/>
	<div>申请的会议</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="1" id="approvefrm" width="100%"></iframe>
</td>
</tr>
</table>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/office_resources/will_attendmeet.jsp";
loadarray[1] = "<%=contextPath %>/erp/office_resources/attendedmeet.jsp";
loadarray[2] = "<%=contextPath %>/erp/office_resources/my_applymeeting.jsp";
function rightload(index){
	var url = loadarray[index];
	document.getElementById("approvefrm").src = url;
}

window.onload = function(){
	rightload(0);
}
</script>
</body>
</html>