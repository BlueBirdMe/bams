<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
		<title>新增日志</title>
		<style type="text/css">
			body {
				background-color: #EDF5FA;
			}
		</style>
		<%
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader("Expires",0);
			String worklogpk = request.getParameter("worklogpk");
			String date = request.getParameter("date");
			String isedit = "false";
			if (worklogpk != null) {//编辑时使用
				isedit = "true";
			}
		%>
<script type="text/javascript">
window.onload = function(){
	initInput("helpTitle","工作日志新增，您可以在此处添加自己的日志，可设置共享，共享状态下他人可见！");
	useLoadingMassage();
	saveOredit();
	document.getElementById("oaWorklogTitle").focus();
    show1();			
}
	
function  saveOredit(){
	if(<%=worklogpk%> != null){
		var primaryKey = <%=worklogpk%>;
		dwrWorkArrangeService.getWorklogByPk(primaryKey,setWorkloginfo);
	}
	if(<%=date%> != null){
		DWRUtil.setValue("oaWorklogDate",'<%=date%>');
	}
}	

var fckvalue="";
var fck;
 
function setWorkloginfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var worklog = data.resultList[0];
 			DWRUtil.setValue("oaWorklogTitle",worklog.oaWorklogTitle);
 			DWRUtil.setValue("oaWorklogType",worklog.oaWorklogType);
 			DWRUtil.setValue("oaWorklogDate",worklog.oaWorklogDate);
 			DWRUtil.setValue("oaWorklogDepIds",worklog.oaWorklogDeps);
 			DWRUtil.setValue("oaWorklogEmpIds",worklog.oaWorklogEmps);
 			DWRUtil.setValue("oaWorklogDeps",worklog.oaWorklogDepsName);
 			DWRUtil.setValue("oaWorklogEmps",worklog.oaWorklogEmpsName);
 			setRadioValueByName("oaWorklogRange",worklog.oaWorklogRange);
 			if(worklog.oaWorklogRange == 2){
 			  show2();
 			}
 			//fckvalue临时
			fckvalue = worklog.oaWorklogContent;
			if(fck!=null){
				fck.SetHTML(fckvalue);
			}
			//附件
				if(worklog.oaWorklogAnnexid!=null && worklog.oaWorklogAnnexid!=undefined && worklog.oaWorklogAnnexid!= "undefined" && worklog.oaWorklogAnnexid.length>0){
					dwrCommonService.getAttachmentInfoListToString(worklog.oaWorklogAnnexid,setaccept);
				}		
 			
 		}else{
 			alert(data.message);
 		}
	}else{
 		alert(data.message);
	}
}

//放入附件
	function setaccept(data){
		Sys.setFilevalue("oaWorklogAnnexid",data);
	}
	
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function getWorkloginfo(){
	var worklog = new Object();
	if(<%=worklogpk%> != null){
		worklog.primaryKey = <%=worklogpk%>;
	}  
	worklog.oaWorklogTitle = DWRUtil.getValue("oaWorklogTitle");
	worklog.oaWorklogType = DWRUtil.getValue("oaWorklogType");
	worklog.oaWorklogDate =  DWRUtil.getValue("oaWorklogDate");
	worklog.oaWorklogRange = getRadioValueByName("oaWorklogRange");
	if(worklog.oaWorklogRange == 2){
 		worklog.oaWorklogDeps = DWRUtil.getValue("oaWorklogDepIds");
	    worklog.oaWorklogEmps = DWRUtil.getValue("oaWorklogEmpIds");	 
 	}
	worklog.oaWorklogContent = fck.GetXHTML();
	return worklog;
}
function save(){
	var validResult = validvalue('helpTitle');
	var warnArr = new Array();
	warnArr[0] ="setEmpsMust";
	warnArr[1] ="setDepsMust";
	warnArr[2] ="oaWorklogContentMust";
	warnInit(warnArr);
	if(validResult){  
	     //判断共享范围
	     if(getRadioValueByName("oaWorklogRange") == 2){ //共享
	        var depids = DWRUtil.getValue("oaWorklogDepIds");
	        var empids = DWRUtil.getValue("oaWorklogEmpIds");
	        if(depids == "" && empids == ""){
				var chooseArr = new Array();
				chooseArr[0] = "setDepsMust";
				chooseArr[1] = "setEmpsMust";
				chooseWarn(chooseArr);
				window.scrollTo(0,0);
	        }else if(fck.GetXHTML() == "" || fck.GetXHTML() == null || fck.GetXHTML().length == 0){
		        setMustWarn("oaWorklogContentMust","日志内容不能为空。");
		        fck.Focus();
		        window.scrollTo(0,0);
			}else{
				if(<%=worklogpk%> != null){
					UpdateWorklog();
				}else{
					submitWorklog();
				}
			}
	     }else if(fck.GetXHTML() == "" || fck.GetXHTML() == null || fck.GetXHTML().length == 0){
	        setMustWarn("oaWorklogContentMust","日志内容不能为空。");
	        fck.Focus();
	        window.scrollTo(0,0);
			}else{
			   if(<%=worklogpk%> != null){
			     UpdateWorklog();
			   }else{
			   	 submitWorklog();
			   }
	    	}
	}
}
	
function UpdateWorklog(){
	var oaWorklogAnnexid =  DWRUtil.getValue("oaWorklogAnnexid"); //附件
	Btn.close();
	dwrWorkArrangeService.saveWorkLog(getWorkloginfo(),oaWorklogAnnexid,updataCallback);
}

function submitWorklog(){
	var oaWorklogAnnexid =  DWRUtil.getValue("oaWorklogAnnexid"); //附件
	Btn.close();
	dwrWorkArrangeService.saveWorkLog(getWorkloginfo(),oaWorklogAnnexid,saveCallback);
}
	
function updataCallback(data){
	document.getElementById("oaWorklogTitle").focus();
	data.message = "修改工作日志成功！";
	alertmsg(data,"reSet()");
}
	
function saveCallback(data){
	Btn.open();
	if(data.success){
		document.getElementById("oaWorklogTitle").focus();
		confirmmsgAndTitle("添加日志成功！是否想继续添加请？","reSet();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}
	
function reSet(){
	if(<%=worklogpk%> != null){
		reloadpager();
	}else{
		repload();
	}   
}

function reloadpager(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function repload(){
	DWRUtil.setValue("oaWorklogTitle","");
	setSelectValue("oaWorklogType",1);
	//DWRUtil.setValue("oaWorklogDate","");
	DWRUtil.setValue("oaWorklogDepIds","");
	DWRUtil.setValue("oaWorklogEmpIds","");
	DWRUtil.setValue("oaWorklogDeps","");
	DWRUtil.setValue("oaWorklogEmps","");
	setRadioValueByName("oaWorklogRange",1);
	DWRUtil.setValue("oaWorklogContentMust","");
	DWRUtil.setValue("setEmpsMust","");
	show1();
	//设置fck
	fck.SetHTML("");
	//刷新附件
	Sys.setFilevalue("oaWorklogAnnexid","");
	window.scrollTo(0,0);
	document.getElementById("oaWorklogTitle").focus();
	
	refreshMDITab(<%=request.getParameter("tab")%>,"workLogfrm");
}

function getupcode(){
	if(<%=worklogpk%> != null){
		var box = SEL.getDeptIds("check","oaWorklogDeps","oaWorklogDepIds","workLogfrm@@processloadfrm");
		box.show();
	}else{
		var box = SEL.getDeptIds("check","oaWorklogDeps","oaWorklogDepIds");
		box.show();
	}
}

function getemployee(){
	if(<%=worklogpk%> != null){
		var box = SEL.getEmployeeIds("check","oaWorklogEmps","oaWorklogEmpIds","workLogfrm@@processloadfrm");
		box.show();
	}else{
		var box = SEL.getEmployeeIds("check","oaWorklogEmps","oaWorklogEmpIds");
		box.show();
	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"workLogfrm");
}

function show1(){
	DWRUtil.setValue("oaWorklogDepIds","");
	DWRUtil.setValue("oaWorklogEmpIds","");
	DWRUtil.setValue("oaWorklogDeps","");
	DWRUtil.setValue("oaWorklogEmps","");
	$("#logrange").animate({height: 'hide',   opacity: 'hide'  }, 'slow');
}

function show2(){
	$("#logrange").animate({height: 'show',opacity: 'show'  }, 'slow');
}

</script>
	</head>
	<body class="inputcls">
		<div class="formDetail">
			<div class="requdiv">
				<label id="helpTitle"></label>
			</div>
			<div class="formTitle">
				日志内容
			</div>
			<table class="inputtable" border="0">
				<tr>
					<th width="20%">
						<em> * </em>日志标题
					</th>
					<td width="40%" >
						<input type="text" id="oaWorklogTitle" must="日志标题不能为空!"
							formust="oaWorklogTitleMust" maxlength="100" style="width:90%;" maxlength="50">
					</td>
					<td width="40%">
						<label id="oaWorklogTitleMust"></label>
					</td>
				</tr>

				<tr>
					<th width="20%">
						日志类型
					</th>
					<td width="40%" style="text-align: left;">
						<select id="oaWorklogType">
							<%=UtilTool.getSelectOptions(this.getServletContext(), request, null, "04")%>
						</select>
					</td>
					<td width="40%"></td>
				</tr>

				<tr>
					<th width="20%">
						日志日期
					</th>
					<td width="40%">
						<input type="text" readonly="readonly" id="oaWorklogDate"
							class="Wdate" onClick="WdatePicker({isShowClear:false});"
							value="<%=UtilWork.getToday()%>">
					</td>
					<td width="40%"></td>
				</tr>

				<tr>
					<th width="20%">
						是否共享
					</th>
					<td width="40%" style="text-align: left;">
						<input type="radio" name="oaWorklogRange" value="1"
							checked="checked" onclick="show1()" id="oaWorklogRange1">
						<label for="oaWorklogRange1">
							私有
						</label>
						<input type="radio" name="oaWorklogRange" value="2"
							onclick="show2()" id="oaWorklogRange2">
						<label for="oaWorklogRange2">
							共享
						</label>
					</td>
					<td width="40%"></td>
				</tr>
				<tr>
					<td colspan="3" width="100%" style="padding: 0; margin: 0">
						<div id="logrange">
							<table style="width: 100%">
								<tr>
									<th width="20%">
										<span style="color:blue">•</span>&nbsp;<font color="black">阅读范围<br />(部门)</font>
									</th>
									<td width="40%">
										<textarea id="oaWorklogDeps" readonly="readonly"
											linkclear="oaWorklogDepIds" onclick="getupcode();"
											style="color: #999;" title="点击选择部门"></textarea>
										<input type="hidden" id="oaWorklogDepIds" value="">
									</td>
									<td width="40%">
										<label id="setDepsMust"></label>
									</td>
								</tr>
								<tr>
									<th></th>
									<td colspan="3">
										<label style="padding-left: 10px; color: #808080">
											所选部门的所有人员都能看到该日志，并可以添加评论
										</label>
									</td>
								</tr>
								<tr>
									<th width="20%">
										<span style="color:blue">•</span>&nbsp;<font color="black">阅读范围<br />(人员)</font>
									</th>
									<td width="40%">
										<textarea id="oaWorklogEmps" linkclear="oaWorklogEmpIds"
											title="双击获取编码" readonly="readonly"
											onclick="getemployee();" style="color: #999;"></textarea>
										<input type="hidden" id="oaWorklogEmpIds" value="">
									</td>
									<td width="40%">
										<label id="setEmpsMust"></label>
									</td>
								</tr>
								<tr>
									<th></th>
									<td colspan="2">
										<label style="padding-left: 10px; color: #808080">
											所选的人员能看到该日志，并可以添加评论
										</label>
									</td>
								</tr>

							</table>
						</div>
					</td>
				</tr>
				<tr>
					<th>
						附件
					</th>
					<td style="text-align: left" colspan="2">
						<file:multifileupload width="90%" acceptTextId="oaWorklogAnnexid"
							height="100" saveType="file" edit="<%=isedit%>" type="office"></file:multifileupload>
					</td>
				</tr>
				<tr>
					<th width="20%"></th>
					<td width="40%">
						<label id="oaWorklogContentMust"></label>
					</td>
					<td width="40%"></td>
				</tr>
				<tr>
					<th>
						<em> * </em>日志内容
					</th>
					<td style="text-align: left" colspan="2">
						<FCK:editor instanceName="oaWorklogContent" width="90%"
							height="240px"></FCK:editor>
					</td>

				</tr>

			</table>
		</div>
		<table align="center">
			<tr>
				<td>
					<btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn>
				</td>
				<td style="width: 20px;"></td>
				<td>
				<%if (worklogpk == null){ %>
				<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%}else{ %>
				<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				<%} %>
				</td>
			</tr>
		</table>
	</body>
</html>