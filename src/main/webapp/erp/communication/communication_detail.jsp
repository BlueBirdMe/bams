<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录明细查看</title>
<%
    String chatId = request.getParameter("chatId");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrOaCommunicationService.getCommunicationById(<%=Integer.parseInt(chatId)%>,setPageValue);
}
function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var communication = data.resultList[0];
          DWRUtil.setValue("oaChatEmp",communication.oaChatEmp);
          
          var sex = "<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%>";
          if(communication.oaChatSex == 2){
               sex = "<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%>";
          }
          DWRUtil.setValue("oaChatSex",sex);
          DWRUtil.setValue("oaChatCom",communication.oaChatCom);
          
          var group = "<无>";
          if(communication.oaChatGroups != null){
               group = communication.oaChatGroups.oaChatgpName;
          }
          DWRUtil.setValue("oaChatGroup",group);
          
          DWRUtil.setValue("oaChatAddress",communication.oaChatAddress);
          DWRUtil.setValue("oaHomeTel",communication.oaHomeTel);
          DWRUtil.setValue("oaChatMobile",communication.oaChatMobile);
          DWRUtil.setValue("oaWorkTel",communication.oaWorkTel);
          DWRUtil.setValue("oaChatQq",communication.oaChatQq);
          DWRUtil.setValue("oaChatMsn",communication.oaChatMsn);
          DWRUtil.setValue("oaChatEmail",communication.oaChatEmail);
          DWRUtil.setValue("shareEmpName",communication.shareEmpName);
          var face = document.getElementById("oaChatPhotos");
			face.src+="&imgId="+communication.oaChatPhotos;
      }
  }
}
</script>
</head>  
  <body class="inputdetail">
    <div class="requdivdetail"><label>查看帮助:&nbsp;在这里查看联系人详细信息。</label></div>
		<div class="detailtitle">通讯录明细</div>
		<table class="detailtable" align="center">
			<tr>
				<th width="15%">姓&nbsp;名</th>
				<td id="oaChatEmp" class="detailtabletd"></td>
				
				<th rowspan="4" style="text-align: right;padding-bottom: 50px;">照 片</th>
				<td rowspan="4" ><file:imgshow  id="oaChatPhotos" width="128"></file:imgshow></td>
			</tr>
			<tr>
				<th>所属分组</th>
				<td id="oaChatGroup" class="detailtabletd"></td>
				
			</tr>
			<tr>
				<th>性&nbsp;别</th>
				<td id="oaChatSex" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>单&nbsp;位</th>
				<td id="oaChatCom" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>家庭电话</th>
				<td id="oaHomeTel" class="detailtabletd"></td>
				<th>EMAIL</th>
				<td id="oaChatEmail" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>移动电话</th>
				<td id="oaChatMobile" class="detailtabletd"></td>
				<th>工作电话</th>
				<td id="oaWorkTel" class="detailtabletd" style="width: 34%"></td>
			</tr>
			<tr>
				<th>Q&nbsp;Q</th>
				<td id="oaChatQq" class="detailtabletd"></td>
			
				<th>MSN</th>
				<td id="oaChatMsn" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>家庭地址</th>
				<td id="oaChatAddress" class="detailtabletd" colspan="3"></td>
			</tr>
			<tr>
			<th>共享人员</th>
				<td id="shareEmpName" class="detailtabletd" colspan="3"></td>
			</tr>
		</table>
  </body>
</html>
