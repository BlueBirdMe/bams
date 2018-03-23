<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日程安排</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
<%
GregorianCalendar calendar = new GregorianCalendar();
int year = calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH);
int day = calendar.get(Calendar.DATE);

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
   var day = document.getElementById("day").value;
   dwrWorkArrangeService.listOaCalenderWeek(year,month,day,index,dateback);
}

function dateback(data){
	if(data.success == true){
		var rlen = document.getElementById("resultTable").rows.length;	
		for(var i=rlen-1;i>=1;i--){
			document.getElementById("resultTable").deleteRow(i);
		}
		if(data.resultList.length > 0){
			for ( var i = 0; i < data.resultList.length; i++) {
				var calbean = data.resultList[i];
				var otr = document.getElementById("resultTable").insertRow(-1);
				otr.style.cssText="color:black;background-color:#FFFFFF;text-align: center;line-height:22px;";
				otr.id = calbean.time;
				otr.ondblclick = function(){addCalender(this);}; 
				
				var td1=document.createElement("td");
				td1.style.cssText ="background-color:#F6F6F6;color:#555";
				var td2=document.createElement("td");
				td2.style.cssText = "text-align:left;padding-left:5px;padding-right:5px;"
				td1.innerHTML = calbean.time;
				td2.innerHTML = calbean.value;
				otr.appendChild(td1);
				otr.appendChild(td2);
				
				DWRUtil.setValue("newdate",calbean.newdate);
				DWRUtil.setValue("year",calbean.year);
				DWRUtil.setValue("month",calbean.month);
				DWRUtil.setValue("day",calbean.day);
			}
		}
		
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
	var datetime = index.id;
	datetime = datetime.substring(8,18)+' '+'<%=UtilWork.getNowTime().substring(11, UtilWork.getNowTime().length())%>';
	var url = '<%=contextPath%>/erp/work_arrange/workCalender_add.jsp?calendertype=1&startTime='+datetime;
	openMDITab(url + "&tab="+getMDITab());
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

function setdate(){
	var date = DWRUtil.getValue("date");
	DWRUtil.setValue("newdate",date);
	var year = document.getElementById("newyear").value;
	var month = document.getElementById("newmonth").value;
	var day = document.getElementById("newday").value;
	dwrWorkArrangeService.listOaCalenderWeek(year,month,day,0,dateback);
}

function changeview(){
	Sys.load("work_arrange/workCalender_list.jsp"); 
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
<body style="overflow: hidden">
	<div style="display:none;" id='functiondiv'>
		<ul>
			<li id='info'><img src="<%=contextPath%>/images/grid_images/info.png"> <label id="label"></label></li>
			<li id='edit'><img src="<%=contextPath%>/images/grid_images/edit.png"> 编辑</li>
			<li id='del'><img src="<%=contextPath%>/images/grid_images/close.png"> 删除</li>
		</ul>
	</div>
	<input type="hidden" id="year" value="<%=year%>">
	<input type="hidden" id="month" value="<%=month%>">
	<input type="hidden" id="day" value="<%=day%>">
	<input type="hidden" id="date" value="<%=date%>">
	<input type="hidden" id="newyear" value="<%=year%>">
	<input type="hidden" id="newmonth" value="<%=month%>">
	<input type="hidden" id="newday" value="<%=day%>">
	<table height="100%" width="100%" cellpadding="0" cellspacing="0" border="0">
		<tr style="width: 100%">
			<td style="padding-bottom: 5px; padding-top: 5px;">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td align='left' style='padding-left: 15px;' width="20px"><a title='上一周' onclick='init(-7)' href="javascript:void(0)" style="cursor: pointer; font: 12px '宋体'; text-decoration: none;">&nbsp;<<&nbsp;</a></td>
						<td id="newdate" align='left' width="75px"></td>
						<td align='left' width="20px"><a title='下一周' onclick='init(7)' href="javascript:void(0)" style="cursor: pointer; font: 12px '宋体'; text-decoration: none;">&nbsp;>>&nbsp;</a></td>
						<td style='padding-left: 30px;'><btn:btn onclick="setdate()" value=" 本 周 " width="50px"></btn:btn></td>
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
				<div style="overflow: auto; height: 100%; border-top: 1px outset #ffffff; width: 100%">
					<sys:table tableTitle="周列表" width="100%" id="resultTable">
						<td class='tableTitle1' width="70" id="time" nowrap="nowrap">时间</td>
						<td class='tableTitle1' id="value">内容</td>
					</sys:table>
				</div></td>
		</tr>
	</table>
</body>
</html>