<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
<title>新增版块界面</title>
<%
    String forumId = request.getParameter("forumId");
    String iedit= "false";
    if(forumId != null){
         iedit = "true";
    }
 %>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
    initInput('aaac',"添加论坛版块， 您可以在此添加论坛版块，版块名字最长5个中文字。");
		//第一个输入框获取焦点
	document.getElementById("oaForumName").focus();
    saveOredit();
}

function saveOredit(){
   if(<%=forumId%> != null){
       var id = <%=forumId%>;
       dwrOaCommunicationService.getForumByid(id,setForumInfo);
   }
}

function setForumInfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 		     var forum = data.resultList[0]; 
 		     DWRUtil.setValue("oaForumName",forum.oaForumName);
 		     DWRUtil.setValue("oaForumText",forum.oaForumText);
 		     if(forum.oaForumAdmin != null){
 		         DWRUtil.setValue("Employeeid",forum.oaForumAdmin);
 		         DWRUtil.setValue("oaObjEmp",forum.forumAdminName);
 		     }
 		     
 		     //放入图片
 		     if(forum.oaForumImage != null && forum.oaForumImage != undefined && forum.oaForumImage.length > 0){
 			      dwrCommonService.getImageInfoListToString(forum.oaForumImage,setImage);
 			 }
 		}else{
 		     alert(data.message);
 		}
    }else{
        alert(data.message);
    }
}

function setImage(data){
   Sys.setFilevalue("oaForumImage",data);
}

function getForumsinfo(){
	var forum = new Object();
	if(<%=forumId%> != null){
	      forum.primaryKey = <%=forumId%>;
	}
	forum.oaForumName = document.getElementById("oaForumName").value;
	forum.oaForumAdmin = document.getElementById("Employeeid").value;
	forum.oaForumText = document.getElementById("oaForumText").value;
	forum.oaForumImage = document.getElementById("oaForumImage").value;
	
	return forum;
}
function save(){
      var bl = validvalue('aaac');
      if(bl){
           if(<%=forumId%> != null){
                dwrOaCommunicationService.updateForums(getForumsinfo(),saveCallback); 
                Btn.close();
           }else{
                dwrOaCommunicationService.saveForums(getForumsinfo(),saveCallback);
                Btn.close(); 
           }
      }
}
	
function saveCallback(data){
	Btn.open();
	if(<%=forumId%>!=null){
		 alertmsg(data,"reload()");
	}else{
		if(data.success){
			confirmmsgAndTitle("添加版块成功！是否想继续添?","reloadpager();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function  reloadpager(){
      document.getElementById("oaForumName").value = "";
      document.getElementById("oaForumText").value = "";
      document.getElementById("oaObjEmp").value = "";
      document.getElementById("Employeeid").value = "";
      //清空图片框
      Sys.setFilevalue("oaForumImage","");
      document.getElementById("oaForumName").focus(); 
      refreshMDITab(<%=request.getParameter("tab")%>);
}

function getemployee(){
	<%if (forumId == null){ %>
		var box = SEL.getEmployeeIds("radio","oaObjEmp","Employeeid");
	<%}else{ %>
		var box = SEL.getEmployeeIds("radio","oaObjEmp","Employeeid","processloadfrm");
	<%}%>
	box.show();
}

function reload(){
    window.parent.MoveDiv.close();
	window.parent.queryData();
}

</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="aaac"></label></div>
		<div class="formTitle">
		新增版块
		</div>
	<div>
	
	<table class="inputtable">
	<tr>
		<th>版块图片</td>
		<td>
		<file:imgupload width="150px" acceptTextId="oaForumImage" height="150" edit="<%=iedit%>" ></file:imgupload>
		</td>
	</tr>
	<tr>
		<th><em>* </em>版块名称</th>
		<td><input type="text" id="oaForumName" must="版块名称不能为空!" value="" maxlength="5" 
		formust="oaForumNamemust">
		<label id="oaForumNamemust"></label>
		</td>
	</tr>
	<tr>
		<th>版主</th>
		<td>
		<input type="text" class="takeform" linkclear="Employeeid" readonly="readonly" id="oaObjEmp" title="点击获取人员" 
		onclick="getemployee();">
		<input type="hidden" id="Employeeid" value="">
		</td>	
	</tr>
	<tr>
		<th>版块描述</th>
		<td>
		<textarea style="height: 200px;" id="oaForumText"></textarea>
		</td>
	</tr>
	</table>
	</div>
</div>
<table align="center">
<tr>
    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    	<td style="width: 10px;"></td>
    	<td>
    	<%if (forumId == null){ %>
		<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
		<%}else{ %>
		<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
		<%} %>
		</td>
    	
</tr>
</table>
</body>
</html>