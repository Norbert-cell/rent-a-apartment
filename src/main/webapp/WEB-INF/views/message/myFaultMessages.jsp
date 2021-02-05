<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

Tytul: ${messageTitle}<br/>
    <c:forEach items="${messages}" var="message">
        ${message.dateOfSendMsg}
        ${message.content}<br/>
    </c:forEach>

<a href="<c:url value="/app/message/send/${msgAboutApartmentId}/${senderId}?type=FAULT" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/message/msg?type=FAULT" />">Wroc</a><br/>