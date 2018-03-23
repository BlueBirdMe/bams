<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时提醒</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/personal_work/timed_query_valid.jsp";
loadarray[1] = "<%=contextPath %>/erp/personal_work/timed_query_invalid.jsp";
function rightload(index){
	var url = loadarray[index];
	Sys.load(url,"timedfrm");
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
			<div class="leftbut" onclick="rightload(0)" title="有效提醒">
				<img src="<%=contextPath %>/images/pagemethodimg/sc0910131_5.png"/>
				<div>有效提醒</div>
			</div>
			<div class="leftbut" onclick="rightload(1)" title="失效提醒">
				<img src="<%=contextPath %>/images/pagemethodimg/Vista4.png"/>
				<div>失效提醒</div>
			</div>
		</div>
		</div>
</td>
<td>
		<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="1" id="timedfrm" width="100%"></iframe>
</td>
</tr>
</table>
</div>
</body>
</html>