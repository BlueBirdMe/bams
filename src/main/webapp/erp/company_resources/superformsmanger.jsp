<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表格管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<script type="text/javascript">
	//查询方法
	function queryData(){
		startQuery();
		var forms = getQueryParam();
		var pager = getPager();
		dwrOACompanyResourcesService.getSuperFormsListByPager(forms,pager,queryCallback);
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
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/company_resources/forms_detail.jsp?fid='+obj.value+'&noid=1','800','500');
	box.msgtitle="<b>查看表格明细</b>";
	box.show();
	}
	function createMethod(rowObj){
		var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
		return str;
	}
	function getAcceCount(rowObj){
		var count =0 ;
		if(rowObj.oaFormAcce!=null&&rowObj.oaFormAcce != undefined&& rowObj.oaFormAcce != "undefined"&&rowObj.oaFormAcce.length>0){
			var cs = rowObj.oaFormAcce.split(",");
			count = cs.length;
		}
		return count;
	}
	function add(){
		MoveDiv.show('添加常用表格',"<%=contextPath%>/erp/company_resources/forms_add.jsp?url=company_resources/superformsmanger.jsp");
	}
	function edit(id){
		MoveDiv.show('编辑常用表格',"<%=contextPath%>/erp/company_resources/forms_add.jsp?fid="+id+'&url=company_resources/superformsmanger.jsp');
	}
	function del(id){
		confirmmsg("确定要删除记录吗?","delok("+id+")");
	}
	
	function delok(id){
		var ids = new Array();
		ids[0] = id;
		dwrOACompanyResourcesService.deleteFormsByIds(ids, delcallback);
	
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
		dwrOACompanyResourcesService.deleteFormsByIds(recordsPks,delcallback);
	}
</script>
</head>
<body>
<%
	SysGrid grid = new SysGrid(request,"常用表格列表");
grid.setBorder(1);
grid.setShowImg(false);//不显示图片信息
//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("批量删除","deletebatch()","close.png"));
grid.setBtnList(btnList);

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

grid.setShowProcess(true);
grid.setProcessMethodName("createMethod");

//设置附加信息
grid.setQueryFunction("queryData");	//查询的方法名
grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
grid.setDblBundle("primaryKey");//双击列的绑定的列值

//开始创建
out.print(grid.createTable());
%>
</body>
</html>