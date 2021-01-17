<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Edycja </h2>

<form:form method="post" modelAttribute="user">
    <form:hidden path="id"/> <br/>
    <form:hidden path="role"/><br/>
    <form:hidden path="created"/><br/>
    <form:hidden path="password"/><br/>
<%--    <form:hidden path="userName"/><br/>--%>
    Imie: <form:input path="firstName"/> <br/>
    <form:errors path="firstName"/><br/>
    Nazwisko: <form:input path="lastName"/> <br/>
    <form:errors path="lastName"/><br/>
    Email: <form:input path="userName"/><br/>
<%--    <form:errors path="userName"/><br/>--%>
    <input type="submit" value="Wyslij">

</form:form>
</body>