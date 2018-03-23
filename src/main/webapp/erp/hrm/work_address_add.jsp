<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增地区</title>
<%
     String area = request.getParameter("areaId");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
    initInput("helpTitle","工作地区，您可以在此处添加您想要添加的工作地区。");
    
    saveOrEdit();
    
    //第一个输入框获取焦点
    document.getElementById("hrmAreaName").focus();
}

function saveOrEdit(){
    if(<%=area%> != null){
         Btn.hidden("backBtn");
         dwrHrmEmployeeService.getWorkareaByPk(<%=area%>,setpagevalue);
    }else{
         Btn.hidden("cancelBtn");
    }
}

function save(){
	var warnArr = new Array();
	warnArr[0] ="oposttextMust";
	warnInit(warnArr);
	var bl = validvalue('title');
	if(bl){
		var sposttext = DWRUtil.getValue("hrmAreaDesc");
		if(sposttext.length>200){
		 	setMustWarn("oposttextMust","工作地区备注必须在200字以内");
		 	return;
		}
	    Btn.close();
		var workarea =getWorkarea();
		dwrHrmEmployeeService.saveWorkarea(workarea,savecallback);
	}
}
	
function savecallback(data){
    Btn.open();
	if(<%=area%> != null){
	     alertmsg(data,"returnload()");
	}else{
	     if(data.success){
	     	confirmmsgAndTitle("添加工作地区成功！是否想继续添加工作地区？","sevent();","继续添加","closePage();","关闭页面");
		 }else{
		 	alertmsg(data);
		 }
	}
}
	
function returnload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function sevent(){
	DWRUtil.setValue("hrmAreaName","");
	DWRUtil.setValue("hrmAreaEngname","");
	DWRUtil.setValue("hrmAreaDesc","");
	document.getElementById("hrmAreaName").focus();
}

function getWorkarea(){
	var workarea = new Object();
	if(<%=area%> != null){
	    workarea.primaryKey = <%=area%>;
	}
	workarea.hrmAreaName = DWRUtil.getValue("hrmAreaName");
	workarea.hrmAreaEngname = DWRUtil.getValue("hrmAreaEngname");
	workarea.hrmAreaDesc = DWRUtil.getValue("hrmAreaDesc");
	return workarea;
}
	
function setpagevalue(data){
	if(data!=null){
		var workarea =data.resultList[0];
		DWRUtil.setValue("hrmAreaName",workarea.hrmAreaName);
		DWRUtil.setValue("hrmAreaEngname",workarea.hrmAreaEngname);
		DWRUtil.setValue("hrmAreaDesc",workarea.hrmAreaDesc);
	}
}

function closePage(){
	closeMDITab();
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">添加/编辑工作地区</div>
		<table class="inputtable">
			<tr>
				<th>
					<em>*</em>&nbsp;&nbsp;地区名称
				</th>
				<td>
					<input type="text" id="hrmAreaName" must="地区名称不能为空!"
						formust="hrmAreaNameMust" maxlength="25" size="25">
					<label id="hrmAreaNameMust"></label>
				</td>
			</tr>
			<tr>
				<th>
					英文名称
				</th>
				<td>
					<input type="text" id="hrmAreaEngname" maxlength="50"
						size="50">
				</td>
			</tr>
			<tr>
					<th></th>
						<td><label id="oposttextMust"></label></td>
					</tr>
			<tr>
				<th>
					备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注
				</th>
				<td>
					<textarea id="hrmAreaDesc"></textarea>
				</td>
			</tr>
		</table>
	</div>
		<table align="center">
			<tr>
				<td>
					<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存工作地区信息" ></btn:btn>
				</td>
				<td width="20%"></td>
				<td>
					<div id="cancelBtn"><btn:btn onclick="window.parent.MoveDiv.close();" value="取 消 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></div>
					<div id="backBtn"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></div>
				</td>
			</tr>
		</table>
</body>
</html>