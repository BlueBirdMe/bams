<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<title>新增公告界面</title>
<%
	String sysMsgPk = request.getParameter("sysMsgPk");
%>
<script type="text/javascript">
window.onload = function(){
	initInput('title');
	if(<%=sysMsgPk%> != null){
	    dwrSysProcessService.getSysMsgByPk(<%=sysMsgPk%>,setPageValue);
	}
}

var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function setPageValue(data){
	 if(data.success && data.resultList.length>0){
          var sysMsg = data.resultList[0];
          DWRUtil.setValue("msgTitle",sysMsg.msgTitle);
          DWRUtil.setValue("msgVsdate",sysMsg.msgVsdate);
          DWRUtil.setValue("msgVedate",sysMsg.msgVedate);
		  fckvalue = sysMsg.msgContext;
		  if(fck!=null){
		  	fck.SetHTML(fckvalue);
		  }
	  }
}

function getSysMsgInfo(){
	var sysMsg = new Object();
   	if(<%=sysMsgPk%> != null){
      sysMsg.primaryKey = <%=sysMsgPk%>;
    }
	sysMsg.msgTitle = DWRUtil.getValue("msgTitle");
    sysMsg.msgVsdate = DWRUtil.getValue("msgVsdate");
	sysMsg.msgVedate = DWRUtil.getValue("msgVedate");
	sysMsg.msgContext = fck.GetXHTML();
	return sysMsg;
}
function reload(){
    Sys.load('<%=contextPath%>/erp/system_manger/sys_msg.jsp');
}

function save(){
   var bl = validvalue('title');
	if(bl){
    	 if(fck.GetXHTML().length < 10){
     		setMustWarn("msgContextMust","公告内容必须大于10个字符！");
         	return;
         }
         if(<%=sysMsgPk%> != null){
	        dwrSysProcessService.updataSysMsg(getSysMsgInfo(),saveCallback);
	     }else{
	        dwrSysProcessService.saveSysMsg(getSysMsgInfo(),saveCallback);
	    }
	}
}
    
function saveCallback(data){
    alertmsg(data,"reback()");
}	

function reback(){
	if(<%=sysMsgPk%> != null){
	     reload();
    }else{
         DWRUtil.setValue("msgTitle","");
         DWRUtil.setValue("msgVsdate","");
	     DWRUtil.setValue("msgVedate","");
         fck.SetHTML("");
    }
}

</script>
</head>
<body>

<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>新增/编辑公告</legend>
	<div>
	
	<table class="inputtable">
	<tr>
	<th><em>*</em>公告标题</th>
	<td colspan="3" style="text-align: left;">
	<input type="text" id="msgTitle" must="公告名称不能为空" formust="msgTitleMust" value="" style="width: 90%" maxlength="50">
	<br/>
	<label id="msgTitleMust"></label>
	</td>
	</tr>
	<tr>
	
	<th><em>*</em>有效期时间</th>
	<td style="text-align: left">
	<input type="text" id="msgVsdate" readonly="readonly" class="Wdate" onClick="WdatePicker({isShowClear:false,minDate:'%y-%M-%d'})" must="有效期开始时间不能为空！" formust="dateMust">
	&nbsp;至&nbsp;<input type="text" id="msgVedate" readonly="readonly" class="Wdate" onclick="WdatePicker({isShowClear:false,minDate:'#F{$dp.$D(\'msgVsdate\',{d:1});}'})" must="有效期结束时间不能为空！" formust="dateMust">
	<label id="dateMust"></label>
	</td>
	</tr>
	<tr>
	<th><em>*</em>公告内容</th>
	<td style="text-align: left" colspan="3">
	<label id="msgContextMust"></label>
	<FCK:editor instanceName="msgContext" height="280" width="90%" ></FCK:editor>
	</td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>
<br/>
<br/>
<center>
<table>
<tr>
<td><btn:btn onclick="save();" value=" 确  定 " /></td>
<td style="width: 20px;"></td>
<td ><btn:btn onclick="reload();" value=" 返  回 "/></td>
</tr>
</table>
</center>
</body>
</html>