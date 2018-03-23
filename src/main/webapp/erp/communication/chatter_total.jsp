<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录查看</title>
<script  type='text/javascript'   src='<%=contextPath%>/js/leftMethod.js'></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<%
    long comid = UtilTool.getCompanyId(request);
    String useId = UtilTool.getEmployeeId(request);
    WebApplicationContext appcontext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
   	DwrOaCommunicationService communicationservice = (DwrOaCommunicationService)appcontext.getBean("dwrOaCommunicationService");
   	List<OaChatGroups> grouplist = communicationservice.getAllChatGroup(this.getServletContext(),request,useId,comid).getResultList();
   	long pk=0;
   	if(grouplist.size()>0){
   		pk=grouplist.get(0).getPrimaryKey();
   	}
%>
<script type="text/javascript">
window.onload=function(){

Sys.load('communications_manual.jsp?groupId=<%=pk%>','chatter');
}
function refreshGroups(obj){
	Sys.load("communications_manual.jsp?groupId="+obj.id,"chatter");
}
</script>
</head>
<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100%">
<tr>
<td id="split_l" style="width:130px;">
<div class="div_title">选择操作</div>
		
<%
if(grouplist.size()>0){
 %>
<div class="div_content">
<div class="div_leftmethod">
	<%
	for(int i=0;i<grouplist.size();i++){
		OaChatGroups fr = grouplist.get(i);
	 %>
	<div class="leftbut" onclick="refreshGroups(this);" id="<%=fr.getPrimaryKey()%>" title="<%=fr.getOaChatgpName() %>">
	<img src="<%=contextPath%>/images/pagemethodimg/company-1.png"/>
	<div><%=fr.getOaChatgpName() %></div>
	</div>
	<%} %>
</div>
</div>
<%} %>
</td>
<td>
<iframe  frameborder="0"  height="100%" scrolling="auto" id="chatter" width="100%"></iframe>
</td>
</tr>
</table>
</body>
</html>
