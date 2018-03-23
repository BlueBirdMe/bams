<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%
	String cid =request.getParameter("nid");
    String useId = UtilTool.getEmployeeId(request);

 %>


<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>

<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput('aaac');
	dwrOfficeResourcesService.getMeetapplyByPk(<%=cid%>,setPageValue);
}

	    
function save(){ 
	var bl = validvalue('aaac');
	if(bl){
		if(DWRUtil.getValue("oaWorkplanEmps_s") == ""){
         	DWRUtil.setValue("aaac","请指定读者！");
    	}else if(DWRUtil.getValue("oaSummaryContent") == "" && DWRUtil.getValue("oaSummaryNeirong") == ""){
    		DWRUtil.setValue("aaac","纪要内容和附件必须选填一项!");
    	}else{ 
			dwrOfficeResourcesService.saveSummary(getSummaryinfo(),saveCallback);
		}
	}
}

function getSummaryinfo(){
    var summary = new Object();
    summary.oaSummaryMeetId= <%=cid%>;
    summary.oaSummaryName = DWRUtil.getValue("oaSummaryName");
    summary.oaSummaryMeetDate = DWRUtil.getValue("oaSummaryMeetDate");
    summary.oaSummaryReader = DWRUtil.getValue("oaWorkplanEmps_s")
    summary.oaSummaryContent = DWRUtil.getValue("oaSummaryContent");
    return summary;
}

function saveCallback(data){
	alertmsg("添置成功","clearContext()");
}	

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}

function clearContext(){
	DWRUtil.setValue("oaSummaryName","");
	DWRUtil.setValue("oaSummaryReader","");
	DWRUtil.setValue("oaWorkplanEmps_s","");
	Sys.setFilevalue("oaSummaryContent","");
}

 
    

var fckvalue="";       
function setSummaryinfo(data){
	if(data.success == true){
 		if(data.resultList.length > 0){
 			var summary = data.resultList[0];
 		 DWRUtil.setValue("oaSummaryName",summary.oaSummaryName);
	     DWRUtil.setValue("oaSummaryDate",summary.oaSummaryDate);
	     DWRUtil.setValue("oaSummaryReader",summary.oaSummaryReader);
	     
	     fckvalue = summary.oaSummaryContent;
 		}else{
 			alert(data.message);
 		}
 	}else{
 		alert(data.message);
 	}
}
	
	
function selectEmps(){
	var box = new Sys.msgbox('指定读者','<%=contextPath%>/erp/select_takepage/select_emp.jsp?treetype=check&textid=oaSummaryReader&valueid=oaWorkplanEmps_s','700','500');
	box.msgtitle="<b></b><br/>选择阅读人员";
	var butarray = new Array();
	butarray[0] = "ok|employeeclick('approvefrm');";
	butarray[1] = "cancel";
	box.buttons = butarray;
	box.show();
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
          
          var sumPerson = meetapply.oaMeetapplySummary.split(",");
          for(var i=0;i<sumPerson.length;i++){
          
	          if('<%=useId%>'==sumPerson[i]){
	          	
	          	  document.getElementById("tag2").style.display="block";
	          	  break;
			  }else{
			  
			  	  document.getElementById("tag2").style.display="none";
			  }
          }

		
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
              impo = "<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.TWO.value)%>";
          }
          DWRUtil.setValue("oaMeetapplyDegree",impo);
          DWRUtil.setValue("oaMeetapplyStar",meetapply.oaMeetapplyStar);
          DWRUtil.setValue("oaMeetapplyEnd",meetapply.oaMeetapplyEnd);
          document.getElementById("oaMeetapplyDescribe").innerHTML = meetapply.oaMeetapplyDescribe;
          Sys.showDownload(meetapply.oaMeetapplyAffix,"files");	
      }
  }
}
	
function dblCallback(obj){
	var box = new Sys.msgbox('纪要明细查看','<%=contextPath%>/erp/office_resources/summary_detail.jsp?sid='+obj.value,'600','350');
		box.msgtitle="<b>会议纪要明细</b>";
		box.show();
}

function back(){
	Sys.href('<%=contextPath%>/erp/office_resources/meetapply.jsp');
}

function getAffixCount(rowObj){
	var count =0 ;
	if(rowObj.oaSummaryContent!=null&&rowObj.oaSummaryContent != undefined&& rowObj.oaSummaryContent != "undefined"&&rowObj.oaSummaryContent.length>0){
		var cs = rowObj.oaSummaryContent.split(",");
		count = cs.length;
	}
	return count;
}

</script>

</head>
<body>

<DIV class="tabdiv" style="width: 100%" id="tabdiv1" style="overflow: hidden;">
	<UL class="tags">
	<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">会议明细</A></LI>
	<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">纪要明细</A></LI>
	</UL>
	<DIV class="tagContentdiv">
		<DIV class="tagContent" id="tag0" style="overflow: hidden;">
			<table class="detailtable" align="center">
				<tr>
				<th width="15%">会议名称</th>
				<td id="oaMeetapplyName" colspan="3"></td>
				</tr>
				<tr>
				<th width="15%">会议主题</th>
				<td id="oaMeetapplyTheme" colspan="3"></td></tr>
				<tr>
				<th width="15%">会议类型</th>
				<td id="oaMeetapplyType"></td>
				<th width="15%">会议室</th>
				<td id="oaMeetapplyRoom"></td>
				</tr>
				<tr>
				<th width="15%">申请人</th>
				<td id="oaMeetapplyEmp"></td>
			
				<th width="15%">申请时间</th>
				<td id="oaMeetapplyDate"></td>
				</tr>
				<tr>
				<th width="15%">会议纪要人</th>
				<td id="oaMeetapplySummary" colspan="3"></td>
				</tr>
				<tr>
				<th width="15%">主办部门</th>
				<td id="oaMeetapplyDep" colspan="3"></td>
				</tr>
				<tr>
				<th width="15%">出席人员(外部)</th>
				<td id="oaMeetapplyEmpw" colspan="3"></td>
				</tr>
				<tr>
				<th width="15%">出席人员(内部)</th>
				<td id="oaMeetapplyEmpn" colspan="3"></td>
				</tr>
				<tr>
				<th width="15%">是否短信提醒</th>
				<td id="oaMeetapplyAwoke"></td>
				
				<th width="15%">重要程度</th>
				<td id="oaMeetapplyDegree"></td>
				</tr>
				
				<tr>
				<th width="15%">开始时间</th>
				<td id="oaMeetapplyStar"></td>
			
				<th width="15%">结束时间</th>
				<td id="oaMeetapplyEnd" colspan="3"></td>
				</tr>
				<tr>
				<th>附件</th>
				<td valign="bottom" colspan="3">
				<iframe frameborder="0" marginheight="0" allowTransparency="true" width="100%" height="40"  id="files" scrolling="auto" style="padding-top: 5px;"></iframe>
				</td>
				</tr>
				<tr>
				<th>会议描述</th>
				<td colspan="3"  id="oaMeetapplyDescribe">
				</td>
				</tr>
			</table>
			
		</DIV>
		
		<DIV class="tagContent" id="tag1" style="overflow: auto;height:550px;text-align: center;">
			<table style="width: 99%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD class="td_border">
						<%
							SysGrid bg =new SysGrid(request);
															bg.setTableHeight("300px");
																bg.setTableTitle("纪要明细");
																bg.setShowImg(false);//默认为true 显示切换视图 为true必须指定图片相关信息
														        bg.setCheckboxOrNum(false);
																//设置附加信息
																bg.setQueryFunction("queryData");	//查询的方法名
																bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
																bg.setDblBundle("primaryKey");		//双击列的绑定的列值
																	
																//放入按钮
																ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
																	btnList.add(new SysGridBtnBean("返回", "back()", "add.png"));
																	bg.setBtnList(btnList);		
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
			<table style="width: 100%" border="0" id="tag2" cellpadding="0" cellspacing="0" >
				<TR>
					<TD>
						<fieldset>
							<div class="requdiv"><label id="aaac"></label></div>
							<legend>会议纪要</legend>
							<div>
							<table class="inputtable">
							<tr>
							<th><em>*</em>纪要名称</th>
							<td style="text-align: left;" colspan="3"><input type="text" id="oaSummaryName" must="请输入纪要名称" maxlength="50" style="width: 90%;"></td>
							</tr>													
							
							<tr>
							<th><em>*</em>指定读者</th>
							<td colspan="3">
							<textarea id="oaSummaryReader" readonly="readonly" onclick="selectEmps()" style="color: #999" >双击选择</textarea>
							<input type="hidden" id="oaWorkplanEmps_s">
							</td>	
							</tr>
							<tr>
							<th><em><li style="color:blue"></li></em><font color="#214079">纪要附件</font></th><td style="text-align: left" colspan="3">
							<file:multifileupload width="90%" acceptTextId="oaSummaryContent" height="120"></file:multifileupload>
							</td>
							</tr>
							<tr>
							<th><em><li style="color:blue"></li></em><font color="#214079">纪要内容</font></th><td style="text-align: left" colspan="3">
							<textarea width="90%" id="oaSummaryNeirong" style="height: 150px" ></textarea>
							</td>
							</tr>
							</table>	
							</div>
						</fieldset>
						<br/>
							<table align="center">
							   <tr>
							     <td><btn:btn onclick="save()" value=" 确  定 "></btn:btn></td>
							     <td style="width: 10px;"></td>
							     <td><btn:btn onclick="reload()" value=" 返  回 "></btn:btn></td>
							   </tr>
							</table>
							</TD>
							</TR>
							</table>
		</DIV>
	</DIV>
</DIV>

</body>
<script type="text/javascript">
	var tab =new SysTab('<%=contextPath%>',null,"tabdiv1");
</script>
</html>