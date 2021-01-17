<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h3>Aukcje</h3>
<c:forEach items="${auctions}" var="apartment">
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>

</c:forEach>

<h3>Wynajete mieszkania</h3>
<c:forEach items="${rentedApartment}" var="apartment">
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>
</c:forEach>