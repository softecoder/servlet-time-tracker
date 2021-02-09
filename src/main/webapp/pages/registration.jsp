<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css"/>
    <title>Registration Page</title>
</head>
<body>
<div style="position:absolute; left:100px; top:200px;">
    <form name="registrationForm" method="POST" action="login">
        <input type="hidden" name="command" value="registration"/>
        <fieldset>
            <legend align="center"><fmt:message key="Registration_Form"/></legend>
            <table>
                <tr>
                    <td><fmt:message key="Name"/></td>
                    <td><input class="inputElement" type="text" name="firstName" value=""/></td>
                </tr>
                <tr>
                    <td><fmt:message key="Surname"/>:</td>
                    <td><input class="inputElement" type="text" name="surname" value=""/></td>
                </tr>
                <tr>
                    <td><fmt:message key="login"/></td>
                    <td><input class="inputElement" type="text" name="login" value=""/></td>
                </tr>
                <tr>
                    <td><fmt:message key="password"/></td>
                    <td><input class="inputElement" type="password" name="password" value=""/></td>
                </tr>
            </table>
            <div class="wrapperButtons">
                <input class="buttonElement" type="submit" value="<fmt:message key="Register"/>"/>
                <input class="buttonElement" type="reset" value="<fmt:message key="Reset"/>"/>
                ${pageContext.session.setAttribute("backpage", "login")}
                <input type="button" value="<fmt:message key="Back"/>" onclick='location.href="login?command=back"'/>
            </div>
        </fieldset>
    </form>
</div>
<div>
    <div class="operationMessageElement" style="position:absolute; left:100px; top:370px;">
        <c:if test="${requestScope.errorLoginOrPassword!= null}">
            <fmt:message key="${requestScope.errorLoginOrPassword}"/>
        </c:if>
    </div>
</div>

<!--LANGUAGE-->
<div class="languageElement" style="position:fixed; right:20px; top:10px;">
    <table>
        <tr>
            <form class="formElement" name="actionForm" method="POST" action="registration">
                <td>
                    <input type="hidden" name="command" value="setLanguage"/>
                    <input type="hidden" name="page" value="registerPage"/>
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
