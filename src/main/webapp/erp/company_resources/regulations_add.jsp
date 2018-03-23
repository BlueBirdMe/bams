<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<title>添加规章制度</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%
			response.setHeader("Cache-Control","no-cache"); 
			response.setHeader("Pragma","no-cache"); 
			response.setDateHeader("Expires",0);
			String rid = request.getParameter("rid");
			String isedit = "false";
			if (rid != null) {//编辑时使用
				isedit = "true";
			}
		%>
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrOACompanyResourcesService.js"></script>
		<script type="text/javascript">
window.onload = function(){	
	useLoadingMassage();
	initInput('title',"您可以在此处添加您要添加的规章制度，他人可以查阅，且遵守！");
	//第一个输入框获取焦点
	document.getElementById("regultitle").focus();
	if(<%=rid%>!=null){
		 dwrOACompanyResourcesService.getOaRegulations(<%=rid%>,setpagevalue);
	}
}

var fckvalue="";
var fck;
function setpagevalue(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			setSelectValue("regultype",tmp.oaRegulationsType);
			setRadioValueByName("regulstatus",tmp.regulationsStatus);
			DWRUtil.setValue("regultitle",tmp.oaRegulationsTitle);
			DWRUtil.setValue("regulcreatetime",tmp.oaRegulationsTime);
			DWRUtil.setValue("regulstarttime",tmp.regulatStratTime);
			//fckvalue临时
			fckvalue = tmp.regulatContext;
			if(fck!=null){
				fck.SetHTML(fckvalue);
			}
			//附件
			if(tmp.oaRegulationsAttachs!=null && tmp.oaRegulationsAttachs!=undefined && tmp.oaRegulationsAttachs!= "undefined" && tmp.oaRegulationsAttachs.length>0){
				dwrCommonService.getAttachmentInfoListToString(tmp.oaRegulationsAttachs,setaccept);
			}
		}
	}
}
	
	//放入附件
function setaccept(data){
	Sys.setFilevalue("regulfiles",data);
}

function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}
function returnload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function save(){
	var warnArr = new Array();
	warnArr[0] = "regulfilesmust";
	//清空所有信息提示
	warnInit(warnArr);
   
	var bl = validvalue('title');
	if(bl){
		var reg = getregul();
		var accs = DWRUtil.getValue("regulfiles");//附件
		if(accs== "" && reg.regulatContext.length==0){
			setMustWarn("regulfilesmust","请输入规章制度内容或上传附件");  
			return false;
		}
		dwrOACompanyResourcesService.saveRegulations(reg,accs,savecallback);
		Btn.close();
	}
}
	
function savecallback(data){
     Btn.open();
	if(<%=rid%>!=null){
		alertmsg(data,"returnload()");
	}else{
		if(data.success){
			document.getElementById("regultitle").focus();
		 	confirmmsgAndTitle("添加制度成功！是否想继续添加?","repload();","继续添加","closePage();","关闭页面");
		}else{
		 	alertmsg(data);
		}
	}
}

function repload(){

	DWRUtil.setValue("regultitle","");
	DWRUtil.setValue("regulcreatetime","<%=UtilWork.getToday()%>");
	DWRUtil.setValue("regulstarttime","<%=UtilWork.getToday()%>");
	document.getElementById("regultype").selectedIndex =0;
	
	//设置fck
	fck.SetHTML("");
	//刷新附件
	Sys.setFilevalue("regulfiles","");
	 window.scrollTo(0,0);

	document.getElementById("regultitle").focus();
}
function backToNewsList(){
	Sys.href('<%=contextPath%>/erp/company_resources/regulations_manger.jsp');
}
function getregul(){
	var regulations = new Object();
	if(<%=rid%>!=null){
		regulations.primaryKey = <%=rid%>;
	}
	regulations.oaRegulationsTitle = DWRUtil.getValue("regultitle");
	regulations.oaRegulationsType = DWRUtil.getValue("regultype");
	
	regulations.regulationsStatus = getRadioValueByName("regulstatus");
	regulations.oaRegulationsTime = DWRUtil.getValue("regulcreatetime");
	regulations.regulatStratTime = DWRUtil.getValue("regulstarttime");
	regulations.regulatContext = fck.GetXHTML();
	return regulations;
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
	</head>
	<body class="inputcls">
		<div class="formDetail">
			<div class="requdiv">
				<label id="title"></label>
			</div>
			<div class="formTitle">
				添加/编辑规章制度
			</div>
			<div>
				<table class="inputtable">
					<tr>
						<th>
							<em>* </em>&nbsp;&nbsp;规章标题
						</th>
						<td colspan="3">
							<input type="text" id="regultitle" must="请输入规章制度标题"
								formust="regultitlemust" style="width: 74%" maxlength="50">
							<label id="regultitlemust"></label>
						</td>
					</tr>
					<tr>
						<th>
							&nbsp;&nbsp;规章类型
						</th>
						<td>
							<select id="regultype" must="请选择规章制度类型" formust="regultypemust">
								<%=UtilTool.getSelectOptions(this.getServletContext(), request, null, "11")%>
							</select>
							<label id="regultypemust"></label>
						</td>
						<th>
							规章状态
						</th>
						<td>
							<%=UtilTool.getRadioOptionsByEnum(EnumUtil.SYS_ISACTION.getSelectAndText(null), "regulstatus")%>
							<label id="regulstatusmust"></label>
						</td>
					</tr>
					<tr>
						<th>
							创建日期
						</th>
						<td>
							<input id="regulcreatetime" type="text" readonly="readonly"
								class='Wdate'
								onFocus="WdatePicker({isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'regulstarttime\')}'});"
								value="<%=UtilWork.getToday()%>" />
						</td>
						<th>
							生效日期
						</th>
						<td>
							<input id="regulstarttime" type="text" readonly="readonly"
								class='Wdate'
								onFocus="WdatePicker({isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'regulcreatetime\')}'});"
								value="<%=UtilWork.getToday()%>" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<label id="regulfilesmust"></label>
						</td>
					</tr>
					<tr>
						<th>
							<span style="color:blue">•</span>&nbsp;&nbsp;规章附件
						</th>
						<td colspan="3">
							<file:multifileupload width="90%" acceptTextId="regulfiles" height="100" saveType="file" edit="<%=isedit%>"
								type="office"></file:multifileupload>
						</td>
					</tr>
					<tr>
						<th>
								<span style="color:blue">•</span>&nbsp;&nbsp;规章内容
						</th>
						<td colspan="3">
							<FCK:editor instanceName="regultext" width="90%" height="240"></FCK:editor>
						</td>
					</tr>
				</table>
			</div>
			<br>
		</div>
		<br />
		<table align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
				</td>
				<td style="width: 15px;"></td>
				<td>
				<%if (rid == null){ %>
				<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%}else{ %>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%} %>
				</td>
			</tr>
		</table>
	</body>
</html>