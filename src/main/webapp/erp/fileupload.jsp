<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.pinhuba.common.util.ConstWords"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.pinhuba.common.util.file.properties.SystemConfig"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type='text/javascript' src='<%=request.getContextPath()%>/js/jquery-1.7.2.min.js'></script>
<title>附件上传</title>
<style type="text/css">
<!--
body{
	margin: 0px;
	font-size: 12px;
}
a{
color:#3F3D3D;
text-decoration:none;
}
a:link{
color:#3F3D3D;
}
a:hover{
color:#3F3D3D;
text-decoration:underline;
}
a:active{
color:#3F3D3D;
}
a.files {
    overflow:hidden;
    display:-moz-inline-box;
    display:inline-block;
    background:url(<%=request.getContextPath()%>/images/addfile_.png) no-repeat;
    cursor: default;
}

a.files {
    width:16px;
    height:16px;
    vertical-align:middle;
}

a.files input {
    margin-left:-240px;
    margin-top:-2px;
    font-size:18px;
    filter:alpha(opacity=0);
    opacity:0;
}
.addfilediv{
	border: 0px solid #555;
	width: 20px;
	height: 20px;
	background-color: #eee;
	vertical-align: middle;
	text-align: center;
	padding: 1px;
}

.addfilediv:hover{
	border: 1px solid #316ac5;
	width: 20px;
	height: 20px;
	cursor: default;
	background-color: #dff1ff;
	padding: 1px;
	vertical-align: middle;
	text-align: center;
}

.filediv{
	border: 0px solid #555;
	padding: 1px;
	width: 20px;
	height: 20px;
	background-color: #eee;
	vertical-align: middle;
	text-align: center;
}

tr,td{
	font-size: 12px;
}
#file {
  font-size: 12px;
  font-family: 宋体;
}
#fileList{
	width:100%;
	overflow:auto;
	scrollbar-face-color:#DEDEDE;
	scrollbar-base-color:#F5F5F5;
	scrollbar-arrow-color:black;
	scrollbar-track-color:#F5F5F5;
	scrollbar-shadow-color:#EBF5FF;
	scrollbar-highlight-color:#F5F5F5;
	scrollbar-3dlight-color:#C3C3C3;
	scrollbar-darkshadow-Color:#9D9D9D;
	font-family: Verdana, Arial,Vrinda,Tahoma;
	font-size:12px;
	height:100%;
}
.btn {
	background:url(<%=request.getContextPath()%>/images/upfile.png) no-repeat center;
	width:16px;height:20px;line-height:25px;
	font-size:12px;color:#333333;border:0;
}
.btn_end{
	background:url(<%=request.getContextPath()%>/images/upfile_.png) no-repeat center;
	width:16px;height:20px;line-height:25px;
	font-size:12px;color:#000;border:0;  
}
.fileclear {
	background:url(<%=request.getContextPath()%>/images/fileclear.png) no-repeat center;
	width:16px;height:20px;line-height:25px;
	font-size:12px;color:#333333;border:0;
}
.filerep{
	background:url(<%=request.getContextPath()%>/images/filerep.png) no-repeat center;
	width:16px;height:20px;line-height:25px;
	font-size:12px;color:#333333;border:0;
}
.fileclear_end{
	background:url(<%=request.getContextPath()%>/images/fileclear_.png) no-repeat center;
	width:16px;height:20px;line-height:25px;
	font-size:12px;color:#000;border:0;  
}
#newPreview
{
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
}
.opentool{
	width:100%;
	height:100%;
	background:url(<%=request.getContextPath()%>/images/grid_images/uup.png) no-repeat 0px  21px;
	border-right: 1px inset #cccccc;
	cursor: default;
}
.closetool{
	background:url(<%=request.getContextPath()%>/images/grid_images/ddn.png) no-repeat 1px center;
}
-->
</style>
<%
//peng.ning 2009-11-11
//附件上传处理，支持文件大小、个数、格式、存储位置等设置
//支持图片类型上传前后预览
//支持编辑时显示已上传文件

Object obj =request.getAttribute(ConstWords.TempStringRequest);
Object other =request.getAttribute(ConstWords.TempStringRequest2);
String filecount=null,fileSize=null,type=null,ext=null,he=null,saveType=null,saveDir=null,acceptTextId =null,edit = "false";
String fileCountMsg = "无限制";
String[] exts=null;
if(obj==null){
	filecount =request.getParameter("fileCount")==null?"0":request.getParameter("fileCount");
	if(!filecount.equals("0")){
		fileCountMsg =filecount;
	}
	fileSize =request.getParameter("fileSize")==null?SystemConfig.getParam("erp.upload.fileSize"):request.getParameter("fileSize");
	type=request.getParameter("type");
	ext= request.getParameter("ext")==null?"":request.getParameter("ext");
	he =request.getParameter("he");
	saveType =request.getParameter("saveType")==null?"image":request.getParameter("saveType");
	saveDir =request.getParameter("saveDir")==null?"":request.getParameter("saveDir");
	if(type!=null&&type.length()>0){
		if(type.equalsIgnoreCase("image")){
			ext="jpg|gif|jpeg|png|bmp";
		}else if(type.equalsIgnoreCase("txt")){
			ext="txt|rtf";
		}else if(type.equalsIgnoreCase("office")){
			 //ext="doc|docx|xls|xlsx|ppt|pptx|pdf|xml|rtf|wps|7z|zip|rar";
			ext="docx|xlsx|pptx|7z|avi|bmp|doc|gif|jpeg|jpg|mp3|mp4|mpeg|mpg|odt|pdf|png|ppt|pxd|rar|rm|rmi|rmvb|rtf|swf|sxw|tar|tgz|tif|tiff|txt|vsd|wav|wma|wmv|xls|xml|zip";
		}else if(type.equalsIgnoreCase("other")){
			ext="7z|aiff|asf|avi|bmp|csv|doc|fla|flv|gif|gz|gzip|jpeg|jpg|mid|mov|mp3|mp4|mpc|mpeg|mpg|ods|odt|pdf|png|ppt|pxd|qt|ram|rar|rm|rmi|rmvb|rtf|sdc|sitd|swf|sxc|sxw|tar|tgz|tif|tiff|txt|vsd|wav|wma|wmv|xls|xml|zip";
		}
	}
	acceptTextId = request.getParameter("AcceptText");
	if(ext!=null&&ext.length()>0){
		exts=ext.split("\\|");
	}
	edit = request.getParameter("edit");
	if(edit==null){
		edit ="false";
	}
}else{
	Map<String,String> otherMap=(Map)other;
	he = otherMap.get("he");
	acceptTextId =otherMap.get("aname");
}
if(acceptTextId == null){
	out.print("接收文本框对象没有指定!");
	return;
}
if(he==null){
	out.print("请指定组件高度!");
	return;
}
boolean  isedit = edit.equalsIgnoreCase("true");

%>
<script type="text/javascript">
function getById(id){
	return document.getElementById(id);
}
//删除左右两端的空格   
function trim(str){   
 return str.replace(/(^\s*)|(\s*$)/g, "");   
}   
//删除左边的空格   
function ltrim(str){   
 return str.replace(/(^\s*)/g,"");   
}   
//删除右边的空格   
function rtrim(str){   
 return str.replace(/(\s*$)/g,"");   
}
window.onload=function(){
	getById("fileList").style.height=parseInt('<%=he%>')-30;
	<%if(acceptTextId!=null){%>
		var atext =window.parent.document.getElementById('<%=acceptTextId%>');
		if(atext==null){
			document.write("接收文本框对象未创建！");
			return;
		}
	<%}%>
}
var array =null;
<%if(exts!=null&&exts.length>0){%>
	array =new Array(<%=exts.length%>);
	<%for(int a=0;a<exts.length;a++){%>
		array[<%=a%>] = '<%=exts[a].toLowerCase()%>';
	<%}%>
<%}%>
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
<%if(obj==null&&!isedit){%>
	var File = {
	    num : 1, count : 0, name : 'upfile', status : 'file', form : 'frmname', urls : {},
	    add : function (fromfile) {
	    	var max=0;
	    	//数量限制
	    	<%if(!filecount.equals("0")){%>
	    		max=parseInt('<%=filecount%>');
	    		if(this.count>=max){
	    			alert('只能添加'+max+"个附件!");
	            	return false;
	    		}
	    	<%}%>
	    	//类型显示
	    	if(validext(fromfile)==false){
	    		return false;
	    	}
	    	
	    	//添加附件
	        if (this.urls[fromfile.value]) {
	            alert('此文件已存在');
	            return false;
	        }
	        
	        var a = fromfile.parentNode, status = document.getElementById(this.status);
	        this.urls[fromfile.value] = 1;
	        getById(this.form).appendChild(fromfile);
	        if (/Firefox/.test(window.navigator.userAgent)) {
	        //中转一下，否则FF里有很NB的错误会出现。。。
	            var b = a, a = a.cloneNode(true);
	            b.parentNode.replaceChild(a, b);
	            b = null;
	        }
	        
	        fromfile.style.display = 'none';
	        a.innerHTML = '<input id="' + this.name + (this.num + 1) + '" name="' + this.name + (this.num + 1) + '" onchange="File.add(this)" type="file"/>';
	        
	        var pageno=status.rows.length;
	        var otr = status.insertRow(-1);
	        otr.style.cssText="line-height:25px;text-align:center;";
	        otr.id="tr"+pageno;
	        var td2=document.createElement("td");
	        var s ="tr"+pageno;
	        var str = "<a  href='javascript:void(0)' onclick='File.del(this, " + this.num + ",\""+s+"\")' style='color:#336699'>取消</a>";
	        td2.style.cssText="padding-left:5px;text-align:left;";
	        var event ="showImgPreview(this);";
	        var outent ="hiddenImgPreview();";
	        td2.innerHTML ="<img src='<%=request.getContextPath()%>/images/fileico.png' border='0' width='16' height='16'  style='vertical-align: middle;'/>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'  onmouseout="+outent+" onclick="+event+" value='"+fromfile.value+"'>"+(/[^\\]+$/.exec(fromfile.value)||'')+"</a>&nbsp;&nbsp;&nbsp;&nbsp;"+str;
	        otr.appendChild(td2);
	        
	        this.count ++, this.num ++, a = fromfile= null;
	        document.getElementById("count").value=this.count;
	        var obj=getById("subbtn");
	        var sdiv =getById("subdiv");
	        var cla=getById("clabtn");
	        var cdiv =getById("cladiv");
	        if(this.count>0){
	        	obj.className='btn_end';
	        	sdiv.className ="addfilediv";
	        	cla.className='fileclear';
	        	cdiv.className ="addfilediv";
	        }else{
	        	obj.className='btn';
				sdiv.className ="filediv";
				cla.className='fileclear_end';
	        	cdiv.className ="filediv";
	        }
	    },
	    del : function (span,num,val) {

	    	//删除附件
	        var fromfile = getById(this.name + num);
	        if(fromfile!=null){
	        	delete this.urls[fromfile.value];
	        	document.getElementById(this.form).removeChild(fromfile);
	        }
	        
	        var tab=document.getElementById(this.status);
	        var row =document.getElementById(val);
			tab.deleteRow(row.rowIndex);
	        
	        this.count --, num = null;
	        document.getElementById("count").value=this.count;
	        var obj=document.getElementById("subbtn");
	        var sdiv =document.getElementById("subdiv");
	         var cla=document.getElementById("clabtn");
	        var cdiv =document.getElementById("cladiv");
	        if(this.count>0){
	        	obj.className='btn_end';
	        	sdiv.className ="addfilediv";
	        	cla.className='fileclear';
	        	cdiv.className ="addfilediv";
	        }else{
	        	obj.className='btn';
				sdiv.className ="filediv";
				cla.className='fileclear_end';
	        	cdiv.className ="filediv";
	        }
	    }
	};
	function showfile(){
		var f =getById("upfile1");
		File.add(f);
	}
	function clearAll(){
		for(var i=1;i<File.num;i++){
			var fromfile = getById(File.name + i);
			if(fromfile!=null){
		    	delete File.urls[fromfile.value];
		    	document.getElementById(File.form).removeChild(fromfile);
		    	File.count --;
		    }
		}
		document.getElementById("count").value=File.count;
		var rlen = getById("file").rows.length;	
		for(var i=rlen-1;i>=0;i--){
			getById("file").deleteRow(i);
		}
		var obj=getById("subbtn");
	    var sdiv =getById("subdiv");
	    var cla=getById("clabtn");
	    var cdiv =getById("cladiv");
	    obj.className='btn';
		sdiv.className ="filediv";
		cla.className='fileclear_end';
	    cdiv.className ="filediv";
	}
	function showImgPreview(a){
		var path=a.getAttribute("value");
		var suffix = path.substring(path.lastIndexOf(".")+1);
		var newPreview = getById("newPreview");
		if (suffix=='gif' || suffix=="jpg" || suffix=="jpeg" || suffix=="bmp" || suffix=="png"){
			newPreview.style.display="block";
	    	try{
	    		newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = path;
	    	}catch(e){}
	    	newPreview.style.width = "90px";
	    	newPreview.style.height = "80px";
	    }else{
	    	newPreview.style.display="none";
	    }
	}
	function hiddenImgPreview(){
		var newPreview = getById("newPreview");
		newPreview.style.display="none";
	}
	function uploadSave(){
		if(getById("file").rows.length==0){
			return;
		}
		getById("msgBox").innerHTML ="上传中...";
		var size=getById("fileSize").value;
		var savetype =getById("saveType").value;
		var savedir =getById("saveDir").value;
		var he = getById("he").value;
		var count = getById("count").value;
		var aname =getById("acceptName").value;
		var par ="&fileSize="+size+"&saveType="+savetype+"&savedir="+savedir+"&he="+he+"&count="+count+"&aname="+aname;
		document.frmname.action = "<%=request.getContextPath()+"/upload.do?forward=file"%>"+par;
		document.frmname.submit();
	}
<%}else{%>
	function repSend(){
		var cf=confirm("系统将清除已上传文件，确定重新上传吗？");
		if(cf){
			//清除已上传文件
			var delObjs = document.getElementsByName("filePathName");
			var delPaths ="";
			for(var a =0;a<delObjs.length;a++){
				var tmp =delObjs[a].value.split("|");
				delPaths+=tmp[1]+",";
			}
			
			$.get("<%=request.getContextPath()%>/erp/fileProcess.jsp?filepath="+delPaths,
		    function(bl){
				if(bl == "true") deleteAllRow();//删除行
		        else alert("删除文件异常！");
		  	});  
		}
	}
	function showOkImg(img){
		var path=img.getAttribute("value");
		var tmp =img.getAttribute("temp");
		try{
			var suffix = tmp.substring(tmp.lastIndexOf(".")+1);
			if (suffix=='gif' || suffix=="jpg" || suffix=="jpeg" || suffix=="bmp" || suffix=="png"){
				getById("filePre").innerHTML ="<img src='<%=request.getContextPath() %>/showimg.do?imgPath="+path+"' width='100' style='border: 1px solid #cccccc;'/>";
			}
		}catch(e){}
	}
	function hiddenOkImg(){
		getById("filePre").innerHTML=""; 
	}
	var trId ="";
	function deleteOkFile(a){
		var cf=confirm("确定要删除已上传文件吗？");
		if(cf){
			var dpath=getById("filePathName"+a).value;
			var tmp =dpath.split("|");
			trId=a;
			
			$.get("<%=request.getContextPath()%>/erp/fileProcess.jsp?filepath="+tmp[1],
		    function(bl){
				if(bl == "true") deleteRow();//删除行
		        else alert("删除文件异常！");
		  	});
		}
	}
	function setAcceptText(){
		<%if(acceptTextId!=null){%>
			var obj=window.parent.document.getElementById('<%=acceptTextId%>');
			var delObjs = document.getElementsByName("filePathName");
			var delPaths ="";
			for(var a =0;a<delObjs.length;a++){
				delPaths+=delObjs[a].value+",";
			}
			if(obj!=null){
				obj.value = delPaths.substring(0,delPaths.length-1);
			}else{
				document.write("接收返回结果对象错误!");
			}
		<%}%>
	}

	function deleteAllRow(){
		var tab=getById("file");
		var rlen=tab.rows.length;
		for(var a=rlen-1;a>=0;a--){
			tab.deleteRow(a);
		}
		deleteBack();
	}
	function deleteRow(){
		 var tab=getById("file");
	     var row =getById("tr"+trId);
		 tab.deleteRow(row.rowIndex);
		 deleteBack();
	}
	function deleteBack(){
		setAcceptText();
		var tab=getById("file");
		var len =tab.rows.length;
		if(len==0){
			<%if(isedit){%>
				var h=window.location.href;
				var ul =h.substring(0,h.lastIndexOf("?"));
				var par =h.substring(h.lastIndexOf("?")+1);
				var pars =par.split("&");
				for(var a =0; a<pars.length;a++){
					var tmp = pars[a].split("=");
					if(tmp[0]!="edit"){
						if(a==0){
							ul+="?"+pars[a];
						}else{
							ul+="&"+pars[a];
						}
					}
				}
				window.location.href = ul;
			<%}else{%>
		 		window.history.back();
		 	<%}%>
		}
	}
	//创建编辑文件列表
	function createFileList(){
		var fl=window.parent.document.getElementById('<%=acceptTextId%>').value;
		if(fl!=""){
		    var f =getById("file");
			var rlen = f.rows.length;	
			for(var i=rlen-1;i>=0;i--){
				f.deleteRow(i);
			}

			var stra = fl.split(",");
			for(var i=0; i<stra.length;i++){
				var tmp = stra[i].split("|");
				var key = tmp[0];
				var t = tmp[1];
				var otr = f.insertRow(-1);
		        otr.id="tr"+i;
		        var td=document.createElement("td");
		        td.style.cssText = "padding-left:5px;text-align:left;";
		        var str = "<a  href='javascript:void(0)' onclick='deleteOkFile("+i+");' style='color:#336699'>删除</a>";
		        var hid = "<input type='hidden' id='filePathName"+i+"' name='filePathName' value='"+key+"|"+t+"'/>";
		        var event ="showOkImg(this);";
		        var outent ="hiddenOkImg();";
		        td.innerHTML ="<img src='<%=request.getContextPath()%>/images/fileokico.png' border='0' width='16' height='16'  style='vertical-align: middle;'/>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'  onmouseout="+outent+" onclick="+event+" value='"+t+"' temp='"+key+"' >"+key+"</a>&nbsp;&nbsp;&nbsp;&nbsp;"+str+hid;
		        otr.appendChild(td);
	        }
		}else{
			var h=window.location.href;
			var ul =h.substring(0,h.lastIndexOf("?"));
			var par =h.substring(h.lastIndexOf("?")+1);
			var pars =par.split("&");
			for(var a =0; a<pars.length;a++){
				var tmp = pars[a].split("=");
				if(tmp[0]!="edit"){
					if(a==0){
						ul+="?"+pars[a];
					}else{
						ul+="&"+pars[a];
					}
				}
			}
			window.location.href = ul;
		}
	}
<%}%>

function closetool(){
	document.getElementById("tools").style.display ="none";
	document.getElementById("templabel").style.display ="";
	getById("fileList").style.height=parseInt('<%=he%>')-10;
}
function opentool(){
	document.getElementById("tools").style.display ="";
	document.getElementById("templabel").style.display ="none";
	getById("fileList").style.height=parseInt('<%=he%>')-30;
}
</script>
</head>
<body  style="background-color:transparent;">
<input type="hidden" id="acceptName" name="acceptName" value="<%=acceptTextId %>"/>
<%if(obj==null&&!isedit){%> 
<form id="frmname" method="post" enctype="multipart/form-data" name="frmname">
<input type="hidden" id="fileSize" name="fileSize" value="<%=fileSize %>"/>
<input type="hidden" id="saveType" name="saveType" value="<%=saveType %>"/>
<input type="hidden" id="saveDir" name="saveDir" value="<%=saveDir %>"/>
<input type="hidden" id="he" name="he" value="<%=he %>"/>
<input type="hidden" id="count" name="count" value="0">
</form>
<%} %>
<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
<tr>
<td>
<div style="background:#F7F7F7;border-bottom:2px inset #cccccc;">
	<div id="templabel"  onclick="opentool();" title="展开工具栏" style="height: 8px;cursor: default;display: none;width: 100%" class="closetool"></div>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding-left: 2px;" id="tools">
		<tr>
			<td width="9" height="28"  onclick="closetool();" title="折叠工具栏">
				<div class="opentool"></div>
			</td>
			<td width="5" height="28" style="background: url('<%=request.getContextPath() %>/images/toolsplit.gif') no-repeat 2px 2px;">
			</td>
			<%if(obj==null&&!isedit){%> 
			<td align="center" nowrap="nowrap" width="28" height="28">
				<div class="addfilediv">
					<a href="javascript:void(0);" class="files" title="添加附件">
						<input id="upfile1" name="upfile1" type="file" onchange="File.add(this)">
					</a>
				</div>
			</td>
			<td align="center"  nowrap="nowrap" width="28" height="28">
				<div class="filediv" id="cladiv">
					<input type="button" value="" class="fileclear_end" onclick="clearAll();" id="clabtn" title="全部清除" alt="全部清除">
				</div>
			</td>
			<td align="center"  nowrap="nowrap" width="28" height="28">
				<div class="filediv" id="subdiv">
					<input type="button" name="submit" class="btn" id="subbtn" title="上传附件" alt="上传附件" onclick="uploadSave();">
				</div>
			</td>
			
			<% 
			String tmptitle = "";
			if(ext!=null&&ext.length()>20){
				tmptitle = ext;
				ext = ext.substring(0,20)+"...";
			}else{
				if(ext!=null&&ext.length()==0){
					ext ="无限制";
				}
			}
			%>
			<td>&nbsp;&nbsp;<span style="font:12px '宋体';color:#666666;cursor: default;" id="msgBox">选择文件并上传[个数:<%=fileCountMsg %>&nbsp;&nbsp;&nbsp;每个文件大小不超过:<%=fileSize %>M&nbsp;&nbsp;&nbsp;文件类型限制:<font title="<%=tmptitle %>" style="font:12px '宋体';color:#666666"><%=ext %></font>]</span></td> 
			<%}else if(obj!=null){%>
			<td align="center"  nowrap="nowrap" width="28" height="28">
				<div class="addfilediv">
					<input type="button" class="filerep"  title="重新上传" alt="重新上传" onclick="repSend();">
				</div>
			</td>
			<td>&nbsp;&nbsp;<font color="#666666" id="msgBox" face="宋体" style="cursor: default;">文件已上传成功</font></td> 
			<%}else if(isedit){ %>
			<td align="center"  nowrap="nowrap" width="28" height="28">
				<div class="addfilediv">
					<input type="button" class="filerep"  title="重新上传" alt="重新上传" onclick="repSend();">
				</div>
			</td>
			<td>&nbsp;&nbsp;<font color="#666666" id="msgBox" face="宋体" style="cursor: default;">已上传文件</font></td> 
			<%} %>
		</tr>
	</table>
</div>
</td>
</tr>
<tr>
<td align="left" valign="top">
<div id="fileList">
<table width="98%" cellpadding="0" cellspacing="0" height="100%">
<tr>
<td valign="top">
	<table border="0" cellpadding="0" cellspacing="0" width="95%" id="file" style="line-height: 25px;" height="100%">
	<%
	int i=0;
	if(obj!=null){
		Map<String,String> lastMap =(Map)obj;
		Set<String> keySet =lastMap.keySet();
		Iterator<String> it =keySet.iterator();
		while(it.hasNext()){ 
			String key =it.next();
			String t =lastMap.get(key);
		%>
			<tr id="tr<%=i %>">
				<td style="padding-left:5px;text-align:left;">
					<img src='<%=request.getContextPath()%>/images/fileokico.png' border='0' width='16' height='16' style="vertical-align: middle;"/>&nbsp;&nbsp;&nbsp;
					<a href='javascript:void(0);' onclick="showOkImg(this);"  onmouseout="hiddenOkImg();" value="<%=t%>" temp="<%=key %>"><%=key %></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href='javascript:void(0);' onclick='deleteOkFile(<%=i %>)' style='color:#336699'>删除</a>
					<input type="hidden" id="filePathName<%=i %>" name="filePathName" value="<%=key+"|"+t %>"/>
				</td>
			</tr>
		<%
		i++;
		}%>
		<script type="text/javascript">setAcceptText();</script>
	<%}else if(isedit){%>
		<!-- 创建编辑文件列表 -->
		<script type="text/javascript">createFileList();</script>
	<%}%>
	</table>
</td>
<td width="110px" valign="middle">
<%if(obj==null&&!isedit){%>
<div id="newPreview" style="margin: 5px;border: 1px solid #cccccc;display: none;"></div>
<%}else{ %>
<div id="filePre" style="margin: 5px;"></div>
<%} %>
</td>
</tr>
</table>
</div>
</td>
</tr>
</table>
</body>
</html>