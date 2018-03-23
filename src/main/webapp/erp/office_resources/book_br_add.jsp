<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String bookbrpk =request.getParameter("bookbrpk");
 %>
<title>出借图书</title>
<style type="text/css">
	body {
	background-color: #EDF5FA;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","出借图书，您可以在此处录入图书出借的信息。 借书者可以是内部人员和外部人员选填一个。");
}


//返回借书列表
function backToBorrowList(){
	window.location = "book_history.jsp";
}

function save(){ 
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaBookNameMust";
	warnArr[1] = "oaBookbrLendn_sMust";
	warnArr[2] = "oaBrLendwMust";
	//清空所有信息提示
	warnInit(warnArr);
	
	if(DWRUtil.getValue("oaBookid") == "" || DWRUtil.getValue("oaBookid") == null){
		     setMustWarn("oaBookNameMust","图书名称不能为空！");
		     return false;
	}
	
	var mc = DWRUtil.getValue("booksCount");
   	var bc = DWRUtil.getValue("oaBrCount");
   	if(parseFloat(bc)<=0||parseInt(bc)>parseFloat(mc)){
   	 	setMustWarn("oaBrCountMust","借用数量输入错误!");
   	 	return;
   	 }
	
	var bl = validvalue('helpTitle');
	if(bl){
		if(DWRUtil.getValue("oaBookbrLendn_s") == "" && trim(DWRUtil.getValue("oaBrLendw")) == ""){
		     //DWRUtil.setValue("bookbr","借书人(内部)/(外部)至少一个不能为空！");
		     setMustWarn("oaBookbrLendn_sMust","借书人(内部)/(外部)至少一个不能为空！");
		     setMustWarn("oaBrLendwMust","借书人(内部)/(外部)至少一个不能为空！");
		     return false;
		}else if(DWRUtil.getValue("oaBookbrLendn_s") != "" && trim(DWRUtil.getValue("oaBrLendw")) != ""){
		     //DWRUtil.setValue("bookbr","借书人(内部)/(外部)至多一种！");
		     setMustWarn("oaBookbrLendn_sMust","借书人(内部)/(外部)至多一种！");
		     setMustWarn("oaBrLendwMust","借书人(内部)/(外部)至多一种！");
		     return false;
		}else{
			Btn.close();
		    dwrOfficeResourcesService.saveBookbr(getBookbrinfo(),saveCallback);
		}
	}
}
	
function saveCallback(data){
	Btn.open();
	if(data.success){
		confirmmsgAndTitle("添加出借图书信息成功！是否想继续添加？","reSet();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}	

function reSet(){
	if(<%=bookbrpk%> != null){
          reload();
    }else{
     DWRUtil.setValue("oaBookid","");
     DWRUtil.setValue("oaBookbrLendn_s","");
     DWRUtil.setValue("oaBrLendw","");
     DWRUtil.setValue("oaBrBdate","");
     DWRUtil.setValue("oaBrRdate","");
     DWRUtil.setValue("oaBrCount","");
     DWRUtil.setValue("oaBookbrRemark","");
     DWRUtil.setValue("oaBookbrLendn","");
     DWRUtil.setValue("oaBookName","");
     }
}

function getBookbrinfo(){
     var bookbr = new Object();
     bookbr.oaBrBookid = DWRUtil.getValue("oaBookid");
     bookbr.oaBrLendn = DWRUtil.getValue("oaBookbrLendn_s");
     bookbr.oaBrLendw = DWRUtil.getValue("oaBrLendw");
     bookbr.oaBrBdate = DWRUtil.getValue("oaBrBdate");
     bookbr.oaBrRdate = DWRUtil.getValue("oaBrRdate");
     bookbr.oaBrCount = DWRUtil.getValue("oaBrCount");
     bookbr.oaBrRemark = DWRUtil.getValue("oaBookbrRemark");
     return bookbr;
}
	
function selectEmps(){
	if(<%=bookbrpk%> != null){
		var box = SEL.getEmployeeIds("radio","oaBookbrLendn","oaBookbrLendn_s","processloadfrm");
		box.show();
	}else{
		var box = SEL.getEmployeeIds("radio","oaBookbrLendn","oaBookbrLendn_s");
		box.show();
	}
	
}

function selectBook(){
	if(<%=bookbrpk%> != null){
		var box = SEL.getBookIds("radio","oaBookName","oaBookid","processloadfrm","getMaxCount()");
		box.show();
	}else{
		var box = SEL.getBookIds("radio","oaBookName","oaBookid","undefined","getMaxCount()");
		box.show();
	}
}

function getMaxCount(){
	var id = document.getElementById("oaBookid").value;
	if(id!=null&&id.length>0){
		dwrOfficeResourcesService.getBookByPk(id,function(data){
			if(data.success&&data.resultList.length>0){
				var mc = data.resultList[0].oaBookRemain;
				DWRUtil.setValue("booksCount",mc);
				document.getElementById("maxcount").innerHTML ="<li>可申请最大数量为:&nbsp;&nbsp;<font color='red'>"+mc+"</font>&nbsp;&nbsp;本。</li>";
			}
		});
	}
}

function setMaxCountMsg(){
	var id = document.getElementById("oaBookid").value;
	if(id==null||id.length==0){
		document.getElementById("maxcount").innerHTML ="<li>请选择要借用的图书再输入数量</li>";
		DWRUtil.setValue("booksCount",0);
	}
}

function reload(){
    window.parent.MoveDiv.close();
    window.parent.queryData();
}

function closePage(){
		closeMDITab();
}
</script>
</head>
<body class="inputcls">
<input type="hidden" id="booksCount" value="0">
	<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">出借图书</div>
	<div>
		<table class="inputtable" border="0">
		<tr>
			<th width="15%"><em>* </em>图书名称</th>
			<td width="40%" colspan="2">
				<input id="oaBookName"  readonly="readonly" linkclear="oaBookid" onclick="selectBook()" style="color: #999;" class="takeform"/>
				<input type="hidden" id="oaBookid">
			</td>
			<td width="30%"><label id="oaBookNameMust"></label></td>
		</tr>
		<tr>
			<th>
				<em>* </em>数&nbsp;量
			</th>
			<td>
				<input type="text" id="oaBrCount" must="借书数量不能为空！"
					class="numform" formust="oaBrCountMust" maxlength="4" onfocus="setMaxCountMsg()">
				
			</td>
			<td>
					<ul style="color: #808080;line-height: 24px;margin-top: 0;margin-bottom: 0" id="maxcount">
					</ul>
				</td>
			<td width="30%"><label id="oaBrCountMust"></label></td>
		</tr>
		<tr>
			<th>
				<em>* </em>借书日期
			</th>
			<td style="text-align: left">
				<input type="text" id="oaBrBdate" must="借书时间不能为空！"
					readonly="readonly" class="Wdate" onClick="WdatePicker()" formust="oaBrBdateMust">
			</td>
			<td width="40%"><label id="oaBrBdateMust"></label></td>
		</tr>
			<tr>
				<th>
					<em>* </em>还书日期
				</th>
				<td style="text-align: left">
					<input type="text" id="oaBrRdate" must="还书时间不能为空！"
						readonly="readonly" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'oaBrBdate\')}'})" formust="oaBrRdateMust">
				</td>
				<td width="40%"><label id="oaBrRdateMust"></label></td>
			</tr>
			
			<tr>
				<th>
					<span style="color:blue">•</span>&nbsp;&nbsp;借书人（内部）
				</th>
				<td style="text-align: left;">
					<input type="text" id="oaBookbrLendn" class="takeform"
						readonly="readonly" onclick="selectEmps()"
						style="color: #999" linkclear="oaBookbrLendn_s">
					<input type="hidden" id="oaBookbrLendn_s">
				</td>
				<td width="40%"><label id="oaBookbrLendn_sMust"></label></td>
				</tr>
				<tr>
				<th>
					<span style="color:blue">•</span>&nbsp;&nbsp;借书人（外部）
				</th>
				<td style="text-align: left;">
					<input type="text" id="oaBrLendw" maxlength="10">
				</td>
				<td width="40%"><label id="oaBrLendwMust"></label></td>
			</tr>
			<tr>
				<th>
					备&nbsp;注
				</th>
				<td style="text-align: left" colspan="3" >
					<textarea  style="height: 150px;"  id="oaBookbrRemark"></textarea>
				</td>
			</tr>
		 </table>
		<br/>
	</div>
</div>
<br/>
<table align="center">
   	<tr>
     <td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
     <td style="width: 10px;"></td>
     <td>
	<DIV id ="backToList"><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></DIV>
	</td>
   	</tr>
</table>
</body>
</html>