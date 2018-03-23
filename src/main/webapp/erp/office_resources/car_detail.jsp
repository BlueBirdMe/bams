 	<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆明细</title>
<%
String cid =request.getParameter("cid");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
	window.onload = function(){
		dwrOfficeResourcesService.getCarByPk(<%=cid%>,setpagevalue);
	}
	function setpagevalue(data){
		if(data!=null){
			if(data.resultList.length>0){
				var tmp = data.resultList[0];
				DWRUtil.setValue("oaCarCards",tmp.oaCarCards);
				DWRUtil.setValue("oaCarName",tmp.oaCarName);
				DWRUtil.setValue("oaCarModel",tmp.oaCarModel);
				DWRUtil.setValue("oaCarEngine",tmp.oaCarEngine);
				DWRUtil.setValue("oaCarType",tmp.library.libraryInfoName);
				DWRUtil.setValue("oaCarMete",tmp.oaCarMete);
				DWRUtil.setValue("oaCarMax",tmp.oaCarMax);
				DWRUtil.setValue("oaCarMotoeman",tmp.oaCarMotoeman);
				//DWRUtil.setValue("oaCarPrice",tmp.oaCarPrice);
				if(tmp.oaCarPrice != null){
					DWRUtil.setValue("oaCarPrice","￥"+FormatNumber(tmp.oaCarPrice,2));
				}
				DWRUtil.setValue("oaCarStatus",tmp.oaCarStatus);
				DWRUtil.setValue("oaCarBuydate",tmp.oaCarBuydate);
			
				document.getElementById("oaCarRemark").innerHTML = tmp.oaCarRemark;
				  var face = document.getElementById("oaCarPhoto");
				  face.src+="&imgId="+tmp.oaCarPhoto;
			}
		}
	}
		//查询方法
function queryData(){
	startQuery();
	var maintain=new Object();
	maintain.carId=<%=cid%>;
	var pager = getPager();
	dwrOfficeResourcesService.listCarmaintens(maintain,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}
	function edit(pkey) {
		Sys.load('<%=contextPath%>/erp/office_resources/car_mainten_add.jsp?pk=<%=cid%>&pkey='+pkey);
	}
function createProcessMethod(rowObj){
	var str="";
	if(rowObj.maintainType != 999){
	      
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='编辑' onclick=\"edit('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	
	}else{
		str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='已报废不能编辑'><img src='<%=contextPath%>/images/grid_images/rowset.png' border='0' /></a>";
		
	}
	str+="&nbsp;&nbsp;<a href='javascript:void(0)' title='删除' onclick=\"del('"+rowObj.primaryKey+"')\"><img src='<%=contextPath%>/images/grid_images/rowdel.png' border='0'/></a>";
	return str;
}
function del(pk){

		confirmmsg("确定要删除记录吗?","delok("+pk+")");
	
	}
		function delok(pk){
		var pks = new Array();
		pks[0] = pk;
		dwrOfficeResourcesService.deleteCarmaintensByPks(pks,delcallback);
	}
	function delcallback(data){
		alertmsg(data,"queryData()");
	}
	
function modi(){
	Sys.load("<%=contextPath%>/erp/office_resources/car.jsp");
}
function dblCallback(obj) {
	var box = new Sys.msgbox('明细查看', '<%=contextPath%>/erp/office_resources/car_carmaintens_detail.jsp?pk=' + obj.value, '700', '450');
	box.msgtitle = "<b>车辆维修明细</b>";
	box.show();
}

function repPrice(rowObj){
	var str="";
	if(rowObj.maintainMoney!=null){
		str = "￥"+FormatNumber(rowObj.maintainMoney,2);
	}
	return str;
}

</script>
</head>
  <body class="inputcls">
  <div class="formDetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;查看车辆状态的详细信息以及维修记录等。</label></div>
    <div class="detailtitle">车辆明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">车&nbsp;牌&nbsp;号</th>
		<td width="30%" id="oaCarCards" class="detailtabletd"></td>
		<td></td>
		<td rowspan="5" colspan="2">
			<file:imgshow  id="oaCarPhoto" width="128" ></file:imgshow><br/>
		</td>
	</tr>
	<tr>
	<th>车辆名称</th>
	<td id="oaCarName" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>厂牌型号</th>
	<td id="oaCarModel" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>排&nbsp;&nbsp;量</th>
	<td id="oaCarMete" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>车辆类型</th>
	<td id="oaCarType" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>最大载重</th>
	<td id="oaCarMax" class="detailtabletd"></td>
	</tr>
	<tr>
	<th>购买价格</th>
	<td id="oaCarPrice" class="detailtabletd"></td>
	<th>购买日期</th>
	<td id="oaCarBuydate" class="detailtabletd"></td>
	</tr>
	<tr>
	</tr>
	<tr>
	<th>备&nbsp;&nbsp;注</th>
	<td colspan="3" id="oaCarRemark" class="detailtabletd">
	</td>
	</tr>
		</table>
<br/>	
<center><div class="linediv"></div></center>
<br/>
<br/>	
<div class="detailtitle">维修记录</div>
<table class="detailtable" align="center">
<TR>
					<TD style="HEIGHT: 100%">
			<%
				SysGrid bg =new SysGrid(request);
					bg.setTableTitle("维修记录");
					bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
			        bg.setCheckboxOrNum(false);
			        bg.setTableHeight("350px");
			        bg.setBorder(1);
			        bg.setBodyScroll("auto");
					//设置附加信息
					bg.setQueryFunction("queryData");	//查询的方法名
					bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
					bg.setDblBundle("primaryKey");		//双击列的绑定的列值
					
			  		ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
			   		sccList.add(new SysColumnControl("applyEmployee.hrmEmployeeName","操作人",1,2,2,20));//设置GRIV 列
			   		sccList.add(new SysColumnControl("libraryName","维修类型",1,2,2,0));
			   	 	sccList.add(new SysColumnControl("maintainMoney","维修金额",1,2,2,0));
			    	sccList.add(new SysColumnControl("maintainDate","维修时间",1,2,2,50));
			    	sccList.add(new SysColumnControl("maintainAppendnews","维修原因",1,2,2,50));
			        ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
			        
			        for(int i=0;i<colList.size();i++){

					SysGridColumnBean bc =colList.get(i);
					if(bc.isShowAdvanced()||bc.isShowColumn()){
					
				    	if("maintainMoney".equalsIgnoreCase(bc.getDataName())){
								bc.setColumnStyle("text-align:right;");
								bc.setColumnReplace("repPrice");
							}
						}
						if("maintainDate".equalsIgnoreCase(bc.getDataName())){
								bc.setColumnStyle("text-align:center;");
						}
						
					}
					        
					bg.setColumnList(colList);
					out.print(bg.createTable());
			%>
					
		 			</TD>
				</TR>

	</table>
<br/>
</div>
	<table align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<btn:btn onclick="closeMDITab();" value="关 闭 "
						imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn>
				</td>
			</tr>
		</table>
</body>

</html>