<%@ page contentType="text/html; charset=gbk" language="java" import="java.sql.*"%>
<%--<%@ page import="java.io.*,java.util.*" %>--%>
<html>
<body>

<%
    String str = request.getParameter("str").trim();
    System.out.println("str:" + str);
    String city  = null;
    String region = null;
    int city_id = 0;
    int index1 = str.indexOf(" ");
    int index2 = str.lastIndexOf(" ");
    System.out.println("index:" + index1 + " " + index2);
    city = str.substring(index1+1,index2-1);
    System.out.println("city:" + city);
    region = str.substring(index2+1,str.length());
    System.out.println("region:" + region);
   Connection con=java.sql.DriverManager.getConnection("jdbc:mysql://120.77.22.212:3306/jsq?useUnicode=true&characterEncoding=utf8","wpuser","wppass");
   Statement stmt=con.createStatement();

    String sql1 = "SELECT row_id FROM `city_info` WHERE city=";
    sql1 = sql1 +"'" + city + "'";
    System.out.println("sql1:" + sql1);
    ResultSet rst=stmt.executeQuery(sql1);
    while(rst.next())
    {
//        out.println(rst.getString("province_id"));
        city_id = Integer.parseInt(rst.getString("row_id"));
    }

    String sql2 = "SELECT region_tds FROM `region_info` WHERE city_id=" + city_id + " AND region=" + "'" + region + "'";
    System.out.println("sql2:" + sql2);
    rst=stmt.executeQuery(sql2);
    while(rst.next())
    {
        out.println(rst.getString("region_tds"));
    }

    //关闭连接、释放资源
    rst.close();
    stmt.close();
    con.close();
    %>

</body>
</html>