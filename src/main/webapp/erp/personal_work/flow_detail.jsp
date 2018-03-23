<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrPersonalProcessService.js"></script>
<%
	String id = request.getParameter("id");
%>
<script type="text/javascript">
	window.onload = function() {
		useLoadingMassage();
		dwrPersonalProcessService.findHistoryDetail(<%=id%>, setData);
	}

	function setData(data) {
		if (data.success == true) {
			if (data.resultList.length > 0) {

				for ( var i = 0; i < data.resultList.length; i++) {
					var obj = data.resultList[i];
					var otr = document.getElementById("resultTable").insertRow(-1);

					var td1 = document.createElement("td");
					td1.style.cssText = "text-align:center;"
					td1.innerHTML = "第" + parseInt(i + 1) + "步";

					var td2 = document.createElement("td");
					td2.style.cssText = "text-align:center;"
					td2.innerHTML = obj.historicTaskInstance.name;

					var td3 = document.createElement("td");
					var s = "";
					if (obj.employee.hrmEmployeeName != null)
						s += obj.employee.hrmEmployeeName;
					if (obj.durationTime != null && obj.durationTime != "")
						s += "[" + obj.durationTime + "]"
					if (obj.instanceStartTime != null)
						s += "<br/>开始时间：" + obj.instanceStartTime + "<br/>"
					if (obj.instanceEndTime != null)
						s += "结束时间：" + obj.instanceEndTime
					td3.innerHTML = s;

					var td4 = document.createElement("td");
					td4.style.cssText = "text-align:center;"
					td4.innerHTML = obj.taskStatus;	
						
					var td5 = document.createElement("td");
					var s = "&nbsp;";
					for ( var j = 0; j < obj.commentList.length; j++) {
						s = obj.commentList[j].fullMessage;
					}
					td5.innerHTML = s;

					otr.appendChild(td1);
					otr.appendChild(td2);
					otr.appendChild(td3);
					otr.appendChild(td4);
					otr.appendChild(td5);
				}
				
				dwrPersonalProcessService.checkProcessFinish(<%=id%>, function(data){
					var str = "流程正在执行中";
					if(data){
						str = "流程已结束";
					}
					var tr = document.getElementById("resultTable").insertRow(-1);
					var td = document.createElement("td");
					td.style.cssText = "text-align:center;padding:10px;font-weight:bold;"
					td.colSpan = "6";
					td.innerHTML = str;
					tr.appendChild(td);
					
				});
			}
		}
	}
</script>

<table cellspacing="0" cellpadding="0" width="95%" align="center">
<tr>
	<td>
	<DIV class="tabdiv">
		<UL class="tags">
		<LI><A onClick="tab.selectTag(this);" href="javascript:void(0)">审批步骤</A></LI>
		<LI><A onClick="tab.selectTag(this)" href="javascript:void(0)">流程图</A> </LI>
		</UL>
		<DIV class="tagContentdiv">
			<DIV class="tagContent" id="tag0">
			   <div style="overflow: hidden;" >
					<table cellspacing='0' cellpadding="4" width="95%" border="1" id="resultTable" bordercolor="#808080" style="border-collapse: collapse;">
						<tr>
						<td class="tableTitle1" width="60">步骤</td>
						<td class="tableTitle1" width="100">节点</td>
						<td class="tableTitle1" width="200">办理情况</td>
						<td class="tableTitle1" width="80">办理状态</td>
						<td class="tableTitle1">相关意见</td>
						</tr>
			   		</table>
				</div>
			</DIV>
			<DIV class="tagContent" id="tag1">
				<div style="overflow: hidden;" >
					<img src="<%=request.getContextPath()%>/processTrace.do?id=<%=id %>"/>
				</div>
			</DIV>
		</DIV>
	</DIV>
	</td>
</tr>
</table>

<script type="text/javascript">
	var tab = new SysTab('<%=request.getContextPath()%>');
</script>
