<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jogg
  Date: 22.08.17
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
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
                    <th>${message.body}</th>
                </tr>
            </tbody>
        </table>
    </c:forEach>
</body>
</html>
