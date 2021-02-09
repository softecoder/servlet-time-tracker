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
<div class="wrapperWelcomeInfo">
    <div class="welcomeElement">
        <fmt:message key="welcome_admin"/>
        <c:out value="${sessionScope.adminUser.firstName} ${sessionScope.adminUser.surName}"/>!
    </div>
</div>
<div class="wrapperPageData">
    <fieldset>
        <legend align="center"><fmt:message key="overview_activity_table"/></legend>
        <div class="activityInfoForm">
            <table>
                <col width="180">
                <col width="100">
                <col width="200">
                <col width="230">
                <tr>
                    <th align="left"><fmt:message key="USERS"/></th>
                    <th><fmt:message key="ACTIVITIES"/></th>
                    <th><fmt:message key="REQUEST_FROM_CLIENT"/></th>
                    <th align="left"><fmt:message key="NOTICE"/></th>
                </tr>
                <c:set var="countPage" value="${1}" scope="page"/>
                <c:set var="countItem" value="${0}" scope="page"/>
                <c:forEach items="${sessionScope.userList}" var="user">
                    <c:if test="${user.userType.userType=='client'}">
                        <c:if test="${countPage == sessionScope.currentPage}">
                            <tr>
                                <td>
                                    <c:out value="${user.firstName} ${user.surName}"/>
                                </td>
                                <td>
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="admin">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="userId" value="${user.userId}"/>
                                            <input type="hidden" name="command" value="overviewClient"/>
                                            <input class="buttonElement" type="submit"
                                                   value="<fmt:message key="overview"/>"
                                                   style="height:20px; width:70px"/>
                                        </div>
                                    </form>
                                </td>

                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <c:set var="flag" value="false"/>
                                                <c:if test="${user.requestAdd=='true'}">
                                                    <button class="mockButton red"><fmt:message
                                                            key="add_new_activity"/></button>
                                                    <c:set var="flag" value="true"/>
                                                </c:if>
                                                <c:if test="${flag == 'false'}">
                                                    <button class="mockButton blue"><fmt:message
                                                            key="add_new_activity"/></button>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:set var="flag" value="false"/>
                                                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                                                    <c:if test="${tracking.user.userId==user.userId &&
                                                 tracking.userRequest == 'REMOVE'}">
                                                        <c:set var="flag" value="true"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${flag == 'true'}">
                                                        <button class="mockButton red"><fmt:message
                                                                key="remove_finished_activity"/></button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="mockButton blue"><fmt:message
                                                                key="remove_finished_activity"/></button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <c:set var="flag" value="false"/>
                                    <c:forEach items="${sessionScope.trackingList}" var="tracking">
                                        <c:if test="${tracking.user.userId==user.userId && (tracking.userRequest == 'REMOVE'||
                                    tracking.userRequest == 'ADD' || user.requestAdd == 'true')}">
                                            <c:set var="flag" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${flag == 'true'}">
                                            <fmt:message key="waiting"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="no_request"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:if>
                        <c:set var="countItem" value="${countItem + 1}"/>
                        <c:if test="${countItem%sessionScope.itemsPerPage==0}">
                            <c:set var="countPage" value="${countPage + 1}"/>
                        </c:if>
                    </c:if>
                </c:forEach>
            </table>
            <table align="center">
                <tr>
                    <td>
                        <div class="pagination">
                            <c:if test="${sessionScope.currentPage>'1'}">
                                <c:set var="count" value="${sessionScope.currentPage - 1}" scope="page"/>
                                <a href="admin?command=chosePage&currentPage=${count}">&laquo;</a>
                            </c:if>
                            <c:forEach items="${sessionScope.numbersPages}" var="page">
                                <c:choose>
                                    <c:when test="${page == sessionScope.currentPage}">
                                        <a href="admin?command=chosePage&currentPage=${page}" class="active">
                                            <c:out value="${page}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="admin?command=chosePage&currentPage=${page}">
                                            <c:out value="${page}"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${sessionScope.currentPage<sessionScope.lastPage}">
                                <c:set var="count" value="${sessionScope.currentPage + 1}" scope="page"/>
                                <a href="admin?command=chosePage&currentPage=${count}">&raquo;</a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
    <div class="wrapperTableActivityAdmin">
        <fieldset>
            <legend align="center"><fmt:message key="AVAILABLE_ACTIVITIES"/></legend>
            <div class="activityInfoForm">
                <table style=width:250px>
                    <col width="100">
                    <tr>
                        <td width="250" align="left">
                            <form class="formElement" name="actionForm" method="POST"
                                  action="admin">
                                <div class="wrapperButtons">
                                    <input type="hidden" name="command" value="createActivity"/>
                                    <input class="buttonElement" type="submit" value="<fmt:message key="add_activity"/>"
                                           style="height:20px; width:150px"/>
                                    <input class="inputElement" type="text" name="activityName" value=""
                                           style="height:20px; width:180px"/>
                                </div>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <c:forEach items="${sessionScope.activityAdminList}" var="activity">
                                ${activity.activityName}<br>
                            </c:forEach>
                        </td>
                    </tr>
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
<div class="logoutElement" style="position:fixed; right:230px; top:12px;">
    <form name="logout" method="POST" action="login">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="<fmt:message key="logout"/>"/>
    </form>
</div>

<!--LANGUAGE-->
<div class="languageElement" style="position:fixed; right:20px; top:10px;">
    <table>
        <tr>
            <form class="formElement" name="actionForm" method="POST" action="admin">
                <td>
                    <input type="hidden" name="command" value="setLanguage"/>
                    <input type="hidden" name="page" value="adminMainPage"/>
                    <%--<input type="submit" value="<fmt:message key="language"/>"/>--%>
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