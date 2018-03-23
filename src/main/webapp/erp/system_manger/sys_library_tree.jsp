<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String contextPath = request.getContextPath();
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
var jsmethod= document.getElementById("treejs").value;
var tree = new WebFXLoadTree("业务字典","<%=request.getContextPath()%>/erp/tree/syslibrary.jsp?upcode=00&method="+jsmethod,""+jsmethod+"('','');");
function treeclick(id,code){
	document.getElementById("upcode").value =code;
	queryData();
}
</script>
<div class="div_tree">
<script type="text/javascript">
  document.write(tree);
</script>
</div>
<input type="hidden" id="upcode">