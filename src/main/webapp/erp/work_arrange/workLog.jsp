<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>工作日志</title>
		<script type="text/javascript"	src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
			<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript">

window.onload=function(){
	Sys.load('myLog.jsp','workLogfrm');
	init(0);
}

function init(index){
	var year = document.getElementById("year").value;
	var month = document.getElementById("month").value;
	dwrWorkArrangeService.initDate(year,month,index,dateback);
}

function dateback(data){
	if(data.success == true){
		document.getElementById("date").innerHTML = data.resultList[0];
		document.getElementById("year").value = data.resultList[1];
		document.getElementById("month").value = data.resultList[2];
	}else{
		alert(data.message);
	}
}

function addDayToCurrentShift(date,value){
	if(value == 0){
		Sys.load("myLog.jsp?date="+date,'workLogfrm');
	}else{
		Sys.load("myLog.jsp?date="+date,'workLogfrm');
	}
}

function setdate(year,month){
	dwrWorkArrangeService.initDate(year,month,dateback);
}

</script>
	</head>
	<body style="overflow: hidden;">
		<%
			GregorianCalendar calendar = new GregorianCalendar();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
		%>
		<input type="hidden" id="year" value="<%=year%>">
		<input type="hidden" id="month" value="<%=month%>">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l" style="width:200px;">
						<div class="div_title">
							选择操作
						</div>
						<div class="div_content">
						<div class="div_leftmethod">
							<div id="date" style="text-align: center;">
							</div>
						</div>
						</div>
</td>
<td>
						<iframe frameborder="0" height="100%" scrolling="auto"
							marginheight="3" id="workLogfrm" width="100%"></iframe>
</td>
</tr>
</table>
	</body>
</html>