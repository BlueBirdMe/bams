<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>期刊类型</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
  useLoadingMassage();
	startQuery();
	var jourtype = getQueryParam();
	var pager = getPager();
	dwrOACompanyResourcesService.getJournalsTypeListPager(jourtype,pager,queryCallback);
}
	
function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+= "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}
function edit(id){
	MoveDiv.show('编辑期刊',"<%=contextPath%>/erp/company_resources/addjournals_type.jsp?fid="+id);
}
	
function del(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
function delok(id){
	dwrOACompanyResourcesService.deleteJournalsByPk(id,delcallback);
}
function delcallback(data){
	DWRUtil.setValue("typeid","");
	alertmsg(data,"queryData()");
}
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/company_resources/journals_type_detail.jsp?jid='+obj.value+'&noid=1','750','450');
	box.msgtitle="<b>查看期刊类型明细</b>";
	box.show();
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
function add(id){
	var url = "<%=contextPath%>/erp/company_resources/addjournals_type.jsp";
	openMDITab(url + "?tab="+getMDITab());
}
</script>
</head>
<body>
	<%
		SysGrid grid = new SysGrid(request,"期刊类型列表");
			grid.setShowImg(false);//不显示图片信息.
			grid.setCheckboxOrNum(false);
		//放入列
			
			ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("新增类型","add()","add.png"));
			grid.setBtnList(btnList);
			//放入列
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-期刊类型"));
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