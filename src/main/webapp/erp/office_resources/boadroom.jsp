<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议室管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var boadroom = getQueryParam();
	var pager = getPager();
	dwrOfficeResourcesService.listBoadrooms(boadroom,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function save(){
	Btn.close();
	var bl = validvalue('title');
	if(bl){
		var boardroom =getboardroom();
		dwrOfficeResourcesService.saveBoadroom(boardroom,savecallback);
	}
}
function savecallback(data){
	Btn.open();
	alertmsg(data,"sevent()");
	queryData();
}

function sevent(){
	DWRUtil.setValue("oaBoardroomId","");
	DWRUtil.setValue("roomName","");
	DWRUtil.setValue("roomCapacity","");
	DWRUtil.setValue("roomAddress","");
	DWRUtil.setValue("oaBoardroomEquipment","");
	DWRUtil.setValue("roomDescribe","");
}


function getboardroom(){
	var boadroom = new Object();
	boadroom.primaryKey = DWRUtil.getValue("oaBoardroomId");
	boadroom.oaBoardroomName = DWRUtil.getValue("roomName");
	boadroom.oaBoardroomCapacity = DWRUtil.getValue("roomCapacity");
	boadroom.oaBoardroomAddress = DWRUtil.getValue("roomAddress");
	boadroom.oaBoardroomDescribe = DWRUtil.getValue("roomDescribe");
	return boadroom;
}

function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+= "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}

function edit(id){
	MoveDiv.show('编辑会议室','<%=contextPath%>/erp/office_resources/boadroom_edit.jsp?id='+id);
}
function setpagevalue(data){
	if(data!=null){
		var boadroom =data.resultList[0];
		DWRUtil.setValue("oaBoardroomId",boadroom.primaryKey);
		DWRUtil.setValue("roomName",boadroom.oaBoardroomName);
		DWRUtil.setValue("roomCapacity",boadroom.oaBoardroomCapacity);
		DWRUtil.setValue("roomDescribe",boadroom.oaBoardroomDescribe);
		DWRUtil.setValue("roomAddress",boadroom.oaBoardroomAddress);
	}
}


function del(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
function delok(id){
	dwrOfficeResourcesService.deleteBoadroomsByPks(id,delcallback);
}
function delcallback(data){
	DWRUtil.setValue("oaBoardroomId","");
	alertmsg(data,"queryData()");
}


//双击数据
function dblCallback(obj){
	//MoveDiv.show('明细查看','<%=contextPath%>/erp/office_resources/boadroom_detail.jsp?nid='+obj.value);
	
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/boadroom_detail.jsp?nid='+obj.value,'700','450');
	box.msgtitle="<b>会议室明细查看</b>";
	box.show();
}

function add(){
	var url = '<%=contextPath%>/erp/office_resources/boadroom_edit.jsp';
	openMDITab(url + "?tab="+getMDITab());
}
	
</script>
</head>
<body>
	<%
		SysGrid grid = new SysGrid(request,"会议室列表");
			grid.setShowImg(false);//不显示图片信息.
			grid.setCheckboxOrNum(false);
			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("添加会议室", "add()","add.png"));
			grid.setBtnList(btnList);
			
			//放入列
			//ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-类型"));
			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
		    sccList.add(new SysColumnControl("oaBoardroomName","会议室名称",1,1,2,20));
		    sccList.add(new SysColumnControl("oaBoardroomCapacity","可容纳人数",1,1,2,0));
		    sccList.add(new SysColumnControl("oaBoardroomAddress","位置",1,1,2,50));
		    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
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