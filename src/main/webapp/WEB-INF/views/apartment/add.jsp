<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript">
        updateList = function() {
            var input = document.getElementById('images');
            var output = document.getElementById('fileList');
            var children = "";
            for (var i = 0; i < input.files.length; ++i) {
                children += '<li>' + input.files.item(i).name + '</li>';
            }
            output.innerHTML = '<ul>'+children+'</ul>';
        }
    </script>
    <meta charset="UTF-8">
</head>
<body>
<h2>Dodaj aukcje</h2>

<form:form method="post" modelAttribute="apartment" enctype="multipart/form-data">
    Tytuł: <form:input path="title"/> <br/>
    <form:errors path="title"/><br/>
    Opis: <form:textarea cols="45" rows="20" path="content"/> <br/>
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
    <label for="images">Wstaw zdjecia</label>
    <input type="file" accept="image/jpeg, image/png" multiple="multiple" name="images" id="images" onchange="javascript:updateList()"/>
    <p>Wybrane pliki:</p>
    <div id="fileList"></div>
    <input type="submit" value="Wyslij">
</form:form>
</body>
</html>