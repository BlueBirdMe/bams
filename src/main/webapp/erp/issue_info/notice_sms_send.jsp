<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信提醒人员</title>
<%
    String oid = request.getParameter("oid");
    String noid = request.getParameter("noid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script> 
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput('helpTitle','您可以在此处发送通知短信提醒！提醒您想要提醒的人员！');

	dwrOaNewsService.getNoticeByPk(<%=Integer.parseInt(oid)%>,setPageValue);
}

function reSet(){
    document.getElementById("oaSmsSendAcpempName").value = "";
    document.getElementById("oaSmsSendAcpemp").value = "";
    fck.SetHTML("");
}
    
function setPageValue(data){
   if(data != null){
      if(data.resultList.length>0){
         var notice = data.resultList[0];
          DWRUtil.setValue("noticeName",notice.oaNotiName);
          
         var type = "";
         if(notice.oaNotiType ==<%=EnumUtil.OA_NOTICE_TYPE.GENERAL.value%>){
             type = "<%=EnumUtil.OA_NOTICE_TYPE.valueOf(EnumUtil.OA_NOTICE_TYPE.GENERAL.value)%>";
         }else if(notice.oaNotiType ==<%=EnumUtil.OA_NOTICE_TYPE.UNEMERGENCY.value%>){
             type = "<%=EnumUtil.OA_NOTICE_TYPE.valueOf(EnumUtil.OA_NOTICE_TYPE.UNEMERGENCY.value)%>";
         }else{
              type = "<%=EnumUtil.OA_NOTICE_TYPE.valueOf(EnumUtil.OA_NOTICE_TYPE.EMERGENCY.value)%>&nbsp;<img src='<%=contextPath%>/images/lve1.gif'>";
         }
         document.getElementById("noticeType").innerHTML = type;
         
         DWRUtil.setValue("noticeTime",notice.oaNotiTime);
         DWRUtil.setValue("oaSmsSendAcpempName",notice.empLIst);
         DWRUtil.setValue("oaSmsSendAcpemp",notice.oaObjEmp);
         DWRUtil.setValue("noticeObjEmp",notice.empLIst);
         DWRUtil.setValue("noticeObjDep",notice.depList);
         
         var noticeStatus = "<font color='green'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>";
         if(notice.oaNotiStatus == <%=EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value%>){
               noticeStatus = "<font color='red'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>";
         }
         document.getElementById("noticeStatus").innerHTML = noticeStatus;
          
         if(notice.employee!=null){
            document.getElementById("noticeEmpName").innerHTML = notice.employee.hrmEmployeeName;
         }else{
          	document.getElementById("noticeEmpName").innerHTML ="&nbsp;";
         }
         document.getElementById("noticeText").innerHTML = notice.oaNotiText;
         
         //附件显示为下载
		 Sys.showDownload(notice.oaNotiAcce,"noticeAcce");
      }
   }
}
	
function reback(){
   if(<%=noid%> == "1"){
        Sys.load('<%=contextPath%>/erp/issue_info/notice_manager.jsp');
   }else{
        Sys.load('<%=contextPath%>/erp/issue_info/notice_info.jsp');
   }
}
	
function getemployee(){
	var box = SEL.getEmployeeIds("check","oaSmsSendAcpempName","oaSmsSendAcpemp");
	box.show();
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
	 warnArr[0] = "oaSmsSendAcpempNameMust";
	 warnArr[1] = "oaSmsSendContentMust";
	 //清空所有信息提示
	 warnInit(warnArr);
     
     var bl = validvalue('helpTitle');
     if(bl){
         if(document.getElementById("oaSmsSendAcpemp").value == ""){
		     setMustWarn("oaSmsSendAcpempNameMust","收件人不能为空!");
			 return false;
		 }
         if(trim(fck.GetXHTML())== ""  && trim(fck.GetXHTML()).length < 10){
		    setMustWarn("oaSmsSendContentMust","短信内容不能为空,且长度不能小于10个字符！");
               fck.Focus();
			fck.SetHTML("");
			return false;
		 }
		 Btn.close();
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
    Btn.open();
	confirmmsgAndTitle("短信提醒成功！继续短信提醒？","reSet();","继续提醒","closePage();","关闭页面");
}

function closePage(){
	closeMDITab();
}

</script>
</head>
<body class="inputcls">
	<div class="formdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
    <div class="detailtitle">通知明细</div>
	<table class="detailtable" align="center">
		<tr>
			<th width="15%">通知标题</th>
			<td id="noticeName" class="detailtabletd"></td>
			<td class="attachtd" rowspan="5">
				<div class="attachdiv">
					<div class="attachtitle">附件下载</div>
					<div class="attachdownload" id="noticeAcce"></div>				
				</div>
			</td>
		</tr>
		<tr>
			<th>发布人</th>
			<td id="noticeEmpName" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>发布日期</th>
			<td id="noticeTime" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>通知类型</th>
			<td id="noticeType" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>通知状态</th>
			<td id="noticeStatus" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>通知范围(人员)</th> 
			<td colspan="2" id="noticeObjEmp" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>通知范围(部门)</th>
			<td colspan="2" id="noticeObjDep" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>通知内容</th>
			<td colspan="2"  id="noticeText" class="detailtabletd"></td>
		</tr>
	</table>
    </div>
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">短信提醒</div>
	<div>
	<table class="inputtable">
	<tr>
	    <th></th>
	    <td><label id = "oaSmsSendAcpempNameMust"></label></td>
	</tr>
	<tr>
		<th width="15%"><em>*</em>&nbsp;收件人</th>
		<td>
		<textarea  id="oaSmsSendAcpempName" linkclear="oaSmsSendAcpemp" title="点击选择人员" readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
		<input type="hidden" id="oaSmsSendAcpemp" value="">
		</td>
	</tr>			
	<tr>
	  <th width="15%">发送方式</th>
	  <td>
	    <input type="checkbox" id="moblie">手机短信
	  </td>
	</tr>
	<tr>
	    <th></th>
	    <td><label id = "oaSmsSendContentMust"></label></td>
	</tr>
	<tr>
		<th><em>*</em>&nbsp;短信内容</th>
		<td>
		<FCK:editor instanceName="oaSmsSendContent" width="90%" toolbarSet="Basic" height="200px"></FCK:editor>
		</td>
	</tr>
	</table>
	</div>
	</div>
	<table align="center">
		<tr>
			<td><btn:btn onclick="save();" value="发 送 " imgsrc="../../images/png-1718.png" title="发送短信" /></td>
			<td style="width: 20px;"></td>
			<td ><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
		</tr>
	</table>
</body>
</html>
