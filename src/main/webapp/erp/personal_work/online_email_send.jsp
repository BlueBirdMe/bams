<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@ include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>发送邮件</title>
		<%
			String employeepk = request.getParameter("employeepk");
		%>
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrMailService.js"></script>
		<script type="text/javascript">
window.onload = function(){
	initInput("helpTitle","短信提醒，您可以向在线人员发送短信。");
	dwrHrmEmployeeService.getEmployeeByPK(<%=employeepk%>,setEmployeeinfo);
	document.getElementById("oaMailSendTitle").focus();
}

function setEmployeeinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var employee = data.resultList[0];
 			DWRUtil.setValue("hrmEmployeeName",employee.hrmEmployeeName);
 			DWRUtil.setValue("oaSmsSendAcpempName",employee.hrmEmployeeName);
 			document.getElementById("oaSmsSendAcpempName").innerHTML = employee.hrmEmployeeName;
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
	
function reback(){
    closeMDITab();
}
   
var fckvalue ="";
var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);//初始赋值
	window.status = editorInstance.Description;
}

	
function getOaMailSendinfo(){
	var oaMailSend = new Object();
		oaMailSend.oaMailSendEmpids = DWRUtil.getValue("oaSmsSendAcpemp")+","; //收件人id集合
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
	
function saveCallback(data){
	alertmsg(data,"reback()");
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

function save(){
    var warnArr = new Array();
	warnArr[0] = "oaMailSendEmpNamesmust";
    var bl = validvalue('helpTitle');
    if(bl){
	    var oaMailSend = getOaMailSendinfo();
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
    if(data.success){
		confirmmsgAndTitle("发送邮件成功！是否想继续添加？","cleanup();","继续添加","reback();","关闭页面");
	}else{
		alertmsg(data);
	}
}

function backToNewsList(){
	window.location = "online.jsp";
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
	var box = SEL.getEmployeeIds("check","oaMailSendEmpNames","oaMailSendEmpids");
	box.show();
}
   
function getemployeeEmpCS(){
	var box = SEL.getEmployeeIds("check","oaMailSendEmpCSNames","oaMailSendEmpCSids");
	box.show();
}
   
function getemployeeEmpMS(){
	var box = SEL.getEmployeeIds("check","oaMailSendEmpMSNames","oaMailSendEmpMSids");
	box.show();
    
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

</script>
	</head>
	<body class="inputcls">
		<div class="formDetail">
			<div class="requdivdetail">
				<label>
					查看帮助:&nbsp; 显示人员相关信息，点击附件可下载附件！
				</label>
			</div>
			<div class="detailtitle">
				人员信息
			</div>
			<table class="detailtable" align="center">
				<tr>
					<th width="15%">
						员工编号
					</th>
					<td id="hrmEmployeeCode" class="detailtabletd" width="25%"></td>
					<th rowspan="4" style="text-align: right; padding-bottom: 65px;">
						照片
					</th>
					<td rowspan="4" style="text-align: left;">
						<file:imgshow id="hrmEmployeeImageInfoId" height="128"></file:imgshow>
					</td>
				</tr>
				<tr>
					<th>
						姓&nbsp;名
					</th>
					<td id="hrmEmployeeName" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						英文名
					</th>
					<td id="hrmEmployeeEngname" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						性&nbsp;别
					</th>
					<td id="hrmEmployeeSex" class="detailtabletd"></td>
				</tr>
				<tr>
				<tr>
					<th>
						部&nbsp;&nbsp;门
					</th>
					<td id="hrmEmployeeDeptext" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						工作岗位
					</th>
					<td id="hrmEmployeePostIdtext" class="detailtabletd"></td>

				</tr>
				<tr>
					<th>
						兼职岗位
					</th>
					<td id="hrmPartPosttext" class="detailtabletd"></td>
				</tr>
				<tr>
					<th>
						工作地区
					</th>
					<td id="hrmEmployeeWorkareaid" class="detailtabletd"></td>
				</tr>
			</table>
		</div>

		<div class="formDetail">
			<div class="requdiv">
				<label id="helpTitle"></label>
			</div>
			<div class="formTitle">
				邮件发送
			</div>
			<div>
				<table class="inputtable">
					<tr>
						<th></th>
						<td>
							<label id="oaMailSendTitlemust"></label>
						</td>
					</tr>
					<tr>
						<th width="15%">
							<em>*</em>&nbsp;&nbsp;邮件主题
						</th>
						<td colspan="3">
							<input type="text" id="oaMailSendTitle" maxlength="50"
								style="width: 90%" must="主题不能为空" formust="oaMailSendTitlemust">
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<label id="oaMailSendEmpNamesmust"></label>
						</td>
					</tr>
					<tr>
						<th width="15%">
							收件人
						</th>
						<td>
							<div id="oaSmsSendAcpempName" style="width: 90%"></div>
							<input type="hidden" id="oaSmsSendAcpemp">
							<label id="oaSmsSendAcpempMust"></label>
						</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="3">
							<input type="checkbox" id="mailcscheck" onclick="mailcsChange()">
							<label for="mailcscheck">
								抄送
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="mailmscheck" onclick="mailmsChange()">
							<label for="mailmscheck">
								密送
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="oaMailInboxReceipt">
							<label for="oaMailInboxReceipt">
								是否要求回执
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="4" width="100%" style="padding: 0; margin: 0">
							<div id="mailcs" style="display: none">
								<table style="width: 100%">
									<tr>
										<th width="15%">
											抄送人
										</th>
										<td>
											<textarea id="oaMailSendEmpCSNames"
												linkclear="oaMailSendEmpCSids" title="点击选择人员"
												readonly="readonly" onclick="getemployeeEmpCS();"
												style="color: #999;"></textarea>
											<input type="hidden" id="oaMailSendEmpCSids" value="">
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="4" width="100%" style="padding: 0; margin: 0">
							<div id="mailms" style="display: none">
								<table style="width: 100%">
									<tr>
										<th width="15%">
											密送人
										</th>
										<td>
											<textarea id="oaMailSendEmpMSNames"
												linkclear="oaMailSendEmpMSids" title="点击选择人员"
												readonly="readonly" onclick="getemployeeEmpMS();"
												style="color: #999;"></textarea>
											<input type="hidden" id="oaMailSendEmpMSids" value="">
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>

					<tr>
						<th width="15%">
							重要程度
						</th>
						<td>
							<%=UtilTool.getRadioOptionsByEnum((EnumUtil.OA_CALENDER_LEVEL.getSelectAndText("")), "oaMailSendIsurgent")%>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="checkbox" id="isSaveSendBox" checked="checked">
							<label for="isSaveSendBox">
								&nbsp;写入发件箱
							</label>
						</td>
					</tr>
					<tr>
						<th>
							邮件附件
						</th>
						<td colspan="3">
							<file:multifileupload width="90%" acceptTextId="oaMailSendAffix"
								height="100" saveType="file" type="office"></file:multifileupload>
						</td>
					</tr>
					<tr>
						<th>
							邮件内容
						</th>
						<td colspan="3">
							<FCK:editor instanceName="oaMailSendContent" width="90%"
								height="240"></FCK:editor>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<center>
			<table>
				<tr>
					<td>
						<btn:btn onclick="save()" value="发 送 "
							imgsrc="../../images/fileokico.png" title="发送邮件"></btn:btn>
					</td>
					<td style="width: 10px;"></td>
					<td>
						<btn:btn onclick="reback()" value="关 闭 "
							imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>
