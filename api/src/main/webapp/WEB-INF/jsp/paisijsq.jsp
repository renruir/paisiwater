<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/12/18 0018
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
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
    <link href="/web/css/psjsq.css" rel="stylesheet"/>
</head>
<body style="width: 100%;height: 100%">
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
            <a>/</a>
            <a>水温：<b style="font-size: 0.8rem">{{watertemp}}</b></a>
            <a>℃</a>
            </div>
    </div>

</div>
<div class="background_bottom">
    <h1>第二</h1>
</div>


<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="/web/js/mqttws31.js" type="text/javascript"></script>
<script src="/web/js/protocol-parse.js?v=1.1" type="text/javascript"></script>
<script src="/web/js/filterfunction.js?v=1.1" type="text/javascript"></script>
<script src="/web/js/swiper-3.4.1.min.js" type="text/javascript"></script>
<script src="/web/js/echarts.min.js" type="text/javascript"></script>
<script src="/web/js/waveEffect.js" type="text/javascript"></script>
<script src="/web/js/vue.min.js" type="text/javascript"></script>

<script type="text/javascript">
    var tdsvalue = new Vue({
        el: '#water-quality',
        data: {
            tds: '25',
            jsqstate: '待机',
            sourcetds:'136',
            watertemp:'13'
        }
    })


    $(function () {
        // wave.start();

    });
</script>

</body>
</html>
