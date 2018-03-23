<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>帖子查看/回复</title>
		<%
		    String  postsid = request.getParameter("postsId");
		%>
<script>
window.onload=function(){
    var id = <%=Integer.parseInt(postsid)%>;
    queryData(id);
    getAllQuestion("null");
}

function queryData(pk){
	dwrOaCommunicationService.getPostsByid(pk,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		if(data.resultList.length > 0){
		   dwrOaCommunicationService.getForumByid(data.resultList[0].oaPostForum,updateTitle);
		   document.getElementById("poststitle").innerHTML = "帖子标题："+data.resultList[0].oaPostName;
		   document.getElementById("postsName").innerHTML = data.resultList[0].oaPostName;
		}
	}else{
		alertmsg(data.message);
	}
}

function updateTitle(data){
    if(data.success == true){
		if(data.resultList.length > 0){
		    var title = document.getElementById("poststitle").value;
		    document.getElementById("poststitle").value =data.resultList[0].oaForumName+" 版块中 "+title;
		    //var obj = document.getElementById("poststitle");
		    //obj.value=data.resultList[0].oaForumName+" 版块中 "+title;
		    
		}else{
		     alertmsg(data.message);
		}
	}else{
		alertmsg(data.message);
	}
}

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	window.status = editorInstance.Description;
}

function getRegInfo(){
    var postspk = <%=Integer.parseInt(postsid)%>;
	
	var regPostsInfo = new Object();
	regPostsInfo.oaPostReg = postspk;
	regPostsInfo.oaPostText = fck.GetXHTML();
	
	return regPostsInfo;
}

function saveReg(){
    var str = fck.GetXHTML();
   	if(str==""){
   		fck.SetHTML("");
   		alertmsg("帖子内容不允许为空！");
   		return false;
   	}else if(str.length < 8){
   		alertmsg("帖子内容字数不得少于8个！");
   		return false;
   	}else{
   	    dwrOaCommunicationService.savePostsRegInfo(getRegInfo(),saveCallback);
   	}
}

function saveCallback(data){
	if(data.success == true){
		alertmsg("回复成功！");
		fck.SetHTML("");
		getAllQuestion("null");
	}else{
		alertmsg(data.message);
	}
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
		pagerTool = "首页&nbsp;上一页&nbsp;下一页&nbsp;尾页&nbsp;转到&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;GO&nbsp;";
	}else if(parseInt(pager.currentPage) == 1){
		pagerTool += "<label>首页</label>&nbsp;";
		pagerTool += "<label>上一页</label>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('next');"+"\" style=\"color:black\">下一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('last');"+"\" style=\"color:black\">尾页</a>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;";
	}else if(parseInt(pager.currentPage) == parseInt(pager.totalPages)){
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('first');\" style=\"color:black\">首页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('previous');"+"\" style=\"color:black\">上一页</a>&nbsp;";
		pagerTool += "<label>下一页</label>&nbsp;";
		pagerTool += "<label>尾页</label>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;";
	}else{
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('first');\" style=\"color:black\">首页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('previous');"+"\" style=\"color:black\">上一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('next');"+"\" style=\"color:black\">下一页</a>&nbsp;";
		pagerTool += "<a href='javascript:void(0)' onclick=\""+methodName+"('last');"+"\" style=\"color:black\">尾页</a>&nbsp;";
		pagerTool += "<label>转到</label>&nbsp;<input name='gotoPage' id='gotoPage' type='text'class='niceform' size='1'/>&nbsp;<label>页</label> &nbsp;<a href='javascript:void(0)'onclick=\""+methodName+"('go');"+"\">GO</a>&nbsp;";
	}
	
	document.getElementById("pagerTool").innerHTML = pagerTool;
}

function getAllQuestion(method){
	var pager = getPager(method,10);
	var id = <%=Integer.parseInt(postsid)%>;
	dwrOaCommunicationService.getRegByPostid(id,pager,queryRegPostback);
}

function queryRegPostback(data){
    if(data.success == true){
		if(data.resultList.length > 0){
		     showPager(data.pager,"getAllQuestion");	//显示分页
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		     
		     for ( var i = 0; i < data.resultList.length; i++) {
		          var resultTable = document.createElement("table");
		          resultTable.id = data.resultList[i].primaryKey;
		          resultTable.width = "100%";
		          resultTable.cellpadding="0";
		          resultTable.cellspacing="0";
		          resultDiv.appendChild(resultTable);
		          
		          var tr1 = document.createElement("tr");
		        /******************************************************************/  
		          var td1 = document.createElement("td");
		              td1.width= "15%";
		              td1.style.borderBottom = "1px dotted #ccc";
			      var sontable1 = document.createElement("table");
			          sontable1.align = "center";

		          var son1tr1 = document.createElement("tr");
		          var son1tr1td = document.createElement("td");
		              son1tr1td.width = "100%";
		          var fileimg = document.createElement("img");
		              if(data.resultList[i].employee != null){
		                fileimg.src= "<%=request.getContextPath() %>/showimg.do?imgId="+data.resultList[i].employee.hrmEmployeeImageInfoId;
		              }else{
		                fileimg.src= "<%=request.getContextPath() %>/showimg.do?imgId=1";
			          }
			           fileimg.width="90";
			          fileimg.style.height = "auto";
			          son1tr1td.appendChild(fileimg);
			          son1tr1.appendChild(son1tr1td);

			      var son1tr2 = document.createElement("tr");
			      var son1tr2td = document.createElement("td");
			          son1tr2td.align = "center";
			      var empName = "<无>";
			      if(data.resultList[i].employee != null){
			          empName = data.resultList[i].employee.hrmEmployeeName;
			      }
			          son1tr2td.innerHTML = empName;
			          son1tr2.appendChild(son1tr2td);
			          
			          sontable1.appendChild(son1tr1);
			          sontable1.appendChild(son1tr2);
		              td1.appendChild(sontable1);
                 /****************************************************************************/
		          var td2 = document.createElement("td");
		              td2.width = "85%";
		              td2.style.cssText ="padding:5px;";
		              td2.style.borderBottom = "1px dotted #ccc";
		          var sontable2 = document.createElement("table");
		              sontable2.width= "100%";
		              sontable2.cellpadding="0";
		              sontable2.cellspacing="0";
		          var son2tr1 = document.createElement("tr");
                      
		          var son2tr1td1 = document.createElement("td"); 
		          son2tr1td1.align = "right";
		          son2tr1td1.style.cssText = "padding:5px;padding-right:10px;color:#666666";
		          var pageNo = document.getElementById("currentPage").innerHTML;
		          pageNo = pageNo -1;
		          var num = i+1;
		          num = num +(10*pageNo);
		          son2tr1td1.innerHTML = num+"楼";
		          son2tr1.appendChild(son2tr1td1);
	

		          var son2tr2 = document.createElement("tr");
		              son2tr2.height="150px";
		          var son2tr2td = document.createElement("td");
			          son2tr2td.colspan= "3";
			          son2tr2td.style.cssText = "padding:5px;padding-right:10px;word-break:break-all";
			      	  son2tr2td.innerHTML = data.resultList[i].oaPostText;
			      	  son2tr2.appendChild(son2tr2td);
                
                
                  var imager1Text = "<img src='<%=contextPath%>/images/grid_images/yiyong.gif' title='引用'  style='cursor:pointer;width:12px;' onclick='quoteData(" +resultTable.id+")'/>";
                  
		          var imager2Text ="<img src='<%=contextPath%>/images/grid_images/delat.gif' title='删除'  style='cursor:pointer;width:12px;' onclick='deleteReg(" +resultTable.id+")'/>";
                
                  var son2tr3 = document.createElement("tr");
                  var son2tr3td1 = document.createElement("td");
                      son2tr3td1.align = "right";
                      son2tr3td1.style.cssText = "padding:5px;padding-right:10px;color:#666666";
                      son2tr3td1.innerHTML = data.resultList[i].oaPostTime+" &nbsp;&nbsp;"+ imager1Text+"&nbsp;" + imager2Text;  
                      son2tr3.appendChild(son2tr3td1);
                                            
			      	  sontable2.appendChild(son2tr1);
			      	  sontable2.appendChild(son2tr2);
			      	  sontable2.appendChild(son2tr3);
			      	  td2.appendChild(sontable2);
		      	  
		         /******************************************************************/  
		      	  tr1.appendChild(td1);
		          tr1.appendChild(td2); 
		          resultTable.appendChild(tr1);  
		     }
		     resultDiv.outerHTML =  resultDiv.outerHTML;
		}
	}else{
		alertmsg(data.message);
	}
	document.getElementById("poststitle").focus();
}

function quoteData(pk){
    dwrOaCommunicationService.getRegPostsByid(pk,quoteBack);
}
function quoteBack(data){
    if(data.success == true){
		if(data.resultList.length > 0){
		  
		     fck.EditorDocument.body.innerHTML =" 引用： "+ data.resultList[0].oaPostText;
		     setTimeout(function() {fck.Focus(); }, 1000);
		      window.scrollTo(0,document.body.scrollHeight);
		}
	}else{
	    alertmsg("引用失败，请重试！");
	}	
}

function deleteReg(pk){
    confirmmsg("确定要删除此回复吗？","delRegok("+pk+")");
}
 
function delRegok(id){
    dwrOaCommunicationService.deleteRegById(id,deleteBack);
}

function deleteBack(data){
		alertmsg(data);
		getAllQuestion("null");
}

function reload(){
    Sys.load('<%=contextPath%>/erp/communication/my_posts_manage.jsp');
}

function reback(){
	window.location="my_posts_manage.jsp";
}

function closePage(){
		closeMDITab();
}
</script>
</head>
<body class="inputcls">
		<div class="requdivdetail"><label>查看帮助:&nbsp;帖子明细查看。</label></div>
		<div id="poststitle" style="border-bottom:1px dotted #cccccc;padding:10px;font-weight: bold;font-size: 16px;font-family: '宋体'">
		</div>
		<div id="queryResult"></div>
		<table align="center"  cellpadding="0" cellspacing="0" class="pager_table_style" style="margin-top:5px;margin-bottom:5px;padding: 2px;">
		    <tr>
		    	<td class="show_table_page">
		   			<script type="text/javascript">pageSet("getAllQuestion");</script>
		    	</td>				    	
		    </tr>
	    </table>
		<div class="formDetail">
		<div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">参与讨论</div>
		<div>
			<table class="inputtable">
				<tr>
					<th><em style="color: black">标&nbsp;&nbsp;题:</em></th>
					<td id="postsName"></td>
				</tr>
				<tr>
					<th><em style="color: black">内&nbsp;&nbsp;容:</em></th>
					<td style="text-align: left" colspan="6"><FCK:editor instanceName="regText" height="300" width="90%"></FCK:editor></td>
				</tr>
			</table>
		</div>
		</div>
	
	    <table align="center">
		    <tr>
	    	<td><btn:btn onclick="saveReg()" value="回 复 " imgsrc="../../images/fileokico.png" title="回复"></btn:btn></td>
	    	<td style="width: 10px;"></td>
	    	<td><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
		    </tr>
	    </table>
	  </body>
</html>
