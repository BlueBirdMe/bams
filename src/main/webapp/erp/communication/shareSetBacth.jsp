<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrOaCommunicationService.js"></script>
<title>共享通讯手册</title>
<%
    String pks = request.getParameter("pks");
    String[] ids = pks.split(",");
    List<Integer> list = new ArrayList<Integer>();
    for(int i = 0;i<ids.length;i++){
         int id = Integer.parseInt(ids[i]);
         list.add(id);
    }
 %>
<script type="text/javascript">
window.onload = function(){
	initInput('aaac', '在此处设置通讯手册共享，共享后他人方可看到！');
}

function getEmpinfo(){
	var shareEmp = "";
	shareEmp = document.getElementById("shareEmpIds").value;
	return shareEmp;
}

function save(){
      if(DWRUtil.getValue("shareEmpIds") == ""){
     	 setMustWarn("ShareEmpIdsmust","共享人员不能为空！");
      }else{
           var ids = <%=list%>;
           Btn.close();
	       dwrOaCommunicationService.shareSetBakch(ids,getEmpinfo(),saveCallback);  
      }
}
	
function saveCallback(data){
    Btn.open();
	alertmsg(data,"reload()");
}

function reload(){
     window.parent.MoveDiv.close();
     window.parent.queryData();
}

function getemployee(){
		var box = SEL.getEmployeeIds("check","chatShareEmp","shareEmpIds","processloadfrm");
		box.show();
}
</script>
</head>
<body class="inputcls">

<div class="formDetail">
		<div class="requdiv"><label id="aaac" ></label></div>
		<div class="formTitle">
			共享通讯手册
		</div>
		<table class="inputtable">
		<tr>
		<th></th>
			<td>
			<label id="ShareEmpIdsmust"></label>
			</td>
		</tr>
		<tr>
			<th width="15%"><em>*</em>共享人员</th>
			<td>
			<textarea  id="chatShareEmp" linkclear="ShareEmpIds" title="点击选择人员"  readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
			<input type="hidden" id="shareEmpIds" value="">
		</td>
	</tr>
	</table>
<br>
</div>

<br/>

<center>
<table>
<tr>
<td><btn:btn onclick="save();" value="确 定 " imgsrc="../../images/fileokico.png" title="确定"/></td>
<td style="width: 10px;"></td>
<td id="btncancel"><btn:btn onclick="window.parent.MoveDiv.close();s"  value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面" /></td>
</tr>
</table>
</center>
</body>
</html>