<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作地区管理</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrHrmEmployeeService.js"></script>
<script type="text/javascript">	
//查询方法
function queryData(){
	startQuery();
	var workarea = getQueryParam();
	var pager = getPager();
	dwrHrmEmployeeService.listWorkarea(workarea,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function createMethod(rowObj){
	var	str="<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	str+= "&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>"
	return str;
}

function edit(id){
   MoveDiv.show('编辑工作地区','<%=contextPath%>/erp/hrm/work_address_add.jsp?areaId='+id);
}
	
function deletebatch(){
    if(getAllRecordArray() != false){
		confirmmsg("确定要删除工作地区记录吗?","delbatchok()");
	}else{
		alertmsg("请选择要删除的工作地区记录！");
	}
}

function delbatchok(){
    var ids = getAllRecordArray();
    dwrHrmEmployeeService.deleteWorkareaBypk(ids,delcallback);
}

function del(id){
	confirmmsg("确定要删除工作地区记录吗?","delok("+id+")");
}

function delok(id){
    var ids = new Array();
    ids[0] = id; 
	dwrHrmEmployeeService.deleteWorkareaBypk(ids,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

//双击数据
function dblCallback(obj){
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/hrm/workAddress_detail.jsp?pid='+obj.value,'700','450');
	box.msgtitle="<b>工作地区明细查看</b>";
	box.show();
}
</script>
</head>
<body>
	<%
		SysGrid grid = new SysGrid(request,"工作地区列表");
			grid.setShowImg(false);//不显示图片信息.
			grid.setCheckboxOrNum(true);

			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			
		    sccList.add(new SysColumnControl("hrmAreaName","地区名称",1,1,2,0));
		    sccList.add(new SysColumnControl("hrmAreaEngname","英文名称",1,1,2,0));
		    sccList.add(new SysColumnControl("hrmAreaDesc","备注",1,2,2,20));
		    
		    //放入按钮
			ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>();
			btnList.add(new SysGridBtnBean("批量删除", "deletebatch()", "close.png"));
			grid.setBtnList(btnList);
		    
		    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
			grid.setColumnList(colList);
			
			grid.setShowProcess(true);
			grid.setProcessMethodName("createMethod");
			
			//设置附加信息
			grid.setQueryFunction("queryData");	//查询的方法名
			grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
			grid.setDblBundle("primaryKey");	//双击列的绑定的列值
			
			//开始创建
			out.print(grid.createTable());
	%>		
</body>
</html>