<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
    if (pk == null) {
        SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
        HrmEmployee empinfo = user.getEmployeeInfo();
        pk = empinfo.getHrmEmployeeCode();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>个人信息明细</title>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxUserinfoService.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxEducateService.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementService.js"></script>
    <script src="../../js/rlzy/Chart.bundle.min.js"></script>
    <script src="../../js/rlzy/utils.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            dwrDxUserinfoService.getDxUserinfoByPk('<%=pk%>', setPageValueUser);
            dwrDxEducateService.getDxEducateByPk('<%=pk%>', setPageValueEducate);
            dwrDxArchivementService.getDxArchivementByPk('<%=pk%>', setPageValueArchivement);
            dwrDxUserinfoService.computeUserLev('<%=pk%>', computeCallback)
            window.myRadar = new Chart(document.getElementById('canvas'), config);
        }

        function computeCallback(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    config.data.datasets[0].data = data.resultList[0];
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }

        function setPageValueUser(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var dxuserinfo = data.resultList[0];
                    DWRUtil.setValue("name", dxuserinfo.name);
                    DWRUtil.setValue("technicalpost", dxuserinfo.technicalpost);
                    DWRUtil.setValue("birthday", dxuserinfo.birthday);
                    DWRUtil.setValue("minority", dxuserinfo.minority);
                    DWRUtil.setValue("workdate", dxuserinfo.workdate);
                    DWRUtil.setValue("corpdate", dxuserinfo.corpdate);
                    DWRUtil.setValue("fulltime", dxuserinfo.fulltime);
                    DWRUtil.setValue("graduate", dxuserinfo.graduate);
                    DWRUtil.setValue("major", dxuserinfo.major);
                    DWRUtil.setValue("education", dxuserinfo.education);
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }
        function setPageValueEducate(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var dxeducate = data.resultList[0];
                    DWRUtil.setValue("experience", dxeducate.experience);
                    DWRUtil.setValue("certificate", dxeducate.certificate);
                    DWRUtil.setValue("specialty", dxeducate.specialty);
                    DWRUtil.setValue("date", dxeducate.date);
                    DWRUtil.setValue("pastspecialty", dxeducate.pastspecialty);
                    DWRUtil.setValue("pastdate", dxeducate.pastdate);
                } else {
                    alert(data.message);
                }
            } else {
//                alert(data.message);
            }
        }

        function setPageValueArchivement(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var dxarchivement = data.resultList[0];
                    DWRUtil.setValue("ability", dxarchivement.ability);
                    DWRUtil.setValue("type", dxarchivement.type);
                    DWRUtil.setValue("compet", dxarchivement.compet);
                    DWRUtil.setValue("result", dxarchivement.result);
                    DWRUtil.setValue("archieve", dxarchivement.archieve);
                    DWRUtil.setValue("performance", dxarchivement.performance);
                    DWRUtil.setValue("patent", dxarchivement.patent);
                    DWRUtil.setValue("compet", dxarchivement.compet);
                    DWRUtil.setValue("result", dxarchivement.result);
                } else {
                    alert(data.message);
                }
            } else {
//                alert(data.message);
            }
        }

        var randomScalingFactor = function () {
            return Math.round(Math.random() * 100);
        };

        var color = Chart.helpers.color;
        var config = {
            type: 'radar',
            data: {
                labels: ['学历', '年龄', '培训经历', ['获奖情况', '重大贡献'], '当前工作年限', '人才等级'],
                datasets: [{
                    label: '能力值',
                    backgroundColor: color(window.chartColors.orange).alpha(0.2).rgbString(),
                    borderColor: window.chartColors.orange,
                    pointBackgroundColor: window.chartColors.orange
                }]
            },
            options: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: '个人能力雷达图'
                },
                scale: {
                    ticks: {
                        beginAtZero: true
                    }
                }
            }
        };
    </script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp; 显示个人信息相关信息！</label></div>
<div class="detailtitle">个人信息明细</div>
<table class="detailtable">
    <tr>
        <th>姓&nbsp;&nbsp;名</th>
        <td id="name" class="detailtabletd"></td>
        <th rowspan="10"></th>
        <td rowspan="10" style="text-align: match-parent">
            <div style="width:80%">
                <canvas id="canvas"></canvas>
            </div>
        </td>
    </tr>
    <tr>
        <th>职&nbsp;&nbsp;称</th>
        <td id="technicalpost" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>出生日期</th>
        <td id="birthday" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>民&nbsp;&nbsp;族</th>
        <td id="minority" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>参加工作时间</th>
        <td id="workdate" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>进入本单位时间</th>
        <td id="corpdate" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>全日制学历</th>
        <td id="fulltime" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>毕业院校</th>
        <td id="graduate" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>专&nbsp;&nbsp;业</th>
        <td id="major" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>最高学历</th>
        <td id="education" class="detailtabletd"></td>
    </tr>
</table>
<hr/>
<div class="detailtitle">培训信息</div>
<table class="detailtable">
    <tr>
        <th>培训经历</th>
        <td id="experience" class="detailtabletd"></td>
        <th>社会认证证书</th>
        <td id="certificate" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>现从事专业</th>
        <td id="specialty" class="detailtabletd"></td>
        <th>现从业时间</th>
        <td id="date" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>曾从事专业</th>
        <td id="pastspecialty" class="detailtabletd"></td>
        <th>曾从业时间</th>
        <td id="pastdate" class="detailtabletd"></td>
    </tr>
</table>
<hr/>
<div class="detailtitle">人才信息</div>
<table class="detailtable">
    <tr>
        <th>B/C级人才</th>
        <td id="ability" class="detailtabletd"></td>
        <th>类&nbsp;&nbsp;型</th>
        <td id="type" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>竞&nbsp;&nbsp;赛</th>
        <td id="compet" class="detailtabletd"></td>
        <th>成&nbsp;&nbsp;绩</th>
        <td id="result" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>获奖情况</th>
        <td id="archieve" class="detailtabletd"></td>
        <th>业&nbsp;&nbsp;绩</th>
        <td id="performance" class="detailtabletd"></td>
    </tr>
    <tr>
        <th>专利情况</th>
        <td id="patent" class="detailtabletd"></td>
    </tr>
</table>
</body>
</html>
