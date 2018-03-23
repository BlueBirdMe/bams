<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String contextPath = request.getContextPath();
String ischeck = request.getParameter("ischeck");
String tmp ="";
if(ischeck!=null&&ischeck.length()>0){
	tmp+="&ischeck=true";
}
%>
<link type='text/css' rel='stylesheet' href='<%=contextPath%>/css/xtree.css' />
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/map.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xtree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xloadtree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxTreeItem.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/xmlextras.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/checkboxXLoadTree.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioTreeItem.js' charset='UTF-8'></script>
<script type='text/javascript' src='<%=contextPath%>/js/treeJs/radioXLoadTree.js' charset='UTF-8'></script>
<script type='text/javascript'>webFXTreeConfig.setImagePath('<%=contextPath%>/js/treeJs/images/default/');</script>
<script type="text/javascript">
	function setliandong(obj){
		webFXTreeConfig.setCascadeCheck(obj.checked); 
	}
	webFXTreeConfig.setCascadeCheck(true);	
    var tree = new WebFXLoadTree("组织机构树","<%=request.getContextPath()%>/erp/tree/departmenttree.jsp?fid=00<%=tmp%>","treeclick('');");
</script>

<div class="div_tree">
	<%
	if(ischeck!=null && ischeck.length()>0){ 
	%>
		<input type="checkbox" id="lidong" onchange="setliandong(this)" checked="checked">
		<label for="lidong" style="color: #336699">选择上级自动选中下级</label>
	<%
	} 
	%>
<script type="text/javascript">
     document.write(tree);
     
     function getCheckedIds(){
		document.getElementById("upcode").value =getCheckValues();
		queryData();
	}
</script>
</div>
<input type="hidden" id="upcode">
