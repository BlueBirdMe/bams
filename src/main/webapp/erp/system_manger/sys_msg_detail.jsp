<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String aid = request.getParameter("aid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script> 
  <script type="text/javascript">
     window.onload = function(){
		dwrSysProcessService.getSysMsgByPk(<%=Integer.parseInt(aid)%>,setPageValue);
	}
	function setPageValue(data){
	var effectiveDate ="";
	
	
	 if(data != null){
	      if(data.resultList.length>0){
	          var sysMsg = data.resultList[0];
	          DWRUtil.setValue("msgTitle",sysMsg.msgTitle);
	         
	          DWRUtil.setValue("msgDate",sysMsg.msgDate);
	          
	          effectiveDate=sysMsg.msgVsdate + "  至  " + sysMsg.msgVedate ;
	           DWRUtil.setValue("effectiveDate",effectiveDate);
	           var msgIsEffective = "";
	           if(sysMsg.msgIsEffective == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
	              msgIsEffective = '<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%>';
	           }else{
	              msgIsEffective = '<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%>';
	           }
	           DWRUtil.setValue("msgIsEffective",msgIsEffective);
	          document.getElementById("msgContext").innerHTML = sysMsg.msgContext;
	          //附件显示为下载
			 
	      }
	  }
	}
</script>
</head>
  <body>
   <fieldset>
	<legend>公告明细</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">公告名称</th>
	<td id="msgTitle" colspan="3"></td>
	</tr>
	
	<tr>
	<th>发布时间</th>
	<td id="msgDate"></td>
	</tr>
	
		
	<tr>
	<th>有效期</th>	
	<td id="effectiveDate"></td>	
	</tr>
	
	<tr>
	<th>公告状态</th>
	<td id="msgIsEffective"></td>
	</tr>
	<tr>
	<th>公告内容</th>
	<td colspan="3"  id="msgContext">
	</td>
	</tr>
	</table>
	</div>
</fieldset>
<br/>

<br/>
</body>
</html>