<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>The Start page</title>
</head>
<body>
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
</body>
</html>
