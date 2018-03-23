<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告明细查看</title>
<%
    String gid = request.getParameter("gid");
%>
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrSysProcessService.js"></script> 
<script type="text/javascript">
    window.onload = function(){
		dwrSysProcessService.getSysMethodInfoByPK('<%=gid%>',setPageValue);
	}
	function setPageValue(data){
		if(data.success == true){
		      if(data.resultList.length>0){
		          var method = data.resultList[0];
		          DWRUtil.setValue("methodNo",method.primaryKey);	        
		          DWRUtil.setValue("methodName",method.methodInfoName);	          
		          DWRUtil.setValue("methodEName",method.methodInfoEngname);      
                  DWRUtil.setValue("viewNo",method.methodNo);
                 
                  if(method.methodLevel == <%=EnumUtil.SYS_METHOD_LEVEL.TOP.value%>){
                       DWRUtil.setValue("grade","<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.TOP.value)%>");
                  }else if(method.methodLevel == <%=EnumUtil.SYS_METHOD_LEVEL.ONE.value%>){
                       DWRUtil.setValue("grade","<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.ONE.value)%>");
                  }else if(method.methodLevel == <%=EnumUtil.SYS_METHOD_LEVEL.TWO.value%>){
                       DWRUtil.setValue("grade","<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.TWO.value)%>");
                  }else{
                       DWRUtil.setValue("grade","<%=EnumUtil.SYS_METHOD_LEVEL.valueOf(EnumUtil.SYS_METHOD_LEVEL.THREE.value)%>");
                  }

                  if(method.upSysMethodInfo != null){
                       DWRUtil.setValue("upMethod",method.upSysMethodInfo.methodInfoName);
                  }

                  if(method.isAction == <%=EnumUtil.SYS_ISACTION.Vaild.value%>){
                       DWRUtil.setValue("methodStatus","<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.Vaild.value)%>");
                  }else{
                       DWRUtil.setValue("methodStatus","<%=EnumUtil.SYS_ISACTION.valueOf(EnumUtil.SYS_ISACTION.No_Vaild.value)%>");
                  }
		          
		          if(method.imageSrc != null || method.imageSrc != ""){
		               var src ="'" +"<%=contextPath%>/images/projectimg/"+method.imageSrc+"'";
		               var img = "<img src ="+src+">";
		               //DWRUtil.setValue("methodImage",img);
		               document.getElementById("methodImage").innerHTML = img;
		          }
		          
		          DWRUtil.setValue("jumpLoad",method.methodUri);
		          DWRUtil.setValue("methodMsg",method.methodMsg);
		          DWRUtil.setValue("methodPages",method.methodPages);
		      }else{
		          alertmsg("没找到相关记录！");
		      }
		  }else{
              alertmsg(data.message);
		  }
	}
</script>
</head>
  <body>
   <fieldset>
	<legend>功能目录明细</legend>
	<div>
	<table class="detailtable" align="center">
	<tr>
	<th width="15%">功能编号</th>
	<td id="methodNo"></td>
	<th width="15%">功能名称</th>
	<td id="methodName"></td>
	</tr>
	
	<tr>
	<th width="15%">功能英文名</th>
	<td id="methodEName"></td>
	<th width="15%">显示顺序</th>
	<td id="viewNo"></td>
	</tr>
	
	<tr>
	<th width="15%">等级</th>
	<td id="grade"></td>
	<th width="15%">上级功能</th>
	<td id="upMethod"></td>
	</tr>

    <tr>
	<th width="15%">状态</th>
	<td id="methodStatus"></td>
	<th width="15%">图标</th>
	<td id="methodImage"></td>
	</tr>

    <tr>
	<th width="15%">跳转路径</th>
	<td colspan="3"  id="jumpLoad">
	</td>
	</tr>
	
	<tr>
	<th width="15%">功能描述</th>
	<td colspan="3"  id="methodMsg">
	</td>
	</tr>
	<tr>
	<th width="15%">相关页面</th>
	<td colspan="3"  id="methodPages">
	</td>
	</tr>
	
	</table>
	</div>
</fieldset>
<br/>

<br/>
</body>
</html>