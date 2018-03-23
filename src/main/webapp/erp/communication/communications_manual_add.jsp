<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
<title>新增通讯</title>
<%   String iedit = "false";
     String chatId = request.getParameter("chatId");
     if(chatId != null){
        iedit = "true";
     }
     String groupid = request.getParameter("groupid");
 %>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	//第一个输入框获取焦点
	document.getElementById("oaChatEmp").focus();
	initInput('aaac',"您可以在此处添加联系人，可在我的通讯录里查看已添加所有联系人信息！");
	saveoredit();
	show1();
}
function saveoredit(){
    if(<%=chatId%> != null){
		 var primaryKey = <%=chatId%>;
		 dwrOaCommunicationService.getCommunicationById(primaryKey,setCommunicationinfo);
	 }
	if(<%=groupid%> != null){
	    DWRUtil.setValue("oaChatGroup",<%=groupid%>);
	}
}

function getCommunicationinfo(){
	var chatter = new Object();
   	if(<%=chatId%> != null){
    	chatter.primaryKey = <%=chatId%>;
    }
	chatter.oaChatEmp = document.getElementById("oaChatEmp").value;
	chatter.oaChatSex = getRadioValueByName("sex");
	chatter.oaChatCom = document.getElementById("oaChatCom").value;
	chatter.oaChatGroup = document.getElementById("oaChatGroup").value;
	chatter.oaChatAddress = document.getElementById("oaChatAddress").value;
	chatter.oaHomeTel = document.getElementById("oaHomeTel").value;
	chatter.oaChatMobile = document.getElementById("oaChatMobile").value;
	chatter.oaWorkTel = document.getElementById("oaWorkTel").value;
	chatter.oaChatQq = document.getElementById("oaChatQq").value;
	chatter.oaChatMsn = document.getElementById("oaChatMsn").value;
	chatter.oaChatEmail = document.getElementById("oaChatEmail").value;
	chatter.oaChatPhotos = document.getElementById("oaChatPhotos").value;
	chatter.oaIsShare = getRadioValueByName("chatShare");
	chatter.oaShareEmp = document.getElementById("shareEmpIds").value;
	return chatter;
}

function save(){
    var warnArr = new Array();
	warnArr[0] = "sexmust";
	warnArr[1]="chatShareEmpmust";
	warnArr[2]="telephone";
	//清空所有信息提示
	warnInit(warnArr);
     var bl = validvalue('aaac');
     if(bl){
           var sex=getRadioValueByName("sex");
            if(sex==null){
            
            setMustWarn("sexmust","联系人性别不能为空！");
            
            return false;
            }
           	var telephone=document.getElementById("oaHomeTel").value;
     		if(telephone.length<=0){
     		   telephone= document.getElementById("oaChatMobile").value;
     		    if(telephone.length<=0){
     		         telephone= document.getElementById("oaWorkTel").value;
     		            if(telephone.length<=0){
     		                setMustWarn("telephone","联系电话至少一项不能为空！");
     		                return false;
     		            }
     		    }
     		}
           var isShare = getRadioValueByName("chatShare");
           if(isShare == <%=EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value%> && document.getElementById("shareEmpIds").value == ""){
                
                   setMustWarn("chatShareEmpmust","共享状态下共享人员不能为空！");
           }else{
                 if(<%=chatId%> != null){
	                  dwrOaCommunicationService.updateCommunication(getCommunicationinfo(),saveCallback);
	                  Btn.close();
	             }else{ 
	                  dwrOaCommunicationService.saveCommunication(getCommunicationinfo(),saveCallback);
	             	  Btn.close();
	             }
           }
	 }
}
function backToNewsList(){
	 	Sys.href('<%=contextPath%>/erp/communication/communications_manual.jsp');
}
function saveCallback(data){
  		 Btn.open();
  	if(<%=chatId%> != null){
  		 alertmsg(data,"reload()");
    }else{
    	if(data.success){
			confirmmsgAndTitle("添加联系人成功！是否想继续添加?","reloadpager();","继续添加","closePage();","关闭页面");
		}else{
			 alertmsg(data);
		}
	}
}
function  reloadpager(){
       document.getElementById("oaChatEmp").value = "";
       document.getElementById("oaChatCom").value = "";
       document.getElementById("oaChatAddress").value = "";
       document.getElementById("oaChatMobile").value = "";
       document.getElementById("oaWorkTel").value = "";
       document.getElementById("oaChatQq").value = "";
       document.getElementById("oaChatMsn").value = "";
       document.getElementById("oaChatEmail").value = "";
       document.getElementById("oaHomeTel").value = "";
       document.getElementById("chatShareEmp").value = "";
       document.getElementById("shareEmpIds").value = "";
       //setRadioValueByName("sex","");
       document.getElementById("oaChatGroup").selectedIndex =0;
       Sys.setFilevalue("oaChatPhotos","");
       document.getElementById("oaChatEmp").focus();
	   refreshMDITab(<%=request.getParameter("tab")%>);
}


function setCommunicationinfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var commnuication = data.resultList[0];
 			DWRUtil.setValue("oaChatEmp",commnuication.oaChatEmp);
 			
 			setRadioValueByName("sex",commnuication.oaChatSex);
 			DWRUtil.setValue("oaChatCom",commnuication.oaChatCom);
 			DWRUtil.setValue("oaChatGroup",commnuication.oaChatGroup);
 			DWRUtil.setValue("oaChatAddress",commnuication.oaChatAddress);
 			DWRUtil.setValue("oaHomeTel",commnuication.oaHomeTel);
 			DWRUtil.setValue("oaChatMobile",commnuication.oaChatMobile);
 			DWRUtil.setValue("oaWorkTel",commnuication.oaWorkTel);
 			DWRUtil.setValue("oaChatQq",commnuication.oaChatQq);
 			DWRUtil.setValue("oaChatMsn",commnuication.oaChatMsn);
 			DWRUtil.setValue("oaChatEmail",commnuication.oaChatEmail);
 			setRadioValueByName("chatShare",commnuication.oaIsShare);
 			DWRUtil.setValue("chatShareEmp",commnuication.shareEmpName);
 			DWRUtil.setValue("shareEmpIds",commnuication.oaShareEmp);
 			if(commnuication.oaChatPhotos != null && commnuication.oaChatPhotos != undefined && commnuication.oaChatPhotos.length > 0){
 			      dwrCommonService.getImageInfoListToString(commnuication.oaChatPhotos,setImage);
 			}
 			//如果是共享的，显示共享框！
 			if(commnuication.oaIsShare == 1){
 			show2();
 			}
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}
function setImage(data){
   Sys.setFilevalue("oaChatPhotos",data);
}

function getemployee(){
    if(<%=chatId%> != null){
         var box = SEL.getEmployeeIds("check","chatShareEmp","shareEmpIds","processloadfrm");
		 box.show();
    }else{
         var box = SEL.getEmployeeIds("check","chatShareEmp","shareEmpIds");
	     box.show();
    }
}
function show1(){
	DWRUtil.setValue("chatShareEmp","");
 	DWRUtil.setValue("shareEmpIds","");
	$("#chatShareObj").animate({height: 'hide',   opacity: 'hide'  }, 'slow');
}

function show2(){
	$("#chatShareObj").animate({height: 'show',opacity: 'show'  }, 'slow');
}

function reload(){
    if(<%=chatId%> != null){
       	window.parent.MoveDiv.close();
       	window.parent.queryData();
    }
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="aaac"></label></div>
	<div class="formTitle">
	新增/编辑通讯手册
	</div>
	<table class="inputtable">
	<tr>
		<th><em>* </em>姓 名</th>
		<td style="text-align: left;"><input type="text" id="oaChatEmp" must="姓名不能为空!" value="" maxlength="10" formust="oaChatEmpmust">
		<label id="oaChatEmpmust"></label>
		</td>
		<td rowspan="6" style="text-align: right;">照片</td>
		<td rowspan="6" >
		<file:imgupload width="150px" acceptTextId="oaChatPhotos" height="160" edit="<%=iedit%>" ></file:imgupload>
		</td>
	</tr>
	<tr>
		<th>性 别</th>
		<td style="text-align: left;"><input type="radio" name="sex" value="1" id="radio1" checked="checked"><label for="radio1">男</label>&nbsp;
		 <input type="radio" name="sex" value="2" id="radio2"><label for="radio2">女</label>
		 <label id="sexmust"></label>
		</td>
	</tr>
	<tr>
		<th>单 位</th>
		<td style="text-align: left;"><input type="text" id="oaChatCom"  value="" maxlength="25"></td>
	</tr>
	<tr>
		<th>所属分组</th>
		<td style="text-align: left;"><select must="请选择所属分组" id="oaChatGroup" formust="oaChatGroupmust"><%=UtilTool.getCommunSelectOptions(this.getServletContext(),request,null)%></select>
		<label id="oaChatGroupmust"></label>
		</td>
	</tr>
	
	<tr>
	<th></th>
		<td>
		<label id="telephone"></label>
		</td>
	</tr>
	<tr>
		<th><span style="color:blue">•</span>&nbsp;家庭电话</th>
		<td style="text-align: left">
		<input type="text"  id="oaHomeTel"  value="" maxlength="20">
		</td>
	</tr>
	<tr>
		<th><span style="color:blue">•</span>&nbsp;移动电话</th>
		<td style="text-align: left;"><input type="text" id="oaChatMobile"  value="" maxlength="20">
		</td>
	</tr>
	<tr>
		<th><span style="color:blue">•</span>&nbsp;工作电话</th>
		<td style="text-align: left;">
			<input type="text" id="oaWorkTel"  value="" maxlength="20">
		</td>
		<th>QQ</th>
		<td style="text-align: left;">
			<input type="text" id="oaChatQq"  value="" maxlength="12" class="numform">
		</td>
	</tr>
	<tr>
		<th>MSN</th>
		<td style="text-align: left;"><input type="text" id="oaChatMsn"  value="" maxlength="25">
		</td>
		<th>EMAIL</th>
		<td style="text-align: left;"><input type="text" id="oaChatEmail"  value="" maxlength="25">
		</td>
	</tr>
	<tr>
		<th>家庭地址</th>
		 
		 <td style="text-align: left;" colspan="3">
		 <input type="text" id="oaChatAddress" style="width: 74%" maxlength="50">
	</td>
	</tr>
	<tr>
		<th>是否共享</th>
		<td style="text-align: left;">
	   <input  type="radio" name="chatShare" value="2" checked="checked" onclick="show1()" id="chatShare1"><label for="chatShare1">私有</label>
	   <input type="radio" name="chatShare" value="1" onclick="show2()" id="chatShare2"><label for="chatShare2">共享</label>
		</td>	
	</tr>
	<tr>
	    <td colspan="4" width="100%" style="padding: 0;margin: 0">
		<div id="chatShareObj">
			<table style="width: 100%">
			<tr>
				<th></th>
				<td>
				<label id="chatShareEmpmust"></label>
				</td>
			</tr>
				<tr>
					<th width="15%"><em>* </em>共享人员</th>
					<td>
					<textarea  id="chatShareEmp" linkclear="ShareEmpIds" title="点击选择人员" readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
					<input type="hidden" id="shareEmpIds" value="">
					</td>
				</tr>
			</table>
	   </div>
	   </td>
	</tr>
	</table>
	</div>
<table align="center">
<tr>
    <td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    <td style="width: 10px;"></td>
	<td>
	<td>
	<%if (chatId == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
</tr>
</table>
</body>
</html>