<%--
  Created by IntelliJ IDEA.
  User: songxin
  Date: 2017/2/15
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <style>
        p{
            font-size:20px;
        }

        #btn1 {
            border-radius: 4px;
            border: 1px solid #c8cccf;
            color: #6a6f77;
            -web-kit-appearance: none;
            -moz-appearance: none;
            display: block;
            outline: 0;
            padding: 0 1em;
            text-decoration: none;
            width:100%;
            font-size:20px;
        }
    </style>
</head>
<body>
<script>
    var openid = "${wxUserDetailsInfo.openid}"
    var sugtype = "${wxUserDetailsInfo.sugtype}";
    var usertel = "${wxUserDetailsInfo.usertel}";
    var username = "${wxUserDetailsInfo.username}";
    var province = "${wxUserDetailsInfo.s1}";
    var city = "${wxUserDetailsInfo.s2}";
    var area = "${wxUserDetailsInfo.s3}";
    var addrdetail = "${wxUserDetailsInfo.addrdetail}";
    var sugdetail = "${wxUserDetailsInfo.sugdetail}";

    function yes() {
        window.location.href = "/web/wechat/cs.html";
    }

</script>
<p>非常感谢您的意见，请核对以下信息，反馈已提交到后台，请按确定返回：</p>
<p>${wxUserDetailsInfo.sugtype}:</p>
<p>用户号码：${wxUserDetailsInfo.usertel}</p>
<p>用户姓名：${wxUserDetailsInfo.username}</p>
<p>用户地址：${wxUserDetailsInfo.s1} ${wxUserDetailsInfo.s2} ${wxUserDetailsInfo.s3} </p>
<p>${wxUserDetailsInfo.addrdetail}</p>
<p style="word-break:break-all">详细内容：${wxUserDetailsInfo.sugdetail}</p>
<p></p>
<input type="button" id="btn1" value="确定" onclick="yes();" />

</body>
</html>
