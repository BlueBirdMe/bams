<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String path = request.getParameter("path");
String textid = request.getParameter("textid");
String frmname = request.getParameter("frmname");
String imgpath = "/images/"+path+"/";

List<String> imglist = new ArrayList<String>();
File[] files = new File(request.getRealPath(imgpath)).listFiles();
for (int i = 0; i < files.length; i++){
   imglist.add(files[i].getName());
}
%>
<title>拾取图标</title>
<script>
function selectImg(obj){
	var myfrmname = "<%=frmname%>";
    var win = Sys.getfrm();//获取index页面iframe window对象	
    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
    	win = win.document.getElementById(myfrmname).contentWindow;	
    }
    var textid=win.document.getElementById("<%=textid%>");
    
    var s = obj.src;
    var img = s.substring(s.lastIndexOf("/")+1);
	textid.value = img;
	Sys.close("imgDialog");
}
</script>
</head>
<body>
<% for(String imgName : imglist){%>
<img src="<%=contextPath + imgpath + imgName%>" 
	style="margin:3px;cursor:pointer;" onclick="selectImg(this);" title="<%=imgName%>">
<%} %>
</body>
</html>