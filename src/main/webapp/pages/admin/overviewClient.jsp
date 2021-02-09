<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <link rel="stylesheet" type="text/css" href="<spec:url value="/css/adminMain.css"/>"/>
    <title>Admin page</title>
</head>
<body>

<div class="wrapperUserData">

    <fieldset>
        <legend align="center"><fmt:message key="Client"/><c:out value="${sessionScope.overviewUser.firstName}
                                                ${sessionScope.overviewUser.surName}"/></legend>
        <div class="activityInfoForm">
            <table>
                <col width="200">
                <col width="150">
                <col width="100">
                <col width="150">
                <col width="200">
                <tr>
                    <th align="left"><fmt:message key="ACTIVITIES"/></th>
                    <th align="left"><fmt:message key="STATUS"/></th>
                    <th align="left"><fmt:message key="TIME"/></th>
                    <th align="center"><fmt:message key="ACTION"/></th>
                    <th align="left"><fmt:message key="NOTICE"/></th>
                </tr>
                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                    <c:set var="userId" value="${sessionScope.overviewUser.userId}"/>
                    <c:if test="${tracking.user.userId==userId}">
                        <tr>
                            <td>
                                <c:out value="${tracking.activity.activityName}"/>
                            </td>
                            <td>
                                <fmt:message key="${tracking.status}"/>
                            </td>
                            <td>
                                <c:out value="${tracking.elapsedTime}"/>
                            </td>
                            <td>
                                <c:set var="status" value="${tracking.userRequest}"/>
                                <c:if test="${status=='REMOVE'}">
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="admin">
                                        <input type="hidden" name="trackingId" value="${tracking.trackingId}"/>
                                        <input type="hidden" name="userId" value="${tracking.user.userId}"/>
                                        <input type="hidden" name="command" value="removeAdmin"/>
                                        <input class="buttonElement" type="submit" value="<fmt:message key="remove"/>"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                <c:set var="request" value="${tracking.userRequest}"/>
                                <c:if test="${request=='REMOVE'}">
                                    <fmt:message key="response"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </fieldset>
    <div class="wrapperTableActivity">
        <fieldset>
            <legend align="center"><fmt:message key="AVAILABLE_ACTIVITIES"/></legend>
            <div class="activityInfoForm">
                <table style=width:330px>
                    <col width="100">
                    <c:forEach items="${sessionScope.activityAdminList}" var="activity">
                        <tr>
                            <td>
                                <form class="formElement" name="actionForm" method="POST"
                                      action="admin">
                                    <div class="wrapperButtons">
                                        <input type="hidden" name="userId" value="${sessionScope.overviewUser.userId}"/>
                                        <input type="hidden" name="activityId" value="${activity.activityId}"/>
                                        <input type="hidden" name="command" value="addActivity"/>
                                        <input class="buttonElement" type="submit" value="<fmt:message key="add"/>"
                                               style="height:20px; width:80px"/>
                                    </div>
                                </form>
                            </td>
                            <td>
                                <c:out value="${activity.activityName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <table style=width:330px>
                    <tr>
                        <td>
                            <div>
                                <c:if test="${requestScope.operationMessage!= null}">
                                    <fmt:message key="${requestScope.operationMessage}"/>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>
</div>

<!--LOGOUT-->
<div class="logoutElement">
    <form name="logout" method="POST" action="login">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="<fmt:message key="logout"/>"/>
    </form>
</div>

<!--BACK-->
<div class="backElement">
    <form name="backForm" method="POST" action="admin">
        <input type="hidden" name="command" value="backAdmin"/>
        <input type="submit" value="<fmt:message key="Back"/>"/>
    </form>
</div>
<!--LANGUAGE-->
<div class="languageElement" style="position:fixed; right:20px; top:10px;">
    <table>
        <tr>
            <form class="formElement" name="actionForm" method="POST" action="admin">
                <td>
                    <input type="hidden" name="command" value="setLanguage"/>
                    <input type="hidden" name="page" value="adminOverviewPage"/>
                     </td>
                <td>
                    <select name="chosenLanguage"   onchange="this.form.submit()">
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