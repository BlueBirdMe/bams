<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表格查看</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var forms = getQueryParam();
	var pager = getPager();
	dwrOACompanyResourcesService.getFormsByPagerAndPerm(forms, pager, queryCallback);
}
function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alertmsg(data);
	}
	endQuery();
} //双击数据
function dblCallback(obj) {
	var box = new Sys.msgbox('明细查看', '<%=contextPath%>/erp/company_resources/forms_detail.jsp?fid=' + obj.value + '&noid=1', '800', '500');
	box.msgtitle = "<b>查看表格明细</b>";
	var butarray = new Array();
	butarray[0] = "cancel|| 关 闭 ";
	box.buttons = butarray;
	box.show();
}
function getAcceCount(rowObj) {
	var count = 0;
	if (rowObj.oaFormAcce != null && rowObj.oaFormAcce != undefined && rowObj.oaFormAcce != "undefined" && rowObj.oaFormAcce.length > 0) {
		var cs = rowObj.oaFormAcce.split(",");
		count = cs.length;
	}
	return count;
}

</script>
</head>
<body>
<%
	SysGrid grid = new SysGrid(request,"常用表格列表");
	grid.setBorder(1);
	grid.setShowImg(false);//不显示图片信息
	grid.setCheckboxOrNum(false);

	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-常用表格"));

	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("oaFormType".equalsIgnoreCase(bc.getDataName())){
	SelectType select = new SelectType(UtilTool.getWareTypeString(this.getServletContext(),request,"-1,-请选择类型-",EnumUtil.OA_TYPE.FORMS.value));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
		}
		if("oaFormTime".equalsIgnoreCase(bc.getDataName())){
	DateType date = new DateType();
	bc.setColumnTypeClass(date);
		}
	}
	colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
	grid.setColumnList(colList);

	//设置附加信息
	grid.setQueryFunction("queryData");	//查询的方法名
	grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	grid.setDblBundle("primaryKey");//双击列的绑定的列值
	
	//开始创建
	out.print(grid.createTable());
%>
</body>
</html>