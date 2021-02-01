<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<h3>Wiadomosc do: ${ownerUserFullName}</h3><br/>
<form:form modelAttribute="message" method="post" action="/app/message/rent">
    <form:hidden path="msgAboutApartmentId"/>
    <form:hidden path="receiverUser"/>
    <form:hidden path="senderUser"/>
    <form:hidden path="title"/>
    <form:hidden path="type"/>
    <form:hidden path="msgAboutApartmentId"/>
    Tytuł:${apartmentTitle}<br/>
    Opis: <form:input path="content"/><br/>
    <form:errors path="content"/>
    Sugerowana cena:<form:input path="estimatedPrice"/><br/>
    <form:errors path="estimatedPrice"/>
    <form:button value="Wyslij">Wyslij</form:button>
</form:form>

<a href="<c:url value="/app/1" />">Wroc</a><br/>
