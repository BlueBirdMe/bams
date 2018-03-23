<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaCommunicationService.js"></script>
		<title>版块管理</title>
		<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var forums = getQueryParam();
	var pager = getPager();
	dwrOaCommunicationService.listForumsByPager(forums,pager,queryCallback);

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
	var box = new Sys.msgbox('明细查看','<%=contextPath%>/erp/communication/forum_detail.jsp?forumId='+obj.value,'800','500');
	box.msgtitle="<b>查看版块明细</b>";
	box.show();
}

function edit(id){
   MoveDiv.show('编辑版块','<%=contextPath%>/erp/communication/forum_add.jsp?forumId='+id+'');
}

function deleteObject(){
	//针对多条记录进行操作
	if(getAllRecordArray() != false){
		confirmmsg("确定要删除选中的论坛版块吗?","delRecord()");
	}else{
	    alertmsg("请选择删除的论坛版块!");
	}
}
function delRecord(){
    var recordsPks = getAllRecordArray();
    dwrOaCommunicationService.deleteForumsById(recordsPks,delCallback);
}
function del(pk){
    confirmmsg("确定要删除选此论坛版块吗?","delok("+pk+")");
}
function delok(id){
    var ids = new Array();
    ids[0] = id;
    dwrOaCommunicationService.deleteForumsById(ids,delCallback);
}
function delCallback(data){
    alertmsg(data,"queryData()");
}

function add(){
    var url = '<%=contextPath%>/erp/communication/forum_add.jsp';
	openMDITab(url + "?tab="+getMDITab());
}

function createProcessMethod(rowObj){
    var str="";
    str= "<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>&nbsp;&nbsp;"
    str += "<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
    return  str;
}

function getPostCount(rowObj){
   var count = 0; 
   if(rowObj.oaPostsCount != null && rowObj.oaPostsCount != undefined){
        count = rowObj.oaPostsCount;
   }
   return count;
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("版块管理");
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    nw.setShowImg(true);
    
    ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
    btnList.add(new SysGridBtnBean("新增版块","add()","add.png"));
    btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
    
    nw.setBtnList(btnList);
    
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"论坛版块列表"));
    
    colList.add(ColumnUtil.getCusterShowColumn("postcount", "帖子数", "getPostCount", 0, "text-align:center"));
    //进行高级查询显示处理
   for(int i=0;i<colList.size();i++){
	SysGridColumnBean bc =colList.get(i);
	if(bc.isShowAdvanced()||bc.isShowColumn()){
		if("oaForumTime".equalsIgnoreCase(bc.getDataName())){
	//高级查询显示
	DateType date = new DateType();
	//date.setDefaultDate(UtilWork.getToday());
	bc.setColumnTypeClass(date);
	//列样式
	bc.setColumnStyle("padding-left:15px;");
		}
	  }
	}
    
    //设置列操作对象
	nw.setShowProcess(true);//默认为false 为true请设置processMethodName
	nw.setProcessMethodName("createProcessMethod");//生成该操作图标的js方法,系统默认放入数据行对象
   
    nw.setColumnList(colList);
   
   //设置图片显示信息
   nw.setImgShowUrl("oaForumImage");//显示img的属性字段，没有填写-1
   nw.setImgShowText("oaForumName");
   nw.setImgNoDefaultPath(absPath+"/images/noimages/other.png");//可以不指定，系统采用默认暂无图片
   //nw.setImgShowCode("_Min");//如果需要显示的图片为缩略图请使用
   nw.setImgdivwidth("300");//显示详细信息的div大小，默认280;
   nw.setImgheight("80");//不设置为自动
   nw.setImgShowTextLen(10);//显示文本的最大长度,不设置为8个字符

   nw.setShowImageDetail(true);//是否显示图片详细
    
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
