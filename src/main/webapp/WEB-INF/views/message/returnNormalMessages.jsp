<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>HELLO</h2>

<a href="<c:url value="/app/user/edit"/>">Edytuj uzytkownika</a>
<a href="<c:url value="/app/user/add-apartment"/>">Dodaj mieszkanie</a>
<a href="<c:url value="/app/user/auctions/1" />">Moje aukcje</a><br/>
<a href="<c:url value="/app/user/rent-auctions/1" />">Wynajete mieszkania</a><br/>
<a href="<c:url value="/app/user/rented" />">Najem</a><br/>
<a href="<c:url value="/app/user/earning" />">Zarobki</a><br/>

<a href="<c:url value="/logout" />">Wyloguj</a><br/>
<h3>Odpowiedz ${ownerUserFullName}</h3><br/>
<form:form modelAttribute="message" method="post" action="/app/message/rent-message/return">
    Tytuł:${title}<br/>
    Wiadomosc: <form:input path="content"/><br/>
    <form:errors path="content"/>
    <form:hidden path="estimatedPrice"/>
    <form:hidden path="type"/>
    <form:hidden path="title"/>
    <form:hidden path="msgAboutApartmentId"/>
    <form:hidden path="senderUser"/>
    <form:hidden path="receiverUser"/>
    <form:button value="Wyslij">Wyslij</form:button>
</form:form>