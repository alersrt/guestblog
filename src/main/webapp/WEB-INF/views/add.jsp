<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="container">
        <form:form method="POST" modelAttribute="messageForm">
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="title" class="form-control" placeholder="Title"
                                autofocus="true"></form:input>
                    <form:errors path="title"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="body">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="body" class="form-control" placeholder="The body of the post"
                                autofocus="true"></form:input>
                    <form:errors path="body"></form:errors>
                </div>
            </spring:bind>

            <button class="button" type="submit">Submit</button>
        </form:form>
    </div>
</body>
</html>
