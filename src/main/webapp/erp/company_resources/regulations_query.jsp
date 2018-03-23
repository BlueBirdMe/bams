<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
  <%@ include file="../editmsgbox.jsp" %>
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
	dwrOACompanyResourcesService.getAllRegulationsVaildByPager(regul,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaRegulationsAttachs!=null&&rowObj.oaRegulationsAttachs != undefined&& rowObj.oaRegulationsAttachs != "undefined"&&rowObj.oaRegulationsAttachs.length>0){
		var cs = rowObj.oaRegulationsAttachs.split(",");
		count = cs.length;
	}
	return count;
}

function dblCallback(obj){
	//MoveDiv.show('明细查看','<%=contextPath%>/erp/company_resources/regulations_detail.jsp?rid='+obj.value);
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/company_resources/regulations_detail.jsp?rid='+obj.value,'800','500');
	box.msgtitle="<b>查看规章记录明细</b>";
	box.show();
}
</script>
</head>
<body>
<%
SysGrid grid = new SysGrid(request,"规章制度列表");
grid.setShowImg(false);//不显示图片信息
grid.setCheckboxOrNum(false);
//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-规章制度"));
//移除列
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("regulationsStatus".equalsIgnoreCase(bc.getDataName())){
		colList.remove(i);
	}
}
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
	if("regulatStratTime".equalsIgnoreCase(bc.getDataName())){
		DateType date = new DateType();
		bc.setColumnTypeClass(date);
		bc.setColumnStyle("text-align:center;");
	}
}
colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
grid.setColumnList(colList);

//操作提示
ArrayList<SysGridTitleBean> titleList = new ArrayList<SysGridTitleBean>();
titleList.add(new SysGridTitleBean("","双击行显示规章制度明细信息"));
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