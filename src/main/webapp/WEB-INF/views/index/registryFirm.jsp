<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Zarejestruj</h2>

<form:form method="post" modelAttribute="user" action="/registry-firm">
    <form:hidden path="id"/> <br/>
    Email: <form:input path="userName"/> <br/>
    <form:errors path="userName"/><br/>
    Nazwa firmy: <form:input path="firmName"/><br/>
    <form:errors path="firmName"/><br/>
    Nip: <form:input path="nip"/> <br/>
    <form:errors path="nip"/><br/>
    Regon: <form:input path="regon"/> <br/>
    <form:errors path="regon"/><br/>
    Haslo: <form:password path="password"/><br/>
    <form:errors path="password"/>
    <input type="submit" value="Wyslij">
</form:form>

</body>