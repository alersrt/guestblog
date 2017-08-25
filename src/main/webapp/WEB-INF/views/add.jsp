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
</head>
<body>

<div class="container-fluid m-2">
    <form:form method="POST" modelAttribute="messageForm" enctype="multipart/form-data" >
        <div class="col-auto">
            <spring:bind path="title">
                <div class="form-group">
                    <form:input path="title" type="text"
                                id="title"
                                placeholder="Title"
                                class="form-control ${status.error ? 'is-invalid' : 'is-valid'}"/>
                </div>
            </spring:bind>

            <spring:bind path="body">
                <div class="form-group">
                    <form:textarea path="body" id="message"
                                   placeholder="Your Message"
                                   class="form-control  ${status.error ? 'is-invalid' : 'is-valid'}"/>
                </div>
            </spring:bind>

            <div class="row">
                <spring:bind path="image">
                    <div class="col text-left ${status.error ? 'has-error' : ''}">
                        <form:errors path="image"/>
                        <form:input id="imageButton" type="file" path="image" class="form-control input-sm"/>
                    </div>
                </spring:bind>


                <div class="col text-right">
                    <a href="${contextPath}/"
                       class="btn btn-danger">Cancel</a>
                    <button type="submit" class="btn btn-primary">Send Message
                    </button>
                </div>

            </div>
        </div>
    </form:form>
</div>


<script type="text/javascript" src="/resources/jquery/jquery.js"></script>
<script type="text/javascript"
        src="/resources/bootstrap/js/bootstrap.js"></script>
<link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.css'/>
</body>
</html>
