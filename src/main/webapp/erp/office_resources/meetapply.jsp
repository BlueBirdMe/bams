<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<title>java生成grid</title>
<script type="text/javascript">


//查询方法
function queryData(){
	startQuery();
	var meetapply = getQueryParam();
	var type = document.getElementById("library.libraryInfoName").value;
	if(type != null){
	    meetapply.oaMeetapplyType = type;
	}
	var pager = getPager();
	dwrOfficeResourcesService.listMeetapplys(meetapply,pager,queryCallback);
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
	/**
		var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/meetapply_detail.jsp?nid='+obj.value,'750','550');
		box.msgtitle="<b>会议申请明细列表</b>";
		box.show();
	*/
	Sys.load('<%=contextPath%>/erp/office_resources/meetSummary_detail.jsp?cid='+obj.value,'approvefrm');
}


function repleaStatus(rowObj){
    str = "";
    if(rowObj.oaMeetapplyStatus == <%=EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value%>){
         str= "<font color='red'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value)%></font>";
    }else if(rowObj.oaMeetapplyStatus == <%=EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value%>){
         str= "<font color='green'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value)%></font>";
    }else{
         str= "<font color='blue'><%=EnumUtil.OA_MEETAPPLY_STATUS.valueOf(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value)%></font>";
    }
    return str;
}

function getsummaryCount(rowObj){
    var count = 0;
    if(rowObj.summaryCount != null&&rowObj.summaryCount != undefined&& rowObj.summaryCount != "undefined"&&rowObj.summaryCount.length>0){
		count = rowObj.summaryCount;
	}
	return count;
}

function getAffixCount(rowObj){
	var count =0 ;
	if(rowObj.oaMeetapplyAffix!=null&&rowObj.oaMeetapplyAffix != undefined&& rowObj.oaMeetapplyAffix != "undefined"&&rowObj.oaMeetapplyAffix.length>0){
		var cs = rowObj.oaMeetapplyAffix.split(",");
		count = cs.length;
	}
	return count;
}


</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);

bg.setTableTitle("会议管理");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息

//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值
bg.setCheckboxOrNum(false);

//放入列
    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
    sccList.add(new SysColumnControl("oaMeetapplyName","会议名称",1,1,1,40));
    sccList.add(new SysColumnControl("library.libraryInfoName","会议类型",1,2,1,0));
    sccList.add(new SysColumnControl("employee.hrmEmployeeName","申请人",1,1,1,0));
    sccList.add(new SysColumnControl("oaMeetapplyStar","开始时间",1,2,1,0));
    sccList.add(new SysColumnControl("oaMeetapplyEnd","结束时间",1,2,1,0));  
    sccList.add(new SysColumnControl("oaMeetapplyStatus","会议状态",1,2,1,0));


ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
colList.add(ColumnUtil.getCusterShowColumn("summaryCount","纪要数","getsummaryCount",0,"text-align:center")); 
colList.add(ColumnUtil.getCusterShowColumn("affixCount","附件数","getAffixCount",0,"text-align:center"));

//进行高级查询显示处理
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaMeetapplyStar".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
		if("oaMeetapplyEnd".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
		if("library.libraryInfoName".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(),request,"-1,-请选择会议类型-","12"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
		
		if("oaMeetapplyStatus".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	
	SelectType select  = new SelectType(EnumUtil.OA_MEETAPPLY_STATUS.getSelectAndText("-1,-请选择会议状态-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	
	bc.setColumnReplace("repleaStatus");
	
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
	}
}

bg.setColumnList(colList);




//开始创建
out.print(bg.createTable());
%>
</body>
</html>