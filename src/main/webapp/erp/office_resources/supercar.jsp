<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>车辆管理</title>
<script>
//查询方法
	function queryData(){
	startQuery();
	var car = getQueryParam();
	var pager = getPager();
	dwrOfficeResourcesService.listCars(car,pager,queryCallback);
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
	openMDITab('<%=contextPath%>/erp/office_resources/car_detail.jsp?cid='+obj.value);
}


function edit(pk){
   MoveDiv.show('编辑车辆状态',"<%=contextPath%>/erp/office_resources/car_add.jsp?carpk="+pk+'&url=office_resources/supercar.jsp');
}

function del(pk){
	confirmmsg("确定要删除记录吗?此次删除将会删除申请车辆，维修车辆的记录，请谨慎操作","delok("+pk+")");
}

function delok(pk){
	var pks = new Array();
	pks[0] = pk;
	dwrOfficeResourcesService.deleteSuperCarsByPks(pks,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}


function deleteObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?此次删除将会删除申请车辆，维修车辆的记录，请谨慎操作","delbatchok()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}
function delbatchok(){
	var recordsPks = getAllRecordArray();
	dwrOfficeResourcesService.deleteSuperCarsByPks(recordsPks,delcallback);
}


function createProcessMethod(rowObj){
	var str="";

	str+="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;<a href='javascript:void(0)' title='维护' onclick=\"modi('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/bullet_wrench.png' border='0''/></a>";
	str+="&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function repcarsta(rowObj){
	var str ="";
	if(rowObj.oaCarSta == <%=EnumUtil.OA_CAR_STA.GOOD.value%>){
		str = "<font color='green'><%=EnumUtil.OA_CAR_STA.valueOf(EnumUtil.OA_CAR_STA.GOOD.value)%></font>";
	}else{
		str = "<font color='red'><%=EnumUtil.OA_CAR_STA.valueOf(EnumUtil.OA_CAR_STA.FAIL.value)%></font>";
	}
	return str;
}

function modi(id){
	var url = "<%=contextPath%>/erp/office_resources/car_mainten_add.jsp?pk="+id+'&url=office_resources/supercar.jsp';
	url += "&tab="+getMDITab();
	openMDITab(url);
}

function repPrice(rowObj){
	var str="";
	if(rowObj.oaCarPrice!=null){
		str = "￥"+FormatNumber(rowObj.oaCarPrice,2);
	}
	return str;
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
		bg.setTableTitle("车辆管理");
		bg.setShowImg(true);//默认为true 显示切换视图 为true必须指定图片相关信息

//设置附加信息
		bg.setQueryFunction("queryData");	//查询的方法名
		bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
		bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
		btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
		bg.setBtnList(btnList);

//放入列
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
   		sccList.add(new SysColumnControl("oaCarCards","车牌号",1,1,1,20));//设置GRIV 列
   		sccList.add(new SysColumnControl("oaCarName","车辆名称",1,1,1,50));
   	 	sccList.add(new SysColumnControl("library.libraryInfoName","车辆类型",1,2,2,0));
    	sccList.add(new SysColumnControl("oaCarPrice","购买价格",1,2,2,50));
    	sccList.add(new SysColumnControl("oaCarBuydate","购买日期",1,2,1,50));
    	sccList.add(new SysColumnControl("oaCarSta","运行状态",1,2,1,0));
    	sccList.add(new SysColumnControl("oaCarType","车辆类型",2,2,1,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){

	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
	
    	if("oaCarBuydate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
	bc.setColumnStyle("text-align:center;");
		}
		if("oaCarType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择车辆类型-","13"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
	bc.setColumnStyle("text-align:center;");
		}
		
		if("oaCarSta".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	SelectType select  = new SelectType(EnumUtil.OA_CAR_STA.getSelectAndText("-1,-请选择当前状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("repcarsta");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaCarPrice".equalsIgnoreCase(bc.getDataName())){
	bc.setColumnStyle("text-align:right;");
	bc.setColumnReplace("repPrice");
		}
		if("oaCarCards".equalsIgnoreCase(bc.getDataName())){
	bc.setColumnStyle("text-align:center;");
		}
	}
}

		bg.setColumnList(colList);
//设置列操作对象 
  		 bg.setShowProcess(true);//默认为false 为true请设置processMethodName
  		 bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象
  		 bg.setImgShowUrl("oaCarPhoto");//显示img的属性字段，没有填写-1
  		 bg.setImgShowText("oaCarName");
  		 bg.setImgNoDefaultPath(absPath+"/images/noimages/other.png");//可以不指定，系统采用默认暂无图片
  		 bg.setImgdivwidth("300");//显示详细信息的div大小，默认280;
  		 bg.setImgheight("100");//不设置为自动
  		 bg.setImgShowTextLen(10);//显示文本的最大长度,不设置为8个字符
  		 bg.setShowImageDetail(true);//是否显示图片详细
//开始创建
		out.print(bg.createTable());
%>
</body>
</html>