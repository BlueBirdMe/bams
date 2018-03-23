<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@page import="java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>论坛讨论</title>
		<%
		    String  forumid = request.getParameter("forumId");
			String forumName = request.getParameter("forumName");
		 %>
<script>
window.onload=function(){
    getAllPosts("null");
}

function pageSet(methodName){
	var pagerData = "共&nbsp;<label id='rowCount'>0</label>&nbsp;条&nbsp;&nbsp;&nbsp;";
	pagerData += "第&nbsp;<label id='currentPage'>0</label>&nbsp;页/";
	pagerData += "共&nbsp;<label id='pageCount'>0</label>&nbsp;页&nbsp;&nbsp;&nbsp;";
	pagerData += "<span id='pagerTool'></span>";
	document.write(pagerData);
}


//获取当前分页信息
function getPager(method,size){
	var pager = new Object();
	pager.pageSize = size;
	pager.pageMethod = method;

	if(method == 'go'){
		pager.currentPage = document.getElementById("gotoPage").value;
	}else{
		pager.currentPage = document.getElementById("currentPage").innerHTML;
	}
	pager.totalPages = document.getElementById("pageCount").innerHTML;
	
	return pager;
}

//显示分页信息
function showPager(pager,methodName){
	document.getElementById("currentPage").innerHTML=pager.currentPage;
	document.getElementById("pageCount").innerHTML=pager.totalPages;
	document.getElementById("rowCount").innerHTML=pager.totalRows;

	var pagerTool = "";
	if(parseInt(pager.totalPages) == 1){
		pagerTool = "首页&nbsp;上一页&nbsp;下一页&nbsp;尾页&nbsp;转到&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;GO&nbsp;&nbsp;&nbsp;";
	}else if(parseInt(pager.currentPage) == 1){
		pagerTool += "<label>首页</label>&nbsp;";
		pagerTool += "<label>上一页</label>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('next');"+"\" style=\"color:black\">下一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('last');"+"\" style=\"color:black\">尾页</a>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;&nbsp;&nbsp;";
	}else if(parseInt(pager.currentPage) == parseInt(pager.totalPages)){
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('first');\" style=\"color:black\">首页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('previous');"+"\" style=\"color:black\">上一页</a>&nbsp;";
		pagerTool += "<label>下一页</label>&nbsp;";
		pagerTool += "<label>尾页</label>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;&nbsp;&nbsp;";
	}else{
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('first');\" style=\"color:black\">首页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('previous');"+"\" style=\"color:black\">上一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('next');"+"\" style=\"color:black\">下一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('last');"+"\" style=\"color:black\">尾页</a>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;&nbsp;&nbsp;";
	}
	
	document.getElementById("pagerTool").innerHTML = pagerTool;
}

function getAllPosts(method){
    
	var posts = new Object();
	var pager = getPager(method,15);
	if(<%=forumid%> != null){
	     posts.oaPostForum = <%=forumid%>;
	}
	
	dwrOaCommunicationService.listPosts(posts,pager,queryCallback);
}
function loadTitle(){
   if(<%=forumid%> != null){
       var  id = <%=forumid%>;
       dwrOaCommunicationService.getForumByid(id,setTableTitle);
   }
}

function getQueryParam1(){
	var names = new Array();
	var values = new Array();
	return createObject(names,values);
}

function add() {
	var url = '<%=contextPath%>/erp/communication/posts_add.jsp?flag=false&fid=<%=forumid%>';
	openMDITab(url + "&tab="+getMDITab());
}

function queryCallback(data){
    if(data.success == true){
    	if(data.resultList.length > 0){	
		     showPager(data.pager,"getAllPosts");	//显示分页
			 //title
		     var titleDIV = document.getElementById("titleDIV");
	         titleDIV.innerHTML="<a class=ForumA href='communications_total.jsp'>论坛</a><FONT class=MenuPoint> ⇒ </FONT><a class=ForumA>"+data.resultList[0].forums.oaForumName+"</a>";
	          
	         //operatorDIV
	         var operatorDIV = document.getElementById("operatorDIV");
		     operatorDIV.innerHTML = "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"add()\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/add.png'/>&nbsp;发帖&nbsp;</div>";
		     
		     //detail
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		      var resultTable = document.createElement("table");
		      var tr2 = document.createElement("tr");
		           var td2 = document.createElement("td");
		           	  td2.width= "5%";
		              td2.className="ForumPageTableTitle";
		              td2.innerHTML = "帖子类型";
		           tr2.appendChild(td2);
		           var td3 = document.createElement("td");
		           	  td3.width= "30%";
		              td3.className="ForumPageTableTitle";
		              td3.innerHTML = "标题";
		           tr2.appendChild(td3);
		           var td5 = document.createElement("td");
		           	  td5.width= "5%";
		              td5.className="ForumPageTableTitle";
		              td5.innerHTML = "点击数";
		           tr2.appendChild(td5);
		           var td4 = document.createElement("td");
		           	  td4.width= "5%";
		              td4.className="ForumPageTableTitle";
		              td4.innerHTML = "发帖人";
		           tr2.appendChild(td4);
		           var td6 = document.createElement("td");
		           	  td6.width= "8%";
		              td6.className="ForumPageTableTitle";
		              td6.innerHTML = "最后回复人";
		           tr2.appendChild(td6);
		            var td7 = document.createElement("td");
		           	  td7.width= "15%";
		              td7.className="ForumPageTableTitle";
		              td7.innerHTML = "最后回复时间";
		           tr2.appendChild(td7);
		           resultTable.appendChild(tr2);
		           
//table detail
			  
		       for ( var i = 0; i < data.resultList.length; i++) {
		          var tr1 = document.createElement("tr");
		         	 var td1 = document.createElement("td");
					if(data.resultList[i].oaIsBoutique ==<%=EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value%>){
						td1.innerHTML= "<img src='<%=contextPath%>/images/grid_images/forum_top.gif'/>&nbsp;<font style='color:red'><%=EnumUtil.OA_POSTS_IS_BOUTIQUE.valueOf(EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value)%></font>";
					}else{
						td1.innerHTML= "<img src='<%=contextPath%>/images/grid_images/forum_comm.gif'/>&nbsp;<font style='color:#8E8E8E'><%=EnumUtil.OA_POSTS_IS_BOUTIQUE.valueOf(EnumUtil.OA_POSTS_IS_BOUTIQUE.UNBOUTIQUE.value)%></font>";
					}	
						
					td1.className="ForumTopicPageDataLine";	
		          	tr1.appendChild(td1);
		          	var td2 = document.createElement("td");
		          	var a1 = document.createElement("a");
		          	a1.className = "ForumTitleA";
		          	a1.href ="posts_reg.jsp?postsId="+data.resultList[i].primaryKey+"&forumId="+<%=forumid%>; 
		          	a1.innerHTML = data.resultList[i].oaPostName;
		          	
		          	td2.className="ForumTopicPageDataLine";
		        	td2.appendChild(a1);
		          	tr1.appendChild(td2);
		          
		          	var td4 = document.createElement("td");
		          	td4.style.cssText="text-align:center;";
		          	td4.innerHTML = data.resultList[i].oaReadCount;
		          	td4.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td4);
		          		
		          	 var td3 = document.createElement("td");
		          	 td3.style.cssText="text-align:center;";
		          	td3.innerHTML = data.resultList[i].employee.hrmEmployeeName;
		          	td3.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td3);
		          	
		          	var td5 = document.createElement("td");
		          	td5.style.cssText="text-align:center;";
		          	td5.innerHTML = data.resultList[i].oaLastRegEmp;
		          	td5.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td5);	
		          		
		          	tr1.appendChild(td5);    
		          	var td6 = document.createElement("td");
		          	td6.style.cssText="text-align:center;";
		          	td6.innerHTML = data.resultList[i].oaPostLastregter;
		          	td6.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td6);
		          	resultTable.appendChild(tr1);
		          
		     	}
	  } else {
	  		showPager(data.pager,"getAllPosts");	//显示分页
			//title
		     var titleDIV = document.getElementById("titleDIV");
	         titleDIV.innerHTML="<a class=ForumA href='communications_total.jsp'>论坛</a><FONT class=MenuPoint> ⇒ </FONT><a class=ForumA><%=URLDecoder.decode(forumName,"UTF-8") %></a>";
	          
	         //operatorDIV
	         var operatorDIV = document.getElementById("operatorDIV");
		     operatorDIV.innerHTML = "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"add('')\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/add.png'/>&nbsp;发帖&nbsp;</div>";
        
		     //detail
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		      var resultTable = document.createElement("table");
		      var tr2 = document.createElement("tr");
		           var td2 = document.createElement("td");
		           	  td2.width= "5%";
		              td2.className="ForumPageTableTitle";
		              td2.innerHTML = "帖子类型";
		           tr2.appendChild(td2);
		           var td3 = document.createElement("td");
		           	  td3.width= "30%";
		              td3.className="ForumPageTableTitle";
		              td3.innerHTML = "标题";
		           tr2.appendChild(td3);
		           var td5 = document.createElement("td");
		           	  td5.width= "5%";
		              td5.className="ForumPageTableTitle";
		              td5.innerHTML = "点击数";
		           tr2.appendChild(td5);
		           var td4 = document.createElement("td");
		           	  td4.width= "5%";
		              td4.className="ForumPageTableTitle";
		              td4.innerHTML = "发帖人";
		           tr2.appendChild(td4);
		           var td6 = document.createElement("td");
		           	  td6.width= "8%";
		              td6.className="ForumPageTableTitle";
		              td6.innerHTML = "最后回复人";
		           tr2.appendChild(td6);
		            var td7 = document.createElement("td");
		           	  td7.width= "15%";
		              td7.className="ForumPageTableTitle";
		              td7.innerHTML = "最后回复时间";
		           tr2.appendChild(td7);
		           resultTable.appendChild(tr2);
		           var tr1 = document.createElement("tr");
		          var td1 = document.createElement("td");
		          td1.className="ForumPageTableDataLine";
		          td1.colSpan="6";
		          td1.innerHTML = "<b>无帖子</b>";
		          tr1.appendChild(td1);
		          resultTable.appendChild(tr1);
	  }
  		      resultTable.width = "100%";
	          resultTable.cellpadding="0";
	          resultTable.cellspacing="0";
	          resultDiv.appendChild(resultTable);
		     
		     titleDIV.outerHTML =  titleDIV.outerHTML;
		     operatorDIV.outerHTML =  operatorDIV.outerHTML;
		     resultDiv.outerHTML =  resultDiv.outerHTML;
	}else{
		alertmsg(data.message);
	}
}


</script>
</head>
<body class="inputcls" style="overflow:hidden">
	<div id="titleDIV" class=ForumTitle></div>
	<div id="operatorDIV"></div>
	<div style="clear:both;"></div>
	<div id="queryResult" class="ForumPageTableBorder"></div>
	<table align="right"  cellpadding="0" cellspacing="0" style="padding-right:20px;">
   <tr>
   	<td class="show_table_page" >
  		<script type="text/javascript">pageSet("getAllPosts");</script>
   	</td>				    	
   </tr>
   </table>
</body>
</html>
