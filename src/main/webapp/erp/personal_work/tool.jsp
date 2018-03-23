<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script>
	function go(p){
		window.location="erptool1.jsp?par="+p;
	}
</script>
</head>
<body>

<table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;交通常用查询</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/tianqi.png" style="cursor:pointer;" onclick="go('1')"/><br/>各地天气预报</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/jiudian.png" style="cursor:pointer;" onclick="go('2')"/><br/>酒店查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/gjxl.png" style="cursor:pointer;" onclick="go('3')"/><br/>公交线路查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/lcsk.png" style="cursor:pointer;" onclick="go('4')"/><br/>列车时刻查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/hbcx.png" style="cursor:pointer;" onclick="go('5')"/><br/>航班查询</td>
    </tr>
    <tr>
      <td class="title_left" colspan="7"><hr color="#ecedf2"/></td>
    </tr>
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;地区查询工具</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/dhqh.png" style="cursor:pointer;" onclick="go('6')"/><br/>电话区号查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/yzbm.png" style="cursor:pointer;" onclick="go('7')"/><br/>邮政编码查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/zxdt.png" style="cursor:pointer;" onclick="go('8')"/><br/>在线地图</td>
    </tr>
    <tr>
      <td class="title_left" colspan="7"><hr color="#ecedf2"/></td>
    </tr>
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;所属地查询工具</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/ipcx.png" style="cursor:pointer;" onclick="go('9')"/><br/>IP所属地查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/sjsd.png" style="cursor:pointer;" onclick="go('10')"/><br/>手机所属地查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/dhsd.png" style="cursor:pointer;" onclick="go('11')"/><br/>固定电话查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/sfzh.png" style="cursor:pointer;" onclick="go('12')"/><br/>身份证所属地查询</td>
    </tr>
    <tr>
      <td class="title_left" colspan="7"><hr color="#ecedf2"/></td>
    </tr>
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;时间工具</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/qqsj.png" style="cursor:pointer;" onclick="go('17')"/><br/>全球时间</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/wnl.png" style="cursor:pointer;" onclick="go('13')"/><br/>万年历</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/jjr.png" style="cursor:pointer;" onclick="go('14')"/><br/>全球节日查询</td>
    </tr>
    <tr>
      <td class="title_left" colspan="7"><hr color="#ecedf2"/></td>
    </tr>
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;度量换算工具</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/cdhs.png" style="cursor:pointer;" onclick="go('15')"/><br/>长度换算</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/mjhs.png" style="cursor:pointer;" onclick="go('16')"/><br/>面积换算</td>
    </tr>
    <tr>
      <td class="title_left" colspan="7"><hr color="#ecedf2"/></td>
    </tr>
    <tr>
      <td class="title_left" colspan="7">&nbsp;&nbsp;其他帮助</td>
    </tr>
    <tr>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/kdcx.png" style="cursor:pointer;" onclick="go('18')"/><br/>快递查询</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/zxfy.png" style="cursor:pointer;" onclick="go('19')"/><br/>在线翻译</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/baidu.png" style="cursor:pointer;" onclick="go('20')"/><br/>百度搜索</td>
      <td width="20%" class="show_table_detail"><img src="<%=contextPath%>/images/commentoolsimage/google.png" style="cursor:pointer;" onclick="go('21')"/><br/>谷歌搜索</td>
    </tr>
  </table>

</body>
</html>