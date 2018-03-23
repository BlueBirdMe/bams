<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出借归还明细查看</title>
<%
    String bid = request.getParameter("bid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
     dwrOfficeResourcesService.getBookbrByPk(<%=bid%>,setPageValue);
	
}
	
function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var tmp = data.resultList[0];
          if(tmp.bookInfo != null){
              DWRUtil.setValue("oaBookbrName",tmp.bookInfo.oaBookName);
          }
          if(tmp.bookerEmp != null){
              DWRUtil.setValue("oaBookbrBooker",tmp.bookerEmp.hrmEmployeeName);
          }
          
          DWRUtil.setValue("oaBookbrBdate",tmp.oaBrBdate);
          DWRUtil.setValue("oaBookbrRdate",tmp.oaBrRdate);
          
          if(tmp.lendnEmp != null){
              DWRUtil.setValue("oaBookbrLendn",tmp.lendnEmp.hrmEmployeeName);
          }
          if(tmp.oaBrStatus ==<%=EnumUtil.OA_BOOKBR_STATUS.BACK.value%>){
              DWRUtil.setValue("oaBookStatus","已归还");
          }else{
              DWRUtil.setValue("oaBookStatus","已借出");
          }
          
          DWRUtil.setValue("oaBookbrLendw",tmp.oaBrLendw);
          DWRUtil.setValue("oaBookbrRemark",tmp.oaBrRemark);
          DWRUtil.setValue("oaBrCount",tmp.oaBrCount);
          DWRUtil.setValue("oaBrDate",tmp.oaBrDate);
      }
  }
}
</script>
</head>

<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;图书借出和归还的详细信息查看。</label></div>
	<div class="detailtitle">出借归还明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">图书名称</th>
		<td id="oaBookbrName" class="detailtabletd"></td>
		<td class="attachtd" rowspan="5">
		<!--
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="noticeAcce"></div>				
			</div>
		-->
		</td>
	</tr>
	<tr>
	<th width="15%">书籍状态</th>
	<td id ="oaBookStatus" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">登&nbsp;记&nbsp;人</th>
	<td id="oaBookbrBooker" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">数&nbsp;&nbsp;量</th>
	<td id="oaBrCount" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">借书日期</th>
	<td id="oaBookbrBdate" class="detailtabletd" colspan="2"></td>
	</tr>
	<tr>
	<th width="15%">还书日期</th>
	<td id="oaBookbrRdate" class="detailtabletd" colspan="2"></td>
	</tr>
	<tr>
	<th width="15%">实际还书时间</th>
	<td id="oaBrDate" class="detailtabletd" colspan="2"></td>
	</tr>
	
	<tr>
	<th width="15%">借书人（内部）</th>
	<td id="oaBookbrLendn"  class="detailtabletd" colspan="2"></td>
	</tr>
	<tr>
	<th width="15%">借书人（外部）</th>
	<td id="oaBookbrLendw" class="detailtabletd" colspan="2"></td>
	</tr>
	<tr>
	<th width="15%">备&nbsp;&nbsp;注</th>
	<td id="oaBookbrRemark" class="detailtabletd" colspan="2">
	</td>
	</tr>
	
	</table>
</body>


</html>
