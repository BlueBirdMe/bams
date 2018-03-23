<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String aid =request.getParameter("aid");
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('title',"欢迎您在这里添加相册，可以将相片上传于新的相册");

//第一个输入框获取焦点
    document.getElementById("photosname").focus();
	if(<%=aid%>!=null){
		dwrOACompanyResourcesService.getAlbumByPk(<%=aid%>,false,setpagevalue);
	}
}
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("photosname",tmp.albumName);
			setSelectValue("photostype",tmp.albumType);
			DWRUtil.setValue("photostext",tmp.albumDesc);
			//设置人员
			Sys.showEmpNames(tmp.albumEmps,"photosempsname","photosempsids");
			//设置部门
			Sys.showDeptNames(tmp.albumDeps,"photosdeptname","photosdeptids");
		}
	}
}
function save(){
   var warnArr = new Array();
   warnArr[0] = "photosempsnameMust";
   warnArr[1] = "photosdeptnameMust";
   //清空所有信息提示
   warnInit(warnArr);
	var bl = validvalue('title');
	if(bl){
	 var depid = document.getElementById("photosempsname").value;
        var empid = document.getElementById("photosdeptname").value;
        //验证可选框
        if(trim(depid) == "" && trim(empid) == ""){
        	//可选框提示
        	var chooseArr = new Array();
		chooseArr[0] = "photosempsnameMust";
		chooseArr[1] = "photosdeptnameMust";
		chooseWarn(chooseArr);
           //返回顶端
           window.scrollTo(0,0);
           return false;
	}else{
		dwrOACompanyResourcesService.saveAlbum(getalbum(),savecallback);
		Btn.close();
		}
	}
}
	
function savecallback(data){
	Btn.open();
	if(<%=aid%>!=null){
		alertmsg(data,"currentload()");
	}else{
		if(data.success){
			confirmmsgAndTitle("添加相册成功！是否想继续添加?","repload();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}

function repload(){
	DWRUtil.setValue("photosname","");
	DWRUtil.setValue("photosempsname","");
	DWRUtil.setValue("photosempsids","");
	DWRUtil.setValue("photosdeptname","");
	DWRUtil.setValue("photosdeptids","");
	document.getElementById("photostype").selectedIndex =0;
	//设置fck
	DWRUtil.setValue("photostext","");
	document.getElementById("photosname").focus();
}
function getalbum(){
	var album = new Object();
	if(<%=aid%>!=null){
		album.primaryKey = <%=aid%>;
	}
	album.albumName = DWRUtil.getValue("photosname");
	album.albumType = DWRUtil.getValue("photostype");
	album.albumEmps = DWRUtil.getValue("photosempsids");
	album.albumDeps = DWRUtil.getValue("photosdeptids");
	album.albumDesc = DWRUtil.getValue("photostext");
	return album;
}
function currentload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function getdept(){
	if(<%=aid%>!=null){
		var box = SEL.getDeptIds("check","photosdeptname","photosdeptids","processloadfrm");
		box.show();
	}
	else{
	   var box=SEL.getDeptIds("check","photosdeptname","photosdeptids");
	   box.show();
	}
}
function getemployee(){
	if(<%=aid%>!=null){
		var box = SEL.getEmployeeIds("check","photosempsname","photosempsids","processloadfrm");
		box.show();
	}else{
		var box = SEL.getEmployeeIds("check","photosempsname","photosempsids");
		box.show();
	}
}
	function backToNewsList(){
	Sys.href('<%=contextPath%>/erp/company_resources/photos_albumquery.jsp');
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
<title>创建/编辑相册</title>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
		<div class="formTitle">创建/编辑相册</div>
			<div>
			<table class="inputtable">
			<tr>
				<th width="12%"><em>*</em>&nbsp;&nbsp;相册名称</th>
				<td>
				<input id="photosname" must="相册名称不能为空" formust="photosnamemust" size="30" maxlength="20">
				<label id="photosnamemust"></label>
				</td>
	
			</tr>
			<tr>
				<th>&nbsp;&nbsp;相册类型</th>
				<td>
				<select id="photostype" must="请选择相册类型" formust="photostypemust">
				<%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"09") %>
				</select>
				<label id="photostypemust"></label>
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color:blue">•</span>&nbsp;&nbsp;浏览范围<br/>(人员)</th>
				<td width="40%">
				<textarea  id="photosempsname" readonly="readonly" onclick="getemployee();" title="点击拾取人员" 
				linkclear="photosempsids"></textarea>
				<input type="hidden" id="photosempsids">
				</td>
				<td width="40%">
				<label id="photosempsnameMust">
				</label>
				</td>
			</tr>
			<tr>	
			<th width="20%"><span style="color:blue">•&nbsp;&nbsp;</span><font color="black">浏览范围<br/>(部门)</font></th>
				<td width="40%">
				<textarea id="photosdeptname" readonly="readonly" onclick="getdept();" title="点击拾取部门" linkclear="photosdeptids" ></textarea>
				<input type="hidden" id="photosdeptids">
				</td>
				<td width="40%"><label id="photosdeptnameMust"></label>
				</td>
			</tr>
			<tr>
				<th>相册描述</th>
				<td colspan="3">
				<textarea id="photostext" style="height: 200px"></textarea>
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
			<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
			</td>
			<td style="width: 15px;"></td>
				<td>
				<%if (aid == null){ %>
				<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%}else{ %>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%} %>
				</td>
		</tr>
		</table>
		</body>
</html>