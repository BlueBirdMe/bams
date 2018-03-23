<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<title>记事发布</title>
<%
	String adversariapk =request.getParameter("adversariapk");
	
	String isedit = "false";
	if(adversariapk != null){
	     isedit = "true";
	}
%>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','您可以在此处添加您想发布的公司记事！记事有效时可被他人阅读！');
	saveOredit();
	tmp="请输入记事内容";

    //第一个输入框获取焦点
    document.getElementById("oaAdverTitle").focus();
}

function backToAdverList(){
    window.location = "adversaria_info.jsp";
}

function getAdversariainfo(){
	var adversaria = new Object();
   	
   	if(<%=adversariapk%> != null){
      adversaria.primaryKey = <%=adversariapk%>;
    }
    
	adversaria.oaAdverTitle = document.getElementById("oaAdverTitle").value;
	adversaria.oaAdverLevel = getRadioValueByName("oaAdverLevel");
	adversaria.oaAdverText = fck.GetXHTML();
	return adversaria;
}

function save(){
    //定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaAdverTextMust";
	//清空所有信息提示
	warnInit(warnArr);
	
    //验证常用组件
    var bl = validvalue('helpTitle');
    if(bl){
        var context = fck.GetXHTML();
        if(DWRUtil.getValue("oaAdverAcce") == "" && trim(context) == "" || trim(context).length < 10 && DWRUtil.getValue("oaAdverAcce") == ""){
             setMustWarn("oaAdverTextMust","记事内容和附件至少一个不能为空,且记事内容至少10个字符!");
             fck.Focus();
             return;
        }else{
             if(<%=adversariapk%> != null){
             	 Btn.close();
                 var acceFile = document.getElementById("oaAdverAcce").value;
	             dwrOaNewsService.updateAdversaria(getAdversariainfo(),acceFile,updateCallback);
	         }else{ 
	        	 Btn.close();
	             var acceFile = document.getElementById("oaAdverAcce").value;
	             dwrOaNewsService.saveAdversaria(getAdversariainfo(),acceFile,saveCallback);
	         }
        }
    }
}
	
function saveCallback(data){
	Btn.open();
	document.getElementById("oaAdverTitle").focus();
	confirmmsgAndTitle("添加公司记事成功！是否继续添加?","loadpager();","继续添加","closePage();","关闭页面");
}

//修改成功后处理
function updateCallback(data){
	alertmsg("修改公司记事成功！","loadpager();");
}

function loadpager(){
    if(<%=adversariapk%> != null){
    	window.parent.MoveDiv.close();
    	 window.parent.queryData();
    }else{
         DWRUtil.setValue("oaAdverTitle","");
         //document.getElementById("oaAdverLevel").selectedIndex =0;
         //刷新附件
		 Sys.setFilevalue("oaAdverAcce","");
         fck.SetHTML("");
         
         document.getElementById("oaAdverTitle").focus();
    }
}

function  saveOredit(){
     if(<%=adversariapk%> != null){
		var primaryKey = <%=adversariapk%>;
		dwrOaNewsService.getAdversariaByPk(primaryKey,setAdversariainfo);
		var btn = document.getElementById("backToList");
	    btn.style.display = "none";
	 }else{
	    var btn = document.getElementById("backbtn");
	    btn.style.display = "none";
	 }
}

var fckvalue ="";

function setAdversariainfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var adversaria = data.resultList[0];
 			document.getElementById("oaAdverTitle").value = adversaria.oaAdverTitle;
 			setRadioValueByName("oaAdverLevel",adversaria.oaAdverLevel);	//设置radio的值
 			if(adversaria.oaAdverAcce != null && adversaria.oaAdverAcce != undefined && adversaria.oaAdverAcce.length > 0){
					dwrCommonService.getAttachmentInfoListToString(adversaria.oaAdverAcce,setaccept);
				}			
 			fckvalue = adversaria.oaAdverText;
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

//放入附件
function setaccept(data){
	Sys.setFilevalue("oaAdverAcce",data);
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
    <div class="formTitle">记事内容</div>
	<div>
	    <table class="inputtable" border="0">
	    <tr>
			<th width="20%"><em>*</em>&nbsp;&nbsp;记事标题</th>
			<td width="40%" style="text-align: left;"><input type="text" id="oaAdverTitle" must="记事标题不能为空！" formust="oaAdverTitleMust" value="" style="width:90%;" maxlength="50" ></td>
			<td width="40%"><label id="oaAdverTitleMust"></label></td>
		</tr>
		<tr>
		    <th width="20%">&nbsp;&nbsp;重要级</th>
		    <td style="text-align: left;"><%=UtilTool.getRadioOptionsByEnum(EnumUtil.OA_NEWS_ISTOP.getSelectAndText(""),"oaAdverLevel")%></td>
		    <td></td>
		</tr>
		<tr>
		    <th><span style="color:blue">•</span>&nbsp;&nbsp;附件</th>
		    <td  colspan="3">
		    	<file:multifileupload width="90%" acceptTextId="oaAdverAcce" height="100" edit="<%=isedit%>"></file:multifileupload>
		    </td>
		</tr>
		<tr>
		    <th><span style="color:blue">•</span>&nbsp;&nbsp;内容</th>
		    <td style="text-align: left" colspan="2">
			    <label id="oaAdverTextMust"></label>
			    <FCK:editor instanceName="oaAdverText" width="90%" height="240"></FCK:editor>
			</td>
		</tr>
	    </table>
		<br/>
	</div>
</div>
<br/>
	<table align="center">
		<tr>
			<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存公司记事" /></td>
			<td style="width: 20px;"></td>
			<td id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
			<td ><DIV id ="backbtn"><btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></DIV></td>
		</tr>
	</table>
</body>
</html>