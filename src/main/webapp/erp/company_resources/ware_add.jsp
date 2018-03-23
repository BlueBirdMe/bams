<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>新增知识</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%
	String wid =request.getParameter("wid");
	String isedit = "false";
	if(wid!=null){//编辑时使用
		isedit = "true";
	}
	 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
window.onload = function() {
	useLoadingMassage();
	initInput('helpTitle', "您可以在此处添加您想要上传的知识，以便他人查阅！");
	document.getElementById("warename").focus();
	if ( <%=wid %>!=null) {
		dwrOACompanyResourcesService.getWarehouseByPk( <%=wid %>, setpagevalue);
	} 
}

var fckvalue = "";
function setpagevalue(data) {
	if (data != null) {
		if (data.resultList.length > 0) {
			var tmp = data.resultList[0];
			DWRUtil.setValue("warename", tmp.oaWareName);
			DWRUtil.setValue("warekeyword", tmp.oaKeyword);
			setSelectValue("waretype", tmp.oaWareType); //fckvalue临时
			fckvalue = tmp.oaWareText;
			if (fck != null) {
				fck.SetHTML(fckvalue);
			} //附件
			if (tmp.oaWareAcce != null && tmp.oaWareAcce != undefined && tmp.oaWareAcce != "undefined" && tmp.oaWareAcce.length > 0) {
				dwrCommonService.getAttachmentInfoListToString(tmp.oaWareAcce, setaccept);
			}
		}
	}
}

 //放入附件
function setaccept(data) {
	Sys.setFilevalue("warefiles", data);
}

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck = editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}

function returnload() {
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function save() {
	var warnArr = new Array();
	warnArr[0] = "oaNewsTextMust"; //清空所有信息提示
	warnInit(warnArr); //验证常用组件
	var bl = validvalue('helpTitle');
	if (bl) {

		var context = fck.GetXHTML();
		if (trim(context) == "" || trim(context) == null || trim(context).length < 10) {
			setMustWarn("oaNewsTextMust", "知识内容不能为空，且知识内容字符不能小于10个字符。");
			fck.Focus();
		} else {
			var accs = DWRUtil.getValue("warefiles"); //附件
			dwrOACompanyResourcesService.saveWarehouse(getWarehouse(), accs, savecallback);
			Btn.close();
		}
	}
}

function savecallback(data) {
	Btn.open();
	if ( <%=wid %>!=null) {
		alertmsg(data, "returnload()");
	} else {
		if(data.success){
			confirmmsgAndTitle("添加知识！是否想继续添加知识?", "repload();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}


function backToNewsList() {
	Sys.href('<%=contextPath%>/erp/company_resources/waremanger.jsp');
}

function repload() {
	DWRUtil.setValue("warename", "");
	DWRUtil.setValue("warekeyword", "");
	document.getElementById("waretype").selectedIndex = 0; //设置fck
	fck.SetHTML(""); //刷新附件
	Sys.setFilevalue("warefiles", "");
	window.scrollTo(0, 0);
	document.getElementById("warename").focus();
}

function getWarehouse() {
	var warehouse = new Object();
	if ( <%=wid %>!=null) {
		warehouse.primaryKey = <%=wid %>;
	}
	warehouse.oaWareName = DWRUtil.getValue("warename");
	warehouse.oaKeyword = DWRUtil.getValue("warekeyword");
	warehouse.oaWareType = DWRUtil.getValue("waretype");
	warehouse.oaWareText = fck.GetXHTML();
	return warehouse;
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>

</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">添加知识</div>
				<div>
   					<table  class="inputtable" border="0">
					 <tr>
						<th>
						<em>*</em>&nbsp;&nbsp;知识标题
						</th>
						<td colspan="2">
							<input type="text" id="warename"  maxlength="50" style="width: 39.8%"
							 must="知识标题不能为空。"formust="warenameMust" value="">	
							 <label id="warenameMust">
							 </label>
						</td>

					  </tr>
					  <tr>
						    <th>关键字</th>
							<td>
								<input type="text" id="warekeyword" maxlength="50" style="width: 40%" >
							</td>
					   </tr>
					   <tr>
						<th>知识类型</th>
							<td align="left">
							<select id="waretype" must="知识类型不能为空。" formust="waretypemust" value="">
							<%=UtilTool.getWareTypeOptions(this.getServletContext(),request,null,EnumUtil.OA_TYPE.WARW.value) %>
							</select>
							</td>
							<td>
							<label id="waretypemust"></label>
							</td>
						</tr>
						<tr>
						<th>知识附件</th>
							<td colspan="3">
							<file:multifileupload width="90%" acceptTextId="warefiles" height="100" saveType="file" edit="<%=isedit %>"></file:multifileupload>
							</td>
						</tr>
						<tr>
						<th>
						<em>*</em> &nbsp;&nbsp;知识内容
						</th>
							<td colspan="3" >
							<label id="oaNewsTextMust">
							</label>
							<FCK:editor instanceName="waretext" width="90%" height="240">
							</FCK:editor>
							</td>
						</tr>
						</table>
					</div>
				<br/>
			</div>
				<br>
			<table align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
				</td>
				<td style="width: 10px;">
				</td>
				<td>
				<%if (wid == null){ %>
				<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%}else{ %>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%} %>
				</td>
			</tr>
			</table>
	</body>
</html>