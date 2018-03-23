<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮箱设置示例</title>
</head>
<body class="inputdetail">
<input type="hidden" id="mailempPk">
<div class="requdivdetail">
			<label>
				查看帮助:&nbsp;请先登录邮箱进入设置SMTP及POP支持,以126邮箱为例。
			</label>
  </div>
  <div class="detailtitle">设置示例</div>
	<table class="detailtable" align="center">
	<tr>
		<th width="15%">发送别名</th>
		<td class="detailtabletd">张三</td>
		<td style="color: #808080">接受邮件显示的名称</td>
	</tr>
	<tr>
		<th>邮箱名称</th>
		<td class="detailtabletd">XXXXX@126.com</td>
		<td style="color: #808080"></td>
	</tr>
	<tr>
	  <th>邮箱SMTP</th>
	  <td class="detailtabletd">smtp.126.com</td>
	  <td style="color: #808080">发送邮件协议</td>
	</tr>
	<tr>
	  <th>邮箱POP</th>
	  <td class="detailtabletd">pop.126.com</td>
	  <td style="color: #808080">接收邮件协议</td>
	</tr>
	<tr>
	   <th>邮箱用户名</th>
	   <td class="detailtabletd">XXXXX</td>
	   <td style="color: #808080">登录邮箱的用户名</td>
	</tr>
	<tr>
		<th>邮箱密码</th>
		<td class="detailtabletd">••••••</td>
		<td style="color: #808080">登录邮箱的密码</td>
	</tr>
	<tr>
		<th>是否SMTP验证</th>
		<td class="detailtabletd">是</td>
		<td style="color: #808080">是否进行有效性验证，默认为是</td>
	</tr>
	<tr>
		<th>接收最大行</th>
		<td class="detailtabletd">10-99</td>
		<td style="color: #808080">每次接收邮件的最大数量，根据实际网速及邮件大小进行设置，以免邮件过多或网速过慢出现连接超时,系统默认为每次20条</td>
	</tr>
	<tr>
		<th>复选框</th>
		<td class="detailtabletd">选中标识可以使用该邮箱发送或接收邮件</td>
		<td style="color: #808080"></td>
	</tr>
	<tr>
		<th>SSL设置</th>
		<td class="detailtabletd" colspan="2">126、163、QQ、sina、sohu等邮箱不用选择，默认端口为：接收110 发送25<br/>Gmail、yahoo邮箱需要选择，默认端口为：接收995 发送465<br/>hotmail邮箱需要选择，默认端口为：接收995 发送25或587</td>
	</tr>
	
	<tr>
		<th>常用POP及SMTP</th>
		<td class="detailtabletd" colspan="2">
		<ul>
		<li>126邮箱：pop.126.com smtp.126.com 端口 110 25 不使用SSL</li>
		<li>163邮箱：pop.163.com smtp.163.com 端口 110 25 不使用SSL</li>
		<li>sina邮箱：pop.sina.com smtp.sina.com 端口 110 25 不使用SSL</li>
		<li>QQ邮箱：pop.qq.com smtp.qq.com 端口 110 25 不使用SSL</li>
		<li>sohu邮箱：pop3.sohu.com smtp.sohu.com 端口 110 25 不使用SSL</li>
		<li>Gmail邮箱：pop.gmail.com smtp.gmail.com 端口 995 465 使用SSL</li>
		<li>hotmail邮箱：pop3.live.com smtp.live.com 端口 995 25或587 使用SSL</li>
		<li>yahoo邮箱：pop.mail.yahoo.cn smtp.mail.yahoo.com 端口 995 465 使用SSL</li>
		</ul>
		</td>
	</tr>
	
	</table>
</body>
</html>