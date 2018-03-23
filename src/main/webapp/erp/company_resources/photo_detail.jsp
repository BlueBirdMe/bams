<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相片明细</title>
<%
String pid =request.getParameter("pid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
var imgId;
	window.onload = function(){
		useLoadingMassage();
		dwrOACompanyResourcesService.getPhotoByPk(<%=pid%>,setpagevalue);
	}
	function setpagevalue(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("photoname",tmp.photoName);
				DWRUtil.setValue("albumname",tmp.album.albumName);
				var str="否"
				if(tmp.isAlubmFace!=null&&tmp.isAlubmFace.length>0){
					str ="<font color='red'>是</font>";
				}
				document.getElementById("isface").innerHTML=str;
				DWRUtil.setValue("phototime",tmp.photoTime);
				document.getElementById("phototext").innerHTML = tmp.photoDesc;
				
				var face = document.getElementById("photo");
				imgId = tmp.imageId;
				face.src += "&imgId=" + imgId;
			}
		}
	}
	function photoShow(){
		window.open("<%=contextPath%>/erp/company_resources/photo.jsp?imgId="+imgId);
	}
</script>
</head>
<body class="inputcls">
<div class="requdivdetail"><label>查看帮助:&nbsp;可以点击菜单栏，添加相片，进行相片上传到公司相册。</label></div>
	<div class="detailtitle">相片明细</div>
	<table class="detailtable" align="center" border="0">
	<tr>
	<th width="15%">所属相册</th>
	<td id="albumname" class="detailtabletd" ></td>
	<th>是否为相册封面</th>
	<td id="isface" class="detailtabletd" ></td>
	</tr>
	<tr>
	<th>上传时间</th>
	<td id="phototime" class="detailtabletd"></td>
	<th></th>
	<td></td>
	</tr>
	<tr>
	<th>相片描述</th>
	<td colspan="3" id="phototext" class="detailtabletd">
	</td>
	</tr>
	<tr>
	<td colspan="4" style="text-align: center;">
		<br/>
		<file:imgshow id="photo" onclick="photoShow()" style="cursor: pointer;width: 200px;height: auto" title="点击查看大图" alt="点击查看大图"></file:imgshow><br/>
		<strong>相片名称：</strong><label id="photoname" onclick="photoShow()" style="cursor: pointer;"></label>
	</td>
	</tr>
	</table>
</body>
</html>