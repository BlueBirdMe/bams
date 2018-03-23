<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrOaNewsService.js"></script>
<title>公告发布</title>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
	String announcementpk = request.getParameter("announcementpk");
	String noid = request.getParameter("noid");
	String isedit = "false";
	if (announcementpk != null) {//编辑时使用
		isedit = "true";
	}
%>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处添加您想发布的公告！公告有效时可被他人阅读！');
	saveOredit();
	tmp="请输入新闻内容";
	
    //第一个输入框获取焦点
    document.getElementById("oaAnnoName").focus();
}

function backToAnnoList(){
    window.location = "announcement_manager.jsp";
}

function getAnnouncementinfo(){
	var announcement = new Object();
   	if(<%=announcementpk%> != null){
      announcement.primaryKey = <%=announcementpk%>;
    }
    
	announcement.oaAnnoName = document.getElementById("oaAnnoName").value;
	announcement.oaAnnoType = document.getElementById("oaAnnoType").value;
	announcement.oaAnnoLevel = getRadioValueByName("oaAnnoLevel");
	announcement.oaAnnoText = fck.GetXHTML();
	return announcement;
}

function reload(){
    if(<%=noid%> == '1'){
        Sys.load('<%=contextPath%>/erp/issue_info/announcement_manager.jsp');
    }else{
        Sys.load('<%=contextPath%>/erp/issue_info/announcement_info.jsp');
    }
}

function save(){
    //定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaAnnoTextMust";
	//清空所有信息提示
	warnInit(warnArr);
	
	//验证常用组件
    var bl = validvalue('helpTitle');
    if(bl){
         var acce = DWRUtil.getValue("oaAnnoAcce");
         var context = fck.GetXHTML();
         if(trim(acce) == "" && trim(context) == "" || trim(context).length < 10 && trim(acce) == ""){
              setMustWarn("oaAnnoTextMust","公告内容和附件至少一个不能为空,且公告内容至少10个字符!");
              fck.Focus();
              return;
         }else{
              if(<%=announcementpk%> != null){
              	   Btn.close();
	               var acce =   document.getElementById("oaAnnoAcce").value;
	               dwrOaNewsService.updateAnnouncement(getAnnouncementinfo(),acce,updateCallback);
	          }else{ 
	          		Btn.close();
	               var acce =   document.getElementById("oaAnnoAcce").value;
	               dwrOaNewsService.saveAnnouncement(getAnnouncementinfo(),acce,saveCallback);
	          }
         }   
	}
}
    
function saveCallback(data){
	Btn.open();
    document.getElementById("oaAnnoName").focus();
    confirmmsgAndTitle("添加公告成功！是否继续添加?","reback();","继续添加","closePage();","关闭页面");
}	

//修改成功后处理
function updateCallback(data){
	data.message = "修改公告成功！";
	alertmsg(data,"reback();");
}

function reback(){
	if(<%=announcementpk%> != null){
		window.parent.MoveDiv.close();
    	window.parent.queryData();
	    //reload();
    }else{
        DWRUtil.setValue("oaAnnoName","");
        //刷新附件
	 	Sys.setFilevalue("oaAnnoAcce","");
        document.getElementById("oaAnnoType").selectedIndex = 0;
        fck.SetHTML("");
         
        document.getElementById("oaAnnoName").focus();
    }
}

function  saveOredit(){
     if(<%=announcementpk%> != null){
		var primaryKey = <%=announcementpk%>;
		dwrOaNewsService.getAnnouncementByPk(primaryKey,setAnnouncementinfo);
		var btn = document.getElementById("backToList");
	    btn.style.display = "none";
	 }else{
	    var btn = document.getElementById("backbtn");
	    btn.style.display = "none";
	 }
}

var fckvalue ="";

function setAnnouncementinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var announcement = data.resultList[0];
 			document.getElementById("oaAnnoName").value = announcement.oaAnnoName;
 			document.getElementById("oaAnnoType").value = announcement.oaAnnoType;
 			setRadioValueByName("oaAnnoLevel",announcement.oaAnnoLevel);	//设置radio的值
 			if(announcement.oaAnnoAcce != null && announcement.oaAnnoAcce != undefined && announcement.oaAnnoAcce.length > 0){
					dwrCommonService.getAttachmentInfoListToString(announcement.oaAnnoAcce,setaccept);
			}
 			fckvalue = announcement.oaAnnoText;
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

//放入附件
function setaccept(data){
	Sys.setFilevalue("oaAnnoAcce",data);
}

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function closePage(){
	closeMDITab();
}
</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">公告内容</div>
	<div>
	    <table class="inputtable" border="0">
	    <tr>
			<th width="40%"><em>*</em>&nbsp;&nbsp;公告名称</th>
			<td width="40%" style="text-align: left;"><input type="text" id="oaAnnoName" must="公告名称不能为空!" formust="oaAnnoNameMust" value="" style="width:90%;" maxlength="50" ></td>
			<td width="40%"><label id="oaAnnoNameMust"></label></td>
		</tr>
		<tr>
			<th width="40%">公告类型</th>
			<td width="40%"><select must="公告类型不能为空！" id="oaAnnoType"><%=UtilTool.getSelectOptions(this.getServletContext(), request, null, "06")%></select></td>
			<td></td>
		</tr>
		<tr>
			<th width="20%">重要级</th>
			<td style="text-align: left;"><%=UtilTool.getRadioOptionsByEnum(EnumUtil.OA_NEWS_ISTOP.getSelectAndText(""),"oaAnnoLevel")%></td>
			<td></td>
		</tr>
		<tr>
			<th><span style="color:blue">•</span>&nbsp;&nbsp;附件</th>
			<td  colspan="3">
			<file:multifileupload width="90%" acceptTextId="oaAnnoAcce" height="100" edit="<%=isedit%>" saveType="file"></file:multifileupload>
			</td>
		</tr>
		<tr>
			<th><span style="color:blue">•</span>&nbsp;&nbsp;内容</th>
			<td style="text-align: left" colspan="2">
			<label id="oaAnnoTextMust"></label>
			<FCK:editor instanceName="oaAnnoText" width="90%" height="250"></FCK:editor>
			</td>
		</tr>
	    </table>
	    <br/>
	</div>
</div>
<br/>
	<table align="center">
		<tr>
			<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存公告信息" /></td>
			<td style="width: 20px;"></td>
			<td id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
			<td><div id="backbtn"><btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></div></td>
		</tr>
	</table>
</body>
</html>