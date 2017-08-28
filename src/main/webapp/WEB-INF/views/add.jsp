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
    <link rel='stylesheet' href='/resources/octicons/build/font/octicons.css'/>
    <script src="/resources/jquery/jquery.min.js"
            type="text/javascript"></script>
    <script src="/resources/popper.js/dist/umd/popper.min.js"
            type="text/javascript"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"
            type="text/javascript"></script>
</head>
<body>

<div class="row justify-content-center">
    <div class="col-xs-12 col-sm-8 col-md-6">
        <form:form method="POST" modelAttribute="messageForm"
                   action="/addMessage?${_csrf.parameterName}=${_csrf.token}"
                   enctype="multipart/form-data">

            <spring:bind path="title">
                <form:errors path="title" cssClass="alert-danger"/>
                <div class="input-group" style="margin-bottom: 25px">
                    <span class="input-group-addon"><i
                            class="octicon octicon-pencil"></i></span>
                    <form:input path="title" type="text"
                                id="title"
                                placeholder="Title"
                                class="form-control ${status.error ? 'is-invalid' : 'is-valid'}"/>
                </div>
            </spring:bind>

            <spring:bind path="body">
                <form:errors path="body" cssClass="alert-danger"/>
                <div class="input-group" style="margin-bottom: 25px">
                    <span class="input-group-addon"><i
                            class="octicon octicon-file-text"></i></span>
                    <form:textarea path="body" id="message"
                                   placeholder="Your Message"
                                   class="form-control  ${status.error ? 'is-invalid' : 'is-valid'}"
                                   rows="10"/>
                </div>
            </spring:bind>

            <div class="form-group">

                <div class="row">

                    <div class="col">
                        <spring:bind path="image">
                            <div class="form-group text-left ${status.error ? 'has-error' : ''}">
                                <label for="imageButton">
                                    <span id="imageButtonSpan" class="btn btn-info "><i
                                            class="octicon octicon-file-media"></i>Choose your image</span>
                                </label>
                                <form:errors path="image"
                                             cssClass="alert-danger"/>
                                <form:input id="imageButton" path="image"
                                            cssClass="custom-file-input"
                                            type="file"/>
                                <script type="text/javascript">
                                    $("document").ready(function () {
                                        $('#imageButton').change(function () {
                                            document.getElementById('imageButtonSpan').innerHTML = document.getElementById('imageButton').files[0].name;
                                        });
                                    });
                                </script>
                            </div>
                        </spring:bind>
                    </div>

                    <div class="col text-right">
                        <button type="submit" class="btn btn-primary"><span><i
                                class="octicon octicon-cloud-upload"></i>Send</span>
                        </button>
                        <a href="${contextPath}/"
                           class="btn btn-danger"><span><i
                                class="octicon octicon-sign-out"></i>Cancel</span></a>
                    </div>

                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
