<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String aid =request.getParameter("aid");
WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOACompanyResourcesService oaCompanyService = (DwrOACompanyResourcesService) webAppContext.getBean("dwrOACompanyResourcesService");
ResultBean bean = oaCompanyService.getAlbumByPk(this.getServletContext(),request,Long.parseLong(aid),true);
OaAlbum am = new OaAlbum();
if(bean!=null&&bean.getResultList().size()>0){
	am=(OaAlbum)bean.getResultList().get(0);
}
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<title>相册相片管理</title>
<script type="text/javascript">
function queryData(){
	useLoadingMassage();
	startQuery();
	var photo = getQueryParam();
	photo.albumId = <%=aid%>;
	var pager = getPager();
	dwrOACompanyResourcesService.getPhotoByPager(photo,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
		/*
			DWRUtil.setValue("AlbumName",'<%=am.getAlbumName() %>');
			DWRUtil.setValue("AlbumTime",'<%=am.getAlbumTime() %>');
			DWRUtil.setValue("CreateEmployee",'<%=am.getCreateEmployee()==null?"无":am.getCreateEmployee().getHrmEmployeeName() %>');
			DWRUtil.setValue("count",'	<%=am.getAlbumPhotoCount() %>'+" 张");
			DWRUtil.setValue("LibraryInfoName",'<%=am.getLibraryType().getLibraryInfoName() %>');
			DWRUtil.setValue("AlbnumDesc",'<%=am.getAlbumDesc() %>');
			*/
	}else{
		alertmsg(data);
	}
	endQuery();
}

function realbum(){
	window.location.href = "<%=contextPath%>/erp/company_resources/photos_manger.jsp";
}
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath %>/erp/company_resources/photo_detail.jsp?pid='+obj.value+'&noid=1','900','550');
	box.msgtitle="<b>查看相片明细</b>";
	var butarray = new Array();
	butarray[0] = "ok|photoShow();|查看大图";
	butarray[1] = "cancel|| 关 闭 ";
	box.buttons = butarray;
	box.show();
}
function edit(){
	if(getAllRecordArray() != false){
		if(getAllRecordArray().length>1){
			alertmsg("只能对一个相片进行修改...");
		}else{
		
			MoveDiv.show('编辑相片信息','<%=contextPath%>/erp/company_resources/photo_edit.jsp?pid='+getAllRecordArray());
		}
	}else{
		alertmsg("请选择要修改的相片...");
	}
}
function del(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除相片吗?","delok()");
	}else{
		alertmsg("请选择要删除的相片...");
	}
}
	
function delok(){
	dwrOACompanyResourcesService.deletePhoto(getAllRecordArray(),callback);
}
	
	
function callback(data){
	alertmsg(data,"queryData()");
}
	
function setface(){
	if(getAllRecordArray() != false){
		if(getAllRecordArray().length>1){
			alertmsg("只能设置一张相片为封面...");
		}else{
			dwrOACompanyResourcesService.updateAlbumByPhoto(getAllRecordArray()[0],callback);
		}
	}else{
		alertmsg("请选择要设置的相片...");
	}
}
	
function repisface(rowObj){
	var str=rowObj.photoName;
	if(rowObj.isAlubmFace!=null&&rowObj.isAlubmFace.length>0){
		str = "<font color='green'>"+rowObj.isAlubmFace+"</font>&nbsp;"+rowObj.photoName;
	}
	return str;
}
function moveph(){
	if(getAllRecordArray() != false){
		var box = new Sys.msgbox('相册选择','<%=contextPath%>/erp/company_resources/select_album.jsp?aid=<%=aid%>&textid=albumId','700','450');
		box.msgtitle="<b>相片转移</b>";
		var butarray = new Array();
		butarray[0] = "ok|getAlbumId(null,'movephotosback()');";
		butarray[1] = "cancel";
		box.buttons = butarray;
		box.show();
	}else{
		alertmsg("请选择要转移的相片...");
	}
}

function movephotosback(){
	var ids = getAllRecordArray();
	var abid = document.getElementById("albumId").value;
	if(abid.length>0){
		dwrOACompanyResourcesService.movePhotos(ids,abid,callback);
	}
}

</script>
</head>
<body style="overflow: hidden;">
<input type="hidden" id="albumId">

<%
	SysGrid grid = new SysGrid(request,"相册：<font color='blue'>"+am.getAlbumName()+"</font>");
	grid.setBorder(1);
	grid.setShowView(SysGrid.SHOW_ALL);
	grid.setDefaultShow(SysGrid.DEFAULT_SHOWIMAGE);
	grid.setTableRowSize(20);
	
	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("修改相片","edit()","edit.png"));
	btnList.add(new SysGridBtnBean("删除相片","del()","close.png"));
	btnList.add(new SysGridBtnBean("设为相册封面","setface()","set.png"));
	btnList.add(new SysGridBtnBean("转移相片","moveph()","moveset.png"));
	btnList.add(new SysGridBtnBean("返回相册列表","realbum()","return.png"));
	grid.setBtnList(btnList);
	
	
	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司相册-相片"));
	
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc = colList.get(i);
		if("photoName".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repisface");
		}
	}
	grid.setColumnList(colList);
	
	//设置图片显示信息
	grid.setImgShowUrl("imageId");//显示img的属性字段，没有填写-1
	grid.setImgShowText("&<font color='green'>,isAlubmFace,&</font>&nbsp;,photoName");
	grid.setImgheight("64");//不设置为自动
	grid.setImgShowTextLen(50);//显示文本的最大长度,不设置为8个字符
	//设置附加信息
	grid.setQueryFunction("queryData");	//查询的方法名
	grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	grid.setDblBundle("primaryKey");	//双击列的绑定的列值
	
	//开始创建
	out.print(grid.createTable());
%>
		
</body>
</html>