<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0);
	String meetapplypk =request.getParameter("meetapplypk");
	String isedit = "false";
	if(meetapplypk!=null){//编辑时使用
		isedit = "true";
	}
%>
<title>会议召开</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","会议申请，在开会之前, 您可以在此处预定会议室。");
	saveOrEdit();	
	}
	
function save(){ 
	var warnArr = new Array();
	warnArr[0]="commonMust";
	warnArr[1]="oaMeetapplyDescribemust";
	warnArr[2] = "oaMeetapplyNameMust";
	warnArr[3] = "oaMeetapplyThemeMust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');//验证id=aaac的table下的带有must属性的项
	if(bl){
	
		if(DWRUtil.getValue("oaMeetapplySummary_s") == ""){
         	setMustWarn("commonMust","会议纪要人不能为空！");
    		return;
    	}
    	if(DWRUtil.getValue("oaMeetapplyDep_s") == ""){
         	setMustWarn("commonMust","主办部门不能为空！");
    		return;
    	}
    	if(DWRUtil.getValue("oaMeetapplyEmpn_s") == ""){
         	setMustWarn("commonMust","内部出席人员不能为空！");
         	return;
   	 	}
   	 	if(document.getElementById("oaMeetapplyDescribe").value.length>1000){
   	 	     setMustWarn("oaMeetapplyDescribemust","会议描述字数，不能大于1000！");
   	 	     return false;
   	 	}
   	 	if(<%=meetapplypk%> != null){ 
   	 		Btn.close();
			dwrOfficeResourcesService.updateMeetapply(getMeetapplyinfo(),saveCallback);
		}else{ 
			Btn.close();
	 		dwrOfficeResourcesService.saveMeetapply(getMeetapplyinfo(),saveCallback);
		}
	}
}

function clearContent(){
	DWRUtil.setValue("oaMeetapplyName","");
    DWRUtil.setValue("oaMeetapplyTheme","");
    DWRUtil.setValue("oaMeetapplyDate","");
    DWRUtil.setValue("oaMeetapplySummary","");
    DWRUtil.setValue("oaMeet","");
    DWRUtil.setValue("oaMeetapplyDep","");
    DWRUtil.setValue("oaMeetapplyStar","");
    DWRUtil.setValue("oaMeetapplyEnd","");
    DWRUtil.setValue("oaMeetapplyEmpn","");
    DWRUtil.setValue("oaMeetapplyEmpw","");
    DWRUtil.setValue("oaMeetapplyDescribe","");
    DWRUtil.setValue("oaMeetapplyAffix","");
    DWRUtil.setValue("oaMeetapplyAwoke","");
    DWRUtil.setValue("oaMeetapplySummary_s","");
    DWRUtil.setValue("oaMeetapplyDep_s","");
    DWRUtil.setValue("oaMeetapplyEmpn_s","");

    DWRUtil.setValue("oaMeetapplyAffix","");
    Sys.setFilevalue("oaMeetapplyAffix","");
    fck.SetHTML("");
}

function saveCallback(data){
    Btn.open();
    if(<%=meetapplypk%> != null){
		alertmsg(data,"reSet();");
	} else {
		if(data.success){
			confirmmsgAndTitle("申请会议成功！是否想继续添加？","reSet();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}	

//返回列表
function backToList(){
	window.location = "mymeeting.jsp";
}
  

function reSet(){
    if(<%=meetapplypk%> != null){
        window.parent.MoveDiv.close();
    	window.parent.queryData();
    }else{
        clearContent();
    }
}


function getMeetapplyinfo(){
	    var meetapply = new Object();
	    	if(<%=meetapplypk%> != null){
	    	meetapply.primaryKey = <%=meetapplypk%>;
	    }
	    meetapply.oaMeetapplyName = DWRUtil.getValue("oaMeetapplyName");
	    meetapply.oaMeetapplyTheme = DWRUtil.getValue("oaMeetapplyTheme");
	    meetapply.oaMeetapplyType = DWRUtil.getValue("oaMeetapplyType");
	    meetapply.oaMeetapplyDate = DWRUtil.getValue("oaMeetapplyDate");
	    
	    meetapply.oaMeetapplySummary = DWRUtil.getValue("oaMeetapplySummary_s");
	    meetapply.oaMeetapplyRoom = DWRUtil.getValue("oaMeetId");
	    meetapply.oaMeetapplyDep = DWRUtil.getValue("oaMeetapplyDep_s");
	    meetapply.oaMeetapplyStar = DWRUtil.getValue("oaMeetapplyStar");
	    meetapply.oaMeetapplyEnd = DWRUtil.getValue("oaMeetapplyEnd");
	    meetapply.oaMeetapplyEmpn = DWRUtil.getValue("oaMeetapplyEmpn_s");
	    meetapply.oaMeetapplyEmpw = DWRUtil.getValue("oaMeetapplyEmpw");
	    meetapply.oaMeetapplyDescribe = DWRUtil.getValue("oaMeetapplyDescribe");
	    meetapply.oaMeetapplyAffix = DWRUtil.getValue("oaMeetapplyAffix");
	    meetapply.oaMeetapplyAwoke = DWRUtil.getValue("oaMeetapplyAwoke");
	    meetapply.oaMeetapplyDegree = DWRUtil.getValue("oaMeetapplyDegree");
	    
	    return meetapply;
	    
    }

function saveOrEdit(){
      if(<%=meetapplypk%> != null){
		dwrOfficeResourcesService.getMeetapplyByPk(<%=meetapplypk%>,setMeetapplyinfo);
	 }
}

function setMeetapplyinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var meetapply = data.resultList[0];
 			
			DWRUtil.setValue("oaMeetapplyName",meetapply.oaMeetapplyName);
			DWRUtil.setValue("oaMeetapplyTheme",meetapply.oaMeetapplyTheme);
			DWRUtil.setValue("oaMeetapplyType",meetapply.oaMeetapplyType);
			DWRUtil.setValue("oaMeetapplyDate",meetapply.oaMeetapplyDate);
			

			
			DWRUtil.setValue("oaMeetapplySummary",meetapply.jiyaoEmpNames);
			DWRUtil.setValue("oaMeetapplySummary_s",meetapply.oaMeetapplySummary);
			
			if(meetapply.meetApplyRoomObj != null){
				DWRUtil.setValue("oaMeet",meetapply.meetApplyRoomObj.oaBoardroomName);
			}
			DWRUtil.setValue("oaMeetId",meetapply.oaMeetapplyRoom);
			
			DWRUtil.setValue("oaMeetapplyDep",meetapply.zhubanDep);
			DWRUtil.setValue("oaMeetapplyDep_s",meetapply.oaMeetapplyDep);
			
			DWRUtil.setValue("oaMeetapplyStar",meetapply.oaMeetapplyStar);
			DWRUtil.setValue("oaMeetapplyEnd",meetapply.oaMeetapplyEnd);
			
			DWRUtil.setValue("oaMeetapplyEmpn",meetapply.chuxiEmpName);
			DWRUtil.setValue("oaMeetapplyEmpn_s",meetapply.oaMeetapplyEmpn)
			
			
			DWRUtil.setValue("oaMeetapplyEmpw",meetapply.oaMeetapplyEmpw);
	    	if(meetapply.oaMeetapplyAffix != null &&meetapply.oaMeetapplyAffix != undefined && meetapply.oaMeetapplyAffix.length > 0 ){
	     		dwrCommonService.getAttachmentInfoListToString(meetapply.oaMeetapplyAffix,setaccept);
	    	}
	     	DWRUtil.setValue("oaMeetapplyDescribe",meetapply.oaMeetapplyDescribe);
	    	//DWRUtil.setValue("oaMeetapplyAwoke",meetapply.oaMeetapplyAwoke);
	    	DWRUtil.setValue("oaMeetapplyDegree",meetapply.oaMeetapplyDegree);
	    	
	    	setRadioValueByName("oaMeetapplyDegree",meetapply.oaMeetapplyDegree);

 		}
 	}
 }
 
//放入附件
function setaccept(data){
	Sys.setFilevalue("oaMeetapplyAffix",data);
}
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function selectDep(){
	if(<%=meetapplypk%> != null){
		var box = SEL.getDeptIds("check","oaMeetapplyDep","oaMeetapplyDep_s","approvefrm@@processloadfrm");
		box.show();
	}else{
		var box = SEL.getDeptIds("check","oaMeetapplyDep","oaMeetapplyDep_s");
		box.show();
	}
}
   
function selectSummary(){
		if(<%=meetapplypk%> != null){
			var box = SEL.getEmployeeIds("check","oaMeetapplySummary","oaMeetapplySummary_s","approvefrm@@processloadfrm");
			box.show();
		}else{
			var box = SEL.getEmployeeIds("check","oaMeetapplySummary","oaMeetapplySummary_s");
			box.show();
		}
}
   
function selectEmpn(){
		if(<%=meetapplypk%> != null){
			var box = SEL.getEmployeeIds("check","oaMeetapplyEmpn","oaMeetapplyEmpn_s","approvefrm@@processloadfrm");
			box.show();
		}else{
			var box = SEL.getEmployeeIds("check","oaMeetapplyEmpn","oaMeetapplyEmpn_s");
			box.show();
		}
}
   

   function selectboadRoom(){
   		if(<%=meetapplypk%> != null){
			var box = SEL.getBoardIds("radio","oaMeet","oaMeetId","approvefrm@@processloadfrm");
			box.show();
		}else{
			var box = SEL.getBoardIds("radio","oaMeet","oaMeetId");
			box.show();
		}
   } 
   
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>,"approvefrm");
}  
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">会议申请</div>
		<div>
			<table class="inputtable" border="0">
			<tr>
				<th><em>* </em>会议名称</th>
				<td><input type="text" id="oaMeetapplyName" must="请输入会议名称。" style="width:400px;" formust="oaMeetapplyNameMust" maxlength="30">
				<label id="oaMeetapplyNameMust"></label>
				</td>
			</tr>
			<tr>
				<th><em>* </em>会议主题</th>
				<td><input type="text" id="oaMeetapplyTheme" must="请输入会议主题。"  style="width:400px;" formust="oaMeetapplyThemeMust" maxlength="30">
				<label id="oaMeetapplyThemeMust"></label>
				</td>
			</tr>
			<tr>
				<th width="15%"><em>* </em>使用会议室</th>
				<td>
				<input type="text" id="oaMeet" readonly="readonly" class="takeform"  must="请选择会议室和开会时间。" formust="oaMeetMust" linkclear="oaMeetId" onclick="selectboadRoom()">
				<input type="hidden" id="oaMeetId">
				<label id="oaMeetMust"></label>
				</td>
			</tr>
			<tr>
				<th>开会时间</th>
				<td style="text-align: left">
				<input type="text" id="oaMeetapplyStar" readonly="readonly" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" disabled="disabled">
				至
				<input type="text" id="oaMeetapplyEnd" readonly="readonly" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" disabled="disabled">
				</td>
			</tr>
			<tr>
				<th>会议类型</th>
				<td> 
				<select must="请选择会议类型" id="oaMeetapplyType" >
				<%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"12") %>
				</select>
				</td>
			</tr>
			<tr>
				<th>重要程度</th>
				<td>
				<input type="radio" name="oaMeetapplyDegree" value="<%=EnumUtil.OA_MEET_TYPE.ONE.value %>" checked="checked" id="oaMeetapplyDegree1" /><label  for="oaMeetapplyDegree1">一般</label>
		 	   <input type="radio" name="oaMeetapplyDegree" value="<%=EnumUtil.OA_MEET_TYPE.TWO.value %>" id="oaMeetapplyDegree2" /><label for="oaMeetapplyDegree2">重要</label>
				</td>
			</tr>
			<tr>
				<th></th>
				<td><label id="commonMust"></label></td>
			</tr>
			<tr>
				<th>人&nbsp;&nbsp;员</th>
				<td valign="top">
				<DIV class="tabdiv" style="width: 90%;float:left;" id="tabdiv1" style="overflow: hidden;">
					<UL class="tags">
					<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">会议纪要人</A></LI>
					<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">主办部门</A></LI>
					<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">出席人员</A></LI>
					</UL>
				
					<DIV class="tagContentdiv">
						<DIV class="tagContent" id="tag0" style="overflow: hidden;">
						<table style="width: 98%" border="0">
						<tr>
							<th style="width: 10%"><em>* </em>选择人员</th>
							<td>
							<textarea id="oaMeetapplySummary" readonly="readonly" onclick="selectSummary()" linkclear="oaMeetapplySummary_s"></textarea>
							<input type="hidden" id="oaMeetapplySummary_s">	
							</td>
						</tr>
						</table>		
						</DIV>
						<DIV class="tagContent" id="tag1" style="overflow: hidden;">
							<table style="width: 98%" border="0">
							<tr>
							<th style="width: 10%"><em>* </em>选择部门</th>
							<td>
							<textarea readonly="readonly" id="oaMeetapplyDep" linkclear="oaMeetapplyDep_s" onclick="selectDep()"></textarea>
							<input type="hidden" id="oaMeetapplyDep_s">
							</td>
						</tr>
						</table>
						</DIV>
						<DIV class="tagContent" id="tag1" style="overflow: hidden;">
						<table style="width: 98%" border="0">
						<tr>
							<th style="width: 10%">外部</th>
							<td>
							<textarea id="oaMeetapplyEmpw"></textarea>
							</td>
						</tr>
						<tr>
							<th style="width: 10%"><em>* </em>内部</th>
							<td>
							<textarea id="oaMeetapplyEmpn" readonly="readonly" onclick="selectEmpn()" linkclear="oaMeetapplyEmpn_s"></textarea>
							<input type="hidden" id="oaMeetapplyEmpn_s">
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
							<input type="checkbox"  id="oaMeetapplyAwoke"><label  for="oaMeetapplyAwoke">短信通知出席人员</label>
							</td>
						</tr>
						</table>
						</div>
					</DIV>
				</DIV>
			</td>
			</tr>
			<tr>
			<th>会议附件</th>
				<td style="text-align: left">
				<file:multifileupload width="90%" acceptTextId="oaMeetapplyAffix" height="100px" edit="<%=isedit %>"></file:multifileupload>
				</td>
			</tr>
			<tr>
				<th></th>
				<td><label id="oaMeetapplyDescribemust"></label></td>
			</tr>
			<tr>
				<th>会议描述</th>
				<td style="text-align: left">
				<textarea id="oaMeetapplyDescribe" style="height: 200px"></textarea>
				</td>
			</tr>
			</table>
			</br>
		</div>
</div>
<br/>
<table align="center">
   <tr>
     <td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
     <td style="width: 10px;"></td>
     <td>
	<%if (meetapplypk == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
   </tr>
</table>
<script type="text/javascript">
	var tab =new SysTab('<%=contextPath%>',null,"tabdiv1");
</script>
</body>
</html>