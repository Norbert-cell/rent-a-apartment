<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Zarejestruj</h2>

<form:form method="post" modelAttribute="user" action="/registry-user">
    <form:hidden path="id"/> <br/>
    Email: <form:input path="userName"/> <br/>
    <form:errors path="userName"/><br/>
    Imie: <form:input path="firstName"/> <br/>
    <form:errors path="firstName"/><br/>
    Nazwisko: <form:input path="lastName"/> <br/>
    <form:errors path="lastName"/><br/>
    Haslo: <form:password path="password"/><br/>
    <form:errors path="password"/>
    <input type="submit" value="Wyslij">
</form:form>

</body>