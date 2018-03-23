<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书类别</title>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
    var booktype = getQueryParam();
    var pager = getPager();
    dwrOfficeResourcesService.listBooktypes(booktype,pager,queryCallback);
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

function add(){
	var url = '<%=contextPath%>/erp/office_resources/book_type_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function edit(id){
	MoveDiv.show('编辑图书类型','<%=contextPath%>/erp/office_resources/book_type_add.jsp?bookType='+id);
}

function deleteObject(){
    //针对多条记录进行操作
    if(getAllRecordArray() != false){
        confirmmsg("确定要删除选中的图书类型吗?","delRecord()");	
    }else{
        alertmsg("请选择要删除的图书类型！");
    }
}

function delRecord(){
    var ids = getAllRecordArray();
    dwrOfficeResourcesService.deleteBooktypesByPks(ids,delcallback);
}

function del(id){
	confirmmsg("确定要删除此图书类型信息吗?","delok("+id+")");
}

function delok(id){
    var ids = new Array();
    ids[0] = id;
	dwrOfficeResourcesService.deleteBooktypesByPks(ids,delcallback);
}

function delcallback(data){
	alertmsg(data,"queryData()");
}

//双击数据
function dblCallback(obj){
	//MoveDiv.show('明细查看','<%=contextPath%>/erp/office_resources/book_type_detail.jsp?bid='+obj.value);
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/office_resources/book_type_detail.jsp?bid='+obj.value,'700','450');
	box.msgtitle="<b>图书类别明细查看</b>";
	box.show();
}

function getBookCount(rowObj){
    var count =0 ;
    if(rowObj.bookCount!=null&&rowObj.bookCount != undefined && rowObj.bookCount != "undefined"&&rowObj.bookCount.length>0){
	    count = rowObj.bookCount;
    }
    return count;
   }
</script>
</head>
<body>
<%
	SysGrid grid = new SysGrid(request,"图书类别列表");
	grid.setShowImg(false);//不显示图片信息.

	ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增图书类型","add()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    
    grid.setBtnList(btnList);
	
	//放入列
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"图书类型"));
	grid.setColumnList(colList);
	
	grid.setShowProcess(true);
	grid.setProcessMethodName("createMethod");
	
	//设置附加信息
	grid.setQueryFunction("queryData");	//查询的方法名
	grid.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	grid.setDblBundle("primaryKey");	//双击列的绑定的列值
	
	colList.add(ColumnUtil.getCusterShowColumn("bookcount","图书数","getBookCount",0,"text-align:center"));
	//开始创建
	out.print(grid.createTable());
%>		
</body>
</html>