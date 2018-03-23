<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String aid = request.getParameter("aid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script> 
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	dwrOaNewsService.getAnnouncementByPk(<%=Integer.parseInt(aid)%>,setPageValue);
}
function setPageValue(data){
	if(data != null){
		if(data.resultList.length>0){
	          var announcement = data.resultList[0];
	          DWRUtil.setValue("announcementName",announcement.oaAnnoName);
	          
	          var type = "<无>";
	          if(announcement.oaAnnoType != null){
	              type = announcement.oaAnnoLib.libraryInfoName;
	          }
	          DWRUtil.setValue("announcementType",type);
	          
	          var announcementStatusName = "<font color='green'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>";
	          if(announcement.oaAnnoStatus == <%=EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value%>){
	              announcementStatusName = "<font color='red'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>";
	          }
	          document.getElementById("announcementStatus").innerHTML = announcementStatusName;
	          
	          if(announcement.oaAnnoLevel == <%=EnumUtil.OA_NEWS_ISTOP.YES.value%>){
	               document.getElementById("announcementLevel").innerHTML = "<img src='<%=contextPath%>/images/lve1.gif'>&nbsp;<%=EnumUtil.OA_NEWS_ISTOP.valueOf(EnumUtil.OA_NEWS_ISTOP.YES.value)%>";
	          }else{
	               document.getElementById("announcementLevel").innerHTML = "<%=EnumUtil.OA_NEWS_ISTOP.valueOf(EnumUtil.OA_NEWS_ISTOP.NO.value)%>";
	          }
	          
	          DWRUtil.setValue("announcementTime",announcement.oaAnnoTime);
	          
	          if(announcement.employee!=null){
	          	document.getElementById("announcementEmpName").innerHTML = announcement.employee.hrmEmployeeName;
	          }else{
	          	document.getElementById("announcementEmpName").innerHTML ="&nbsp;";
	          }
	          
	          document.getElementById("announcementText").innerHTML = announcement.oaAnnoText;
	          //附件显示为下载
			  Sys.showDownload(announcement.oaAnnoAcce,"announcementAcce");
         }
    }
}
</script>
</head>
  <body class="inputdetail">
  <div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
  <div class="detailtitle">公告明细</div>
	<table class="detailtable" align="center">
		<tr>
			<th width="15%">公告名称</th>
			<td id="announcementName" class="detailtabletd"></td>
			<td class="attachtd" rowspan="6">
				<div class="attachdiv">
					<div class="attachtitle">附件下载</div>
					<div class="attachdownload" id="announcementAcce"></div>
				</div>
			</td>
		</tr>
		<tr>
			<th>公告类型</th>
			<td id="announcementType" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>发布人</th>
			<td id="announcementEmpName" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>公告时间</th>
			<td id="announcementTime" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>重要级</th>
			<td id= "announcementLevel" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>公告状态</th>
			<td id="announcementStatus" class="detailtabletd"></td>
		</tr>
		<tr>
			<th>公告内容</th>
			<td colspan="3"  id="announcementText" class="detailtabletd"></td>
		</tr>
	</table>
</body>
</html>