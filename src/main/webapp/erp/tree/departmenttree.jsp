<%@ page pageEncoding="UTF-8" language="java"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.core.pojo.HrmDepartment"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrHrmEmployeeService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String fid = request.getParameter("fid");
String ischeck = request.getParameter("ischeck");
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
			String chstr = "";
			String val=dep.getHrmDepId();
			int row = hrmService.listDownDepartByCodeCount(request,dep.getHrmDepId());
			if(ischeck!=null&&ischeck.length()>0){
				if(row>0){
					tmp ="src=\""+request.getContextPath()+"/erp/tree/departmenttree.jsp?fid="+dep.getHrmDepId()+"&ischeck=true\"";
				}else{
					tmp="";
				}
				chstr ="onchange=\"getCheckedIds();\"  type=\"check\"";
				val = String.valueOf(dep.getPrimaryKey());
			}else{
				if(row>0){
					tmp ="src=\""+request.getContextPath()+"/erp/tree/departmenttree.jsp?fid="+dep.getHrmDepId()+"\"  action=\"treeclick("+dep.getPrimaryKey()+",'"+dep.getHrmDepName()+"');\"";
				}else{
					tmp ="action=\"treeclick("+dep.getPrimaryKey()+",'"+dep.getHrmDepName()+"');\"";
				}
			}
			//输出树节点
			sb.append("<tree "+chstr+" id =\"dept_"+dep.getPrimaryKey()+"\" text=\""+dep.getHrmDepName()+"\" value=\""+val+"\" "+tmp+"/>\n");
		}
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}
%>
