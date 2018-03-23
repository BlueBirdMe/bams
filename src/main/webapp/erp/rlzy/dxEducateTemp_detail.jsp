<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训审核明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateTempService.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrDxEducateService.getDxEducateByPk('<%=pk%>',setOriPageValue);
    dwrDxEducateTempService.getDxEducateTempByPk('<%=pk%>',setPageValue);
}

function setOriPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxeducate = data.resultList[0];
            DWRUtil.setValue("name",dxeducate.name);
            DWRUtil.setValue("experience",dxeducate.experience);
            DWRUtil.setValue("certificate",dxeducate.certificate);
            DWRUtil.setValue("empid",dxeducate.empid);
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}

function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxeducateTemp = data.resultList[0];
            DWRUtil.setValue("e_xperience",dxeducateTemp.experience);
            DWRUtil.setValue("c_ertificate",dxeducateTemp.certificate);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示培训审核相关信息！</label></div>
    <div class="detailtitle">培训审核明细</div>
    <table class="detailtable">
        <tr>
            <th>姓&nbsp;&nbsp;名</th>
            <td id="name" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>工&nbsp;&nbsp;号</th>
            <td id="empid" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>培训经历</th>
            <td id="experience" class="detailtabletd"></td>
            <th>修改为</th>
            <td id="e_xperience" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>认证证书</th>
            <td id="certificate" class="detailtabletd"></td>
            <th>修改为</th>
            <td id="c_ertificate" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
