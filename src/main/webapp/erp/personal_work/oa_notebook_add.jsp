<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增便签</title>
<%
String type = request.getParameter("type");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script> 
  <script type="text/javascript">
window.onload = function(){
  	useLoadingMassage();
  	document.getElementById("grouptext").focus();
  	initInput("helpTitle","您可以创建属于自己的便签,字数范围为:3-500");
  	if('<%=type%>'=='1'){
  		document.getElementById("btndiv").style.display="none";
  	}
  	initHotkeys();
}

function initHotkeys(){
	$(document).bind('keydown.return',function (evt){save(); return false; });
}
  
function save(){
 	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "leavereasonmust";
	//清空所有信息提示
	warnInit(warnArr);
    var info = DWRUtil.getValue("grouptext");
    if(info.length >=3 && info.length <= 500){
     	dwrPersonalOfficeService.saveNotebook(info,saveCallback);
     	Btn.close();
    }else{
   		setMustWarn("leavereasonmust","请输入3个以上、500个以下的字符。")
   		return;
    }
}
 
function saveCallback(data){
    Btn.open();
	if(data.success){
		confirmmsgAndTitle("添加便签成功！是否想继续添加便签？","reset();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}	

function reset(){
	refreshMDITab(<%=request.getParameter("tab")%>);
	DWRUtil.setValue("grouptext","");
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

var myfrmname =null;
var mymethod = null;
var mydialogId = null;
function savedesktop(dialogId,frm,method){
	 myfrmname = frm;
	 mymethod = method;
	 mydialogId = dialogId;
	 var info = DWRUtil.getValue("grouptext");
     if(info.length >=3 && info.length <= 500){
      	dwrPersonalOfficeService.saveNotebook(info,saveDesktopCallback);	       
     }else{
     	setMustWarn("leavereasonmust","请输入3个以上、500个以下的字符。");
     	return;
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
		<table class="inputtable">
			<tr><th></th><td><label id="leavereasonmust"></label></td></tr>
			<tr height="100%">
				<td width="10%" nowrap="nowrap" align="center">便签内容</td>
				<td >
				<textarea  id="grouptext" style="width: 99%;height: 100px;" must="请输入3个以上的字符。" formust="leavereasonmust" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	<table id="btndiv" align="center">
		<tr>
			<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存新增便签"/></td>
			<td style="width: 20px;"></td>
			<td><DIV id ="backbtn"><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></DIV></td>
		</tr>
	</table>
	<br/>
</body>
</html>