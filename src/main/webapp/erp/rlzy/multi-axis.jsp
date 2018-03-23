<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>数据统计</title>
    <script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxUserinfoService.js"></script>
    <script src="../../js/rlzy/Chart.bundle.min.js"></script>
    <script src="../../js/rlzy/utils.js"></script>
    <style>
        canvas {
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
        }
    </style>
    <script type="text/javascript">
        var color = Chart.helpers.color;
        var barChartData = {
            datasets: [{
                label: '人员总数',
                backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                borderColor: window.chartColors.red,
                borderWidth: 1
            }, {
                label: '按年龄段人数',
                backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                borderColor: window.chartColors.blue,
                borderWidth: 1
            }, {
                label: '按部门人数',
                backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                borderColor: window.chartColors.green,
                borderWidth: 1
            }]
        };

        window.onload = function () {
            var ctx = document.getElementById('canvas').getContext('2d');
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: '员工人数统计'
                    }
                }
            });

            var s_age = document.getElementById('age_start').value;
            var e_age = document.getElementById('age_end').value;
            var deptName = document.getElementById('select_deptname').value;
            dwrDxUserinfoService.countUserByDept_Age(null, null, null, queryCallbackA);
            dwrDxUserinfoService.countUserByDept_Age(s_age, e_age, null, queryCallbackB);
            dwrDxUserinfoService.countUserByDept_Age(null, null, deptName, queryCallbackC);
            window.myBar.update();

            document.getElementById('update').addEventListener('click', function () {
                var s_age = document.getElementById('age_start').value;
                var e_age = document.getElementById('age_end').value;
                var deptName = document.getElementById('select_deptname').value;
                dwrDxUserinfoService.countUserByDept_Age(null, null, null, queryCallbackA);
                dwrDxUserinfoService.countUserByDept_Age(s_age, e_age, null, queryCallbackB);
                dwrDxUserinfoService.countUserByDept_Age(s_age, e_age, deptName, queryCallbackC);
            });
        };

        function queryCallbackA(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var labs = new Array();
                    var datas = new Array();
                    for (var i = 0; i < data.resultList.length; i++) {
                        var obj = data.resultList[i];
                        labs[i] = obj[0];
                        datas[i] = obj[1];
                    }
                    barChartData.labels = labs;
                    barChartData.datasets[0].data = datas;
//                    alert("111"+barChartData.datasets[0].data);
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }

        function queryCallbackB(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var labs = new Array();
                    var datas = new Array();
                    for (var i = 0; i < data.resultList.length; i++) {
                        var obj = data.resultList[i];
                        labs[i] = obj[0];
                        datas[i] = obj[1];
                    }
                    barChartData.datasets[1].data = datas;
//                    alert("222"+barChartData.datasets[1].data);
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }

        function queryCallbackC(data) {
            if (data.success == true) {
                if (data.resultList.length > 0) {
                    var labs = new Array();
                    var datas = new Array();
                    for (var i = 0; i < data.resultList.length; i++) {
                        var obj = data.resultList[i];
                        labs[i] = obj[0];
                        datas[i] = obj[1];
                    }
                    barChartData.datasets[2].data = datas;
//                    alert("333"+barChartData.datasets[0].data);
                    window.myBar.update();
                } else {
                    alert(data.message);
                }
            } else {
                alert(data.message);
            }
        }
    </script>
</head>
<body class="inputdetail">
<div class="requdivdetail"><label>查看帮助:&nbsp; 显示员工人数统计相关信息！</label></div>
<div class="detailtitle">员工人数统计</div>
部门:
<select id="select_deptname">
    <option></option>
    <option>采购物流管理部</option>
    <option>采购物流支撑中心</option>
    <option>运行维护部</option>
    <option>网络监控维护中心</option>
    <option>网络发展部</option>
    <option>无线网优化中心</option>
    <option>市场部</option>
    <option>党群工作部</option>
    <option>信息安全部</option>
    <option>企业信息化部</option>
    <option>人力资源部</option>
</select>
年龄段:
<input type="text" id="age_start" size="5" maxlength="3">
至
<input type="text" id="age_end" size="5" maxlength="3">
<button id="update">查询</button>
<div id="container" style="width: 75%;">
    <canvas id="canvas"></canvas>
</div>
</body>
</html>