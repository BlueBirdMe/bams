<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规章制度管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
	//查询方法
function queryData(){
   
	startQuery();
	var regul = getQueryParam();
	var pager = getPager();
	dwrOACompanyResourcesService.getAllRegulationsByPager(regul,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	if(rowObj.regulationsStatus == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为无效' onclick=\"setvalid('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.No_Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0'/></a>";
	}else{
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='设置为有效' onclick=\"setvalid('"+rowObj.primaryKey+"',<%=EnumUtil.SYS_ISACTION.Vaild.value%>)\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0' /></a>";
	}
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}
function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaRegulationsAttachs!=null&&rowObj.oaRegulationsAttachs != undefined&& rowObj.oaRegulationsAttachs != "undefined"&&rowObj.oaRegulationsAttachs.length>0){
		var cs = rowObj.oaRegulationsAttachs.split(",");
		count = cs.length;
	}
	return count;
}
function getStatus(rowObj){
	var str="";
	if(rowObj.regulationsStatus == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str = "<font color='green'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%></font>";
	}else{
		str = "<font color='red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}
	return str;
}

function edit(id){
	MoveDiv.show('编辑制度',"<%=contextPath%>/erp/company_resources/regulations_add.jsp?rid="+id);
}
function del(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrOACompanyResourcesService.deleteOaRegulationsIds(ids,delcallback);
}
	
function delcallback(data){
	alertmsg(data,"queryData()");
}
	
function deletebatch(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}
function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrOACompanyResourcesService.deleteOaRegulationsIds(recordsPks,delcallback);
}
	
function setvalid(id){
	var ids = new Array();
	ids[0] = id;
	dwrOACompanyResourcesService.setOaRegulationsStatus(ids,delcallback);
}
	
function set(){
	if(getAllRecordArray() != false){
	confirmmsg("确定设置记录吗?","setOaRegulationStatus()");			
	}else{
		alertmsg("请选择要设置的记录...");
	}
}
function setOaRegulationStatus(){
	dwrOACompanyResourcesService.setOaRegulationsStatus(getAllRecordArray(),delcallback);
}
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/company_resources/regulations_detail.jsp?rid='+obj.value+'&noid=1','800','500');
	box.msgtitle="<b>查看制度明细</b>";
	box.show();
}
</script>
</head>
<body>
<%
	SysGrid grid = new SysGrid(request,"规章制度列表");
	grid.setShowImg(false);//不显示图片信息
	//放入按钮
	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
	
	btnList.add(new SysGridBtnBean("设置有效性","set()","set1.png"));
	btnList.add(new SysGridBtnBean("批量删除","deletebatch()","close.png"));
	grid.setBtnList(btnList);
	
	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-规章制度"));
	
	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("oaRegulationsType".equalsIgnoreCase(bc.getDataName())){
	SelectType select = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择类型-","11"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
		}
		if("oaRegulationsTime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType();
	bc.setColumnTypeClass(date);
	
	bc.setColumnStyle("text-align:center;");
		}
		if("regulationsStatus".equalsIgnoreCase(bc.getDataName())){
	SelectType select = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("getStatus");
	bc.setColumnStyle("text-align:center;");
		}
		if("regulatStratTime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType();
	bc.setColumnTypeClass(date);
	
	bc.setColumnStyle("text-align:center;");
		}
		
	}
	colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
	grid.setColumnList(colList);
	
	grid.setShowProcess(true);
	grid.setProcessMethodName("createMethod");
	
	//操作提示
	ArrayList<SysGridTitleBean> titleList = new ArrayList<SysGridTitleBean>();
	String imgpath = contextPath+"/images/grid_images";
	titleList.add(new SysGridTitleBean("<img src='"+imgpath+"/set.png' border='0'/>","‘有效性设置’将根据用户选择批量将 有效 更改为 无效，无效更改为有效"));
	grid.setHelpList(titleList);
	
	//设置附加信息
	grid.setQueryFunction("queryData");	//查询的方法名
	grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	grid.setDblBundle("primaryKey");//双击列的绑定的列值
	
	//开始创建
	out.print(grid.createTable());
%>
</body>
</html>