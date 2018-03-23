<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String bookpk =request.getParameter("bookpk");
String isedit = "false";
if(bookpk != null){
   isedit = "true";
}
 %>
<title>新增图书</title>
<style type="text/css">
	body {
	background-color:#EDF5FA;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","新增图书，您可以在此处添加新的图书，添加后可以给员工借阅。");
	saveOrEdit();
	//第一个输入框获取焦点
	document.getElementById("oaBookName").focus();
}

function save(){ 
    var bl = validvalue('helpTitle');
    if(bl){
       	Btn.close();
        dwrOfficeResourcesService.saveBook(getBookinfo(),saveCallback);
	}
}

function updateBook(){
    dwrOfficeResourcesService.updateBook(getBookinfo(),saveCallback);
}

function saveCallback(data){
	Btn.open();
	if(<%=bookpk%> != null){
		alertmsg(data, "reloadPager();");
	}else{
		if(data.success){
			confirmmsgAndTitle("新增图书成功！是否想继续添加？","reloadPager();","继续添加","closePage();","关闭页面");
		}else{
			alertmsg(data);
		}
	}
}	
 
function reloadPager(){
    if(<%=bookpk%> != null){
         window.parent.MoveDiv.close();
   		 window.parent.queryData();
    }else{
         DWRUtil.setValue("oaBookName","");
	     DWRUtil.setValue("oaBookDep_s","");
	     DWRUtil.setValue("oaBookDep","");
	     DWRUtil.setValue("oaBookCode","");
	     DWRUtil.setValue("oaBookAuthor","");
	     DWRUtil.setValue("oaBookIsbn","");
	     DWRUtil.setValue("oaBookConcern","");
	     DWRUtil.setValue("oaPublishDate","");
	     DWRUtil.setValue("oaBookAddress","");
	     DWRUtil.setValue("oaBookCount","");
	     DWRUtil.setValue("oaBookRemain","");
	     DWRUtil.setValue("oaBookPrice","");
	     DWRUtil.setValue("oaBuyDate","");
	     DWRUtil.setValue("oaRegistyDate","");
	     DWRUtil.setValue("oaBookSynopsis","");
	     DWRUtil.setValue("oaBookRemark","");
	     //刷新附件
	     Sys.setFilevalue("oaBookAcce","");
	     document.getElementById("oaBookType").selectedIndex = 0;
	     document.getElementById("oaBookName").focus();
	     refreshMDITab(<%=request.getParameter("tab")%>);     
    }
}
 
function getBookinfo(){
	    var book = new Object();
	    if(<%=bookpk%> != null){
	      book.primaryKey = <%=bookpk%>;
	    }	
	    book.oaBookName = DWRUtil.getValue("oaBookName");
	    book.oaBookDep = DWRUtil.getValue("oaBookDep_s");
	    book.oaBookCode = DWRUtil.getValue("oaBookCode");
	    book.oaBookType = DWRUtil.getValue("oaBookType");
	    book.oaBookAuthor = DWRUtil.getValue("oaBookAuthor");
	    book.oaBookIsbn = DWRUtil.getValue("oaBookIsbn");
	    book.oaBookConcern = DWRUtil.getValue("oaBookConcern");
	    book.oaPublishDate = DWRUtil.getValue("oaPublishDate");
	    book.oaBookAddress = DWRUtil.getValue("oaBookAddress");
	    book.oaBookCount = DWRUtil.getValue("oaBookCount");
	    book.oaBookRemain = DWRUtil.getValue("oaBookRemain");
	    book.oaBookPrice = DWRUtil.getValue("oaBookPrice");
	    book.oaBuyDate = DWRUtil.getValue("oaBuyDate");
	    book.oaRegistyDate = DWRUtil.getValue("oaRegistyDate");
	    book.oaBookSynopsis = DWRUtil.getValue("oaBookSynopsis");
	    book.oaBookRemark = DWRUtil.getValue("oaBookRemark");
	    book.oaBookAcce = DWRUtil.getValue("oaBookAcce");
	    return book;
}

function saveOrEdit(){
      if(<%=bookpk%> != null){
		var bookprimaryKey = <%=bookpk%>;
		dwrOfficeResourcesService.getBookByPk(bookprimaryKey,setBookinfo);
	 }
}
  
function setBookinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var book = data.resultList[0];
 		 DWRUtil.setValue("oaBookName",book.oaBookName);
	     DWRUtil.setValue("oaBookDep_s",book.oaBookDep);
	     DWRUtil.setValue("oaBookDep",book.department.hrmDepName);
	     DWRUtil.setValue("oaBookCode",book.oaBookCode);
	     DWRUtil.setValue("oaBookType",book.oaBookType);
	     DWRUtil.setValue("oaBookAuthor",book.oaBookAuthor);
	     DWRUtil.setValue("oaBookIsbn",book.oaBookIsbn);
	     DWRUtil.setValue("oaBookConcern",book.oaBookConcern);
	     DWRUtil.setValue("oaPublishDate",book.oaPublishDate);
	     DWRUtil.setValue("oaBookAddress",book.oaBookAddress);
	     DWRUtil.setValue("oaBookCount",book.oaBookCount);
	     DWRUtil.setValue("oaBookRemain",book.oaBookRemain);
	     DWRUtil.setValue("oaBookPrice",book.oaBookPrice);
	     DWRUtil.setValue("oaBuyDate",book.oaBuyDate);
	     DWRUtil.setValue("oaRegistyDate",book.oaRegistyDate);
	     DWRUtil.setValue("oaBookSynopsis",book.oaBookSynopsis);
	     DWRUtil.setValue("oaBookRemark",book.oaBookRemark);
         //编辑时放入附件
         if(book.oaBookAcce != null && book.oaBookAcce  != undefined && book.oaBookAcce .length > 0){
					dwrCommonService.getAttachmentInfoListToString(book.oaBookAcce,setaccept);
		 }
 		}else{
 			alertmsg(data.message);
 		}
 	}else{
 		alertmsg(data.message);
 	}
 }

//放入附件
function setaccept(data){
	Sys.setFilevalue("oaBookAcce",data);
}

function selectDeps(){
	var box = SEL.getDeptIds("radio","oaBookDep","oaBookDep_s");
	box.show();
}
   
function  reload(){
	window.parent.MoveDiv.close();
    window.parent.queryData();
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}
</script>
</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">新增/编辑图书</div>
		<div>
			<table class="inputtable" border="0">
			<tr>
				<th><em>* </em>图书名称</th>
				<td style="text-align: left;"><input type="text" id="oaBookName" must="图书名称不能为空！" maxlength="30" formust="oaBookNameMust">
				<label id="oaBookNameMust"></label>
				</td>
				<th><em>* </em>所属部门</th>
				<td style="text-align: left" colspan="3">
				<input type="text" id="oaBookDep" class="takeform" must="所属部门不能为空！" formust="oaBookDepMust" readonly="readonly" linkclear="oaBookDep_s" onclick="selectDeps()" style="color: #999">
				<input type="hidden" id="oaBookDep_s">
				<label id="oaBookDepMust"></label>
				</td>
				</tr>
				<tr>
				<th>类别</th>
				<td style="text-align: left" ><select id= "oaBookType" must="图书类型不能为空！"><%=UtilTool.getBookTypeSelectOptions(this.getServletContext(),request,null)%></select></td>
				<th><em>* </em>总数量</th><td ><input type="text" id="oaBookCount" must="图书数量不能为空！" formust="oaBookCountMust" maxlength="4" class="numform">
				<label id="oaBookCountMust"></label>
				</td>
				</tr>
				<tr>
				<th>ISBN号</th>
				<td style="text-align: left;"><input type="text" id="oaBookIsbn" maxlength="20"></td>	
				<th>单价</th>
				<td style="text-align: left;"><input type="text" class="rmbform" maxlength="6" id="oaBookPrice">&nbsp;元</td>	
				</tr>
					
				<tr>
				<th>图书编号</th>
				<td style="text-align: left;"><input type="text" id="oaBookCode" maxlength="20"></td>	
				
				<th>出版社</th>
				<td style="text-align: left;"><input type="text" id="oaBookConcern" maxlength="20"></td>
				</tr>
				<tr>
				<th>作者</th>
				<td style="text-align: left;"><input type="text" id="oaBookAuthor" maxlength="10"></td>
				</tr>
				<tr>
				<th>出版日期</th>
				<td style="text-align: left">
				<input type="text" id="oaPublishDate" readonly="readonly" class="Wdate" onClick="WdatePicker()">
				</td>
				<th>购买日期</th>
				<td style="text-align: left">
				<input type="text" id="oaBuyDate" readonly="readonly" class="Wdate" onClick="WdatePicker()" >
				</td>
				</tr>
				<tr>
				<th>存放地点</th>
				<td colspan="3" width="100%"><input type="text" id="oaBookAddress" maxlength="50"  style="width: 90%"></td>		
				</tr>
				<tr>
				<th>附件</th>
				<td  colspan="3">
				<file:multifileupload width="90%" acceptTextId="oaBookAcce" height="100" ext="txt|chm|ppt|pdf|doc|docx" edit="<%=isedit%>"></file:multifileupload>
				</td>
				</tr>
				<tr>
				<th>内容简介</th>
				<td style="text-align: left;" colspan="3"><textarea style="height:200px;" id="oaBookSynopsis"></textarea></td>
				</tr>
				<tr>
				<th>备注</th>
				<td style="text-align: left" colspan="3"><textarea rows="4" style="height:200px;" cols="6" id="oaBookRemark"></textarea></td>
			</tr>
			</table>
		</div>
		<br>
	</div>

	<br/>
	<table align="center">
   	<tr>
     <td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
     <td style="width: 20%;"></td>
     <td id="backbtn">
	 <%if (bookpk == null){ %>
	 <btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	 <%}else{ %>
	 <btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	 <%} %>
	</td>
     
     
   	</tr>
	</table>
</body>
</html>