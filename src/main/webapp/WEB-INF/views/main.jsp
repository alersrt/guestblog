<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
<c:set var="loggedUsername" value="${pageContext.request.userPrincipal.name}"/>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

<!DOCTYPE html>
<html>
<head>
    <title>Guest blog</title>

    <link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='/resources/octicons/build/font/octicons.css'/>
    <link rel='stylesheet' href='/resources/css/local.css'/>
    <script src="/resources/jquery/jquery.min.js"
            type="text/javascript"></script>
    <script src="/resources/popper.js/dist/umd/popper.min.js"
            type="text/javascript"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"
            type="text/javascript"></script>
</head>
<body>

<%--Actions' forms--%>
<form id="logoutForm" method="POST" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<form id="addMessForm" method="GET" action="${contextPath}/addMessage">
</form>

<%--Navbar--%>
<nav class="navbar navbar-dark bg-dark" style="box-shadow: 2px 2px 5px gray">
    <button type="submit" class="btn btn-primary"
            onclick="document.forms['addMessForm'].submit()"><span><i
            class="octicon octicon-pencil"></i>Add post</span>
    </button>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2><span class="text-light"
                  style="text-transform: capitalize">Welcome ${fn:toLowerCase(loggedUsername)}</span>
        </h2>
    </c:if>
    <form class="form-inline">
        <c:choose>
            <c:when test="${isAuthenticated}">
                <button type="button" class="btn btn-danger"
                        onclick="document.forms['logoutForm'].submit()"><span><i
                        class="octicon octicon-sign-out"></i>Log Out</span>
                </button>
            </c:when>
            <c:otherwise>
                <div class="col controls">
                    <a type="button" class="btn btn-primary"
                       href="${contextPath}/login"><span><i
                            class="octicon octicon-sign-in"></i>Sign In</span></a>
                    <a type="button" class="btn btn-secondary"
                       href="${contextPath}/registration"><span><i
                            class="octicon octicon-lock"></i>Sign Up</span></a>
                </div>
            </c:otherwise>
        </c:choose>


    </form>
</nav>

<div class="card-columns m-2">

    <c:forEach var="message" items="${listMessages}">
        <div class="card border rounded"
             style="box-shadow: 2px 2px 5px gray;">
            <div class="card-body">
                <img class="card-img-top img-thumbnail" width="100%"
                     src="data:${utils:getMimeTypeFromBynary(message.image)};base64,${utils:binaryDataToBase64String(message.image)}"/>
                <h4 class="card-title">${message.title}</h4>

                <span class="card-subtitle text-capitalize"
                      style="font-weight: bold"><i
                        class="octicon octicon-person"></i>${message.user.username.length() > 0 ? message.user.username : 'anonymous'}</span>


                <p class="card-text">${message.body}</p>

                <c:if test="${message.user.username.equals(loggedUsername) || isAdmin}">
                    <a href="${contextPath}/delMessage?id=${message.id}"
                       class="btn btn-danger m-2 align-items-end">
                        <span><i
                                class="octicon octicon-trashcan"></i>Delete</span></a>
                </c:if>
            </div>
            <div class="card-footer justify-content-between">
                <small class="text-muted text-left">${message.timestamp.toLocalDate().format(dateFormatter)}</small>
                <small class="text-muted text-right">${message.timestamp.toLocalTime().format(timeFormatter)}</small>
            </div>
        </div>

    </c:forEach>

</div>

</body>
</html>
