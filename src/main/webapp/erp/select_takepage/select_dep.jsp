<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
String textid = request.getParameter("textid");
String valueid = request.getParameter("valueid");
%>
<title>功能树</title>
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
var treetype= '<%=treetype%>';
function setliandong(obj){
	webFXTreeConfig.setCascadeCheck(obj.checked);
}
window.onload = function(){
	if (treetype == "radio") {
		webFXTreeConfig.setCascadeCheck(false);
		document.getElementById("lidongdiv").style.display="none";
		document.getElementById("div_tree").style.height="100%";
	}else{
		webFXTreeConfig.setCascadeCheck(document.getElementById("lidong").checked);
	}
	
	var tree = new WebFXLoadTree("部门树","<%=request.getContextPath()%>/erp/select_takepage/dep_tree.jsp?fid=00&treetype="+treetype+"");
	document.getElementById("div_tree").innerHTML = tree;
}

function treeclick(myfrmname) {
	var win = Sys.getfrm(); //获取index页面iframe window对象	
	if (myfrmname != null && myfrmname != "undefined" && myfrmname != undefined) {
		var myfrmnames = myfrmname.split("@@");
    	if(myfrmnames.length>1){
    		for(var i=0;i<myfrmnames.length;i++){
    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
    		}
    	}else{
    		win = win.document.getElementById(myfrmname).contentWindow;
    	}
	}
	var textid = win.document.getElementById("<%=textid%>");
	var valueid = win.document.getElementById("<%=valueid%>");
	if (treetype == "radio") {
		var value = getCheckValue();
		var text = getCheckText();
		if(value==null){
			alertmsg("请选择相应数据记录...");
			return;
		}
		textid.value = text;
		valueid.value = value;
	} else if (treetype == "check") {
		var values = getCheckValues();
		var texts = getCheckTexts();
		var value = "";
		var text = "";
		if(values.length==0){
			alertmsg("请选择相应数据记录...");
			return;
		}
		for (var i = 0; i < values.length; i++) {
			value += values[i] + ",";
		}
		for (var i = 0; i < texts.length; i++) {
			text += texts[i] + ",";
		}
		if (valueid.value == "" || valueid.value == null) {
			valueid.value = value;
			textid.value = text;
		} else { //去除重复
			var tmps = removerepeat(valueid.value + value, textid.value + text);
			 textid.value = tmps[1];
			valueid.value = tmps[0];
		}
	}
}

function deptclickcustomer(dialogId,myfrmname,method){
	treeclick(myfrmname);
	if(method != null && method != "undefined" && method != undefined){
		var win = Sys.getfrm();	
		if (myfrmname != null && myfrmname != "undefined" && myfrmname != undefined) {
		     var myfrmnames = myfrmname.split("@@");
	    	if(myfrmnames.length>1){
	    		for(var i=0;i<myfrmnames.length;i++){
	    			win = win.document.getElementById(myfrmnames[i]).contentWindow;
	    		}
	    	}else{
	    		win = win.document.getElementById(myfrmname).contentWindow;
	    	}
    	}
		eval("win."+method);
	}
	Sys.close(dialogId);
}
</script>
</head>
<body style="overflow: hidden;">
<div style="background: #ededed;width: 100%;padding: 5px" id="lidongdiv">
<input type="checkbox" id="lidong" onchange="setliandong(this)" checked="checked">
<label for="lidong" style="color: #336699">选择上级自动选中下级</label>
</div>
<div class="div_tree" id="div_tree" style="height:90%"></div>
<input type="hidden" id="upcode">
</body>
</html>