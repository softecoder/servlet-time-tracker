<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <link rel="stylesheet" type="text/css" href="<spec:url value="/css/login.css"/>"/>
    <title>Login Page</title>
</head>
<body>
<div style="position:absolute; left:100px; top:200px;">
    <form name="loginForm" method="POST" action="user">
        <fieldset>
            <legend align="center">
                <fmt:message key="loginTitle"/>
            </legend>
            <input type="hidden" name="command" value="login"/>
            <fmt:message key="login"/><br/><input class="inputElement" type="text" name="login" value=""/><br/>
            <fmt:message key="password"/><br/><input class="inputElement" type="password" name="password"
                                                     value=""/><br/><br/>
            <input type="submit" value="<fmt:message key="log_in"/>"/>
            <input type="button" value="<fmt:message key="registration"/>"
                   onclick='location.href="registration?command=gotoregistration"'/>
        </fieldset>
    </form>

    <%--    validation--%>
    <div class="errorMessage">
        <c:if test="${requestScope.errorLoginOrPassword!= null}">
            <fmt:message key="${requestScope.errorLoginOrPassword}"/>
        </c:if>
    </div>
</div>

<!--LANGUAGE-->
<div class="languageElement" style="position:fixed; right:20px; top:10px;">
    <table>
        <tr>
            <form class="formElement" name="actionForm" method="POST" action="login">
                <td>
                    <input type="hidden" name="command" value="setLanguage"/>
                    <input type="hidden" name="page" value="loginPage"/>
                </td>
                <td>
                    <select name="chosenLanguage" onchange="this.form.submit()">
                        <c:choose>
                            <c:when test="${current == 'en_EN'}">
                                <option value="en_EN"><fmt:message key="en"/></option>
                                <option value="ru_RU"><fmt:message key="ru"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="ru_RU"><fmt:message key="ru"/></option>
                                <option value="en_EN"><fmt:message key="en"/></option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
            </form>
        </tr>
    </table>
</div>
</body>
</html>
