<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训信息明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrDxEducateService.getDxEducateByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxeducate = data.resultList[0];
            DWRUtil.setValue("name",dxeducate.name);
            DWRUtil.setValue("experience",dxeducate.experience);
            DWRUtil.setValue("certificate",dxeducate.certificate);
            DWRUtil.setValue("specialty",dxeducate.specialty);
            DWRUtil.setValue("date",dxeducate.date);
            DWRUtil.setValue("year",dxeducate.year);
            DWRUtil.setValue("pastspecialty",dxeducate.pastspecialty);
            DWRUtil.setValue("pastdate",dxeducate.pastdate);
            DWRUtil.setValue("pstyear",dxeducate.pstyear);
            DWRUtil.setValue("empid",dxeducate.empid);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示培训信息相关信息！</label></div>
    <div class="detailtitle">培训信息明细</div>
    <table class="detailtable">
        <tr>
            <th>姓&nbsp;&nbsp;名</th>
            <td id="name" class="detailtabletd"></td>
            <th>工&nbsp;&nbsp;号</th>
            <td id="empid" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>培训经历</th>
            <td id="experience" class="detailtabletd"></td>
            <th>认证证书</th>
            <td id="certificate" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>现从事专业</th>
            <td id="specialty" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>现专业开始时间</th>
            <td id="date" class="detailtabletd"></td>
            <th>现专业年从事年限</th>
            <td id="year" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>曾从事专业</th>
            <td id="pastspecialty" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>曾专业开始时间</th>
            <td id="pastdate" class="detailtabletd"></td>
            <th>曾专业年从事年限</th>
            <td id="pstyear" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
