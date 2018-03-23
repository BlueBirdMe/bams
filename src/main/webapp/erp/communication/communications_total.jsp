<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>进入版块</title>
		
<script>


window.onload=function(){
    getAllForums("null");
}

function getAllForums(method){
	var forums = new Object();
	dwrOaCommunicationService.listForums(forums,queryCallback);
}

function queryCallback(data){
    if(data.success == true){
		
		     //showPager(data.pager,"getAllForums");	//显示分页
		     var resultDiv = document.getElementById("queryResult");
		     resultDiv.innerHTML = "";
		      var resultTable = document.createElement("table");
		      resultTable.style.cssText = "margin:0px;border:0px;padding:0px;height:100%;width:100%;";
		      var tr2 = document.createElement("tr");
		           var td2 = document.createElement("td");
		           	  td2.width= "30%";
		              td2.className="ForumPageTableTitle";
		              td2.innerHTML = "版块";
		              td2.colSpan = "2";
		           tr2.appendChild(td2);
		           var td3 = document.createElement("td");
		           	  td3.width= "5%";
		              td3.className="ForumPageTableTitle";
		              td3.innerHTML = "今日";
		           tr2.appendChild(td3);
		           var td4 = document.createElement("td");
		           	  td4.width= "5%";
		              td4.className="ForumPageTableTitle";
		              td4.innerHTML = "主题";
		           tr2.appendChild(td4);
		           var td5 = document.createElement("td");
		           	  td5.width= "5%";
		             td5.className="ForumPageTableTitle";
		              td5.innerHTML = "帖数";
		           tr2.appendChild(td5);
		           var td6 = document.createElement("td");
		           	  td6.width= "20%";
		              td6.className="ForumPageTableTitle";
		              td6.innerHTML = "最后发表";
		           tr2.appendChild(td6);
		            var td7 = document.createElement("td");
		           	  td7.width= "10%";
		              td7.className="ForumPageTableTitle";
		              td7.innerHTML = "版主";
		           tr2.appendChild(td7);
		           resultTable.appendChild(tr2);

			//table detail
			if(data.resultList.length > 0){
		       for ( var i = 0; i < data.resultList.length; i++) {
		          var tr1 = document.createElement("tr");
		          var td1 = document.createElement("td");
		          td1.className="ForumPageTableDataLine";
		          td1.width="5%";
		          //模块图片
		          var imageID = data.resultList[i].oaForumImage;
		          if(imageID==null || imageID=="undefined" || imageID==undefined) {
		          	imageID="-1";
		          }
		          var fileimg = document.createElement("img");
		          fileimg.src= "<%=request.getContextPath() %>/showimg.do?imgId="+imageID;
		          fileimg.width="60";
		          fileimg.height="60";
			      td1.appendChild(fileimg);
			      tr1.appendChild(td1);
			      
			      var tdSuperLink = document.createElement("td");
			      tdSuperLink.className="ForumPageTableDataLine";
		          //模块超链接
		          	var ul1 = document.createElement("ul");
		          		ul1.className="ForumPageTopicUl";
		          	var li1 = document.createElement("li");
		          		li1.className="ForumPageTopic";
		          		
		          	 var a1 = document.createElement("a");
		          	a1.className="ForumPageTopic";
		          	a1.href = "posts_list.jsp?forumId="+data.resultList[i].primaryKey+"&forumName="+encodeURI(data.resultList[i].oaForumName);
		          	a1.innerHTML = data.resultList[i].oaForumName+"</br>";
		          	li1.appendChild(a1);
		          	ul1.appendChild(li1);
		          	
		          	var li2 = document.createElement("li");
		          		li2.className="ForumPageTopicMemo";
		          		var tmp = data.resultList[i].oaForumText;
		          		if(tmp!=null&&tmp.length>20){
		          			tmp = tmp.substring(0,20)+"...";
		          		}
		          		li2.title=data.resultList[i].oaForumText;
		          		li2.innerHTML=tmp;
		          	ul1.appendChild(li2);
		          	
		          	tdSuperLink.appendChild(ul1);
		          	tr1.appendChild(tdSuperLink);
		          	var td2 = document.createElement("td");
		          	td2.className="ForumPageToday";
		          	td2.style.cssText="text-align:center;";
		          	td2.innerHTML = data.resultList[i].todayPostCount;
		          	tr1.appendChild(td2);
		          	
		          	var td3 = document.createElement("td");
		          	td3.style.cssText="text-align:center;";
		          	td3.innerHTML = data.resultList[i].topicCount;
		          	td3.className="ForumPageTableDataLine";
		          	tr1.appendChild(td3);
		          	
		          	var td4 = document.createElement("td");
		          	td4.style.cssText="text-align:center;";
		          	td4.innerHTML = data.resultList[i].articleCount;
		          	td4.className="ForumPageTableDataLine";
		          	tr1.appendChild(td4);
		          	
		          	var td5 = document.createElement("td");
		          	td5.style.cssText="text-align:center;";
		          	
	          		var title = data.resultList[i].lastReplyTitle;
	          		var tittmp= title;
	          		var author = data.resultList[i].lastReplyAuthor;
	          		var date = data.resultList[i].lastReplyDate;
	          		var url = "version_posts_reg.jsp?postsId="+data.resultList[i].lastReplyID+"&forumId="+data.resultList[i].listReplyForumID;
	          		if(title==null || title=='undefined' || title==undefined) {
	          			title = "";
	          			tittmp = "";
	          		}else if(title.length>15){
	          			tittmp = title;
	          			title = title.substring(0,15)+"...";
	          		}
	          		if(author==null || author=='undefined' || author==undefined) {
	          			author = "";
	          		}
	          		if(date==null  || date=='undefined' || date==undefined) {
	          			date = "";
	          		} 
	          		td5.className="ForumPageTableDataLine";
		          	td5.innerHTML = "<ul class=ForumPageTopicUl><LI><font COLOR=#444444>主题：</font><a class=ForumTitleA href="+url+" title='"+tittmp+"'>"+title+"</a></LI><LI><font COLOR=#444444>作者：</font>"+author+"</LI><LI><font COLOR=#444444>时间：</font>"+date+"</LI></ul>";
		          	tr1.appendChild(td5);
		          	
		          	var td6 = document.createElement("td");
		          	td6.style.cssText="text-align:center;";
		          	td6.innerHTML = data.resultList[i].oaForumAdmin;
		          	td6.className = "ForumPageTableDataLine";
		          	tr1.appendChild(td6);
		          	resultTable.appendChild(tr1);
		          	
		          	var trLine1 = document.createElement("tr");
		           		trLine1.height="1";
		           	var tdLine1 = document.createElement("td");
		           		tdLine1.colSpan="7";
		           
		           	trLine1.appendChild(tdLine1);
		           	resultTable.appendChild(trLine1);
		          
		     	}
		   } else {
			var tr1 = document.createElement("tr");
		          var td1 = document.createElement("td");
		          td1.className="ForumPageTableDataLine";
		          td1.colSpan="6";
		          td1.innerHTML = "<b>无论坛版块,请添加.</b>";
		          tr1.appendChild(td1);
		          resultTable.appendChild(tr1);
			}
		          resultTable.width = "100%";
		          resultTable.cellpadding="0";
		          resultTable.cellspacing="0";
		          resultDiv.appendChild(resultTable);
		          
		     
		     resultDiv.outerHTML =  resultDiv.outerHTML;
	}else{
		alertmsg(data.message);
	}
}
</script>
</head>
<body class="inputcls">
		<table border="0" style="width:100%;text-align: center;">
		<tr>
			<td><div id="queryResult" class=ForumPageTableBorder></div></td>
		</tr>
		</table>
</body>
</html>
