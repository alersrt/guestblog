<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Add post</title>

    <link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
    <script src="/resources/jquery/jquery.min.js"
            type="text/javascript"></script>
    <script src="/resources/popper.js/dist/umd/popper.min.js"
            type="text/javascript"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"
            type="text/javascript"></script>
</head>
<body>

<div class="row justify-content-center">
    <div class="col-xs-9 col-sm-6 col-md-3">
        <form:form method="POST" modelAttribute="messageForm"
                   action="/add?${_csrf.parameterName}=${_csrf.token}"
                   enctype="multipart/form-data">

            <spring:bind path="title">
                <div class="form-group">
                    <form:errors path="title" cssClass="alert-danger"/>
                    <form:input path="title" type="text"
                                id="title"
                                placeholder="Title"
                                class="form-control ${status.error ? 'is-invalid' : 'is-valid'}"/>
                </div>
            </spring:bind>

            <spring:bind path="body">
                <div class="form-group">
                    <form:errors path="body" cssClass="alert-danger"/>
                    <form:textarea path="body" id="message"
                                   placeholder="Your Message"
                                   class="form-control  ${status.error ? 'is-invalid' : 'is-valid'}"/>
                </div>
            </spring:bind>

            <div class="form-group">
                <div class="row">
                    <spring:bind path="image">
                        <div class="col text-left ${status.error ? 'has-error' : ''}">
                            <form:errors path="image" cssClass="alert-danger"/>
                            <form:input id="imageButton" type="file"
                                        path="image"/>
                        </div>
                    </spring:bind>


                    <div class="col text-right">
                        <a href="${contextPath}/"
                           class="btn btn-danger">Cancel</a>
                        <button type="submit" class="btn btn-primary">Send
                            Message
                        </button>
                    </div>

                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
