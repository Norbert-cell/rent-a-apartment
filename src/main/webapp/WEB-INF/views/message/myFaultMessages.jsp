<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

Od:${userFullName}
<c:forEach items="${messages}" var="message">
    ${message.senderUser}<br/>
    ${message.title}<br/>
    ${message.content}<br/>
    <a href="<c:url value="/app/message/return/${message.id}" />">Odpowiedz</a><br/>
</c:forEach>