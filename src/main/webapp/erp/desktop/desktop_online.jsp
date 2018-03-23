<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="<%=contextPath%>/dwr/interface/dwrOADesktopService.js"></script>
<title>在线人员</title>
<script type="text/javascript">
	var t = null;
	window.onload = function(){
		t = window.setInterval("queryData()",1000*30);
		queryData();
	}
	
	window.onunload = function(){
		if(t!=null){
			window.clearInterval(t);
		}
	}
	function queryData(){
		var bl = true;
		dwrOADesktopService.getOnlineEmployee(queryCallback);
	}
	
	function queryCallback(data){
		var retab = document.getElementById("onlinetable");
		if(retab!=null&&retab != undefined&& retab != "undefined"){
			var rlen = retab.rows.length;	
			for(var i=rlen-1;i>=0;i--){
				retab.deleteRow(i);
			}
		}
		for(var i=0;i<data.length;i++){
			var obj = data[i];
			var tr = retab.insertRow(-1);
			if(i%2==0){
				tr.style.cssText ="background-color:#ededed";
			}else{
				tr.style.cssText ="background-color:#fefefe";
			}
			var td1 = document.createElement("td");
			td1.style.cssText="padding-left:5px;padding-right:5px;text-align:center";
			var imgsrc = "<%=contextPath%>/showimg.do?imgId="+obj.imageId;
			td1.innerHTML ="<img src='"+imgsrc+"' border='0' width='16px' height='16px'/>";
			var td2 = document.createElement("td");
			td2.style.cssText="padding-left:5px;text-align:left;width:100px;";
			td2.innerHTML ="<div style='overflow:hidden;white-space:nowrap;text-overflow:ellipsis;width:100px'>"+obj.employeeName+"</div>";
			var td3 = document.createElement("td");
			td3.style.cssText="text-align:right";
			var str = "<a href='javascript:void(0)' title='发送短信' onclick=\"sendMSG('"+obj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/send_msg.png'  border='0'/></a>&nbsp;&nbsp;";
			str += "<a href='javascript:void(0)' title='发送邮件' onclick=\"sendEmail('"+obj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/send_mail.png' border='0'/></a>&nbsp;&nbsp;"; 
			td3.innerHTML = str;
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
		}
	}

	function sendMSG(pk){
		window.parent.hiddenlineDiv();
		var url = "<%=contextPath%>/erp/mobile_sms/send_sms.jsp?oaSmsOnlineid="+pk+"&type=3&choose=2";
		openMDITab(url);
	}

	function sendEmail(pk){
		window.parent.hiddenlineDiv();
		var url = "<%=contextPath%>/erp/mobile_sms/mail_send.jsp?mailtype=5&backtype=-1&choose=1&oaSmsOnlineid="+pk;
		openMDITab(url);
	}
</script>
</head>
<body style="overflow:hidden;text-align: center;">
<div style="overflow: auto;height:272px">
<table cellpadding="0" cellspacing="0" width="98%" border="0" id="onlinetable" style="line-height: 22px">
</table>
</div>
</body>
</html>