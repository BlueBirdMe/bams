<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识仓库类型</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var waretype = getQueryParam();
	waretype.formsorware = <%=EnumUtil.OA_TYPE.WARW.value %>;
	var pager = getPager();
	dwrOACompanyResourcesService.getWareTypeByPager(waretype, pager, queryCallback);
}

function createMethod(rowObj){
	var str = "<a href='javascript:void(0)' title='编辑' onclick=\"edit('" + rowObj.primaryKey + "')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('" + rowObj.primaryKey + "')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}

function del(id) {
	confirmmsg("确定要删除记录吗?", "delok(" + id + ")");
}

function delok(id) {
	dwrOACompanyResourcesService.deleteWareTypeBypk(id, delcallback);
}

function delcallback(data) {
	DWRUtil.setValue("typeid", "");
	alertmsg(data, "queryData()");
}

function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alertmsg(data);
	}
	endQuery();
} 
//双击数据
function dblCallback(obj) {
	var box = new Sys.msgbox('明细查看', '<%=contextPath%>/erp/company_resources/waretype_detail.jsp?wid=' + obj.value + '&noid=1', '700', '450');
	box.msgtitle = "<b>查看知识类型明细</b>";
	box.show();
}

function repPremcount(rowObj) {
	var str = rowObj.premCount;
	if (rowObj.premCount == null || rowObj.premCount.length == 0 || rowObj.premCount == 0) {
		str = "无限制";
	}
	return str;
}

function edit(id) {
	MoveDiv.show('编辑类型', "<%=contextPath%>/erp/company_resources/addwaretype.jsp?fid=" + id);
}

function add() {
	var url = '<%=contextPath%>/erp/company_resources/addwaretype.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
</script>
</head>
<body>
<input type="hidden" id="typeid">
<%
	SysGrid grid = new SysGrid(request,"知识仓库类型列表");
		grid.setShowImg(false);//不显示图片信息.
		grid.setCheckboxOrNum(false);
		//放入按钮
		ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
		btnList.add(new SysGridBtnBean("新增类型","add()","add.png"));
		grid.setBtnList(btnList);
		//放入列
		ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-类型"));
		colList.add(ColumnUtil.getCusterShowColumn("premcount","可查看人员数量","repPremcount",0,"padding-left:10px"));
		grid.setColumnList(colList);
		grid.setShowProcess(true);
		grid.setProcessMethodName("createMethod");
		
		//设置附加信息
		grid.setQueryFunction("queryData");	//查询的方法名
		grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
		grid.setDblBundle("primaryKey");	//双击列的绑定的列值
		
		//开始创建
		out.print(grid.createTable());
%>		
</body>
</html>