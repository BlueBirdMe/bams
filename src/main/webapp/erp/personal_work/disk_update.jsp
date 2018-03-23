<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
    String ids=   request.getParameter("ids");
 %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加编辑页面</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrNetdiskService.js"></script>
<script type="text/javascript">
window.onload = function() {
	useLoadingMassage();
	initInput('helpTitle','您可以设置个人磁盘的大小');
	dwrNetdiskService.getOaNetdisksize(<%=ids%>,getsize);
	
}

function getsize(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var oaNetdisk = data.resultList[0];
        	DWRUtil.setValue("totalSpace",oaNetdisk.totalSpace);
    	}
	}
}

function getDisk(){
	//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "totalSpacemust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
	   var totalspace=DWRUtil.getValue("totalSpace");
	   bl = isNumber(totalspace);
	   if(bl){
			Btn.close();
			var disk= new Object();
			disk.totalSpace=totalspace;
		 	disk.hrmEmployeeId='<%=ids%>';
			dwrNetdiskService.addOaNetdisk(disk,saveCallback);
	   }else{
	   		setMustWarn("totalSpacemust","请输入一个整数!");
	   		document.getElementById("totalSpace").focus();
	   		return;
	   }
	}
}
	
function saveCallback(data){
	Btn.open();
	alertmsg(data,"returnload()");
}

function returnload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}

</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
	<div class="formTitle">设置大小</div>
	<div>
		<table class="inputtable">
			<tr>
			
				<th><em>*&nbsp;</em>大小</th>
				<td>
				  <input type="text"  id="totalSpace"  class="numform" must="请设置磁盘大小" maxlength="4" formust="totalSpacemust">
				  &nbsp;&nbsp;<label id="totalSpacemust"></label>
				</td>
					
			</tr>
		</table>	
	</div>
</div>
	<br/>
		<table align="center">
   	<tr>
     	<td><btn:btn onclick="getDisk()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存磁盘大小" /></td>
     	<td style="width: 10px;"></td>
     	<td id="backbtn"><btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
   	</tr>
	</table>
</body>
</html>