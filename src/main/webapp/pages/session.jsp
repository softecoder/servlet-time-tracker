<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>Session Page</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/session.css"/>
</head>
<body>
<div style = "position:absolute; left:100px; top:200px;">
	<form name="sessionForm" method="POST" action="login">
		<input type="hidden" name="command" value="login"/>
		<fieldset>
			<legend align="center">Session notice</legend>
			<table>
				<tr>
					<td>Your session is out</td>
				</tr>
			</table>
			<div class="wrapperButtons">
				${pageContext.session.setAttribute("backpage", "login")}
				<input type="button" value="Log In" onclick='location.href="login?command=back"'/>
			</div>
		</fieldset>
		<div class="operationMessageElement">
			<c:if test="${requestScope.operationMessage!= null}">
				<fmt:message key="${requestScope.operationMessage}"/>
			</c:if>
		</div>
	</form>
</div>
<div class="registrationPageMessage">SESSION PAGE</div>
</body>
</html>
