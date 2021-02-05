<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:choose>
    <c:when test="${apartmentDoesntExist}">
        Nie wynajales apartamentu<br/>
    </c:when>
    <c:otherwise>
    Tytuł: <h1>${apartment.title}</h1><br/>
    Cena: <h3>${apartment.price}</h3><br/>
    Powierzchnia: <h3>${apartment.apartmentArea}m2</h3><br/>
    Pokoje: <h3>${apartment.rooms}</h3><br/>
    Wlasciciel: <a href="<c:url value="/app/user/details/${apartment.ownerUser.id}"/>"><h3>${apartment.ownerUser.fullName}</h3></a>
        <br/>
    Adres: <h3>${apartment.address.fullStreet}</h3><br/>
    Najemca:${apartment.tenantUser.fullName}<br/>

<a href="<c:url value="/app/message/send/${apartment.id}/${apartment.ownerUser.id}?type=FAULT"/>">Zglos usterke</a>
    </c:otherwise>
</c:choose>
<a href="<c:url value="/app/1"/>">Wroc</a>

