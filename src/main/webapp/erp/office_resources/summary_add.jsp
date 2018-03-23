<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加纪要</title>
<%
	String cid =request.getParameter("meetapplypk");
    String useId = UtilTool.getEmployeeId(request);
 %>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOfficeResourcesService.js"></script>
<script type="text/javascript">

window.onload = function(){
	useLoadingMassage();
	initInput("helpTitle","添加纪要，您可以在此处添加会议纪要。");
	dwrOfficeResourcesService.getMeetapplyByPk(<%=cid%>,setPageValue);
}

function save(){ 
//定义信息提示数组
	var warnArr = new Array();
	warnArr[0] = "oaSummaryReaderMust";
	warnArr[1] = "oaSummaryContentMust";
	warnArr[2] = "oaSummaryNeirongMust";
	//清空所有信息提示
	warnInit(warnArr);
	var bl = validvalue('helpTitle');
	if(bl){
		if(DWRUtil.getValue("oaWorkplanEmps_s") == ""){
         	//DWRUtil.setValue("aaac","请指定读者！");
         	setMustWarn("oaSummaryReaderMust","读者不能为空！");
    	}else if(DWRUtil.getValue("oaSummaryContent") == "" && DWRUtil.getValue("oaSummaryNeirong") == ""){
    		//DWRUtil.setValue("aaac","纪要内容和附件必须选填一项!");
    		var chooseArr = new Array();
			setMustWarn("oaSummaryContentMust","纪要内容和附件必须选填一项!");
			setMustWarn("oaSummaryNeirongMust","纪要内容和附件必须选填一项!");
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
    summary.oaSummaryNeirong = DWRUtil.getValue("oaSummaryNeirong");
    return summary;
}

function saveCallback(data){
	if(data.success){
		confirmmsgAndTitle("添加纪要成功！是否想继续添加？","clearContext();","继续添加","closePage();","关闭页面");
	}else{
		alertmsg(data);
	}
}	

var fck;
function FCKeditor_OnComplete(editorInstance) {
	fck= editorInstance;
	editorInstance.SetHTML(fckvalue);
	window.status = editorInstance.Description;
}

//返回会议室列表
function backToList(){
	 window.parent.MoveDiv.close();
	 window.parent.queryData();
}
function clearContext(){
	queryData();
	DWRUtil.setValue("oaSummaryName","");
	DWRUtil.setValue("oaSummaryReader","");
	DWRUtil.setValue("oaWorkplanEmps_s","");
	Sys.setFilevalue("oaSummaryContent","");
	DWRUtil.setValue("oaSummaryNeirong","");
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
		var box = SEL.getEmployeeIds("check","oaSummaryReader","oaWorkplanEmps_s");
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
              impo = "<%=EnumUtil.OA_MEET_TYPE.valueOf(EnumUtil.OA_MEET_TYPE.TWO.value)%>&nbsp;<img src='<%=contextPath%>/images/lve1.gif'>";
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
	Sys.href('<%=contextPath%>/erp/office_resources/meet_summary.jsp');
}

function getAffixCount(rowObj){
	var count =0 ;
	if(rowObj.oaSummaryContent!=null&&rowObj.oaSummaryContent != undefined&& rowObj.oaSummaryContent != "undefined"&&rowObj.oaSummaryContent.length>0){
		var cs = rowObj.oaSummaryContent.split(",");
		count = cs.length;
	}
	return count;
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
	<table class="detailtable" align="center" style="width: 90%">
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
<br/>
	<div class="formDetail">
			<div class="requdiv"><label id="helpTitle"></label></div>
			<div class="formTitle">会议纪要</div>
			<div>
				<table class="inputtable" border="0">
					<tr>
					<th width="15%"><em>* </em>纪要名称</th>
					<td style="text-align: left;" colspan="2" width="30%"><input type="text" id="oaSummaryName" must="纪要名称不能为空" formust="oaSummaryNameMust" maxlength="50" style="width: 90%;"></td>
					<td width="40%"><label id="oaSummaryNameMust"></label></td>
					</tr>													
					
					<tr>
					<th><em>* </em>指定读者</th>
					<td colspan="2">
					<textarea id="oaSummaryReader" readonly="readonly" onclick="selectEmps()" style="color: #999" must="读者不能为空" formust="oaSummaryReaderMust" linkclear="oaWorkplanEmps_s"></textarea>
					<input type="hidden" id="oaWorkplanEmps_s">
					</td>
					<td width="40%"><label id="oaSummaryReaderMust"></label></td>	
					</tr>
					<tr>
					<th><span style="color:blue">• </span><font color="#214079">纪要附件</font></th><td style="text-align: left" colspan="3">
					<file:multifileupload width="90%" acceptTextId="oaSummaryContent" height="100"></file:multifileupload>
					</td>
					<td width="38%"><label id="oaSummaryContentMust"></label></td>	
					</tr>
					<tr>
					<th><span style="color:blue">• </span><font color="#214079">纪要内容</font></th><td style="text-align: left" colspan="3">
					<textarea width="90%" id="oaSummaryNeirong" style="height: 150px" ></textarea>
					</td>
					<td width="38%"><label id="oaSummaryNeirongMust"></label></td>	
					</tr>
				</table>
			</div>
		</div>
						
<br/>
	<table align="center">
	   <tr>
    	<td><btn:btn onclick="save()" value="保 存 " imgsrc="../../images/png-1718.png" title="保存信息"></btn:btn></td>
    	<td style="width: 10px;"></td>
    	<td><btn:btn onclick="closePage()" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"></btn:btn></td>
	   </tr>
	</table>
<br/>

</body>
</html>