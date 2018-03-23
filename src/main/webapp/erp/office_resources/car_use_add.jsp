<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String carusepk =request.getParameter("carusepk");
String url =request.getParameter("url");
String carname = request.getParameter("carname");
if(carname!=null)
	carname = URLDecoder.decode(carname,"UTF-8");
String carid = request.getParameter("carid");
if(carid!=null)
	carid = URLDecoder.decode(carid,"UTF-8");
String begindate = request.getParameter("begindate");
if(begindate!=null)
	begindate = URLDecoder.decode(begindate,"UTF-8");
String enddate = request.getParameter("enddate");
if(enddate!=null)
	enddate = URLDecoder.decode(enddate,"UTF-8");

SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
String cudate = sf.format(new Date());

 %>
<title>车辆使用登记</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","预定车辆，您可以在此处预定车辆使用，采取先预定先用的原则。");
	if(<%=carusepk%> != null){
		dwrOfficeResourcesService.getCaruseByPk(<%=carusepk%>,setCaruseinfo);
		//返回列表按钮隐藏
     Btn.hidden("backToList");
 	}else{
 		Btn.hidden("backbtn");
 	}
 	if("<%=carname %>"!=null && "<%=carname %>"!="null") {
	 	DWRUtil.setValue("car",'<%=carname %>');
	 	DWRUtil.setValue("carid",'<%=carid %>');
	 	DWRUtil.setValue("applyBegindate","<%=begindate%>");
     	DWRUtil.setValue("applyEnddate","<%=enddate%>");
 	}
}


function backToList() {
	window.location = "car_app.jsp";
}

function save(){
     var bl = validvalue('helpTitle');
     if(bl){
     	var caruse = getCaruseinfo();
     	
     	Btn.close();
		dwrOfficeResourcesService.saveCaruse(caruse,saveCallback);
	 }
}
function saveCallback(data){
	Btn.open();
	if(<%=carusepk%> != null){
    	alertmsg(data,"returnload()");
    }else{
    	if(data.success){
    		confirmmsgAndTitle("车辆预定成功！是否想继续添加？","reset();","继续添加","closePage();","关闭页面"); 
    	}else{
    		alertmsg(data);
    	}
    }
}
function reset(){
	DWRUtil.setValue("car","");
	DWRUtil.setValue("carid","");
	DWRUtil.setValue("applyBegindate","");
    DWRUtil.setValue("applyEnddate","");
    DWRUtil.setValue("applyNum","");
    DWRUtil.setValue("applyPhone","");
    DWRUtil.setValue("applyTask","");
    DWRUtil.setValue("applyUser","");
    DWRUtil.setValue("applyUserid","");
}
function returnload(){
	window.parent.MoveDiv.close();
   	window.parent.queryData();
}
function setCaruseinfo(data){
	if(data.success&&data.resultList.length > 0){
 		 var caruse = data.resultList[0];
 		 DWRUtil.setValue("car",caruse.oaCar.oaCarName);
 		 DWRUtil.setValue("carid",caruse.carId);
 		 DWRUtil.setValue("applyBegindate",caruse.applyBegindate);
	     DWRUtil.setValue("applyEnddate",caruse.applyEnddate);
	     DWRUtil.setValue("applyNum",caruse.applyNum);
	     DWRUtil.setValue("applyPhone",caruse.applyPhone);
	     DWRUtil.setValue("applyTask",caruse.applyTask);
	     DWRUtil.setValue("applyUser",caruse.applyEmployee.hrmEmployeeName);
	     DWRUtil.setValue("applyUserid",caruse.applyUser);
 	}else{
 		alertmsg(data.message);
 	}
	}

function getUser(){
	if(<%=carusepk%> != null){
		var box = SEL.getEmployeeIds("radio","applyUser","applyUserid","processloadfrm");
		box.show();
	}else{
		var box = SEL.getEmployeeIds("radio","applyUser","applyUserid");
		box.show();
	}
  }


function getcar(){
	if(<%=carusepk%> != null){
		var box = SEL.getCarIds("radio","car","carId","processloadfrm");
		box.show();
	}else{
		var box = SEL.getCarIds("radio","car","carId");
		box.show();
	}
}

function getCaruseinfo(){
   var caruse = new Object();
  	if(<%=carusepk%> != null){
    		caruse.primaryKey = <%=carusepk%>;
  	}
   caruse.applyUser = DWRUtil.getValue("applyUserid");
   caruse.applyPhone = DWRUtil.getValue("applyPhone");
   caruse.applyTask = DWRUtil.getValue("applyTask");
   caruse.applyBegindate = DWRUtil.getValue("applyBegindate");
   caruse.applyEnddate = DWRUtil.getValue("applyEnddate");
   caruse.applyNum = DWRUtil.getValue("applyNum");
   caruse.carId = DWRUtil.getValue("carid");
   caruse.applyTask = DWRUtil.getValue("applyTask");
   caruse.oaCarStatus=1;
   return caruse;
}
  
function reback() {
 if("<%=carname %>"!=null && "<%=carname %>"!="null") {
 		window.location='seletcar.jsp';
 } else 
 		window.location='car_app.jsp';
}
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"personfrm");
}
   
function changestatr(obj){
	document.getElementById("applyEnddate").value = obj.value;
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">车辆预定</div>
		<div>
			<table class="inputtable" border="0">
				<tr>
					<th width="15%">
					<em>* </em>使用车辆
					</th>
					<td style="text-align: left"  width="20%">
						<input type="text" class="takeform" linkclear="carId" readonly="readonly" id="car" title="点击选择车辆信息。" onclick="getcar();" must="请选择公司车辆。"  formust="carMust" style="color: #999;">
						<input type="hidden" id="carid" value="">
						<label id="carMust"></label>
					</td>
			     	<th>
					用车时间
					</th>
					     <td style="text-align: left">
					    	<input type="text" id="applyBegindate" readonly="readonly" class="Wdate" onchange="changestatr(this)" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false})" value="<%=cudate %>">
					    	 - <input type="text" id="applyEnddate" readonly="readonly" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false,minDate:'#F{$dp.$D(\'applyBegindate\')}'})">
				    </td> 
				</tr>
				<tr>
					<th width="15%">
					  <em>* </em>用&nbsp;车&nbsp;人
					</th>
				     <td style="text-align: left"  width="30%">
			    	   <input type="text" class="takeform" linkclear="applyUser" readonly="readonly" id="applyUser" title="点击选择人员信息。" onclick="getUser()"  must="请选择用车人用车人。" formust="applyUserMust" style="color: #999;">
		        	   <input type="hidden" id="applyUserid" value="">
		        	   <label id="applyUserMust"></label>
	        	   </td>
					<th>联系电话</th>
						<td style="text-align: left;"><input type="text" id="applyPhone" class="numform" maxlength="14">
						<label id="applyPhoneMust"></label>
					</td>
				</tr>
				<tr>
					<th><em>* </em>用车人数</th>
					<td style="text-align: left;"><input type="text" id="applyNum"  class="numform" maxlength="3" must="请输入用车人数。" formust="appluNumMust">
					<label id="appluNumMust"></label>
					</td>
			    </tr>
				<tr>	
				</tr>
				<tr>
				<th>出车任务</th>
				<td style="text-align: left;" colspan="3">
					<textarea style="height: 120px;" id = "applyTask"></textarea> </td>
					<td></td>
				</tr>
			</table>
		</div>
		<br>
</div>

<br/>
<table align="center">
   	<tr>
     	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
     	<td style="width: 10px;"></td>
     	<td><div  id="backbtn"><btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></div>
		<DIV id ="backToList"><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></DIV>
		</td>
   	</tr>
</table>
</body>
</html>