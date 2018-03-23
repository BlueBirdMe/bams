<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆查看</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/office_resources/car_query.jsp";
loadarray[1] = "<%=contextPath %>/erp/office_resources/car_query.jsp?id=1";
loadarray[2] = "<%=contextPath %>/erp/office_resources/car_query_using.jsp?id=2";
function rightload(index){
	var url = loadarray[index];
	Sys.load(url,"personfrm");
}

window.onload = function(){
	rightload(0);
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l" style="width:130px;">
<div class="div_title">选择操作</div>
<div class="div_content">
<div class="div_leftmethod">
	<div class="leftbut" onclick="rightload(0)" title="全部车辆">
	<img src="<%=contextPath %>/images/pagemethodimg/lorry_link.png"/>
	<div>全部车辆</div>
	</div>
	<div class="leftbut" onclick="rightload(1)" title="可用车辆">
	<img src="<%=contextPath %>/images/pagemethodimg/car_add.png"/>
	<div>可用车辆</div>
	</div>
	<div class="leftbut" onclick="rightload(2)" title="在用车辆">
	<img src="<%=contextPath %>/images/pagemethodimg/controller_add.png"/>
	<div>在用车辆</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="1" id="personfrm" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>