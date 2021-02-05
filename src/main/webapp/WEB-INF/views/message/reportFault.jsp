<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h2>USTERKA</h2>

<h3>Zglos usterke do uzytkownika ${ownerUserFullName}</h3><br/>
<form:form modelAttribute="message" method="post" action="/app/message/send">
    <form:hidden path="msgAboutApartmentId"/>
    <form:hidden path="type"/>
    <form:hidden path="senderUserId"/>
    <form:hidden path="receiverUserId"/>
    Tytuł:<form:input path="title"/><br/>
    Opis usterki: <form:textarea cols="45" rows="15" path="content"/><br/>
    <form:errors path="content"/>
    Szacowana wartosc naprawy:<form:input path="estimatedPrice"/><br/>
    <form:errors path="estimatedPrice"/>
    <form:button value="Wyslij">Wyslij</form:button>
</form:form>
