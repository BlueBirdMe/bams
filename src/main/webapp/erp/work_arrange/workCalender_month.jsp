<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作日志</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
<%
GregorianCalendar calendar = new GregorianCalendar();
int year = calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH);
String date = UtilWork.getToday();
String view = request.getParameter("view");
%>
<script type="text/javascript">

window.onload=function(){
	DWRUtil.setValue("view",<%=view%>);
	queryData();
}

function queryData(){
	init(0);
}

function init(index){
	var year = document.getElementById("year").value;
	var month = document.getElementById("month").value;
	dwrWorkArrangeService.listOaCalenderMonth(year,month,index,dateback);
}

function dateback(data){
	if(data.success == true){
	 	document.getElementById("dateview").innerHTML = data.resultList[0];
	 	DWRUtil.setValue("year",data.resultList[1]);
	 	DWRUtil.setValue("month",data.resultList[2]);
	 	DWRUtil.setValue("newdate",data.resultList[3]);
	 	
	 	
	 	$('a.cal').contextMenu('functiondiv', {
			allowMouseOver:true,
			bindings : {
				'info' : function(t) {
					complete(t.id)
				},
				'edit' : function(t) {
					edit(t.id)
				},
				'del' : function(t) {
					del(t.id)
				}
			}
		});
	 	
	 	
	}else{
		alert(data.message);
	}
}

function changeStatus(status){
	if(status == <%=EnumUtil.OA_CALENDER_STATUS.one.value%>){
		$("#label").text("重新启用");
	}else{
		$("#label").text("完成工作");
	}
}

function addCalender(index){
	var datetime = index+' '+'<%=UtilWork.getNowTime().substring(11, UtilWork.getNowTime().length())%>';
	var url = '<%=contextPath%>/erp/work_arrange/workCalender_add.jsp?calendertype=1&startTime='+datetime;
	openMDITab(url + "&tab="+getMDITab());
}

function setdate(year,month){
	var year = document.getElementById("newyear").value;
	var month = document.getElementById("newmonth").value;
	var date = year+"-"+month;
	DWRUtil.setValue("newdate",date);
	dwrWorkArrangeService.listOaCalenderMonth(year,month,0,dateback);
}

function getView(){
	var view = document.getElementById("view").value;
	if(view =="1"){
		Sys.load("work_arrange/workCalender.jsp?view='"+view+"'");
	}else if(view =="2"){
		Sys.load("work_arrange/workCalender_week.jsp?view='"+view+"'");
	}else if(view =="3"){
		Sys.load("work_arrange/workCalender_month.jsp?view='"+view+"'");
	}else{
	  Sys.load("work_arrange/workCalender_year.jsp?view='"+view+"'");
	}
}

function del(pk){
	confirmmsg("确定要删除记录吗?","delbatchok('"+pk+"')");
}

function delbatchok(pk){
    var recordsPks = new Array();
	recordsPks[0] = pk;
    dwrWorkArrangeService.deleteOaCalenderByPks(recordsPks,setcallback);
}

function setcallback(data){
	alertmsg(data,"init(0)");
}

function edit(id){
	MoveDiv.show('新增\编辑日程安排','<%=contextPath%>/erp/work_arrange/workCalender_add.jsp?calendertype=1&calenderpk='+id);
}

function complete(pk){
	dwrWorkArrangeService.completeOaCalenderByPks(pk,setcallback);
}


</script>
</head>
<body style="overflow: hidden;">
	<div style="display:none;" id='functiondiv'>
		<ul>
			<li id='info'><img src="<%=contextPath%>/images/grid_images/info.png"> <label id="label"></label></li>
			<li id='edit'><img src="<%=contextPath%>/images/grid_images/edit.png"> 编辑</li>
			<li id='del'><img src="<%=contextPath%>/images/grid_images/close.png"> 删除</li>
		</ul>
	</div>
	<input type="hidden" id="year" value="<%=year%>">
	<input type="hidden" id="month" value="<%=month%>">
	<input type="hidden" id="date" value="<%=date%>">

	<input type="hidden" id="newyear" value="<%=year%>">
	<input type="hidden" id="newmonth" value="<%=month%>">

	<table height="100%" width="100%" cellpadding="0" cellspacing="0" border="0">
		<tr style="width: 100%">
			<td style="padding-bottom: 5px; padding-top: 5px;">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td align='left' style='padding-left: 15px;' width="20px"><a title='上一天' onclick='init(-1)' href="javascript:void(0)" style="cursor: pointer; font: 12px '宋体'; text-decoration: none;">&nbsp;<<&nbsp;</a></td>
						<td id="newdate" align='left' width="53px"></td>
						<td align='left' width="20px"><a title='下一天' onclick='init(1)' href="javascript:void(0)" style="cursor: pointer; font: 12px '宋体'; text-decoration: none;">&nbsp;>>&nbsp;</a></td>
						<td style='padding-left: 30px;'><btn:btn onclick="setdate()" value=" 本 月 " width="50px"></btn:btn></td>
						<td style="padding-right: 20px" align="right"><select onchange="getView()" id="view">
								<option value="1">日列表</option>
								<option value="2">周列表</option>
								<option value="3">月列表</option>
								<option value="4">年列表</option>
						</select></td>
					</tr>
				</table></td>
		</tr>
		<tr style="height: 100%">
			<td valign="top">
				<div style="overflow: auto; height: 100%; width: 100%" id="dateview"></div></td>
		</tr>
	</table>

</body>
</html>