<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加联系人</title>
<%
String pid = request.getParameter("pid");
String type = request.getParameter("type");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput("helpTitle","添加外部邮箱联系人，发送外部邮箱使用！");
		if(<%=pid%>!=null){
			dwrMailService.getOaNetMailPersonByPk(<%=pid%>,setpagevalue);
		}
		document.getElementById("personname").focus();
	}
	
	function setpagevalue(data){
		if(data.success&&data.resultList.length>0){
			var obj = data.resultList[0];
			DWRUtil.setValue("personname",obj.oaNetmailEmpname);
			DWRUtil.setValue("personmail",obj.oaNetmailEmpmail);
		}
	}
	
	function saveperson(){
		var warnArr = new Array();
		warnArr[0] = "personnameMust";
		warnArr[1] = "personmailMust";
		//清空所有信息提示
		warnInit(warnArr);
		var bl = validvalue('helpTitle');
		if(bl){
			dwrMailService.saveNetmailPerson(getPerson(),savecallback);
		}
	}
	
	function savecallback(data){
		if(<%=type%>!=null){
			if(data.success&&data.resultList.length>0){
				var frm = window.parent.parent.getMDIFrame(<%=request.getParameter("tab")%>);
				if(isBlank(frm.location)) frm = frm.contentWindow;
				var obj = data.resultList[0];
				if(frm.netmail != undefined){
					var mad = frm.netmail.document.getElementById("oasendMailAdder");
				}else{
					var mad = frm.document.getElementById("oasendMailAdder");
				}
				var tmps = removerepeatIds(mad.value+obj.oaNetmailEmpmail+";");
				mad.value = tmps;
				confirmmsgAndTitle("添加联系人成功！是否想继续添加联系人？","reset();","继续添加","closeMDITab();","关闭页面");
			}else{
				alertmsg(data);
			}
		}else{
			if(<%=pid%>!=null){
				alertmsg(data,"backToList()");
			}else{
				if(data.success){
					confirmmsgAndTitle("添加联系人成功！是否想继续添加联系人？","reset();","继续添加","closePage();","关闭页面");
				}else{
					alertmsg(data);
				}
			}
		}
	}
	
	function reset(){
		DWRUtil.setValue("personname","");
		DWRUtil.setValue("personmail","");
	}
	
	function backToList(){
		window.parent.MoveDiv.close();
		window.parent.queryData();
	}
	
	function getPerson(){
		var per = new Object();
		if(<%=pid%>!=null){
			per.primaryKey = <%=pid%>;
		}
		per.oaNetmailEmpname = DWRUtil.getValue("personname");
		per.oaNetmailEmpmail = DWRUtil.getValue("personmail");
		return per;
	}
	
	function closePage(){
		closeMDITab(<%=request.getParameter("tab")%>,"netmail");
	}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">添加联系人</div>
	<div>
	<table class="inputtable">
	<tr>
	<th><em>* </em>联系人名称</th>
	<td><input type="text" maxlength="20" size="20" id="personname" must="联系人名称不能为空！" formust="personnameMust"/></td>
	<td id="personnameMust"></td>
	</tr>
	<tr>
	<th><em>* </em>邮箱地址</th>
	<td><input type="text" maxlength="50" size="50" id="personmail" must="邮箱地址不能为空！" formust="personmailMust"/></td>
	<td id="personmailMust"></td>
	</tr>
	</table>
	<br/>
	</div>
</div>
<br/>
<table align="center">
<tr>
<td><btn:btn onclick="saveperson();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
<td style="width: 10px;"></td>
	<td>
	<%if (pid == null){ %>
		<%if (type == null){ %>
			<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
		<%}else{ %>
			<btn:btn onclick="closeMDITab()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
		<%} %>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
</tr>
</table>
</body>
</html>