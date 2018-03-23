<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.UtilWork"%>
<script type="text/javascript">
var timerID = null;
var timerRunning = false;

function stopclock (){
	if(timerRunning)
		clearTimeout(timerID);
	timerRunning = false
}

function getNowTime(){
	$.get("<%=request.getContextPath()%>/getServerTimer.jsp?time="+Math.random(),
    function(tm){
	  document.getElementById("currdate").value = tm;
  	});  
}
 
function getServerDate(){
	getNowTime();
	var tmp = document.getElementById("currdate").value;
	var tms = tmp.split("-");
	return tms;
}
 
window.onunload = function(){
	stopclock();
}
</script>
<input type="hidden" id="currdate" value="<%=UtilWork.getCustomerDay("yyyy-M-d-HH-mm-ss-E") %>">
