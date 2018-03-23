<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>期刊类型</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<% String id=request.getParameter("fid"); %>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
   	document.getElementById("journame").focus();
   	if(<%=id%>!=null && <%=id%>!='undefined'){
	dwrOACompanyResourcesService.getJournalsByPk(<%=id%>,setpagevalue);
	}
	initInput('title',"您可以在此处添加期刊类型，以便以后添加该类型的期刊！");
}
	 
function save(){
	var bl = validvalue('title');
	if(bl){
		var type =getjourType();
		dwrOACompanyResourcesService.saveJournalsType(type,savecallback);
		Btn.close();
	}
}
function savecallback(data){
	Btn.open();
	if(<%=id%>!=null){
		alertmsg(data,"reclose()");
	}else{
		if(data.success){
			confirmmsgAndTitle("添加期刊类型成功！是否想继续添加?","sevent();","继续添加","closePage();","关闭页面");
  	  	}else{
	     	alertmsg(data);
		}
	}
}
function reclose(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function sevent(){
	DWRUtil.setValue("typeid","");
	DWRUtil.setValue("journame","");
	DWRUtil.setValue("jourpress","");
	DWRUtil.setValue("jourremark","");
	document.getElementById("journame").focus();
	refreshMDITab(<%=request.getParameter("tab")%>);
}
function getjourType(){
	var type = new Object();
	type.primaryKey = DWRUtil.getValue("typeid");
	type.journalsTypeName = DWRUtil.getValue("journame");
	type.journalsTypePress = DWRUtil.getValue("jourpress");
	type.journalsTypeProper = DWRUtil.getValue("jourremark");
	return type;
}
function setpagevalue(data){
	if(data!=null){
		var type =data.resultList[0];
		DWRUtil.setValue("typeid",type.primaryKey);
		DWRUtil.setValue("journame",type.journalsTypeName);
		DWRUtil.setValue("jourpress",type.journalsTypePress);
		DWRUtil.setValue("jourremark",type.journalsTypeProper);
	}
}
	
function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}	
	
</script>
</head>
<body class="inputcls">
	<input type="hidden" id="typeid">
				<div class="formDetail">
					<div class="requdiv"><label id="title"></label></div>
					<div class="formTitle">新增/编辑期刊类型</div>
						<div>
							<table class="inputtable">
							<tr>
								<th width="15%"><em>* </em>类型名称</th>
								<td>
								<input type="text" id="journame" must="类型名称不能为空" formust="journamemust" maxlength="100" 
								size="35">
								<label id="journamemust"></label>
								</td>
							</tr>
							<tr>
								<th>出版社</th>
								<td><input type="text" id="jourpress"  maxlength="100" size="35">
								</td>
							</tr>
							<tr>
								<th>附加说明</th>
								<td colspan="3">
								<textarea id="jourremark" style="width: 90%"></textarea>
								</td>
							</tr>
							<tr>
								<th></th>
								<td colspan="3">
								<div style="width: 90%;text-align: right;padding: 10px;padding-right: 0px;">
								<table align="center"><tr>
						    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    							<td style="width: 10px;"></td>
    							<td>
								<%if (id == null){ %>
								<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
								<%}else{ %>
								<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
								<%} %>
								</td>
							</tr>
							</table>
						</div>
					</td>
					</tr>
					</table>
					</div>
				</div>
			<br/>
	
</body>
</html>