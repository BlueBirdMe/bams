<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司记事明细查看</title>
<%
    String did = request.getParameter("did");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrOaNewsService.js"></script>   
<script type="text/javascript">
window.onload = function(){
   useLoadingMassage();
   dwrOaNewsService.getAdversariaByPk(<%=Integer.parseInt(did)%>,setPageValue);
}

function setPageValue(data){
	if(data != null){
	    if(data.resultList.length>0){
	        var adversaria = data.resultList[0];
	        DWRUtil.setValue("adverName",adversaria.oaAdverTitle);
	        
	        var type = "";
	        if(adversaria.oaAdverLevel ==<%=EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.MID.value%>){
	            type = "<%=EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.valueOf(EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.MID.value)%>";
	        }else{
	            type = "<img src='<%=contextPath%>/images/lve1.gif'>&nbsp;<%=EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.valueOf(EnumUtil.OA_ADVERSARIA_LEVEL_TYPE.HIGH.value)%>";
	        }
	        document.getElementById("adverLevel").innerHTML = type;
	        
	        var oaAdverStatus = "<font color='green'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value)%></font>";
	        if(adversaria.oaAdverStatus ==<%=EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value%>){
	        	oaAdverStatus = "<font color='red'><%=EnumUtil.OA_ISSUEINFO_STATUS.valueOf(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value)%></font>";
	        }
	        document.getElementById("adverStatus").innerHTML = oaAdverStatus;
	        
	        DWRUtil.setValue("adverTime",adversaria.oaAdverTime);
	        
	        if(adversaria.employee!=null){
	        	document.getElementById("adverEmpName").innerHTML = adversaria.employee.hrmEmployeeName;
	        }else{
	        	document.getElementById("adverEmpName").innerHTML ="&nbsp;";
	        }
	        
	        document.getElementById("adverText").innerHTML = adversaria.oaAdverText;
	        //附件显示为下载
	 		Sys.showDownload(adversaria.oaAdverAcce,"adverAcce");
	    }
	}
}
</script>
</head>
<body class="inputdetail">
	<div class="requdivdetail"><label>查看帮助:&nbsp;可通过点击附件来进行下载。</label></div>
		<div class="detailtitle">公司记事明细</div>
		<table class="detailtable" align="center">
			<tr>
				<th width="15%">记事标题</th>
				<td id="adverName" class="detailtabletd"></td>
				<td class="attachtd" rowspan="5">
					<div class="attachdiv">
						<div class="attachtitle">附件下载</div>
						<div class="attachdownload" id="adverAcce"></div>
					</div>
				</td>
			</tr>
			<tr>
				<th>记事人</th>
				<td id="adverEmpName" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>重要级</th>
				<td id="adverLevel" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>记事时间</th>
				<td id="adverTime" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>记事状态</th>
				<td id="adverStatus" class="detailtabletd"></td>
			</tr>
			<tr>
				<th>记事内容</th>
				<td colspan="3"  id="adverText" class="detailtabletd">
			    </td>
			</tr>
		</table>
</body>
</html>
