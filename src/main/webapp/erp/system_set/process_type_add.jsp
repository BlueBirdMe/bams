<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的流程分类！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑流程分类信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>流程分类</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrApproveProcessService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput("helpTitle","<%=helpTitle%>");
    saveOrEdit();
}
function saveOrEdit(){
    if(<%=isedit%>){
        var pk = '<%=pk%>';
        dwrApproveProcessService.getSysProcessTypeByPk(pk,setProcessType);
    }
}

function setProcessType(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var processType = data.resultList[0];
            DWRUtil.setValue("typeName",processType.typeName);
            DWRUtil.setValue("typeDesc",processType.typeDesc);
            DWRUtil.setValue("priority",processType.priority);
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}
function save(){
    var warnArr = new Array();
    //清空所有信息提示
    warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
        //此处可编写js代码进一步验证数据项

        //Btn.close();
        if(<%=isedit%>){
            dwrApproveProcessService.updateSysProcessType(getProcessType(),updateCallback);
        }else{
            dwrApproveProcessService.saveSysProcessType(getProcessType(),saveCallback);
        }
    }
}
function getProcessType(){
    var processType = new Object();
    if(<%=isedit%>){
        processType.primaryKey = '<%=pk%>';
    }
    processType.typeName = DWRUtil.getValue("typeName");
    processType.typeDesc = DWRUtil.getValue("typeDesc");
    processType.priority = DWRUtil.getValue("priority");
    return processType;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加流程分类成功！是否想继续添加流程分类？","reset();","继续添加","closePage();","关闭页面");
    }else{
        alertmsg(data);
    }
}
function updateCallback(data){
    //Btn.open();
    if(data.success){
        alertmsg(data,"closePage();");
    }else{
        alertmsg(data);
    }
}
function reset(){
    Sys.reload();
}
function closePage(){
    closeMDITab(<%=tab%>);
}
</script>
</head>
<body class="inputcls">
    <div class="formDetail">
        <div class="requdiv"><label id="helpTitle"></label></div>
        <div class="formTitle"><%=saveOrEdit%>流程分类</div>
        <table class="inputtable">
            <tr>
                <th><em>*</em>&nbsp;&nbsp;分类名称</th>
                <td>
                    <input type="text" id="typeName" must="分类名称不能为空!" formust="typeNameMust"></input><label id="typeNameMust"></label>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;排序名称</th>
                <td>
                    <input type="text" id="priority" must="分类排序不能为空!" formust="priorityMust"></input><label id="priorityMust"></label>
                </td>
            </tr>
            <tr>
                <th>分类描述</th>
                <td>
                    <textarea id="typeDesc" style="width:50%;"></textarea>
                </td>
            </tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存流程分类信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
