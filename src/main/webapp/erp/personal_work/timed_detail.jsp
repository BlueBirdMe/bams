<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>排班明细</title>
<%
String timedID =request.getParameter("timedID");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	if(<%=timedID%>!=null){
		dwrPersonalOfficeService.getTimedRecordByPk(<%=timedID%>,setpagevalue);
	}
}

function setpagevalue(data){
	if(data != null){
	    if(data.resultList.length>0){
	        var timedRecord = data.resultList[0];
	        if(timedRecord.timedType =='<%=EnumUtil.TIMED_TYPE.Vaild.value%>'){
				DWRUtil.setValue("timedType","<%=EnumUtil.TIMED_TYPE.valueOf(EnumUtil.TIMED_TYPE.Vaild.value)%>");
			}else{
	   			 DWRUtil.setValue("timedType","<%=EnumUtil.TIMED_TYPE.valueOf(EnumUtil.TIMED_TYPE.No_Vaild.value)%>");
	 		}
	        DWRUtil.setValue("timedDate",timedRecord.timedDate);
	        DWRUtil.setValue("timedDescription",timedRecord.timedDescription);
	    }
	}
}
</script>
</head>
  <body class="inputdetail">
   <div class="requdivdetail"><label>查看帮助:&nbsp;可以在此查看定时提醒明细。</label></div>
	<div class="detailtitle">定时提醒明细</div>
		<table class="detailtable" align="center">
			<tr>
				<th width="15%">提醒类型</th>
				<td id="timedType" class="detailtabletd"></td>
				<td class="attachtd" rowspan="2"></td>
			</tr>
			<tr>
				<th>提醒时间</th>
				<td  id="timedDate" class="detailtabletd">
				</td>
			</tr>
			<tr>
				<th>提醒内容</th>
				<td colspan="3"  id="timedDescription" class="detailtabletd">
				</td>
			</tr>
		</table>
</body>
</html>