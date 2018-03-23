<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@page import="java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String aid =request.getParameter("aid");
String folderName = request.getParameter("folderName");
String folderPath = URLDecoder.decode(request.getParameter("folderPath"),"UTF-8");
String folderPathEncode = URLEncoder.encode(folderPath.substring(5), "UTF-8");
String hrmEmployId = UtilTool.getEmployeeId(request);
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrNetdiskService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput("title","文件夹共享，您可以在此处共享文件夹。不选择任何人员和部门将取消共享。");
		var netdiskShare = new Object();
		netdiskShare.folderName = "<%=folderName %>";
		netdiskShare.folderPath = "<%=folderPathEncode %>";
		netdiskShare.hrmEmployeeId = "<%=hrmEmployId %>";
		dwrNetdiskService.getShareByHrmEmpIDandPath(netdiskShare, setpagevalue);
	}

	function setpagevalue(data){
		if(data.success && data.resultList.length > 0){
		        var tmp = data.resultList[0];
				//设置fck
			
				DWRUtil.setValue("netdisktext",tmp.shareDesc);
				//设置人员
				Sys.showEmpNames(tmp.netdiskEmps,"netdiskempsname","netdiskempsids");
				//设置部门
				Sys.showDeptNames(tmp.netdiskDeps,"netdiskdeptname","netdiskdeptids");
		}
	}
	function savenetdiskShare(){
		var bl = validvalue('title');
		if(bl){
			var netdiskShare = getnetdiskShare();
			/*if(netdiskShare.netdiskEmps=="" && netdiskShare.netdiskDeps==""){
				DWRUtil.setValue("title","浏览范围至少选择一个");
				return false;
			}*/
			dwrNetdiskService.saveNetdiskShare(netdiskShare,savecallback);
		}
	}
	
	function savecallback(data){
		if(<%=aid%>!=null){
			alertmsg(data,"currentload()");
		}else{
			alertmsg(data,"currentload()");
		}
	}
	
	function repload(){
		DWRUtil.setValue("netdiskempsname","");
		DWRUtil.setValue("netdiskempsids","");
		DWRUtil.setValue("netdiskdeptname","");
		DWRUtil.setValue("netdiskdeptids","");
		//设置fck
		DWRUtil.setValue("netdisktext","");
		
	}
	function getnetdiskShare(){
		var netdiskShare = new Object();
		if(<%=aid%>!=null){
			netdiskShare.primaryKey = <%=aid%>;
		}
		netdiskShare.folderName = "<%=folderName %>";
		netdiskShare.folderPath = "<%=folderPathEncode %>";
		netdiskShare.netdiskEmps = DWRUtil.getValue("netdiskempsids");
		netdiskShare.netdiskDeps = DWRUtil.getValue("netdiskdeptids");
		netdiskShare.shareDesc = DWRUtil.getValue("netdisktext");
		return netdiskShare;
	}
	function currentload(){
		window.parent.Ext.getCmp("netdiskframepanel").close();
	}
	function getdept(){
		var box = SEL.getDeptIds("check","netdiskdeptname","netdiskdeptids","netdiskiframe");
		box.show();
	}
	function getemployee(){
		var box = SEL.getEmployeeIds("check","netdiskempsname","netdiskempsids","netdiskiframe");
		box.show();
		
	}
</script>
<title>共享文件夹</title>
</head>
<body class="inputcls">
	<div class="requdiv"><label id="title"></label></div>
		<div class="formTitle">
				共享文件夹
			</div>
	<div>
	<table class="inputtable">
	<tr>
	<th width="12%">文件夹名称</th>
	<td>
	<%=URLDecoder.decode(folderName,"UTF-8") %>
	</td>
	</tr>
	<tr>
	<th width="12%">文件夹路径</th>
	<td>
	<%=URLDecoder.decode(folderPath,"UTF-8") %>
	</td>
	</tr>
	<tr>
	<th><font color="black">共享范围<br/>(人员)</font></th>
	<td colspan="3">
	<textarea id="netdiskempsname" readonly="readonly" onclick="getemployee();" title="点击选择人员" linkclear="netdiskempsids"></textarea>
	<input type="hidden" id="netdiskempsids">
	</td>
	</tr>
	<tr>
	<th><font color="black">共享范围<br/>(部门)</font></th>
	<td colspan="3">
	<textarea id="netdiskdeptname" readonly="readonly" onclick="getdept();" title="点击选择部门" linkclear="netdiskdeptids" ></textarea>
	<input type="hidden" id="netdiskdeptids">
	</td>
	</tr>
	<tr>
	<th>共享描述</th>
	<td colspan="3">
	<textarea width="90%" height="100" id="netdisktext"></textarea>
	
	</td>
	</tr>
	</table>
	</div>

<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<btn:btn onclick="savenetdiskShare();"></btn:btn>
</td>
<td style="width: 15px;"></td>
<td>
<btn:cancel onclick="currentload()" ></btn:cancel>
</td>
</tr>
</table>
</body>
</html>

