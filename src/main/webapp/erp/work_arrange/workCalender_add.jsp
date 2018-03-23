<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
		<title>新增安排</title>
		<%
			response.setHeader("Cache-Control","no-cache"); 
			response.setHeader("Pragma","no-cache"); 
			response.setDateHeader("Expires",0);
			String calenderpk = request.getParameter("calenderpk");
			String calendertype = request.getParameter("calendertype");
			String startTime = request.getParameter("startTime");
		%>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","工作安排，您可以在此处添加您想要发布的工作安排。");
	saveOredit();
	setTime();
	if(<%=calenderpk%> == null && <%=calendertype%> == null ){
	}
}
	
function getCalenderinfo(){
	var oaCalender = new Object();
	if(<%=calenderpk%> != null){
	oaCalender.primaryKey = <%=calenderpk%>;
	}  
	oaCalender.oaCalenderLevel = DWRUtil.getValue("oaCalenderLevel");
	oaCalender.oaCalenderType = DWRUtil.getValue("oaCalenderType");
	oaCalender.oaCalenderStart =  DWRUtil.getValue("oaCalenderStart");
	oaCalender.oaCalenderEnd = DWRUtil.getValue("oaCalenderEnd");
	oaCalender.oaCalenderContent = DWRUtil.getValue("oaCalenderContent");
	return oaCalender;
}

function save(){ 
	var warnArr = new Array();
	warnArr[0] ="oaCalenderEndMust";
	warnInit(warnArr);
	var validResult = validvalue('helpTitle');
	if(validResult){
		var startTime = document.getElementById("oaCalenderStart").value;
		var endTime = document.getElementById("oaCalenderEnd").value;
		if(startTime > endTime){
			setMustWarn("oaCalenderEndMust","计划开始时间不能大于结束时间。");
			window.scrollTo(0,0);
		}else{
			if(<%=calenderpk%> != null){
				Btn.close();
				dwrWorkArrangeService.saveOaCalender(getCalenderinfo(),updataCallback);
			}else{
				Btn.close();
				dwrWorkArrangeService.saveOaCalender(getCalenderinfo(),saveCallback);
			}
		}
	}
}
	
function updataCallback(data){
	alertmsg("修改工作安排成功！","reSet();");
}
	
function saveCallback(data){
	Btn.open();
	if(data.success){
		confirmmsgAndTitle("添加安排成功！是否想继续添加？","reSet();","继续添加","backToWorkCalenderList();","关闭页面");
	}else{
		alertmsg(data);
	}
}
    
function backToWorkCalenderList(){
	var startTime='<%=startTime%>';
	if(<%=calenderpk%> != null && startTime != 'null'){
		reloadpager();
	}else{
		closePage();
	}
}
	
function reSet(){
	if(<%=calenderpk%> != null){
		reloadpager();
	}else{
		repload();
	}   
}

function reloadpager(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function repload(){
    DWRUtil.setValue("oaCalenderLevel",1);
	DWRUtil.setValue("oaCalenderType",-1);
	DWRUtil.setValue("oaCalenderStart","<%=UtilWork.getNowTime()%>");
	DWRUtil.setValue("oaCalenderEnd","");
	DWRUtil.setValue("oaCalenderContent","");
	DWRUtil.setValue("ContentMust","");
	DWRUtil.setValue("oaCalenderEndMust","");
    document.getElementById("oaCalenderContent").focus();
}
    
function  saveOredit(){
	if(<%=calenderpk%> != null){
		var primaryKey = <%=calenderpk%>;
		dwrWorkArrangeService.getOaCalenderByPk(primaryKey,setOaCalenderinfo);
	} 
}

function setOaCalenderinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var oaCalender = data.resultList[0];
 			setRadioValueByName("oaCalenderLevel",oaCalender.oaCalenderLevel);
 			DWRUtil.setValue("oaCalenderType",oaCalender.oaCalenderType);
 			DWRUtil.setValue("oaCalenderStart",oaCalender.oaCalenderStart);
 			DWRUtil.setValue("oaCalenderEnd",oaCalender.oaCalenderEnd);
 			DWRUtil.setValue("oaCalenderContent",oaCalender.oaCalenderContent);
 			
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function closePage(){ 
	closeMDITab(<%=request.getParameter("tab")%>);
}


function setTime(){
    var startTime='<%=startTime%>';
    if(startTime != 'null'){
		DWRUtil.setValue("oaCalenderStart",startTime);
		DWRUtil.setValue("oaCalenderEnd",null);
    }else{
		startTime='<%=UtilWork.getNowTime()%>';
		DWRUtil.setValue("oaCalenderStart",startTime);
    }
}

function returnload(){
	window.parent.MoveDiv.close();
}
	
function selectTime(){
	var time="";
	var startTime='<%=startTime%>';
	if(<%=calenderpk%>  != null ){
		time = WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'oaWorkplanEnd\')}'});
	}else if(startTime != 'null'){
		time = WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
	}else{
		time = WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'%y-%M-%d %h-%m-01'});
	}
	return time;
}	


</script>
	</head>
	<body class="inputcls">
		<div class="formDetail">
			<div class="requdiv">
				<label id="helpTitle"></label>
			</div>
			<div class="formTitle">
				工作安排内容
			</div>
				<table class="inputtable">

					<tr>
						<th width="20%">
							优先级
						</th>
						<td>
							<%=UtilTool.getRadioOptionsByEnum(EnumUtil.OA_CALENDER_LEVEL.getSelectAndText(""), "oaCalenderLevel")%>
						</td>
						<td></td>
					</tr>

					<tr>
						<th width="20%">
							日程类型
						</th>
						<td width="40%">
							<select id="oaCalenderType">
								<%=UtilTool.getSelectOptions(this.getServletContext(), request, null, "10")%>
							</select>
						</td>
						<td width="40%"></td>
					</tr>

					<tr>
						<th width="20%">
							<em>*&nbsp;</em>工作周期
						</th>
						<td width="40%" align="right">
							<input type="text" readonly="readonly" id="oaCalenderStart"
								class="Wdate" onClick="selectTime()"
								onchange="onStrTimeChange()">
								
							至
							<input type="text" readonly="readonly" id="oaCalenderEnd"
								class="Wdate"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'oaCalenderStart\',{H:1})}'})"
								value="" must="工作结束日期不能为空。" formust="oaCalenderEndMust">
						</td>
						<td width="40%">
							<label id="oaCalenderEndMust"></label>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<label id="ContentMust"></label>
						</td>
					</tr>
					<tr>
					<tr>
						<th>
							<em>*&nbsp;</em>内容
						</th>
						<td colspan="3">
							<textarea style="height: 200px" id="oaCalenderContent"
								must="工作安排内容部能为空" formust="ContentMust"></textarea>
						</td>
					</tr>
				</table>
		</div>
		<table align="center">
			<tr>
				<td>
					<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
				</td>
				<td style="width: 10px;"></td>
					<td>
					<%if (calenderpk == null){ %>
					<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
					<%}else{ %>
					<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
					<%} %>
					</td>
			</tr>
		</table>
	</body>
</html>