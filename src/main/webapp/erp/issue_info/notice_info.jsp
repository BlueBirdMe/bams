<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
<title>通知管理</title>
<%
     String useId = UtilTool.getEmployeeId(request);
     String use = "'"+useId+"'";
 %>
<script>
//查询方法
function queryData(){
	startQuery();
	var notice = getQueryParam();//getQueryParam()方法就是获取了前台的查询条件,把查询条件封装到了notice对象
	notice.oaNotiEmp = <%=use%>;
	var pager = getPager();
	dwrOaNewsService.listNotice(notice,pager,queryCallback);
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
	var objTemp = getObjectByPk(obj.value); 
	
	if(objTemp.oaNotiStatus == <%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
	    var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/notice_detail_manager.jsp?oid='+obj.value+'&noid=1','800','500');
		box.msgtitle="<b>查看通知记录明细</b>";
		var butarray = new Array();
		butarray[0] = "ok|sendSms('"+box.dialogId+"');|短信提醒";
		butarray[1] = "cancel";
		box.buttons = butarray;
		box.show();
	}else{
	    var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/notice_detail_manager.jsp?oid='+obj.value+'&noid=1','800','500');
		box.msgtitle="<b>查看通知记录明细</b>";
		box.show();
	}	
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的通知吗?","delRecord()");
	}else{
	   alertmsg("请你选择您要删除的通知！");
	}
}

function delRecord(){
   var recordsPks = getAllRecordArray();
   dwrOaNewsService.deleteNoticeById(recordsPks,deleback);
}

function deleback(data){
   alertmsg(data,"queryData()");
}

function del(id){
   confirmmsg("确定要删除此通知吗?","delok("+id+")");
}

function delok(id){
   var ids = new Array();
   ids[0] = id;
   dwrOaNewsService.deleteNoticeById(ids,deleback);
}

function checkDatestatus(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要设置选中的通知吗?","setStatus()");
	}else{
	   alertmsg("请你选择您要设置的通知！");
	}
}

function smsSet(pk){
	openMDITab("<%=contextPath%>/erp/issue_info/notice_sms_send.jsp?oid="+pk+"&noid=1");   
}

function edit(id){
	MoveDiv.show('编辑通知','<%=contextPath%>/erp/issue_info/notice_add.jsp?noticepk='+id+'&noid=1');
}

function  setStatus(){
   var recordsPks = getAllRecordArray();
   var strName = "notice";
   dwrOaNewsService.setIssueInfo(recordsPks,strName,callback);
}

function setNoticeStatus(pk){
    confirmmsg("确定要设置选中的通知吗?","setok("+pk+")");
}

function setok(id){
    var strName = "notice";
    var ids = new Array();
    ids[0]= id;
    dwrOaNewsService.setIssueInfo(ids,strName,callback);
}

function callback(data){
    alertmsg(data,"queryData()");
}

function createProcessMethod(rowObj){
	var str="";
	if(rowObj.oaNotiStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<a href='javascript:void(0)' title='失效' onclick=\"setNoticeStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0' /></a>&nbsp;&nbsp;"
	    str += "<a href='javascript:void(0)' title='有效状态,不能编辑' ><img src='<%=contextPath%>/images/grid_images/rowedit1_.png' border='0'/></a>&nbsp;&nbsp;";
	    str += "<a href='javascript:void(0)' title='短信提醒' onclick=\"smsSet("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/messages.png' border='0'/></a>&nbsp;&nbsp;";
	}else{
		str="<a href='javascript:void(0)' title='生效' onclick=\"setNoticeStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>&nbsp;&nbsp";
	    str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit1.png' border='0'/></a>&nbsp;&nbsp;";
	    str += "<a href='javascript:void(0)' title='无效状态,不能短信提醒' ><img src='<%=contextPath%>/images/grid_images/messages_.png' border='0'/></a>&nbsp;&nbsp;";
	}
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	
	return str;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.oaNotiStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<font style='color:green'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>"
	}
	return str;
}

function replaceType(rowObj){
	var str="";
	if(rowObj.oaNotiType == <%=EnumUtil.OA_NOTICE_TYPE.EMERGENCY.value%>){
		str= "<image src='<%=request.getContextPath()%>/images/jj.png' border='0' title='紧急'/>";
	}else if(rowObj.oaNotiType == <%=EnumUtil.OA_NOTICE_TYPE.GENERAL.value%>){
		str= "！";
	}else{
	    str= "";
	}
	return str;
}

function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaNotiAcce!=null&&rowObj.oaNotiAcce != undefined&& rowObj.oaNotiAcce != "undefined"&&rowObj.oaNotiAcce.length>0){
		var cs = rowObj.oaNotiAcce.split(",");
		count = cs.length;
	}
	return count;
}
</script>
</head>
<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("通知管理");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close1.png"));
    btnList.add(new SysGridBtnBean("生效/失效","checkDatestatus()","set1.png"));
    
    nw.setBtnList(btnList);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("oaNotiType","",1,2,2,0));
    sccList.add(new SysColumnControl("oaNotiName","通知标题",1,1,1,30));
    sccList.add(new SysColumnControl("oaNotiTime","发布时间",1,2,1,0));
    sccList.add(new SysColumnControl("oaNotiStatus","通知状态",1,2,1,0));
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
    
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if(bc.isShowAdvanced()||bc.isShowColumn()){
	if("oaNotiTime".equalsIgnoreCase(bc.getDataName())){
		//高级查询显示
		DateType date = new DateType();
		date.setDateFmt("yyyy-MM-dd HH:mm");
		bc.setColumnTypeClass(date);
		//列样式
		//bc.setColumnStyle("padding-left:15px;");
	}
	if("oaNotiType".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式
		
		SelectType select  = new SelectType(EnumUtil.OA_NOTICE_TYPE.getSelectAndText("-1,-请选择通知类型-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
	    bc.setColumnReplace("replaceType");
		//设置列显示样式
		bc.setColumnStyle("text-align:center;");
	}
	if("oaNotiStatus".equalsIgnoreCase(bc.getDataName())){
		//设置高级查询显示样式
		
		SelectType select  = new SelectType(EnumUtil.OA_ISSUEINFO_STATUS.getSelectAndText("-1,-请选择通知状态-"));
		select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
		bc.setColumnTypeClass(select);
		
		bc.setColumnReplace("replaceStatus");
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
