<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader("Expires",0);
		String treetype = request.getParameter("treetype");
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
		<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
		<script type="text/javascript">
		var tree = new WebFXLoadTree("功能目录","<%=contextPath%>/erp/select_takepage/user_methods_tree.jsp?code=-1&treetype=<%=treetype%>");
			
		function methodInfoIdclick(dialogId,method){
			var methodId = getCheckValue();
			if(isNotBlank(methodId)){
				dwrSysProcessService.saveSysMethodShortcut(methodId,function(data){saveCallback(data,dialogId,method);});
			}else{
				Sys.close(dialogId);
			}
		}
		
		function saveCallback(data,dialogId,method){
		    if(data.success){
		    	var win = Sys.getfrm();
		    	eval("win."+method);
			    Sys.close(dialogId);
		    }else{
		    	alertmsg(data);
		    }
		}
		</script>
	</head>
	<body>
	<div class="div_tree" style="height:100%;">
	<script type="text/javascript">
	  document.write(tree);
	</script>
	</div>
	
   </body>
</html>