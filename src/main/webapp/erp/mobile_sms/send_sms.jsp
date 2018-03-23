<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script>
<title>发送短信</title>
<%
String type = request.getParameter("type"); // 1 从收件箱传过来 2 从发件箱传过来 3从在线人员传过来
String oaSmsSendid =request.getParameter("oaSmsSendid");
String oaSmsInboxid =request.getParameter("oaSmsInboxid");
String oaSmsInboxbackid =request.getParameter("oaSmsInboxbackid");
String choose = request.getParameter("choose");
String oaSmsOnlineid =request.getParameter("oaSmsOnlineid");
String sta=request.getParameter("sta");
 %>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	initInput('title',"您可以在此处发送您想要发送的公司短信，进入收件箱查阅他人发送的短信！");
	saveOredit();
	document.getElementById("oaSmsSendAcpempName").focus();
	if(<%=choose%>!=null){
		document.getElementById("btncancel").style.display="none";
	}
}

 function  saveOredit(){
    if(<%=oaSmsSendid%> != null){
	var primaryKey = <%=oaSmsSendid%>;
	dwrMoblieSmsService.getOaSmsSendByPk(primaryKey,setOaSmsSendinfo);
	}
	 if(<%=oaSmsInboxid%> != null){
	
	var primaryKey = <%=oaSmsInboxid%>;
	dwrMoblieSmsService.getOaSmsInboxByPk(primaryKey,setOaSmsInboxinfo);
	}
	 if(<%=oaSmsInboxbackid%> != null){
	var primaryKey = <%=oaSmsInboxbackid%>;		
	dwrMoblieSmsService.getOaSmsInboxByPk(primaryKey,setOaSmsInboxbackinfo);
	}
 	if('<%=oaSmsOnlineid%>' != 'null' && <%=type%> == 3){	
	var primaryKey = '<%=oaSmsOnlineid%>';
		dwrMoblieSmsService.listEmployee(primaryKey,setOnlineInfo);
	}
}




function setOnlineInfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var employee = data.resultList[0];
 			DWRUtil.setValue("oaSmsSendAcpemp",employee.primaryKey+",");
            DWRUtil.setValue("oaSmsSendAcpempName",employee.hrmEmployeeName+",");	
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}


function setOaSmsSendinfo(data){
    if(data.success == true){

 		if(data.resultList.length > 0){

 			var oaSmsSend = data.resultList[0];
 		
				DWRUtil.setValue("oaSmsSendContent",oaSmsSend.oaSmsSendContent);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function setOaSmsInboxinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var oaSmsInbox = data.resultList[0];
 			DWRUtil.setValue("oaSmsSendContent",oaSmsInbox.oaSmsInboxContent);
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

function setOaSmsInboxbackinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var oaSmsInbox = data.resultList[0];
 			DWRUtil.setValue("oaSmsSendAcpemp",oaSmsInbox.oaSmsInboxSenderid+",");
            DWRUtil.setValue("oaSmsSendAcpempName",oaSmsInbox.oaSmsInboxSenderName+",");	
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

	
	function getSmsSendinfo(){
		var smsSend = new Object();
		smsSend.oaSmsSendAcpemp = DWRUtil.getValue("oaSmsSendAcpemp");
		smsSend.oaSmsSendAcpempName = DWRUtil.getValue("oaSmsSendAcpempName");
		smsSend.oaSmsSendContent = DWRUtil.getValue("oaSmsSendContent");
		return smsSend;
   }
   
   function save(){
	   	var warnArr = new Array();
		warnArr[0] = "oaSmsSendAcpempNamemust";
	    warnArr[1]="oaSmsSendContentmust";
	   	warnInit(warnArr);
		if(document.getElementById("oaSmsSendAcpemp").value == ""){
		    setMustWarn("oaSmsSendAcpempNamemust","请选择收件人。");
			return false;
		}
		
       if(document.getElementById("oaSmsSendContent").value == ""){
			setMustWarn("oaSmsSendContentmust","请输入短信内容。");
			return false;
		}
        dwrMoblieSmsService.saveSmsSend(getSmsSendinfo(),saveCallback);
        Btn.close();
	}
	
	function saveCallback(data){
	    Btn.open();
	    if(<%=choose%>!=null){
	    	alertmsg(data,"closePage()");
	    }else{
	        if(<%=oaSmsSendid%>!=null || <%=oaSmsInboxid%> != null||<%=oaSmsInboxbackid%> != null||'<%=oaSmsOnlineid%>'!='null'||<%=type%>!=null){
	        	    confirmmsgAndTitle("发送短信成功！是否想继续发送短信?","cleanup();","继续添加","closePage();","关闭页面");
	        }else{
	        	if(data.success){
		     		confirmmsgAndTitle("发送短信成功！是否想继续发送短信?","cleanup();","继续添加","backToNewsList();","返回列表");
				}else{
					alertmsg(data);
				}
			}
		}
    }
    function returnload(){
    	window.parent.MoveDiv.close();
    }
    function cleanup(){
     DWRUtil.setValue("oaSmsSendAcpemp","");
     DWRUtil.setValue("oaSmsSendAcpempName","");
	 DWRUtil.setValue("oaSmsSendContent","");
    }
   
   function reback(){
     if(<%=type%> != null && <%=type%> == 1){
       Sys.load("<%=contextPath%>/erp/mobile_sms/inbox.jsp",'sms');
     }else if(<%=type%> != null && <%=type%> == 2){
       Sys.load("<%=contextPath%>/erp/mobile_sms/outbox.jsp",'sms');
     }else if(<%=type%> != null && <%=type%> == 3){
       Sys.load("<%=contextPath%>/erp/personal_work/online.jsp");
     }
   }
  function getemployee(){
		if(<%=choose%>!=null){
			var box = SEL.getEmployeeIds("check","oaSmsSendAcpempName","oaSmsSendAcpemp");
			box.show();
		}else{
			if('<%=oaSmsSendid%>' != 'null' || '<%=oaSmsInboxid%>' != 'null' || '<%=oaSmsInboxbackid%>' != 'null' || '<%=oaSmsOnlineid%>' != 'null'){
				var box = SEL.getEmployeeIds("check","oaSmsSendAcpempName","oaSmsSendAcpemp");
				box.show();
			}else{
				var box = SEL.getEmployeeIds("check","oaSmsSendAcpempName","oaSmsSendAcpemp","sms");
				box.show();
			}
		}
   }

function backToNewsList(){
	if(<%=sta%>==null || <%=sta%>==""){
		Sys.href('<%=contextPath%>/erp/mobile_sms/inbox.jsp');
	}else if('<%=sta%>'==1){
	    Sys.href('<%=contextPath%>/erp/mobile_sms/outbox.jsp');
	}else if('<%=sta%>'==2){
		Sys.href('<%=contextPath%>/erp/mobile_sms/inbox.jsp');
	}
}
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
		<div class="formTitle">发送短信</div>
	<div>
	
	<table class="inputtable">
	<tr>
	<th></th>
	<td>
	<label id="oaSmsSendAcpempNamemust"></label>
	</td>
	</tr>
	<tr>
		<th width="15%"><em>* </em>收件人</th>
		<td>
		<textarea  id="oaSmsSendAcpempName" linkclear="oaSmsSendAcpemp" title="点击获取收件人信息。" readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
		<input type="hidden" id="oaSmsSendAcpemp" value="">
		</td>
	</tr>			
	<tr>
	  <th width="15%">发送方式</th>
	  <td>
	    <input type="checkbox" id="moblie"><label for="moblie">同时发送手机短信</label>
	  </td>
	</tr>
<tr>
<th></th>
<td>	<label id="oaSmsSendContentmust"></label></td>
</tr>
	<tr>
	<th><em>* </em>短信内容</th>
	<td>
	 <textarea id="oaSmsSendContent" style="height: 200px;"></textarea> 
	</td>
	</tr>
	</table>
	</div>
	<br/>
</div>

<br/>
<center>
<table>
<tr>
<td><btn:btn onclick="save()" value="发 送 " imgsrc="../../images/fileokico.png" title="发送信息"></btn:btn></td>
<td width="10px"></td>
<td id="btncancel">
	<%if (type != null){ %>
		<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
		<btn:btn onclick="backToNewsList();" value="返 回 " imgsrc="../../images/back_cir.png" title="返回"></btn:btn>
	<%} %>
 </td>
</tr>
</table>
</center>
</body>
</html>