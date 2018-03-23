<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@ include file="../editmsgbox.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalOfficeService.js"></script>
<title>有效提醒</title>
<script>
//查询方法
function queryData(){
	startQuery();
	var timed = getQueryParam();
	var pager = getPager();
	dwrPersonalOfficeService.getTimedValidByCompanyAndEmpId(timed,pager,queryCallback);
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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/personal_work/timed_detail.jsp?timedID='+obj.value,'700','450');
	box.msgtitle="<b>查看定时提醒明细</b><br/>";
	box.show();
}


function del(){
    if(getAllRecordArray() != false){
		confirmmsg("确定要删除记录吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的记录...");
	}
	
}

function delbatchok(){
	var recordsPks = getAllRecordArray();
    dwrPersonalOfficeService.deleteTimedRecordByPks(recordsPks,setcallback);
}

function setcallback(data){
	alertmsg(data,"queryData()");
}

function add(){
	var url = '<%=contextPath %>/erp/personal_work/timed_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function update(id){
	MoveDiv.show('修改','<%=contextPath %>/erp/personal_work/timed_add.jsp?timedID='+id);
}

function deleteone(id){
	confirmmsg("确定要删除记录吗?","delok("+id+")");
}
	
function delok(id){
	var ids = new Array();
	ids[0] = id;
	dwrPersonalOfficeService.deleteTimedRecordByPks(ids,setcallback);
}

function createMethod(rowObj){
	var	str="";
	    str="<a href='javascript:void(0)' title='编辑' onclick=\"update('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	    str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"deleteone('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}

function replaceStatus(rowObj){
	var str="";
	if(rowObj.timedType ==<%=EnumUtil.TIMED_TYPE.Vaild.value%>){
		str= "<font style='color:blue'><%=EnumUtil.TIMED_TYPE.valueOf(EnumUtil.TIMED_TYPE.Vaild.value)%></font>"
	}else{
		str= "<font style='color:green'><%=EnumUtil.TIMED_TYPE.valueOf(EnumUtil.TIMED_TYPE.No_Vaild.value)%></font>"
	}
	return str;
}
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
bg.setTableTitle("有效提醒");
bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
//设置附加信息
bg.setQueryFunction("queryData");	//查询的方法名
bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
bg.setDblBundle("primaryKey");		//双击列的绑定的列值

//放入按钮
ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("新增提醒","add()","add.png"));
btnList.add(new SysGridBtnBean("批量删除","del()","close.png"));
bg.setBtnList(btnList);

//放入列
ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"定时提醒列表"));

  // ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
  //  sccList.add(new SysColumnControl("timedType","提醒类型",1,2,2,0));
  //  sccList.add(new SysColumnControl("timedDate","提醒时间",1,2,2,0));
  // sccList.add(new SysColumnControl("timedDescription","提醒内容",1,2,2,30));
    
   // ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 

    
for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("timedDate".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("text-align:center;");
		}
		if("timedType".equalsIgnoreCase(bc.getDataName())){
	//设置高级查询显示样式
	SelectType select  = new SelectType(EnumUtil.TIMED_TYPE.getSelectAndText("-1,-请选择要查询的提醒类型-"));
	select.setCustomerFunction(new String[]{"onchange=\"queryData();\""});
	bc.setColumnTypeClass(select);
	bc.setColumnReplace("replaceStatus");
	//设置列显示样式
	bc.setColumnStyle("text-align:center;");
		}
		
	}
   }
    
bg.setColumnList(colList);
bg.setShowProcess(true);
bg.setProcessMethodName("createMethod");

//开始创建
out.print(bg.createTable());
%>
</body>
</html>