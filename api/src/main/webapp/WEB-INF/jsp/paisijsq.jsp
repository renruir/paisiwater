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
                <a>原水TDS：<b style="font-size: 0.8rem">{{sourcetds}}</b></a>
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
        <div>
            <p style="font-size: 0.6rem">{{filterdes}}</p>
            <p style="font-size: 0.6rem">{{filterlife}}</p>
        </div>

    </div>

    <div class="filter-button">
        <div class="filter-reset">
            <a class="weui-btn weui-btn_primary" @click="buyFilter()"
               style="background-color: #1C8DE0;border-radius: 1rem;" id="buyFilter">购买滤芯</a>
        </div>
        <div class="filter-reset" id="filter-reset">
            <a class="weui-btn weui-btn_primary" @click="resetFilter()"
               style="background-color: #1C8DE0;border-radius: 1rem;" id="filterReset">滤芯复位</a>
        </div>
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
<script src="/web/js/popup_dialgo.js?v=1.1"></script>

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

    var loading;

    var filterInfo = new Array();

    <c:forEach items="${filterInfo}" var="FilterInfo">
    var filter = {
        grade: "${FilterInfo.getRank()}",
        name: "${FilterInfo.getFilterName()}",
        life: "${FilterInfo.getFilterLife()}",
        detail: "${FilterInfo.getFilterDetail()}",
        other: "${FilterInfo.getOtherInfo()}",
    }
    filterInfo.push(filter);
    </c:forEach>

    var dianIntervalId = null;
    var dianCount = 1;
    var filterIndex = 0;

    function mqttInit(host, port, openid) {
        console.log("mqtt init");
        client = new Paho.MQTT.Client(host, Number(port), openid);
        client.onConnectionLost = onConnectionLost
        client.onMessageArrived = onMessageArrived;
        client.connect({
            userName: "admin",
            password: "password",
            useSSL: true,
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
                mainDiv.jsqstate = "离线"
            }

            if ((arr.length == 2) && (arr[0] == 111) && (arr[1] == 110)) {//on消息表示wifi模块上线
                setTimeout(function () {
                    queryDeviceStates(TYPE_WATER_PURIFIER, deviceId);
                }, 3000)
            }

            if ((arr[0] == 122) && (arr[1] == 122)) {//0x7A,0x7A
                if (arr[index] == 49 && arr[4] == 45) { //回复状态查询或者正常的状态上报信息x31
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

    function updataFilterAfterReset(arr) {
        var filterInfo = getFilterResetState(arr);
        //process filter info
        if (filterInfo.is_reset_success) {
            // resetresult(filterInfo.reset_series - 1, true);
            loading.hide(function () {
                console.log('重置滤芯成功');
            });
            weui.toast('重置滤芯成功', 3000);
        } else {
            // resetresult(filterInfo.reset_series - 1, false);
            loading.hide(function () {
                console.log('重置滤芯失败');
            });
            weui.alert('重置滤芯失败');
        }
    }

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
            console.log("========" + filters.length);
            for (var i = 0; i < filterInfo.length; i++) {
                mainDiv.updateProgress(i, jsqInfo.filter[i]);
            }
            updateResinStatus(jsqInfo.rinse_state);
            // Rinse.rinse(jsqInfo.rinse_state);
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
                tds: '0',
                jsqstate: '未知',
                sourcetds: '0',
                watertemp: '0',
                devicemodel: '未知',
                devicesn: '1234567890',
                filters: [
                    {name: "滤芯1", progress: 0},
                    {name: "滤芯2", progress: 0},
                    {name: "滤芯3", progress: 0},
                    {name: "滤芯4", progress: 0},
                    // {name: filterInfo[1].name, progress: 0},
                    // {name: filterInfo[2].name, progress: 0},
                    // {name: filterInfo[3].name, progress: 0},
                ],
            }
        },
        created: function () {
        },
        mounted: function () {
            console.log("deviceId: " + deviceId);
            this.devicemodel = model;
            this.devicesn = sn;
            if (deviceId == null || deviceId == "" || openid == null || openid == "") {
                alert("您还没有绑定净水器设备！");
            } else {
                mqttInit(host, port, openid);
                console.log("filterInfo:" + filterInfo.length);
                for (var i = 0; i < filterInfo.length; i++) {
                    this.filters.splice(i, filterInfo.length, {name: filterInfo[i].name, progress: 0});
                }
            }
        },
        methods: {
            filterdetail: function (index) {
                console.log(this.filters[index].name);
                filterIndex = index;
                filter.clickFilter = true;
                this.showMain = false;
                filter.filtername = this.filters[index].name;
                console.log(filterInfo[index].detail)
                filter.filterdes = filterInfo[index].detail;
                filter.filterlife = filterInfo[index].life;
                filter.updateDonut(perunused[index]);
            },
            updateProgress: function (index, progress) {
                console.log("new progress: " + progress)
                Vue.set(mainDiv.filters, index, {name: filterInfo[index].name, progress: progress})
            }
        },
        watch: {
            filters: function (val) {
                for (var i = 0; i < val.length; i++) {
                    perunused[i] = val[i].progress;
                }
            },
            jsqstate: function (val) {
                console.log("new value: " + val)
            }
        }
    })

    // mainDiv.$watch('filters', function () {
    //
    // })


    var filter = new Vue({
        el: "#filter-details-dialog",
        data: {
            clickFilter: false,
            filtername: '滤芯名',
            filterdes: '滤芯膜的描述',
            filterlife: '滤芯膜的寿命'
        },
        created() {

        },
        mounted() {
            this.updateDonut(100) // 初始化百分比
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

            filterReset() {
                console.log("reset");
                // $.MsgBox.Confirm(filterInfo[filterIndex].name, "");
                // $.MsgBox.InformWait("重置","dddd");
                resetFilter();
            },
            buyFilter() {
                window.location.href = "https://shop.m.jd.com/?shopId=36858"
            }
        }
    });

    function resetFilter() {
        // $.MsgBox.Confirm(filterInfo[filterIndex].name, startResetFilter);
        weui.confirm('重置滤芯会将滤芯的使用状态重置，并从头计数。此功能一般针对新购买滤芯替换后重置状态，重新计数。\n 确定要重置该滤芯吗？', function () {
            console.log('yes')
            reset()
        }, function () {
            console.log('no')
        });
    }

    function reset() {
        console.log("filterIndex:" + filterIndex);
        sendCommand2Devices(COMMAND_FILTER_RESET, deviceId, filterIndex);
        loading = weui.loading('重置中…');
        setTimeout(function () {
            loading.hide(function () {
                weui.alert('重置滤芯失败');
            });
        }, 30000);
    }

    function startResetFilter() {
        console.log("filterIndex:" + filterIndex);
        sendCommand2Devices(COMMAND_FILTER_RESET, deviceId, filterIndex);
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
            var title = "";
            var content = "正在重置中" + dian;
            $.MsgBox.InformWait(title, content);
            dianCount = dianCount + 1;
            if (dianCount > 6) {
                dianCount = 1;
            }
        }, 200);
        filterResetTimer = setTimeout(function () {
            if (dianIntervalId != null) {
                clearInterval(dianIntervalId);
            }
            var title = "";
            var content = "响应超时";
            $.MsgBox.InformFail(title, content);
        }, 10000);
    }

    function resineFilter() {
        var currentState = mainDiv.jsqstate;
        // currentState = "水满"
        console.log("currentState: " + currentState);
        if (currentState == '离线' || currentState == '未知') {
            weui.alert("设备状态" + currentState + ", 请稍后再试！");
        } else if (currentState == '冲洗') {
            if (loading.hidden == false) {
                loading = weui.loading("正在冲洗…")
            }
        } else {
            // Rinse.startRinse();
            sendCommand2Devices(COMMAND_RINSE, deviceId);
            loading = weui.loading("正在冲洗…")
            setTimeout(function () {
                loading.hide(function () {
                    weui.alert('状态未知，请检查设备');
                });
            }, 30000);

        }
    }

    function updateResinStatus(rinseState) {
        console.log("rinseState: " + rinseState);
        if (rinseState == RINSE_START) {
        } else if (rinseState == RINSING) {
        } else if (rinseState == RINSE_COMPLETED) {
            loading.hide()
            weui.toast('冲洗完成', 3000);
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
