<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
		<title>公司管理</title>
		<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var company = getQueryParam();
	company.companyInfoStatus = <%=EnumUtil.SYS_COMPANY_STATUS.TAKE.value%>;
	//company.companyInfoType = <%=EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value%>;
	var pager = getPager();
	dwrSysProcessService.listSysCompanyInfoByPagerForParam(company,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

//双击数据
function dblCallback(obj){
	window.location.href = "<%=contextPath%>/erp/system_manger/sys_company_detail.jsp?type=2&cid="+obj.value;
}

function setParam(id){
	Sys.href("<%=contextPath%>/erp/system_manger/parammanger.jsp?cid="+id);
}

function createMethod(rowObj){
	var str = "<a href='javascript:void(0)' title='设置运行参数' onclick=\"setParam('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>"
	return str;
}

function repType(rowObj){
	var str="";
	if(rowObj.companyInfoType==<%=EnumUtil.SYS_COMPANY_TYPE_SEL.TRIAL.value%>){
		str = "<%=EnumUtil.SYS_COMPANY_TYPE_SEL.valueOf(EnumUtil.SYS_COMPANY_TYPE_SEL.TRIAL.value)%>";
	}else if(rowObj.companyInfoType==<%=EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value%>){
		str = "<%=EnumUtil.SYS_COMPANY_TYPE_SEL.valueOf(EnumUtil.SYS_COMPANY_TYPE_SEL.OFFICIAL.value)%>";
	}
	return str;
}

function repcolor(rowObj){
	var ed = rowObj.companyInfoEdate;
	var str =ed;
	try{
		var cd = new Date();
		ed = ed.replace(/-/g,"/");
		var ede = new Date(ed);
		if(ede<=cd){
			str = "<font color='red' title='服务到期'>"+str+"</font>";
		}
	}catch(e){
		return str;
	}
	return str;
}
</script>
	</head>
	<body>
		<%
			SysGrid grid = new SysGrid(request, "注册运行中公司列表");
			grid.setShowImg(false);//不显示图片信息
			grid.setBorder(1);
			grid.setCheckboxOrNum(false);
			//放入列
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(), "公司信息"));

			//进行高级查询显示处理
			for (int i = 0; i < colList.size(); i++) {
				SysGridColumnBean bc = colList.get(i);
				if ("companyInfoCode".equalsIgnoreCase(bc.getDataName())) {
					bc.setColumnStyle("text-aling:center");
				}
				if ("companyInfoSdate".equalsIgnoreCase(bc.getDataName()) || "companyInfoEdate".equalsIgnoreCase(bc.getDataName())) {
					DateType date = new DateType();
					bc.setColumnTypeClass(date);
					bc.setColumnStyle("text-align:center");
				}

				if ("companyInfoEdate".equalsIgnoreCase(bc.getDataName())) {
					bc.setColumnReplace("repcolor");
				}

				if ("companyInfoRegDate".equalsIgnoreCase(bc.getDataName()) || "companyInfoRegDate".equalsIgnoreCase(bc.getDataName())) {
					DateType date = new DateType();
					bc.setColumnTypeClass(date);
					bc.setColumnStyle("text-align:center");
				}

				if ("companyInfoType".equalsIgnoreCase(bc.getDataName())) {
					bc.setShowColumn(false);
					bc.setShowQuerySelsect(false);
					bc.setShowAdvanced(false);
				}
			}

			grid.setColumnList(colList);

			grid.setShowProcess(true);
			grid.setProcessMethodName("createMethod");

			//设置附加信息
			grid.setQueryFunction("queryData"); //查询的方法名
			grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			grid.setDblBundle("primaryKey"); //双击列的绑定的列值

			//开始创建
			out.print(grid.createTable());
		%>
	</body>
</html>