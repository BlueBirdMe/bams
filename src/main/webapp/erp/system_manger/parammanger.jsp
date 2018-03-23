<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<title>运行参数管理</title>
<%
 	String cidtemp = request.getParameter("cid");
	Integer cid = Integer.parseInt(cidtemp);
	WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	DwrSysProcessService sysProcessService = (DwrSysProcessService)context.getBean("dwrSysProcessService");
	ResultBean bean = sysProcessService.getSysCompanyInfoByPk(this.getServletContext(),request,cid.longValue());
	List<SysCompanyInfo> comList = bean.getResultList();
	SysCompanyInfo companyInfo = comList.get(0);
	String title = companyInfo.getCompanyInfoName();
%>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var param = getQueryParam();
	param.companyId = <%=cid%>;
	var pager = getPager();
	dwrSysProcessService.getSysParamBypager(param,pager,queryCallback);
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
}

function createMethod(rowObj){
	var str ="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function add(){
	Sys.load('<%=contextPath %>/erp/system_manger/parammanger_add.jsp?cid=<%=cid%>');
}
function edit(id){
	Sys.load('<%=contextPath %>/erp/system_manger/parammanger_add.jsp?pid='+id + "&cid=<%=cid%>");
}

function del(id){
	confirmmsg("<font color='red'>删除参数将影响程序运行，请慎重！</font><br/>确定要删除吗?","delok("+id+")");
}

function delok(id){
	dwrSysProcessService.deleteSysParamByPk(id,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

function repType(rowObj){
	var str="";
	if(rowObj.paramType == <%=EnumUtil.SYS_PARAM_TYPE.TEXT.value%>){
		str = "<%=EnumUtil.SYS_PARAM_TYPE.valueOf(EnumUtil.SYS_PARAM_TYPE.TEXT.value)%>";
	}else if(rowObj.paramType == <%=EnumUtil.SYS_PARAM_TYPE.NUM.value%>){
		str = "<%=EnumUtil.SYS_PARAM_TYPE.valueOf(EnumUtil.SYS_PARAM_TYPE.NUM.value)%>";
	}else if(rowObj.paramType == <%=EnumUtil.SYS_PARAM_TYPE.SELECT.value%>){
		str = "<%=EnumUtil.SYS_PARAM_TYPE.valueOf(EnumUtil.SYS_PARAM_TYPE.SELECT.value)%>";
	}
	return str;
}
</script>
</head>
<body>
<%
	SysGrid grid = new SysGrid(request,title + "运行参数列表");
grid.setShowImg(false);//不显示图片信息
grid.setBorder(1);
//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增","add()","add.png"));
grid.setBtnList(btnList);

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"运行参数"));
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("paramType".equalsIgnoreCase(bc.getDataName())){
		SelectType select = new SelectType(EnumUtil.SYS_PARAM_TYPE.getSelectAndText("-1,-请选择参数类型-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		bc.setColumnReplace("repType");
		bc.setColumnStyle("text-align:center;");
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