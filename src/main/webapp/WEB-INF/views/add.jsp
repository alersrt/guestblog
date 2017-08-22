<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script src="${contextPath}/resources/js/bootstrap.js"></script>

<html>
<head>
    <title>Guest Blog</title>
    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>

    <form:form class="form-horizontal" method="POST" modelAttribute="messageForm">

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="title" class="col-sm-2 control-label">Title</label>
            <spring:bind path="title">
                <div class="col-sm-4">
                    <form:input path="title" type="text" class="form-control" id="title" name="title" placeholder="Title"/>
                    <form:errors path="title"></form:errors>
                </div>
            </spring:bind>
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="message" class="col-sm-2 control-label">Message</label>
            <spring:bind path="body">
                <div class="col-sm-6">
                    <form:textarea path="body" id="message" name="message" class="form-control" placeholder="Your Message" rows="5"/>
                    <form:errors path="body"></form:errors>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-6">
                <button type="submit" class="btn btn-primary">Send Message</button>
            </div>
        </div>
    </form:form>

</body>
</html>
