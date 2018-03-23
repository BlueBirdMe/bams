<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String typePk =request.getParameter("typePk");
String toolid = request.getParameter("toolid");
String isedit = "false";
if(toolid != null){
	isedit = "true";
}
 %>
<title>工具添加</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("toolText").focus();
	useLoadingMassage();
	initInput("helpTitle","您可以创建属于自己的工具，路径格式为:'http://www.pinhuba.com'");
	saveOrEdit();
}
	
function saveOrEdit(){
	if(<%=toolid%> != null){
		dwrPersonalOfficeService.getToolByTypeAndPk(<%=typePk%>,<%=toolid%>,setOaToolinfo);
 	}
}

function setOaToolinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var oaTool = data.resultList[0];
			DWRUtil.setValue("toolText",oaTool.oaToolText);
			DWRUtil.setValue("toolPath",oaTool.oaToolPath);
			if(oaTool.oaToolImageId>0){
				dwrCommonService.getImageInfoListToString(oaTool.oaToolImageId,setToolImage);
			}
 		}
 	}
}
	
function setToolImage(data){
	Sys.setFilevalue("toolImage",data);
}

function save(){
	var bl = validvalue('helpTitle');
	if (bl){
		var image = DWRUtil.getValue("toolImage");
		dwrPersonalOfficeService.addTool(getToolInfo(),image,saveCallback);
		Btn.close();
	}
}

function getToolInfo(){
	var tool = new Object();
	if(<%=toolid%> != null){
	     tool.primaryKey = <%=toolid%>;
	}
	tool.oaToolType = <%=typePk%>;
	tool.oaToolText = document.getElementById("toolText").value;
	tool.oaToolPath = document.getElementById("toolPath").value;
	//tool.oaToolImage = document.getElementById("toolImage").value;
	return tool;
}
	
function saveCallback(data){
    Btn.open();
   	if(<%=toolid%>!=null){
    	alertmsg(data, "reload()");
    }
    else{
    	alertmsg(data, "reload()");
    }
}
	
function canse(){
	window.parent.location = "commentools.jsp";
	window.parent.MoveDiv.close();
}

function reset() {
	DWRUtil.setValue("toolText", "");
	DWRUtil.setValue("toolPath", "");
	Sys.setFilevalue("toolImage","");
}
	
function reload() {
	window.parent.MoveDiv.close();
  	window.parent.location = "<%=contextPath %>/erp/personal_work/commentools.jsp";
}

function reback(){
	window.parent.document.location.reload();
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">修改/添加工具</div>
	    <table class="inputtable">
			<tr>
				<th><em>*</em>&nbsp;工具名称</th>
				<td style="text-align: left;"><input type="text" id="toolText"  size="60%" must="工具名称不能为空！" formust="toolTextmust">&nbsp;&nbsp;<label id="toolTextmust"></label></td>
			</tr>
			<tr>
				<th><em>*</em>&nbsp;连接路径</th>
				<td style="text-align: left;"><input type="text" id="toolPath"  size="60%" must="连接路径不能为空！" formust="toolPathmust">&nbsp;&nbsp;<label id="toolPathmust"></label></td>
			</tr>
			
			<tr>
				<th>工具图片</th>
				<td>
				    <file:imgupload width="128" acceptTextId="toolImage" height="128" edit="<%=isedit%>" ></file:imgupload>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<table align="center">
		<tr>
			<td>
				<btn:btn onclick="save()" value="确 定 " imgsrc="../../images/fileokico.png" title="保存信息"></btn:btn>
			</td>
			<td style="width: 10px;"></td>
			<td>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
			</td>
		</tr>
	</table>
</body>
</html>