<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>The start page</title>
</head>
<body>

<div class="container-fluid">

    <div class="row m-2">
        <div class="col">
            <form:form method="GET" action="${contextPath}/add">
                <button type="submit" class="btn btn-primary">Add post</button>
            </form:form>
        </div>
    </div>

    <c:forEach var="message" items="${listMessages}">
        <div class="col m-2">
            <div class="row">
                <div class="col">
                    <img class="img-thumbnail" width="250"
                         src="${utils:byteArrayToString(message.image)}"/>
                </div>
                <div class="col">
                    <div class="row text-left">
                        <p class="font-weight-bold">${message.title}</p>
                    </div>
                    <div class="row justify-content-between">
                        <div class="col text-center">
                            <p><em><small>${message.timestamp.toLocalDate().format(dateFormatter)}</small></em></p>
                        </div>
                        <div class="col text-center">
                            <p><em><small>${message.timestamp.toLocalTime().format(timeFormatter)}</small></em></p>
                        </div>
                    </div>
                    <div class="row text-left">
                        <p>${message.body}</p>
                    </div>
                </div>
                <div></div>
            </div>
        </div>

    </c:forEach>
</div>

<script type="text/javascript" src="/resources/jquery/jquery.min.js"></script>
<link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
</body>
</html>
