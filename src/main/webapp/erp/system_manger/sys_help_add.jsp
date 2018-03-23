<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<title>新增帮助</title>
<%
	String sysHelpPk = request.getParameter("sysHelpPk");
	List<SysMethodInfo> methodsList = (List<SysMethodInfo>)application.getAttribute(ConstWords.ServletContext_Method);
%>
<script type="text/javascript">
window.onload = function(){
	initInput('title');
	if(<%=sysHelpPk%> != null){
	    dwrSysProcessService.getSysHelpByPk(<%=sysHelpPk%>,setPageValue);
	}
}

var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function setPageValue(data){
	 if(data.success && data.resultList.length>0){
          var sysHelp = data.resultList[0];
          setSelectValue("methodcode",sysHelp.methodCode);
          DWRUtil.setValue("findsign",sysHelp.findSign);
          DWRUtil.setValue("helpTitle",sysHelp.helpTitle);
          DWRUtil.setValue("helpKeyword",sysHelp.helpKeyword);
         
		  fckvalue = sysHelp.helpContext;
		  if(fck!=null){
		  	fck.SetHTML(fckvalue);
		  }
	  }
}


function getSysHelpInfo(){
	var sysHelp = new Object();
   	if(<%=sysHelpPk%> != null){
      sysHelp.primaryKey = <%=sysHelpPk%>;
    }
    sysHelp.methodCode = DWRUtil.getValue("methodcode");
    sysHelp.findSign = DWRUtil.getValue("findsign");
	sysHelp.helpTitle = DWRUtil.getValue("helpTitle");
    sysHelp.helpKeyword = DWRUtil.getValue("helpKeyword");
	
	sysHelp.helpContext = fck.GetXHTML();
	return sysHelp;
}
function reload(){
    Sys.load('<%=contextPath%>/erp/system_manger/sys_help.jsp');
}

function save(){
   var bl = validvalue('title');
	if(bl){
    	 if(fck.GetXHTML().length < 10){
     		setMustWarn("helpContextMust","帮助内容必须大于10个字符！")
         	return;
         }
         if(<%=sysHelpPk%> != null){
	        dwrSysProcessService.updataSysHelp(getSysHelpInfo(),saveCallback);
	     }else{
	        dwrSysProcessService.saveSysHelp(getSysHelpInfo(),saveCallback);
	    }
	}
}
    
function saveCallback(data){
    alertmsg(data,"reback()");
}	

function reback(){
	if(<%=sysHelpPk%> != null){
	     reload();
    }else{
    	document.getElementById("methodcode").selectedIndex = 0;
         DWRUtil.setValue("findsign","");
         DWRUtil.setValue("helpTitle","");
         DWRUtil.setValue("helpKeyword","");
         fck.SetHTML("");
    }
}
</script>
</head>
<body>

<fieldset>
	<div class="requdiv"><label id="title"></label></div>
	<legend>新增/编辑帮助</legend>
	<div>
	<table class="inputtable">
	<tr>
	<th><em>*</em>所属模块</th>
	<td style="text-align: left;">
		<select id="methodcode">
			<%
			if(methodsList!=null && methodsList.size()>0){
				for(int i=0;i<methodsList.size();i++){
					SysMethodInfo sysMethod =methodsList.get(i);
			 %>
			 <option value="<%=sysMethod.getPrimaryKey() %>"><%=sysMethod.getMethodInfoName() %></option>
			 <%}} %>
		</select>
	</td>
	<th><em>*</em>检索标识</th>
	<td>
	<input type="text" id="findsign" must="检索标识不能为空" formust="findsignMust" value="" maxlength="20" size="35">
	<label id="findsignMust"></label>
	</td>
	</tr>
	<tr>
	<th><em>*</em>帮助标题</th>
	<td colspan="3" style="text-align: left;">
	<input type="text" id="helpTitle" must="帮助标题不能为空" formust="helpTitleMust" value="" style="width: 90%" maxlength="50">
	<br/>
	<label id="helpTitleMust"></label>
	</td>
	</tr>
	<tr>	
	<th><em>*</em>关键字</th>
	<td style="text-align: left" colspan="3">
	<input type="text" id="helpKeyword" must="关键字不能为空" formust="helpKeywordMust" value="" style="width: 90%;" maxlength="50">
	<br/>
	<label id="helpKeywordMust"></label>
	</td>
	</tr>
	<tr>
	<th><em>*</em>帮助内容</th>
	<td style="text-align: left" colspan="3">
	<label id="helpContextMust"></label><br/>
	<FCK:editor instanceName="helpContext" height="280" width="90%" ></FCK:editor>
	</td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>
<br/>
<br/>
<center>
<table>
<tr>
<td><btn:btn onclick="save();" value=" 确  定 " /></td>
<td style="width: 20px;"></td>
<td ><btn:btn onclick="reload();" value=" 返  回 "/></td>
</tr>
</table>
</center>
</body>
</html>