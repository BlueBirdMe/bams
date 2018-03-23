<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务字典拾取</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String name = request.getParameter("name");
String code = request.getParameter("code");
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
var tree = new WebFXLoadTree("标准代码","<%=request.getContextPath()%>/erp/select_takepage/syslibrary_standard_xml.jsp?upcode=00");
function gettreecheck(myfrmname){
	var code = getCheckValue();
	var name = getCheckText();
	if(code == null){
		alertmsg("请选择上级编码...");
		return;
	}else{
		var w = Sys.getfrm();//获取index页面iframe window对象
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	w = w.document.getElementById(myfrmname).contentWindow;	
	    }
		var libupname = w.document.getElementById("<%=name%>");
		var libupcode = w.document.getElementById("<%=code%>");
		if(isNotBlank(libupname) && isNotBlank(libupcode)){
			libupname.value=name;
			libupcode.value = code;
		}
	}
}

function gettreecheckcustom(dialogId,myfrmname,method){
	gettreecheck(myfrmname);
	if(method!=null&&method != "undefined" && method != undefined){
		var win = Sys.getfrm();//获取index页面iframe window对象
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
		eval("win."+method);
	}
	Sys.close(dialogId);
}
</script>
</head>
<body style="overflow: hidden;">
<div class="div_tree">
<script type="text/javascript">
  document.write(tree);
</script>
</div>
</body>
</html>