<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common.jsp" %>
    <%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrMailService.js"></script>
<%
   String backtype = request.getParameter("backtype");
   String mailtype = request.getParameter("mailtype"); //由收件明细页面传入 1 回复 2 全部回复 3 重发 4 转发 5 在线人员
   String mailEmpPk = request.getParameter("mailempPk");
   String choose = request.getParameter("choose");
   String oaSmsOnlineid =request.getParameter("oaSmsOnlineid");
   String edited = request.getParameter("isEdit");
   
   String isedit = "false";
   if(mailtype != null &&!"".equals(mailtype)){//重发时使用或者 转发
	  if(mailtype.equals("3") || mailtype.equals("4")){
	   isedit = "true";
	  }
   }
   
   //从session中取当前用户的id,用于全部回复中收件人的剔除
   String employeeid = UtilTool.getEmployeeId(request);

 %>
<title>邮件发送</title>

<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('title','您可以在此处发送内部邮件！系统内部人员可以查阅！');
	saveOredit();
	document.getElementById("oaMailSendTitle").focus();
	if(<%=choose%>!=null && document.getElementById("btncancel")!=null){
		document.getElementById("btncancel").style.display="none";
	}
	if(<%=edited%> == 1 || <%=mailtype%> == 5){
	    var btn = document.getElementById("backbtn");
	    if(btn!=null){
	    	btn.style.display = "none";
	    }
	}else{
		var btn = document.getElementById("cancelBtn");
	    if(btn!=null){
	    	btn.style.display = "none";
	    }
	}
}
	
var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

function saveOredit(){
	if(<%=mailtype%> != null && <%=mailtype%> != <%=EnumUtil.OA_MAIL_MAILTYPE.THREE.value%> && <%=mailtype%> != <%=EnumUtil.OA_MAIL_MAILTYPE.FIVE.value%> ){
		  if(<%=mailEmpPk%> != null){
		    dwrMailService.getMailInboxByMailEmpPk(<%=mailEmpPk%>,setMialInboxinfo);
		  }
	}else if(<%=mailtype%> != null && <%=mailtype%> == <%=EnumUtil.OA_MAIL_MAILTYPE.THREE.value%>){
	     if(<%=mailEmpPk%> != null){
		    dwrMailService.getMailOutboxByPk(<%=mailEmpPk%>,setMailOutboxinfo);
		  }
	}
	 
	if('<%=oaSmsOnlineid%>' != 'null' && <%=mailtype%> == <%=EnumUtil.OA_MAIL_MAILTYPE.FIVE.value%>){	
		var primaryKey = '<%=oaSmsOnlineid%>';
		dwrMailService.listEmployee(primaryKey,setOnlineInfo);
	}
 }

	function setOnlineInfo(data){
	    if(data.success == true){
	 		if(data.resultList.length > 0){
	 			var employee = data.resultList[0];
	 			DWRUtil.setValue("oaMailSendEmpids",employee.primaryKey+",");
	            DWRUtil.setValue("oaMailSendEmpNames",employee.hrmEmployeeName+",");	
	 		}else{
	 			alert(data.message);
	 		}
	 	}else{
	 		alert(data.message);
	 	}
	}

	
function setMialInboxinfo(data){
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			if(<%=mailtype%> == <%=EnumUtil.OA_MAIL_MAILTYPE.ONE.value%>){  //回复
			
			  	DWRUtil.setValue("oaMailSendEmpNames",tmp.oaMailEmpInboxid.oaMailInboxSendName+",");
			  	DWRUtil.setValue("oaMailSendEmpids",tmp.oaMailEmpInboxid.oaMailInboxSendid+",");
			  
			   	DWRUtil.setValue("oaMailSendTitle","Re:"+tmp.oaMailEmpInboxid.oaMailInboxTitle);
				//fck
				var content = "==============="+tmp.oaMailEmpInboxid.oaMailInboxSendName+"在"+tmp.oaMailEmpInboxid.oaMailInboxSendtime+"的来信中写道：=============="+"</br></br>"
				+tmp.oaMailEmpInboxid.oaMailInboxContent+"</br>";
				fckvalue = content;
				if(fck!=null){
					fck.SetHTML(fckvalue);
				}
			}else if(<%=mailtype%> == <%=EnumUtil.OA_MAIL_MAILTYPE.TWO.value%>){  //全部回复
					
			  	//去除重复的名字和id
				var names = tmp.oaMailEmpInboxid.oaMailInboxSendName+","+tmp.oaMailEmpInboxid.oaMailInboxEmpNames;
				var ids = tmp.oaMailEmpInboxid.oaMailInboxSendid+","+tmp.oaMailEmpInboxid.oaMailInboxEmpids;
			
				
			  	var values = removerepeat(ids,names);
		
				//去除当前人的名字和id
				if('<%=employeeid%>' != null){
			    	values = removeItemFromIds('<%=employeeid%>',values[0],values[1]);
			  	}
				DWRUtil.setValue("oaMailSendEmpNames",values[1]);
				DWRUtil.setValue("oaMailSendEmpids",values[0]);
				if(tmp.oaMailEmpInboxid.oaMailInboxEmpCSids != "" && tmp.oaMailEmpInboxid.oaMailInboxEmpCSids != null){//抄送人
					DWRUtil.setValue("oaMailSendEmpCSids",tmp.oaMailEmpInboxid.oaMailInboxEmpCSids);
		        	DWRUtil.setValue("oaMailSendEmpCSNames",tmp.oaMailEmpInboxid.oaMailInboxEmpCSNames);
					document.getElementById("mailcscheck").checked = true;
					mailcsChange();
				}
				DWRUtil.setValue("oaMailSendTitle","Re:  "+tmp.oaMailEmpInboxid.oaMailInboxTitle);
				var content = "==============="+tmp.oaMailEmpInboxid.oaMailInboxSendName+"在"+tmp.oaMailEmpInboxid.oaMailInboxSendtime+"的来信中写道：=============="+"</br></br>"
				+tmp.oaMailEmpInboxid.oaMailInboxContent+"</br></br>";
				fckvalue = content;
				if(fck!=null){
					fck.SetHTML(fckvalue);
				}
			}else{  //4 转发
				DWRUtil.setValue("oaMailSendTitle",tmp.oaMailEmpInboxid.oaMailInboxTitle);
				var content = tmp.oaMailEmpInboxid.oaMailInboxContent;
				fckvalue = content;
				if(fck!=null){
					fck.SetHTML(fckvalue);
				}
				//附件
				if(tmp.oaMailEmpInboxid.oaMailInboxAffix!=null && tmp.oaMailEmpInboxid.oaMailInboxAffix!=undefined && tmp.oaMailEmpInboxid.oaMailInboxAffix!= "undefined" && tmp.oaMailEmpInboxid.oaMailInboxAffix.length>0){
					dwrCommonService.getAttachmentInfoListToString(tmp.oaMailEmpInboxid.oaMailInboxAffix,setaccept);
				}
			}
		}
	}
}
	
function setMailOutboxinfo(data){  //重发
	if(data!=null){
		if(data.resultList.length>0){
			var tmp = data.resultList[0];
			DWRUtil.setValue("oaMailSendEmpids",tmp.oaMailSendEmpids);
			DWRUtil.setValue("oaMailSendEmpNames",tmp.oaMailSendEmpNames);
			if(tmp.oaMailSendEmpCSids != "" && tmp.oaMailSendEmpCSids != null){
				DWRUtil.setValue("oaMailSendEmpCSids",tmp.oaMailSendEmpCSids);
				DWRUtil.setValue("oaMailSendEmpCSNames",tmp.oaMailSendEmpCSNames);
			    document.getElementById("mailcscheck").checked = true;
                mailcsChange();
			}
			if(tmp.oaMailSendEmpMSids != "" && tmp.oaMailSendEmpMSids != null){
				DWRUtil.setValue("oaMailSendEmpMSids",tmp.oaMailSendEmpMSids);
				DWRUtil.setValue("oaMailSendEmpMSNames",tmp.oaMailSendEmpMSNames);
				document.getElementById("mailmscheck").checked = true;
                mailmsChange();
			}
			if(tmp.oaMailInboxReceipt != null && tmp.oaMailInboxReceipt == <%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>){
			    document.getElementById("oaMailInboxReceipt").checked = true;
			}
			
			setRadioValueByName("oaMailSendIsurgent",tmp.oaMailSendIsurgent)
		
			DWRUtil.setValue("oaMailSendTitle",tmp.oaMailSendTitle);
			
			//fckvalue临时
			fckvalue = tmp.oaMailSendContent;
			if(fck!=null){
				fck.SetHTML(fckvalue);
			}
		   //附件
			if(tmp.oaMailSendAffix!=null && tmp.oaMailSendAffix!=undefined && tmp.oaMailSendAffix!= "undefined" && tmp.oaMailSendAffix.length>0){
				dwrCommonService.getAttachmentInfoListToString(tmp.oaMailSendAffix,setaccept);
			}
		}
	}
}

//放入附件
function setaccept(data){
	Sys.setFilevalue("oaMailSendAffix",data);
}

function getOaMailSendinfo(){
	var oaMailSend = new Object();
	oaMailSend.oaMailSendEmpids = DWRUtil.getValue("oaMailSendEmpids"); //收件人id集合
	oaMailSend.oaMailSendTitle = DWRUtil.getValue("oaMailSendTitle");
	oaMailSend.oaMailSendIsurgent = getRadioValueByName("oaMailSendIsurgent");
	oaMailSend.oaMailSendAffix = DWRUtil.getValue("oaMailSendAffix");
	oaMailSend.oaMailSendEmpNames = DWRUtil.getValue("oaMailSendEmpNames");
	if(document.getElementById("mailcscheck").checked){
	    oaMailSend.oaMailSendEmpCSids = DWRUtil.getValue("oaMailSendEmpCSids");
	    oaMailSend.oaMailSendEmpCSNames = DWRUtil.getValue("oaMailSendEmpCSNames");
	}else{
	    oaMailSend.oaMailSendEmpCSids = "";
	    oaMailSend.oaMailSendEmpCSNames = "";
	}
	if(document.getElementById("mailmscheck").checked){
	    oaMailSend.oaMailSendEmpMSids = DWRUtil.getValue("oaMailSendEmpMSids");
	    oaMailSend.oaMailSendEmpMSNames = DWRUtil.getValue("oaMailSendEmpMSNames");
	}else{
	    oaMailSend.oaMailSendEmpMSids = "";
	    oaMailSend.oaMailSendEmpMSNames = "";
	}
	if(document.getElementById("oaMailInboxReceipt").checked){
	    oaMailSend.oaMailInboxReceipt = <%=EnumUtil.OA_MAIL_RECEIPT.ONE.value%>;
	}else{
	    oaMailSend.oaMailInboxReceipt = <%=EnumUtil.OA_MAIL_RECEIPT.TWO.value%>;
	}

	oaMailSend.oaMailSendContent = fck.GetXHTML();
	
	return oaMailSend;
  }
  
function save(index){
	var warnArr = new Array();
	warnArr[0] = "oaMailSendEmpNamesmust";
	warnInit(warnArr);
	var bl = validvalue('title');
	if(bl){
        if(document.getElementById("oaMailSendEmpids").value == ""){      
		    setMustWarn("oaMailSendEmpNamesmust","收件人不能为空。");
			window.scrollTo(0,0);
			return false;
		}
	    var oaMailSend = getOaMailSendinfo();
        oaMailSend.oaMailSendType = index;
        var isSaveSendBox = "";    
        if(document.getElementById("isSaveSendBox").checked){
        	isSaveSendBox = "1"; //写入发件箱
        }
        dwrMailService.saveOaMailSend(oaMailSend,isSaveSendBox,saveCallback);
        Btn.close();
	}
}

function saveCallback(data){
    Btn.open();
    document.getElementById("oaMailSendTitle").focus();
	if(<%=edited%> == 1 || <%=mailtype%> == 5){
		alertmsg(data,"closePage()");
		return;
	}
    if(<%=choose%>!=null){
    	alertmsg(data,"closePage()");
    }else{
    	if(<%=mailtype%> == 5){
    		alertmsg(data,"closeonline()");
    	}else{
    		if(data.success){
    			<%if (mailtype != null){ %>
    			confirmmsgAndTitle("操作邮件成功！是否想继续写邮件?","cleanup();","继续写邮件","closePage();","关闭页面");
    			<%}else{ %>
    			confirmmsgAndTitle("操作邮件成功！是否想继续写邮件?","cleanup();","继续写邮件","backToNewsList();","返回列表");
    			<%} %>
    		}else{
    			alertmsg(data);
    		}
    	}
    }
}
    
function closeonline(){
	window.parent.MoveDiv.close();
}
    
function cleanup(){
    DWRUtil.setValue("oaMailSendEmpNames","");
    DWRUtil.setValue("oaMailSendEmpids","");
    DWRUtil.setValue("oaMailSendTitle","");
    DWRUtil.setValue("oaMailSendEmpCSNames","");
    DWRUtil.setValue("oaMailSendEmpCSids","");
    DWRUtil.setValue("oaMailSendEmpMSNames","");
    DWRUtil.setValue("oaMailSendEmpMSids","");
   
	if(document.getElementById("mailcscheck").checked){
		document.getElementById("mailcscheck").checked = false;
		mailcsChange();
	}
   
	if(document.getElementById("mailmscheck").checked){
       document.getElementById("mailmscheck").checked = false;
       mailmsChange();
	}  
	//设置fck
	fck.SetHTML("");
	//刷新附件
	Sys.setFilevalue("oaMailSendAffix","");	
	document.getElementById("oaMailSendTitle").focus();
 }

function getemployee(){
	if(<%=choose%>!=null){
		var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids");
	    box.show();
	}else{
	    if(<%=edited%> != null){
	        var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids");
	      	box.show();
	    }else{
	   		<%if (mailtype != null){ %>
		        var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids");
		      	box.show();
	      	<%}else{ %>
		      	var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids","mail");
		      	box.show();
	      	<%} %>
	    }  
    }
}
   
function getemployeeEmpCS(){
	if(<%=choose%>!=null){
		var box = SEL.getEmployeeIds("check","oaMailSendEmpCSNames","oaMailSendEmpCSids");
	   	box.show();
	}else{
	    if(<%=mailtype%> != null){
	        var box = SEL.getEmployeeIds("check","oaMailSendEmpCSNames","oaMailSendEmpCSids");
	   		box.show();
	    }else{
	        var box = SEL.getEmployeeIds("check","oaMailSendEmpCSNames","oaMailSendEmpCSids","mail");
	   		box.show();
	    }
	}
}
   
function getemployeeEmpMS(){
	if(<%=choose%>!=null){
		var box = SEL.getEmployeeIds("check","oaMailSendEmpMSNames","oaMailSendEmpMSids");
	   	box.show();
	}else{
	    if(<%=mailtype%> != null){
	        var box = SEL.getEmployeeIds("check","oaMailSendEmpMSNames","oaMailSendEmpMSids");
	   		box.show();
	    }else{
	        var box = SEL.getEmployeeIds("check","oaMailSendEmpMSNames","oaMailSendEmpMSids");
	   		box.show();
	    }
    }
}
	
function mailcsChange(){
	 if(document.getElementById("mailcscheck").checked){
	    $("#mailcs").animate({height: 'show',opacity: 'show'  }, 'slow');
	  }else{
	  	DWRUtil.setValue("oaMailSendEmpCSNames","");
 		DWRUtil.setValue("oaMailSendEmpCSids","");
	    $("#mailcs").animate({height: 'hide',   opacity: 'hide'  }, 'slow');
	  }
} 
	
function mailmsChange(){
	 if(document.getElementById("mailmscheck").checked){
	    $("#mailms").animate({height: 'show',opacity: 'show'  }, 'slow');
	  }else{
	  	DWRUtil.setValue("oaMailSendEmpMSNames","");
 		DWRUtil.setValue("oaMailSendEmpMSids","");
	    $("#mailms").animate({height: 'hide',   opacity: 'hide'  }, 'slow');
	  }
} 
	
function backToNewsList(){
    if(<%=backtype%> != null && <%=backtype%> == 4){ //由回收站明细传过来
         Sys.href('<%=contextPath%>/erp/mobile_sms/mail_delbox.jsp');
    }else if(<%=backtype%> != null && <%=backtype%> == 2){ //由发件箱明细传过来
         Sys.href('<%=contextPath%>/erp/mobile_sms/mail_outbox.jsp');
    }else if(<%=backtype%> != null && <%=backtype%> == 3){  //由草稿箱明细传过来
         Sys.href('<%=contextPath%>/erp/mobile_sms/mail_draftbox.jsp');
    }else{     //由收件箱明细传过来
         Sys.href('<%=contextPath%>/erp/mobile_sms/mail_inbox.jsp');
    }
}
function closePage(){
	closeMDITab();
}

</script>
</head>
<body class="inputcls"> 

<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">邮件发送</div>
	<div>	
	<table class="inputtable">
	<tr>
		<th></th>
		<td>
		<label id="oaMailSendTitlemust"></label>
		</td>
	</tr>
	<tr>
	   <th width="15%"><em>*</em>&nbsp;&nbsp;邮件主题</th>
	   <td colspan="3">
	      <input type="text" id="oaMailSendTitle" maxlength="50" style="width: 90%" must="邮件主题不能为空！" formust="oaMailSendTitlemust">
	   </td>
	</tr>	
	<tr>
		<th></th>
		<td>
		<label id="oaMailSendEmpNamesmust"></label>
		</td>
	</tr>
	<tr>
		<th width="15%"><em>*</em>&nbsp;&nbsp;收件人</th>
		<td colspan="3">
		<textarea  id="oaMailSendEmpNames" linkclear="oaMailSendEmpids" title="点击获取编码" readonly="readonly" onclick="getemployee();" style="color: #999;"></textarea>
		<input type="hidden" id="oaMailSendEmpids" value="">
		</td>
	</tr>
	<tr>
	   <th></th>
	   <td colspan="3">
	      <input type="checkbox" id="mailcscheck" onclick="mailcsChange()"><label for="mailcscheck">添加抄送</label>&nbsp;&nbsp;&nbsp;&nbsp;
	      <input type="checkbox" id="mailmscheck" onclick="mailmsChange()"><label for="mailmscheck">添加密送</label>
	   </td>
	</tr>
	<tr>
		<td colspan="4" width="100%" style="padding: 0;margin: 0">
		<div id="mailcs" style="display: none">
			<table style="width: 100%">
				<tr>
					<th width="15%">抄送人</th>
					<td>
					<textarea  id="oaMailSendEmpCSNames" linkclear="oaMailSendEmpCSids" title="点击获取人员" readonly="readonly" onclick="getemployeeEmpCS();" style="color: #999;"></textarea>
					<input type="hidden" id="oaMailSendEmpCSids" value="">
					</td>
				</tr>
			</table>
		</div>
	  </td>	  
	</tr>
	<tr>
		<td colspan="4" width="100%" style="padding: 0;margin: 0">
		<div id="mailms" style="display: none">
			<table style="width: 100%">
				<tr>
					<th width="15%">密送人</th>
					<td>
					<textarea  id="oaMailSendEmpMSNames" linkclear="oaMailSendEmpMSids" title="点击选择人员" readonly="readonly" onclick="getemployeeEmpMS();" style="color: #999;"></textarea>
					<input type="hidden" id="oaMailSendEmpMSids" value="">
					</td>
				</tr>
			</table>
		</div>
	</td>	  
	</tr>
			
	<tr>
	  <th width="15%">重要程度</th>
	  <td>
		<%=UtilTool.getRadioOptionsByEnum((EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("")),"oaMailSendIsurgent")%>
	  </td>
	</tr>
	<tr>
		<th>邮件附件</th>
		<td colspan="3">
		<file:multifileupload width="90%" acceptTextId="oaMailSendAffix" edit="<%=isedit %>" height="100" saveType="file" type="office"></file:multifileupload>
		</td>
	</tr>
	<tr>
		<th>邮件内容</th>
		<td colspan="3">
		<FCK:editor instanceName="oaMailSendContent" width="90%" height="400px"></FCK:editor>
		</td>
	</tr>
	<tr>
		<th></th>
		<td>
	   <input type="checkbox" id="isSaveSendBox" checked="checked"><label for="isSaveSendBox">&nbsp;写入发件箱</label>&nbsp;&nbsp;&nbsp;&nbsp;
	   <input type="checkbox" id="oaMailInboxReceipt"><label for="oaMailInboxReceipt">是否要求回执</label>
	  </td>
	</tr>
	</table>
	</div>
	<br>
</div>
<br/>
<center>
	<table>
	<tr>
		<td><btn:btn onclick="save(1);" value="立刻发送" imgsrc="../../images/fileokico.png"/></td>
		<td style="width: 20px;"></td>
		<td><btn:btn onclick="save(2);" value="保存为草稿" imgsrc="../../images/png-1718.png"/></td>
		<td style="width: 20px;"></td>
		<td id="btncancel">
		<%if (mailtype != null){ %>
		<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
		<%}else{ %>
		<btn:btn onclick="backToNewsList()" value="返 回 " imgsrc="../../images/back_cir.png" title="返回"></btn:btn>
		<%} %>
		</td>
	</tr>
	</table>
</center>
</body>
</html>