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
    <title>咨询服务</title>
</head>
<body style="font-size: 2.5rem;
    margin: 0 auto;
    text-align: center;
    position: fixed;
    left: 0;
    right: 0;
    top: 3rem;">

<div>
    <span>客服热线: </span><a onclick="callCustomerTel()">400-139-1366</a>
</div>
<div style="margin-top: 2rem">
    <span>产品热线: </span><a onclick="callproductTel()">13705117955</a>
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

