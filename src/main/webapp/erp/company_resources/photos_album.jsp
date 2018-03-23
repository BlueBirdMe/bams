<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<title>相册管理</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/company_resources/photos_manger_add.jsp";
loadarray[1] = "<%=contextPath %>/erp/company_resources/photos_manger.jsp";
loadarray[2] = "<%=contextPath %>/erp/company_resources/photos_manger_addphoto.jsp";

function rightload(index){
	var url = loadarray[index];
	Sys.load(url,"photosfrm");
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
	<div class="leftbut" onclick="rightload(0);" title="创建相册">
	<img src="<%=contextPath %>/images/pagemethodimg/create_photos.png"/>
	<div>创建相册</div>
	</div>
	<div class="leftbut" onclick="rightload(1);" title="管理相册">
	<img src="<%=contextPath %>/images/pagemethodimg/manger_photos.png"/>
	<div>管理相册</div>
	</div>
	<div class="leftbut" onclick="rightload(2);" title="添加照片">
	<img src="<%=contextPath %>/images/pagemethodimg/input_photos.png"/>
	<div>添加照片</div>
	</div>
</div>
</div>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" id="photosfrm" width="100%" ></iframe>
</td>
</tr>
</table>
</body>
</html>