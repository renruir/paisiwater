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
    <title>纯水机系列</title>
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
            <div class="content-block-title">纯水机安装视频</div>
            <div class="list-block media-list">
                <ul>
                    <li>
                        <div class="item-content" onclick="jump1()">
                            <div class="item-media">
                                <img src="/web/images/product/RO150.png"
                                     width="44" style="width: 2.5rem; height: 2.5rem"></div>
                            <div class="item-inner" style="display: flex;">
                                <div class="item-title">双出水PS-RO150净水器安装视频</div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content" onclick="jump2()">
                            <div class="item-media">
                                <img src="/web/images/product/RO-500.jpg"
                                     width="44" style="width: 2.5rem; height: 2.5rem"></div>
                            <div class="item-inner" style="display: flex;">
                                <div class="item-title">RO-500安装视频</div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content" onclick="jump3()">
                            <div class="item-media">
                                <img src="/web/images/product/RO-5000.png"
                                     width="44" style="width: 2.5rem; height: 2.5rem"></div>
                            <div class="item-inner" style="display: flex;">
                                <div class="item-title">PS-X3即热型</div>
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

    function jump1() {
        window.location.href = "https://mp.weixin.qq.com/s/tnZ_tYK9Fybvre1nvT7Wzw"
    }

    function jump2() {
        window.location.href = "https://mp.weixin.qq.com/s/okxcS60OwxY6SBT87kOD7w"
    }

    function jump3() {
        window.location.href = "https://mp.weixin.qq.com/s/EwSxbP0E_Wn4AD4nCZzyuw"
    }

</script>
</body>
</html>
