<%--
  Created by IntelliJ IDEA.
  User: renrui
  Date: 2016/12/12 0012
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品列表</title>
</head>

<style>
    .faqStyle {
        text-align: center;
        margin: 0 auto;
        font-size: 30px;
    }
</style>
<body>

<div style="margin: 0 auto; text-align: center; top: 30%;">
    <div>
        <a href="/web/wechat/FaQ/jsq_faq.html" class="faqStyle">创维净水壶</a>
    </div>

    <div>
        <a href="/web/wechat/FaQ/jsq_faq.html" class="faqStyle">创维智能净水器</a>
    </div>
</div>


<script language="javascript" type="text/javascript">
    function jsqFaQ() {
        var hrefjsp = "/web/wechat/FaQ/jsq_faq";
        console.info(hrefjsp);
        window.location.href = hrefjsp;
    }

</script>
</body>
</html>
