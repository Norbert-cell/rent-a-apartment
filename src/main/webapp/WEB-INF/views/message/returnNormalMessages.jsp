<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h3>WIADOMOSC</h3>
<form:form modelAttribute="message" method="post" action="/app/message/return">
    Tytuł:${message.title}<br/>
    Wiadomosc:<br/><form:textarea cols="45" rows="20" path="content"/><br/>
    <form:errors path="content"/>
    <form:hidden path="users"/>
    <form:hidden path="type"/>
    <form:hidden path="msgAboutApartmentId"/>
    <form:button value="Wyslij">Wyslij</form:button>
</form:form>