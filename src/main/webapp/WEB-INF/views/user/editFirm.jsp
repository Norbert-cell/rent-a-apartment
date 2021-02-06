<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Edycja </h2>

<form:form method="post" modelAttribute="user" action="/app/user/edit-firm">
    <form:hidden path="id"/> <br/>
    <form:hidden path="role"/><br/>
    <form:hidden path="created"/><br/>
    <form:hidden path="userName"/><br/>
    <form:hidden path="password"/><br/>
    <form:hidden path="enabled"/><br/>
    <form:hidden path="regon"/><br/>
    <form:hidden path="nip"/><br/>
    Nazwa firmy: <form:input path="firmName"/> <br/>
    <form:errors path="firmName"/><br/>
    <input type="submit" value="Wyslij">

</form:form>

<a href="<c:url value="/app/1" />">Wroc</a><br/>

</body>