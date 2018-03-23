<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统运行日志明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<%
    Integer pk = Integer.valueOf(request.getParameter("pk"));
%>
<script type="text/javascript">
window.onload = function(){
    dwrSysProcessService.getSysLogRuntimeByPk(<%=pk%>,setPageValue);
}

function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var sysLog = data.resultList[0];
            DWRUtil.setValue("className",sysLog.className);
            DWRUtil.setValue("methodName",sysLog.methodName);
            DWRUtil.setValue("createTime",sysLog.createTime);
            DWRUtil.setValue("logLevel",sysLog.logLevel);
            DWRUtil.setValue("msg",sysLog.msg);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示系统运行日志相关信息！</label></div>
    <div class="detailtitle">系统运行日志明细</div>
    <table class="detailtable">
        <tr>
            <th>类名</th>
            <td id="className" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>方法名</th>
            <td id="methodName" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>生成时间</th>
            <td id="createTime" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>日志级别</th>
            <td id="logLevel" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>运行信息</th>
            <td id="msg" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
