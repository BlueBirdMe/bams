<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统运行日志管理</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
    startQuery();
    var sysLog = getQueryParam();//java实体类相对应
    var pager = getPager();
    dwrSysProcessService.listSysLogRuntime(sysLog,pager,queryCallback);
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
    var box = new Sys.msgbox("明细查看","<%=contextPath%>/erp/system_manger/sys_log_detail.jsp?pk="+obj.value);
    box.show();
}


function delbatch(){
    if(getAllRecordArray() != false){
        confirmmsg("确定要删除系统运行日志吗?","delbatchok()");
    }else{
        alertmsg("请选择要删除的系统运行日志...");
    }
}
function delbatchok(){
    var pks = getAllRecordArray();
    dwrSysProcessService.deleteSysLogRuntimeByPks(pks,delCallback);
}
function delCallback(data){
    alertmsg(data,"queryData()");
}
</script>
</head>
<body>
<%
SysGrid grid = new SysGrid(request,"系统运行日志列表");
//放入按钮
ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
btnList.add(new SysGridBtnBean("批量删除", "delbatch()", "close.png"));
grid.setBtnList(btnList);
//放入操作提示，请在系统管理-帮助管理处添加
grid.setHelpList(UtilTool.getGridTitleList(this.getServletContext(), request));
ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
sccList.add(new SysColumnControl("primaryKey","ID",1,2,2,0));
sccList.add(new SysColumnControl("logLevel","日志级别",1,1,1,0));
sccList.add(new SysColumnControl("msg","日志信息",1,2,2,100));
//sccList.add(new SysColumnControl("className","类名",1,2,2,0));
//sccList.add(new SysColumnControl("methodName","方法名",1,2,2,0));
sccList.add(new SysColumnControl("createTime","生成时间",1,2,1,0));

ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
for(int i = 0; i < colList.size(); i++){
    SysGridColumnBean bc = colList.get(i);
    if ("createTime".equalsIgnoreCase(bc.getDataName())){
    	DateType date = new DateType();
		bc.setColumnTypeClass(date);
        bc.setColumnStyle("text-align:center");
    }
}
grid.setColumnList(colList);
//设置附加信息
grid.setShowImg(false);
grid.setQueryFunction("queryData");//查询的方法名
grid.setDblFunction("dblCallback");//双击列的方法名，有返回值，为列对象
grid.setDblBundle("primaryKey");//双击列的绑定的列值
grid.setShowProcess(false);//默认为false 为true请设置processMethodName
out.print(grid.createTable());
%>
</body>
</html>
