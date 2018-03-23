<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能目录管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var method = getQueryParam();
	//取得树值
	method.levelUnit = document.getElementById("upcode").value;
	var pager = getPager();
	dwrSysProcessService.getSysMethodList(method,pager,queryCallback);
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
	var box = new Sys.msgbox('功能目录明细查看','<%=contextPath%>/erp/system_manger/sys_method_detail.jsp?gid='+obj.value,'750','550');
	box.msgtitle="功能明细查看";
	box.show();
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的功能目录吗?","delectOk()");
	}else{
	    alertmsg("请选择要删除的功能目录！");
	}
}

function delectOk(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.deleteSysMethodInfoById(recordsPks,delCallback);
}

function delOk(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrSysProcessService.deleteSysMethodInfoById(ids,delCallback);
}

function delCallback(data){
	tree.reload();
    alertmsg(data,"queryData()");
}

function del(pk){
    confirmmsg("确定要删除此功能目录吗?","delOk('"+pk+"')");
}

function add(){
	openMDITab("<%=contextPath%>/erp/system_manger/sys_method_add.jsp?tab="+getMDITab());
}

function edit(pk){
	openMDITab("<%=contextPath%>/erp/system_manger/sys_method_edit.jsp?sid="+pk+"&tab="+getMDITab());
}

function setMethod(){
   //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要启用/禁用吗?设置后其下级目录也将失效！","setOk()");
	}else{
	    alertmsg("请选择要启用/禁用的功能目录！");
	}
}

function setOk(){
    var recordsPks = getAllRecordArray();
    dwrSysProcessService.setMethodInfoStatus(recordsPks,delCallback);
}

function setStatus(pk){
    confirmmsg("确定要启用/禁用吗?设置后其下级目录也将失效！","setMethodOk('"+pk+"')");
}

function setMethodOk(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrSysProcessService.setMethodInfoStatus(ids,delCallback);
}

function repmethodLevel(rowObj){
	var ml = rowObj.methodLevel;
	var str="";
	if(ml == <%=EnumUtil.SYS_METHOD_LEVEL.TOP.value%>){
		str ="<font color='blue'><%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.TOP.value)%></font>";
	}else if(ml == <%=EnumUtil.SYS_METHOD_LEVEL.ONE.value%>){
		str ="<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.ONE.value)%>";
	}else if(ml == <%=EnumUtil.SYS_METHOD_LEVEL.TWO.value%>){
		str ="<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.TWO.value)%>";
	}else{
		str ="<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.THREE.value)%>";
	}
	return str;
}

function repisAction(rowObj){
	var ac = rowObj.isAction;
	var str ="";
	if(ac == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
		str ="<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%>";
	}else{
		str ="<font color='red'><%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%></font>";
	}
	return str;
}

function createProcessMethod(rowObj){
    var str = "";
    if(rowObj.isAction == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
         str="<a href='javascript:void(0)' title='启用/禁用' onclick=\"setStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>&nbsp;&nbsp;";
    }else{
         str="<a href='javascript:void(0)' title='启用/禁用' onclick=\"setStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0' style='filter:gray;'/></a>&nbsp;&nbsp;";
    }
	
	str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function repImageSrc(rowObj){
	var imgName = rowObj.imageSrc;
	var imgSrc = "<%=contextPath%>/images/projectimg/"+rowObj.imageSrc;
	return "<img height='16' title='"+imgName+"' src='"+imgSrc+"'/>";
}
</script>
</head>
<body>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
<div class="div_title">选择上级</div>
<div class="div_content">
<jsp:include page="sys_method_tree.jsp" flush="false"></jsp:include>
</div>
</td>
<td>
<%
SysGrid grid = new SysGrid(request,"系统功能列表");
grid.setShowImg(false);//不显示图片信息
//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增","add()","add.png"));
btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
btnList.add(new SysGridBtnBean("启用/禁用","setMethod()","set.png"));
grid.setBtnList(btnList);
grid.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));
//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"系统功能列表"));

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if("imageSrc".equalsIgnoreCase(bc.getDataName())){
		bc.setColumnReplace("repImageSrc");
	}
	if(bc.isShowAdvanced()){
		if("methodLevel".equalsIgnoreCase(bc.getDataName())){
			SelectType select = new SelectType(EnumUtil.SYS_METHOD_LEVEL.getSelectAndText("-2,-请选择功能等级-"));
			select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
			bc.setColumnTypeClass(select);
			bc.setColumnReplace("repmethodLevel");
			bc.setColumnStyle("text-align:center;");
		}
		if("isAction".equalsIgnoreCase(bc.getDataName())){
			//设置高级查询显示样式
			SelectType select  = new SelectType(EnumUtil.SYS_ISACTION.getSelectAndText("-1,-请选择功能状态-"));
			select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
			bc.setColumnTypeClass(select);
			bc.setColumnReplace("repisAction");
			//设置列显示样式
			bc.setColumnStyle("text-align:center;");
		}
	}
}

grid.setColumnList(colList);

//设置列操作对象
grid.setShowProcess(true);//默认为false 为true请设置processMethodName
grid.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

//设置附加信息
grid.setQueryFunction("queryData");	//查询的方法名
grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
grid.setDblBundle("primaryKey");		//双击列的绑定的列值

//开始创建
out.print(grid.createTable());
%>
</td>
</tr>
</table>
</body>
</html>