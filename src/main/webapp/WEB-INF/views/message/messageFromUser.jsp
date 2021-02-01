<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

Tytul: ${messageTitle}<br/>
Od: ${userFullName} <br/>
<c:forEach items="${messages}" var="message">
    ${message.dateOfSendMsg}
    ${message.content}<br/>
</c:forEach>
<a href="<c:url value="/app/message/rent-message/return/${message.id}" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/1" />">Wroc</a><br/>
