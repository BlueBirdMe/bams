<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<title>人员导入</title>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
String ext="xls|xlsx";
String[] exts=ext.split("\\|");

Object msg = request.getAttribute(ConstWords.TempStringMsg);
Object obj  =request.getAttribute("excellist");
 %>
<script type="text/javascript">
window.onload = function(){
}
function nocontextmenu() {
 event.cancelBubble = true
 event.returnValue = false;
 
 return false;
}

function downfile(){
	window.parent.document.location.href= "<%=contextPath %>/downloadfile/employee-model.xls";
}

//判断上传文件类型
var array =null;
<%if(exts!=null&&exts.length>0){%>
	array =new Array(<%=exts.length%>);
	<%for(int a=0;a<exts.length;a++){%>
		array[<%=a%>] = '<%=exts[a].toLowerCase()%>';
	<%}%>
<%}%>

function improtfile(){
	document.getElementById("filemsg").innerHTML = "";
	var imp = document.getElementById("impfile");
	if(imp.value==""){
		setMustWarn("filemsg","请选择要导入的文件！"); 
		document.getElementById("impfile").focus(); 
		return false;
	}
	if(array!=null||array.length>0){
		//类型显示
		var bl =false;
		var strFileFormat = imp.value;
		var fext=strFileFormat.substring(strFileFormat.lastIndexOf('.')+1);
		for(var i=0;i<array.length;i++){
			if(fext.toLowerCase() == array[i]){
				bl=true;
				break;
			}
		}
		if(bl==false){
			setMustWarn("filemsg","文件类型错误,只能上传类型为:<%=ext%>"); 
			document.getElementById("impfile").focus(); 
			return false;
		}
	}
	document.frmname.action = "<%=request.getContextPath()+"/emp_import.do"%>";
	document.frmname.submit();
	Btn.close();
}

function saveemp(r){
	var msg = document.getElementById("empmsg_"+r);
	msg.innerHTML = "";
	msg.style.color="blue";
	var row = document.getElementById("emprow_"+r);
	row.style.backgroundColor="#ffffbe";
	var hrmEmployeeName = document.getElementById("hrmEmployeeName_"+r);
	if(trim(hrmEmployeeName.value).length==0){
		msg.innerHTML = "姓名不能为空！";
		hrmEmployeeName.focus();
		return false;	
	}
	
	var hrmEmployeeSex = document.getElementById("hrmEmployeeSex_"+r);
	if(trim(hrmEmployeeSex.value).length==0){
		msg.innerHTML = "性别不能为空！";
		hrmEmployeeSex.focus();
		return false;	
	}
	
	var hrmEmployeeCode = document.getElementById("hrmEmployeeCode_"+r);
	if(trim(hrmEmployeeCode.value).length==0){
		msg.innerHTML = "员工工号不能为空！";
		hrmEmployeeCode.focus();
		return false;	
	}
	
	var hrmEmployeeBirthday = document.getElementById("hrmEmployeeBirthday_"+r);
	if(trim(hrmEmployeeBirthday.value).length==0){
		msg.innerHTML = "出生日期不能为空！";
		hrmEmployeeBirthday.focus();
		return false;
	}
	
	var hrmEmployeeStatus = document.getElementById("hrmEmployeeStatus_"+r);
	if(trim(hrmEmployeeStatus.value).length==0){
		msg.innerHTML = "入职状态不能为空！";
		hrmEmployeeStatus.focus();
		return false;
	}
	
	var hrmEmployeeActive = document.getElementById("hrmEmployeeActive_"+r);
	if(trim(hrmEmployeeActive.value).length==0){
		msg.innerHTML = "员工状态不能为空！";
		hrmEmployeeActive.focus();
		return false;
	}
	
	var emp = getEmpinfo(r);
	
	dwrHrmEmployeeService.saveEmployee(emp,"","",function(data){
		savecallback(r,data);
	});
}

function savecallback(r,data){
	if(data.success){
		alertmsg(data,"delrow("+r+")");
	}else{
		document.getElementById("empmsg_"+r).innerHTML = data.message;
	}
}


//获取人员实体
function getEmpinfo(r){
	var emp = new Object();
    emp.hrmEmployeeName = document.getElementById("hrmEmployeeName_"+r).value;
    emp.hrmEmployeeSex = document.getElementById("hrmEmployeeSex_"+r).value;
    emp.hrmEmployeeCode = document.getElementById("hrmEmployeeCode_"+r).value;
    emp.hrmEmployeeBirthday = document.getElementById("hrmEmployeeBirthday_"+r).value;
    emp.hrmEmployeeStatus = document.getElementById("hrmEmployeeStatus_"+r).value;
    emp.hrmEmployeeActive = document.getElementById("hrmEmployeeActive_"+r).value;
   
    emp.hrmEmployeeIdentitycard = document.getElementById("hrmEmployeeIdentitycard_"+r).value;
    emp.hrmEmployeeHouseAddress = document.getElementById("hrmEmployeeHouseAddress_"+r).value;
    emp.hrmEmployeeMobileTele = document.getElementById("hrmEmployeeMobileTele_"+r).value;
    emp.hrmEmployeeWorkTele = document.getElementById("hrmEmployeeWorkTele_"+r).value;
    emp.hrmEmployeeInTime = document.getElementById("hrmEmployeeInTime_"+r).value;
    emp.hrmEmployeeWorkTime = document.getElementById("hrmEmployeeWorkTime_"+r).value;
	return emp;
}

function delrow(r){
	var row = document.getElementById("emprow_"+r);
	var tab = document.getElementById("tableresult");
	var rIndex = row.rowIndex;
	tab.deleteRow(rIndex);
	
	if(tab.rows.length==1){
		document.getElementById("tabdiv").innerHTML="";
	}
}




</script>
</head>
<body class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle">请下载导入模板并按正确格式填写，执行批量导入！</label></div>
	<div class="formTitle">导入人员信息</div>
	<form id="frmname" method="post" enctype="multipart/form-data" name="frmname">
	<table class="inputtable" border="0">
		<tr>
			<th width="15%"><em>* </em>选择文件</th>
			<td>
				<input type="file" class="niceform" id="impfile" name="impfile" style="width: 80%">
			</td>
			<td width="10%">
			<btn:btn onclick="improtfile()" value=" 导 入 "></btn:btn>
			</td>
			<td width="10%">
			<btn:btn onclick="downfile()" value=" 下载模板 "></btn:btn>
			</td>
		</tr>
		<tr>
		<td width="15%"></td>
		<td colspan="3" id="filemsg"></td>
		</tr>
	</table>
	</form>
	
	<br/>
	<div id="tabdiv">
	<%if(msg!=null){ %>
	<div class="formTitle">导入结果</div>
	<div style="color: green;padding-left: 3px;text-align: left;width: 98%;padding-top: 10px"><%=msg.toString() %></div>
	<br/>
	<%if(obj!=null){ 
	List<EmployeeExcelBean> beanlist = (List<EmployeeExcelBean>)obj;
	if(beanlist.size()>0){
	%>
	<table cellpadding='0' cellspacing='0' border='0' align='center' width ='98%'/>
	<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="26px">
	<td align='left' style='padding-left:10px;font-weight: bold;' id='tableresult_TitleName'>需调整数据列表</td><td align='right' id='systablebtntd'></td></tr>
	<tr><td valign='top' colspan='2'>
	<table   cellSpacing='0' cellPadding='4' width='100%' align='center' border='1' id='tableresult' style="BORDER-RIGHT:#BDBCBC 1px solid;BORDER-TOP:  #BDBCBC  1px solid;BORDER-LEFT:  #BDBCBC  1px solid;BORDER-BOTTOM:  #BDBCBC  1px solid;BORDER-COLLAPSE: collapse;BACKGROUND-COLOR: #fff;">
	<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg2.gif');">
	<td class='tableTitle1' style="text-align: center;">序号</td>
	<td class='tableTitle1' style="text-align: center;color: red">必填项目(性别，入职状态，员工状态均为模板文件中所对应的编码)</td>
	<td class='tableTitle1' style="text-align: center;">选填项目</td>
	<td class='tableTitle1' style="text-align: center;">信 息</td>
	<td class='tableTitle1' style="text-align: center;">操 作</td>
	</tr>
	<%
	int r=0;
	for(int i=0;i<beanlist.size();i++){
	EmployeeExcelBean bean = beanlist.get(i);
	r = i+1;
	 %>
	<tr id="emprow_<%=r %>">
	
	<!-- 序号 -->
	<td align="center"><%=r %></td>
	
	<!--必填项目开始 -->
	<td align="center">
	<table cellpadding="0" cellspacing="0" border="0" style="border: 0">
	<tr>
	<td nowrap="nowrap">员工姓名：</td>
	<td><input type="text" value="<%=bean.getHrmEmployeeName() %>" id="hrmEmployeeName_<%=r %>"/></td>
	<td nowrap="nowrap">员工性别：</td>
	<td><input type="text" value="<%=bean.getHrmEmployeeSex() %>" id="hrmEmployeeSex_<%=r %>"/></td>
	
	</tr>
	<tr>
	<td nowrap="nowrap">员工工号：</td>
	<td><input type="text" value="<%=bean.getHrmEmployeeCode() %>"  id="hrmEmployeeCode_<%=r %>"/></td>
	
	<td nowrap="nowrap">出生日期：</td>
	<td><input type="text" readonly="readonly"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<%=bean.getHrmEmployeeBirthday()%>" id="hrmEmployeeBirthday_<%=r %>" ></td>
	
	</tr>
	<tr>
	<td nowrap="nowrap">入职状态：</td>
	<td><input type="text" value="<%=bean.getHrmEmployeeStatus() %>" maxlength="25" id="hrmEmployeeStatus_<%=r %>"/></td>
	<td nowrap="nowrap">员工状态：</td>
	<td><input type="text" value="<%=bean.getHrmEmployeeActive() %>" maxlength="25" id="hrmEmployeeActive_<%=r %>"/></td>
	</tr>
	</table>
	</td>
	<!--必填项目结束 -->
	
	
	<!--选填项目结束 -->
	<td align="center">
	<table cellpadding="0" cellspacing="0" border="0" style="border: 0">
		<tr>
			<td nowrap="nowrap">身份证号：</td>
			<td><input type="text" value="<%=bean.getHrmEmployeeIdentitycard() %>" id="hrmEmployeeIdentitycard_<%=r %>"/></td>
			<td nowrap="nowrap">家庭地址：</td>
			<td><input type="text" value="<%=bean.getHrmEmployeeHouseAddress() %>" id="hrmEmployeeHouseAddress_<%=r %>"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap">移动电话：</td>
			<td><input type="text" value="<%=bean.getHrmEmployeeMobileTele() %>" id="hrmEmployeeMobileTele_<%=r %>"/></td>
			<td nowrap="nowrap">工作电话：</td>
			<td><input type="text" value="<%=bean.getHrmEmployeeWorkTele() %>" id="hrmEmployeeWorkTele_<%=r %>"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap">入职日期：</td>
			<td><input type="text" readonly="readonly"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<%=bean.getHrmEmployeeInTime() %>" id="hrmEmployeeInTime_<%=r %>" ></td>
			<td nowrap="nowrap">转正日期：</td>
			<td><input type="text" readonly="readonly"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<%=bean.getHrmEmployeeWorkTime() %>" id="hrmEmployeeWorkTime_<%=r %>" ></td>
		</tr>
	</table>
	</td>
	<!--选填项目结束 -->
	
	<!--提示信息 -->
	<td align="left" style="padding-left: 5px;color: red" id="empmsg_<%=r %>"><%=bean.getImpMsg() %></td>
	
	<td align="center" nowrap="nowrap">
	<a href="javascript:void(0)" onclick="saveemp(<%=r %>)" title="确认写入"><img src="<%=contextPath %>/images/png-1718.png" border="0"></a>
	<a href="javascript:void(0)" onclick="delrow(<%=r %>)" title="删除"><img src="<%=contextPath %>/images/del.gif" border="0"></a>
	</td>
	</tr>
	<%} %>
	</table>
	</td></tr></table>
	<br/>
	<%}} %>
	<%} %>
	</div>
	</div>
</body>
</html>