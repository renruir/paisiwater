<%--
  Created by IntelliJ IDEA.
  User: skyworth
  Date: 2017/2/24
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>tds</title>
    <link rel="stylesheet" href="/web/css/weui.min.css">
    <link rel="stylesheet" href="/web/css/jquery-weui.css">
    <link rel="stylesheet" href="/web/css/demos.css">
</head>
<body>
<header class="demos-header">
    <h1 class="demos-title">TDS查询</h1>
</header>

<div class="weui-cells weui-cells_form">
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">发出地</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="start" onchange="showTDS(this.value)" type="text" value="北京 北京市 东城区" readonly="" data-code="440103" data-codes="440000,440100,440103">
        </div>
    </div>
</div>
<div id="txtHint">dts信息将显示在这...</div>

<script src="/web/js/jquery-2.1.4.js"></script>
<script src="/web/js/fastclick.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script src="/web/js/jquery-weui.js"></script>

<script src="/web/js/city-picker.js"></script>

<script>
    $("#start").cityPicker({
        title: "选择所在地",
        onChange: function (picker, values, displayValues) {
            console.log(displayValues);
        }
    });

    function showTDS(str)
    {
        var xmlhttp;
        if (str=="")
        {
            document.getElementById("txtHint").innerHTML="未查询到该地区dts值";
            return;
        }
        console.log("jxq str:" + str);
        if (window.XMLHttpRequest)
        {
            // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            // IE6, IE5 浏览器执行代码
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
//                if(xmlhttp.resp){
//                    document.getElementById("txtHint").innerHTML="未查询到该地区tds值";
//                }
                document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
            }
        }
//        str = "南平";
        xmlhttp.open("GET","/web/getTDS.jsp?str="+str,true);
        xmlhttp.send();
    }
//
//    //创建数据库连接对象
//    var conn = new ActiveXObject("ADODB.Connection");
//    //创建数据集对象
//    var rs = new ActiveXObject("ADODB.Recordset");
//    try{
//       var connectionstring = "Driver={com.mysql.jdbc.Driver};Server=jdbc:mysql://120.77.22.212:3306/jsq?useUnicode=true&characterEncoding=utf8;Database=jsq;User=wpuser;Password=wppass;Port=3306";
//        conn.open(connectionstring);
//
//        var sql = "SELECT province_id FROM `city_info` WHERE city='南平'";
//        rs.open(sql,conn);
//         while(!rs.eof){
//            console.log("jxq:" + rs.Fields("province_id"));
//            rs.moveNext();
//        }
//        rs.close();
//        conn.close();
//    } catch(e){
//        console.log(e.message);
//    } finally{
//    }
</script>
</body>
</html>
