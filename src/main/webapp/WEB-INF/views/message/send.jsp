<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Wyslij wiadomosc</h2>

<form:form method="post" modelAttribute="message" action="/app/message/send">
    <form:hidden path="id"/> <br/>
    <form:hidden path="receiverUserId"/>
    Wiadomosc: <form:textarea path="value" rows="35" cols="45"/><br/>
    <input type="submit" value="Wyslij">
</form:form>

</body>