<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Edycja apartamentu</h2>
        <form:form method="post" modelAttribute="apartment" enctype="multipart/form-data">
            <input type="file" accept="image/jpeg, image/png" multiple="multiple" name="images" id="images"/>
            <form:hidden path="id"/> <br/>
            <form:hidden path="created"/><br/>
            <form:hidden path="rented"/><br/>
            <form:hidden path="ownerUser.id"/><br/>
            <form:hidden path="address.id"/><br/>
            Tytuł: <form:input path="title"/> <br/>
            <form:errors path="title"/><br/>
            Opis: <form:textarea path="content"/> <br/>
            <form:errors path="content"/><br/>
            Powierzchnia(m2): <form:input path="apartmentArea"/><br/>
            <form:errors path="apartmentArea"/><br/>
            Pokoje: <form:input path="rooms"/><br/>
            <form:errors path="rooms"/><br/>
            Cena(PLN): <form:input path="price"/><br/>
            <form:errors path="price"/><br/>
            Wydatki(PLN): <form:input path="myBills"/><br/>
            <form:errors path="myBills"/><br/>
            Ulica: <form:input path="address.street"/><br/>
            <form:errors path="address.street"/><br/>
            Numer: <form:input path="address.streetNumber"/><br/>
            <form:errors path="address.streetNumber"/><br/>
            Kod pocztowy: <form:input path="address.postCode"/><br/>
            <form:errors path="address.postCode"/><br/>
            Miasto: <form:input path="address.city"/><br/>
            <form:errors path="address.city"/><br/>
            <input type="submit" value="Wyslij">
        </form:form>
</body>