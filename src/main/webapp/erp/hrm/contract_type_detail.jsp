<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同类型明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmContractService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrHrmContractService.getHrmContractTypeByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var contractType = data.resultList[0];
            DWRUtil.setValue("typeName",contractType.typeName);
            //放入附件
            if(isNotBlank(contractType.typeFile)){
                Sys.showDownload(contractType.typeFile,"typeFile");
            }
            DWRUtil.setValue("typeDesc",contractType.typeDesc);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示合同类型相关信息！</label></div>
    <div class="detailtitle">合同类型明细</div>
    <table class="detailtable">
        <tr>
            <th>类别名称</th>
            <td id="typeName" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>合同模板</th>
            <td id="typeFile" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>类别描述</th>
            <td id="typeDesc" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
