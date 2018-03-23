<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrApproveProcessService.js"></script>
<title>部署流程</title>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
String ext="zip|bar|bpmn|xml";
String[] exts=ext.split("\\|");
 %>
<script type="text/javascript">

//判断上传文件类型
var array =null;
<%if(exts!=null&&exts.length>0){%>
	array =new Array(<%=exts.length%>);
	<%for(int a=0;a<exts.length;a++){%>
		array[<%=a%>] = '<%=exts[a].toLowerCase()%>';
	<%}%>
<%}%>

function improtfile(){
	document.getElementById("filemsg").innerHTML = "";
	var imp = document.getElementById("impfile");
	if(imp.value==""){
		setMustWarn("filemsg","请选择要部署的流程文件！"); 
		document.getElementById("impfile").focus(); 
		return false;
	}
	if(array!=null||array.length>0){
		//类型显示
		var bl =false;
		var strFileFormat = imp.value;
		var fext=strFileFormat.substring(strFileFormat.lastIndexOf('.')+1);
		for(var i=0;i<array.length;i++){
			if(fext.toLowerCase() == array[i]){
				bl=true;
				break;
			}
		}
		if(bl==false){
			setMustWarn("filemsg","文件类型错误,只能上传类型为:<%=ext%>"); 
			document.getElementById("impfile").focus(); 
			return false;
		}
	}
	document.frmname.action = "<%=request.getContextPath()+"/processDeploy.do"%>";
	document.frmname.submit();
	Btn.close();
}

function backList(){
	window.location = "process.jsp";
}

<%if(request.getAttribute(ConstWords.TempStringMsg) !=  null){	%>
	alertmsg("<%=request.getAttribute(ConstWords.TempStringMsg)%>");
<%}%>

</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle">支持文件格式：zip、bar、bpmn、xml</label></div>
	<div class="formTitle">部署新流程</div>
	<form id="frmname" method="post" enctype="multipart/form-data" name="frmname">
	<table class="inputtable" border="0">
		<tr>
			<th><em>* </em>选择文件</th>
			<td>
				<input type="file" id="impfile" name="impfile" style="width: 80%"></input>
			</td>
			<td>
			<btn:btn onclick="improtfile()" value=" 部 署 "></btn:btn>
			</td>
			<td>
			<btn:btn onclick="backList()" value=" 返回列表 "></btn:btn>
			</td>
		</tr>
		<tr>
		<td width="15%"></td>
		<td colspan="3" id="filemsg"></td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>