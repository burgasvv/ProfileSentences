<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Log Out Page</title>
</head>
<body>
    <mytags:navbar-guest/>
    <div style="padding: 70px">
        <h1 style="color: dimgrey">
            Goodbye, ${sessionScope.get("userName")}!
        </h1>
        <h2 style="color: dimgrey">
            Online time: ${sessionScope.get("onlineTime")}.
        </h2>
        <input type="button" value="OK" style="color: black; box-shadow: 2px 2px 2px lightgray; background-color: lavender; height: 30px; width: 80px; border-radius: 10px" onclick="location.href='/index.jsp'">
    </div>
</body>
</html>
