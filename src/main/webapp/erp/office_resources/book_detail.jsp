<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书信息明细查看</title>
<%
    String bid = request.getParameter("bid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
      init();
	
}
function init(){
   dwrOfficeResourcesService.getBookByPk(<%=bid%>,setPageValue);
}


function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var tmp = data.resultList[0];
          DWRUtil.setValue("oaBookCode",tmp.oaBookCode);
          DWRUtil.setValue("oaBookName",tmp.oaBookName);
          if(tmp.bookType != null){
              DWRUtil.setValue("oaBookType",tmp.bookType.oaBooktypeName);
          }
          DWRUtil.setValue("oaBookAuthor",tmp.oaBookAuthor);
          DWRUtil.setValue("oaBookIsbn",tmp.oaBookIsbn);
          DWRUtil.setValue("oaBookConcern",tmp.oaBookConcern);
          DWRUtil.setValue("oaPublishDate",tmp.oaPublishDate);
          DWRUtil.setValue("oaBookAddress",tmp.oaBookAddress);
          tmp.oaBookCount = tmp.oaBookCount +" 本 ";
          DWRUtil.setValue("oaBookCount",tmp.oaBookCount);
          tmp.oaBookRemain = tmp.oaBookRemain +" 本 ";
          DWRUtil.setValue("oaBookRemain",tmp.oaBookRemain);
          //tmp.oaBookPrice ="￥"+FormatNumber(tmp.oaBookPrice,2);
          if(tmp.oaBookPrice != null){
          DWRUtil.setValue("oaBookPrice","￥"+FormatNumber(tmp.oaBookPrice,2));
          }
          DWRUtil.setValue("oaBuyDate",tmp.oaBuyDate);
          if(tmp.employee != null){
              DWRUtil.setValue("oaBookBooker",tmp.employee.hrmEmployeeName);
          }
          DWRUtil.setValue("oaRegistyDate",tmp.oaRegistyDate);
          if(tmp.department != null){
              DWRUtil.setValue("oaBookDep",tmp.department.hrmDepName);
          }
          document.getElementById("oaBookSynopsis").innerHTML = tmp.oaBookSynopsis;
          document.getElementById("oaBookRemark").innerHTML = tmp.oaBookRemark;
          
           //附件显示为下载
		  Sys.showDownload(tmp.oaBookAcce,"bookAcce");
      }
  }
}
</script>
</head>
<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;公司图书的详细信息查看，可通过点击附件来进行下载。</label></div>
	<div class="detailtitle">图书信息明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">图书编号</th>
		<td width="25%" id="oaBookCode" class="detailtabletd"></td>
		<td></td>
		<td class="attachtd" rowspan="5" colspan="2" >
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="bookAcce"></div>				
			</div>
		</td>
	</tr>
	<tr>
	<th width="15%">图书名称</th>
	<td id="oaBookName" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">类&nbsp;&nbsp;别</th>
	<td id="oaBookType" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">作&nbsp;&nbsp;者</th>
	<td id="oaBookAuthor" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">ISBN号</th>
	<td id="oaBookIsbn" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">出&nbsp;版&nbsp;社</th>
	<td id="oaBookConcern" class="detailtabletd"></td>
	
	<th width="10%">出版日期</th>
	<td width="35%" id="oaPublishDate" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">数&nbsp;&nbsp;量</th>
	<td id="oaBookCount" class="detailtabletd"></td>
	
	<th width="15%">剩余数量</th>
	<td id="oaBookRemain" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">单&nbsp;&nbsp;价</th>
	<td id="oaBookPrice" class="detailtabletd"></td>
	
	<th width="15%">购买日期</th>
	<td id="oaBuyDate" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">登&nbsp;记&nbsp;人</th>
	<td id="oaBookBooker" class="detailtabletd"></td>
	
	<th width="15%">登记时间</th>
	<td id="oaRegistyDate" class="detailtabletd"></td>
	</tr>
	<tr>
	<th width="15%">所属部门</th>
	<td id="oaBookDep" class="detailtabletd"></td>
	
	<th width="15%">存放地点</th>
	<td id="oaBookAddress" class="detailtabletd"></td>
	</tr>

	<tr>
	<th>内容简介</th>
	<td colspan="3"  id="oaBookSynopsis" class="detailtabletd">
	</td>
	</tr>
	<tr>
	<th>备&nbsp;&nbsp;注</th>
	<td colspan="3"  id="oaBookRemark" class="detailtabletd">
	</td>
	</tr>
	</table>
</body>

</html>
