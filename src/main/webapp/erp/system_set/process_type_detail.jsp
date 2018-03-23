<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程分类明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrApproveProcessService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrApproveProcessService.getSysProcessTypeByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var processType = data.resultList[0];
            DWRUtil.setValue("typeName",processType.typeName);
            DWRUtil.setValue("typeDesc",processType.typeDesc);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示流程分类相关信息！</label></div>
    <div class="detailtitle">流程分类明细</div>
    <table class="detailtable">
        <tr>
            <th>分类名称</th>
            <td id="typeName" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>分类描述</th>
            <td id="typeDesc" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
