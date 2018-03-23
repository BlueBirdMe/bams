<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>日程安排明细</title>
		<%
			String calenderpk = request.getParameter("calenderpk");
		%>
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
<script type="text/javascript">
window.onload = function(){
	dwrWorkArrangeService.getOaCalenderByPk(<%=calenderpk%>,setOaCalenderinfo);
}
function setOaCalenderinfo(data){
	if(data.success == true){
		if(data.resultList.length > 0){
			var oaCalender = data.resultList[0];
			var level="";
			if (oaCalender.oaCalenderLevel == <%=EnumUtil.OA_CALENDER_LEVEL.one.value%>){
				level = "<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.one.value)%>&nbsp;<img  title ='重要/紧急' src='<%=contextPath%>/images/grid_images/zyjj.png' border='0'/>";
			}else if (oaCalender.oaCalenderLevel == <%=EnumUtil.OA_CALENDER_LEVEL.two.value%>){
				level = "<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.two.value)%>&nbsp;<img title ='重要/不紧急' src='<%=contextPath%>/images/grid_images/zybjj.png' border='0'/>";
			}else if (oaCalender.oaCalenderLevel == <%=EnumUtil.OA_CALENDER_LEVEL.three.value%>){
				level = "<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.three.value)%>&nbsp;<img title ='不重要/紧急' src='<%=contextPath%>/images/grid_images/bzyjj.png' border='0'/>";
			}else{
				level = "<%=EnumUtil.OA_CALENDER_LEVEL.valueOf(EnumUtil.OA_CALENDER_LEVEL.four.value)%>&nbsp;<img title ='不重要/不紧急' src='<%=contextPath%>/images/grid_images/bzybjj.png' border='0'/>";
			}
			
			document.getElementById("oaCalenderLevel").innerHTML = level;
			DWRUtil.setValue("oaCalenderType",oaCalender.library.libraryInfoName);
			var dateTime =oaCalender.oaCalenderStart+" 至 "+oaCalender.oaCalenderEnd
			DWRUtil.setValue("oaCalenderStart",dateTime);
			
			
			var status = "";
			if(oaCalender.oaCalenderStatus == <%=EnumUtil.OA_CALENDER_STATUS.one.value%>){
			status = '<%=EnumUtil.OA_CALENDER_STATUS.valueOf(EnumUtil.OA_CALENDER_STATUS.one.value)%>';
			}else{
			status = '<%=EnumUtil.OA_CALENDER_STATUS.valueOf(EnumUtil.OA_CALENDER_STATUS.two.value)%>';
			}
			DWRUtil.setValue("oaCalenderStatus",status);
			DWRUtil.setValue("oaCalenderContent",oaCalender.oaCalenderContent);
					
		}else{
			alert(data.message);
		}
	}else{
		alert(data.message);
	}
}
</script>
	</head>
	<body class="inputdetail">

		<div class="requdivdetail">
			<label>
				查看帮助:&nbsp;您可以在此了解工作安排。
			</label>
		</div>
			<div class="detailtitle">
				工作安排明细
			</div>
			<table class="detailtable" align="center">
				<tr>
					<th width="15%">
						优先级
					</th>
					<td id="oaCalenderLevel" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						日程类型
					</th>
					<td id="oaCalenderType" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						开始日期
					</th>
					<td id="oaCalenderStart" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						日程状态
					</th>
					<td colspan="3" id="oaCalenderStatus" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						内 容
					</th>
					<td colspan="3" id="oaCalenderContent" class="detailtabletd">
					</td>
				</tr>
			</table>
	</body>
</html>