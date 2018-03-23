<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>通讯手册</title>
		<script>
//查询方法
function queryData(){
	startQuery();
	var communication = getQueryParam();
	var pager = getPager();
	dwrOaCommunicationService.listCommunications(communication,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/communication/communication_detail.jsp?chatId='+obj.value,'800','500');
		box.msgtitle="<b>查看通讯录记录明细</b>";
		box.show();
}
function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的通讯手册吗？","delRecord()");
	}else{
	    alertmsg("请选择要删除的通讯手册记录！");
	}
}
function delRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaCommunicationService.deleteCommunicationById(recordsPks,deleCallback);
}
function del(pk){ 
    confirmmsg("确定要删除此通讯手册吗？","delok("+pk+")");
}
function delok(id){
    var ids = new Array();
    ids[0] = id;
    dwrOaCommunicationService.deleteCommunicationById(ids,deleCallback);
}
function deleCallback(data){
   alertmsg(data,"queryData()");
}

function edit(pk){
	MoveDiv.show('编辑',"<%=contextPath%>/erp/communication/communications_manual_add.jsp?chatId="+pk);
}
function importDate(){
    alert("导入通讯手册记录");
}
function exportDate(){
    alert("导出通讯手册记录");
}
function replaceSex(rowObj){
	var str="";
	if(rowObj.oaChatSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<font style='color:green'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%></font>"
	}
	return str;
}
function replaceIsShare(rowObj){
	var str="";
	if(rowObj.oaIsShare ==<%=EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value%>){
		str= "<font style='color:green'><%=EnumUtil.OA_COMMUNICATION_IS_SHARE.valueOf(EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value)%></font>";
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_COMMUNICATION_IS_SHARE.valueOf(EnumUtil.OA_COMMUNICATION_IS_SHARE.UNSHARE.value)%></font>";
	}
	return str;
}

function shareSet(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("设置共享则原有共享记录将丢失,确定共享吗？","setRecord()");
	}else{
	    alertmsg("请选择要共享的通讯手册记录！");
	}
}

function setRecord(){
    var recordsPks = getAllRecordArray();
    MoveDiv.show('设置共享',"<%=contextPath%>/erp/communication/shareSetBacth.jsp?pks="+recordsPks);
}

function share(pk){
    confirmmsg("设置共享则原有共享记录将丢失,确定共享吗？","setShareOk("+pk+")");
}

function setShareOk(pk){
    var ids = new Array();
    ids[0] = pk;
    MoveDiv.show('设置共享',"<%=contextPath%>/erp/communication/shareSetBacth.jsp?pks="+ids);
}

function createProcessMethod(rowObj){
    var str="";
    if(rowObj.oaCheckId != null && rowObj.oaCheckId != undefined && rowObj.oaCheckId != "undefined" && rowObj.oaCheckId.length>0){
         str="<a href='javascript:void(0)' title='签入记录，不可共享'><img src='<%=contextPath%>/images/grid_images/chart_pie_link.png' border='0' style='filter:gray;'/></a>&nbsp;&nbsp;";
    }else{
         str="<a href='javascript:void(0)' title='设置共享' onclick=\"share('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/chart_pie_link.png' border='0'/></a>&nbsp;&nbsp;";
    }
	str+= "<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;&nbsp;"
    str+="<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
    return  str;
}


function add(){
	var url = '<%=contextPath%>/erp/communication/communications_manual_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("通讯手册管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(true);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("添加联系人","add()","add.png"));
    btnList.add(new SysGridBtnBean("设置共享","shareSet()","wrench.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));

    
    nw.setBtnList(btnList);
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"通讯手册"));
    
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaChatSex".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.HRM_EMPLOYEE_SEX.getSelectAndText("-1,-请选择性别-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("replaceSex");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaChatGroup".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getChatGroupInfoList(this.getServletContext(),request,"-1,-请选择所属分组-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaIsShare".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_COMMUNICATION_IS_SHARE.getSelectAndText("-1,-请选择共享状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("replaceIsShare");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
   }
 //设置列操作对象
nw.setShowProcess(true);//默认为false 为true请设置processMethodName
nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象   
    
    nw.setColumnList(colList);
    
    
    //设置图片显示信息
//bg.setImgShowNum(6);//不指定默认5个
nw.setImgShowUrl("oaChatPhotos");//显示img的属性字段，没有填写-1
nw.setImgShowText("oaChatEmp");
nw.setImgNoDefaultPath(absPath+"/images/noimages/other.png");//可以不指定，系统采用默认暂无图片

nw.setImgdivwidth("300");//显示详细信息的div大小，默认280;

nw.setImgheight("128");//不设置为自动


nw.setShowImageDetail(true);//是否显示图片详细
    
    //开始创建
out.print(nw.createTable());
%>
	</body>
</html>
