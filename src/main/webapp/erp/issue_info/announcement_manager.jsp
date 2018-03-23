<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<title>公告管理</title>
<%
     String useId = UtilTool.getEmployeeId(request);
     String use = "'"+useId+"'";
 %>
<script>
function queryData(){

	startQuery();
	var announcement = getQueryParam();
	announcement.oaAnnoEmp = <%=use%>;
	var pager = getPager();
	dwrOaNewsService.listAnnouncement(announcement,pager,queryCallback);
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
		box.msgtitle="<b>查看公告明细</b>";
		box.show();
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
	   confirmmsg("确定要删除选中的公告吗?","deleAnno()");
	}else{
	   alertmsg("请选择要删除的公告！");
	}
}

function deleAnno(){
    var recordsPks = getAllRecordArray();
    dwrOaNewsService.deleteAnnouncementById(recordsPks,deleback);
}

function deleback(data){
   alertmsg(data,"queryData()");
}

function edit(id){
	MoveDiv.show('编辑公告','<%=contextPath%>/erp/issue_info/announcement_add.jsp?announcementpk='+id+'&noid=1');
}

function del(pk){
    confirmmsg("确定要删除公告吗?","delok("+pk+")");
}

function delok(pk){
    var ids = new Array();
    ids[0] = pk;
    dwrOaNewsService.deleteAnnouncementById(ids,deleback);
}

function createProcessMethod(rowObj){
	var str="";
	if(rowObj.oaAnnoStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<a href='javascript:void(0)' title='失效' onclick=\"setAnnoStatus("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0' /></a>&nbsp;&nbsp;"
	}else{
		str="<a href='javascript:void(0)' title='生效' onclick=\"setAnnoStatus("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>&nbsp;&nbsp;";
	}
	if(rowObj.oaAnnoStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
	    str += "<a href='javascript:void(0)' title='有效状态，不能编辑'><img src='<%=contextPath%>/images/grid_images/rowedit1_.png' border='0'/></a>&nbsp;";
	}else{
	    str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit1.png' border='0'/></a>&nbsp;";
	}
	
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	
	str += "&nbsp;&nbsp;<a href='javascript:void(0)' title='生成word文档' onclick=\"exportWord('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/doc.gif' border='0'/></a>";
	return str;
}

function exportWord(id){
	location.href= "<%=contextPath %>/exportAnnounce.do?id="+id;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.oaAnnoStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<font style='color:green'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>"
	}
	return str;
}

function replaceLevel(rowObj){
    var str="";
	if(rowObj.oaAnnoLevel ==<%=EnumUtil.OA_NEWS_ISTOP.YES.value%>){
	   str= "<image src='<%=request.getContextPath()%>/images/lve1.gif' border='0' title='重要'/>";
	}else{
		str= "";
	}
	return str;
}

function checkStatus(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要设置选中的公告吗?","setStatus()");
	}else{
	   alertmsg("请你选择要设置的公告！");
	}
}

function setStatus(){
    var recordsPks = getAllRecordArray();
    var strName = "announcement";
    dwrOaNewsService.setIssueInfo(recordsPks,strName,callback);
}

function setAnnoStatus(pk){
    confirmmsg("确定要设置此公告状态吗?","setStatusok("+pk+")");
}

function setStatusok(pk){
    var ids = new Array();
	ids[0] = pk;
    var strName = "announcement";
    dwrOaNewsService.setIssueInfo(ids,strName,callback);
}

function callback(data){
    alertmsg(data,"queryData()");
}

function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaAnnoAcce!=null&&rowObj.oaAnnoAcce != undefined&& rowObj.oaAnnoAcce != "undefined"&&rowObj.oaAnnoAcce.length>0){
		var cs = rowObj.oaAnnoAcce.split(",");
		count = cs.length;
	}
	return count;
}
</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("公告管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close1.png"));
    btnList.add(new SysGridBtnBean("生效/失效","checkStatus()","set1.png"));
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("oaAnnoLevel","",1,2,2,0));
    sccList.add(new SysColumnControl("oaAnnoLib.libraryInfoName","公告类型",1,2,2,0));
    sccList.add(new SysColumnControl("oaAnnoName","公告标题",1,1,1,40));
    sccList.add(new SysColumnControl("oaAnnoType","公告类型",2,2,1,0));
    sccList.add(new SysColumnControl("oaAnnoTime","发布时间",1,2,1,0));
    sccList.add(new SysColumnControl("oaAnnoStatus","公告状态",1,2,1,0));
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
		//bc.setColumnStyle("padding-left:15px;");
	}
	if("oaAnnoType".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式
		
		SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择公告类型-","06"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if("oaAnnoStatus".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式
		
		SelectType select  = new SelectType(EnumUtil.OA_ISSUEINFO_STATUS.getSelectAndText("-1,-请选择公告状态-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
		        bc.setColumnReplace("replaceStatus");
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
   
    //设置列操作对象
    nw.setShowProcess(true);//默认为false 为true请设置processMethodName
    nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象    
   
    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
</body>
</html>
