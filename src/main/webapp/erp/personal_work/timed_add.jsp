<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script>
<%
String pk =request.getParameter("timedID");
String type = request.getParameter("type");

 %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增提醒</title>
<script type="text/javascript">
var selValue;
window.onload = function() {
	useLoadingMassage();
	initInput('helpTitle','您可以新增每日定时提醒或只是提醒一次');
	if('<%=type%>'=='1'){
  		document.getElementById("btndiv").style.display="none";
  		$("#timed1").hide();
		selValue=0;
  	}else{
		if ( <%=pk %>!=null) {
			dwrPersonalOfficeService.getTimedRecordByPk( <%=pk%>, setTimed);
		} else {
			$("#timed1").hide();
			selValue=0;
		}
	}
}


function setTimed(data) {
	if(data != null){
		if(data.resultList.length>0){
		    var timedRecord = data.resultList[0];
		    setRadioValueByName("remindType",timedRecord.timedType);
		    if(timedRecord.timedType =='<%=EnumUtil.TIMED_TYPE.Vaild.value%>'){
				$("#timed1").hide();
				selValue=0;
				DWRUtil.setValue("timedDate0",timedRecord.timedDate);
			}else{
			    $("#timed0").hide();
			    selValue=1;
			    DWRUtil.setValue("timedDate1",timedRecord.timedDate);
			}
		        DWRUtil.setValue("timedDescription",timedRecord.timedDescription);
		} else {
			alert(data.message);
		}
	}
}

function validvalue1(requtid){
	var lb =document.getElementById(requtid);
	var ips;
	if(selValue==0) {
		ips = document.getElementById("timedDate0");
	} else {
		ips = document.getElementById("timedDate1");
	}
	var sels = document.getElementsByTagName("select");
	var tas = document.getElementsByTagName("textarea");
	for(var b=0;b<sels.length;b++){
		if(sels[b].options.length==0){
			lb.innerHTML = "<font color=\"red\">请确保下拉列表已经初始化!</font>";
			window.scrollTo(0,0);
			return false;
		}
	}
	if(ips!=null && ips != "undefined" && ips != undefined) {
		var type = ips.type;
		var ms = ips.getAttribute("must");
		var msTitle = ips.getAttribute("formust");
		if(msTitle != null && msTitle!="" && trim(msTitle).length>0){
			lb = document.getElementById(msTitle);
		}
		if(ms!=null&&ms.length>0){
			if(type=="text" || type == "password"){
				var vl = ips.value;
				if(vl==""||trim(vl).length==0){
					lb.innerHTML = "<img src=\""+Sys.getProjectPath()+"/images/grid_images/rowdel.png\">&nbsp;<font color=\"red\">" + ms + "</font>";
					//ips.focus();
					return false;
				}else{
					lb.innerHTML="";
				}
			}
		}
	}
	//select
	for(var b=0;b<sels.length;b++){
		var ms = sels[b].getAttribute("must");
		var msTitle = sels[b].getAttribute("formust");
		if(msTitle != null && msTitle!="" && trim(msTitle).length>0){
			lb = document.getElementById(msTitle);
		}
		if(ms!=null&&ms.length>0){
			if(sels[b].value == null||sels[b].value==-1){
				lb.innerHTML = ms;
				sels[b].focus();
				return false;
			}else{
				lb.innerHTML="";
			}
		}
	}
	//textarea
	for(var a=0;a<tas.length;a++){
		var ms = tas[a].getAttribute("must");
		var msTitle = tas[a].getAttribute("formust");
		if(msTitle != null && msTitle!="" && trim(msTitle).length>0){
			lb = document.getElementById(msTitle);
		}
		if(ms!=null&&ms.length>0){
			var vl = tas[a].value;
			if(vl==""||trim(vl).length==0){
				lb.innerHTML = ms;
				tas[a].focus();
				return false;
			}
		}else{
			lb.innerHTML="";
		}
	}
	//input
	for(var c=0;c<ips.length;c++){
		var cln = ips[c].className;
		var msTitle = ips[c].getAttribute("formust");
		if(msTitle != null && msTitle!="" && trim(msTitle).length>0){
			lb = document.getElementById(msTitle);
		}
		if(cln=="numform" || cln == "rmbform"){
			var vl = ips[c].value;
			if(vl!=null&&trim(vl).length>0){
				if(!isDigit(vl)){
					lb.innerHTML = "<img src=\""+Sys.getProjectPath()+"/images/grid_images/rowdel.png\">&nbsp;<font color=\"red\">数字框只能输入数字。</font>";
					//ips[c].focus();
					return false;
				}
			}else{
				lb.innerHTML="";
			}
		}
	}
	return true;
}

function save() {
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "timedDate1must";
	warnArr[1] = "timedDate0must";
	warnArr[2] = "remindCon";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue1('helpTitle');
	if (bl) {
		var content = DWRUtil.getValue("timedDescription");
		if(document.getElementById("timedDescription").value==""||trim(content)==0){
			setMustWarn("remindCon","请输入提醒内容。");
			document.getElementById("timedDescription").focus();
			return;
		}
		if(content.length < 5 || content.length > 500){
		
			setMustWarn("remindCon","请输入5个以上、500个以下的字符。");
			document.getElementById("timedDescription").focus();
			return;
		}
		
		var timedRecord = getTimedRecord();
	    var timed;
		if(selValue==0) {
			if(<%=pk%>!=null){
		    	dwrPersonalOfficeService.saveTimedRecord(timedRecord,updateOaRefisterout);
	    	} else {
	    		dwrPersonalOfficeService.saveTimedRecord(timedRecord,saveTimedRecord);
	    	}
		} else {
			timed = document.getElementById("timedDate1");
		    var end = timed.value.split(" ");
		    var start = getFullCurrentDate().split(" ");
		    var pass = true;
		    if(start[0] > end[0]){      //比较日期
		        pass = false;
		    }else if(start[0] == end[0]){
		        var time1 =  start[1].split(":");
		        var time2 = end[1].split(":");
		        if(time1[0] > time2[0]){                   //比较时间（小时）
		             pass = false;
		        }else if(time1[0] == time2[0]){            //（分钟）
		             if(time1[1] >= time2[1]){
		                 pass = false;
		             }
		        }else{
		             pass = true;
		        }
		    }else{
		        pass = true;
		    }
		    if(pass) {
		    	if(<%=pk%>!=null){
			    	dwrPersonalOfficeService.saveTimedRecord(timedRecord,updateOaRefisterout);
			    	Btn.close();
		    	} else {
		    		dwrPersonalOfficeService.saveTimedRecord(timedRecord,saveTimedRecord);
		    		Btn.close();
		    	}
		    } else {
		    	setMustWarn("timedDate1must","时间不能早于当前时间");
				document.getElementById("timedDate1").focus();
				window.scrollTo(0,0);
				return false;
		    }
		}
	}
}

function getTimedRecord(){
	var timedRecord = new Object();
	if(<%=pk%>!=null){//编辑
		timedRecord.primaryKey = <%=pk%>;
	}
	    timedRecord.timedDescription = DWRUtil.getValue("timedDescription");
	    timedRecord.timedType = getRadioValueByName("remindType");
	    if(selValue ==0 ) {
	    	timedRecord.timedDate = DWRUtil.getValue("timedDate0");
	    }else {
	    	timedRecord.timedDate = DWRUtil.getValue("timedDate1");
	    }
	    
	return timedRecord;
}

function updateOaRefisterout(data) {
    Btn.open();
	alertmsg(data, "reload()");
}

function reload() {
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function saveTimedRecord(data) {
    Btn.open();
	if(data.success){
		confirmmsgAndTitle("添加定时提醒成功！是否想继续添加？","reset();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}

function closePage(){
	if ( <%=pk %>!=null) {
		window.parent.MoveDiv.close();
		window.parent.queryData();
	}else{
		closeMDITab(<%=request.getParameter("tab")%>,"timedfrm");
	}
}

function reset() {
	DWRUtil.setValue("timedDate0", "");
	DWRUtil.setValue("timedDate1", "");
	DWRUtil.setValue("timedDescription", "");
	//setSelectValue("timedType", "");
	refreshMDITab(<%=request.getParameter("tab")%>);
}

function changeTimedType(value) {
	//$("#timed0").animate({height: 'hide',   opacity: 'hide'  }, 'slow');
	selValue = value;
	if(value==1) {
		$("#timed1").show();
		$("#timed0").hide();
	} else {
		$("#timed0").show();
		$("#timed1").hide();
	}
}
function show1(){
	$("#timed0").show();
	$("#timed1").hide();
	selValue=0;
}
function show2(){
	$("#timed1").show();
	$("#timed0").hide();
	selValue=1;
}
var myfrmname =null;
var mymethod = null;
var mydialogId = null;
function savedesktop(dialogId,frm,method){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "timedDate1must";
	warnArr[1] = "timedDate0must";
	warnArr[2] = "remindCon";
	//清空所有信息提示
	warnInit(warnArr);
	myfrmname = frm;
	mymethod = method;
	mydialogId = dialogId;
	var bl = validvalue1('helpTitle');
	if (bl) {
		var content = DWRUtil.getValue("timedDescription");
		if(document.getElementById("timedDescription").value==""||trim(content)==0){
			setMustWarn("remindCon","请输入提醒内容。");
			document.getElementById("timedDescription").focus();
			return;
		}
		if(content.length < 5 || content.length > 500){
		
			setMustWarn("remindCon","请输入5个以上、500个以下的字符。");
			document.getElementById("timedDescription").focus();
			return;
		}
		var timedRecord = getTimedRecord();
	    var timed;
		if(selValue==0) {
	    	dwrPersonalOfficeService.saveTimedRecord(timedRecord,saveDesktopCallback);
		} else {
			timed = document.getElementById("timedDate1");
		    var end = timed.value.split(" ");
		    var start = getFullCurrentDate().split(" ");
		    var pass = true;
		    if(start[0] > end[0]){      //比较日期
		        pass = false;
		    }else if(start[0] == end[0]){
		        var time1 =  start[1].split(":");
		        var time2 = end[1].split(":");
		        if(time1[0] > time2[0]){                   //比较时间（小时）
		        	pass = false;
		        }else if(time1[0] == time2[0]){            //（分钟）
					if(time1[1] >= time2[1]){
					    pass = false;
					}
		        }else{
		        	pass = true;
		        }
		    }else{
		        pass = true;
		    }
		    if(pass) {
	    		dwrPersonalOfficeService.saveTimedRecord(timedRecord,saveDesktopCallback);
		    } else {
		    	setMustWarn("timedDate1must","时间不能早于当前时间");
				document.getElementById("timedDate1").focus();
				return false;
		    }
		}
	}
}

function saveDesktopCallback(data){
	var win = Sys.getfrm();//获取index页面iframe window对象
    if(isNotBlank(myfrmname)){
    	win = win.document.getElementById(myfrmname).contentWindow;	
    }
    eval("win."+mymethod);
    Sys.close(mydialogId);
}
</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">定时提醒</div>
	<div>	
		<table class="inputtable">
			<tr>
				<th>提醒类型</th>
				<td style="text-align: left;">
					<input type="radio" name="remindType" value="0" checked="checked" onclick="show1()" id="reType1"><label for="reType1">每天</label>
				    <input type="radio" name="remindType" value="1" onclick="show2()" id="reType2"><label for="reType2">一次</label>
				</td>
			</tr>
			<tr id="timed1">
				  <th><em>*</em>&nbsp;提醒时间</th>
				  <td><input id="timedDate1" type="text" readonly="readonly" class="Wdate" must="请输入日期时间" formust="timedDate1must" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:{%m+5}'})"></td>
				  <td><label id="timedDate1must"></label></td>
			</tr>
			<tr id="timed0">
				<th><em>*</em>&nbsp;提醒时间</th>
				<td>
					<input id="timedDate0" type="text" readonly="readonly" class="Wtime" must="请输入时间" formust="timedDate0must" onfocus="WdatePicker({dateFmt:'HH:mm'})" />
				</td>
				<td><label id="timedDate0must"></label></td>
			</tr>
			<tr ><th></th><td><label id="remindCon"></label></td></tr>
			<tr>	
				<th><em>*</em>&nbsp;提醒内容</th>
				<td colspan="3">
				<textarea style="height: 180px;" id ="timedDescription" ></textarea>
				</td>
			</tr>
		</table>	
	</div>
	<br>
</div>
<div id="btndiv">
<br>
<br/>
<table align="center">
  	<tr>
    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    	<td style="width: 10px;"></td>
    		<td>
			<%if (type == null){ %>
			<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%}else{ %>
			<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			<%} %>
			</td>
    	
  	</tr>
</table>
</div>
</body>
</html>