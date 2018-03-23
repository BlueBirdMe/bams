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
String type = request.getParameter("type");
WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrOACompanyResourcesService oaCompanyService = (DwrOACompanyResourcesService) webAppContext.getBean("dwrOACompanyResourcesService");
ResultBean bean = oaCompanyService.getAlbumByPk(this.getServletContext(),request,Long.parseLong(aid),true);
OaAlbum am = new OaAlbum();
if(bean!=null&&bean.getResultList().size()>0){
	am=(OaAlbum)bean.getResultList().get(0);
}
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOACompanyResourcesService.js"></script>
<title>相册明细</title>
<script type="text/javascript">
function queryData(){
	startQuery();
	var photo = getQueryParam();
	photo.albumId = <%=aid%>;
	var pager = getPager();
	useLoadingMassage();
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
	if("<%=type%>"=="query"){
		window.location.href = "<%=contextPath%>/erp/company_resources/photos_albumquery.jsp";
	}else{
		window.location.href = "<%=contextPath%>/erp/company_resources/photos_albumset.jsp";
	}
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
function repisface(rowObj){
	var str=rowObj.photoName;
	if(rowObj.isAlubmFace!=null&&rowObj.isAlubmFace.length>0){
		str = "<font color='green'>"+rowObj.isAlubmFace+"</font>&nbsp;"+rowObj.photoName;
	}
	return str;
}
</script>
</head>
<body style="overflow: hidden;">
<%
	SysGrid grid = new SysGrid(request,"相册：<font color='blue'>"+am.getAlbumName()+"</font>");
		grid.setBorder(1);
		grid.setShowView(SysGrid.SHOW_ALL);
		grid.setDefaultShow(SysGrid.DEFAULT_SHOWIMAGE);
		grid.setTableRowSize(20);
		grid.setCheckboxOrNum(false);
		ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
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