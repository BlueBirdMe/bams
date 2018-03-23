<%@ page pageEncoding="UTF-8" language="java"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.core.pojo.HrmDepartment"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrHrmEmployeeService"%>
<%@page import="com.pinhuba.common.util.UtilWork"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String fid = request.getParameter("fid");
String treetype = request.getParameter("treetype");
if(fid == null||fid.length()==0){
	return;
}else{
	WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DwrHrmEmployeeService hrmService = (DwrHrmEmployeeService)webAppContext.getBean("dwrHrmEmployeeService");
	List<HrmDepartment> deptlist = hrmService.listDownDepartByCode(request,fid);
	if(deptlist!=null&& deptlist.size()>0){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<tree>\n");
		for(int i=0;i<deptlist.size();i++){
			HrmDepartment dep = deptlist.get(i);
			String tmp = "";
			int row = hrmService.listDownDepartByCodeCount(request,dep.getHrmDepId());
			if(row>0){
				tmp ="src=\""+request.getContextPath()+"/erp/select_takepage/dep_tree.jsp?fid="+dep.getHrmDepId()+"&treetype="+treetype+"\"";
			}
			//输出树节点
			sb.append("<tree type=\""+treetype+"\" id =\"dept_"+dep.getPrimaryKey()+"\" text=\""+dep.getHrmDepName()+"\" value=\""+dep.getPrimaryKey()+"\" "+tmp+"/>\n");
		}                        
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}
%>
