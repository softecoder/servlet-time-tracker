<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Error Pge</title>
    <style type="text/css">
        body {
            margin: 0px;
            padding: 0px;
            display: inline-block;
            background: url("../images/bg_menu.jpg") no-repeat center center fixed;
            background-size: cover;
        }

        .errorElement {
            position: absolute;
            left: 5%;
            top: 5%;
        }

        .errorPageElement {
            position: fixed;
            left: 10px;
            bottom: 10px;
            color: firebrick;
        }
    </style>
</head>
<body>
<div class="errorElement">
    <h2>Error was occured!</h2>
    <h3>${errorDataBase}</h3>
</div>
<div class="errorPageElement">
    <form name="logout" method="POST" action="login">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="Back to Login page"/>
    </form>
</div>
</body>
</html>