<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String oid = request.getParameter("oid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script> 
 <script type="text/javascript">
     window.onload = function(){
        initInput("helpTitle","短信提醒，您可以使用系统内部短信和手机短信来提醒与会人。");
		dwrOfficeResourcesService.getMeetapplyByPk(<%=oid%>,setPageValue);
	}

function setPageValue(data){
 	if(data != null){
      if(data.resultList.length>0){
          var meetapply = data.resultList[0];
          DWRUtil.setValue("oaMeetapplyName",meetapply.oaMeetapplyName);
          DWRUtil.setValue("oaMeetapplyTheme",meetapply.oaMeetapplyTheme);
          if(meetapply.library != null){
              DWRUtil.setValue("oaMeetapplyType",meetapply.library.libraryInfoName);
          }
          if(meetapply.meetApplyRoomObj != null){
              DWRUtil.setValue("oaMeetapplyRoom",meetapply.meetApplyRoomObj.oaBoardroomName);
          }
          if(meetapply.employee != null){
              DWRUtil.setValue("oaMeetapplyEmp",meetapply.employee.hrmEmployeeName);
          }
          DWRUtil.setValue("oaMeetapplyDate",meetapply.oaMeetapplyDate);
          DWRUtil.setValue("oaMeetapplySummary",meetapply.jiyaoEmpNames);
          DWRUtil.setValue("oaMeetapplyDep",meetapply.zhubanDep);
          DWRUtil.setValue("oaMeetapplyEmpw",meetapply.oaMeetapplyEmpw);
          
          DWRUtil.setValue("oaSmsSendAcpempName", meetapply.chuxiEmpName);
          DWRUtil.setValue("oaSmsSendAcpemp",meetapply.oaMeetapplyEmpn);
          
          DWRUtil.setValue("oaMeetapplyEmpn",meetapply.chuxiEmpName);
          
          var tixi = "";
          if(meetapply.oaMeetapplyAwoke == "false"){
              tixi = "否";
          }else if(meetapply.oaMeetapplyAwoke == "true"){
              tixi = "是";
          }
          DWRUtil.setValue("oaMeetapplyAwoke",tixi);
          var impo = "";
          if(meetapply.oaMeetapplyDegree == <%=EnumUtil.OA_MEET_TYPE.ONE.value%>){
              impo = "<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.ONE.value)%>";
          }else{
              impo = "<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.TWO.value)%>&nbsp;<img src='<%=contextPath%>/images/lve1.gif'>";
          }
          document.getElementById("oaMeetapplyDegree").innerHTML = impo;
          DWRUtil.setValue("oaMeetapplyStar",meetapply.oaMeetapplyStar);
          DWRUtil.setValue("oaMeetapplyEnd",meetapply.oaMeetapplyEnd);
          document.getElementById("oaMeetapplyDescribe").innerHTML = meetapply.oaMeetapplyDescribe;
          Sys.showDownload(meetapply.oaMeetapplyAffix,"files");
      }
  }
}



function reback(){
    Sys.href('<%=contextPath%>/erp/office_resources/my_applymeeting.jsp');
}
   
 var fckvalue ="";
 var fck;
 function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}
   
   function save(){
   //定义信息提示数组
var warnArr = new Array();
warnArr[0] = "oaSmsSendContentMust";
//清空所有信息提示
warnInit(warnArr);

     var bl = validvalue('helpTitle');
     if(bl){
        if(document.getElementById("oaSmsSendAcpemp").value == ""){
		   // DWRUtil.setValue("title","收件人不能为空");
		   setMustWarn("oaSmsSendAcpempMust","收件人不能为空");
			return false;
		}
       if(trim(fck.GetXHTML())== ""  && trim(fck.GetXHTML()).length < 10){
			//DWRUtil.setValue("title","短信内容不能为空,且长度不能小于10个字符！");
			//fck.SetHTML("");
			setMustWarn("oaSmsSendContentMust","短信内容不能为空,且长度不能小于10个字符！");
			fck.SetHTML("");
			return false;
		}
        dwrMoblieSmsService.saveSmsSend(getSmsSendinfo(),saveCallback);
      }
}

function getSmsSendinfo(){
    var smsSend = new Object();
    smsSend.oaSmsSendAcpemp = DWRUtil.getValue("oaSmsSendAcpemp");
    smsSend.oaSmsSendAcpempName = DWRUtil.getValue("oaSmsSendAcpempName");
    smsSend.oaSmsSendContent = fck.GetXHTML();
    return smsSend;
}

function saveCallback(data){
	alertmsg(data,"closePage()");
	
   }


function closePage(){
		closeMDITab();
}
</script>
  </head>
  
   <body class="inputcls">
	<div class="formdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
    <div class="detailtitle">会议明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">会议名称</th>
		<td width="20%" id="oaMeetapplyName" class="detailtabletd"></td>
		<th rowspan="5"></th>
		<td class="attachtd" rowspan="5">
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="files"></div>				
			</div>
		</td>
	</tr>
			<tr>
				<th>会议主题</th>
				<td id="oaMeetapplyTheme" class="detailtabletd"></td></tr>
				<tr>
				<th>会议类型</th>
				<td id="oaMeetapplyType" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议室</th>
				<td id="oaMeetapplyRoom" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>主办部门</th>
				<td id="oaMeetapplyDep" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>申请人</th>
				<td id="oaMeetapplyEmp" class="detailtabletd"></td>
				<th>申请时间</th>
				<td id="oaMeetapplyDate" class="detailtabletd" width="30%"></td>
				</tr>
				
				<tr>
				<th>是否短信提醒</th>
				<td id="oaMeetapplyAwoke" class="detailtabletd"></td>
				
				<th>重要程度</th>
				<td id="oaMeetapplyDegree" class="detailtabletd"></td>
				</tr>
				
				<tr>
				<th>开始时间</th>
				<td id="oaMeetapplyStar" class="detailtabletd"></td>
				<th>结束时间</th>
				<td id="oaMeetapplyEnd" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>出席人员(外部)</th>
				<td id="oaMeetapplyEmpw" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>出席人员(内部)</th>
				<td id="oaMeetapplyEmpn" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议纪要人</th>
				<td id="oaMeetapplySummary" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议描述</th>
				<td colspan="3"  id="oaMeetapplyDescribe" class="detailtabletd">
				</td>
				</tr>
	</table>
</div>
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">短信提醒</div>
		<div>
			<table class="detailtable" border="0" align="center">
			<tr>
				<th width="5%"><em>* </em>收件人</th>
				<td>
				<div id="oaSmsSendAcpempName" style="width: 90%" ></div>
				<input type="hidden" id="oaSmsSendAcpemp" value="">
				<label id="oaSmsSendAcpempMust"></label>
				</td>
			</tr>			
			<tr>
			  <th width="15%">发送方式</th>
			  <td>
			    <input type="checkbox" id="moblie">手机短信
			  </td>
			</tr>
			<tr>
				<th><em>* </em>短信内容</th>
				<td>
				<label id="oaSmsSendContentMust"></label>
				<FCK:editor instanceName="oaSmsSendContent" width="90%" toolbarSet="Basic" height="100px"></FCK:editor>
				</td>
			</tr>
			</table>
	</div>
<br/>
</div>
<br/>
<center>
<table>
<tr>
<td><btn:btn onclick="save()" value="发 送 " imgsrc="../../images/fileokico.png" title="发送信息"></btn:btn></td>
<td style="width: 10px;"></td>
<td ><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
</tr>
</table>
</center>
</body>
  
</html>
