<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <script src="/web/js/fitness.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0;">
    <title>统计</title>
    <link href="/web/css/jsq_home.css?v=1.2" rel="stylesheet"/>
    <link href="/web/css/swiper-3.4.1.min.css" rel="stylesheet"/>
    <link href="/web/css/gh-buttons.css?v=1.0" rel="stylesheet"/>
    <script src="/web/js/echarts.min.js"></script>
</head>
<body>
<div style="background-color: rgb(255, 255, 255); position:absolute; width: 100%;height: 100%; font-family:Microsoft YaHei;">
    <div style="padding: 0.25rem 1rem 0.25rem;background-color: rgb(255, 255, 255);">
        <div style="font-size: 1.2rem; color: rgb(22, 155, 213); font-weight:500;text-align: center">
            流量统计
        </div>
    </div>
    <div style="position: absolute; width: 100%; top: 0.8rem;">
        <div style="width: 100%; text-align: center; height: 20px">
            <div style="position: relative; display: inline; margin: auto; color: rgb(255, 255, 255); font-size: 17px; padding: 6px 0px 0px;">
                <div style="position: absolute; top: 50%; margin-top: -18px; padding: 10px;"
                     onclick="history.back()">
                    <img src="/web/images/1204131232a69cebeb9d57a9cd.png" style="height: 40px; opacity: 0.5;">
                </div>
            </div>
        </div>
    </div>
    <div style="position: absolute; top: 10%; width: 100%;">
        <div style="width: 13rem; height: 20rem; margin: auto; position: relative; color: rgb(255, 255, 255); font-size: 12px; text-align: left">
            <div id="countChart" style="width:14rem; height:20rem;"></div>
        </div>
    </div>
    <div style="position: absolute; top: 14.5%; width: 100%;">
        <ul class="button-group">
            <li><a href="javascript:void(0);" class="button primary pill" onclick="dailystyle()" >日</a></li>
            <li><a href="javascript:void(0);" class="button pill" onclick="monthstyle()">月</a></li>
            <li><a href="javascript:void(0);" class="button pill" onclick="yearstyle()">年</a></li>
        </ul>
    </div>
</div>
<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var daycount = "${daycount}";
    var monthcount = "${monthcount}";
    var yearcount = "${yearcount}";
    var day = ("${day}").split(",");
    var js_total_day = ("${js_total_day}").split(",");
    var ys_size_day = ("${ys_size_day}").split(",");
    var mon = ("${month}").split(",");
    var js_total_month = ("${js_total_month}").split(",");
    var ys_size_month = ("${ys_size_month}").split(",");
    var year = ("${year}").split(",");
    var js_total_year = ("${js_total_year}").split(",");
    var ys_size_year = ("${ys_size_year}").split(",");
    var option_bar = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['废水量', '净水量']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: [],
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        borderColor: '#ccc',
                        type: 'dashed'
                    }
                },
//                splitArea: {
//                    "show": false
//                },
                axisLabel: {
                    interval: 0,
                    rotate: 45,
                    show: true,
                    textStyle: {
                        color: '#188df0',
                        fontFamily: "微软雅黑",
                        fontSize: 12
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: "净水量/L",
                position: 'right',
                axisTick: {
                    show: false
                },
                splitLine: {
                    lineStyle: {
                        borderColor: '#ccc',
                        type: 'dashed'
                    }
                },
                min: 0,
                max: 200,
                splitNumber:10,
                axisLabel: {
                    interval: 0,
                    show: true,
                    textStyle: {
                        color: '#188df0',
                        fontFamily: "微软雅黑",
                        fontSize: 12
                    }
                },
            }
        ],
        dataZoom: [
            {
                type: 'inside',
//                xAxisIndex: 0,
                fliterMode: 'filter',
                start: 0,
                end: 50,
                zoomLock: 'true',
            }
        ],
        series: [
            {
                name: '废水量',
                type: 'bar',
                stack: '水量统计',
                data: [],
                barWidth: 25,
                itemStyle: {
                    normal: {
//                        color: new echarts.graphic.LinearGradient(
//                                0, 0, 0, 1,
//                                [
//                                    {offset: 0, color: '#000000'},
//                                    {offset: 0.5, color: '#188df0'},
//                                    {offset: 1, color: '#188df0'}
//                                ]
//                        )
                          color: '#4575b4'
                    },
                    emphasis: {
//                        color: new echarts.graphic.LinearGradient(
//                                0, 0, 0, 1,
//                                [
//                                    {offset: 0, color: '#2378f7'},
//                                    {offset: 0.7, color: '#2378f7'},
//                                    {offset: 1, color: '#83bff6'}
//                                ]
//                        )
                          color: 'rgba(252,206,16,0.5)'
                    }
                },
            },
            {
                name: '净水量',
                type: 'bar',
                stack: '水量统计',
//                data: [js_total[0], js_total[1], js_total[2], js_total[3], js_total[4], js_total[5],js_total[6], js_total[7], js_total[8], js_total[9], js_total[10], js_total[11], js_total[12], js_total[13]],
                data: [],
                barWidth: 25,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#83bff6'},
                                    {offset: 0.5, color: '#188df0'},
                                    {offset: 1, color: '#188df0'}
                                ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: '#83bff6'}
                                ]
                        )
                    }
                },
            },

        ]
    };

    $(function () {
        dailystyle();
    });

    function dailystyle(){
        var jstotal = new Array();
        var yssize = new Array();
        var date = new Array();
        console.info("daycount :" + daycount + " ");
        daycount = daycount > 14 ? 14 : daycount;
        for (var i = 0 ; i < daycount; i++) {
            var tmpday = day[i].split(" ");
            var tmpdate = tmpday[0].split("-");
            if(!isNaN(js_total_day[i+1])) {
                jstotal[i] = js_total_day[i] - js_total_day[i+1];
                yssize[i] = ys_size_day[i] - ys_size_day[i+1];
            }else{
                jstotal[i] = parseInt(js_total_day[i]);
                yssize[i] = parseInt(ys_size_day[i]);
            }
            date[i] = tmpdate[1] + "/" + tmpdate[2];
            console.info("date" + i + ": " + date[i] + " js_total" + i + ": " + jstotal[i] + " ys_size" + i + " " + yssize[i]);
        }

        option_bar.series[0].data.length = 0;
        option_bar.series[1].data.length = 0;
        option_bar.xAxis[0].data.length = 0;
        option_bar.xAxis[0].axisLabel.rotate = 45;
        option_bar.series[0].barWidth = 20;
        option_bar.series[1].barWidth = 20;
        for(var i = 0; i < daycount; i++){
            option_bar.series[0].data.push(yssize[i]-jstotal[i]);
            option_bar.series[1].data.push(jstotal[i]);
            option_bar.xAxis[0].data.push(date[i]);
        }
        console.info(option_bar.series[0].data);
        console.info(option_bar.series[1].data);
        console.info(option_bar.xAxis[0].data);

        if(daycount < 8){
            option_bar.dataZoom[0].end = 100;
        }else{
            option_bar.dataZoom[0].end = 7*100/daycount;
        }
        option_bar.yAxis[0].max = 200;
        var mycountChart = echarts.init(document.getElementById('countChart'));
        mycountChart.setOption(option_bar);
    }

    function monthstyle(){
        var jstotal = new Array();
        var yssize = new Array();
        var month = new Array();
        console.info("monthcount :" + monthcount + " ");
        monthcount = monthcount > 6 ? 6 : monthcount;
        for (var i = 0 ; i < monthcount; i++) {
            var tmpmonth = mon[i].split(" ");
            tmpmonth = tmpmonth[0].split("-");
            console.info("tmpmonth is " + tmpmonth);
            if(!isNaN(js_total_month[i+1])) {
                jstotal[i] = js_total_month[i] - js_total_month[i+1];
                yssize[i] = ys_size_month[i] - ys_size_month[i+1];
            }else{
                jstotal[i] = parseInt(js_total_month[i]);
                yssize[i] = parseInt(ys_size_month[i]);
            }
            month[i] = tmpmonth[1] + "月";
            console.info(month[i] + " js_total_month" + i + ": " + jstotal[i] + " ys_size_month" + i + " " + yssize[i]);
        }

//        option_bar.series[0].data.splice(0,option_bar.series[0].data.length);
        option_bar.series[0].data.length = 0;
        option_bar.series[1].data.length = 0;
        option_bar.xAxis[0].data.length = 0;
        option_bar.xAxis[0].axisLabel.rotate = 0;
        option_bar.series[0].barWidth = 30;
        option_bar.series[1].barWidth = 30;
        for(var i = 0; i < monthcount; i++){
            option_bar.series[0].data.push(yssize[i]-jstotal[i]);
            option_bar.series[1].data.push(jstotal[i]);
            option_bar.xAxis[0].data.push(month[i]);
        }
        console.info(option_bar.series[0].data);
        console.info(option_bar.series[1].data);
        console.info(option_bar.xAxis[0].data);

        option_bar.dataZoom[0].end = 100;
        option_bar.yAxis[0].max = 2000;
        var mycountChart = echarts.init(document.getElementById('countChart'));
        mycountChart.setOption(option_bar);

    }

    function yearstyle(){
        var jstotal = new Array();
        var yssize = new Array();
        var yearcopy = new Array();
        console.info("yearcount :" + yearcount + " ");
        yearcount = yearcount > 3 ? 3 : yearcount;
        for (var i = 0 ; i < yearcount; i++) {
            var tmpyear = year[i].split(" ");
            var tmpyear = tmpyear[0].split("-");
            console.info("tmpyear is " + tmpyear);
            if(!isNaN(js_total_year[i+1])) {
                jstotal[i] = js_total_year[i] - js_total_year[i+1];
                yssize[i] = ys_size_year[i] - ys_size_year[i+1];
            }else{
                jstotal[i] = parseInt(js_total_year[i]);
                yssize[i] = parseInt(ys_size_year[i]);
            }
            yearcopy[i] = tmpyear[0] + "年";
            console.info(yearcopy[i] + " js_total_year" + i + ": " + jstotal[i] + " ys_size_year" + i + " " + yssize[i]);
        }

//        option_bar.series[0].data.splice(0,option_bar.series[0].data.length);
        option_bar.series[0].data.length = 0;
        option_bar.series[1].data.length = 0;
        option_bar.xAxis[0].data.length = 0;
        option_bar.xAxis[0].axisLabel.rotate = 0;
        option_bar.series[0].barWidth = 50;
        option_bar.series[1].barWidth = 50;
        for(var i = 0; i < yearcount; i++){
            option_bar.series[0].data.push(yssize[i]-jstotal[i]);
            option_bar.series[1].data.push(jstotal[i]);
            option_bar.xAxis[0].data.push(yearcopy[i]);
        }
        console.info(option_bar.series[0].data);
        console.info(option_bar.series[1].data);
        console.info(option_bar.xAxis[0].data);

        option_bar.dataZoom[0].end = 100;
        option_bar.yAxis[0].max = 4000;
        var mycountChart = echarts.init(document.getElementById('countChart'));
        mycountChart.setOption(option_bar);

    }
</script>
</body>
</html>
