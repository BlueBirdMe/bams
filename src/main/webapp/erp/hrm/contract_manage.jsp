<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmContractService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
    startQuery();
    var contract = getQueryParam();//java实体类相对应
    var pager = getPager();
    dwrHrmContractService.listHrmContract(contract,pager,queryCallback);
}
function queryCallback(data){
    if(data.success == true){
        initGridData(data.resultList,data.pager);
    }else{
        alert(data.message);
    }
    endQuery();
}
//双击数据
function dblCallback(obj){
    var box = new Sys.msgbox("明细查看","<%=contextPath%>/erp/hrm/contract_detail.jsp?pk="+obj.value);
    box.show();
}
function edit(pk){
    openMDITab("<%=contextPath%>/erp/hrm/contract_add.jsp?pk="+pk+"&tab="+getMDITab());
}
function createProcessMethod(rowObj){
    var str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
    str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
    return str;
}
function del(pk){
    confirmmsg("确定要删除合同吗?","delok('"+pk+"')");
}
function delok(pk){
    var pks = new Array();
    pks[0] = pk;
    dwrHrmContractService.deleteHrmContractByPks(pks,delCallback);
}
function delCallback(data){
    alertmsg(data,"queryData()");
}
function delbatch(){
    if(getAllRecordArray() != false){
        confirmmsg("确定要删除合同吗?","delbatchok()");
    }else{
        alertmsg("请选择要删除的合同...");
    }
}
function delbatchok(){
    var pks = getAllRecordArray();
    dwrHrmContractService.deleteHrmContractByPks(pks,delCallback);
}
function add(){
    openMDITab("<%=contextPath%>/erp/hrm/contract_add.jsp?tab="+getMDITab());
}

function repContractLimitType(rowObj){
	var typeName;
	if(rowObj.contractLimitType == <%= EnumUtil.CONTRACT_LIMIT_TYPE.GD.value%>){
		typeName = "<%=EnumUtil.CONTRACT_LIMIT_TYPE.valueOf(EnumUtil.CONTRACT_LIMIT_TYPE.GD.value)%>";
	}else if(rowObj.contractLimitType == <%= EnumUtil.CONTRACT_LIMIT_TYPE.WGD.value%>){
		typeName = "<%=EnumUtil.CONTRACT_LIMIT_TYPE.valueOf(EnumUtil.CONTRACT_LIMIT_TYPE.WGD.value)%>";
	}
	return typeName
}
function repContractStatus(rowObj){
	var statusName;
	if(rowObj.contractStatus == <%= EnumUtil.CONTRACT_STATUS.YX.value%>){
		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.YX.value)%>";
	}else if(rowObj.contractStatus == <%= EnumUtil.CONTRACT_STATUS.ZZ.value%>){
		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.ZZ.value)%>";
	}else if(rowObj.contractStatus == <%= EnumUtil.CONTRACT_STATUS.GQ.value%>){
		statusName = "<%=EnumUtil.CONTRACT_STATUS.valueOf(EnumUtil.CONTRACT_STATUS.GQ.value)%>";
	}
	return statusName
}
</script>
</head>
<body>
<%
SysGrid grid = new SysGrid(request,"合同列表");
//放入按钮
ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增", "add()", "add.png"));
btnList.add(new SysGridBtnBean("批量删除", "delbatch()", "close.png"));
grid.setBtnList(btnList);
//放入操作提示，请在系统管理-帮助管理处添加
grid.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("employee.hrmEmployeeName","人员",1,1,1,0));
sccList.add(new SysColumnControl("contractCode","合同编号",1,1,1,0));
sccList.add(new SysColumnControl("contractName","合同名称",1,1,1,0));
sccList.add(new SysColumnControl("contractLimitType","期限类型",1,2,1,0));
sccList.add(new SysColumnControl("contractStatus","合同状态",1,2,1,0));
sccList.add(new SysColumnControl("contractType.typeName","合同类别",1,2,2,0));
sccList.add(new SysColumnControl("contractType.primaryKey","合同类别",2,2,1,0));
sccList.add(new SysColumnControl("contractBegindate","开始日期",1,2,1,0));
sccList.add(new SysColumnControl("contractEnddate","结束日期",1,2,1,0));
//sccList.add(new SysColumnControl("contractContent","合同内容",1,2,2,0));
//sccList.add(new SysColumnControl("contractFile","合同附件",1,2,2,0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
for(int i = 0; i < colList.size(); i++){
    SysGridColumnBean bc = colList.get(i);
    if ("contractLimitType".equalsIgnoreCase(bc.getDataName())){
        bc.setColumnReplace("repContractLimitType");
        SelectType select = new SelectType(EnumUtil.CONTRACT_LIMIT_TYPE.getSelectAndText("-1,-请选择期限类型-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);
    }
    if ("contractStatus".equalsIgnoreCase(bc.getDataName())){
        bc.setColumnReplace("repContractStatus");
        SelectType select = new SelectType(EnumUtil.CONTRACT_STATUS.getSelectAndText("-1,-请选择合同状态-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);
    }
    if ("contractType.primaryKey".equalsIgnoreCase(bc.getDataName())){
        SelectType select = new SelectType(UtilTool.getContractTypeString(this.getServletContext(), request, "-1,-请选择合同类型-"));
		select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
		bc.setColumnTypeClass(select);
    }
    if ("contractBegindate".equalsIgnoreCase(bc.getDataName())){
    	DateType date = new DateType();
		bc.setColumnTypeClass(date);
    }
    if ("contractEnddate".equalsIgnoreCase(bc.getDataName())){
    	DateType date = new DateType();
		bc.setColumnTypeClass(date);
    }
}
grid.setColumnList(colList);
//设置附加信息
grid.setShowImg(false);
grid.setQueryFunction("queryData");//查询的方法名
grid.setDblFunction("dblCallback");//双击列的方法名，有返回值，为列对象
grid.setDblBundle("primaryKey");//双击列的绑定的列值
grid.setShowProcess(true);//默认为false 为true请设置processMethodName
grid.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象
out.print(grid.createTable());
%>
</body>
</html>
