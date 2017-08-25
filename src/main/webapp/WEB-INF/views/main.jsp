<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Guest blog</title>
</head>
<body>

<div class="container-fluid">

    <div class="row">
        <div class="col">
            <form:form method="GET" action="${contextPath}/add">
                <button type="submit" class="btn btn-primary m-2">Add post</button>
            </form:form>
        </div>
    </div>

    <div class="row">
        <c:forEach var="message" items="${listMessages}">

            <div class="col-xs-18 col-sm-6 col-md-3">
                <div class="my-2 my-sm-2 my-md-2 rounded" style="background-color: lightgray">
                    <div class="thumbail">
                        <img class="img-thumbnail" width="100%"
                             src="data:${utils:getMimeTypeFromBynary(message.image)};base64,${utils:binaryDataToBase64String(message.image)}"/>
                        <div class="caption">
                            <h4>${message.title}</h4>

                            <div class="row justify-content-between">
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
                            <p>${message.body}</p>

                            <a href="${contextPath}/del?id=${message.id}"
                               class="btn btn-danger m-2">Del post</a>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>

</div>

<script type="text/javascript" src="/resources/jquery/jquery.min.js"></script>
<link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
</body>
</html>
