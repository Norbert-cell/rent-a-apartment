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
<h3>Zglos usterke do uzytkownika ${ownerUserFullName}</h3><br/>
<form:form modelAttribute="message" method="post" action="/app/message/report-fault">
    <form:hidden path="msgAboutApartmentId"/>
    <form:hidden path="receiverUser"/>
    <form:hidden path="title"/>
    <form:hidden path="type"/>
    Tytuł:${message.title}<br/>
    Opis usterki: <form:input path="content"/><br/>
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