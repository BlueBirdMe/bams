<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 导入所需要css及js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/editdiv/editmsgboxJs.js"></script>
<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/js/editdiv/skin/editdiv.css"></link>

<!-- 执行div -->
<div class="shielddiv" id="shielddiv"></div>
<div class="processdiv" id="processdivObj">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
		<tr height="25px">
			<td class="title_left"></td>
			<td align="left" class="title_center" ondblclick="MoveDiv.max()">
				<label id="editmsgtitle">标题</label>
				<div class="closebtn" onclick="MoveDiv.close()" title="关闭"></div>
				<div class="maxbtn" onclick="MoveDiv.max()" title="全屏" id="editmsgdivmaxbtn"></div>
			</td>
			<td class="title_right"></td>
		</tr>
		<tr height="100%">
			<td class="body_l"></td>
			<td class="body_content">
				<iframe id="processloadfrm" allowTransparency="true" frameborder="0" height="100%" scrolling="auto" marginheight="0" width="100%"></iframe>
			</td>
			<td class="body_r"></td>
		</tr>
		<tr height="3px">
			<td class="bottom_l"></td>
			<td class="bottom_c"></td>
			<td class="bottom_r"></td>
		</tr>
	</table>
</div>