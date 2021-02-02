<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h2>USTERKA</h2>

<h3>Zglos usterke do uzytkownika ${ownerUserFullName}</h3><br/>
<form:form modelAttribute="message" method="post" action="/app/message/report">
    <form:hidden path="msgAboutApartmentId"/>
    <form:hidden path="type"/>
    <form:hidden path="users"/>
    Tytuł:<form:input path="title"/><br/>
    Opis usterki: <form:textarea cols="20" rows="45" path="content"/><br/>
    <form:errors path="content"/>
    Szacowana wartosc naprawy:<form:input path="estimatedPrice"/><br/>
    <form:errors path="estimatedPrice"/>
    <form:button value="Wyslij">Wyslij</form:button>
</form:form>


<%--<c:forEach items="${messages}" var="message">--%>
<%--    Od: <h1>${message.senderUser.fullName}</h1><br/>--%>
<%--    Wiadomosc: <h3>${message.value}</h3><br/>--%>
<%--    Szacowana wartosc: <h3>${message.estimatedPrice}</h3>--%>
<%--    <a href="<c:url value="/app/message/send/${message.senderUser.id}" />">Odpisz</a>--%>
<%--</c:forEach>--%>