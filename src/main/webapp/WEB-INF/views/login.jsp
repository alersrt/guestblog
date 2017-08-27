<%--
  Created by IntelliJ IDEA.
  User: jogg
  Date: 26.08.17
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Login Form</title>

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
        <form accept-charset="UTF-8" method="POST" action="${contextPath}/login"
              class="form-signin">
            <h5 class="form-heading">Log in</h5>

            <div class="form-group ${error != null ? 'has-error' : ''}">
                <input name="username" type="text" class="form-control"
                       path="username"
                       placeholder="Username"
                       autofocus="true"/>
                <input name="password" type="password" class="form-control"
                       path="password"
                       placeholder="Password"/>
                <span class="alert-danger">${error}</span>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>

                <div class="row">
                    <div class="col justify-content-end">
                        <button class="btn btn-primary" type="submit">Log In</button>
                        <a class="btn btn-danger" href="${contextPath}/">Cancel</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

</body>
</html>
