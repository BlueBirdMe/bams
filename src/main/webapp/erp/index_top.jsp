<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$('#config').contextMenu('configMenu', {
		bindings : {
			'viewemployee' : function(t) {
				var box = new Sys.msgbox('个人信息','<%=contextPath %>/erp/employee_info.jsp','700','500');
				box.msgtitle="个人信息";
				box.show();
			},
			'editname' : function(t) {
				var box = new Sys.msgbox('修改登录用户名','<%=contextPath %>/erp/editUserNameMsg.jsp','700','400');
				box.msgtitle="<b>修改用户名</b><br/>修改登录用户名后再次登录将生效!";
				var butarray = new Array();
				butarray[0] = "ok|updateName();";
				butarray[1] = "cancel";
				box.buttons = butarray;
				box.show();
			},
			'editpass' : function(t) {
				var box = new Sys.msgbox('修改登录密码','<%=contextPath %>/erp/editUserPwdMsg.jsp','700','450');
				box.msgtitle="<b>修改密码</b><br/>修改登录密码后再次登录将生效!";
				var butarray = new Array();
				butarray[0] = "ok|updatepassword();";
				butarray[1] = "cancel";
				box.buttons = butarray;
				box.show();
			},
			'exit' : function(t) {
				if(window.confirm("确定要退出系统吗？")){
					location.href="<%=contextPath %>/checkSession.do";
				}
			}
		},
		menuStyle : {
			width : '100px'
		},
		itemStyle: {
		      padding: '3px 10px'
		},
		allowMouseOver : true,
		offsetx : 80
	});
})

function updatepasswordback(data){
	if(data.success){
		confirmmsg("密码修改成功，点击确定重新登录!","repload()");
	}
}

function updatenamedback(data){
	if(data.success){
		confirmmsg("用户名修改成功，点击确定重新登录!","repload()");
	}
}

function repload(){
	location.href="<%=contextPath %>/login.jsp";
}

</script>

<table cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td align="right" height="30">
			<table border="0" style="margin-right:10px;">
			<tr>
				<td style="color:#FFFFFF;">
					欢迎您：<%=sessionUser.getEmployeeName() %>&nbsp;&nbsp;
					<%--[ 部门:<%=sessionUser.getEmployeeDeptName() %>&nbsp;&nbsp;--%>
					<%--岗位:<%=sessionUser.getMainPost() == null ? "管理员" : sessionUser.getMainPost().getHrmPostName() %> ]&nbsp;&nbsp;--%>
				</td>
				<td>
					<a id="config" href="javascript:void(0);"><img src="<%=contextPath %>/images/logout.png" /></a>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	
	<!-- 
	<tr>
		<td height="20"></td>
	</tr>	
	 -->
	 
	<tr>
		<td height="27">
            <table style="width: 100%; table-layout: fixed;" cellSpacing="0" border="0" cellPadding="0">
              <tr>
                <td id="tab_left" class="pre"><a class="hidden" title="向前" href="javascript:onLefttab();">向前</a></td>
                <td><iframe id="tabFrame" src="<%=contextPath %>/js/tabs/systabs.html" frameBorder="0" 
	                 width="100%" height="27" name="tabs" marginWidth="0" marginHeight="0" scrolling="no" allowTransparency="true"></iframe></td>
                <td id="tab_right" class="next"><a class="hidden" title="向后 " href="javascript:onRighttab();">向后</a></td>
              </tr>
	        </table>
		</td>
	</tr>
</table>

<div style="display:none;" id='configMenu'>
	<ul>
		<li id='viewemployee'>查看个人信息</li>
		<li id='editname'>修改用户名</li>
		<li id='editpass'>修改密码</li>
		<li id='exit'><font color="red">退出系统</font></li>
	</ul>
</div>