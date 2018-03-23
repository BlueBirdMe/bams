<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送短信</title>
<%
    String employeepk = request.getParameter("employeepk");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMoblieSmsService.js"></script> 
 <script type="text/javascript">
     window.onload = function(){
        initInput("helpTitle","短信提醒，您可以向在线人员发送短信。");
		dwrHrmEmployeeService.getEmployeeByPK(<%=employeepk%>,setEmployeeinfo);
	}

	
function setEmployeeinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var employee = data.resultList[0];
 			DWRUtil.setValue("hrmEmployeeName",employee.hrmEmployeeName);
 			DWRUtil.setValue("oaSmsSendAcpempName",employee.hrmEmployeeName);
 			DWRUtil.setValue("oaSmsSendAcpemp",employee.primaryKey);
		    DWRUtil.setValue("hrmEmployeeEngname",employee.hrmEmployeeEngname);
		    DWRUtil.setValue("hrmEmployeeCode",employee.hrmEmployeeCode);
		    var sexName ="";
		    if(employee.hrmEmployeeSex == <%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		       sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%>';
		    }else{
		      sexName = '<%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%>';
		    }
		    DWRUtil.setValue("hrmEmployeeSex",sexName);
		    
		    
		    DWRUtil.setValue("hrmEmployeeWorkareaid",employee.hrmEmployeeWorkarea.hrmAreaName);
		    

		    DWRUtil.setValue("hrmEmployeeDeptext",employee.hrmDepartment.hrmDepName);
		    
		    DWRUtil.setValue("hrmEmployeePostIdtext",employee.hrmEmployeePost.hrmPostName);

		    DWRUtil.setValue("hrmPartPosttext",employee.hrmPartPostName);
		    

			//照片显示
				var face = document.getElementById("hrmEmployeeImageInfoId");
				face.src+="&imgId="+employee.hrmEmployeeImageInfoId;
 		}
 	}
}	
	
	function closePage(){
	   closeMDITab();
	}
    /*
    var fckvalue ="";
    var fck;
    function FCKeditor_OnComplete(editorInstance) {
		fck= editorInstance;
		editorInstance.SetHTML(fckvalue);//初始赋值
		window.status = editorInstance.Description;
	}
    */
    function save(){
    //定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaSmsSendContentMust";
	//清空所有信息提示
	warnInit(warnArr);
	
      var bl = validvalue('helpTitle');
	     if(bl){
	        if(document.getElementById("oaSmsSendAcpempName").value == ""){
			   // DWRUtil.setValue("title","收件人不能为空");
			   setMustWarn("oaSmsSendAcpempMust","收件人不能为空");
				return false;
			}
	       if(DWRUtil.getValue("oaSmsSendContent") == "" || DWRUtil.getValue("oaSmsSendContent").length < 10){
				//DWRUtil.setValue("title","短信内容不能为空,且长度不能小于10个字符！");
				//fck.SetHTML("");
				setMustWarn("oaSmsSendContentMust","短信内容不能为空,且长度不能小于10个字符！");
				//fck.SetHTML("");
				return false;
			}
	        dwrMoblieSmsService.saveSmsSend(getSmsSendinfo(),saveCallback);
	      }
	}
	
	function getSmsSendinfo(){
	    var smsSend = new Object();
	    
	    smsSend.oaSmsSendAcpemp = DWRUtil.getValue("oaSmsSendAcpemp");
		smsSend.oaSmsSendAcpempName = DWRUtil.getValue("oaSmsSendAcpempName");
		smsSend.oaSmsSendContent = DWRUtil.getValue("oaSmsSendContent");
	    
	    //smsSend.CimsSmsSendAcpemp = DWRUtil.getValue("oaSmsSendAcpemp")+",";
	    //smsSend.CimsSmsSendAcpempName = DWRUtil.getValue("oaSmsSendAcpempName");
	    //smsSend.CimsSmsSendContent = DWRUtil.getValue("oaSmsSendContent");
	    return smsSend;
	}
	
	function saveCallback(data){
	if(data.success){
		confirmmsgAndTitle("发送短信成功！是否想继续添加？","cleanup();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
    }
    
    function cleanup(){
    	DWRUtil.setValue("oaSmsSendContent","");
    }

</script>
</head>
 
<body class="inputcls">
 	<div class="formDetail">
		<div class="requdivdetail"><label>查看帮助:&nbsp; 显示人员相关信息，点击附件可下载附件！</label></div>
		<div class="detailtitle">人员信息</div>
		<table class="detailtable" align="center">
			<tr>
				<th width="15%">员工编号</th>
				<td id="hrmEmployeeCode" class="detailtabletd" width="25%"></td>
				<th rowspan="4" style="text-align: right;padding-bottom: 65px;">照片</th>
				<td  rowspan="4" style="text-align: left;"><file:imgshow  id="hrmEmployeeImageInfoId" height="128"></file:imgshow></td>
			</tr>
			<tr>
				<th>姓&nbsp;名</th>
				<td id="hrmEmployeeName" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>英文名</th>
				<td id="hrmEmployeeEngname" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>性&nbsp;别</th>
				<td id="hrmEmployeeSex" class="detailtabletd"></td>
			</tr>
			<tr>
			
			<tr>
				<th>部&nbsp;门</th>
				<td id="hrmEmployeeDeptext" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>工作岗位</th>
				<td id="hrmEmployeePostIdtext" class="detailtabletd"></td>
				
			</tr>
			<tr>
				<th>兼职岗位</th>
				<td id="hrmPartPosttext" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>工作地区</th>
				<td id="hrmEmployeeWorkareaid" class="detailtabletd"></td>
			</tr>
		</table>
	</div>
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">发送短信</div>
		<div>
			<table class="inputtable" border="0">
				<tr>
				<th width="15%">收件人</th>
				<td>
				<div  id="oaSmsSendAcpempName" style="width: 90%"></div>
				<input type="hidden" id="oaSmsSendAcpemp">
				<label id="oaSmsSendAcpempMust"></label>
				</td>
			</tr>			
			<tr>
			  <th width="15%">发送方式</th>
			  <td>
			    <input type="checkbox" id="moblie">手机短信
			  </td>
			</tr>
			<tr><th></th><td>
			<label id="oaSmsSendContentMust"></label></td></tr>
			<tr>
			<th><em>* </em>短信内容</th>
			<td>
			<textarea id="oaSmsSendContent"></textarea>
			<!--<FCK:editor instanceName="oaSmsSendContent" width="90%" toolbarSet="Basic" height="100px"></FCK:editor>-->
			</td>
			</tr>
			</table>
		</div>

<br/>

</div>
<br>
<center>
<table>
<tr>
	<td><btn:btn onclick="save()" value="发 送 " imgsrc="../../images/fileokico.png" title="发送短信息"></btn:btn></td>
	<td style="width: 10px;"></td>
	<td><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
</tr>
</table>
</center>
</body>
  
</html>
