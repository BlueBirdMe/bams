<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的合同类型！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑合同类型信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>合同类型</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmContractService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput("helpTitle","<%=helpTitle%>");
    saveOrEdit();
}
function saveOrEdit(){
    if(<%=isedit%>){
        var pk = '<%=pk%>';
        dwrHrmContractService.getHrmContractTypeByPk(pk,setContractType);
    }
}

function setContractType(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var contractType = data.resultList[0];
            DWRUtil.setValue("typeName",contractType.typeName);
            if(isNotBlank(contractType.typeFile)){
                dwrCommonService.getAttachmentInfoListToString(contractType.typeFile,function(data){Sys.setFilevalue("typeFile",data);});
            }
            DWRUtil.setValue("typeDesc",contractType.typeDesc);
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

        var attach = DWRUtil.getValue("typeFile");//附件
        //Btn.close();
        if(<%=isedit%>){
            dwrHrmContractService.updateHrmContractType(getContractType(),attach,updateCallback);
        }else{
            dwrHrmContractService.saveHrmContractType(getContractType(),attach,saveCallback);
        }
    }
}
function getContractType(){
    var contractType = new Object();
    if(<%=isedit%>){
        contractType.primaryKey = '<%=pk%>';
    }
    contractType.typeName = DWRUtil.getValue("typeName");
    contractType.typeDesc = DWRUtil.getValue("typeDesc");
    return contractType;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加合同类型成功！是否想继续添加合同类型？","reset();","继续添加","closePage();","关闭页面");
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
        <div class="formTitle"><%=saveOrEdit%>合同类型</div>
        <table class="inputtable">
            <tr>
                <th><em>*</em>&nbsp;&nbsp;类别名称</th>
                <td>
                    <input type="text" id="typeName" must="类别名称不能为空!" formust="typeNameMust"></input><label id="typeNameMust"></label>
                </td>
            </tr>
            <tr>
                <th>合同模板</th>
                <td>
                    <file:multifileupload width="90%" acceptTextId="typeFile" height="100" edit="<%=isedit %>"></file:multifileupload>
                </td>
            </tr>
            <tr>
                <th>类别描述</th>
                <td>
                    <br/><textarea width="90%" id="typeDesc" style="height:150px;"></textarea>
                </td>
            </tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存合同类型信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
