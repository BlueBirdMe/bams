<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>认证审核明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementTempService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrDxArchivementTempService.getDxArchivementTempByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxarchivementtemp = data.resultList[0];
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}
</script>
</head>
<body class="inputdetail">
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示认证审核相关信息！</label></div>
    <div class="detailtitle">认证审核明细</div>
    <table class="detailtable">
    </table>
</body>
</html>
