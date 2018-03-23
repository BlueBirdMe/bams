<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的合同！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑合同信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>合同</title>
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
        dwrHrmContractService.getHrmContractByPk(pk,setContract);
    }
}

var fckvalue = "";
var fck;
function FCKeditor_OnComplete(editorInstance) {
    fck = editorInstance;
    editorInstance.SetHTML(fckvalue);
    window.status = editorInstance.Description;
}

function setContract(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var contract = data.resultList[0];
            DWRUtil.setValue("contractCode",contract.contractCode);
            DWRUtil.setValue("contractName",contract.contractName);
            setRadioValueByName("contractLimitType",contract.contractLimitType);
            setSelectValue("contractStatus",contract.contractStatus);
            setSelectValue("contractTypeId",contract.contractType.primaryKey);
            DWRUtil.setValue("contractBegindate",contract.contractBegindate);
            DWRUtil.setValue("contractEnddate",contract.contractEnddate);
            fckvalue = contract.contractContent;
            if(isNotBlank(contract.contractFile)){
                dwrCommonService.getAttachmentInfoListToString(contract.contractFile,function(data){Sys.setFilevalue("contractFile",data);});
            }
            DWRUtil.setValue("empId",contract.employee.primaryKey);
            DWRUtil.setValue("empName",contract.employee.hrmEmployeeName);
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

        var attach = DWRUtil.getValue("contractFile");//附件
        //Btn.close();
        if(<%=isedit%>){
            dwrHrmContractService.updateHrmContract(getContract(),attach,updateCallback);
        }else{
            dwrHrmContractService.saveHrmContract(getContract(),attach,saveCallback);
        }
    }
}
function getContract(){
	
	var employee = new Object();
	employee.primaryKey = DWRUtil.getValue("empId");
	
	var contractType = new Object();
	contractType.primaryKey = DWRUtil.getValue("contractTypeId");
	
	 
    var contract = new Object();
    if(<%=isedit%>){
        contract.primaryKey = '<%=pk%>';
    }
    contract.contractCode = DWRUtil.getValue("contractCode");
    contract.contractName = DWRUtil.getValue("contractName");
    contract.contractLimitType = getRadioValueByName("contractLimitType");
    contract.contractStatus = DWRUtil.getValue("contractStatus");
    contract.contractType = contractType;
    contract.contractBegindate = DWRUtil.getValue("contractBegindate");
    contract.contractEnddate = DWRUtil.getValue("contractEnddate");
    contract.contractContent = fck.GetXHTML();
    contract.employee = employee;
    return contract;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加合同成功！是否想继续添加合同？","reset();","继续添加","closePage();","关闭页面");
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

function getupcode(){
	var box = SEL.getEmployeeIds("radio","empName","empId");
	box.show();
}

</script>
</head>
<body class="inputcls">
    <div class="formDetail">
        <div class="requdiv"><label id="helpTitle"></label></div>
        <div class="formTitle"><%=saveOrEdit%>合同</div>
        <table class="inputtable">
        	<tr>
                <th><em>*</em>&nbsp;&nbsp;人员姓名</th>
                <td>
                    <input type="text" id="empName" class="takeform" readonly="readonly" linkclear="empId" onClick="getupcode();" title="点击选择" formust="empNameMust" must="请选择人员信息"></input>
                    <input type="hidden" id="empId">
                    <label id="empNameMust"></label>
                </td>
                <th></th>
                <td>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;合同编号</th>
                <td>
                    <input type="text" id="contractCode" must="合同编号不能为空!" formust="contractCodeMust"></input><label id="contractCodeMust"></label>
                </td>
                <th><em>*</em>&nbsp;&nbsp;合同名称</th>
                <td>
                    <input type="text" id="contractName" must="合同名称不能为空!" formust="contractNameMust"></input><label id="contractNameMust"></label>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;期限类型</th>
                <td>
                    <%=UtilTool.getRadioOptionsByEnum(EnumUtil.CONTRACT_LIMIT_TYPE.getSelectAndText(""),"contractLimitType")%>
                </td>
                <th><em>*</em>&nbsp;&nbsp;合同状态</th>
                <td>
                    <select id="contractStatus"><%=UtilTool.getSelectOptionsByEnum(EnumUtil.CONTRACT_STATUS.getSelectAndText("")) %></select>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;合同类别</th>
                <td>
                    <select id="contractTypeId"><%=UtilTool.getContractTypeOptions(this.getServletContext(), request, "") %></select>
                </td>
                <th></th>
                <td>
                </td>
            </tr>
            <tr>
            	<th><em>*</em>&nbsp;&nbsp;开始日期</th>
                <td>
                    <input type="text" id="contractBegindate" class="Wdate" readonly="readonly" onClick="WdatePicker()" must="开始日期不能为空!" formust="contractBegindateMust"></input><label id="contractBegindateMust"></label>
                </td>
                <th>结束日期</th>
                <td>
                    <input type="text" id="contractEnddate" class="Wdate" readonly="readonly" onClick="WdatePicker()"></input>
                </td>
             
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;合同内容</th>
                <td colspan="3">
                    <label id="contractContentMust"></label><FCK:editor instanceName="contractContent" width="80%" height="250"></FCK:editor>
                </td>
            </tr>
            <tr>
                <th>合同附件</th>
                <td colspan="3">
                    <file:multifileupload width="80%" acceptTextId="contractFile" height="100" edit="<%=isedit %>"></file:multifileupload>
                </td>
            </tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存合同信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
