<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript"
			src="<%=contextPath%>/dwr/interface/dwrOfficeResourcesService.js"></script>
		<title>会议室拾取</title>
		<%
			String textid = request.getParameter("textid");
			String valueid = request.getParameter("valueid");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String cudate = sf.format(new Date());
		%>
		<script type="text/javascript">
//查询方法
function queryData(){
	startQuery();
	var pager = getPager();
	var sdate = document.getElementById("oaMeetapplyStar").value;
	var edate = document.getElementById("oaMeetapplyEnd").value;
	dwrOfficeResourcesService.listBoadRoomsStatusByDate(sdate,edate,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alertmsg(data);
	}
	endQuery();
}

function roomclick(myfrmname){
     
     	var win = Sys.getfrm();//获取index页面iframe window对象	
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
     
     	var textid=win.document.getElementById("<%=textid%>"); 
     	var valueid = win.document.getElementById("<%=valueid%>");
     	//时间
     	var startdateobj = win.document.getElementById("oaMeetapplyStar");
     	var enddateobj = win.document.getElementById("oaMeetapplyEnd");
     	
		var sdate = document.getElementById("oaMeetapplyStar").value;
		var edate = document.getElementById("oaMeetapplyEnd").value;
		if(sdate.length==0||edate.length==0){
			alertmsg("请选择会议开始和结束时间...");
			return;
		}
		if(sdate >= edate){
     		alertmsg("结束时间应晚于开始时间!");
     		return;
     	}
     	
       if(getOneRecordArray() != false){
			var obj=getObjectByPk(getOneRecordArray());
			
			if(obj.roomStatus == <%=EnumUtil.OA_ROOM_STATUS.Use.value%>){
				confirmmsg("确定要选择被占用的会议室吗?","clickok()");
			}else{
				textid.value = obj.oaBoardroomName;
				valueid.value = obj.primaryKey;
				startdateobj.value = sdate;
				enddateobj.value = edate;
			}
	   }else{
			alertmsg("请选择相应数据记录...");
			return;
	   }
}

function clickok(){
	var win = Sys.getfrm();
	var textid=win.document.getElementById("<%=textid%>"); 
    var valueid = win.document.getElementById("<%=valueid%>");
    var obj=getObjectByPk(getOneRecordArray());
    textid.value = obj.oaBoardroomName;
	valueid.value = obj.primaryKey;
	
	var startdateobj = win.document.getElementById("oaMeetapplyStar");
    var enddateobj = win.document.getElementById("oaMeetapplyEnd");
    	
	startdateobj.value = document.getElementById("oaMeetapplyStar").value;
	enddateobj.value = document.getElementById("oaMeetapplyEnd").value;
	
}

function roomclickcustomer(dialogId,myfrmname,method){
	roomclick(myfrmname);
	if(method!=null&&method != "undefined" && method != undefined){
		var win = Sys.getfrm();//获取index页面iframe window对象	
	    if(myfrmname!=null&&myfrmname != "undefined" && myfrmname != undefined){
	    	win = win.document.getElementById(myfrmname).contentWindow;	
	    }
		eval("win."+method);
	}
	Sys.close(dialogId);
}
function dblCallback(obj){}

function repstatus(rowObj){
	var str="";
	if(rowObj.roomStatus == <%=EnumUtil.OA_ROOM_STATUS.Use.value%>){
		str ="<font color='red'><%=EnumUtil.OA_ROOM_STATUS.valueOf(EnumUtil.OA_ROOM_STATUS.Use.value)%></font>";
	}else{
		str ="<font color='green'><%=EnumUtil.OA_ROOM_STATUS.valueOf(EnumUtil.OA_ROOM_STATUS.Free.value)%></font>";
	}
	return str;
}

function changestatr(obj){
	document.getElementById("oaMeetapplyEnd").value = obj.value;
	queryData();
}
</script>
	</head>
	<body>
		<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		<td valign="top" style="HEIGHT:30px">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" style="line-height: 30px">
				<tr>
				<td style="padding-left: 10px;width: 10%;">开始时间</td>
				<td align="left">
				<input type="text" id="oaMeetapplyStar" readonly="readonly" class="Wdate" onchange="changestatr(this)" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false})" value="<%=cudate %>">
				</td>
				<td style="padding-left: 10px;width: 10%;">结束时间</td>
				<td align="left">
				<input type="text" id="oaMeetapplyEnd" readonly="readonly" class="Wdate" onchange="queryData()" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false,minDate:'#F{$dp.$D(\'oaMeetapplyStar\')}'})"  value="<%=cudate %>">
				</td>
				</tr>
			</table>
		</td>
		</tr>
		<tr>
		<td valign="top" style="HEIGHT: 100%">
		<%
			SysGrid grid = new SysGrid(request, "会议室列表");
			grid.setShowImg(false);//不显示图片信息.
			grid.setCheckboxOrNum(false);
			grid.setIsshowProcessTool(false);
			grid.setIsshowSimpleTool(false);
			//放入列
			//ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(UtilTool.getColumnShow(this.getServletContext(),"公司资源-类型"));
			ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			sccList.add(new SysColumnControl("oaBoardroomName", "会议室名称", 1, 2,2, 20));
			sccList.add(new SysColumnControl("oaBoardroomCapacity", "可容纳人数", 1,2, 2, 0));
			ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
			colList.add(ColumnUtil.getCusterShowColumn("roomStatus","状态","repstatus",0,"text-align:center"));
			grid.setColumnList(colList);
			

			//设置附加信息
			grid.setQueryFunction("queryData"); //查询的方法名
			grid.setDblFunction("dblCallback"); //双击列的方法名，又返回值，为列对象
			grid.setDblBundle("primaryKey"); //双击列的绑定的列值

			//开始创建
			out.print(grid.createTable());
		%>
		</td>
		</tr>
		</table>
	</body>
</html>