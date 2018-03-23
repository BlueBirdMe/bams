<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>认证信息明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrDxArchivementService.getDxArchivementByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxarchivement = data.resultList[0];
            DWRUtil.setValue("name",dxarchivement.name);
            DWRUtil.setValue("empid",dxarchivement.empid);
            DWRUtil.setValue("ability",dxarchivement.ability);
            DWRUtil.setValue("type",dxarchivement.type);
            DWRUtil.setValue("archieve",dxarchivement.archieve);
            DWRUtil.setValue("performance",dxarchivement.performance);
            DWRUtil.setValue("patent",dxarchivement.patent);
            DWRUtil.setValue("compet",dxarchivement.compet);
            DWRUtil.setValue("result",dxarchivement.result);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示认证信息相关信息！</label></div>
    <div class="detailtitle">认证信息明细</div>
    <table class="detailtable">
        <tr>
            <th>姓&nbsp;&nbsp;名</th>
            <td id="name" class="detailtabletd"></td>
            <th>工&nbsp;&nbsp;号</th>
            <td id="empid" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>B/C及人才</th>
            <td id="ability" class="detailtabletd"></td>
            <th>类&nbsp;&nbsp;型</th>
            <td id="type" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>获奖情况</th>
            <td id="archieve" class="detailtabletd"></td>
            <th>业&nbsp;&nbsp;绩</th>
            <td id="performance" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>竞&nbsp;&nbsp;赛</th>
            <td id="compet" class="detailtabletd"></td>
            <th>成&nbsp;&nbsp;绩</th>
            <td id="result" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>专利情况</th>
            <td id="patent" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
