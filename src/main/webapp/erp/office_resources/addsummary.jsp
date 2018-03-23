<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String meetapplypk =request.getParameter("meetapplypk");
 %>
<title>添加编辑页面</title>
<style type="text/css">
	body {
	background-color: #fefefe;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		useLoadingMassage();
		initInput('aaac');
	}
	

   	    
function save(){ 
	var bl = validvalue('aaac');//验证id=aaac的lable下的带有must属性的项
	if(bl){
		if(DWRUtil.getValue("oaWorkplanEmps_s") == ""){
         	DWRUtil.setValue("aaac","请指定读者！");
    	}else if(DWRUtil.getValue("oaSummaryContent") == ""){
    		DWRUtil.setValue("aaac","会议纪要附件不能为空！");
    	}else{ 
			dwrOfficeResourcesService.saveSummary(getSummaryinfo(),saveCallback);
		}
	}
	
}

function getSummaryinfo(){
    var summary = new Object();
    summary.oaSummaryMeetId= <%=meetapplypk%>;
    summary.oaSummaryName = DWRUtil.getValue("oaSummaryName");
    summary.oaSummaryMeetDate = DWRUtil.getValue("oaSummaryMeetDate");
    summary.oaSummaryReader = DWRUtil.getValue("oaWorkplanEmps_s")
    summary.oaSummaryContent = DWRUtil.getValue("oaSummaryContent");
    return summary;
}

function saveCallback(data){
	alertmsg("添置成功","clearContext()");
}	

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}

function clearContext(){
	DWRUtil.setValue("oaSummaryName","");
	DWRUtil.setValue("oaSummaryReader","");
	DWRUtil.setValue("oaWorkplanEmps_s","");
	Sys.setFilevalue("oaSummaryContent","");
}

 
 
 
    

var fckvalue="";       
function setSummaryinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var summary = data.resultList[0];
 		 DWRUtil.setValue("oaSummaryName",summary.oaSummaryName);
	     DWRUtil.setValue("oaSummaryDate",summary.oaSummaryDate);
	     DWRUtil.setValue("oaSummaryReader",summary.oaSummaryReader);
	     
	     fckvalue = summary.oaSummaryContent;
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}
	
	
function selectEmps(){
	var box = new Sys.msgbox('指定读者','<%=contextPath%>/erp/select_takepage/select_emp.jsp?treetype=check&textid=oaSummaryReader&valueid=oaWorkplanEmps_s','700','500');
	box.msgtitle="<b></b><br/>选择阅读人员";
	var butarray = new Array();
	butarray[0] = "ok|employeeclick();";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
   }
   

function reload(){
       Sys.load("<%=contextPath%>/erp/office_resources/meet_summary.jsp");
   }
</script>
</head>
<body>

<fieldset>
	<div class="requdiv"><label id="aaac"></label></div>
	<legend>会议纪要</legend>
	<div>
	<table class="inputtable">
	<tr>
	<th><em>*</em>纪要名称</th>
	<td style="text-align: left;" colspan="3"><input type="text" id="oaSummaryName" must="请输入纪要名称" maxlength="50" style="width: 90%;"></td>
	</tr>													
	
	<tr>
	<th><em>*</em>指定读者</th>
	<td colspan="3">
	<textarea id="oaSummaryReader" readonly="readonly" onclick="selectEmps()" style="color: #999" >双击选择</textarea>
	<input type="hidden" id="oaWorkplanEmps_s">
	</td>	
	</tr>
	<tr>
	<th><em>*</em>纪要附件</th><td style="text-align: left" colspan="3">
	<file:multifileupload width="90%" acceptTextId="oaSummaryContent" height="120"></file:multifileupload>
	</td>
	</tr>
	<tr>
	<th><em>*</em>纪要内容</th><td style="text-align: left" colspan="3">
	<textarea width="90%" id="oaSummaryNeirong" style="height: 150px" ></textarea>
	</td>
	</tr>
	</table>	
	</div>
</fieldset>
<br/>
<table align="center">
   <tr>
     <td><btn:btn onclick="save()" value=" 确  定 "></btn:btn></td>
     <td style="width: 10px;"></td>
     <td><btn:btn onclick="reload()" value=" 返  回 "></btn:btn></td>
   </tr>
</table>
</body>
</html>