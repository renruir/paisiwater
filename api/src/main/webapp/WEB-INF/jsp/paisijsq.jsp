<%--<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/18 0018
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
    <script src="/web/js/fitness.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <title>美国派斯智能净水器</title>

    <link rel="shortcut icon" href="/web/images/favicon.ico" type="image/x-icon"/>
    <link href="/web/css/jsq_home.css?v=1.1" rel="stylesheet"/>
    <link href="/web/css/swiper-3.4.1.min.css" rel="stylesheet"/>
    <link href="/web/css/weui.min.css" rel="stylesheet"/>
    <link href="/web/css/psjsq.css?v=1.0" rel="stylesheet"/>
    <link rel="stylesheet" href="/web/css/popup_dialog.css">
</head>
<body style="width: 100%;height: 100%;margin: 0;padding: 0;">
<div id="main-div" v-if="showMain">
    <div class="background_above">
        <div id="water-quality">
            <b class="tds-value">
                {{ tds }}
            </b>
            <a class="jsq-state">
                {{jsqstate}}
            </a>
            <a class="tds-string">
                TDS
            </a>
            <div class="water-qa">
                <a>原水TDS:<b style="font-size: 0.8rem">{{sourcetds}}</b></a>
                <a>&nbsp;&nbsp;/&nbsp;&nbsp;</a>
                <a>水温：<b style="font-size: 0.8rem">{{watertemp}}</b></a>
                <a>℃</a>
            </div>
        </div>

    </div>
    <div class="background_bottom">
        <div id="filters">
            <ul>
                <li @click="filterdetail(index)" v-for="(filter,index) in filters">
                    <div class="weui-progress" id="filter-life">
                        <img src="/web/images/circle-icon.png" class="filter-icon">
                        <a class="filter-text">{{filter.name}}</a>
                        <div class="weui-progress__bar progressContainer">
                            <div class="weui-progress__inner-bar js_progress progress"
                                 :style="{width:filter.progress+'%'}"></div>
                        </div>
                        <a href="javascript:;" class="weui-progress__opr">
                            <b class="filter-text">{{filter.progress}}%</b>
                        </a>
                    </div>
                </li>
            </ul>
        </div>

        <div class="btn-area" onclick="resineFilter()">
            <div class="filter-rinse">
                <a href="javascript:;" class="weui-btn weui-btn_primary"
                   style="background-color: #1C8DE0;border-radius: 1rem;" id="filterRinse">滤芯冲洗</a>
            </div>
        </div>

        <div id="device-info">
            <a>型号：<a>{{devicemodel}}</a></a>
            <a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 序列号：<a>{{devicesn}}</a></a>
        </div>

        <div class="factory-info">
            <a>Copyright ©1997-2017 南京派斯环保科技有限公司版权所有</a>
        </div>

    </div>
</div>

<div id="filter-details-dialog" class="dialog-mask" v-show="clickFilter">
    <img src="/web/images/back.png" id="back-main-div" @click="backMainDiv()">
    <div id="filter" class="circle-chart">
        <div class="donut-chart">
            <div id="section1" class="clip">
                <div class="item"></div>
            </div>
            <div id="section2" class="clip">
                <div class="item"></div>
            </div>
            <div id="section3" ref="section3" class="clip">
                <div class="item" ref="section3Item"></div>
            </div>
            <div class="center">
                <div class="filter-percent-text">
                    <span class="percent" ref="percent">0</span>%
                    <p class="name-text">
                        {{filtername}}
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div id="filter-description">
        <img src="/web/images/wicon.png" style="height:25px;padding: 0 10px">
        <span style="font-size: 0.6rem">{{filterdes}}</span>
    </div>
    <div class="filter-reset" id="filter-reset">
        <a class="weui-btn weui-btn_primary" @click="filterReset()"
           style="background-color: #1C8DE0;border-radius: 1rem;" id="filterReset">滤芯复位</a>
    </div>
</div>

</div>


<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="/web/js/mqttws31.js" type="text/javascript"></script>
<script src="/web/js/protocol-parse.js?v=1.0" type="text/javascript"></script>
<script src="/web/js/filterfunction.js?v=1.1" type="text/javascript"></script>
<script src="/web/js/swiper-3.4.1.min.js" type="text/javascript"></script>
<script src="/web/js/echarts.min.js" type="text/javascript"></script>
<script src="/web/js/waveEffect.js" type="text/javascript"></script>
<script src="/web/js/vue.min.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.1.1/weui.min.js" type="text/javascript"></script>
<script src="/web/js/arc_progress.js"></script>
<script src="/web/js/popup_dialgo.js?v=1.3"></script>

<script type="text/javascript">
    var deviceId = "${wxBindInfo.deviceId}";
    var openid = "${wxBindInfo.openid}";
    var host = "${serverInfo.host}";
    var port = "${serverInfo.port}";
    var deviceType = "${wxBindInfo.deviceType}";
    var model = "${deviceInfo.model}";
    var sn = "${deviceInfo.seqNum}";
    var hardwareVersion = "${deviceInfo.version}";
    var perunused = [0, 0, 0, 0, 0, 0];

    var filterInfo = new Array();

    <c:forEach items="${filterInfo}" var="FilterInfo">
    var filter = {
        grade: "${FilterInfo.getGrade()}",
        name: "${FilterInfo.getFilter_name()}",
        life: "${FilterInfo.getFilter_life()}",
        detail: "${FilterInfo.getFilter_detail()}",
        other: "${FilterInfo.getOther()}",
    }
    filterInfo.push(filter);
    </c:forEach>

    var dianIntervalId = null;
    var dianCount = 1;

    function mqttInit(host, port, openid) {
        console.log("mqtt init");
        client = new Paho.MQTT.Client(host, Number(port), openid);
        client.onConnectionLost = onConnectionLost
        client.onMessageArrived = onMessageArrived;
        client.connect({
            userName: "admin",
            password: "password",
            onSuccess: onConnect,
        });
    }

    //丢失连接
    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
            weui.alert("与净水器已经断开连接，请检查重试！");
        }
    }

    function onMessageArrived(msg) {
        console.log("new arrived")
        var arr = msg.payloadBytes;
        var index = 8;//索引位置，前面8个是固定的头
        console.log("arrived msg length: " + arr.length);
        console.log("arrived msg: " + bytes2StrForPrint(arr));
        try {
            if ((arr.length == 3) && (arr[0] == 111) && (arr[1] == 102) && (arr[2] == 102)) {//off消息表示wifi模块离线
                // if ((arr.length == 3)) {//off消息表示wifi模块离线
                // $(".home_status_content").css('width','3.41rem');
                // $("#current_state").css('width','0.67rem');
                // $("#current_state").text(WORKING_STATE_OFFLINE);
                mainDiv.jsqstate = "离线"
            }

            if ((arr.length == 2) && (arr[0] == 111) && (arr[1] == 110)) {//on消息表示wifi模块上线
                setTimeout(function () {
                    queryDeviceStates(TYPE_WATER_PURIFIER, deviceId);
                }, 3000)
            }

            if ((arr[0] == 122) && (arr[1] == 122)) {//0x7A,0x7A
                if (arr[index] == 49 && arr[4] == 45) { //回复状态查询或者正常的状态上报信息x31
                    console.log("****************");
                    var jsqInfo = getDeviceStateInfo(arr);
                    updateDevicesState(jsqInfo);
                } else if (arr[index] == 50 && arr[4] == 50) {//处理滤芯复位后的状态数据0x32
                    updataFilterAfterReset(arr);
                } else if ((arr[index - 1] == 0xbb) && (arr[index] == 0xb1)) {//升级状态,命令码，二级命令码
                    updateDeviceUpateState(arr);
                } else if (arr[index] == 0x33) {//UV灯回复
                    if (arr[index + 4] == 0x31) {//设置成功
                        alert("UV灯打开成功");
                    } else {//设置失败
                        alert("UV灯打开失败");
                    }
                } else {
                    console.log("####a mix package#####");
                    if (arr.length > 50) {
                        var dataString = bytes2Str(arr);
                        var d = dataString.split("7a7a");
                        for (var i = 0; i < d.length; i++) {
                            var temp = "7a7a" + d[i];
                            var data = str2Bytes(temp);
                            console.log("mix package data length = " + data.length);
                            console.log("mix package data = " + data.toLocaleString());
                            if (data[4] == 45) {
                                var jsqInfo = getDeviceStateInfo(arr);
                                updateDevicesState(jsqInfo);
                            } else if (data[4] == 50) {
                                updataFilterAfterReset(arr);
                            }
                        }
                    }
                }
            }
        } catch (e) {
            console.log(e.message);
        }
    }

    var isError = 0;

    function updateDevicesState(jsqInfo) {
        if (!jsqInfo.is_fault) {
            if (isError == 1) {
                $(".index_module_page").hide();
                $("#home_page_id").show();
            }
            isError = 0;
            currentState = jsqInfo.working_state;
            if (jsqInfo.pure_water_TDS >= 1000) {
                // currentState = 999;
                jsqInfo.pure_water_TDS = 999;
            }

            // if (jsqInfo.working_state == "水箱满") {
            //     $(".home_status_content").css('width', '3.64rem');
            //     $("#current_state").css('width', '0.9rem');
            // } else if (jsqInfo.working_state == "原水缺水") {
            //     $(".home_status_content").css('width', '3.84rem');
            //     $("#current_state").css('width', '1.1rem');
            // } else {
            //     $(".home_status_content").css('width', '3.41rem');
            //     $("#current_state").css('width', '0.67rem');
            // }

            // $("#TDS_value").html(jsqInfo.pure_water_TDS);
            // $("#source_TDS_value").html(jsqInfo.source_water_TDS);
            // $("#source_water_temperature").text(jsqInfo.pure_water_temperature + "℃");
            // $("#current_state").html(jsqInfo.working_state);
            mainDiv.tds = jsqInfo.pure_water_TDS;
            mainDiv.sourcetds = jsqInfo.source_water_TDS;
            mainDiv.watertemp = jsqInfo.pure_water_temperature;
            mainDiv.jsqstate = jsqInfo.working_state;
            console.log("========"+filters.length);
            for (var i = 0; i < filterInfo.length;i++) {
                mainDiv.updateProgress(i, jsqInfo.filter[i]);
            }
            Rinse.rinse(jsqInfo.rinse_state);
        } else {//故障
            isError = 1;
            $(".index_module_page").hide();
            $("#fault_detail_text").html(jsqInfo.fault_code);
            $("#error_page_id").show();
        }
    }

    //连接成功
    function onConnect() {
        console.log("Connect Success:" + deviceId);
        client.subscribe("nodes/" + deviceId + "/status");
        client.subscribe("nodes/" + deviceId + "/alive");
        //查询设备当前状态
        queryDeviceStates(TYPE_WATER_PURIFIER, deviceId);
    }

    var mainDiv = new Vue({
        el: "#main-div",
        data() {
            return {
                showMain: true,
                tds: '25',
                jsqstate: '待机',
                sourcetds: '136',
                watertemp: '13',
                devicemodel: 'PS-M20',
                devicesn: '1234567890',
                filters: [
                    {name: filterInfo[0].name, progress: 0},
                    {name: filterInfo[1].name, progress: 0},
                    {name: filterInfo[2].name, progress: 0},
                    {name: filterInfo[3].name, progress: 0},
                ],
            }
        },
        mounted: function () {
            this.devicemodel = model;
            this.devicesn = sn;
            mqttInit(host, port, deviceId)
        },
        methods: {
            filterdetail: function (index) {
                console.log(this.filters[index].name);
                filter.clickFilter = true;
                this.showMain = false;
                filter.filtername = this.filters[index].name;
                console.log(filterInfo[index].detail)
                filter.filterdes = filterInfo[index].detail;
                filter.updateDonut(95);
            },
            updateProgress: function (index, progress) {
                console.log("55555555: "+progress)
                Vue.set(mainDiv.filters, index, {name: filterInfo[index].name, progress: progress})
            }
        },
        watch: {
            filters: function (val) {
                for (var i = 0; i < val.length; i++) {
                    console.log("new name: " + val[i].name)
                    console.log("new progress: " + val[i].progress)
                    // this.filters[i].name = val[i].name;
                    // this.filters[i].progress = 65;
                }
            },
            jsqstate: function (val) {
                console.log("new value: " + val)
            }
        }
    })

    mainDiv.$watch('filters', function () {

    })

    // mainDiv.devicemodel = model;

    var filter = new Vue({
        el: "#filter-details-dialog",
        data: {
            clickFilter: false,
            filtername: '滤芯名',
            filterdes: '滤芯膜的描述'
        },
        created() {

        },
        mounted() {
            this.updateDonut(40) // 初始化百分比
        },
        methods: {
            updateDonut(percent) {
                // 圆形进度
                let offset = 0
                let $el = this.$refs.section3
                let $elItem = this.$refs.section3Item
                let $txt = this.$refs.percent
                if (percent < 50) {
                    offset = (360 / 100) * percent
                    $el.style.webkitTransform = $el.style.msTransform = $el.style.MozTransform = 'rotate(' + offset + 'deg)'
                    $elItem.style.webkitTransform = $elItem.style.msTransform = $elItem.style.MozTransform = 'rotate(' + (180 - offset) + 'deg)'
                    $elItem.style.backgroundColor = '#bfe2f9'
                } else {
                    offset = ((360 / 100) * percent) - 180
                    $el.style.webkitTransform = $elItem.style.msTransform = $el.style.MozTransform = 'rotate(180deg)'
                    $elItem.style.webkitTransform = $elItem.style.msTransform = $elItem.style.MozTransform = 'rotate(' + offset + 'deg)'
                    $elItem.style.backgroundColor = '#2270ca'
                }
                $txt.innerHTML = percent
            },

            backMainDiv() {
                console.log("back main div")
                filter.clickFilter = false;
                mainDiv.showMain = true;
            },

        }
    });

    function resineFilter() {
        var currentState = $("#current_state").text();
        if (currentState == '离线' || currentState == '未知') {
            weui.alert("设备状态" + currentState + ", 请稍后再试！");
        } else if (currentState == '冲洗') {
            weui.alert("设备正在冲洗！");
        } else {
            Rinse.startRinse();
        }
    }

    function createBasicDialog(nProgress, nIcon, iSuccess) {
        dianCount = 1;
        if (dianIntervalId != null) {
            clearInterval(dianIntervalId);
        }
        dianIntervalId = setInterval(function () {
            var dian = "";
            if (dianCount == 1) {
                dian = "·<span style=\"color: whitesmoke;\">·····</span>";
            } else if (dianCount == 2) {
                dian = "··<span style=\"color: whitesmoke;\">····</span>";
            } else if (dianCount == 3) {
                dian = "···<span style=\"color: whitesmoke;\">···</span>";
            } else if (dianCount == 4) {
                dian = "····<span style=\"color: whitesmoke;\">··</span>";
            } else if (dianCount == 5) {
                dian = "·····<span style=\"color: whitesmoke;\">·</span>";
            } else if (dianCount == 6) {
                dian = "······";
            }
            var title = "冲洗";
            var content = "正在冲洗中" + dian;
            $.MsgBox.InformWait(title, content);
            dianCount = dianCount + 1;
            if (dianCount > 6) {
                dianCount = 1;
            }
        }, 200);
    }

    var Rinse = (function () {
        var timeout = 0;
        var rinsingTimeout;

        function rinse(rinseState) {
            console.log("rinseState: " + rinseState);
            if (rinseState == RINSE_START) {
                startSuccess();
            } else if (rinseState == RINSING) {
            } else if (rinseState == RINSE_COMPLETED) {
                rinseCompleted("本次冲洗完成");
            }
        }

        function startRinse() {
            sendCommand2Devices(COMMAND_RINSE, deviceId);
            createBasicDialog(true, false, false);
            rinsingTimeout = setTimeout(rinseTimeout, 10000);
        }

        function startSuccess() {
            if (typeof (rinseTimeout) != "undefined") {
                clearTimeout(rinsingTimeout)
            }

            dianCount = 1;
            if (dianIntervalId != null) {
                clearInterval(dianIntervalId);
            }
            dianIntervalId = setInterval(function () {
                var dian = "";
                if (dianCount == 1) {
                    dian = "·<span style=\"color: whitesmoke;\">·····</span>";
                } else if (dianCount == 2) {
                    dian = "··<span style=\"color: whitesmoke;\">····</span>";
                } else if (dianCount == 3) {
                    dian = "···<span style=\"color: whitesmoke;\">···</span>";
                } else if (dianCount == 4) {
                    dian = "····<span style=\"color: whitesmoke;\">··</span>";
                } else if (dianCount == 5) {
                    dian = "·····<span style=\"color: whitesmoke;\">·</span>";
                } else if (dianCount == 6) {
                    dian = "······";
                }
                var title = "冲洗";
                var content = "正在冲洗中" + dian;
                $.MsgBox.InformWait(title, content);
                dianCount = dianCount + 1;
                if (dianCount > 6) {
                    dianCount = 1;
                }
            }, 200);
            rinsingTimeout = setTimeout(rinseTimeout, 60000);
        }

        function rinseCompleted(displayArg) {
            console.log("rinseCompleted......" + rinsingTimeout)
            if (typeof (rinsingTimeout) != "undefined") {
                console.log("rinseCompleted.3333333333")
                clearTimeout(rinsingTimeout)
            }
            if (dianIntervalId != null) {
                clearInterval(dianIntervalId);
            }
            var title = "冲洗";
            var content = "本次冲洗完成";
            $.MsgBox.InformSuccess(title, content);
        }


        function rinseTimeout() {
            if (dianIntervalId != null) {
                clearInterval(dianIntervalId);
            }
            var title = "冲洗";
            var content = "本次冲洗失败";
            $.MsgBox.InformFail(title, content);
        }

        return {rinse: rinse, startRinse: startRinse, rinseCompleted: rinseCompleted};
    }());

</script>

</body>
</html>
