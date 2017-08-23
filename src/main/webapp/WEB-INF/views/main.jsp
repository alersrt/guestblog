<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script src="${contextPath}/resources/js/bootstrap.js"></script>

<html>
<head>
    <title>The Start page</title>
    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>

    <form:form id="addForm" method="GET" action="${contextPath}/add">
        <button type="submit" class="btn btn-primary">Add post</button>
    </form:form>

    <div class="container">
        <div class="row">
            <div class="col-auto">
                <c:forEach var="message" items="${listMessages}">
                    <table>
                        <tbody>
                        <tr>
                            <th>${message.title}</th>
                        </tr>
                        <tr>
                            <th>${message.timestamp}</th>
                        </tr>
                        <tr>
                            <th>${message.body}</th>
                        </tr>
                        </tbody>
                    </table>
                </c:forEach>
            </div>
        </div>
    </div>

</body>
</html>
