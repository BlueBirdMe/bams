<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<title>添加照片</title>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    document.getElementById("albumid").focus();
	initInput('title',"您可以在此处上传照片,展示生活的多姿多彩！");
}
function showorhidden(ck){
	var pname = document.getElementById("photoname");
	if(ck.checked){
		pname.value ="";
		pname.style.display="none";
	}else{
		pname.style.display="";
		pname.focus();
	}
}
function save(){
	var warnArr = new Array();
	warnArr[0] = "photonamemust";
	warnArr[1] = "photonamemsut";
	//清空所有信息提示
	warnInit(warnArr);

	var bl = validvalue('title');
	if(bl){
	 
		if(document.getElementById("pck").checked==false){
			if(document.getElementById("photoname").value.length==0){
				setMustWarn("photonamemust","请输入相片名称");
				return false;
			}
		}
		var f =document.getElementById("photofile").value;
		var fs = document.getElementById("photofiles").value;
		if(f.length==0&&fs.length==0){
			
			setMustWarn("photonamemsut","请选择上传的图片");
			return false;
		}
		dwrOACompanyResourcesService.savePhotos(getphoto(),f,fs,savecallback);
		Btn.close();
	}
}

function savecallback(data){
     Btn.open();
     if(data.success){
     	confirmmsgAndTitle("添加相册成功！是否想继续添加","repload();","继续添加","closePage();","关闭页面");
	 }else{
	 	alertmsg(data);
	 }
}
function backToNewsList(){
	Sys.href('<%=contextPath%>/erp/company_resources/photos_albumquery.jsp');
}
function repload(){
	DWRUtil.setValue("photoname","");
	
	var ck = document.getElementById("pck");
	ck.checked =true;
	showorhidden(ck);
	
	document.getElementById("albumid").selectedIndex =0;
	DWRUtil.setValue("photosdesc","");
	Sys.setFilevalue("photofile","");
	Sys.setFilevalue("photofiles","");
}
function getphoto(){
	var photo = new Object();
	photo.photoName = DWRUtil.getValue("photoname");
	photo.albumId = DWRUtil.getValue("albumid");
	photo.photoDesc = DWRUtil.getValue("photosdesc");
	return photo;
}


function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">添加相片</div>
	<div>
	<table class="inputtable">
	<tr>
		<th width="12%">选择相册</th>
		<td>
		<select id="albumid" must="请选择相册" formust="albumidmust">
		<%=UtilTool.getAlbumOptions(this.getServletContext(),request,null) %>
		</select>
		<label id="albumidmust"></label>
		</td>
	</tr>
	<tr>
		<th width="12%"><em>*</em>&nbsp;&nbsp;相片名称</th>
		<td>
			<div>
			<input type="checkbox" id="pck" checked="checked" onclick="showorhidden(this)"><label for="pck">采用文件名</label>
			</div>
			<input type="text" id="photoname" style="display: none;"><label id="photonamemust"></label>
		</td>
	</tr>
	<tr>
		<th><em>*</em>&nbsp;&nbsp;相片</th>
		<td colspan="3" width="90%">
			<label id="photonamemsut"></label>
			<DIV class="tabdiv" style="width: 90%">
			<UL class="tags">
			<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">单张添加</A></LI>
			<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">批量添加</A> </LI>
			</UL>
				<DIV class="tagContentdiv">
				<DIV class="tagContent" id="tag0">
				<file:imgupload width="128" acceptTextId="photofile" height="150"></file:imgupload>
				</DIV>
				<DIV class="tagContent" id="tag1">
				<file:multifileupload width="100%" acceptTextId="photofiles" height="150" type="image"></file:multifileupload>
				</DIV>
		</DIV>
		</DIV>
				</td>
		</tr>
	<tr>
		<th>相片描述</th>
		<td colspan="3">
		<textarea id="photosdesc"></textarea>
		</td>
	</tr>
	</table>
	<br>
	</div>
	</div>
	<br>
	<table align="center" cellpadding="0" cellspacing="0">
	<tr>
    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    	<td style="width: 10px;"></td>
    	<td><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
	</tr>
	</table>
		<script type="text/javascript">
		var tab =new SysTab('<%=contextPath%>');
		</script>
</body>
</html>