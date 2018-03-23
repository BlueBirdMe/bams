<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>公司管理</title>
<%
String type = request.getParameter("type");
int t = Integer.parseInt(type);
String titname="";
if(t==0){
	titname="等待处理公司列表";
}else if(t==1){
	titname ="试用中公司列表";
}else if(t==2){
	titname ="注册运行中公司列表";
}else if(t==3){
	titname ="服务已到期公司列表";
}
 %>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var company = getQueryParam();
	<%if(t==0){%>
		company.companyInfoStatus = <%=EnumUtil.SYS_COMPANY_STATUS.APPROVE.value%>;
		company.companyInfoType = <%=EnumUtil.SYS_COMPANY_TYPE.APPROVE.value%>;
	<%}else if(t==1){%>
		company.companyInfoStatus = <%=EnumUtil.SYS_COMPANY_STATUS.TAKE.value%>;
		company.companyInfoType = <%=EnumUtil.SYS_COMPANY_TYPE.TRIAL.value%>;
	<%}else if(t==2){%>
		company.companyInfoStatus = <%=EnumUtil.SYS_COMPANY_STATUS.TAKE.value%>;
		company.companyInfoType = <%=EnumUtil.SYS_COMPANY_TYPE.OFFICIAL.value%>;
	<%}else if(t==3){%>
		company.companyInfoContext = "end";
	<%}%>
	var pager = getPager();
	dwrSysProcessService.listSysCompanyInfoByPager(company,pager,queryCallback);
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
	window.location.href = "<%=contextPath%>/erp/system_manger/sys_company_detail.jsp?type=<%=t%>&cid="+obj.value;
}

function del(id){
	confirmmsg("<font color='red'>删除公司将移除公司所有数据,请慎重操作!</font><br/>确定要删除吗?","delok("+id+")");
}

function delok(id){
	dwrSysProcessService.deleteSysCompanyInfoByPk(id,deletecallback);
}

function deletecallback(data){
	alertmsg(data,"queryData()");
}

function edit(id){
	Sys.href("<%=contextPath%>/erp/system_manger/sys_company_edit.jsp?type=<%=t%>&cid="+id);
}

function process(id){
	Sys.href("<%=contextPath%>/erp/system_manger/sys_company_Approveproceess.jsp?type=<%=t%>&cid="+id);
}

function createMethod(rowObj){
	<%if(t>0){%>
		var str="<a href='javascript:void(0)' title='调整' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	<%}%>
	<%if(t==0){%>
		var str="<a href='javascript:void(0)' title='处理' onclick=\"process('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>"
	<%}%>

	str +="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
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
	SysGrid grid = new SysGrid(request,titname);
grid.setShowImg(false);//不显示图片信息
grid.setBorder(1);

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司信息"));

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("companyInfoCode".equalsIgnoreCase(bc.getDataName())){
		if(t==0){
	bc.setShowColumn(false);
	bc.setShowQuerySelsect(false);
	bc.setShowAdvanced(false);
		}else{
	bc.setColumnStyle("text-aling:center");
		}
	}
	if("companyInfoSdate".equalsIgnoreCase(bc.getDataName())||"companyInfoEdate".equalsIgnoreCase(bc.getDataName())){
		if(t==0){
	bc.setShowColumn(false);
	bc.setShowQuerySelsect(false);
	bc.setShowAdvanced(false);
		}else{
	DateType date = new DateType();
	bc.setColumnTypeClass(date);
	bc.setColumnStyle("text-align:center");
		}
	}
	
	if("companyInfoEdate".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repcolor");
	}
	
	if("companyInfoRegDate".equalsIgnoreCase(bc.getDataName())||"companyInfoRegDate".equalsIgnoreCase(bc.getDataName())){
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
		bc.setColumnStyle("text-align:center");
	}
	
	if("companyInfoType".equalsIgnoreCase(bc.getDataName())){
		if(t!=3){
	bc.setShowColumn(false);
	bc.setShowQuerySelsect(false);
	bc.setShowAdvanced(false);
		}else{
	//设置高级查询显示样式
	SelectType select  = new SelectType(EnumUtil.SYS_COMPANY_TYPE_SEL.getSelectAndText("-1,-请选择公司类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("repType");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
}

grid.setColumnList(colList);

grid.setShowProcess(true);
grid.setProcessMethodName("createMethod");

//设置附加信息
grid.setQueryFunction("queryData");	//查询的方法名
grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
grid.setDblBundle("primaryKey");		//双击列的绑定的列值

//开始创建
out.print(grid.createTable());
%>
</body>
</html>