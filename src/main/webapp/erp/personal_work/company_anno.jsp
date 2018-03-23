<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
  <%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
		<title>公司公告</title>
		<script>
function queryData(){
	startQuery();
	var announcement = getQueryParam();
	announcement.oaAnnoStatus = <%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>;
	var pager = getPager();
	dwrOaNewsService.listAnnouncementView(announcement,pager,queryCallback);
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
		//MoveDiv.show('明细查看','<%=contextPath%>/erp/issue_info/announcement_detail.jsp?aid='+obj.value);
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/announcement_detail.jsp?aid='+obj.value,'800','500');
		box.msgtitle="<b>查看公告记录明细</b>";
		box.show();
}

function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaAnnoAcce!=null&&rowObj.oaAnnoAcce != undefined&& rowObj.oaAnnoAcce != "undefined"&&rowObj.oaAnnoAcce.length>0){
		var cs = rowObj.oaAnnoAcce.split(",");
		count = cs.length;
	}
	return count;
}

function replaceLevel(rowObj){
	var src = "";
    if(rowObj.oaAnnoLevel == <%=EnumUtil.OA_NEWS_ISTOP.YES.value%>){
         src= "<image src='<%=request.getContextPath()%>/images/lve1.gif' border='0' title='重要' height='13' style='vertical-align: middle;'/>" ;
	}
    return src;
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("公告查看");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    nw.setCheckboxOrNum(false);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("oaAnnoLevel","",1,2,2,0));
    sccList.add(new SysColumnControl("oaAnnoName","公告标题",1,1,1,40));
    sccList.add(new SysColumnControl("employee.hrmEmployeeName","发布人",1,1,1,0));
    sccList.add(new SysColumnControl("oaAnnoLib.libraryInfoName","公告类型",1,2,2,0));
    sccList.add(new SysColumnControl("oaAnnoType","公告类型",2,2,1,0));
    sccList.add(new SysColumnControl("oaAnnoTime","发布时间",1,2,1,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
    
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaAnnoTime".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	date.setDateFmt("yyyy-MM-dd HH:mm");
	
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaAnnoType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择公告类型-","06"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		if("oaAnnoLevel".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_NEWS_ISTOP.getSelectAndText("-1,-请选择重要级-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	        bc.setColumnReplace("replaceLevel");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
	}
   }
   
   colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center"));
   
    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
