<%--
  Created by IntelliJ IDEA.
  User: renrui
  Date: 2020/1/1 0001
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>前置过滤器</title>
    <meta name="description" content="MSUI: Build mobile apps with simple HTML, CSS, and JS components.">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">

    <!-- Google Web Fonts -->

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

    <script src="//hm.baidu.com/hm.js?ba76f8230db5f616edc89ce066670710"></script>
    <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
    <script type="text/javascript">
        //ga
    </script>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?ba76f8230db5f616edc89ce066670710";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>

</head>
<body style="">
<div class="page-group">
    <div id="page-simple-list" class="page page-current">
        <div class="content native-scroll">
            <div class="content-block-title">简单列表</div>
            <div class="list-block media-list">
                <ul>
                    <li>
                        <div class="item-content" onclick="jump()" >
                            <div class="item-media">
                                <img src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"
                                     width="44"></div>
                            <div class="item-inner" style="display: flex;">
                                <div class="item-title">PS-1安装视频</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

</div>

<div class="modal-overlay"></div>

<script src="/web/js/vue.min.js" type="text/javascript"></script>
<script>
    
    function jump() {
        window.location.href="https://mp.weixin.qq.com/s?__biz=MzIzMTc0OTcyMw==&tempkey=MTA0MV93VzBsSDQrdjFSS2M4N2s2a01jRHo5cm5NM1FhNUZ5VzBSWGFoVjlLUWhyVldHMFRWal9lRzJSNVhCRmg1MUhkay0tbFphQklnRXg0MUh2YlE3TFVyZkVlWVVKa3dPOWQ3bVVzVWw4X0ZDSGtqUHlvZTVJeDFabVdvMXh2RHVrMm1KeF9mS2x2aTV4V05Vdy0yRnp6b2Ixc09MVzBoT09MTWJBamp3fn4%3D&chksm=689e202e5fe9a9384dc3ac89c3ec4b4f651d87421cf73c1986665bec2389ef0e683080547b38#rd"
    }


</script>
</body>
</html>
