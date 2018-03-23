<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<title>发布通知</title>
<%
	String noticepk =request.getParameter("noticepk");
	String noid = request.getParameter("noid");
	String isedit = "false";
	if(noticepk!=null){//编辑时使用
		isedit = "true";
	}
%>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('helpTitle','在此处您可以添加您要发布的通知，通知有效时可被他人阅读！');
	saveOredit();
	tmp="请输入新闻内容";

    //第一个输入框获取焦点
    document.getElementById("oaNotiName").focus();
}

function backToNoticeList(){
   window.location = "notice_manager.jsp";
}

function getNoticeinfo(){
	var notice = new Object();
   	if(<%=noticepk%> != null){
      notice.primaryKey = <%=noticepk%>;
    }
    
	notice.oaNotiName = DWRUtil.getValue("oaNotiName");
	notice.oaNotiType = getRadioValueByName("oaNotiType");	//获取radio的值
	notice.oaObjEmp = DWRUtil.getValue("Employeeid");
	notice.oaObjDep = DWRUtil.getValue("hrmDepid");
	notice.oaNotiText = fck.GetXHTML();
	
	return notice;
}
function save(){
    //定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaObjDepMust";
	warnArr[1] = "oaObjEmpMust";
	warnArr[2] = "oaNotiTextMust";
	//清空所有信息提示
	warnInit(warnArr);
	
	//验证常用组件
    var validResult = validvalue('helpTitle');
    if(validResult){
          var context = fck.GetXHTML();
          if(DWRUtil.getValue("Employeeid") == "" && DWRUtil.getValue("hrmDepid") == ""){
               //可选框提示
         	   var chooseArr = new Array();
			   chooseArr[0] = "oaObjDepMust";
			   chooseArr[1] = "oaObjEmpMust";
			   chooseWarn(chooseArr);
              
               //返回顶端
               window.scrollTo(0,0);
               //DWRUtil.setValue("aaac","阅读范围至少一个不能为空！");
          }else if(trim(context) == "" || trim(context).length < 10){
               setMustWarn("oaNotiTextMust","通知内容不能为空,且通知内容至少10个字符!");
               fck.Focus();
          }else{
               if(<%=noticepk%> != null){
               		Btn.close();
	                var acce =   document.getElementById("oaNotiAcce").value;
	                dwrOaNewsService.updateNotice(getNoticeinfo(),acce,updateCallback);
	           }else{ 
	           		Btn.close();
	                var acce =   document.getElementById("oaNotiAcce").value;
	                dwrOaNewsService.saveNotice(getNoticeinfo(),acce,saveCallback);
	           }
          }   
    } 
}
	
function saveCallback(data){
	Btn.open();
	document.getElementById("oaNotiName").focus();
	confirmmsgAndTitle("添加通知成功！是否继续添加?","reloadpager();","继续添加","closePage();","关闭页面");
}

//修改成功后处理
function updateCallback(data){
	alertmsg(data,"reloadpager();");
}

function reloadpager(){
    if(<%=noticepk%> != null){
    	window.parent.MoveDiv.close();
    	 window.parent.queryData();
         //reload();
    }else{
         DWRUtil.setValue("oaNotiName","");
         DWRUtil.setValue("oaObjDep","");
         DWRUtil.setValue("oaObjEmp","");
         DWRUtil.setValue("hrmDepid","");
         DWRUtil.setValue("Employeeid","");
         //刷新附件
		 Sys.setFilevalue("oaNotiAcce","");
         fck.SetHTML("");
         
         document.getElementById("oaNotiName").focus();
    }
}

function  saveOredit(){
     if(<%=noticepk%> != null){
		var primaryKey = <%=noticepk%>;
		dwrOaNewsService.getNoticeByPk(primaryKey,setNoticeinfo);
		
		//返回列表按钮隐藏
	    var backBtn = document.getElementById("backToList");
	    backBtn.style.display = "none";
	 }else{
	    var btn = document.getElementById("backbtn");
	    btn.style.display = "none";
	 }
}

var fckvalue ="";

function setNoticeinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var notice = data.resultList[0];
 			DWRUtil.setValue("oaNotiName",notice.oaNotiName);
 			setRadioValueByName("oaNotiType",notice.oaNotiType);	//设置radio的值
 			DWRUtil.setValue("Employeeid",notice.oaObjEmp);
 			DWRUtil.setValue("hrmDepid",notice.oaObjDep);
 			DWRUtil.setValue("oaObjEmp",notice.empLIst);
 			DWRUtil.setValue("oaObjDep",notice.depList);
 			
 			//显示通知附件
 			if(notice.oaNotiAcce != null && notice.oaNotiAcce  != undefined && notice.oaNotiAcce .length > 0){
				dwrCommonService.getAttachmentInfoListToString(notice.oaNotiAcce,setaccept);
			}
			
 			fckvalue = notice.oaNotiText;
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}

//放入附件
function setaccept(data){
	Sys.setFilevalue("oaNotiAcce",data);
}

var fck;

function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function getupcode(){
	if(<%=noticepk%> != null){
		var box = SEL.getDeptIds("check","oaObjDep","hrmDepid","processloadfrm");
		box.show();
	}else{
		var box = SEL.getDeptIds("check","oaObjDep","hrmDepid");
		box.show();
	}
}

function getemployee(){
	if(<%=noticepk%> != null){
		var box = SEL.getEmployeeIds("check","oaObjEmp","Employeeid","processloadfrm");
		box.show();
	}else{
		var box = SEL.getEmployeeIds("check","oaObjEmp","Employeeid");
		box.show();
	}
}

function reload(){
    if(<%=noid%> == '1'){
         Sys.load('<%=contextPath%>/erp/issue_info/notice_manager.jsp');
    }else{
         Sys.load('<%=contextPath%>/erp/issue_info/notice_info.jsp');
    }
}

function closePage(){
	closeMDITab();
}
</script>
</head>
<body  class="inputcls">
<div class="formDetail">
	<div class="requdiv"><label id="helpTitle"></label></div>
    <div class="formTitle">通知内容</div>
	<div>
	    <table class="inputtable" border="0">
	    <tr>
			<th width="20%"><em>*</em>&nbsp;&nbsp;通知标题</th>
			<td width="40%" style="text-align: left;"><input type="text" id="oaNotiName" must="通知标题不能为空！" formust="oaNotiNameMust" value="" style="width:90%;" maxlength="50" ></td>
			<td width="40%"><label id="oaNotiNameMust"></label></td>
		</tr>
		<tr>
		    <th>&nbsp;&nbsp;通知类型</th>
			<td style="text-align: left;"><%=UtilTool.getRadioOptionsByEnum(EnumUtil.OA_NOTICE_TYPE.getSelectAndText(""),"oaNotiType")%></td>
			<td></td>
		</tr>
		<tr>
		<th><span style="color:blue">•</span>&nbsp;&nbsp;阅读范围<br/>(部门)</th>
		    <td ><textarea  id="oaObjDep" readonly="readonly" linkclear="hrmDepid" onclick="getupcode();" style="color: #999;" title="点击选择部门"></textarea>
		    <input type="hidden" id="hrmDepid" value="">
		    </td>
		    <td><label id="oaObjDepMust"></label></td>
		</tr>
		<tr>
		    <th><span style="color:blue">•</span>&nbsp;&nbsp;阅读范围<br/>(人员)</th>
		    <td ><textarea  id="oaObjEmp" linkclear="Employeeid" title="点击选择人员" readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
		    <input type="hidden" id="Employeeid" value="">
		    </td>	
		    <td><label id="oaObjEmpMust"></label></td>
		</tr>
		<tr>
		    <th>附件</th>
		    <td  colspan="3">
		    <file:multifileupload width="90%" acceptTextId="oaNotiAcce" height="100" edit="<%=isedit %>"></file:multifileupload>
		    </td>
		</tr>
		<tr>
		    <th><em>*</em>&nbsp;&nbsp;内  容</th>
			<td style="text-align: left" colspan="2">
				<label id="oaNotiTextMust"></label>
				<FCK:editor instanceName="oaNotiText" width="90%" height="250"></FCK:editor>
			</td>
		</tr>
	    </table>
		<br/>
	</div>
</div>
<br/>
	<table align="center">
		<tr>
			<td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存通知内容" /></td>
			<td style="width: 10px;"></td>
			<td><DIV id ="backToList"><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></DIV></td>
			<td ><DIV id ="backbtn"><btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></DIV></td>
		</tr>
	</table>
</body>
</html>