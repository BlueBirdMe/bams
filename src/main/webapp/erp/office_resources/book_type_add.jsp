<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增图书类别</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<%
     String type = request.getParameter("bookType");
 %>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput("helpTitle","新增/编辑图书类别，您可以在此处添加/修改图书类别，录入新图书时需要选择图书类别。");
		saveOrEdit();
			//第一个输入框获取焦点
		document.getElementById("oaBooktypeName").focus();
	}

function save(){
	var bl = validvalue('helpTitle');
	if(bl){
		if(DWRUtil.getValue("oaBooktypeId") != null && DWRUtil.getValue("oaBooktypeId") != ""){
			Btn.close();
		    dwrOfficeResourcesService.updateBooktype(getbooktype(),savecallback);
		}else{
			Btn.close();
		    dwrOfficeResourcesService.saveBooktype(getbooktype(),savecallback);
		}
	}
}

function saveOrEdit(){
    if(<%=type%> != null){
		var  id = <%=type%>;
		dwrOfficeResourcesService.getBooktypeByPk(id,setpagevalue);
    }else{
        //Btn.hidden("backbtn");
    }
}

function savecallback(data){
	Btn.open();
	if(DWRUtil.getValue("oaBooktypeId") != null && DWRUtil.getValue("oaBooktypeId") != ""){
		alertmsg(data, "canse();");
	}else {
		if(data.success){
			confirmmsgAndTitle("图书类别添加成功！是否想继续添加？","sevent();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}

function reloadpager(){
        if(<%=type%> != null){
        	window.parent.MoveDiv.close();
  			 window.parent.queryData();
              
        }else{
              sevent();
        }
}

function canse(){
	 window.parent.MoveDiv.close();
	 window.parent.queryData();
}

function reload(){
     Sys.load("<%=contextPath%>/erp/office_resources/book_type.jsp");
}

function sevent(){
	DWRUtil.setValue("oaBooktypeId","");
	DWRUtil.setValue("oaBooktypeName","");
	DWRUtil.setValue("oaBooktypeRemark","");
	refreshMDITab(<%=request.getParameter("tab")%>);
}


function getbooktype(){
	var booktype = new Object();
	booktype.primaryKey = DWRUtil.getValue("oaBooktypeId");
	booktype.oaBooktypeName = DWRUtil.getValue("oaBooktypeName");
	booktype.oaBooktypeRemark = DWRUtil.getValue("oaBooktypeRemark");
	return booktype;
}

function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+= "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}

function setpagevalue(data){
	if(data!=null){
		var booktype =data.resultList[0];
		DWRUtil.setValue("oaBooktypeId",booktype.primaryKey);
		DWRUtil.setValue("oaBooktypeName",booktype.oaBooktypeName);
		DWRUtil.setValue("oaBooktypeRemark",booktype.oaBooktypeRemark);
	}
}	

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
<input type="hidden" id="oaBooktypeId">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">添加/编辑图书类别</div>
	<div>
		<table class="inputtable" border="0">
		<tr>
			<th width="15%"><em>* </em>类别名称</th>
			<td width="40%" style="text-align: left;"><input type="text" id="oaBooktypeName" must="类型名称不能为空。" formust="oaBooktypeNameMust" value="" maxlength="10" ></td>
			<td width="40%"><label id="oaBooktypeNameMust"></label></td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
			<textarea id="oaBooktypeRemark" style="width: 90%"></textarea>
			</td>
			</tr>
		</table>
		<br/>
	</div>
</div>
<br/>
<table cellpadding="0" cellspacing="0" align="center">
	<tr>
	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
	<td width="10%"></td>
	<td id="backbtn">
	<%if (type == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
	</tr>
</table>
</body>

</html>