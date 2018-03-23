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

function chkClick(chk){
    if(chk.checked){
        chkAll () ;
        }
    else
    {
        unChkAll ();
    }
}
function chkAll (oSrc){
    oSrc = document.getElementsByName("all");
    if(oSrc!=null)
    {
        if(oSrc.length != null)
        {
            if (!oSrc.checked){
            oSrc.checked=true;
            }
        }
        for (i=oSrc.length-1;i>=0;i--){
            if (!oSrc[i].checked){
            oSrc[i].checked=true;
            }
        }
    }
}

function unChkAll (oSrc){
   oSrc =  document.getElementsByName("all");
    if(oSrc!=null)
    {
        if(oSrc.length == null)
       {
            if (oSrc.checked){
            oSrc.checked==true;
            }
        }
        for (i=oSrc.length-1;i>=0;i--){
            if (oSrc[i].checked){
           oSrc[i].checked=false;
            }
        }
    }
}

function allChke(){
  var all=document.getElementsByName("all");
     for(var i=0;i<all.length;i++){
     	 if(all[i].checked==true){
     	  document.getElementsByName("alltop")[0].checked=true;
     	 }
     }
       for(var i=0;i<all.length;i++){
     	 if(all[i].checked==false){
     	  document.getElementsByName("alltop")[0].checked=false;
     	 }
     }
}


function queryCallback(data){
    if(data.success == true){
		if(data.resultList.length > 0){
		     showPager(data.pager,"getAllPosts");	//显示分页
			//title
		     var titleDIV = document.getElementById("titleDIV");
	         titleDIV.innerHTML="<a class=ForumA href='versionManager_total.jsp'>论坛</a><FONT class=MenuPoint> ⇒ </FONT><a class=ForumA>"+data.resultList[0].forums.oaForumName+"</a>";
	        
	    	//operatorDIV
	         var operatorDIV = document.getElementById("operatorDIV");
	         operatorDIV.innerHTML = "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"setPosts('')\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/thumb_down.png'/>&nbsp;加精/取消&nbsp;</div>";
	         operatorDIV.innerHTML += "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"delet('')\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/close.png'/>&nbsp;批量删除&nbsp;</div>";
	    
	          
		     //detail
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		      var resultTable = document.createElement("table");
		      var tr2 = document.createElement("tr");
		         var td21 = document.createElement("td");
		         td21.style.cssText="text-align:center;";
		           	  td21.width= "1%";
		              td21.className="ForumPageTableTitle";
		              td21.innerHTML = "<input type=checkbox name=alltop onclick=chkClick(this);>";
		           tr2.appendChild(td21);
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
		           var td4 = document.createElement("td");
		           	  td4.width= "5%";
		              td4.className="ForumPageTableTitle";
		              td4.innerHTML = "发帖人";
		           tr2.appendChild(td4);
		           var td5 = document.createElement("td");
		           	  td5.width= "5%";
		              td5.className="ForumPageTableTitle";
		              td5.innerHTML = "点击数";
		           tr2.appendChild(td5);
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
		              var td8 = document.createElement("td");
		           	  td8.width= "5%";
		              td8.className="ForumPageTableTitle";
		              td8.innerHTML = " 操作";
		           tr2.appendChild(td8);
		           resultTable.appendChild(tr2);
//table detail
		       for ( var i = 0; i < data.resultList.length; i++) {
		          var tr1 = document.createElement("tr");
		          var td31 = document.createElement("td");
		          	td31.innerHTML = "<input type=checkbox name=all onclick=allChke() value="+data.resultList[i].primaryKey+">"
		          	td31.className="ForumTopicPageDataLine";
		          	tr1.appendChild(td31);
		         	 var td1 = document.createElement("td");
		         	 td1.style.cssText="text-align:center;";
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
		          	a1.href ="version_posts_reg.jsp?postsId="+data.resultList[i].primaryKey+"&forumId="+<%=forumid%>; 
		          	a1.innerHTML = data.resultList[i].oaPostName;
		          	
		          	td2.className="ForumTopicPageDataLine";
		        	td2.appendChild(a1);
		          	tr1.appendChild(td2);
		          	
		          	 var td3 = document.createElement("td");
		          	 td3.style.cssText="text-align:center;";
		          	td3.innerHTML = data.resultList[i].employee.hrmEmployeeName;
		          	td3.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td3);
		          	
		          	var td4 = document.createElement("td");
		          	td4.style.cssText="text-align:center;";
		          	td4.innerHTML = data.resultList[i].oaReadCount;
		          	td4.className="ForumPageTableDataLine";	
		          	tr1.appendChild(td4);
		          	
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
		       
		          	var td7 = document.createElement("td");
		        //  	td7.innerHTML = data.resultList[i].oaPostLastregter;
		          	   if(data.resultList[i].oaIsBoutique == <%=EnumUtil.OA_POSTS_IS_BOUTIQUE.UNBOUTIQUE.value%>){
					        td7.innerHTML = "<a href='javascript:void(0)' title='加精' onclick=\"setBoutique('"+data.resultList[i].primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/thumb_down.png' border='0'/></a>&nbsp;&nbsp";
					    }else{
					         td7.innerHTML = "<a href='javascript:void(0)' title='取消加精' onclick=\"setBoutique('"+data.resultList[i].primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/thumb_down.png' border='0' style='filter:gray;'/></a>&nbsp;&nbsp";
					    }
						td7.innerHTML += "<a href='javascript:void(0)' title='删除' onclick=\"del('"+data.resultList[i].primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
						td7.className="ForumTopicPageDataLine";	
			          	tr1.appendChild(td7);
		          	
		          	resultTable.appendChild(tr1);
		          
		     	}
		          resultTable.width = "100%";
		          resultTable.cellpadding="0";
		          resultTable.cellspacing="0";
		          resultDiv.appendChild(resultTable);
		     
		     titleDIV.outerHTML =  titleDIV.outerHTML;
		     operatorDIV.outerHTML =  operatorDIV.outerHTML;
		     resultDiv.outerHTML =  resultDiv.outerHTML;
		} else {
			showPager(data.pager,"getAllPosts");	//显示分页
			 //title
		     var titleDIV = document.getElementById("titleDIV");
	         titleDIV.innerHTML="<a class=ForumA href='versionManager_total.jsp'>论坛</a><FONT class=MenuPoint> ⇒ </FONT><a class=ForumA><%=URLDecoder.decode(forumName,"UTF-8") %></a>";
	      
	    	 //operatorDIV
	         var operatorDIV = document.getElementById("operatorDIV");
	         operatorDIV.innerHTML = "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"setPosts('')\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/thumb_down.png'/>&nbsp;加精/取消&nbsp;</div>";
	         operatorDIV.innerHTML += "<div style='margin:10px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'  onclick=\"delet('')\">&nbsp;<img class='grid_img'  src='<%=contextPath%>/images/grid_images/close.png'/>&nbsp;批量删除&nbsp;</div>";
	          
		     //detail
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		      var resultTable = document.createElement("table");
		      var tr2 = document.createElement("tr");
		         var td21 = document.createElement("td");
		         td21.style.cssText="text-align:center;";
		           	  td21.width= "1%";
		              td21.className="ForumPageTableTitle";
		              td21.innerHTML = "<input type=checkbox name=alltop onclick=chkClick(this);>";
		           tr2.appendChild(td21);
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
		           var td4 = document.createElement("td");
		           	  td4.width= "5%";
		              td4.className="ForumPageTableTitle";
		              td4.innerHTML = "发帖人";
		           tr2.appendChild(td4);
		           var td5 = document.createElement("td");
		           	  td5.width= "5%";
		              td5.className="ForumPageTableTitle";
		              td5.innerHTML = "点击数";
		           tr2.appendChild(td5);
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
		                var td8 = document.createElement("td");
		           	  td8.width= "5%";
		              td8.className="ForumPageTableTitle";
		              td8.innerHTML = " 操作";
		           tr2.appendChild(td8);
		           resultTable.appendChild(tr2);
		          var tr1 = document.createElement("tr");
		          var td1 = document.createElement("td");
		          td1.className="ForumPageTableDataLine";
		          td1.colSpan="8";
		          td1.innerHTML = "<b>无帖子</b>";
		          tr1.appendChild(td1);
		          resultTable.appendChild(tr1);
		           
		          resultTable.width = "100%";
		          resultTable.cellpadding="0";
		          resultTable.cellspacing="0";
		          resultDiv.appendChild(resultTable);
		     
		     titleDIV.outerHTML =  titleDIV.outerHTML;
		     operatorDIV.outerHTML =  operatorDIV.outerHTML;
		     resultDiv.outerHTML =  resultDiv.outerHTML;
		}
	}else{
		alertmsg(data.message);
	}
}

function  setRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaCommunicationService.setBoutiquePosts(recordsPks,boutiqueCallback);
}

function setBoutique(pk){
    confirmmsg("确定要设子此帖子吗?","setBoutOk("+pk+")");
}

//设置单个
function setBoutOk(pk){
   var ids = new Array();
   ids[0] = pk;
   dwrOaCommunicationService.setBoutiquePosts(ids,boutiqueCallback);
}

function boutiqueCallback(data){
    alertmsg(data,"getAllPosts()");
    ids= new Array();
}

//删除单个
function del(pk){
    confirmmsg("确定要删除此帖子吗?","delok("+pk+")");
}

function delok(id){
   var ids = new Array();
   ids[0] = id;
   dwrOaCommunicationService.deletePostsById(ids,deleCallback);
}

function deleCallback(data){
   alertmsg(data,"getAllPosts()");
   ids= new Array();
}
var ids = new Array();
function delet(){
  //针对多条记录进行操作
  var ckebox=document.getElementsByName("all");
  var c = 0;
  for(var i=0;i<ckebox.length;i++){
  		if(ckebox[i].checked==true){
  			ids[c]=ckebox[i].value;
  			c++;
  		}
  }
  	if(ids.length<=0){
  	   alertmsg("请选择要删除的帖子！");
  	}else{
       confirmmsg("确定要删除选中的帖子吗?","delRecord()");
	}
}
function delRecord(){
    dwrOaCommunicationService.deletePostsById(ids,deleCallback);
    ids = new Array();
}

function setPosts(){
    //针对多条记录进行操作
     var ckebox=document.getElementsByName("all");
      var c = 0;
	  for(var i=0;i<ckebox.length;i++){
	  		if(ckebox[i].checked==true){
	  			ids[c]=ckebox[i].value;
	  			c++;
	  		}
	  }
  	if(ids.length<=0){
  	   alertmsg("请选择要设置的帖子！");
  	}else{
    	confirmmsg("确定要设置选中的帖子吗?","setRecord()");
	}
}

function  setRecord(){
    dwrOaCommunicationService.setBoutiquePosts(ids,boutiqueCallback);
    ids = new Array();
}


</script>
</head>
<body class="inputcls">
	<div id="titleDIV" class=ForumTitle></div>
	<div id="operatorDIV"></div>
	<div style="clear:both;"></div>
	<div id="queryResult" class="ForumPageTableBorder"></div>
	<table align="right"  cellpadding="0" cellspacing="0" style="padding-right: 20px;">
    <tr >
    	<td class="show_table_page" >
   			<script type="text/javascript">pageSet("getAllPosts");</script>
    	</td>				    	
    </tr>
    </table>
</body>
</html>
