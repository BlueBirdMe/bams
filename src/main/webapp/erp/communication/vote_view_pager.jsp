<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投票结果查看</title>
<%
    String voteId = request.getParameter("voteId");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>   
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
	dwrOaCommunicationService.getVoteByid(<%=Integer.parseInt(voteId)%>,setPageValue);
	dwrOaCommunicationService.getAllVoteStatus(<%=Integer.parseInt(voteId)%>,setEmpName);
}

var chooseType = 0;
var viewType = 0;
var voteStatusType = 0;
var vote = new Object();

function setEmpName(data){
	var employeeName = "该投票记录还没有人投票！";
	if(data.success == true){
       if(data.resultList.length>0){
           employeeName = "";
           for(var i = 0; i < data.resultList.length; i++){
               var voteStatus = data.resultList[i];
               employeeName = employeeName + voteStatus.oaVoteEmp +"&nbsp;,&nbsp;";
           }
        	document.getElementById("empListResult").innerHTML = "<label style='padding-left:10px;padding-right:10px'>已投票人员:</label>"+employeeName;
		}
	}
}

function setPageValue(data){
	if(data != null){
		if(data.resultList.length>0){
	        vote = data.resultList[0];
	        chooseType = vote.oaChooseType;
	        viewType = vote.oaViewType;
	        voteStatusType = vote.oaVoteStatus;
	        var votename = document.getElementById("voteName");
	        votename.innerHTML = vote.oaVoteName;
	        
	        if(vote.oaVoteText=="" ||vote.oaVoteText==null){
	            DWRUtil.setValue("voteText","无");
	        }else{
	        	DWRUtil.setValue("voteText",vote.oaVoteText);
			}
	       	document.getElementById("startTime").innerHTML =vote.oaVoteStart+"&nbsp;至&nbsp;"+vote.oaVoteEnd;
	        if(vote.employee != null){
	            DWRUtil.setValue("voteEmp",vote.employee.hrmEmployeeName);
	        }
	        loadOptionStatus();
		}else{
			alertmsg(data.message);
		}
	}else{
		alertmsg(data.message);
	}
}

function loadOptionStatus(){
   var voteid = <%=voteId%>;
   dwrOaCommunicationService.getAlloptionByVote(voteid,setOptionResult);
}

function setOptionResult(data){
	if(data != null && data.resultList.length>0){
		var voteCount = 0;
		for(var k = 0; k < data.resultList.length; k++){
			voteCount = voteCount + data.resultList[k].oaOptionCount;
		}
		for(var i = 0; i < data.resultList.length; i++){
			var op = data.resultList[i];
			var id = op.primaryKey;
			var otr = document.getElementById("optionResult").insertRow(-1);
			otr.id = id;
			var jc =300;//图形基线
			var td0=document.createElement("td");
			if(chooseType == <%=EnumUtil.OA_VOTE_OPTIONS_TYPE.RADIO.value%>){
				td0.innerHTML="<li>"+op.oaOptionName+"</li>";
			}else{
				td0.innerHTML="<li style='list-style: square'>"+op.oaOptionName+"</li>";
			}
			td0.style.cssText ="padding-right:10px;text-align:right";
			otr.appendChild(td0);

			var td1=document.createElement("td");
			td1.style.cssText="text-align:left;padding-left: 10px;padding-top:5px;padding-bottom:5px";
			if(voteCount == 0){
			    voteCount = 1;
			}
			var wi = op.oaOptionCount/voteCount;
			var wi2 = jc*wi;
			
			var color = getColor();
			var tmp ="<div style='width:"+jc+"px;height:15px;float:left;background-color:#ededed;border-top:1px solid #ededed;border-bottom:1px solid #808080;border-left:1px solid #ededed;border-right:1px solid #808080;'><div style='width:"+wi2+"px;background-color:" + color + ";color:#ffffff;height:15px;'></div></div>";
			var ctmp="";
			if(chooseType == <%=EnumUtil.OA_VOTE_OPTIONS_TYPE.RADIO.value%>){
				var cou = (op.oaOptionCount/voteCount)*100;
				cou = Math.round(cou,5);
				ctmp = "("+cou +"%"+")";
			}else{
				ctmp = op.oaOptionCount+"票";
			}

			td1.innerHTML =tmp+"&nbsp;&nbsp;&nbsp;&nbsp;"+ctmp;
			otr.appendChild(td1);

		}
	}else{
		alertmsg(data.message);
	}
}

function getColor(){
	var r = Math.floor(Math.random()*255).toString(16);
	var g = Math.floor(Math.random()*255).toString(16);
	var b = Math.floor(Math.random()*255).toString(16);
	r = r.length == 1 ? "0" + r : r;
	g = g.length == 1 ? "0" + g : g;
	b = b.length == 1 ? "0" + b : b;
	return "#" + r + g + b;
}
</script>
</head>
<body class="inputdetail">
   <div class="requdivdetail"><label>查看帮助:&nbsp;在这里可以查看投票结果，和已经投过票的人员。</label></div>
		<div class="detailtitle">投票信息查看</div>
				<table class="detailtable" align="center">
				<tr>
					<th width="15%">投票名称:</th>
					<td id="voteName" class="detailtabletd"></td>
				</tr>
				<tr>
					<td>
					<br>
					</td>
				</tr>
				<tr>
				<th>投票说明:</th>
					<td id="voteText" class="detailtabletd"></td>
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<th>发起人:</th>
					<td id = "voteEmp" class="detailtabletd"></td>
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<th>投票时间:</th>
					<td id = "startTime" class="detailtabletd"></td>
				</tr>
				</table>
	<br/>
	<center><div class="linediv"></div></center>
	<br/>
	<div class="detailtitle">投票结果查看</div>
	    <table border="0" cellpadding="0" cellspacing="0" id="optionResult" width="90%" style="line-height: 25px;">
		<tr>
		<td nowrap="nowrap" align="right"></td>
		<td nowrap="nowrap"></td>
		</tr>
		</table>
	<br>
	<br>
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			
			<td id = "empListResult" ></td>
		
		</tr>
	</table>
		<center><div class="linediv"></div></center>
</body>
</html>
