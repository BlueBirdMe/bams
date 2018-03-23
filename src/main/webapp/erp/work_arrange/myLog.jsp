<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的日志</title>
		<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrWorkArrangeService.js"></script>
		<%
			String date = request.getParameter("date");
		%>
		<script type="text/javascript">

//查询方法
function queryData(){
	startQuery();
	var worklog = getQueryParam();
	var pager = getPager();
	if (<%=date%> != null){
		document.getElementById("hrmtest1").value = '<%=date%>';
		document.getElementById("hrmtest2").value = '<%=date%>';
	}
	worklog.oaWorklogDate = getother();
	dwrWorkArrangeService.listOaWorkLog(worklog,pager,queryCallback);
}
	
function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}
	
function deleteObject(){
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","del()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
}
	
function del(){
	var recordsPks = getAllRecordArray();
	dwrWorkArrangeService.deleteWorkLogByPks(recordsPks,setcallback);
}
	
function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrWorkArrangeService.deleteWorkLogByPks(ids,setcallback);
}
	
function setcallback(data){
	alertmsg(data,"queryData()");
}
	
function update(pk){
	MoveDiv.show('日志编辑',"<%=contextPath%>/erp/work_arrange/myLog_add.jsp?worklogpk="+pk);
}
	
function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0' width='13' height='13'/></a>";
	return str;
}
	
//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/work_arrange/myLog_detail.jsp?worklogpk='+obj.value,'800','500');
	box.msgtitle="<b>我的日志明细查看</b>";
	box.show();
}
	
function getother(){
	var a = document.getElementById("hrmtest1").value+","+document.getElementById("hrmtest2").value;
	return a;
}

//列显示替换方法
function repleaRange(rowObj){
	var str="";
	if(rowObj.oaWorklogRange ==<%=EnumUtil.OA_WORKLOG_RANGE.one.value%>){
		str= "<font style='color:blue'><%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.one.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_WORKLOG_RANGE.valueOf(EnumUtil.OA_WORKLOG_RANGE.two.value)%></font>"
	}
	return str;
}

//附件数
function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaWorklogAnnexid!=null&& rowObj.oaWorklogAnnexid != undefined && rowObj.oaWorklogAnnexid != "undefined"&& rowObj.oaWorklogAnnexid.length>0){
		var cs = rowObj.oaWorklogAnnexid.split(",");
		count = cs.length;
	}
	return count;
}

function add(){
	var url = '<%=contextPath%>/erp/work_arrange/myLog_add.jsp?date=<%=date%>';
	openMDITab(url + "&tab="+getMDITab());
}
</script>
	</head>
	<body>
		<%
			String title = null;
			if (date != null) {
				title = date + "  日志列表";
			} else {
				title = "我的日志列表(默认显示所有日志)";
			}
			SysGrid grid = new SysGrid(request, title);
			grid.setShowImg(false);//不显示图片信息
			//放入按钮
			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("新增日志","add()","add.png"));
			btnList.add(new SysGridBtnBean("批量删除", "deleteObject()", "close.png"));
			grid.setBtnList(btnList);
			//放入列
			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			sccList.add(new SysColumnControl("oaWorklogTitle", "日志标题", 1, 1, 1, 15));
			sccList.add(new SysColumnControl("oaWorklogType", "日志类型", 2, 2, 1, 0));
			sccList.add(new SysColumnControl("library.libraryInfoName", "日志类型", 1, 2, 2, 0));
			sccList.add(new SysColumnControl("oaWorklogRange", "日志范围", 1, 2, 1, 0));
			sccList.add(new SysColumnControl("oaWorklogDate", "日志日期", 1, 2, 2, 0));
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

			//进行高级查询显示处理
			for (int i = 0; i < colList.size(); i++) {
				SysGridColumnBean bc = colList.get(i);
				if (bc.isShowAdvanced() || bc.isShowColumn()) {

					if ("oaWorklogType".equalsIgnoreCase(bc.getDataName())) {
						//设置高级查询显示样式

						SelectType select = new SelectType(UtilTool.getLibraryInfoList(this.getServletContext(), request, "-1,-请选择日志类型-", "04"));
						select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
						bc.setColumnTypeClass(select);
						//设置列显示样式
						bc.setColumnStyle("text-align:center;");
					}
					if ("oaWorklogRange".equalsIgnoreCase(bc.getDataName())) {
						//设置高级查询显示样式

						SelectType select = new SelectType(EnumUtil.OA_WORKLOG_RANGE.getSelectAndText("-1,-请选择日志范围-"));
						select.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
						bc.setColumnTypeClass(select);
						bc.setColumnReplace("repleaRange");
						//设置列显示样式
						bc.setColumnStyle("text-align:center;");
					}
				}
			}
			//放入自定义高级查询对象
			String firstdate = UtilWork.getFirstDateOfMonth();
			String enddate = UtilWork.getEndDateOfMonth();
			OtherType other = new OtherType("<input type ='text' class ='Wdate' readonly='readonly' id ='hrmtest1' onClick='WdatePicker()' value='" + firstdate
					+ "' />&nbsp;至&nbsp;<input type ='text' class ='Wdate' id ='hrmtest2' readonly='readonly' onClick='WdatePicker()' value='" + enddate + "' />");//自定义对象
			other.setGetValueMethod("getother()");
			colList.add(ColumnUtil.getCusterAdvancedColumn("oaWorklogDate", "日志日期", other));
			colList.add(ColumnUtil.getCusterShowColumn("filecount", "附件数", "getAcceCount", 0, "text-align:center"));
			grid.setColumnList(colList);

			//设置附加信息
			grid.setQueryFunction("queryData"); //查询的方法名
			grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			grid.setDblBundle("primaryKey"); //双击列的绑定的列值
			grid.setShowProcess(true);
			grid.setProcessMethodName("createMethod");
			//开始创建
			out.print(grid.createTable());
		%>
	</body>
</html>