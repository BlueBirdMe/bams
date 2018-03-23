<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script>
		<title>系统配置</title>
		<script>
function queryData(){
	startQuery();
	var sysCnfig = getQueryParam();

	var pager = getPager();
	dwrSysProcessService.listSysConfig(sysCnfig,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function info(obj){
}

function reppName(rowObj){
	var str ="<input type='text' value='"+rowObj.projectName+"' style='height:20px;' maxlength='50' size='35' name='pname'/>";
	str+="<input type='hidden' value='"+rowObj.primaryKey+"' name='priKey'/><input type='hidden' value='"+rowObj.methodId+"' name='mid'/>";
	return str;
}

function repview(rowObj){
	var str ="<input type='text' value='"+rowObj.projectView+"' style='height:20px;' maxlength='100' size='50' name='pview'/>";
	return str;
}

function repenName(rowObj){
	var str ="<input type='text' value='"+rowObj.projectEgName+"' style='height:20px;' maxlength='100' size='50' name='penname'/>";
	return str;
}

function saveconfing(){
	var sysarray = new Array();
	var pks = document.getElementsByName("priKey");
	var pnames = document.getElementsByName("pname"); 
	var pennames = document.getElementsByName("penname");
	var pviews = document.getElementsByName("pview");
	var mids = document.getElementsByName("mid");
	
	for(var i=0;i<pks.length;i++){
		var sc = new Object();
		sc.primaryKey = pks[i].value;    

	    sc.projectName=pnames[i].value;
		sc.projectEgName=pennames[i].value;
		sc.projectView=pviews[i].value;
		sc.methodId=mids[i].value;
		sysarray[i] = sc;
	
		 
	}
	
	dwrSysProcessService.addSysConfig(sysarray,addback);
	
}

function addback(data){
  alertmsg(data);
}
</script>
	</head>
	<body>
<%
	SysGrid nw =new SysGrid(request);
    nw.setTableTitle("系统配置");
    nw.setIsautoQuery(true);
    nw.setShowImg(false);
    nw.setQueryFunction("queryData");	//查询的方法名
    nw.setDblFunction("info");	//双击列的方法名，又返回值，为列对象
    nw.setDblBundle("primaryKey");		//双击列的绑定的列值
    
    ArrayList<SysGridBtnBean> btnlist = new ArrayList<SysGridBtnBean>();
    btnlist.add(new SysGridBtnBean("保存","saveconfing()","add.png"));
    nw.setBtnList(btnlist);
    
    nw.setCheckboxOrNum(false);   
    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"系统配置"));
  	for(int i=0;i<colList.size();i++){
  		SysGridColumnBean cb = colList.get(i);
  		String colname = cb.getDataName();
  		if(colname.equalsIgnoreCase("projectName")){
  			cb.setColumnReplace("reppName");
  			cb.setColumnStyle("margin: 0;padding: 0;text-align:center");
  		}
  		if(colname.equalsIgnoreCase("projectEgName")){
  			cb.setColumnReplace("repenName");
  			cb.setColumnStyle("margin: 0;padding: 0;text-align:center");
  		}
  		if(colname.equalsIgnoreCase("projectView")){
  		cb.setColumnReplace("repview");
  			cb.setColumnStyle("margin: 0;padding: 0;text-align:center");
  		}
  	}

    nw.setColumnList(colList);
    
    //开始创建
    out.print(nw.createTable());
%>
	</body>
</html>
