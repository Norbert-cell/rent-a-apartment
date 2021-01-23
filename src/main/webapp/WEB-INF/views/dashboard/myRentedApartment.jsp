<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Powierzchnia: <h3>${apartment.apartmentArea}m2</h3><br/>
    Pokoje: <h3>${apartment.rooms}</h3><br/>
    Utworzono: <h3>${apartment.created}</h3><br/>
    Wlasciciel: <h3>${apartment.ownerUser.fullName}</h3><br/>
    Adres: <h3>${apartment.address.fullStreet}</h3><br/>
    Najemca:
<c:choose>
    <c:when test="${apartment.tenantUser.firstName != null}">
        ${apartment.tenantUser.fullName}
    </c:when>
    <c:otherwise>
        ${apartment.tenantUser.firmName}
    </c:otherwise>
</c:choose><br/>
<a href="<c:url value="/app/1"/>">Wroc</a>
