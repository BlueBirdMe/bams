<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议明细</title>
<%
String cid =request.getParameter("cid");
String useId = UtilTool.getEmployeeId(request);
String flag = request.getParameter("flag");
String status = request.getParameter("status");
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">
window.onload = function(){
	useLoadingMassage();
	dwrOfficeResourcesService.getMeetapplyByPk(<%=cid%>,setPageValue);
}

function selectEmps(){
			if(<%=flag %>==true) {
		var box = SEL.getEmployeeIds("check","oaSummaryReader","oaWorkplanEmps_s","approvefrm@@processloadfrm");
		box.show();
	} else {
		var box = SEL.getEmployeeIds("check","oaSummaryReader","oaWorkplanEmps_s","processloadfrm");
		box.show();
	}
 }
   
function reload(){
       Sys.load("<%=contextPath%>/erp/office_resources/meet_summary.jsp");
   }


//查询方法
function queryData(){
	startQuery();
	var summary = new Object();
	
	summary.oaSummaryMeetId = <%=cid%>;
	
	var pager = getPager();
	dwrOfficeResourcesService.listSummarys(summary,pager,queryCallback);
}

function queryCallback(data){
	if(data.success == true){
		initGridData(data.resultList,data.pager);
	}else{
		alert(data.message);
	}
	endQuery();
}


function setPageValue(data){
 if(data != null){
      if(data.resultList.length>0){
          var meetapply = data.resultList[0];
          
          DWRUtil.setValue("oaMeetapplyName",meetapply.oaMeetapplyName);
          DWRUtil.setValue("oaMeetapplyTheme",meetapply.oaMeetapplyTheme);
          if(meetapply.library != null){
              DWRUtil.setValue("oaMeetapplyType",meetapply.library.libraryInfoName);
          }
          if(meetapply.meetApplyRoomObj != null){
              DWRUtil.setValue("oaMeetapplyRoom",meetapply.meetApplyRoomObj.oaBoardroomName);
          }
          if(meetapply.employee != null){
              DWRUtil.setValue("oaMeetapplyEmp",meetapply.employee.hrmEmployeeName);
          }
          DWRUtil.setValue("oaMeetapplyDate",meetapply.oaMeetapplyDate);
          DWRUtil.setValue("oaMeetapplySummary",meetapply.jiyaoEmpNames);
          DWRUtil.setValue("oaMeetapplyDep",meetapply.zhubanDep);
          DWRUtil.setValue("oaMeetapplyEmpw",meetapply.oaMeetapplyEmpw);
          DWRUtil.setValue("oaMeetapplyEmpn",meetapply.chuxiEmpName);
          var tixi = "";
          if(meetapply.oaMeetapplyAwoke == "false"){
              tixi = "否";
          }else if(meetapply.oaMeetapplyAwoke == "true"){
              tixi = "是";
          }
          DWRUtil.setValue("oaMeetapplyAwoke",tixi);
          var impo = "";
          if(meetapply.oaMeetapplyDegree == <%=EnumUtil.OA_MEET_TYPE.ONE.value%>){
              impo = "<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.ONE.value)%>";
          }else{
              impo = "<img src='<%=contextPath%>/images/lve1.gif'>&nbsp;<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.TWO.value)%>";
          }
          document.getElementById("oaMeetapplyDegree").innerHTML = impo;
          DWRUtil.setValue("oaMeetapplyStar",meetapply.oaMeetapplyStar);
          DWRUtil.setValue("oaMeetapplyEnd",meetapply.oaMeetapplyEnd);
          document.getElementById("oaMeetapplyDescribe").innerHTML = meetapply.oaMeetapplyDescribe;
		  Sys.showDownload(meetapply.oaMeetapplyAffix,"files");
      }
  }
}
	
function dblCallback(obj){
	var box = new Sys.msgbox('纪要明细查看','<%=contextPath%>/erp/office_resources/summary_detail.jsp?sid='+obj.value,'800','500');
		box.msgtitle="<b>会议纪要明细</b>";
		box.show();
}

function back(){
	if("<%=status%>" == "will_attendedmeet"){
		Sys.href('<%=contextPath%>/erp/office_resources/will_attendmeet.jsp');
	}else if("<%=status%>" == "attendedmeet"){
		Sys.href('<%=contextPath%>/erp/office_resources/attendedmeet.jsp');
	}else if("<%=status%>" == "my_applymeeting"){
		Sys.href('<%=contextPath%>/erp/office_resources/my_applymeeting.jsp');
	}
}

function getAffixCount(rowObj){
	var count =0 ;
	if(rowObj.oaSummaryContent!=null&&rowObj.oaSummaryContent != undefined&& rowObj.oaSummaryContent != "undefined"&&rowObj.oaSummaryContent.length>0){
		var cs = rowObj.oaSummaryContent.split(",");
		count = cs.length;
	}
	return count;
}
function  addSummary(){
	      Sys.close();
	      window.parent.mainframe.location ='<%=contextPath%>/erp/office_resources/summary_add.jsp?meetapplypk='+<%=cid%>+'';
}
function warn() {
	var url = '<%=contextPath%>/erp/office_resources/meetapply_sms_send.jsp?oid='+<%=cid%>
	openMDITab(url);
	
}

function closePage(){
	closeMDITab();
}

</script>

</head>
<body class="inputcls">
<div class="formDetail"  >
    <div class="requdivdetail"><label>查看帮助:&nbsp;查看会议的详细信息，以及纪要的明细，可通过点击附件来进行下载。</label></div>
    <div class="detailtitle">会议明细</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">会议名称</th>
		<td width="25%" id="oaMeetapplyName" class="detailtabletd"></td>
		<th></th>
		<td class="attachtd" rowspan="5" >
			<div class="attachdiv">
				<div class="attachtitle">附件下载</div>
				<div class="attachdownload" id="files"></div>				
			</div>
		</td>
	</tr>
			<tr>
				<th>会议主题</th>
				<td id="oaMeetapplyTheme" class="detailtabletd"></td></tr>
				<tr>
				<th>会议类型</th>
				<td id="oaMeetapplyType" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议室</th>
				<td id="oaMeetapplyRoom" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>主办部门</th>
				<td id="oaMeetapplyDep" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>发布人</th>
				<td id="oaMeetapplyEmp" class="detailtabletd"></td>
				<th width="5%">时间</th>
				<td id="oaMeetapplyDate" class="detailtabletd" width="20%"></td>
				</tr>
				
				<tr>
				<th>是否短信提醒</th>
				<td id="oaMeetapplyAwoke" class="detailtabletd"></td>
				
				<th>重要程度</th>
				<td id="oaMeetapplyDegree" class="detailtabletd"></td>
				</tr>
				
				<tr>
				<th>开始时间</th>
				<td id="oaMeetapplyStar" class="detailtabletd"></td>
				<th>结束时间</th>
				<td id="oaMeetapplyEnd" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>出席人员(外部)</th>
				<td id="oaMeetapplyEmpw" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>出席人员(内部)</th>
				<td id="oaMeetapplyEmpn" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议纪要人</th>
				<td id="oaMeetapplySummary" colspan="3" class="detailtabletd"></td>
				</tr>
				<tr>
				<th>会议描述</th>
				<td colspan="3"  id="oaMeetapplyDescribe" class="detailtabletd">
				</td>
				</tr>
		</table>
<br/>
		<center><div class="linediv"></div></center>
<br/>
<div class="detailtitle">会议纪要</div>
	<table align="center" style="width: 90%">
	<TR>
					<TD >
						<%
							SysGrid bg =new SysGrid(request);
									bg.setTableHeight("300px");
									bg.setBorder(1);
										bg.setTableTitle("纪要明细");
										bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
								        bg.setCheckboxOrNum(false);
										bg.setBodyScroll("auto");
										//设置附加信息
										bg.setQueryFunction("queryData");	//查询的方法名
										bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
										bg.setDblBundle("primaryKey");		//双击列的绑定的列值
											
										//放入列
									    ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
									   		sccList.add(new SysColumnControl("employee.hrmEmployeeName","纪要人",1,2,2,0));//设置GRIV 列
									   		sccList.add(new SysColumnControl("oaSummaryName","纪要名称",1,2,2,0));
									   	 	sccList.add(new SysColumnControl("oaSummaryDate","纪要日期",1,2,2,0));
									    ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList);
											colList.add(ColumnUtil.getCusterShowColumn("affixCount","纪要附件数","getAffixCount",0,"text-align:center"));
											bg.setColumnList(colList);
											out.print(bg.createTable());
						%>
		 			</TD>
				</TR>
	</table>
	<br/>
</div>

						
<br/>
	<table align="center">
	   <tr>
	   <%if(request.getParameter("sms") != null && "true".equals(request.getParameter("sms"))){ %>
	     <td><btn:btn onclick="warn()" value="短信提醒参会人 " imgsrc="../../images/fileokico.png" title="短信通知参会人"></btn:btn></td>
	     <td style="width: 10px;"></td>
	    <%}%>
	     <td ><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
	   </tr>
	</table>
<br/>
</body>
</html>