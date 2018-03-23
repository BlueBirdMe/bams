<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String jid =request.getParameter("jid");
String isedit = "false";
if(jid!=null){//编辑时使用
	isedit = "true";
}
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle',"您可以在此处添加您要添加的期刊，此期刊可被他人查阅！");
	//第一个输入框获取焦点
	document.getElementById("jourcount").focus();
	if(<%=jid%>!=null){
		dwrOACompanyResourcesService.getJournalsMangerByPk(<%=jid%>,setpagevalue);
	}
}
var fckvalue="";
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			setSelectValue("jourtype",tmp.journalsTypeId);
			DWRUtil.setValue("jourcount",tmp.journalsCount);
			DWRUtil.setValue("jourcode",tmp.journalsCode);
			//fckvalue临时
			fckvalue = tmp.journalsContext;
			//封面
			
			if(tmp.journalsFace>0){
			
				dwrCommonService.getImageInfoListToString(tmp.journalsFace,setfaceimg);
			}
			//附件
			if(tmp.journalsAffix!=null && tmp.journalsAffix!=undefined && tmp.journalsAffix!= "undefined" && tmp.journalsAffix.length>0){
				dwrCommonService.getAttachmentInfoListToString(tmp.journalsAffix,setaccept);
			}
		}
	}
}
function setfaceimg(data){
	Sys.setFilevalue("jourface",data);
}

//放入附件
function setaccept(data){
	Sys.setFilevalue("jourfiles",data);
}

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}

function save(){
	var warnArr = new Array();
	warnArr[0] = "jourfilesMust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
		var accs = DWRUtil.getValue("jourfiles");//附件
		if(accs==""){
		   window.scrollTo(0,0);
			setMustWarn("jourfilesMust","请添加并上传附件");  
		}else{
			var face = DWRUtil.getValue("jourface");
			dwrOACompanyResourcesService.saveJournals(getjour(),accs,face,savecallback);
			Btn.close();
		}
	}
}

function savecallback(data){
		Btn.open();
	if(<%=jid%>!=null){
		alertmsg(data,"returnload()");
	}else{
		if(data.success){
			confirmmsgAndTitle("添加期刊成功！是否想继续添加期刊?","repload();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}

function returnload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
	
function repload(){
	DWRUtil.setValue("jourcount","");
	DWRUtil.setValue("jourcode","");
	document.getElementById("jourtype").selectedIndex =0;
	//设置fck
	fck.SetHTML("");
	//刷新附件
	Sys.setFilevalue("jourfiles","");
	Sys.setFilevalue("jourface","");
	document.getElementById("jourcount").focus();
}
function getjour(){
	var jour = new Object();
	if(<%=jid%>!=null){
		jour.primaryKey = <%=jid%>;
	}
	jour.journalsTypeId = DWRUtil.getValue("jourtype");
	jour.journalsCount = DWRUtil.getValue("jourcount");
	jour.journalsCode = DWRUtil.getValue("jourcode");
	jour.journalsContext = fck.GetXHTML();
	return jour;
}

function backToNewsList(){
	Sys.href('<%=contextPath%>/erp/company_resources/journals_manger.jsp');
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
<title>添加期刊</title>
</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle" ></label></div>
		<div class="formTitle">
		添加期刊
	</div>
	<div>
	<table class="inputtable">
	<tr>
		<th width="12%"><em>*</em>&nbsp;&nbsp;期刊期数</th>
		<td>
		<input type="text" id="jourcount" maxlength="20" must="期数不能为空！" formust="jourcountMust" value="">
		<label id="jourcountMust"></label>
		</td>
		<td></td>
		<th rowspan="3" width="12%">期刊封面</th>
		<td rowspan="3">
		<file:imgupload width="128" acceptTextId="jourface" height="128" edit="<%=isedit %>"></file:imgupload>
		</td>
	</tr>

	<tr>
		<th>期刊类型</th>
		<td>
		<select id="jourtype" must="请选择期刊类型" formust="jourtypemust" value="">
		<%=UtilTool.getJournalsTypeOptions(this.getServletContext(),request,null) %>
		</select>	
		</td>
			<td>
		<label id="jourtypemust"></label>
		</td>
	</tr>
	<tr>
		<th>期刊号</th>
		<td>
		<input type="text" id="jourcode" maxlength="20">	
		</td>
		<td>
	</tr>
	<tr>
		<th></th>
		 <td>
		 <label id="jourfilesMust" height="130" width="90%"></label>
		 </td>
	 <tr>
	<tr>
		<th><em>*</em>&nbsp;&nbsp;期刊附件</th>
		<td colspan="4">
		<file:multifileupload width="90%" acceptTextId="jourfiles" height="100" saveType="file" edit="<%=isedit %>"></file:multifileupload>
		</td>
		<td>
		</td>
	</tr>
	<tr>
		<th>期刊描述</th>
		<td colspan="4">
		<FCK:editor instanceName="jourtext" width="90%" height="240"></FCK:editor>
		</td>
		<td>
		</td>
	</tr>
	</table>
	</div>
	<br>
</div>
<br/>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
	<td>
	<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
	</td>
	<td style="width: 15px;"></td>
	<td>
	<%if (jid == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
</tr>
</table>
</body>
</html>