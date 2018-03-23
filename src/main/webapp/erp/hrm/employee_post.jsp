<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员岗位设置</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var employee = getQueryParam();
	employee.hrmEmployeeDepidTree = document.getElementById("upcode").value;
	var pcode = document.getElementById("postcode").value;
	employee.hrmEmployeePostId = pcode;
	if(document.getElementById("partpostchk").checked&&pcode.length>0){
		employee.hrmPartPost = pcode+",";
	}
	var pager = getPager();
	dwrHrmEmployeeService.listEmployees_post(employee,pager,queryCallback);
}

function customerfun(){
	var pcode = document.getElementById("postcode").value;
	if(pcode.length>0){
		queryData();
	}
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/employee_detail.jsp?employeepk='+obj.value,'800','500');
		box.msgtitle="<b>查看人员信息明细</b>";
		box.show();
}

function delok(id){
	var recordsPks = id;
	tempUrlParameter.setTmpvalue(recordsPks);
	openMDITab("<%=contextPath%>/erp/hrm/employee_post_set.jsp?employeepks="+recordsPks+"&tab="+getMDITab());
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

function setbatch(){
	if(getAllRecordArray() != false){
		confirmmsg("批量设置将不能加载人员现有岗位，确定吗?","delbatchok()");
	}else{
		alertmsg("请选择要设置的人员...");
	}	
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
	tempUrlParameter.setTmpvalue(recordsPks);
	openMDITab("<%=contextPath%>/erp/hrm/employee_post_set.jsp?employeepks="+recordsPks+"&tab="+getMDITab());
}

function createProcessMethod(rowObj){
	var str="";
    str="<a href='javascript:void(0)' title='设置岗位' onclick=\"delok('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowset.png' border='0'/></a>";
	return str;
}

//部门树选择
function treeclick(code){
	document.getElementById("upcode").value =code;
	queryData();
}
</script>
</head>
<body>
<input type="hidden" id="postcode">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="split_l">
	<div class="div_title">选择部门</div>
	<div class="div_content_top">
		<jsp:include page="departmentxmlshow.jsp" flush="false">
			<jsp:param name="ischeck" value="true" />
		</jsp:include>
	</div>
	<div class="div_title">选择岗位</div>
	<div class="div_content_bottom" style="height:300px;">
		<iframe frameborder="0" src="<%=request.getContextPath() %>/erp/hrm/postset_tree.jsp" height="100%" scrolling="no" marginheight="0" width="100%"></iframe>
	</div>
</td>
<td>
<%
	SysGrid bg = new SysGrid(request);
	bg.setTableTitle("部门人员");
	
	//设置附加信息
	bg.setQueryFunction("queryData"); //查询的方法名
	bg.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey"); //双击列的绑定的列值
	
	//放入按钮
	ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
	btnList.add(new SysGridBtnBean("岗位设置", "setbatch()",
	"sport_tennis.png"));
	bg.setBtnList(btnList);
	
	//放入列
	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("hrmEmployeeName", "姓名", 1, 1, 1, 0));
	sccList.add(new SysColumnControl("hrmEmployeeCode", "工号", 1, 1, 1, 0));
	sccList.add(new SysColumnControl("hrmDepartment.hrmDepName", "部门", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("hrmEmployeePost.hrmPostName", "岗位", 1, 2, 2, 0));
	sccList.add(new SysColumnControl("hrmPartPostName", "兼职岗位", 1, 2, 2, 0));

	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);

	//进行高级查询显示处理
	for (int i = 0; i < colList.size(); i++) {
		SysGridColumnBean bc = colList.get(i);
		if (bc.isShowAdvanced() || bc.isShowColumn()) {
			if ("hrmEmployeeCode".equalsIgnoreCase(bc.getDataName())) {
				//列样式
				bc.setColumnStyle("padding-left:15px;text-align: left");
			}
		}
	}
	CheckBoxType cb = new CheckBoxType("显示兼职岗位为选择岗位");
	cb.setCustomerFunction(new String[] { "onclick='customerfun()'" });
	colList.add(ColumnUtil.getCusterAdvancedColumn("partpostchk", "", cb));
	bg.setColumnList(colList);
	//设置列操作对象
	bg.setShowProcess(true);//默认为false 为true请设置processMethodName
	bg.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象

	//设置图片显示信息
	//bg.setImgShowNum(6);//不指定默认5个
	bg.setImgShowUrl("hrmEmployeeImageInfoId");//显示img的属性字段，没有填写-1
	bg.setImgShowText("hrmEmployeeName");
	bg.setImgNoDefaultPath(absPath + "/images/noimages/employee.png");//可以不指定，系统采用默认暂无图片
	bg.setImgheight("64");//不设置为自动
	//开始创建
	out.print(bg.createTable());
%>
</td>
</tr>
</table>
</body>
</html>