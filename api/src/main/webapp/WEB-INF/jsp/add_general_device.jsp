<%--
  Created by IntelliJ IDEA.
  User: renrui
  Date: 2018/9/12 0012
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">

    <title>添加设备</title>

    <%--<link href="/web/css/materialize.min.css" rel="stylesheet"/>--%>
    <link href="/web/css/jsq_home_new.css" rel="stylesheet"/>
    <link href="/web/css/weui.min.css" rel="stylesheet"/>

    <style>

        .pre-filter-div {
            margin: 0 auto;
            margin-top: 2rem;
            width: 60%;
            height: 15rem;
            border: 1px solid #7d7c7c;
            border-top-left-radius: 0.5rem;
            border-top-right-radius: 0.5rem;
        }

        .pre-filter-image {
            height: 11rem;
            left: 0;
            right: 0;
            padding-top: 0.5rem;
            padding-bottom: 0.5rem;
            margin: 0 auto;
            align-items: center;
            display: flex;

        }

        .pre-filter-div div {
            text-align: center;
            color: #fff;
            background-color: #7d7c7c;
            padding: 0.5rem;
            height: 2rem;
        }


    </style>

</head>
<body>

<div class="pre-filter-div">
    <img src="../images/qianzhi/TJN-SF-DF35-prime.png" class="pre-filter-image">
    <div>前置过滤器</div>
</div>

<%--<div class="jsqHomeNewBackground">--%>
<form name="添加设备" action="/web/wechat/registerGeneralDevice" method="post" onsubmit="return checkInput()">

    <div style="margin-top: 4rem">

        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd" style="font-weight: 600;"><label class="weui-label">产品名称</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="device_byname" name="deviceByName" type="text" placeholder="请输入产品名称"
                           style="color: #a8a8a8;" value="前置过滤器">
                </div>
            </div>

            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd" style="font-weight: 600;">
                    <label for="" class="weui-label">产品型号</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="selectModel" id="model_selected_name" onchange="changeModel()"
                            style="display: block; color: #a8a8a8;">
                        <option selected="" value="TJN-SF-DF35(尊享版)">TJN-SF-DF35(尊享版)</option>
                        <option value="TJN-SF-DF25(旗舰版)">TJN-SF-DF25(旗舰版)</option>
                        <option value="TJN-SF-DF25(尊享版)">TJN-SF-DF25(尊享版)</option>
                        <option value="TJN-SF-DF65(尊享版)">TJN-SF-DF65(尊享版)</option>
                        <option value="TJN-SF-DF65(自动版)">TJN-SF-DF65(自动版)</option>
                        <option value="TJN-SF-DF55(尊享版)">TJN-SF-DF55(尊享版)</option>
                        <option value="TJN-PF-AS15">TJN-PF-AS15</option>
                        <option value="TJN-PF-AS16">TJN-PF-AS16</option>
                    </select>
                </div>
            </div>

            <div class="weui-cell">
                <div class="weui-cell__hd" style="font-weight: 600;">
                    <label for="" class="weui-label">安装日期</label></div>
                <div class="weui-cell__bd" style="color: #a8a8a8;">
                    <input class="weui-input" name="installDate" type="date" id="install_date" value="">
                </div>
                <img src="../images/edit_device_name.png" style="width: 1rem">
            </div>
        </div>
    </div>

    <input class="weui-btn weui-btn_primary" style="text-align: center; margin-top: 3rem; width: 60%"
           type="submit" id="submit_" value="提交"/>
</form>

</div>

<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
<script type="text/javascript">

    var productModel = "${productModel}";

    $(function () {
        console.log("productModel:" + productModel);
        if (productModel != "" && productModel != undefined && productModel != null) {
            $("#model_selected_name").val(productModel);
            modelImage(productModel);
        }
    });

    function modelImage(model) {
        console.log("aaa=" + model);
        if (model == "TJN-SF-DF35(尊享版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF35-prime.png');
        } else if (model == "TJN-SF-DF25(旗舰版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF25-flagship.png');
        } else if (model == "TJN-SF-DF25(尊享版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF25-prime.png');
        } else if (model == "TJN-SF-DF65(尊享版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF65-prime.png');
        } else if (model == "TJN-SF-DF65(自动版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF65-automatic.png');
        } else if (model == "TJN-SF-DF55(尊享版)") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-SF-DF55-prime.png');
        } else if (model == "TJN-PF-AS15") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-PF-AS15.png');
        } else if (model == "TJN-PF-AS16") {
            $('.pre-filter-image').attr('src', '../images/qianzhi/TJN-PF-AS16.png');
        }
    }

    function changeModel() {
        var selectModel = $('#model_selected_name').val();
        console.log("select model: " + selectModel);
        modelImage(selectModel);
    }

    function checkInput() {
        var selectModel = $('#model_selected_name').val();
        var deviceByName = $('#device_byname').val();
        var installDate = $('#install_date').val();
        console.log("install date: " + installDate);
        if (installDate == "" || installDate == undefined) {
            weui.alert("请输入安装日期!")
            return false;
        }

        if(!compareTime(installDate)){
            weui.alert("请不要选择将来的日期!")
            return false;
        }

        return true;
    }

    function compareTime(installDate) {
        var curTime = new Date();
        installDate = new Date(Date.parse(installDate));
        return (curTime >= installDate);
    }

    function addGeneralDevice() {
        var selected_model = $('#model_selected_name').val();
        var device_byname = $('#device_byname').val();
        var install_date = $('#install_date').val();
        console.log("aaaaa==" + selected_model);
        console.log("bbbb==" + device_byname);
        console.log("cccc==" + install_date);
        window.location.href = "/web/wechat/registerGeneralDevice?" +
            "selectModel=" + selected_model +
            "&deviceByName=" + device_byname +
            "&installDate=" + install_date;
    }

</script>

</body>
</html>
