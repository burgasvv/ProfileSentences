<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <mytags:navbar-guest/>
    <div style="text-align: center; margin-top: 15%">
        <form action="${pageContext.request.contextPath}/registration-servlet" method="post">
            <label for="userName">User Name <br>
                <input type="text" id="userName" name="userName" required style="color: black"> <br><br>
            </label>
            <label for="email">Email <br>
                <input type="email" id="email" name="email" required style="color: black"> <br><br>
            </label>
            <label for="password">Password <br>
                <input type="password" id="password" name="password" required style="color: black"> <br><br>
            </label>
            <input type="submit" value="Create" style="color: black; box-shadow: 2px 2px 2px lightgray; background-color: lavender; height: 30px; width: 80px; border-radius: 10px">
            <input type="button" value="Back" style="color: black; box-shadow: 2px 2px 2px lightgray; background-color: lavender; height: 30px; width: 80px; border-radius: 10px" onclick="location.href='/index.jsp'">
        </form>
    </div>
</body>
</html>
