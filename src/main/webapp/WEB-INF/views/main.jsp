<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="utils" uri="/WEB-INF/tld/utils.tld" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>The Start page</title>
</head>
<body>

<form:form id="addForm" method="GET" action="${contextPath}/add">
    <button type="submit" class="btn btn-primary">Add post</button>
</form:form>
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
        <tr>
            <th><img class="img-thumbnail" width="250"
                     src="${utils:byteArrayToString(message.image)}"/>
            </th>
        </tr>
        </tbody>
    </table>
</c:forEach>


<script type="text/javascript" src="/resources/jquery/jquery.min.js"></script>
<link rel='stylesheet' href='/resources/bootstrap/css/bootstrap.min.css'/>
</body>
</html>
