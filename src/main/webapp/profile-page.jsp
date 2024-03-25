<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Profile Page</title>
</head>
<body>
    <mytags:navbar-user/>
    <div style="padding: 70px; color: dimgrey">
        <h1>Profile information</h1>
        <h2>Profile type: ${sessionScope.get("userType")}</h2>
        <h2>User Name: ${sessionScope.get("userName")}</h2>
        <h2>Email: ${sessionScope.get("email")}</h2>
        <h2>Password: ${sessionScope.get("password")}</h2>
        <h2>Login time: ${sessionScope.get("loginTime")}</h2>
        <input type="button" value="Back" style="color: black; box-shadow: 2px 2px 2px lightgray; background-color: lavender; height: 30px; width: 80px; border-radius: 10px" onclick="location.href='/'">
    </div>
</body>
</html>
