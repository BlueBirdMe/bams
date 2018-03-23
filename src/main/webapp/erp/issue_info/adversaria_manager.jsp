<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<%@include file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrOaNewsService.js"></script>
		<title>记事管理</title>
		<script>
//查询方法
function queryData(){
	startQuery();
	var adversaria = getQueryParam();
	var pager = getPager();
	dwrOaNewsService.listAdversaria(adversaria,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/issue_info/adversaria_detail.jsp?did='+obj.value,'800','500');
		box.msgtitle="<b>查看记事记录明细</b>";
		box.show();
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的公司记事吗?","delRecord()");
	}else{
	   alertmsg("请选择要删除的公司记事！");
	}
}

function delRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaNewsService.deleteAdversariaById(recordsPks,deleCallBack);
}

function deleCallBack(data){
    alertmsg(data,"queryData()");
}

function del(id){
    confirmmsg("确定要删除公司记事吗?","delok("+id+")");
}

function delok(id){
    var ids = new Array();
    ids[0] = id;
    dwrOaNewsService.deleteAdversariaById(ids,deleCallBack);
}

function edit(pk){
    MoveDiv.show('编辑记事记录',"<%=contextPath%>/erp/issue_info/adversaria_add.jsp?adversariapk='"+pk+"'");
}

function createProcessMethod(rowObj){
	var str="";
	if(rowObj.oaAdverStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<a href='javascript:void(0)' title='失效' onclick=\"setAdverStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1.png' border='0'/></a>&nbsp;&nbsp;";
	}else{
		str="<a href='javascript:void(0)' title='生效' onclick=\"setAdverStatus('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/valid1_.png' border='0'/></a>&nbsp;&nbsp;";
	}
	
	str += "<a href='javascript:void(0)' title='编辑' onclick=\"edit("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowedit1.png' border='0'/></a>&nbsp;";
	str += "<a href='javascript:void(0)' title='删除' onclick=\"del("+rowObj.primaryKey+")\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"; 
	
	return str;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.oaAdverStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value%>){
		str= "<font style='color:green'><%=EnumUtil.OA_ISSUEINFO_STATUS
									.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>"
	}else{
		str= "<font style='color:red'><%=EnumUtil.OA_ISSUEINFO_STATUS
									.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>"
	}
	return str;
}

function replaceLevel(rowObj){
	var str="";
	if(rowObj.oaAdverLevel ==<%=EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.HIGH.value%>){
		str= "<image src='<%=request.getContextPath()%>/images/lve1.gif' border='0' title='重要' height='13' style='vertical-align: middle;'/>" ;
	}
	return str;
}

function checkDatestatus(){
    //针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要设置选中的记事吗?","setStatus()");
	}else{
	    alertmsg("请选择要设置的公司记事！");
	}
}

function setStatus(){
    var strName = "adversaria";
    var recordsPks = getAllRecordArray();
    dwrOaNewsService.setIssueInfo(recordsPks,strName,callback);
}

function setAdverStatus(pk){
    confirmmsg("确定要设置此公司记事状态吗?","setStatusok("+pk+")");
}

function setStatusok(pk){
    var strName = "adversaria";
    var ids= new Array();
    ids[0] = pk;
    dwrOaNewsService.setIssueInfo(ids,strName,callback);
}
function callback(data){
    alertmsg(data,"queryData()");
}

function getAcceCount(rowObj){
	var count =0 ;
	if(rowObj.oaAdverAcce!=null&&rowObj.oaAdverAcce != undefined&& rowObj.oaAdverAcce != "undefined"&&rowObj.oaAdverAcce.length>0){
		var cs = rowObj.oaAdverAcce.split(",");
		count = cs.length;
	}
	return count;
}
</script>
	</head>
	<body>
		<%
			SysGrid nw = new SysGrid(request);
			nw.setTableTitle("公司记事管理");
			nw.setIsautoQuery(true);
			nw.setShowImg(false);
			nw.setQueryFunction("queryData"); //查询的方法名
			nw.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			nw.setDblBundle("primaryKey"); //双击列的绑定的列值

			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("批量删除", "deleteObject()",
					"close1.png"));
			btnList.add(new SysGridBtnBean("生效/失效", "checkDatestatus()",
					"set1.png"));

			nw.setBtnList(btnList);

			//ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司记事列表"));

			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			sccList.add(new SysColumnControl("oaAdverLevel", "", 1, 2, 2, 0));
			sccList.add(new SysColumnControl("oaAdverTitle", "记事标题", 1, 1, 1,
					40));
			sccList.add(new SysColumnControl("employee.hrmEmployeeName", "发布人",
					1, 1, 1, 0));
			sccList
					.add(new SysColumnControl("oaAdverTime", "记事时间", 1, 2, 1, 0));
			sccList.add(new SysColumnControl("oaAdverStatus", "记事状态", 1, 2, 1,
					0));
			ArrayList<SysGridColumnBean> colList = UtilTool
					.getGridColumnList(sccList);

			//进行高级查询显示处理
			for (int i = 0; i < colList.size(); i++) {
				SysGridColumnBean bc = colList.get(i);
				if (bc.isShowAdvanced() || bc.isShowColumn()) {
					if ("oaAdverTime".equalsIgnoreCase(bc.getDataName())) {
						//高级查询显示
						DateType date = new DateType();
						date.setDateFmt("yyyy-MM-dd HH:mm");
						bc.setColumnTypeClass(date);
						//列样式
						bc.setColumnStyle("padding-left:15px;");
					}
					if ("oaAdverLevel".equalsIgnoreCase(bc.getDataName())) {
						//设置高级查询显示样式

						SelectType select = new SelectType(
								EnumUtil.OA_ADVERSARIA_LEVEL_TYPE
										.getSelectAndText("-1,—请选择记事重要级—"));
						select
								.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
						bc.setColumnTypeClass(select);

						bc.setColumnReplace("replaceLevel");

						//设置列显示样式
						bc.setColumnStyle("text-align:center;");
					}
					if ("oaAdverStatus".equalsIgnoreCase(bc.getDataName())) {
						//设置高级查询显示样式

						SelectType select = new SelectType(
								EnumUtil.OA_ISSUEINFO_STATUS
										.getSelectAndText("-1,-请选择事记状态-"));
						select
								.setCustomerFunction(new String[] { "onchange=\"queryData();\"" });
						bc.setColumnTypeClass(select);

						bc.setColumnReplace("replaceStatus");
						//设置列显示样式
						bc.setColumnStyle("text-align:center;");
					}
				}
			}

			colList.add(ColumnUtil.getCusterShowColumn("filecount", "附件数",
					"getAcceCount", 0, "text-align:center"));

			//设置列操作对象
			nw.setShowProcess(true);//默认为false 为true请设置processMethodName
			nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象   

			nw.setColumnList(colList);
			//开始创建
			out.print(nw.createTable());
		%>
	</body>
</html>
