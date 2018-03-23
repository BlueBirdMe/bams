<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司信息</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">
var loadarray = new Array();
loadarray[0] = "<%=contextPath %>/erp/personal_work/company_anno.jsp";
loadarray[1] = "<%=contextPath %>/erp/personal_work/company_notice.jsp";
loadarray[2] = "<%=contextPath %>/erp/personal_work/company_adver.jsp";
loadarray[3] = "<%=contextPath %>/erp/company_resources/regulations_query.jsp";
function rightload(index){
	var url = loadarray[index];
	Sys.load(url,"personfrm");
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
			<div class="leftbut" onclick="rightload(0)" title="公告查看">
			<img src="<%=contextPath %>/images/pagemethodimg/06151_3.png"/>
			<div>公告查看</div>
			</div>
			<div class="leftbut" onclick="rightload(1)" title="通知查看">
			<img src="<%=contextPath %>/images/pagemethodimg/04031_8.png"/>
			<div>通知查看</div>
			</div>
			<div class="leftbut" onclick="rightload(2)" title="公司记事">
			<img src="<%=contextPath %>/images/pagemethodimg/timeline_marker.png"/>
			<div>公司记事</div>
			</div>
				<div class="leftbut" onclick="rightload(3)" title="规章制度">
			<img src="<%=contextPath %>/images/pagemethodimg/transmit.png"/>
			<div>规章制度</div>
			</div>
		</div>
		</div>
</td>
<td>
		<iframe frameborder="0"  height="100%" scrolling="auto" id="personfrm" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>