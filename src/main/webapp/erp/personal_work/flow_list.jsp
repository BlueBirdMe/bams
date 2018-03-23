<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建工作</title>
<%
WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrApproveProcessService perService = (DwrApproveProcessService) webContext.getBean("dwrApproveProcessService");
List<SysProcessType> typeList = perService.listSysProcessTypeAll(this.getServletContext(), request).getResultList();
List<ApproveProcessBean> processList = perService.listSysApproveProcessAll(this.getServletContext(), request);
%>
<script type="text/javascript">
function showResource(processDefinitionId,resourceType){
	var box = new Sys.msgbox('流程图查看','<%=contextPath %>/processResource.do?type=image&pid='+processDefinitionId,750,500);
	box.show();
}

function transactFlow(url){
	openMDITab("<%=contextPath%>/erp/"+url);
}
</script>
</head>
<body style="overflow: hidden;">
	<div class="requdiv">
	<label>
	共有 <%=processList.size() %> 个流程，请选择流程开始办理。
	</label>
	</div>
	<% 
	for (int j = 0; j < typeList.size(); j++) {
		SysProcessType processType = typeList.get(j);
	%>
	<div class="formTitle">
	<%= processType.getTypeName() %>
	</div>
	<div>
		<%
		if (processType.getConfigList().size() > 0) {
			for (int i = 0; i < processType.getConfigList().size(); i++) {
				SysProcessConfig processConfig = processType.getConfigList().get(i);
		%>
			<div style="padding:10px 30px;float:left;">
				<img title="<%= processConfig.getProcessDesc()%>" src="<%=contextPath%>/images/workflowimg/evl_fkjc_.png" border="0"/>
				<p style="text-align:center;">
					<%= processConfig.getProcessDefinition().getName() %>
				</p>
				<p style="text-align:center;">
					<a href="javascript:void(0);" title="点击开始办理" onclick="transactFlow('<%= processConfig.getStartPage() %>');">
						新建工作
					</a>
					<a href="javascript:void(0);" title="点击查看流程图" onclick="showResource('<%= processConfig.getProcessDefinition().getId() %>');">
						流程图
					</a>
				</p>
			</div>
		<%
			}}
		%>
		<div style="clear:both"></div>
	</div>
	<%} %>
</body>
</html>