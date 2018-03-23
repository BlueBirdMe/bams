<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
<title>新增投票界面</title>
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader("Expires",0);
    String voteid = request.getParameter("voteId");

 %>
<style type="text/css">
	body {
	background-color: #fefefe;
}
</style>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	initInput('title',"您可以在此处添加你要发起的投票，星号为必填，选项设置最少为2项");
	document.getElementById("oaVoteName").focus();
	saveoredit();
}

function saveoredit(){
	if(<%=voteid%> != null){
	    var id = <%=voteid%>;
	    dwrOaCommunicationService.getVoteByid(id,setVoteInfo);
	    dwrOaCommunicationService.getAlloptionByVote(id,editoption);
	}
}

function editoption(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 		    for(var i = 0; i < data.resultList.length; i++){
 		         var voteOption = data.resultList[i];
 		         var tb = document.getElementById("optionlist")
	             var pageno=tb.rows.length;
	             var otr = tb.insertRow(-1);
	             otr.style.cssText="line-height:24px;";
	             otr.id="tr"+pageno;
                 var td1=document.createElement("td");
                 td1.style.textAlign ="center";
                 td1.innerHTML ="<input type='checkbox' name='optionchk' value='"+pageno+"'>";
   	             var td2=document.createElement("td");
                 td2.innerHTML="<input type='text' id='optionname"+pageno+"' name='optionname' value='"+voteOption.oaOptionName+"' class='niceform' style='width:100%' must='请输入选项内容'>";;
                 var td3=document.createElement("td");
                 td3.style.cssText ="text-align:center";
	             td3.innerHTML="<a href='javascript:void(0)' onclick=\"delrow('"+pageno+"')\">删除</a>";
    
                 otr.appendChild(td1);
                 otr.appendChild(td2);
                 otr.appendChild(td3);
                 document.getElementById("optionname"+pageno).focus();
   				 /*
                 var cks =  document.getElementsByName("optionchk");
   
                 for(var j=0;j<cks.length;j++){
               	     changenicecheckbox(cks[j]);
                 }
                 */
 		    }
 		}else{
 			alertmsg(data.message);
 		}
 	}else{
 		alertmsg(data.message);
 	}
}


function getVoteInfo(){
    var vote = new Object();
    if(<%=voteid%> != null){
         vote.primaryKey = <%=voteid%>;
    }
    vote.oaVoteName = DWRUtil.getValue("oaVoteName");
    vote.oaVoteText = DWRUtil.getValue("oaVoteText");
    vote.oaVoteType = DWRUtil.getValue("oaVoteType");
    vote.oaChooseType = getRadioValueByName("oaChooseType");
    vote.oaVoteStart = DWRUtil.getValue("oaVoteStart");
    vote.oaVoteEnd = DWRUtil.getValue("oaVoteEnd");
    vote.oaRangeDep = DWRUtil.getValue("oaVoteDepid");
    vote.oaRangeEmp = DWRUtil.getValue("oaVoteEmpid");
    vote.oaIsAnonymous = getRadioValueByName("oaIsAnonymous");
    
    return vote;
}
function setVoteInfo(data){
    if(data.success == true){
 		if(data.resultList.length > 0){
 			var vote = data.resultList[0];
 			DWRUtil.setValue("oaVoteName",vote.oaVoteName);
 			DWRUtil.setValue("oaVoteText",vote.oaVoteText);
 			DWRUtil.setValue("oaVoteType",vote.oaVoteType);
 		
		    setRadioValueByName("oaChooseType",vote.oaChooseType); 			
 			DWRUtil.setValue("oaVoteStart",vote.oaVoteStart);
 			DWRUtil.setValue("oaVoteEnd",vote.oaVoteEnd);
 			DWRUtil.setValue("oaVoteDepid",vote.oaRangeDep);
 			DWRUtil.setValue("oaVoteEmpid",vote.oaRangeEmp);

 			setRadioValueByName("oaIsAnonymous",vote.oaIsAnonymous);
 			DWRUtil.setValue("oaVoteDep",vote.rangDepNames);
 			DWRUtil.setValue("oaVoteEmp",vote.rangEmpNames);
 		}else{
 			alertmsg(data.message);
 		}
 	}else{
 		alertmsg(data.message);
 	}
}

function save(){

var warnArr = new Array();
	warnArr[0] = "oaChooseTypemust";
	warnArr[1]="oaIsAnonymousmust";
	warnArr[2]="tagmust";
	warnArr[3]="selectmust";
	//清空所有信息提示
	warnInit(warnArr);
    var bl = validvalue('title');
    if(bl){
        var type=getRadioValueByName("oaChooseType");
         if(type==null){
              setMustWarn("oaChooseTypemust","请选择选项内容");
              return false;
         }
         type=getRadioValueByName("oaIsAnonymous");
          if(type==null){
          setMustWarn("oaIsAnonymousmust","请选择是否匿名投票");
              return false;
          }
        if(DWRUtil.getValue("oaVoteDepid") == ""  &&  DWRUtil.getValue("oaVoteEmpid") == ""){
             setMustWarn("tagmust","投票范围中人员、部门必须填写。");
     			return false;
        }else{
    	    var objs = document.getElementsByName("optionname");
    	    if(objs.length >= 2){
    	        var ars = new Array(objs.length);
    	        for(var i=0;i<objs.length;i++){
    		         ars[i] = objs[i].value;
    	        }
    	        if(<%=voteid%> != null){
    	             dwrOaCommunicationService.updateVote(getVoteInfo(),ars,saveCallback);
    	        }else{
    	             dwrOaCommunicationService.saveVote(getVoteInfo(),ars,saveCallback);
    	        }
    	        
    	    }else{
    	      setMustWarn("selectmust","选项设置至少为两项！");
    	    } 
    	}
    }
}

function saveCallback(data){
   if(<%=voteid%> != null){
   alertmsg(data,"reload();");
    }else{
    	if(data.success){
     		confirmmsgAndTitle("发起投票成功！是否想继续添加?","reloadpager();","继续添加","closePage();","关闭页面");
		}else{
  			alertmsg(data);
		}
	}
}

function closePage(){
	closeMDITab(<%=request.getParameter("tab")%>);
}

function reloadpager(){
       DWRUtil.setValue("oaVoteName","");
       DWRUtil.setValue("oaVoteDep","");
       DWRUtil.setValue("oaVoteDepid","");
       DWRUtil.setValue("oaVoteEmp","");
       DWRUtil.setValue("oaVoteEmpid","");
       DWRUtil.setValue("oaVoteText","");
       //DWRUtil.setValue("oaVoteStart","");
       //DWRUtil.setValue("oaVoteEnd","");
       document.getElementById("oaVoteType").selectedIndex =0;
       //清空选项设置
       var rlen = document.getElementById("optionlist").rows.length;	
      for(var i=rlen-1;i>=1;i--){
	  document.getElementById("optionlist").deleteRow(i);
      }
      document.getElementById("oaVoteName").focus();
}

function getVoteDep(){
	if(<%=voteid%>!=null){
		var box = SEL.getDeptIds("check","oaVoteDep","oaVoteDep","processloadfrm");
		box.show();
	}else{
	  	var box = SEL.getDeptIds("check","oaVoteDep","oaVoteDep");
		box.show();
	}
}
function getVoteEmp(){
	
	if(<%=voteid%>!=null){
		var box = SEL.getEmployeeIds("check","oaVoteEmp","oaVoteEmpid","processloadfrm");
		box.show();
	}else{
	  	var box = SEL.getEmployeeIds("check","oaVoteEmp","oaVoteEmpid");
		box.show();
	}
}

function reload(){
   	window.parent.MoveDiv.close();
	window.parent.queryData();
}

function backToNewsList(){
	Sys.href('<%=contextPath%>/erp/communication/vote_manager.jsp');
}
</script>
</head>
<body class="inputcls">

<div class="formDetail">
	<div class="requdiv"><label id="title"></label></div>
	<div class="formTitle">添加/编辑投票</div>
	<table class="inputtable">
	<tr>
		<th><em>* </em>投票名称</th>
		<td style="text-align: left;">
		<input type="text" id="oaVoteName" must="投票名称不能为空" formust="oaVoteNamemust" maxlength="100" style="width: 44%">
		<label id="oaVoteNamemust"></label>
	</tr>
	<tr>
		<th>是否匿名投票</th>
		<td style="text-align: left;">
		<%=UtilTool.getRadioOptionsByEnum(EnumUtil.ANONYMOUSVOTINH.getSelectAndText(""),"oaIsAnonymous")%>
		<label id="oaIsAnonymousmust"></label>
		</td>
	</tr>
	<tr>
		<th>选项类型</th>
		<td style="text-align: left;">
		<%=UtilTool.getRadioOptionsByEnum(EnumUtil.VOTINHSELECT.getSelectAndText(""),"oaChooseType")%>
		<label id="oaChooseTypemust"></label>
		</td>		
	</tr>
	<tr>
		<th>投票类型</th>
		<td style="text-align: left;"><select must="请选择投票类型" id="oaVoteType" formust="oaVoteTypemust">
		<%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"22") %></select>
		<label id="oaVoteTypemust"></label>
		</td>
	</tr>
	<tr>
		<th><em>* </em>投票时间</th>
		<td style="text-align: left">
	<input type="text" readonly="readonly" id="oaVoteStart" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'oaVoteEnd\')}'});" value="<%=UtilWork.getNowTime() %>">至
	<input type="text" readonly="readonly" id="oaVoteEnd" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'oaVoteStart\')}'});" value="<%=UtilWork.getNowTime() %>" >
		</td>
	<tr>
		<th>投票说明</th>
		<td style="text-align: left" colspan="3">
		<textarea rows="6" cols="3" id="oaVoteText"></textarea>
		</td>
	</tr>
	<tr>
		<th><em>* </em>范围设置</th>
		<td valign="top" colspan="3">
		<label id="tagmust"></label>
		<DIV class="tabdiv" style="width: 90%" id="tabdiv1" style="overflow: hidden;">
		<DIV class="tagContentdiv">
			<DIV class="tagContent" id="tag0" style="overflow: hidden;">
			<table style="width: 98%" border="0">
			<tr>
				<th style="width: 10%"><li style="color:blue"></li><font color="black">投票部门</font></th>
				<td>
				<textarea  id="oaVoteDep" readonly="readonly" linkclear="oaVoteDepid" onclick="getVoteDep();" style="color: #999w" title="点击获取编码"></textarea>
				<input type="hidden" id="oaVoteDepid" value="">
				</td>
			</tr>
			<tr>
				<th style="width: 10%"><li style="color:blue"></li><font color="black">投票人员</font></th>
				<td>
				<textarea  id="oaVoteEmp" linkclear="oaVoteEmpid" title="点击获取编码" readonly="readonly" onclick="getVoteEmp();" style="color: #999;"></textarea>
				<input type="hidden" id="oaVoteEmpid" value="">
				</td>
			</tr>	
			</table>	
			</DIV>
		</DIV>
	</DIV>
		</td>
	</tr>
	
	<tr>
		<td></td>
		<td>
			<div style="border: 1px solid #c1c1c1;width: 90%;" >
			<table cellpadding='0' cellspacing='0' border='0'  width ='100%'/>
			<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/wbg.gif');" height="24px">	
				<td align='left' style='padding-left:10px;font-weight: bold;'><em>* </em>选项设置：选项列表&nbsp;&nbsp;&nbsp;&nbsp;<label id="selectmust"></label></td>
				<td style="text-align: right;padding-right: 10px;" nowrap="nowrap" align="right">
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="deletebatch();">&nbsp;删除选项&nbsp;</div>
				<div class='div_btn' onmouseover ='this.className ="div_btn_hover"' onmouseout ='this.className ="div_btn"'   onclick="addoption()">&nbsp;添加选项&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td valign='top' colspan="2">
				<div style="overflow: auto;height: 180px;" >
				<table  class='tablerowStyleColor'  cellSpacing='1' cellPadding='2' width='100%' align='center' border='1' id='optionlist'>
					<tr style="BACKGROUND-IMAGE: url('<%=contextPath %>/images/grid_images/fhbg.gif');">
						<td  class='tableTitle1' style="text-align: center;" width="10px">
						<input type='checkbox' onclick="selectAll(this,'optionchk')" title='全选/取消'>
						</td>
						<td  class='tableTitle1' style="text-align: center;">选项</td>
						<td  class='tableTitle1' style="text-align: center;width: 10%;" nowrap="nowrap">操作</td>
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

<br/>	
<table align="center">
<tr>
	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
	<td style="width: 10px;"></td>
	<td>
	<%if (voteid == null){ %>
	<btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%}else{ %>
	<btn:btn onclick="window.parent.MoveDiv.close()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
	<%} %>
	</td>
</tr>
</table>
<script type="text/javascript">
var tab =new SysTab('<%=contextPath%>',null,"tabdiv1");
function addoption(){
	var tb = document.getElementById("optionlist")
	var pageno=tb.rows.length;
	var otr = tb.insertRow(-1);
	otr.style.cssText="line-height:24px;";
	otr.id="tr"+pageno;
    var td1=document.createElement("td");
    td1.style.textAlign ="center";
    td1.innerHTML ="<input type='checkbox' name='optionchk' value='"+pageno+"'>";
   	var td2=document.createElement("td");
   	td2.innerHTML="<input type='text' id='optionname"+pageno+"' name='optionname'  class='niceform' style='width:100%' must='选项内容不能为空！' formust='selectmust' maxlength='25'>";;
    var td3=document.createElement("td");
    td3.style.cssText ="text-align:center";
	td3.innerHTML="<a href='javascript:void(0)' onclick=\"delrow('"+pageno+"')\">删除</a>";
    
    otr.appendChild(td1);
    otr.appendChild(td2);
    otr.appendChild(td3);
    document.getElementById("optionname"+pageno).focus();
}

function delrow(pno){
	var tab=document.getElementById("optionlist");
	var row = document.getElementById("tr"+pno);
	var rIndex = row.rowIndex;
	tab.deleteRow(rIndex);
}


function deletebatch(){
	var rows = getCheckedValues("optionchk");
	for(var i in rows){
		delrow(rows[i]);
	}
}
</script>
</body>
</html>