<%@ page pageEncoding="UTF-8" language="java"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrHrmEmployeeService"%>
<%@page import="com.pinhuba.common.util.UtilTool"%>
<%@page import="com.pinhuba.core.pojo.HrmPost"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String fid = request.getParameter("fid");
String treetype = request.getParameter("type");
String getup = request.getParameter("getup");
String getid = request.getParameter("getid");
String tmp ="";
String tmp2 ="";
if(treetype!=null){
	tmp =" type=\""+treetype+"\"";
	tmp2 = "&type="+treetype;
}
if(getup!=null&&getup.length()>0){
	tmp2+= "&getup="+getup;
}
if(getid!=null&&getid.length()>0){
	tmp2+= "&getid="+getid;
}
if(fid == null||fid.length()==0){
	return;
}else{
	WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DwrHrmEmployeeService hrmService = (DwrHrmEmployeeService)webAppContext.getBean("dwrHrmEmployeeService");
	List<HrmPost> postlist = hrmService.getHrmPostByUpCode(request,fid);
	if(postlist!=null&& postlist.size()>0){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<tree>\n");
		for(int i=0;i<postlist.size();i++){
			HrmPost post = postlist.get(i);
			String tmp3 = "";
			String tmp4 = post.getHrmPostId();
			int row = hrmService.getHrmPostByupcodeCount(request,post.getHrmPostId());
			if(treetype==null){
				String tmp5=post.getHrmPostId();
				if(getid!=null){
					tmp5=String.valueOf(post.getPrimaryKey());
				}
				if(row>0){
					tmp3 ="src=\""+request.getContextPath()+"/erp/tree/posttree_xml.jsp?fid="+post.getHrmPostId()+tmp2+"\" action=\"posttreeclick('"+tmp5+"','"+post.getHrmPostName()+"');\"";
				}else{
					tmp3 ="action=\"posttreeclick('"+tmp5+"','"+post.getHrmPostName()+"');\"";
				}
			}else{
				if(row>0){
					tmp3 ="src=\""+request.getContextPath()+"/erp/tree/posttree_xml.jsp?fid="+post.getHrmPostId()+tmp2+"\"";
				}
				tmp4=String.valueOf(post.getPrimaryKey());
			}
			if(getup!=null){
				tmp4 = post.getHrmPostId();
			}
			//输出树节点
			sb.append("<tree "+tmp+" id =\"post_"+post.getPrimaryKey()+"\" text=\""+post.getHrmPostName()+"\" value=\""+tmp4+"\" "+tmp3+"/>\n");
		}                        
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}
%>
