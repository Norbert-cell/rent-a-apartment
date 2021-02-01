<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h3>Aukcje</h3>
<c:choose>
    <c:when test="${auctions.size() > 0}">
        <c:forEach items="${auctions}" var="apartment">
            Tytuł: <h1>${apartment.title}</h1><br/>
            Opis: <h3>${apartment.content}</h3><br/>
            <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a><br/>
            <a href="<c:url value="/app/apartment/edit/${apartment.id}" />">Edytuj</a><br/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Brak apartamentow do wyswietlenia
    </c:otherwise>
</c:choose>
<c:if test="${auctions.size() > 0 }">
    Strony:
    <c:forEach begin="0" end="${totalPages-1}" var="page">
        <a href="/app/user/auctions/${page+1}">${page+1}</a>
    </c:forEach>
</c:if>
<a href="<c:url value="/app/1" />">Wroc</a><br/>

