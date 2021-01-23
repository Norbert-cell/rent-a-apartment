<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>HELLO</h2>

<a href="<c:url value="/app/user/edit"/>">Edytuj uzytkownika</a>
<a href="<c:url value="/app/user/add-apartment"/>">Dodaj mieszkanie</a>
<a href="<c:url value="/app/user/auctions/1" />">Moje aukcje</a><br/>
<a href="<c:url value="/app/user/rent-auctions/1" />">Wynajete mieszkania</a><br/>
<a href="<c:url value="/app/user/rented" />">Najem</a><br/>
<a href="<c:url value="/app/user/earning" />">Zarobki</a><br/>

<a href="<c:url value="/logout" />">Wyloguj</a><br/>
<h3>Lista aukcji</h3>
<c:choose>
    <c:when test="${listApartments.size() > 0}">
<c:forEach items="${listApartments}" var="apartment">
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Cena: <h3>${apartment.price}</h3>
    <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>
    <a href="<c:url value="/app/apartment/rent/${apartment.id}"/>">Wynajmij</a><br/>
</c:forEach>
    </c:when>
    <c:otherwise>
        Brak apartamentow do wyswietlenia
    </c:otherwise>
</c:choose>
<c:if test="${listApartments.size() > 0 }">
    Strony:
            <c:forEach begin="0" end="${totalPages-1}" var="page">
                <a href="/app/${page+1}">${page+1}</a>
            </c:forEach>
</c:if>