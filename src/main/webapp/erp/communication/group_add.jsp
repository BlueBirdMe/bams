<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>新增分组界面</title>
		<%
			String groupid = request.getParameter("groupId");
		%>
		<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	initInput('aaac',"欢迎添加新的好友分组");
		//第一个输入框获取焦点
	document.getElementById("groupName").focus();
	saveOredit();
}

function getGroupinfo(){
	var group = new Object();
	if(<%=groupid%> != null){
	     group.primaryKey = <%=groupid%>;
	}
	group.oaChatgpName = document.getElementById("groupName").value;
	group.oaChatgpDetail = document.getElementById("groupText").value;
	return group;
}

function saveOredit(){
    if(<%=groupid%> != null){
         var  id = <%=groupid%>;
         dwrOaCommunicationService.getGroupByid(id,setGroupInfo);
    }
}

function setGroupInfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var group = data.resultList[0];
 			document.getElementById("groupName").value = group.oaChatgpName;
 			document.getElementById("groupText").value = group.oaChatgpDetail;
 	    }else{
 			alertmsg("没有找到相关记录！");
 		}
 	}else{
 		alertmsg("获取分组信息失败！");
 	}		
}

function save(){
      var bl = validvalue('aaac');
      if(bl){
	       dwrOaCommunicationService.saveChatGroup(getGroupinfo(),saveCallback);  
	       Btn.close();
      }
}
	
function saveCallback(data){
    Btn.open();
    if(data.success){
   		if(<%=groupid%>!=null){
   			alertmsg(data,"reload()");
		}else{
  				confirmmsgAndTitle("添加通讯分组成功！是否想继续添加?","clearpager();","继续添加","closePage();","关闭页面");
		}
    }else{
		alertmsg(data);
	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function clearpager(){
    document.getElementById("groupName").value = "";
    document.getElementById("groupText").value = "";
    refreshMDITab(<%=request.getParameter("tab")%>);
}

function reload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
  
  function reset(){
  	DWRUtil.setValue("groupName","");
  	DWRUtil.setValue("groupText","");
  }
</script>
	</head>
	<body class="inputcls">

		<div class="formDetail">
			<div class="requdiv">
				<label id="aaac"></label>
			</div>
				<div class="formTitle">
				新增通讯分组
			</div>
			<div>

				<table class="inputtable">
				<tr>
				<th></th>
				<td>
				<label id="groupNamemust"></label>
				</td>
				</tr>
					<tr>
						<th>
							<em>* </em>通讯分组名称
						</th>
						<td style="text-align: left;" colspan="3">
							<input type="text" id="groupName" must="分组名称不能为空!" value="" formust="groupNamemust"
								maxlength="25" style="width: 90%">
						</td>

					</tr>
					<tr>
					<th>
						通讯分组说明
					</th>
					<td style="text-align: left" colspan="3">
						<textarea style="height: 200px;" id="groupText" ></textarea>
					</td>
					</tr>
				</table>
				<br>
			</div>
	 	</div>
		<br />
		<br />
		<br />
		<center>
			<table>
				<tr>
			    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
			    	<td style="width: 10px;"></td>
					<td>
					<%if (groupid == null){ %>
					<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
					<%}else{ %>
					<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
					<%} %>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>