<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>java生成grid</title>
<%
String cid =request.getParameter("pk");
 %>
<script>
//查询方法
function queryData(){
	startQuery();
	var carmainten = new Object();
	var pager = getPager();
	dwrOfficeResourcesService.listCarmaintens(carmainten,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/car_mainten_detail.jsp?cid='+obj.value,'650','450');
		box.msgtitle="<b>车辆维护明细列表</b>";
		box.show();
}

function add(){
/*var box = new Sys.msgbox('新增会议室','office_resources/offresadmin_add.jsp','800','500');
	box.msgtitle="新增会议室";
	var butarray = new Array();
	butarray[0] = "ok|saveoredit();";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();*/
	Sys.load('office_resources/car_mainten_add.jsp');
}

function update(){
	//针对一条记录进行操作
	if(getOneRecordArray() != false){
		var obj=getObjectByPk(getOneRecordArray());
		Sys.load("office_resources/car_mainten_add.jsp?carmaintenpk='"+obj.primaryKey+"'");
	}
}

function edit(id){
	var obj=getObjectByPk(id);
	var str="employee["+obj.primaryKey+"]:\n\n\thrmEmployeeName = "+obj.hrmEmployeeName+"\n\n\thrmEmployeeInTime = "+obj.hrmEmployeeInTime+"\n\n";
	alert(str);
}

function edit(pk){
    Sys.load("office_resources/car_mainten_add.jsp?carmaintenpk='"+pk+"'");
}

function del(pk){
		confirmmsg("确定要删除记录吗?","delok("+pk+")");
	}
	
	function delok(pk){
		var pks = new Array();
		pks[0] = pk;
		dwrOfficeResourcesService.deleteCarmaintensByPks(pks,delcallback);
	}
	
	function delcallback(data){
		alertmsg(data,"queryData()");
	}
	
	
	function deleteObject(){
		if(getAllRecordArray() != false){
			confirmmsg("确定要删除记录吗?","delbatchok()");
		}else{
			alertmsg("请选择要删除的记录...");
		}
	}
	function delbatchok(){
		var recordsPks = getAllRecordArray();
		dwrOfficeResourcesService.deleteCarmaintensByPks(recordsPks,delcallback);
	}
	

function createProcessMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
		return str;
}


function info(){
	var objs = getRowsObject();
	var str="";
	for(var i=0;i<objs.length;i++){
		str+="employee["+objs[i].primaryKey+"]:\n\n\thrmEmployeeName = "+objs[i].hrmEmployeeName+"\n\n\thrmEmployeeInTime = "+objs[i].hrmEmployeeInTime+"\n\n";
	}
	alert(str);
}
function getother(){
	var a = document.getElementById("hrmtest1").value+"-"+document.getElementById("hrmtest2").value;
	return a;
	//alert("不封装对象属性获取:"+a.value);
}

//列显示替换方法
function repleaSex(rowObj){
	var str="";
	if(rowObj.hrmEmployeeSex ==<%=EnumUtil.HRM_EMPLOYEE_SEX.Man.value%>){
		str= "<font style='color:blue'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Man.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.HRM_EMPLOYEE_SEX.valueOf(EnumUtil.HRM_EMPLOYEE_SEX.Woman.value)%></font>"
	}
	return str;
}


function repleahrmName(rowObj){
	var str="";
	var namelen = rowObj.hrmEmployeeName.length;
	if(namelen > 0 && namelen<=6){
		str= "<font style='color:#336699'>名字长度:"+namelen+"个字符</font>"
	}else{
		str= "名字长度:"+namelen+"个字符";
	}
	return str;
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);

//设置高度及标题
//bg.setTableHeight("100%");//可以不指定,默认为100%
//bg.setTableWidth("80%");//可以不指定,默认为100%
//bg.setBodyScroll("auto");//设置body页面是否显示滚动条，默认为hidden auto scroll
bg.setTableTitle("车辆维护");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
//bg.setIsautoQuery(false);//默认为自动执行查询
//bg.setTableRowSize(20);//默认每页显示记录数30 选择10 20 30 50 80  
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增","add()","add.png"));
btnList.add(new SysGridBtnBean("删除","deleteObject()","close.png"));
btnList.add(new SysGridBtnBean("修改","update()","edit.png"));
btnList.add(new SysGridBtnBean("查看","info()","info.png"));
bg.setBtnList(btnList);

ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("oaMaintenCards","车牌号",1,1,1,20));
sccList.add(new SysColumnControl("oaMaintenName","车辆名称",1,1,1,50));
sccList.add(new SysColumnControl("oaMaintenEmp","维护人",1,1,1,50));
sccList.add(new SysColumnControl("oaMaintenDate","维护时间",1,1,1,50));
sccList.add(new SysColumnControl("oaMaintenType","维护类型",2,2,1,50));
sccList.add(new SysColumnControl("library.libraryInfoName","维护类型",1,2,2,0));
sccList.add(new SysColumnControl("oaMaintenCause","维护原因",2,2,2,50));
sccList.add(new SysColumnControl("oaMaintenCharge","维护费用",2,2,2,50));
sccList.add(new SysColumnControl("oaMaintenRemark","备注",2,2,2,50));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaMaintenDate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;color:blue;");
		}
		if("oaMaintenCards".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
		//	SelectType select  = new SelectType(EnumUtil.OA_MAINTEN_CARDS.getSelectAndText("-1,-请选择车牌号-"));
		//	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		//	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaMaintenType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择维护类型-","16"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
		
		
		//自定义且不封装到对象
		if("hrmtest".equalsIgnoreCase(bc.getDataName())){
	//自定义对象
	OtherType other = new OtherType("<input type ='text' class ='niceform' id ='hrmtest'/>");
	other.setGetValueMethod("getother()");
	bc.setColumnTypeClass(other);
	//不封装对象
	bc.setColumnToObject(false);
		}
	}
}

bg.setColumnList(colList);

//设置列操作对象
bg.setShowProcess(true);//默认为false 为true请设置processMethodName
bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象


//设置图片显示信息
//bg.setImgShowNum(6);//不指定默认5个
bg.setImgShowUrl("hrmEmployeeImageInfoId");//显示img的属性字段，没有填写-1
bg.setImgShowText("hrmEmployeeName");
bg.setImgNoDefaultPath(absPath+"/images/noimages/other.png");//可以不指定，系统采用默认暂无图片
//bg.setImgShowCode("_Min");//如果需要显示的图片为缩略图请使用
//bg.setImgdivwidth("300");//显示详细信息的div大小，默认280;
//bg.setImgwidth("auto");//不设置为自动
bg.setImgheight("128");//不设置为自动
//bg.setImgShowTextLen(10);//显示文本的最大长度,不设置为8个字符



//开始创建
out.print(bg.createTable());
%>
</body>
</html>