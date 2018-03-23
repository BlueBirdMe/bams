<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String id = request.getParameter("id");
	String tab = request.getParameter("tab");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程设置</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrApproveProcessService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput("helpTitle","您可以在此处对流程进行设置");
    dwrApproveProcessService.getSysProcessConfigByPk("<%=id%>",setConfig);
}

function setConfig(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var config = data.resultList[0];
            setSelectValue("processTypeId",config.processType.primaryKey);
            DWRUtil.setValue("processDesc",config.processDesc);
            DWRUtil.setValue("startPage",config.startPage);
            DWRUtil.setValue("handlePage",config.handlePage);
            DWRUtil.setValue("detailPage",config.detailPage);
            DWRUtil.setValue("definitionName",config.processDefinition.name);
        }else{
            alert(data.message);
        }
    }
}

function save(){
    var warnArr = new Array();
    //清空所有信息提示
    warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
        //Btn.close();
        dwrApproveProcessService.updateSysProcessConfig(getConfig(),updateCallback);
    }
}
function getConfig(){
	var processType = new Object();
	processType.primaryKey = DWRUtil.getValue("processTypeId");
	
    var config = new Object();
    config.primaryKey = '<%=id%>';
    config.processDesc = DWRUtil.getValue("processDesc");
    config.startPage = DWRUtil.getValue("startPage");
    config.handlePage = DWRUtil.getValue("handlePage");
    config.detailPage = DWRUtil.getValue("detailPage");
    config.processType = processType;
    return config;
}

function updateCallback(data){
    //Btn.open();
    if(data.success){
        alertmsg(data,"closePage();");
    }else{
        alertmsg(data);
    }
}
function closePage(){
	closeMDITab(<%=tab%>);
}
</script>
</head>
<body class="inputcls">
    <div class="formDetail">
        <div class="requdiv"><label id="helpTitle"></label></div>
        <div class="formTitle"><label id="definitionName"></label>设置</div>
        <table class="inputtable">
            <tr>
                <th><em>*</em>&nbsp;&nbsp;流程分类</th>
                <td>
                	<select id="processTypeId"><%=UtilTool.getProcessTypeOptions(this.getServletContext(), request, "") %></select>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;开始页面</th>
                <td><input type="text" id="startPage" must="开始页面不能为空!" formust="startPageMust" style="width:300px;"></input><label id="startPageMust"></label></td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;处理页面</th>
                <td><input type="text" id="handlePage" must="处理页面不能为空!" formust="handlePageMust" style="width:300px;"></input><label id="handlePageMust"></label></td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;明细页面</th>
                <td><input type="text" id="detailPage" must="明细页面不能为空!" formust="detailPageMust" style="width:300px;"></input><label id="detailPageMust"></label></td>
            </tr>
             <tr>
                <th>流程描述</th>
                <td>
                    <textarea style="width:50%;" id="processDesc"></textarea>
                </td>
            </tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存设置信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
