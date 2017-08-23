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

    <form:form method="POST" modelAttribute="messageForm">

        <div class="col-auto">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:bind path="title">
                   <i class=""></i> <form:input path="title" type="text"
                                id="title"
                                placeholder="Title" class=""/>
                    <form:errors path="title"/>
                </spring:bind>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:bind path="body">
                    <form:textarea path="body" id="message"
                                   placeholder="Your Message"/>
                    <form:errors path="body"/>
                </spring:bind>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:bind path="image">
                    <input id="imageButton" type="file">
                    <form:hidden path="image" id="dataImage" value=""/>
                    <form:errors path="image"/>

                    <script type="text/javascript">
                        document.getElementById('imageButton').addEventListener('change', function () {
                            var files = document.getElementById('imageButton').files;
                            if (files.length > 0) {
                                getBase64(files[0]);
                            }
                        });

                        function getBase64(file) {
                            var reader = new FileReader();
                            reader.readAsDataURL(file);
                            reader.onload = function () {
                                document.getElementById('dataImage').value = reader.result;
                                console.log(reader.result);
                            };
                            reader.onerror = function (error) {
                                console.log('Error: ', error);
                            };
                        }
                    </script>

                </spring:bind>

            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Send Message</button>
            </div>

        </div>

    </form:form>

</div>


<script type="text/javascript" src="/resources/jquery/jquery.min.js"></script>
<link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
</body>
</html>
