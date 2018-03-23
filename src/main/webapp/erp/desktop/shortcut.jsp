<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrPersonalProcessService.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>快捷菜单</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrSysProcessService.js"></script>
<script type="text/javascript">
window.onload = function() {
    useLoadingMassage();
	initInput("helpTitle","您可以在这里添加经常使用的功能。");
	showShortcut();
}

function showShortcut(){
	$.ajax({
        url: '<%=contextPath%>/erp/desktop/desktopHtml_shortcut.jsp',
			type : 'get',
			success : function(responseText) {
				$("#shortcut").html(responseText);

				$('#shortcut li').contextMenu('shortcutMenu', {
					bindings : {
						'del' : function(t) {
							deleteShortcut(t.id)
						},
						'flag' : function(t) {
							updateShortcut(t.id)
						}
					},
					menuStyle : {
						width : '60px'
					}
				});
			}
		});
	}

	function updateShortcut(pk) {
		dwrSysProcessService.updateSysMethodShortcutByPk(pk, function(data) {
			alertmsg(data);
			showShortcut();
		});
	}

	function deleteShortcut(pk) {
		var pks = new Array();
		pks[0] = pk;
		dwrSysProcessService.deleteSysMethodShortcutByPks(pks, function(data) {
			showShortcut();
		});
	}

	function addShortcut() {
		var box = SEL.getUserSysMethodInfoIds("radio", "showShortcut();");
		box.show();
	}
</script>
<style>
a.deskbtn {
	color: black;
}
#shortcut:after{display:block;clear:both;visibility:hidden;height:0;overflow:hidden;content:".";}
</style>
</head>
<body class="inputcls">
	<div class="formDetail">
		<div class="requdiv">
			<label id="helpTitle"></label>
		</div>
		<div class="formTitle">快捷菜单管理</div>
		<div style="padding:20px;width:700px;" id="shortcut"></div>
		<div style="padding:20px;text-align:left;color:#808080;">
			<p>右击菜单可进行删除和标记操作</p>
			<p>标记后下次登录时默认打开，若要取消再标记一次即可</p>
			<p>带“*”号的为已标记菜单</p>
		</div>
		<div style="display:none;" id='shortcutMenu'>
			<ul>
				<li id='del'><img src="<%=contextPath%>/images/grid_images/close.png"> 删除</li>
				<li id='flag' title="标记后下次默认打开，若要取消再标记一次即可"><img src="<%=contextPath%>/images/grid_images/bullet_wrench.png"> 标记</li>
			</ul>
		</div>
	</div>
	<table align="center">
		<tr>
			<td><btn:btn onclick="addShortcut()" value=" 新 增 " title="新增快捷菜单"></btn:btn>
			</td>
			<td width="20px"></td>
			<td><btn:btn onclick="closeMDITab()" value=" 关 闭 " title="关闭当前页面"></btn:btn>
			</td>
		</tr>
	</table>
</body>
</html>