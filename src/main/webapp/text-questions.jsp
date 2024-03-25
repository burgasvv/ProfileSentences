<jsp:useBean id="questionsAttribute" scope="request" type="java.util.List"/>
<jsp:useBean id="text" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Text Questions</title>
</head>
<body>
    <mytags:navbar-user/>
    <div style="margin: 70px; color: dimgrey">
        <h1>Text:</h1>
        <div style="text-indent: 70px; text-align: justify">
            <h2>${text}</h2>
        </div>
        <h1>Questions from text:</h1>
        <h2>${questionsAttribute}</h2>
        <H1>Questions from cookie:</H1>
        <h2>${cookie.questionsCookie.value}</h2>
        <input type="button" value="Back" style="color: black; box-shadow: 2px 2px 2px lightgray; background-color: lavender; height: 30px; width: 80px; border-radius: 10px" onclick="history.back()">
    </div>
</body>
</html>
