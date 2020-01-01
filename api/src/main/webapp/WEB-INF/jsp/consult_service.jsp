<%--
  Created by IntelliJ IDEA.
  User: renrui
  Date: 2019/12/29 0029
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>咨询服务</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
</head>
<body>

<div>
    <img src="/web/images/shouhoutitle.jpg" width="100%">
    <div style="margin-left: 1rem;font-size: 0.5rem;color: #747474;padding: 0.5rem 0">
        售后或者产品相关问题欢迎拨打售后热线，直接点击下列号码即可拨打：
    </div>
</div>

<div style="background: #ffffff;">
    <div style="margin: 0 1rem;padding: 1rem 0">
        <span style="">客服热线: </span><a onclick="callCustomerTel()">400-139-1366</a>
    </div>
    <div style="margin: 0 1rem;padding: 1rem 0 ">
        <span>产品热线: </span><a onclick="callproductTel()">13705117955</a>
    </div>
</div>

<script type="text/javascript">
    function callCustomerTel() {
        window.location.href = "tel:4001391366";
    }

    function callproductTel() {
        window.location.href = "tel:13705117955";
    }
</script>
</body>

