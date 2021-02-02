<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h3>WIADOMOSCI</h3>
Tytul: ${messageTitle}<br/>
Od: ${userFullName} <br/>
<c:forEach items="${messages}" var="message">
    ${message.dateOfSendMsg}
    ${message.content}<br/>
</c:forEach>
<input hidden value="${senderId}" name="senderId">
<a href="<c:url value="/app/message/return/${msgAboutApartmentId}" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/apartment/rent/${msgAboutApartmentId}" /> ">Wynajmij</a><br/>
<a href="<c:url value="/app/message/normal" />">Wroc</a><br/>
