<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0;">
    <title>滤芯</title>

    <link href="/web/css/index-0c1d2aa4214889da91b26ddb09e2d682.css" rel="stylesheet"/>
    <script src="/web/js/echarts.common.min.js"></script>
</head>
<body>
<div style="background: -webkit-linear-gradient(top, rgb(62, 190, 255), rgb(34, 155, 236));position: absolute;width: 100%;height: 100%;">
    <div style="position: absolute; width: 100%; top: 0.8rem;">
        <div style="width: 100%; overflow: hidden; text-align: center;">
            <div style="position: relative; display: inline; margin: auto; color: rgb(255, 255, 255); font-size: 17px; padding: 6px 0px 0px;">
                <div style="position: absolute; top: 50%; margin-top: -18px; padding: 10px; left: -30px;">
                    <img src="/web/images/ec19375e7e802bea5408a269c38c01d1.png" style="height: 10px; opacity: 0.5;">
                </div>
                <div style="position: absolute; top: 50%; margin-top: -18px; padding: 10px; right: -32px;">
                    <img src="/web/images/0c7f5c1cd58752031d57ef73c7b3c63c.png" style="height: 10px; opacity: 0.5;">
                </div>
            </div>
        </div>
    </div>
    <div style="position: absolute; top: 13%; width: 100%;">
        <div style="width: 300px; height: 300px; margin: auto; position: relative; color: rgb(255, 255, 255); font-size: 12px;">
            <div id="filterChart" style="width:300px; height:300px;"></div>
        </div>
    </div>
    <div style="position:absolute; top :50%; width:100%;">
        <div style="width:100%; text-align: center; position: absolute; font-size: 32px; color: rgb(181, 230, 255);"
             id="filterPic">

        </div>
    </div>
    <div style="position:absolute; top :60%; width:100%;">
        <div style="width: 50%; float:left; padding: 6px 0px 6px; text-align: center; height:60px;">
            <input style="width:100px; height:100%; margin:0px 10px 0px 40px;font-size:20px;" type="button"
                   onclick="reset" value="重置滤芯">
        </div>
        <div style="width: 50%; float:left; padding: 6px 0px 6px; text-align: center; height:60px;">
            <input style="width:100px; height:100%; margin:0px 40px 0px 10px; font-size:20px" type="button"
                   onclick="buy" value="购买滤芯" href="https://weidian.com/?userid=1164650891&wfr=c&ifr=shopdetail">
        </div>
    </div>
</div>

<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="/web/js/filterfunction.js" type="text/javascript"></script>
<script src="/web/js/protocol-parse.js" type="text/javascript"></script>

<script type="text/javascript">
    var levelid = "${(filterdetails).levelid}";
    var filterid = "${(filterdetails.filterid)}";
    var perused = "${(filterdetails.perused)}";
    console.info(perused);
    option_style1 = {
        title : {
            text: '滤芯用量',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['已用','剩余']
        },
        series : [
            {
                name: '滤芯用量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:[
                    {value:perused, name:'已用',color:'rgba(220,220,220,0.8)'},
                    {value:100-perused, name:'剩余',color:'rgba(151,187,205,0.8)'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    option_style2 = {
        title : {
            text: '滤芯用量',
            subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:['rose3','rose5']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'半径模式',
                type:'pie',
                radius : [20, 110],
                center : ['50%', '50%'],
                roseType : 'radius',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[
                    {value:80, name:'已用'},
                    {value:20, name:'剩余'},
                ]
            }

        ]
    };
    var myfilterChart = echarts.init(document.getElementById('filterChart'));
    myfilterChart.setOption(option_style1);


    $(function () {
        console.info(levelid, filterid);
        $("#filterPic").html(getPicfromFilterid(filterid));
    });

    function reset(){
        console.log("filter reset");
        sendCommand2Devices(COMMAND_FILTER_RESET, deviceId);
    }
</script>
</body>
</html>