<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String aid =request.getParameter("aid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
	window.onload = function(){
	    useLoadingMassage();
		initInput('title',"可以通过设置浏览范围，让指定的人员观看相片！");
		dwrOACompanyResourcesService.getAlbumByPk(<%=aid%>,true,setpagevalue);
	}
	function setpagevalue(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("photosname",tmp.albumName);
				DWRUtil.setValue("photostype",tmp.libraryType.libraryInfoName);
				document.getElementById("photostext").innerHTML = tmp.albumDesc;
				//设置人员
				Sys.showEmpNames(tmp.albumEmps,"photosempsname","photosempsids");
				//设置部门
				Sys.showDeptNames(tmp.albumDeps,"photosdeptname","photosdeptids");
			}
		}
	}
	function updatealbum(){
		var bl = validvalue('title');
		if(bl){
			var album = getalbum();
			if(album.albumEmps=="" && album.albumDeps==""){
				DWRUtil.setValue("title","浏览范围至少选择一个");
				return false;
			}
			dwrOACompanyResourcesService.updateAlbum(album,savecallback);
			Btn.close();
		}
	}
	
	function savecallback(data){
	    Btn.open();
		alertmsg(data,"currentload()");

	}

	function getalbum(){
		var album = new Object();
		album.primaryKey = <%=aid%>;
		album.albumEmps = DWRUtil.getValue("photosempsids");
		album.albumDeps = DWRUtil.getValue("photosdeptids");
		return album;
	}
	function currentload(){
		window.parent.MoveDiv.close();
		window.parent.queryData();
	}
	function getdept(){
		var box = SEL.getDeptIds("check","photosdeptname","photosdeptids","processloadfrm");
		box.show();
	}
	function getemployee(){
		var box = SEL.getEmployeeIds("check","photosempsname","photosempsids","processloadfrm");
		box.show();
	}
</script>
<title>设置相册浏览范围</title>
</head>
<body class="inputcls">
	<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
<div class="formTitle">设置相册浏览范围</div>
	<div>
	<table class="inputtable">
	<tr>
	<th width="15%" >相册名称</th>
	<td id="photosname"></td>
	<th>相册类型</th>
	<td id="photostype"></td>
	</tr>
	<tr>
	<th><li style="color: blue"><font color="black">浏览范围<br/>(人员)</font></li></th>
	<td colspan="3">
	<textarea id="photosempsname" readonly="readonly" onclick="getemployee();" title="点击选择人员" linkclear="photosempsids"></textarea>
	<input type="hidden" id="photosempsids">
	</td>
	</tr>
	<tr>
	<th><li style="color: blue"><font color="black">浏览范围<br/>(部门)</font></li></th>
	<td colspan="3">
	<textarea id="photosdeptname" readonly="readonly" onclick="getdept();" title="点击选择部门" linkclear="photosdeptids" ></textarea>
	<input type="hidden" id="photosdeptids">
	</td>
	</tr>
	<tr>
	<th>相册描述</th>
	<td colspan="3">
	<div  id="photostext" style="width: 90%;height: 260px; border: 1px solid #dddddd"></div>
	</td>
	</tr>
	</table>
	<br>
	</div>
</div>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
<btn:btn onclick="updatealbum();" value="确 定 " imgsrc="../../images/fileokico.png" title="确定"></btn:btn>
</td>
<td style="width: 10px;"></td>
<td>
<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
</td>
</tr>
</table>
</body>
</html>