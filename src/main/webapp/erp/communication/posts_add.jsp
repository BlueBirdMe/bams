<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrOaCommunicationService.js"></script>
<title>新发帖子</title>
<%
     String  forumid = request.getParameter("forumID");
     String flag = request.getParameter("flag");
     String ftmpid = request.getParameter("fid");
 %>
<script type="text/javascript">
	window.onload = function(){
    useLoadingMassage();
	initInput('helpTitle',"发布新帖，发布前请选择需要发布的论坛版块!");
	document.getElementById("oaPostName").focus();
}

function loadselect(){
    if(<%=forumid%> != null){
         var id = <%=forumid%>;
         var obj =document.getElementById("oaPostForum");
         obj.value = id;
		 obj.className ="niceform";
		 obj.disabled = true;
    }
}

function getPostsinfo(){
	var posts = new Object();
	posts.oaPostName = document.getElementById("oaPostName").value;
	<%if(ftmpid!=null){%>
		posts.oaPostForum = <%=ftmpid%>;
	<%}else{%>
		posts.oaPostForum = document.getElementById("oaPostForum").value;
	<%}%>
	posts.oaPostText = fck.GetXHTML();
	return posts;
}
function save(){
     var bl = validvalue('helpTitle');
     if(bl){
	      dwrOaCommunicationService.savePosts(getPostsinfo(),saveCallback);  
	      Btn.close();
     }
}
	
function saveCallback(data){
	    Btn.open();
		if(data.success){
			confirmmsgAndTitle("添加新帖成功！是否想继续添加?","reloadpager();","继续添加","closePage();","关闭页面");
		}else{
  			alertmsg(data);
		}
}

function closePage(){
	var frm = window.parent.parent.getMDIFrame(<%=request.getParameter("tab")%>);
	
	if(typeof frm != 'undefined'){
		
		if(isBlank(frm.location)) 
			frm = frm.contentWindow;
	
		if(<%=flag%> != true){
			frm.getAllPosts(null);	//调用原页面查询方法
		}else{
			frm.queryData();
		}
	}
	closeMDITab();
}


function reloadpager(){
    document.getElementById("oaPostName").value = "";
    if(<%=forumid%> != null||<%=ftmpid%>!=null){
          //不变
    }else{
          document.getElementById("oaPostForum").selectedIndex =0;
    }
    fck.SetHTML("");
 	document.getElementById("oaPostName").focus();
 	refreshMDITab(<%=request.getParameter("tab")%>);
}

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	window.status = editorInstance.Description;
}

var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}
  
  
function reset(){
 	DWRUtil.setValue("oaPostName","");
 	fck.SetHTML("");
 	document.getElementById("oaPostName").focus();
 	
}

function backToTopicList(){
  		if(<%=flag%> == true ){
  			window.parent.MoveDiv.close();
			window.parent.queryData();
			return;
  		}else if(<%=flag%> == false ){
  			window.parent.MoveDiv.close();
			window.parent.getAllPosts(null);
  		} else {
			Sys.href('<%=contextPath%>/erp/communication/my_posts_manage.jsp');
		}
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">
		添加帖子
	</div>
	<div>
	<table class="inputtable">
	<tr>
		<th><em>* </em>帖子名称</th>
		<td  colspan="3" style="text-align: left;"><input type="text" id="oaPostName" maxlength="50" style="width: 45%" formust="oaPostNamemust" must="帖子名称不能为空！" value="" >
		<label id="oaPostNamemust"></label>
		</td>
	</tr>
	<%if(ftmpid == null){ %>
	<tr>
		<th><em>* </em>论坛版块</th>
    	<td style="text-align: left;"><select formust="oaPostForummust" must="请选择所属分组" id="oaPostForum"><%=UtilTool.getForumsSelectOptions(this.getServletContext(),request,null)%></select>
    	<label id="oaPostForummust"></label>
    	</td>
	</tr>
	<%} %>
	<tr>
		<th>帖子内容</th>
		<td style="text-align: left" colspan="3">
		<FCK:editor instanceName="oaPostText" height="240" width="90%" ></FCK:editor>
		</td>
	</tr>
	</table>
	</div>
	<br/>
<br/>
</div>

<br/>
<center>
<table>
<tr>
    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    	<td style="width: 10px;"></td>
    	<td><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
</tr>
</table>
</center>
</body>
</html>