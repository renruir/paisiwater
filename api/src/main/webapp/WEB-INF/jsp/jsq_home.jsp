<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>

<html>
<head>
    <script src="/web/js/fitness.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <title>创维智能净水器</title>

    <link rel="shortcut icon" href="/web/images/favicon.ico" type="image/x-icon"/>
    <link href="/web/css/jsq_home.css?v=1.1" rel="stylesheet"/>
    <link href="/web/css/swiper-3.4.1.min.css" rel="stylesheet"/>
    <link href="/web/css/weui.min.css" rel="stylesheet"/>
    <style>
        html {
            height: 100%;
            width: 100%;
            font-family: 'Heiti SC', 'Microsoft YaHei';
            font-size: 20px;
            outline: 0;
            -webkit-text-size-adjust: none;
        }
    </style>
</head>


<body id="main_body" style="display: none">
<div id="wx_logo" style="margin:0 auto;display:none;">
    <img src="/web/images/wx_logo.png"/>
</div>

<div class="swiper-container" id="page_all" style="width: 100%; height: 100%;">
    <div class="swiper-wrapper">
        <div id="normal_page" class="jsqHomeBackground swiper-slide">
            <div style="position: relative; width: 100%;">
                <div style="position: relative; padding: 0.75rem 0; width: 100%;">
                    <div style="width: 9rem; height: 9rem; margin: auto; position: relative; color: rgb(255, 255, 255); font-size: 12px;">
                        <div style="position: absolute; top: 14%; font-size: 0.6rem; font-family: 'Microsoft YaHei'; text-align: center; width: 100%; color: rgb(255, 255, 255); z-index: 9999">
                            纯净水TDS
                        </div>
                        <div id="TDS_value" style="font-family: 'Microsoft YaHei'">
                            ---
                        </div>
                        <div id="loading_TDS"
                             style="position: absolute; bottom: 18%; font-size: 1.1em; line-height: 1.1em; text-align: center; width: 100%; color: rgb(255, 255, 255); z-index: 9999">
                            <span>loading<br>正在获取TDS值</span>
                        </div>
                        <div id="wave" class="wave">
                        </div>
                    </div>
                </div>

                <div style="position: relative; width: 100%; top: 10%; text-align: center" ;>
                    <span style="font-size: 0.8rem; color: rgb(47,173,200);font-family: 'Microsoft YaHei'">当前状态</span>
                    <span id="current_state"
                          style="font-size: 1.5rem; color: rgb(47,173,245); font-family: 'Microsoft YaHei'; font-weight: bold">未知</span>
                </div>

            </div>

            <div style=" position: absolute; bottom: 20%; width: 100%;">
                <div style="width: 50%; float: left; text-align: center; padding: 10px 0px 6px 0; border-right: 1px solid rgb(47,173,245);">
                    <div style="position: relative; display: inline-block; margin: auto;">
                        <div style="float: left; padding-right: 10px;"><img
                                src="/web/images/water_tap_icon.png"></div>
                        <div style="float: left;">
                            <div style="color: rgb(47,173,245); font-size: 0.7rem; font-family:'Microsoft YaHei UI Light';font-weight: bold;line-height: 17px; text-align: left;">
                                自来水水质
                            </div>
                            <div style="color: rgb(255, 255, 255); text-align: left">
                            <span id="source_TDS_value"
                                  style="color:rgb(230,0,18); font-size: 0.9rem;font-weight: bold;">---</span>
                                <span style="font-size: 0.6rem; color: rgb(47,173,245);">TDS</span></div>
                        </div>
                    </div>
                </div>
                <div style="width: 50%; margin-left: 50%; text-align: center; padding: 10px 0px 6px;">
                    <div style="position: relative; display: inline-block; margin: auto;">
                        <div style="float: left; padding-right: 10px;margin-top: -5px">
                            <img src="/web/images/thermometer_icon.png"></div>
                        <div style="float: left;">
                            <div style="color: rgb(47,173,245); font-size: 0.7rem; font-family:'Microsoft YaHei UI Light';font-weight: bold;line-height: 17px; text-align: left;">
                                自来水水温
                            </div>
                            <div style="color: rgb(255, 255, 255); text-align: left">
                        <span id="source_water_temperature"
                              style="color:rgb(230,0,18);font-size: 0.9rem;font-weight: bold;">---</span>
                                <span style="font-size: 0.6rem; color: rgb(47,173,245);">℃</span></div>
                        </div>
                    </div>
                </div>
            </div>

            <div style=" position: fixed; bottom: 5%; width: 100%; text-align: center; margin: 0 auto">
                <div id="filter_rinse_button" style="width: 50%; text-align: center; float: left">
                    <a href="javascript:;" class="weui-btn weui-btn_plain-primary"
                       onclick="showDialog('确定要进行滤芯冲洗吗？', COMMAND_RINSE)"
                       style="width: 50%;margin:auto; color:rgb(47,173,245);border: 2px solid rgb(47,173,245);
                       font-size: 0.6rem; font-family: 'Microsoft YaHei UI'">滤芯冲洗</a>
                </div>
                <div id="UV_button" style="width: 50%; text-align: center; float: right">
                    <a href="javascript:;" class="weui-btn weui-btn_plain-primary"
                       onclick="showDialog('确定进行UV灯杀菌吗？', COMMAND_UV)"
                       style="width: 50%;margin: auto; color:rgb(47,173,245);border: 2px solid rgb(47,173,245);
                       font-size: 0.6rem; font-family: 'Microsoft YaHei UI'">UV杀菌</a>
                </div>
            </div>
        </div>

        <div class="swiper-slide"
             style="background-color: rgb(255, 255, 255); position:absolute;top: 100%; width: 100%;height: 100%; font-family:Microsoft YaHei;">
            <div style="padding: 0.25rem 1rem 0.25rem;background-color: rgb(255, 255, 255);">
                <div style="font-size: 1.2rem; color: rgb(22, 155, 213); font-weight:500;text-align: center">
                    滤芯寿命
                </div>
                <div>
                    <div class="blockleft" onclick="filter(0);">
                        <div style="margin: 0.3rem 0rem; display:inline-block;">
                            <span id="span1"></span><br/>
                            <span id="first_filter_surplus">---</span><br/>
                            <span style="font-size: 15px">剩余百分比</span><br/>
                        </div>
                    </div>
                    <div class="blockright" onclick="filter(1)">
                        <div style="margin: 0.3rem 0rem; display:inline-block;">
                            <span id="span2"></span><br/>
                            <span id="second_filter_surplus">---</span><br/>
                            <span style="font-size: 15px">剩余百分比</span><br/>
                        </div>
                    </div>
                    <div class="blockleft" onclick="filter(2);">
                        <div style="margin: 0.3rem 0rem; display:inline-block;">
                            <span id="span3"></span><br/>
                            <span id="third_filter_surplus">---</span><br/>
                            <span style="font-size: 15px">剩余百分比</span><br/>
                        </div>
                    </div>
                    <div class="blockright" onclick="filter(3)">
                        <div style="margin: 0.3rem 0rem; display:inline-block;">
                            <span id="span4"></span><br/>
                            <span id="forth_filter_surplus">---</span><br/>
                            <span style="font-size: 15px">剩余百分比</span><br/>
                        </div>
                    </div>
                </div>

                <div style="margin-left: 1rem;color: rgb(22, 155, 213);">
                    <ul>设备信息
                        <li style="font-size: 0.7rem;"><a>型号:</a> <span id="device_model_text">CR400D1</span></li>
                        <li style="font-size: 0.7rem;"><a>序列号：</a> <span id="device_serial_number">1234567</span></li>
                        <li style="font-size: 0.7rem;"><a>硬件版本：</a> <span id="device_hardware_version">2015-08-12</span>
                        </li>
                    </ul>
                </div>

            </div>

            <div id="watertotal" class="total">
                <div style="overflow: hidden; height: 7rem;" onclick="countChart(deviceId)">
                    <div style="height: 3.5rem;"><img src="/web/images/cumulative_net_water.png"
                                                      style="height: 3.5rem;">
                    </div>
                    <div style="font-size:14px">
                        <span>累计净水量</span></br>
                        <span id="js_total">---</span>
                        <span>L</span>
                    </div>
                </div>
            </div>
            <div id="watermap" ; class="watermap" ; display="none">
                <div style="overflow: hidden; height: 6rem;" onclick="waterShow()">
                    <div style="height: 3.5rem;"><img src="/web/images/water_map.png" style="height: 3.5rem;"></div>
                    <div style="font-size:14px">
                        <span>全国水图</span>
                    </div>
                </div>
            </div>
        </div>
        <div style="background-color: rgb(255, 255, 255);position: absolute;top:100%;width: 100%;height: 100%; font-family:Microsoft YaHei; display: none;"
             id="filterdisplay">
            <div style="padding: 0.25rem 1rem 0.25rem;background-color: rgb(255, 255, 255);">
                <div style="font-size: 1.2rem; color: rgb(22, 155, 213); font-weight:500;text-align: center">
                    滤芯用量
                </div>
            </div>
            <div style="position: absolute; width: 100%; top: 0.8rem;">
                <div style="width: 100%; text-align: center; height: 20px">
                    <div style="position: relative; display: inline; margin: auto; color: rgb(255, 255, 255); font-size: 17px; padding: 6px 0px 0px;">
                        <div style="position: absolute; top: 50%; margin-top: -18px; padding: 10px;" id="return"
                             onclick="backoff()">
                            <img src="/web/images/1204131232a69cebeb9d57a9cd.png" style="height: 40px; opacity: 0.5;">
                        </div>
                    </div>
                </div>
            </div>
            <div style="position: absolute; top: 10%; width: 100%;">
                <div style="width: 12rem; height: 12rem; margin: auto; position: relative; color: rgb(255, 255, 255); font-size: 12px; text-align: center">
                    <div id="filterChart" style="width:15rem; height:12rem;"></div>
                </div>
            </div>
            <div style="position:absolute; top :50%; width:100%;height:15%;text-align: center;">
                <div style="position:relative;width:300px;height:100%;margin: auto;">
                    <ul class="func">
                        <li>
                            <input onClick="filter_surplus(0)" id="filter_surplus1" class="radio" value="0"
                                   name="filter_surplus" type="radio"/>
                            <label for="filter_surplus1" class="trigger" id="label1"></label>
                        </li>
                        <li>
                            <input onClick="filter_surplus(1)" id="filter_surplus2" class="radio" value="1"
                                   name="filter_surplus" type="radio"/>
                            <label for="filter_surplus2" class="trigger" id="label2"></label>
                        </li>
                        <li>
                            <input onClick="filter_surplus(2)" id="filter_surplus3" class="radio" value="2"
                                   name="filter_surplus" type="radio"/>
                            <label for="filter_surplus3" class="trigger" id="label3"></label>
                        </li>
                        <li>
                            <input onClick="filter_surplus(3)" id="filter_surplus4" class="radio" value="3"
                                   name="filter_surplus" type="radio"/>
                            <label for="filter_surplus4" class="trigger" id="label4"></label>
                        </li>
                    </ul>
                </div>
            </div>
            <div style="position:absolute; top:70%;width:100%;height:120px;text-align: center;">
                <div style="position:relative;width:80%;height:1.5rem;margin: auto;">
                    <div style="font-size: 18px; color: rgb(22, 155, 213); width: 50%;  float: left; margin-left: -2px;border-right:rgb(22, 155, 213) 1px solid; position: relative;text-align: center;">
                        <label id="chosenfilter"></label>
                    </div>
                    <div style="font-size: 18px; color: rgb(22, 155, 213); width: 50%; float: right; position: relative;text-align: center;">
                        <label id="chosenfilter_surplus"></label>
                    </div>
                </div>
                <div style="position:relative;width:80%;height:1.5rem;margin: auto;">
                    <div style="font-size: 12px; color: rgb(22, 155, 213); width: 100%; position: relative;text-align: left; word-break:break-all;">
                        <label><span>第</span><span id="littlenumber"></span><span>级</span>
                            <span id="littlefilter"></span>
                            <span id="littlefilterintroduce"></span>
                        </label>
                    </div>
                </div>
            </div>

            <div style="position:absolute; top :85%; width:100%;">
                <div style="width: 50%;float: left; text-align: center;">
                    <a href="javascript:;" class="weui-btn weui-btn_plain-primary"
                       onclick="showDialog('确定要进行滤芯复位吗？', COMMAND_FILTER_RESET)"
                       style="width: 50%;margin:auto; color:rgb(47,173,245);border: 2px solid rgb(47,173,245);
                       font-size: 0.6rem; font-family: 'Microsoft YaHei UI'">滤芯复位</a>
                </div>
                <div style="width: 50%;margin-left: 50%; text-align: center; ">
                    <a href="javascript:;" class="weui-btn weui-btn_plain-primary" onclick="buy()"
                       style="width: 50%;margin: auto; color:rgb(47,173,245);border: 2px solid rgb(47,173,245);
                       font-size: 0.6rem; font-family: 'Microsoft YaHei UI'">滤芯购买</a>
                </div>
            </div>
        </div>
    </div>
    <div class="swiper-pagination"></div>
</div>

<div id="resin_page" style="width: 100%; height: 100%;display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog" style="max-width: 75%">
        <div class="weui-dialog__hd" style="padding: 1rem;">
            <strong id="rinseState" class="weui-dialog__title word-font-family"
                    style="font-size: 1rem">正在冲洗</strong>
        </div>
        <div class="weui-progress">
            <div class="weui-progress__bar">
                <div class="weui-progress__inner-bar js_progress" style="width: 0%;"></div>
            </div>
        </div>
        <div class="weui-dialog__ft" onclick="rinseCompleteButton()">
            <a href="javascript:" id="rinseCompletedBtn"
               class="weui-dialog__btn weui-dialog__btn_primary word-font-family"
               style="font-size: 0.8rem;">请稍后</a>
        </div>
    </div>
</div>

<div id="error-page" class="jsqHomeBackground" style="position: relative; display: block;">
    <div style="position: relative; width: 100%; height: 100%;">
        <div style="position: relative; top: 1rem; width: 100%; margin: auto;text-align: center;;;">
            <img src="/web/images/error_icon.png" style="width: 70%;">
        </div>
        <div style="text-align: center;position: relative;top: 1rem;">
            <span style="text-align: center;color: rgb(47,173,245);font-size: 1rem;font-family: 'Microsoft YaHei'">噢不，我怎么了，快来检查我</span>
        </div>
        <div style="text-align: center;position: relative;top: 2rem;">
                <span id="fault_detail_text"
                      style="text-align: center;color: rgb(230,0,18);font-size: 1.2rem;font-weight: bold; font-family: 'Microsoft YaHei UI'">进水电磁阀K1故障(01)</span>
        </div>
        <div style="position: relative; width: 100%; top: 3rem;">
            <a href="javascript:;" class="weui-btn weui-btn_plain-primary" onclick="callAfterSalesTel()"
               style="width: 40%;margin: auto; color:rgb(47,173,245);border: 2px solid rgb(47,173,245);
                   font-size: 0.6rem; font-family: 'Microsoft YaHei UI'; border-radius: 5px">拨打售后电话</a>
        </div>
    </div>
</div>

<!--加载动画-->
<div class="loading" style="display:none">
    <div class="model2">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>

<!--BEGIN toast-->
<div id="toast_s" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">升级成功</p>
    </div>
</div>

<div id="toast_f" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">升级失败</p>
    </div>
</div>
<!--end toast-->

<!-- loading toast -->
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">正在升级</p>
    </div>
</div>

<div id="loadingToast_reset" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">正在重启，请稍后。。。</p>
    </div>
</div>
<!--end loading toast -->


<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="/web/js/mqttws31.js" type="text/javascript"></script>
<script src="/web/js/protocol-parse.js?v=1.1" type="text/javascript"></script>
<script src="/web/js/filterfunction.js?v=1.1" type="text/javascript"></script>
<script src="/web/js/swiper-3.4.1.min.js" type="text/javascript"></script>
<script src="/web/js/echarts.min.js" type="text/javascript"></script>
<script src="/web/js/waveEffect.js" type="text/javascript"></script>
<script type="text/javascript">

    var deviceId = "${wxBindInfo.deviceId}";
    var openid = "${wxBindInfo.openid}";
    var host = "${serverInfo.host}";
    var port = "${serverInfo.port}";
    var deviceType = "${wxBindInfo.deviceType}";
    var model = "${deviceInfo.model}";
    var sn = "${deviceInfo.seqNum}";
    var hardwareVersion = "${deviceInfo.version}";
    var jstotal = "${jsTotal}";

    var updateVersionInfo = {
        "version": "${updateDeviceInfo.version}",
        "pkgSize": "${updateDeviceInfo.pkgSize}",
        "md5": "${updateDeviceInfo.md5}",
        "downloadUrl": "${updateDeviceInfo.downloadUrl}"
    };

    var perunused = new Array();
    var myfilterChart = echarts.init(document.getElementById('filterChart'));
    var currentfilterlevel;
    var old_level;
    var new_level;
    var option_pie = {
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['已用', '未用']
        },
        series: [
            {
                name: '滤芯用量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: [
                    {value: 0, name: '已用'},
                    {value: 100, name: '未用'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    normal: {
                        position: "inside"
                    }
                },
                color: ['rgb(47, 173, 245)', 'rgb(136,222,252)']
            }
        ]
    };

    //    for test start
    var deviceId = "bcec23711007a52e_1";
    var openid = "adbd_123";
    var host = "conn.doubimeizhi.com";
    var port = "12901";
    var deviceType = "1";
    var model = "CR400D1";
    var sn = "CR0123456789"
    var hardwareVersion = "SM-N100-VA1.8"
    var perunused = [90, 89, 77, 35];
    // for test end


    var oSwiper = new Swiper('#page_all', {
        direction: 'vertical',
        mousewheelControl: true,
        pagination: '.swiper-pagination',
        initialSlide: filterRank = -1 ? 0 : filterRank,
        longSwipesRatio: 0.1,
        width: window.innerWidth,
        height: window.innerHeight,
        onSetTransition: function (swiper) {
            swiper.enableMousewheelControl();
        }
    })

    $(function () {
        console.log("all start");
        $("#main_body").show();
        //根据产品型号动态获取滤芯页面
        setFilterDetails(model);
        wave.start();
        viewInit();
        if (deviceId == null || deviceId == "" || openid == null || openid == "") {
            alert("您还没有绑定净水器设备！");
        } else {
            mqttInit(host, port, openid);
        }
    });

    function viewInit() {
        console.log("model:" + model);
        try {
            $("#device_model_text").html(model);
            $("#device_hardware_version").html(hardwareVersion);
            $("#device_serial_number").html(sn);

            if (model == "CR400D1") {
                $("#UV_button").css("display", "none");
                $("#filter_rinse_button").css("width", "100%");
                $("#watertotal").css("display", "none");
                $("#watermap").css("display", "none");
            } else if (model == "CR600D1") {
                $("#watertotal").css("display", "none");
                $("#watermap").css("display", "none");
            } else {
                $("#watertotal").css("display", "none");
                $("#watermap").css("display", "none");
            }
        } catch (e) {
            console.log(e.message);
        }
    }


    // 初始化MQTT
    function mqttInit(host, port, openid) {
        console.log("mqtt init");
        client = new Paho.MQTT.Client(host, Number(port), openid);
        client.onConnectionLost = onConnectionLost
        client.onMessageArrived = onMessageArrived;
        client.connect({onSuccess: onConnect});
    }

    //连接成功
    function onConnect() {
        console.log("onConnect");
        client.subscribe("nodes/" + deviceId + "/status");
        client.subscribe("nodes/" + deviceId + "/alive");
        //查询设备当前状态
        queryDeviceStates(deviceId);
        if (updateVersionInfo.version != null && updateVersionInfo.version != "") {
            showDialog('检测到联网模块有新版本，是否升级？', 'update_wifi_model');
        }
    }

    //丢失连接
    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
            alert("与净水器已经断开连接，请检查重试！");
        }
    }

    //上报错误码
    function postErrorCode(deviceid, errorcode) {
        $.ajax({
            type: "POST",  //提交方式
            url: "/web/wechat/postErrorCode",//路径
            data: {
                deviceId: deviceid,
                errorcode: errorcode
            },//数据，这里使用的是Json格式进行传输
        });
    }

    //收到消息
    function onMessageArrived(msg) {
        var arr = msg.payloadBytes;
        var index = 8;//索引位置，前面8个是固定的头
        console.log("arrived msg length: " + arr.length);
        console.log("arrived msg: " + arr.toLocaleString());
        var $loadingToast = $('#loadingToast');
        var $loadingToast_reset = $('#loadingToast_reset');

        try {
            if ((arr.length == 3) && (arr[0] == 111) && (arr[1] == 102) && (arr[2] == 102)) {//off消息表示wifi模块离线
                $("#current_state").text(WORKING_STATE_OFFLINE);
            }

            if ((arr.length == 2) && (arr[0] == 111) && (arr[1] == 110)) {//on消息表示wifi模块上线
                $('#loadingToast_reset').remove();//模块上线，将升级后提示重启的界面隐藏
            }

            if ((arr[0] == 122) && (arr[1] == 122)) {//0x7A,0x7A
                if (arr[index] == 49) { //回复状态查询或者正常的状态上报信息x31
                    var jsqInfo = getDeviceStateInfo(arr);

                    if (!jsqInfo.is_fault) {
                        $("#normal_page").show();
                        $("#error-page").hide();

                        currentState = jsqInfo.working_state;
                        $("#TDS_value").html(jsqInfo.pure_water_TDS);
                        $("#source_TDS_value").html(jsqInfo.source_water_TDS);
                        $("#source_water_temperature").html(jsqInfo.pure_water_temperature);
                        $("#current_state").text(jsqInfo.working_state);
                        $("#loading_TDS").hide();
                        $("#first_filter_surplus").text(jsqInfo.filter[0] + "%");
                        perunused[0] = jsqInfo.filter[0];
                        $("#second_filter_surplus").text(jsqInfo.filter[1] + "%");
                        perunused[1] = jsqInfo.filter[1];
                        $("#third_filter_surplus").text(jsqInfo.filter[2] + "%");
                        perunused[2] = jsqInfo.filter[2];
                        $("#forth_filter_surplus").text(jsqInfo.filter[3] + "%");
                        perunused[3] = jsqInfo.filter[3];
                        if (jstotal != null && jstotal != "") {
                            $("#js_total").text(jstotal);
                        }

                        Rinse.rinse(jsqInfo.rinse_state);
                    } else {//故障
                        $("#normal_page").hide();
                        $("#fault_detail_text").html(jsqInfo.fault_code);
                        $("#error-page").show();
                        postErrorCode(deviceId, getFaultCodeNum());
                    }
                } else if (arr[index] == 50) {//处理滤芯复位后的状态数据0x32
                    var filterInfo = getFilterResetState(arr);
                    //process filter info
                    if (filterInfo.is_reset_success) {
                        resetresult(filterInfo.reset_series);
                    }

                } else if ((arr[index - 1] == 0xbb) && (arr[index] == 0xb1)) {//升级状态,命令码，二级命令码
                    if (arr[index + 1] == 0x20) {//开始升级
                        if ($loadingToast.css('display') != 'none') return;

                        $loadingToast.fadeIn(100);
                    } else if ((arr.length == 16) && (arr[index + 1] == 0x10)) {//升级成功,16为升级命令的固定长度
                        var $toast_s = $('#toast_s');
                        $loadingToast.fadeOut(100);

                        if ($toast_s.css('display') != 'none') return;

                        $toast_s.fadeIn(100);
                        updateVersionInfo.version = null;//升级成功后将该值置为null,解决进入二级页面返回后仍然会提示升级的问题

                        setTimeout(function () {
                            $toast_s.fadeOut(100);
                        }, 2000)

//                        setTimeout(function () {
//                            $toast_s.fadeOut(100);
//                            if ($loadingToast_reset.css('display') != 'none')
//                                return;
//                            $loadingToast_reset.fadeIn(100);
//                        }, 2000);
                    } else if ((arr.length == 16) && (arr[index + 1] == 0x11)) {//升级失败
                        var $toast_f = $('#toast_f');
                        if ($toast_f.css('display') != 'none') return;

                        $toast_f.fadeIn(100);
                        setTimeout(function () {
                            $toast_f.fadeOut(100);
                        }, 2000);
                    } else {
                        console.log("####invalid ota data#####");
                    }
                } else if (arr[index] == 0x33) {//UV灯回复
                    if (arr[index + 4] == 0x31) {//设置成功
                        alert("UV灯打开成功");
                    } else {//设置失败
                        alert("UV灯打开失败");
                    }
                } else {
                    console.log("####invalid data#####");
                }
            }
        } catch (e) {
            console.log(e.message);
        }

    }

    function updateWifiModel() {
//        var url = "http://dl.cdn.doubimeizhi.com/ota/SM-N100-VA1.4.bin";
//        wifiModelUpdate("SM-N100-VA1.4", "308456", url, "B0253178", deviceId);
        wifiModelUpdate(updateVersionInfo.version, updateVersionInfo.pkgSize, updateVersionInfo.downloadUrl, updateVersionInfo.md5, deviceId);
    }

    //UV 灯杀菌
    function UVLed() {
        console.log("UVLed");
        sendCommand2Devices(COMMAND_UV, deviceId);
    }


    function showDialog(title, action) {
        var parentdiv = $('<div></div>');
        parentdiv.attr('id', 'dialogView');
        parentdiv.addClass("js_dialog")
        parentdiv.css('font-family', 'Microsoft YaHei');
        var childMask = $('<div></div>');
        childMask.addClass("weui-mask");
        childMask.appendTo(parentdiv);
        var childDiv1 = $('<div></div>');
        childDiv1.addClass("weui-dialog weui-skin_android");
        var grandSonDiv1 = $('<div></div>');
        if (action == COMMAND_FILTER_RESET && (new_level == 2 || new_level == 3)) {
            console.info("111");
            grandSonDiv1.html(title);
            grandSonDiv1.html("提示：第三级和第四级滤芯将同时复位！确定进行滤芯复位吗？");
        } else {
            grandSonDiv1.html(title);
        }
        grandSonDiv1.addClass("weui-dialog__bd");
        grandSonDiv1.appendTo(childDiv1);
        var grandSonDiv2 = $('<div></div>');
        var btn1 = $('<a>取消</a>');
        var btn2 = $('<a>确定</a>');
        btn1.addClass("weui-dialog__btn weui-dialog__btn_default");
        btn2.addClass("weui-dialog__btn weui-dialog__btn_primary");
        btn1.attr('href', 'javascript:');
        btn2.attr('href', 'javascript:');
        btn1.attr('id', 'cancel-this');
        btn2.attr('id', 'confirm-ok');
        grandSonDiv2.css('padding', '0.5rem');
        grandSonDiv2.css('margin-left', '5rem');
        grandSonDiv2.css('font-size', '0.75rem');

        grandSonDiv2.append(btn1, btn2);
        grandSonDiv2.appendTo(childDiv1);
        childDiv1.appendTo(parentdiv);
        parentdiv.appendTo('body');
        $('#confirm-ok').click(function () {
            $('#dialogView').remove();
            $(this).parents('.js_dialog').fadeOut(200);
            if (action == COMMAND_RINSE) {
                Rinse.startRinse();
            } else if (action == COMMAND_UV) {
                UVLed();
            } else if (action == 'update_wifi_model') {
                updateWifiModel();
            } else if (action == COMMAND_FILTER_RESET) {
                reset();
            }
        })

        $('#cancel-this').click(function () {
            $('#dialogView').remove();
            $(this).parents('.js_dialog').fadeOut(200);
        })
    }

    function callAfterSalesTel() {
        window.location.href = "tel:95105555";
    }

    function filter(arg) {
        if (model == null || model == "")
            return;
        new_level = arg;
        currentfilterlevel = arg;
        var filtername = switchFilter(arg);
        var number = switchNumber(arg);
        var littlefilterintroduce = switchIntroduce(arg);

        option_pie.series[0].data = [
            {value: 100 - perunused[currentfilterlevel], name: '已用'},
            {value: perunused[currentfilterlevel], name: '未用'}
        ];
        myfilterChart.setOption(option_pie);

        var surplus = perunused[currentfilterlevel];
        $("#filterdisplay").show();
        $("#chosenfilter").html(filtername);
        $("#chosenfilter_surplus").html("剩余" + surplus + "%");
        $("#littlenumber").html(number);
        $("#littlefilter").html(filtername);
        $("#littlefilterintroduce").html(littlefilterintroduce);

        switch (currentfilterlevel) {
            case 0:
                $('input:radio[name=filter_surplus]')[0].checked = true;
                break;
            case 1:
                $('input:radio[name=filter_surplus]')[1].checked = true;
                break;
            case 2:
                $('input:radio[name=filter_surplus]')[2].checked = true;
                break;
            case 3:
                $('input:radio[name=filter_surplus]')[3].checked = true;
                break;
            case 4:
                break;
        }
        old_level = $('input:radio[name="filter_surplus"]:checked').val();
    }

    function filter_surplus(arg) {
        new_level = $('input:radio[name="filter_surplus"]:checked').val();
        currentfilterlevel = new_level;

        if (old_level == new_level) {
            //do nothing
            return;
        }
        option_pie.series[0].data = [
            {value: 100 - perunused[new_level], name: '已用'},
            {value: perunused[new_level], name: '未用'}
        ];
        myfilterChart.setOption(option_pie);

        var filtername = switchFilter(arg);
        var surplus = perunused[new_level];
        var number = switchNumber(new_level);
        var littlefilterintroduce = switchIntroduce(arg);
        $("#chosenfilter").html(filtername);
        $("#chosenfilter_surplus").html("剩余" + surplus + "%");
        $("#littlenumber").html(number);
        $("#littlefilter").html(filtername);
        $("#littlefilterintroduce").html(littlefilterintroduce);
        old_level = new_level;
    }

    function waterShow() {
        var hrefjsp = "/web/wechat/watermap.html";
        console.info(hrefjsp);
        window.location.href = hrefjsp;
    }

    function countChart(deviceId) {
        var hrefjsp = "/web/wechat/countchart.html?deviceId=" + deviceId;
//        var hrefjsp = "/web/wechat/countchart.html?deviceId=22" ;
        console.info(hrefjsp);
        window.location.href = hrefjsp;
    }

    function reset() {
        console.log("filter reset: " + new_level);
        sendCommand2Devices(COMMAND_FILTER_RESET, deviceId, new_level);
    }

    function resetresult(arg) {
        option_pie.series[0].data = [
            {value: 0, name: '已用'},
            {value: 100, name: '未用'}
        ];

        switch (parseInt(new_level)) {
            case 0:
                $("#first_filter_surplus").text("100%");
                break;
            case 1:
                $("#second_filter_surplus").text("100%");
                break;
            case 2:
                $("#third_filter_surplus").text("100%");
                $("#forth_filter_surplus").text("100%");
                perunused[3] = 100;
                break;
            case 3:
                $("#third_filter_surplus").text("100%");
                $("#forth_filter_surplus").text("100%");
                perunused[2] = 100;
                break;
        }

        $("#chosenfilter_surplus").html("剩余100%");
        perunused[new_level] = 100;
        myfilterChart.setOption(option_pie);
    }

    //交互等待动画显示/隐藏
    function view_wait() {
        $(".loading").show();
    }
    function view_hide() {
        $(".loading").hide();
    }

    function backoff() {
        $("#filterdisplay").hide();
    }


    function rinseCompleted() {
        clearTimeout(rinseProcess);
        $("#rinseState").html("本次冲洗已经完成");
        $("#rinseCompletedBtn").html("返回");
        $("div.weui-dialog__ft").attr("disabled", false);
    }

    function rinseCompleteButton() {
        if ($("#rinseCompletedBtn").text() == "完成") {
            resetRinseProgress();
            $("#resin_page").hide();
        } else if ($("#rinseCompletedBtn").text() == "返回") {
            $("#resin_page").hide();
        } else {
            return false;
        }
    }

    //冲洗
    function resinDeviceCommand() {
        console.log("resin");
        rinsingProgress();
        sendCommand2Devices(COMMAND_RINSE, deviceId);
        $("#resin_page").show();
        $("#rinseState").html("正在冲洗……");
        $("div.weui-dialog__ft").attr("disabled", "true");
    }

    var Rinse = (function () {

        var timeout = 0;
        var progress = 0;
        var rinseTimeOut;

        function rinse(rinseState) {
            if (rinseState == RINSE_START && $("#resin_page").is(":visible")) {
                startSuccess();
            } else if (rinseState == RINSING && $("#resin_page").is(":visible")) {
                rinseCompleted("本机已经在冲洗，请不要连续重复冲洗");
            } else if (rinseState == RINSE_COMPLETED && $("#resin_page").is(":visible")) {
                rinseCompleted("本次冲洗完成");
            }
        }

        function startRinse() {
            sendCommand2Devices(COMMAND_RINSE, deviceId);
            $("#resin_page").show();
            $("#rinseState").html("等待设备回应……");
            $("div.weui-dialog__ft").attr("disabled", true);
            rinseTimeOut = setTimeout(rinseTimeout, 10000);
        }

        function startSuccess() {
            if (typeof(rinseTimeout) != "undefined") {
                clearTimeout(rinseTimeOut)
            }
            rinsingProgress();
            $("#rinseState").html("正在冲洗……")
        }

        function rinsingProgress() {

            var $progress = $('.weui-progress__inner-bar');
            $("#rinseCompletedBtn").html("请稍后");
            $("#rinseCompletedBtn").attr("disabled", true);

            function uploading() {
                timeout++;
                if (timeout < 200) {
                    if (progress < 99) {
                        $progress.width(++progress % 100 + '%');
                    }
                    rinseProcess = setTimeout(uploading, 300);
                } else {
                    timeout = 0;
                    resetRinseProgress();
                    $("#rinseState").html("响应超时");
                    $("#rinseCompletedBtn").html("完成");
                    rinseTimeout();
                }
            }

            setTimeout(uploading, 50);
        }

        function resetRinseProgress() {
            timeout = 0;
            progress = 0;
            if (typeof (rinseProcess) != "undefined") {
                clearTimeout(rinseProcess);
            }
            var $progress = $('.weui-progress__inner-bar');
            $progress.width(0 + '%');
        }

        function rinseCompleted(displayArg) {
            if (typeof (rinseProcess) != "undefined") {
                clearTimeout(rinseProcess);
            }
            resetRinseProgress();
            $("#rinseState").html(displayArg);
            $("#rinseCompletedBtn").html("返回");
            $("div.weui-dialog__ft").attr("disabled", false);
        }


        function rinseTimeout() {
            console.log("rinseTimeout");
            $("#rinseState").html("响应超时");
            $("#rinseCompletedBtn").html("返回");
            $("div.weui-dialog__ft").attr("disabled", false);
        }

        return {rinse: rinse, startRinse: startRinse, rinseCompleted: rinseCompleted};
    }());


</script>
</body>
</html>