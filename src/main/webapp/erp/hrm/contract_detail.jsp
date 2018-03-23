<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmContractService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrHrmContractService.getHrmContractByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var contract = data.resultList[0];
            DWRUtil.setValue("contractCode",contract.contractCode);
            DWRUtil.setValue("contractName",contract.contractName);
            
            var typeName;
        	if(contract.contractLimitType == <%= EnumUtil.CONTRACT_LIMIT_TYPE.GD.value%>){
        		typeName = "<%=EnumUtil.CONTRACT_LIMIT_TYPE.valueOf(EnumUtil.CONTRACT_LIMIT_TYPE.GD.value)%>";
        	}else if(contract.contractLimitType == <%= EnumUtil.CONTRACT_LIMIT_TYPE.WGD.value%>){
        		typeName = "<%=EnumUtil.CONTRACT_LIMIT_TYPE.valueOf(EnumUtil.CONTRACT_LIMIT_TYPE.WGD.value)%>";
        	}
            DWRUtil.setValue("contractLimitType",typeName);
            
            var statusName;
        	if(contract.contractStatus == <%= EnumUtil.CONTRACT_STATUS.YX.value%>){
        		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.YX.value)%>";
        	}else if(contract.contractStatus == <%= EnumUtil.CONTRACT_STATUS.ZZ.value%>){
        		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.ZZ.value)%>";
        	}else if(contract.contractStatus == <%= EnumUtil.CONTRACT_STATUS.GQ.value%>){
        		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.GQ.value)%>";
        	}
            DWRUtil.setValue("contractStatus",statusName);
            DWRUtil.setValue("contractTypeId",contract.contractType.typeName);
            DWRUtil.setValue("contractBegindate",contract.contractBegindate);
            DWRUtil.setValue("contractEnddate",contract.contractEnddate);
            DWRUtil.setValue("contractContent",contract.contractContent,{escapeHtml:false});
            //放入附件
            if(isNotBlank(contract.contractFile)){
                Sys.showDownload(contract.contractFile,"contractFile");
            }
            DWRUtil.setValue("empId",contract.employee.hrmEmployeeName);
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示合同相关信息！</label></div>
    <div class="detailtitle">合同明细</div>
    <table class="detailtable">
    	<tr>
            <th>人员姓名</th>
            <td id="empId" class="detailtabletd"></td>
            <th></th>
            <td></td>
        </tr>
        <tr>
            <th>合同编号</th>
            <td id="contractCode" class="detailtabletd"></td>
            <th>合同名称</th>
            <td id="contractName" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>期限类型</th>
            <td id="contractLimitType" class="detailtabletd"></td>
            <th>合同状态</th>
            <td id="contractStatus" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>合同类别</th>
            <td id="contractTypeId" class="detailtabletd"></td>
            <th></th>
            <td></td>
        </tr>
        <tr>
        	<th>开始日期</th>
            <td id="contractBegindate" class="detailtabletd"></td>
            <th>结束日期</th>
            <td id="contractEnddate" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>合同内容</th>
            <td colspan="3" id="contractContent" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td colspan="3" id="contractFile" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
