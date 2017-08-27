<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>Guest blog</title>

    <link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
    <script src="/resources/jquery/jquery.min.js"
            type="text/javascript"></script>
    <script src="/resources/popper.js/dist/umd/popper.min.js"
            type="text/javascript"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"
            type="text/javascript"></script>
</head>
<body>

<form id="logoutForm" method="POST" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<nav class="navbar navbar-dark bg-dark" style="box-shadow: 4px 4px 5px gray">
    <form:form method="GET" action="${contextPath}/add">
        <button type="submit" class="btn btn-primary">Add post
        </button>
    </form:form>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>
            Welcome <span
                style="text-transform: capitalize">${fn:toLowerCase(pageContext.request.userPrincipal.name)}</span>
        </h2>
    </c:if>
    <form class="form-inline">
        <button type="button" class="btn btn-danger"
                onclick="document.forms['logoutForm'].submit()">Logout
        </button>
        <a type="button" class="btn btn-outline-primary mx-2"
           href="${contextPath}/login">Sign In</a>
        <a type="button" class="btn btn-outline-secondary mx-2"
           href="${contextPath}/registration">Sing Up</a>
    </form>
</nav>

<div class="container-fluid">

    <div class="row">
        <c:forEach var="message" items="${listMessages}">

            <div class="col-xs-18 col-sm-6 col-md-3">
                <div class="thumbail m-1 m-sm-1 m-md-1 border rounded"
                     style="background-color: lightgray; box-shadow: 4px 4px 5px gray;">
                    <img class="img-thumbnail" width="100%"
                         src="data:${utils:getMimeTypeFromBynary(message.image)};base64,${utils:binaryDataToBase64String(message.image)}"/>

                    <div class="caption m-1 m-sm-1 m-md-1">
                        <h4>${message.title}</h4>

                        <div class="row justify-content-between m-1 m-sm-1 m-md-1">
                            <div class="col text-left">
                                <p><em>
                                    <small>${message.timestamp.toLocalDate().format(dateFormatter)}</small>
                                </em></p>
                            </div>
                            <div class="col text-right">
                                <p><em>
                                    <small>${message.timestamp.toLocalTime().format(timeFormatter)}</small>
                                </em></p>
                            </div>
                        </div>
                        <p class="m-1 m-sm-1 m-md-1">${message.body}</p>

                        <a href="${contextPath}/del?id=${message.id}"
                           class="btn btn-danger m-2 align-items-end">Del
                            post</a>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>

</div>

</body>
</html>
