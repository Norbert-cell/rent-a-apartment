<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

Tytul: ${messageTitle}<br/>
Szacowany koszt: ${estimatedPrice}PLN<br/>
    <c:forEach items="${messages}" var="message">
        ${message.dateOfSendMsg}
        <c:forEach items="${users}" var="user">
            <c:if test="${user.id==message.senderUserId}">
                ${user.fullName}<br/>
            </c:if>
        </c:forEach>
        ${message.content}<br/>
    </c:forEach>

<a href="<c:url value="/app/message/send/${msgAboutApartmentId}/${senderId}?type=FAULT" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/message/msg?type=FAULT" />">Wroc</a><br/>