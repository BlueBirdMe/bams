<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>新增表格</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String fid =request.getParameter("fid");
String isedit = "false";
if(fid!=null){//编辑时使用
	isedit = "true";
}
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function() {
	useLoadingMassage();
	initInput('helpTitle', "您可以在此处添加您要添加的表格,方便办公使用！");
	document.getElementById("formsname").focus();
	if ( <%=fid %>!=null) {
		dwrOACompanyResourcesService.getFormsByPk( <%=fid %>, setpagevalue);
	}
}
function setpagevalue(data) {
	if (data != null) {
		if (data.resultList.length > 0) {
			var tmp = data.resultList[0];
			DWRUtil.setValue("formsname", tmp.oaFormName);
			setSelectValue("formstype", tmp.oaFormType);
			DWRUtil.setValue("formstext", tmp.oaFormText); //附件
			if (tmp.oaFormAcce != null && tmp.oaFormAcce != undefined && tmp.oaFormAcce != "undefined" && tmp.oaFormAcce.length > 0) {
				dwrCommonService.getAttachmentInfoListToString(tmp.oaFormAcce, setaccept);
			}
		}
	}
} //放入附件
function setaccept(data) {
	Sys.setFilevalue("formsfiles", data);
}
function save() {
	var warnArr = new Array();
	warnArr[0] = "formsfilesMust"; //清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if (bl) {
		var accs = DWRUtil.getValue("formsfiles"); //附件
		if (accs == "") {
			window.scrollTo(0, 0);
			setMustWarn("formsfilesMust", "请添加并上传附件");
			return false;
		}
		dwrOACompanyResourcesService.saveForms(getforms(), accs, savecallback);
		Btn.close();
	}
}
function savecallback(data) {
	Btn.open();
	if ( <%=fid %>!=null) {
		alertmsg(data, "returnload()");
	} else {
		if(data.success){
			confirmmsgAndTitle("添加表格！是否想继续添加表格?", "repload();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}
function backToNewsList() {
	Sys.href('<%=contextPath%>/erp/company_resources/formsmanger.jsp');
}
function returnload() {
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function repload() {
	DWRUtil.setValue("formsname", "");
	document.getElementById("formstype").selectedIndex = 0;
	DWRUtil.setValue("formstext", ""); //刷新附件
	Sys.setFilevalue("formsfiles", "");
	document.getElementById("formsname").focus();
}
function getforms() {
	var forms = new Object();
	if ( <%=fid %>!=null) {
		forms.primaryKey = <%=fid %>;
	}
	forms.oaFormName = DWRUtil.getValue("formsname");
	forms.oaFormType = DWRUtil.getValue("formstype");
	forms.oaFormText = DWRUtil.getValue("formstext");
	return forms;
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail" >
		<div class="requdiv"><label id="helpTitle" ></label></div>
			<div class="formTitle">
			<font size="2"><strong>添加表格</strong></font>
 			</div>
			<div>
			<table class="inputtable">
			<tr>
				<th><em>*</em>&nbsp;&nbsp;表格名称</th>
				<td>
				<input type="text" id="formsname" must="请输入表格名称" maxlength="50" size="45" style="width: 43%" 
				formust="formsnameMust" value="">
				<label id="formsnameMust">
				</label>
				</td>
				</tr>
				<tr>
					<th>表格类型</th>
					<td>
					<select id="formstype" must="请选择类型" formust="formstypemust" value="">
					<%=UtilTool.getWareTypeOptions(this.getServletContext(),request,null,EnumUtil.OA_TYPE.FORMS.value) %>
					</select>
					</td>
					<td>
					<label id="formstypemust">
					</label>
					</td>
				</tr>
				<tr>
	 				<th></th>
	 				<td>
	 				<label id="formsfilesMust">
	 				</label>
	 				</td>
	 			<tr>
				 <tr>
	 				<th>
	 				<em>*</em>&nbsp;&nbsp;表格附件
	 				</th>
	    			 <td colspan="4">
					<file:multifileupload width="89.5%"  acceptTextId="formsfiles" height="100" saveType="file" edit="<%=isedit %>" type="office">
					</file:multifileupload>
					</td>
				</tr>
				<tr>
					<th>表格说明</th>
					<td colspan="3">
					<textarea rows="4" cols="6" id="formstext" style="height: 200px;">
					</textarea>
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
				<td style="width: 10px;">
				</td>
				<td>
				<%if (fid == null){ %>
				<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%}else{ %>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%} %>
				</td>
			</tr>
			</table>
</body>
</html>