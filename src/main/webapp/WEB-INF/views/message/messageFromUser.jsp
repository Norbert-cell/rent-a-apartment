<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h3>WIADOMOSCI</h3>
Tytul: ${messageTitle}<br/>
<c:forEach items="${messages}" var="message">
    ${message.dateOfSendMsg}
    <c:forEach items="${users}" var="user">
        <c:if test="${user.id==message.senderUserId}">
            ${user.fullName}<br/>
        </c:if>
    </c:forEach>
    ${message.content}<br/>
</c:forEach>
<input hidden value="${senderId}" name="senderId">
<a href="<c:url value="/app/message/send/${msgAboutApartmentId}/${senderId}?type=NORMAL" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/apartment/rent/${msgAboutApartmentId}" /> ">Wynajmij</a><br/>
<a href="<c:url value="/app/message/msg?type=NORMAL" />">Wroc</a><br/>
