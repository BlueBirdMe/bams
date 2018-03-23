<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司管理</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/system_manger/sys_company_process.jsp";

function rightload(type){
	var url = loadarray[0]+"?type="+type;
	Sys.load(url,"companyfrm");
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
	<div class="leftbut" onclick="rightload(0);" title="等待处理的公司申请">
	<img src="<%=contextPath %>/images/pagemethodimg/company-1.png"/>
	<div>等待处理</div>
	</div>
	<div class="leftbut" onclick="rightload(1);" title="试用中的公司">
	<img src="<%=contextPath %>/images/pagemethodimg/company-2.png"/>
	<div>试用公司</div>
	</div>
	<div class="leftbut" onclick="rightload(2);" title="正式注册公司">
	<img src="<%=contextPath %>/images/pagemethodimg/company-3.png"/>
	<div>注册公司</div>
	</div>
	<div class="leftbut" onclick="rightload(3);" title="公司服务到期">
	<img src="<%=contextPath %>/images/pagemethodimg/company-4.png"/>
	<div>服务到期</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" marginheight="1" id="companyfrm" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>