<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>HELLO</h2>

<a href="<c:url value="/app/user/edit"/>">Edytuj uzytkownika</a>
<a href="<c:url value="/app/user/add-apartment"/>">Dodaj mieszkanie</a>
<a href="<c:url value="/app/user/auctions" />">Moje aukcje</a><br/>
<a href="<c:url value="/app/user/rented" />">Najem</a><br/>
<a href="<c:url value="/app/user/earning" />">Zarobki</a><br/>

<a href="<c:url value="/logout" />">Wyloguj</a><br/>
<h3>Lista aukcji</h3>
<c:forEach items="${listApartments}" var="apartment">
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Cena: <h3>${apartment.price}</h3>
    <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>
    <a href="<c:url value="/app/apartment/rent/${apartment.id}"/>">Wynajmij</a><br/>
</c:forEach>