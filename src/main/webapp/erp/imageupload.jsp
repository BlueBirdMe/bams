<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.security.Base64"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=7" /> 
<title>图片上传</title>
<style type="text/css">
body{
	margin: 0px;
	font-size: 12px;
}
#bodydiv{
  overflow:hidden;
  text-align:center;
  vertical-align:middle;
  width:100%;
}

.addfilediv{
	margin: 2px;
	width: 33px;
	height: 33px;
}

.addfilediv:hover{
	border: 1px solid #316ac5;
	margin: 1px;
	width: 33px;
	height: 33px;
	cursor: pointer;
	background-color: #dff1ff;
}
a.files {
    overflow:hidden;
    display:-moz-inline-box;
    display:inline-block;
    background:url(<%=request.getContextPath()%>/images/imgfilesel.png);
}

a.files {
    width:32px;
    height:32px;
    #vertical-align:middle;
}

a.files input {
    margin-left:-365px;
    margin-top:-7px;
    font-size:32px;
    filter:alpha(opacity=0);
    opacity:0;
}
</style>
<%
String defaultImg=null,fileSize=null,type=null,ext=null,he=null,saveType=null,saveDir="",filecount=null,acceptTextId =null,edit=null;
Object obj =request.getAttribute(ConstWords.TempStringRequest);
Object other =request.getAttribute(ConstWords.TempStringRequest2);
String[] exts=null;
if(other!=null&&obj!=null){
	Map<String,String> otherMap=(Map)other;
	he = otherMap.get("he");
	acceptTextId =otherMap.get("aname");
	edit ="true";
}else{
	defaultImg =request.getParameter("defaultImg");
	edit = request.getParameter("edit");
	if(edit==null){
		edit = "false";
	}
	he =request.getParameter("he");
	
	acceptTextId =request.getParameter("AcceptText");
}
filecount="1";
fileSize=SystemConfig.getParam("erp.upload.imageSize");
type="image";
saveType="image";
ext="jpg|gif|jpeg|png|bmp";
if(ext!=null&&ext.length()>0){
	exts=ext.split("\\|");
}
if(acceptTextId == null){
	out.print("接收文本框对象没有指定!");
	return;
}
if(he==null){
	out.print("请指定组件高度!");
	return;
}
String src="";
if(edit==null||edit.equalsIgnoreCase("false")){
	if(defaultImg==null){
		src = request.getContextPath()+"/showimg.do?imgId=-1";
	}else{
		src = request.getContextPath()+"/showimg.do?imgPath="+defaultImg;
	}
}
%>
<script type="text/javascript">
	function getById(id){
		return document.getElementById(id);
	}
	var array=null;
	<%if(exts!=null&&exts.length>0){%>
   		array =new Array(<%=exts.length%>);
   		<%for(int a=0;a<exts.length;a++){%>
   			array[<%=a%>] = '<%=exts[a].toLowerCase()%>';
   		<%}%>
    <%}%>
	window.onload=function(){
		getById("bodydiv").style.height = "<%=Integer.parseInt(he)-9%>";
		<%if(acceptTextId!=null){%>
		var atext =window.parent.document.getElementById('<%=acceptTextId%>');
		if(atext==null){
			document.write("接收文本框对象未创建！");
			return;
		}
		<%}%>
		<%if(edit!=null&&edit.equalsIgnoreCase("true")){%>
			var txt =window.parent.document.getElementById('<%=acceptTextId%>');
			if(txt!=null){
				<%if(other==null&&obj==null){%>
				var t = txt.value;

				var ts;
				if(t!=""){
					ts=t.split("|");
					getById("oldimg").value=ts[1];
					getById("src").value="<%=request.getContextPath()%>/showimg.do?imgPath="+ts[1];
				}else{
					getById("src").value="<%=request.getContextPath()%>/showimg.do?imgPath=-1";
				}
				<%}else{%>
				txt.value =getById("temptxt").value;
				<%}%>
			}
		<%}%>
		getById("bodyimg").src = getById("src").value;
		getById("butt").style.top =  "<%=Integer.parseInt(he)-38%>";
		
	}
	function showButt(){
		if(getById('butt').style.visibility =="visible"){
			getById('butt').style.visibility="hidden";
		}else{
			getById('butt').style.visibility ="visible";
		}
	}
	function fileSend(obj){
		if(validext(obj)){
			getById('butt').style.visibility="hidden";
			var size=getById("fileSize").value;
			var savetype =getById("saveType").value;
			var he = getById("he").value;
			var count = getById("count").value;
			var aname =getById("acceptName").value;
			var savedir =getById("saveDir").value;
			var oldimg = getById("oldimg").value;
			var par ="&fileSize="+size+"&saveType="+savetype+"&savedir="+savedir+"&he="+he+"&count="+count+"&aname="+aname+"&oldimg="+oldimg;
			document.frmname.action = "<%=request.getContextPath()+"/upload.do?forward=img"%>"+par;
			document.frmname.submit();
		}
	}
	function validext(fromfile){
		if(array==null||array.length==0){
			return true;
		}
		//类型显示
   		var bl =false;
   		var strFileFormat=fromfile.value;
   		var fext=strFileFormat.substring(strFileFormat.lastIndexOf('.')+1);
   		for(var i=0;i<array.length;i++){
   			if(fext.toLowerCase() == array[i]){
   				bl=true;
   				break;
   			}
   		}
   		if(bl==false){
   			alert('文件类型错误,\n只能上传:<%=ext%>');
   			return false;
   		}
   		return bl;
	}
</script>
</head>
<body style="background-color:transparent;" title="单击选择图片上传" >
<div style="position:relative;">
<input type="hidden" id="acceptName" name="acceptName" value="<%=acceptTextId %>"/>
<%if(edit!=null&&edit.equalsIgnoreCase("true")){ %>
	<%if(other==null&&obj==null){%>
	<input type="hidden" name="oldimg" id="oldimg">
	<input type="hidden" name="src" id="src">
	<%}else{ 
	String tempstr="";
	String key="";
	Map<String,String> lastMap =(Map)obj;
	Iterator<String> it =lastMap.keySet().iterator();
	if(it.hasNext()){
		key=it.next();
		tempstr = lastMap.get(key);
	}
	%>
	<input type="hidden" name="oldimg" id="oldimg" value="<%=tempstr %>">
	<input type="hidden" name="src" id="src" value="<%=request.getContextPath()+"/showimg.do?imgPath="+tempstr %>">
	<input type="hidden" name="temptxt" id="temptxt" value="<%=key+"|"+tempstr %>">
	<% }
}else{%>
<input type="hidden" name="oldimg" id="oldimg">
<input type="hidden" name="src" id="src" value="<%=src %>">
<% }%>
<div onclick="showButt();" id="bodydiv">
<img id="bodyimg" border="0" style="max-width: 99%;height: auto;"/>
</div>
<div style="position: absolute;top:0px;visibility:hidden;" id="butt">
<form id="frmname" method="post" enctype="multipart/form-data" name="frmname">
<input type="hidden" id="fileSize" name="fileSize" value="<%=fileSize %>"/>
<input type="hidden" id="saveType" name="saveType" value="<%=saveType %>"/>
<input type="hidden" id="he" name="he" value="<%=he %>"/>
<input type="hidden" id="saveDir" name="saveDir" value="<%=saveDir %>"/>
<input type="hidden" id="count" name="count" value="1">
<table cellpadding="0" cellspacing="0" border="0">
<tr>
<td align="center" nowrap="nowrap" width="33px;" height="33px;">
<div class="addfilediv">
<a href="javascript:void(0);" class="files" title="选择图片上传">
<input id="upfile" name="upfile" type="file" onchange="fileSend(this);">
</a>
</div>
</td>
</tr>
</table>
</form>
</div>
</div>
</body>
</html>