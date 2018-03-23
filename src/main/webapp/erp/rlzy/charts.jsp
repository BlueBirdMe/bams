<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据统计</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrDxArchivementService.js"></script>
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
window.onload = function(){
    dwrDxArchivementService.getDxArchivementByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var dxarchivement = data.resultList[0];
            DWRUtil.setValue("name",dxarchivement.name);
            DWRUtil.setValue("empid",dxarchivement.empid);
            DWRUtil.setValue("ability",dxarchivement.ability);
            DWRUtil.setValue("type",dxarchivement.type);
            DWRUtil.setValue("archieve",dxarchivement.archieve);
            DWRUtil.setValue("performance",dxarchivement.performance);
            DWRUtil.setValue("patent",dxarchivement.patent);
            DWRUtil.setValue("compet",dxarchivement.compet);
            DWRUtil.setValue("result",dxarchivement.result);
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}

    <!--报表-->
var randomScalingFactor = function() {
    return Math.round(Math.random() * 100);
};

var color = Chart.helpers.color;
var config = {
    type: 'radar',
    data: {
        labels: [['Eating', 'Dinner'], ['Drinking', 'Water'], 'Sleeping', ['Designing', 'Graphics'], 'Coding', 'Cycling', 'Running'],
        datasets: [{
            label: 'My First dataset',
            backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
            borderColor: window.chartColors.red,
            pointBackgroundColor: window.chartColors.red,
            data: [
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor()
            ]
        }, {
            label: 'My Second dataset',
            backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
            borderColor: window.chartColors.blue,
            pointBackgroundColor: window.chartColors.blue,
            data: [
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor(),
                randomScalingFactor()
            ]
        }]
    },
    options: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Chart.js Radar Chart'
        },
        scale: {
            ticks: {
                beginAtZero: true
            }
        }
    }
};

window.onload = function() {
    window.myRadar = new Chart(document.getElementById('canvas'), config);
};

document.getElementById('randomizeData').addEventListener('click', function() {
    config.data.datasets.forEach(function(dataset) {
        dataset.data = dataset.data.map(function() {
            return randomScalingFactor();
        });
    });

    window.myRadar.update();
});

var colorNames = Object.keys(window.chartColors);
document.getElementById('addDataset').addEventListener('click', function() {
    var colorName = colorNames[config.data.datasets.length % colorNames.length];
    var newColor = window.chartColors[colorName];

    var newDataset = {
        label: 'Dataset ' + config.data.datasets.length,
        borderColor: newColor,
        backgroundColor: color(newColor).alpha(0.2).rgbString(),
        pointBorderColor: newColor,
        data: [],
    };

    for (var index = 0; index < config.data.labels.length; ++index) {
        newDataset.data.push(randomScalingFactor());
    }

    config.data.datasets.push(newDataset);
    window.myRadar.update();
});

document.getElementById('addData').addEventListener('click', function() {
    if (config.data.datasets.length > 0) {
        config.data.labels.push('dataset #' + config.data.labels.length);

        config.data.datasets.forEach(function(dataset) {
            dataset.data.push(randomScalingFactor());
        });

        window.myRadar.update();
    }
});

document.getElementById('removeDataset').addEventListener('click', function() {
    config.data.datasets.splice(0, 1);
    window.myRadar.update();
});

document.getElementById('removeData').addEventListener('click', function() {
    config.data.labels.pop(); // remove the label first

    config.data.datasets.forEach(function(dataset) {
        dataset.data.pop();
    });

    window.myRadar.update();
});
</script>
</head>
<body class="inputdetail">
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示认证信息相关信息！</label></div>
    <div class="detailtitle">认证信息明细</div>
    <table class="detailtable">
        <tr>
            <th>姓&nbsp;&nbsp;名</th>
            <td id="name" class="detailtabletd"></td>
            <th>工&nbsp;&nbsp;号</th>
            <td id="empid" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>B/C及人才</th>
            <td id="ability" class="detailtabletd"></td>
            <th>类&nbsp;&nbsp;型</th>
            <td id="type" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>获奖情况</th>
            <td id="archieve" class="detailtabletd"></td>
            <th>业&nbsp;&nbsp;绩</th>
            <td id="performance" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>竞&nbsp;&nbsp;赛</th>
            <td id="compet" class="detailtabletd"></td>
            <th>成&nbsp;&nbsp;绩</th>
            <td id="result" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>专利情况</th>
            <td id="patent" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
