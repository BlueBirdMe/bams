<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>添加用户</title>
<%
//从配置文件加载初始密码及密码长度限制
String initpwd = UtilTool.getSysParamByIndex(request,"erp.user.initPwd");
int pwdlen = Integer.parseInt(UtilTool.getSysParamByIndex(request,"erp.user.PwdLen"));
int row =15;
 %>
</head>
<body class="inputcls">
<input type="hidden" id="employeename">
<input type="hidden" id="employeeid">
	<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">批量添加用户</div>
	<div>
	<table class="inputtable">
	<tr>
	<td>
	&nbsp;&nbsp;<input type="checkbox" id="forempname" checked="checked" onclick="createusername()"><label for="forempname">依据人员名称生成用户名</label>
	&nbsp;&nbsp;<input type="checkbox" id="hiddenemp" checked="checked" onclick="createusername()"><label for="hiddenemp">不显示已注册人员</label>
	</td>
	</tr>
	<tr>
	<td valign="top">
	<label id="clike"></label>
	<div style="border: 1px solid #A6D0E8;" >
	<table cellpadding='0' cellspacing='0' border='0' align='center' width ='100%'/>
	  <tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="26px">
		<td align='left' style='padding-left:10px;font-weight: bold;'>选择人员列表</td>
		<td style="text-align: right;" nowrap="nowrap" align="right">
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch();">&nbsp;删除选中人员&nbsp;</div>
		<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="getupcode()">&nbsp;选择人员&nbsp;</div>
		</td>
		</tr>
		<tr><td valign='top' colspan="2" height="360">
		<div style="overflow: auto;height:99%;vertical-align: top;" >
		<table  class='tablerowStyleColor'  cellSpacing='0' cellPadding='3' width='100%' align='center' border='1' id='employeelist'>
			<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
				<td  class='tableTitle1' style="text-align: center;" width="10px">
				<input type='checkbox' onclick="selectAll(this,'empchk')" title='全选/取消'>
				</td>
				<td  class='tableTitle1' style="text-align: center;">人员名称</td>
				<td  class='tableTitle1' style="text-align: center;"><em>*&nbsp;</em>用户名</td>
				<td  class='tableTitle1' style="text-align: center;"><em>*&nbsp;</em>初始密码</td>
				<td  class='tableTitle1' style="text-align: center;" width="20%">信息</td>
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
</div>
<br/>
<table align="center">
<tr>
<td>
<btn:btn onclick="saveuser();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存用户" />
</td>
</tr>
</table>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('title');
	createRow(0);
}

function createusername(){
	var empid =document.getElementById("employeeid").value;
	var empname =document.getElementById("employeename").value;
	if(empid.length==0){
		return;
	}
	dwrSysProcessService.vaildSysUserInfoByEmpIds(empid,empname,document.getElementById("forempname").checked,document.getElementById("hiddenemp").checked,validuserback);
}

function createRow(row){
	var tab = document.getElementById("employeelist");
	for ( var i = row ; i < <%=row%>; i++) {
		var otr = tab.insertRow(-1);
		for(var j=0;j<6;j++){
			var td=document.createElement("td");
			td.innerHTML ="&nbsp;";
			otr.appendChild(td);
		}
    }
}


function validuserback(data){
	var tab = document.getElementById("employeelist");
	if(data.success==true){
		 var rlen = tab.rows.length;	
		for(var i=rlen-1;i>=1;i--){
			tab.deleteRow(i);
		}
		if(data.resultList.length > 0){
			for ( var i = 0; i < data.resultList.length; i++) {
				var emps = data.resultList[i];
				var otr = tab.insertRow(-1);
				otr.id="tr"+emps[0];
		        var td1=document.createElement("td");
		        td1.innerHTML = "<input type='checkbox' name='empchk' value='"+emps[0]+"'><input type='hidden' name='empid' value='"+emps[0]+"'>";
		        var td2=document.createElement("td");
		        td2.innerHTML =emps[1];
		        var td3=document.createElement("td");
		        td3.style.cssText ="text-align:center";
				td3.innerHTML ="<input type='text' name='username' class='niceform' value='"+emps[2]+"' maxlength='50' style='ime-mode:disabled;'>";
				var td4=document.createElement("td");
				td4.style.cssText ="text-align:center";
				td4.innerHTML="<input type='text' name='userpwd' class='niceform' value='"+emps[3]+"' maxlength='18' style='ime-mode:disabled;'>";
				var t ="<label id='lab"+emps[0]+"'></label><input type='checkbox' name='isaction' class='nonice' style='display:none'>";
				if(parseInt(emps[4])>0){
					t ="<label id='lab"+emps[0]+"'></label>该人员已注册为用户<input type='checkbox' name='isaction' class='nonice' checked ='true' style='display:none'>";
				}
				var td5=document.createElement("td");
				td5.style.cssText ="color:red";
				td5.innerHTML=t;
				var td6=document.createElement("td");
				td6.style.cssText ="text-align:center";
				td6.innerHTML="<a href='javascript:void(0)' onclick=\"delrow('"+emps[0]+"')\">删除</a>";
		        otr.appendChild(td1);
		        otr.appendChild(td2);
		        otr.appendChild(td3);
		        otr.appendChild(td4);
		        otr.appendChild(td5);
		        otr.appendChild(td6);
	        }
	        //setselectStyle();
        }
        createRow(data.resultList.length);
	}else{
		alertmsg(data);
	}
}

function delrow(i){
	var tab = document.getElementById("employeelist");
	var row = document.getElementById("tr"+i);
	var rIndex = row.rowIndex;
	tab.deleteRow(rIndex);
	
	var empid =document.getElementById("employeeid").value;
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
	document.getElementById("employeeid").value = tmpid;
	document.getElementById("employeename").value = tmpname;
}

function deletebatch(){
	var ids = getCheckedValues("empchk");
	for(var i=0;i<ids.length;i++){
		delrow(ids[i]);
	}
}

function createusernameback(){
	var emp = document.getElementById("employeename").value;
	if(emp.length==0){
		return;
	}
	if(document.getElementById("forempname").checked){
		dwrCommonService.getPinYinByString(emp,setvalue);
	}	
}
function getupcode(){
	var box = SEL.getEmployeeIds("check","employeename","employeeid","userfrm","createusername();");
	box.show();
}

function saveuser(){
	var tit = document.getElementById("title");
	var ccount = getCheckedCount("isaction");
	if(ccount>0){
		alertmsg("请将<font color='red'>不显示已注册人员</font>复选框选中，去除已注册用户!");
		return false;
	}
	var empids  =document.getElementsByName("empid");
	var usernames = document.getElementsByName("username");
	var userpwds = document.getElementsByName("userpwd");
	
	if(empids.length==0){
		setMustWarn("clike", "请选择要注册为用户的人员");
		return false;
	}else{
		initInput('title');
	}
	
	var tmpvid = new Array();
	for(var i=0;i<empids.length;i++){
		tmpvid[i] = empids[i].value;
	}
	
	var tmpvname = new Array();
	for(var i=0;i<usernames.length;i++){
		tmpvname[i] = usernames[i].value;
	}
	
	var tmpvpwd = new Array();
	for(var i=0;i<userpwds.length;i++){
		tmpvpwd[i] = userpwds[i].value;
	}
	
	
	//清空提示信息框
	for(var i=0;i<tmpvid.length;i++){
		document.getElementById("lab"+tmpvid[i]).innerHTML ="";
	}
	//验证用户不能为空
	for(var i=0;i<usernames.length;i++){
		if(trim(usernames[i].value).length<1){
			usernames[i].focus();
			var eid = empids[i].value;
			document.getElementById("lab"+eid).innerHTML = "用户名不能为空！";
			return false;
		}
	}
	//验证列表用户名是否重复
	for(var i=0;i<usernames.length-1;i++){
		for(var j=i+1;j<usernames.length;j++){
			if (usernames[i].value==usernames[j].value){
				usernames[i].focus();
				var lab1 = document.getElementById("lab"+empids[i].value);
				var lab2 = document.getElementById("lab"+empids[j].value); 
				lab1.innerHTML = "列表用户名重复！";
				lab2.innerHTML = "列表用户名重复！";
				return false;
			}
		}
	}
	//验证密码长度
	for(var i=0;i<userpwds.length;i++){
		if(trim(userpwds[i].value).length<<%=pwdlen%>){
			userpwds[i].focus();
			var eid = empids[i].value;
			document.getElementById("lab"+eid).innerHTML = "密码长度不能小于<font color='blue'><%=pwdlen%></font>位！";
			return false;
		}
	}
	
	//验证列表用户名是否与数据库重复
	
	dwrSysProcessService.vaildSysUserInfoByUserNames(tmpvname,function(data){
		for(var i=0;i<data.length;i++){
			if(data[i]>0){
				usernames[i].focus();
				var eid = empids[i].value;
				document.getElementById("lab"+eid).innerHTML = "用户名已被注册";
				return false;
			}
		}
		//写入数据库
		dwrSysProcessService.saveSysUsers(tmpvid,tmpvname,tmpvpwd,methodcallback);
	});
}


function methodcallback(data){
	alertmsg(data,"sevent()");
}
function sevent(){
	document.getElementById("employeeid").value ="";
	document.getElementById("employeename").value ="";
	var tab = document.getElementById("employeelist");
	var rlen = tab.rows.length;	
	for(var i=rlen-1;i>=1;i--){
		tab.deleteRow(i);
	}
	initInput('title');
	createRow(0);
}
</script>
</body>
</html>