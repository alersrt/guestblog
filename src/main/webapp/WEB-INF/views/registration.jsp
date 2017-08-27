<%--
  Created by IntelliJ IDEA.
  User: jogg
  Date: 27.08.17
  Time: 6:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Registration</title>

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
        <form:form method="POST" modelAttribute="userForm" class="form-signin">
            <h5 class="form-signin-heading">Create your account</h5>

            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="username" class="form-control"
                                placeholder="Username"
                                autofocus="true"/>
                    <form:errors path="username" cssClass="alert-danger"/>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="email" class="form-control"
                                placeholder="E-Mail"
                                autofocus="true"/>
                    <form:errors path="email" cssClass="alert-danger"/>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password"
                                class="form-control"
                                placeholder="Password"/>
                    <form:errors path="password" cssClass="alert-danger"/>
                </div>
            </spring:bind>

            <spring:bind path="confirmPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="confirmPassword"
                                class="form-control"
                                placeholder="Confirm your password"/>
                    <form:errors path="confirmPassword" cssClass="alert-danger"/>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">
                Submit
            </button>
        </form:form>
    </div>
</div>


</body>
</html>
