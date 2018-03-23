<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>用户编组</title>
<%
int row =15;
String gid = request.getParameter("gid");
 %>
</head>
<body class="inputcls">
<input type="hidden" id="employeename">
<input type="hidden" id="userid">
	<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">创建/编辑用户编组</div>
	<table class="inputtable">
	<tr>
	<th>
	<em>*&nbsp;</em>组名称
	</th>
	<td>
	<input type="text" id="groupname" maxlength="50" must="请输入组名称" formust="groupnamemust"><label id="groupnamemust"></label>
	</td>
	<td>
	<input type="checkbox" id="forempname" onclick="getusermsg()"><label for="forempname">不显示已编组用户</label>
	</td>
	</tr>
	<tr>
	<th></th>
	<td id="hrmDepDescMsg"></td>
	</tr>
	<tr>
	<th>组描述</th>
	<td colspan="2">
	<textarea id="grouptext"></textarea>
	</td>
	</tr>
	<tr>
	<th>
	<em>*&nbsp;</em>组用户
	</th>
	<td valign="top" colspan="2">
	<div style="border: 1px solid #A6D0E8;width: 90%" >
	<label id="userlist1"></label>
	<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
	  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">
		<td align='left' style='padding-left:10px;font-weight: bold;'>组用户列表</td>
		<td style="text-align: right;" nowrap="nowrap" align="right">
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch();">&nbsp;删除选中用户&nbsp;</div>
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getupcode()">&nbsp;选择用户&nbsp;</div>
		</td>
		</tr>
		<tr>
		<td valign="top" colspan="2" height="310">
		<div style="overflow: auto;height:99%;vertical-align: top;">
		<table  class='tablerowStyleColor'  cellSpacing='0' cellPadding='3' width='100%'  align='center' border='1' id='userlist'>
			<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
				<td  class='tableTitle1' style="text-align: center;" width="10px">
				<input type='checkbox' onclick="selectAll(this,'userchk')" title='全选/取消'>
				</td>
				<td  class='tableTitle1' style="text-align: center;">用户名称</td>
				<td  class='tableTitle1' style="text-align: center;">所在组信息</td>
				<td  class='tableTitle1' style="text-align: center;">操作</td>
			</tr>
		</table>
		</div>
		</td>
	  </tr>
	</table>
	</div>
	</td>
	</tr>
	</table>
	<br>
</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="saveuser();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存编组信息" />
</td>
<td style="width: 15px;"></td>
<td id="backbtn">
<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/>
</td>
</tr>
</table>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('title');
	createRow(0);
	if(<%=gid%>!=null){
		dwrSysProcessService.getSysUserGroupByPk(<%=gid%>,setpagevalue);
	}else{
  	Btn.hidden("backbtn");
	}
}

function setpagevalue(data){
	if(data!=null){
		var tmp =data.resultList[0];
		DWRUtil.setValue("groupname",tmp.groupName);
		DWRUtil.setValue("grouptext",tmp.groupDecp);
		var userids = "";
		for(var i=0;i<tmp.detailList.length;i++){
			userids+=tmp.detailList[i].userId+",";
		}
		DWRUtil.setValue("userid",userids);
		dwrSysProcessService.getEmployeeNameByUserIds(userids,setemployeenames);
	}
}
function setemployeenames(data){
	if(data!=null&&data.resultList.length>0){
		var names ="";
		for(var i=0;i<data.resultList.length;i++){
			names+=data.resultList[i]+",";
		}
		DWRUtil.setValue("employeename",names);
		getusermsg();
	}
}

function getusermsg(){
	var userid =document.getElementById("userid").value;
	var empname =document.getElementById("employeename").value;
	if(userid.length==0){
		return;
	}
	dwrSysProcessService.getUserGroupDetailList(userid,empname,document.getElementById("forempname").checked,getcallback);
}

function createRow(row){
	var tab = document.getElementById("userlist");
	for ( var i = row ; i < <%=row%>; i++) {
		var otr = tab.insertRow(-1);
		for(var j=0;j<4;j++){
			var td=document.createElement("td");
			td.innerHTML ="&nbsp;";
			otr.appendChild(td);
		}
    }
}


function getcallback(data){
	var tab = document.getElementById("userlist");
	if(data.success==true){
		 var rlen = tab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			tab.deleteRow(i);
		}
		if(data.resultList.length > 0){
			for ( var i = 0; i < data.resultList.length; i++) {
				var users = data.resultList[i];
				var otr = tab.insertRow(-1);
				otr.id="tr"+users[0];
		        var td1=document.createElement("td");
		        td1.innerHTML = "<input type='checkbox' name='userchk' value='"+users[0]+"'>";
		        var td2=document.createElement("td");
		        td2.innerHTML =users[1];
		        var td3=document.createElement("td");
		        if(users[2]==""){
		        	td3.innerHTML ="<font color='green'>未编组</font>";
		        }else{
					td3.innerHTML =users[2];
				}
				var td4=document.createElement("td");
				td4.style.cssText ="text-align:center";
				td4.innerHTML="<a href='javascript:void(0)' onclick=\"delrow('"+users[0]+"')\">删除</a>";
		        otr.appendChild(td1);
		        otr.appendChild(td2);
		        otr.appendChild(td3);
		        otr.appendChild(td4);
	        }
	        //setselectStyle();
        }
        createRow(data.resultList.length);
	}else{
		alertmsg(data);
	}
}

function delrow(i){
	var tab = document.getElementById("userlist");
	var row = document.getElementById("tr"+i);
	var rIndex = row.rowIndex;
	tab.deleteRow(rIndex);
	
	var empid =document.getElementById("userid").value;
	var empname =document.getElementById("employeename").value;
	
	var emps = empid.split(",");
	var empnames = empname.split(",");
	var tmpid="";
	var tmpname ="";
	for(var j=0;j<emps.length;j++){
		if(emps[j].length>0&&emps[j] != i){
			tmpid+= emps[j]+",";
			tmpname+=empnames[j]+",";
		}
	}
	document.getElementById("userid").value = tmpid;
	document.getElementById("employeename").value = tmpname;
}

function deletebatch(){
	var ids = getCheckedValues("userchk");
	for(var i=0;i<ids.length;i++){
		delrow(ids[i]);
	}
}
function getupcode(){
	if(<%=gid%>!=null){
		var box = SEL.getUserIds("check","employeename","userid","usergroupfrm@@processloadfrm","getusermsg()");
		box.show();
	}else{
	  	var box = SEL.getUserIds("check","employeename","userid","usergroupfrm","getusermsg()");
		box.show();
	}
}

function saveuser(){
    var warnArr = new Array();
	warnArr[0] = "userlist1"; //清空所有信息提示
	warnArr[1] = "hrmDepDescMsg";
	warnInit(warnArr); //验证常用组件
	var tit = document.getElementById("title");
	var bl = validvalue('title');
	if(bl){
		var tmp = DWRUtil.getValue("grouptext");
   		  if(tmp.length>200){
   		  	setMustWarn("hrmDepDescMsg","部门描述不能大于200个字符!");
   		  	return;
   		  }
		var cks = document.getElementsByName("userchk");
		if(cks.length==0){
			setMustWarn("userlist1", "请选择编组的用户");
			return false;
		}
		var userids = new Array();
		for(var i=0;i<cks.length;i++){
			userids[i] = cks[i].value;
		}
		Btn.close();
		dwrSysProcessService.saveSysUserGroupAndDetail(getusergroup(),userids,methodcallback);
	}
		
}


function methodcallback(data){
	Btn.open();
	var method = "sevent()";
	if(<%=gid%>!=null){
		method ="returnload()";
	}
	alertmsg(data,method);
}
function returnload(){
	window.parent.MoveDiv.close();
	window.parent.queryData();
}
function sevent(){
	document.getElementById("userid").value ="";
	document.getElementById("employeename").value ="";
	document.getElementById("forempname").checked = false;
	DWRUtil.setValue("groupname","");
	DWRUtil.setValue("grouptext","");
	var tab = document.getElementById("userlist");
	var rlen = tab.rows.length;	
	for(var i=rlen-1;i>=1;i--){
		tab.deleteRow(i);
	}
	initInput('title');
	createRow(0);
	window.parent.queryData();
}

function getusergroup(){
	var group = new Object();
	if(<%=gid%>!=null){
		group.primaryKey = <%=gid%>;
	}
	group.groupName = DWRUtil.getValue("groupname");
	group.groupDecp = DWRUtil.getValue("grouptext");
	return group;
}

</script>
</body>
</html>