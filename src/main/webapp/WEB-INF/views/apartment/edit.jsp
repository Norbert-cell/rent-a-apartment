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
            <form:errors path="title" cssStyle="color: red"/><br/>
            Opis: <form:textarea cols="45" rows="15" path="content"/> <br/>
            <form:errors path="content" cssStyle="color: red"/><br/>
            Powierzchnia(m2): <form:input path="apartmentArea"/><br/>
            <form:errors path="apartmentArea" cssStyle="color: red"/><br/>
            Pokoje: <form:input path="rooms"/><br/>
            <form:errors path="rooms" cssStyle="color: red"/><br/>
            Cena(PLN): <form:input path="price"/><br/>
            <form:errors path="price" cssStyle="color: red"/><br/>
            Wydatki(PLN): <form:input path="myBills"/><br/>
            <form:errors path="myBills" cssStyle="color: red"/><br/>
            Ulica: <form:input path="address.street"/><br/>
            <form:errors path="address.street" cssStyle="color: red"/><br/>
            Numer: <form:input path="address.streetNumber"/><br/>
            <form:errors path="address.streetNumber" cssStyle="color: red"/><br/>
            Kod pocztowy: <form:input path="address.postCode"/><br/>
            <form:errors path="address.postCode" cssStyle="color: red"/><br/>
            Miasto: <form:input path="address.city"/><br/>
            <form:errors path="address.city" cssStyle="color: red"/><br/>
            <input type="submit" value="Wyslij">
        </form:form>
</body>