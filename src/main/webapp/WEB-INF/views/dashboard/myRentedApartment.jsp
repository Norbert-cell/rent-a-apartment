<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Powierzchnia: <h3>${apartment.apartmentArea}m2</h3><br/>
    Pokoje: <h3>${apartment.rooms}</h3><br/>
    Utworzono: <h3>${apartment.created}</h3><br/>
    Wlasciciel: <h3>${apartment.ownerUser.fullName}</h3><br/>
    Adres: <h3>${apartment.address.fullStreet}</h3><br/>
    Najemca: <h3>${apartment.tenantUser.fullName}</h3>
<a href="<c:url value="/app/"/>">Wroc</a>
