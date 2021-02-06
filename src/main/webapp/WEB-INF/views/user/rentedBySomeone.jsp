<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h3>Wynajete mieszkania</h3>
<c:choose>
    <c:when test="${rentedApartment.size() > 0}">
        <c:forEach items="${rentedApartment}" var="apartment">
            Tytuł: <h1>${apartment.title}</h1><br/>
            Opis: <h3>${apartment.content}</h3><br/>
            <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>
            Najemca: <a href="<c:url value="/app/user/details/${apartment.tenantUser.id}"/>">
            ${apartment.tenantUser.fullName}</a><br/>
            <a href="<c:url value="/app/apartment/termination/${apartment.id}" />">Wypowiedz umowe</a><br/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Brak apartamentow do wyswietlenia
    </c:otherwise>
</c:choose>
<c:if test="${rentedApartment.size() > 0 }">
    Strony:
    <c:forEach begin="0" end="${totalPages-1}" var="page">
        <a href="/app/user/rent-auctions/${page+1}">${page+1}</a>
    </c:forEach>
</c:if>
<a href="<c:url value="/app/1" />">Wroc</a><br/>
