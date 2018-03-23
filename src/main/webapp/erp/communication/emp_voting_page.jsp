<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投票界面</title>
<%
    String voteId = request.getParameter("voteId");
    String choose = request.getParameter("choose");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>   
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
    document.getElementById("helpTitle").innerHTML = " 投票至少需选择一项,请选择你支持的选项！";
	dwrOaCommunicationService.getVoteByid(<%=Integer.parseInt(voteId)%>,setPageValue);
	if(<%=choose%>!=null){
		document.getElementById("btndiv").style.display = "none";
	}else{
    	//initInput('title');
    	loadKeycode();
    }
    
}
var chooseType = 0;
function loadOption(){
    dwrOaCommunicationService.getAlloptionByVote(<%=Integer.parseInt(voteId)%>,setOption);
}

function keyListenerCallback(keycode){
	//ESC
	if(keycode == 27){
		if(<%=voteId%> != null){
			confirmmsg("是否要返回投票列表？","window.parent.MoveDiv.close();");
		}else{
			confirmmsg("是否要返回投票列表？","window.parent.MoveDiv.close();");
		}
	}
	
	//ctrl + S
	if((window.event.ctrlKey)&&(keycode==83)){
		voting();
		window.event.returnValue=false; 
	}
	
}

function setPageValue(data){
   if(data != null){
       if(data.resultList.length>0){
           var vote = data.resultList[0];
           if(vote.oaVoteStatus != <%=EnumUtil.OA_VOTE_STATUS.VOTING.value%>){
               alertmsg("该投票记录不是投票中状态，不能投票！","reload()");
           }else{
              var votename = document.getElementById("voteName");
                  votename.innerHTML = vote.oaVoteName;
                  votename.style.cssText="line-height:20px;text-align:center;font-size: 16px;color: #336699;font-weight: bold;font-family: '宋体'";
               
               if(vote.oaIsAnonymous == <%=EnumUtil.OA_VOTE_IS_ANONYMOUS.YES.value%>){
                    document.getElementById("noticeMsg").innerHTML = "<font color='red'>此投票为匿名不记名投票！</font>";
                    document.getElementById("oaIsAnonymous").value = <%=EnumUtil.OA_VOTE_IS_ANONYMOUS.YES.value%>;
               }else{
                    document.getElementById("oaIsAnonymous").value = <%=EnumUtil.OA_VOTE_IS_ANONYMOUS.NO.value%>;
               }   
                  
              // DWRUtil.setValue("voteName",vote.oaVoteName);
               DWRUtil.setValue("voteText",vote.oaVoteText);
               DWRUtil.setValue("startTime",vote.oaVoteStart);
               DWRUtil.setValue("endTime",vote.oaVoteEnd);
               if(vote.employee != null){
                   DWRUtil.setValue("voteEmp",vote.employee.hrmEmployeeName);
               }else{
                   DWRUtil.setValue("voteEmp","");
               }
               
               chooseType = vote.oaChooseType;
               loadOption();
           }
       }else{
           alertmsg(data.message);
       }
   }else{
       alertmsg(data.message);
   }
}

function setOption(data){
    if(data.success == true){
       if(data.resultList.length>0){
           for(var i = 0; i < data.resultList.length; i++){
               var op = data.resultList[i];
               var id = op.primaryKey;
               if(chooseType == <%=EnumUtil.OA_VOTE_OPTIONS_TYPE.RADIO.value%>){
                   var otr = document.getElementById("optionList").insertRow(-1);
                   otr.style.cssText="line-height:30px;text-align:left;";
				   otr.onmousemove = function (){setColor(this,'#ffffbe');};    
                   otr.onmouseout = function (){setColor(this,'#ffffff');};
				   
				   var td1=document.createElement("td");
				   td1.style.cssText="with:100%;text-align:center;padding-right: 10px;";
				   td1.innerHTML = "<input type='radio' name='optionchk' id='"+id+"' value='"+id+"'>";
				   var td2=document.createElement("td");
				   td2.innerHTML ="<label for='"+id+"' style='width:100%'>"+op.oaOptionName+"</label>";
				   
				   otr.appendChild(td1);
				   otr.appendChild(td2);
               }else{
                   var otr = document.getElementById("optionList").insertRow(-1);
                   otr.style.cssText="line-height:30px;text-align:left;";
				   otr.onmousemove = function (){setColor(this,'#ffffbe');};    
                   otr.onmouseout = function (){setColor(this,'#ffffff');}; 
                   
				   var td1=document.createElement("td");
				   td1.style.cssText="with:100%;text-align:center;padding-right: 10px;";
				   td1.innerHTML = "<input type='checkbox' name='optionchk' id='"+id+"' value='"+id+"'>";
				   var td2=document.createElement("td");  
				   td2.innerHTML ="<label for='"+id+"'  style='width:100%'>"+op.oaOptionName+"</label>";
				   
				   otr.appendChild(td1);
				   otr.appendChild(td2);
               }
           }
       }else{
           alertmsg(data.message);
       }
   }else{
       alertmsg(data.message);
   }
}

function setColor(obj,color){    
     setStyle(obj,"background-color:"+color+";line-height:30px;text-align:left;");    
}   

function setStyle(element,text) {       
     element.setAttribute("style",text)       
     element.style.cssText=text       
} 

function voting(myfrmname){
   var voteid = <%=voteId%>;
   var isAnonymous = document.getElementById("oaIsAnonymous").value;

   if(chooseType == <%=EnumUtil.OA_VOTE_OPTIONS_TYPE.CHECKBOX.value%>){
       if(getCheckedCount("optionchk") > 0){
          
		   var ids = getCheckedValues("optionchk");
		   dwrOaCommunicationService.votingSet(ids,voteid,isAnonymous,setCallback);
		   Btn.close();
		   
	   }else{
	       alertmsg("请选择要投的选项记录！");
	   }
   }else{
       if(getCheckedCount("optionchk") > 0){
       
		   var id = getRadioValueByName("optionchk");
		   var voteids = new Array();
		   voteids[0] =id;
		   
		   dwrOaCommunicationService.votingSet(voteids,voteid,isAnonymous,setCallback);
		   Btn.close();
	   }else{
	       alertmsg("请选择要投的选项记录！");
	   }
   }
}

var mydialogId;
function votingByDesk(dialogId){
   mydialogId = dialogId;
   var voteid = <%=voteId%>;
   var isAnonymous = document.getElementById("oaIsAnonymous").value;
   
   if(chooseType == <%=EnumUtil.OA_VOTE_OPTIONS_TYPE.CHECKBOX.value%>){
       if(getCheckedCount("optionchk") > 0){
		   var ids = getCheckedValues("optionchk");
		   dwrOaCommunicationService.votingSet(ids,voteid,isAnonymous,setCallbackByDesk);
	   }else{
	       alertmsg("请选择要投的选项记录！");
	   }
   }else{
       if(getCheckedCount("optionchk") > 0){
       
		   var id = getRadioValueByName("optionchk");
		   var voteids = new Array();
		   voteids[0] =id;
		   
		   dwrOaCommunicationService.votingSet(voteids,voteid,isAnonymous,setCallbackByDesk);
	   }else{
	       alertmsg("请选择要投的选项记录！");
	   }
   }
}

function setCallbackByDesk(data){
	alertmsg(data,"deskclose()");
}

function deskclose(){
	var win = Sys.getfrm();//获取index页面iframe window对象	
    win.loadxml_other();
    Sys.close(mydialogId);
}

function setCallback(data){
   Btn.open();
   alertmsg(data,"reload()");
}

function reload(){
    window.parent.MoveDiv.close();
	window.parent.queryData();
}

</script>
</head>
<body class="inputcls">
	<input type="hidden" id = "oaIsAnonymous">
<div class="formDetail">
	    <div class="requdiv"><label id="helpTitle"></label></div>
		<div class="formTitle">投票界面</div>
	<table align="center" width="100%" style="height: 85%;width: 99%;vertical-align: top;">
		<tr>
			<td colspan="3">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td width="10%" height="20px">发起人：</td>
			<td id="voteEmp"></td>
		</tr>
		<tr>
			<td width="10%" height="20px">开始时间：</td>
			<td id="startTime"></td>
		</tr>
		<tr>
			<td width="10%" height="20px">结束时间：</td>
			<td id="endTime"></td>
		</tr>
		<tr>
			<td  height="20px" id = "noticeMsg" colspan="2"></td>
			<td></td>
		</tr>
			<tr>
			<td>
			<br/>
			</td>
			</tr>
	</table>
	</td>
	</tr>
	<tr>
	<td colspan="3" id="voteName" align="center"></td>
	</tr>
	<tr>
	 <td>
	   </br>
	 </td>
	</tr>
	<tr>
	<td id="voteText" align="left" style="padding-left: 200px;padding-right: 200px;height: 50px;"></td>
	</tr>
	<tr>
	 <td>
	   </br>
	 </td>
	</tr>
	<tr>
	<td colspan="3" align="center" >
	<table border="0" cellpadding="0" cellspacing="0" id="optionList" width="60%">
		<tr>
			<td width="10%" align="center"></td>
			<td width="90%" align="left"></td>
		</tr>
		</table>
		</td>
		</tr>
	</table>
	<br>
</div>
	<div id="btndiv" style="text-align: center;">
	<br>
	<br>
	<table align="center">
	<tr>
	<td><btn:btn onclick="voting();" value=" 投 票 " /></td>
	<td style="width: 20px;"></td>
	<td><btn:cancel onclick="window.parent.MoveDiv.close()"></btn:cancel></td>
	</tr>
	</table>
</div>
</body>
</html>
