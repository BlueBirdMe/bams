<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="../common.jsp" %>
  <%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>
		<title>公司通知</title>
		<script>
//查询方法
function queryData(){
	startQuery();
	var notice = getQueryParam();//getQueryParam()方法就是获取了前台的查询条件,把查询条件封装到了notice对象
	var pager = getPager();
	dwrOaNewsService.listNoticeView(notice,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/notice_detail.jsp?oid='+obj.value,'800','500');
	box.msgtitle="<b>查看通知记录明细</b>";
	box.show();
}

function replaceType(rowObj){
	var src = "";
    if(rowObj.oaNotiType == <%=EnumUtil.OA_NOTICE_TYPE.EMERGENCY.value%>){
         src= "<image src='<%=request.getContextPath()%>/images/jj.png' border='0' title='紧急' height='13' style='vertical-align: middle;'/>" ;
	}
    return src;
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
    nw.setTableTitle("通知查看");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    nw.setCheckboxOrNum(false);
    
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("oaNotiType","",1,2,2,0));
    sccList.add(new SysColumnControl("oaNotiName","通知标题",1,1,1,30));
    sccList.add(new SysColumnControl("employee.hrmEmployeeName","发布人",1,1,1,0));
    sccList.add(new SysColumnControl("oaNotiTime","发布时间",1,2,1,0));
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
		bc.setColumnStyle("padding-left:15px;");
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
		}
	}
   colList.add(ColumnUtil.getCusterShowColumn("filecount","附件数","getAcceCount",0,"text-align:center")); 
   nw.setColumnList(colList);
   //开始创建
   out.print(nw.createTable());
%>
	</body>
</html>
