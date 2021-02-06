<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<b><h3>${userFullName}</h3></b>
<a href="<c:url value="/app/user/edit"/>">Edytuj uzytkownika</a>
<a href="<c:url value="/app/apartment/add-apartment"/>">Dodaj mieszkanie</a>
<a href="<c:url value="/app/user/auctions/1" />">Moje aukcje</a><br/>
<a href="<c:url value="/app/user/rent-auctions/1" />">Wynajete mieszkania</a><br/>
<a href="<c:url value="/app/user/rented" />">Najem</a><br/>
<a href="<c:url value="/app/user/earning" />">Zarobki</a><br/>
<c:choose>
    <c:when test="${faultMessagesSize>0}">
<b><a href="<c:url value="/app/message/msg?type=FAULT" />">Zgloszone usterki</a></b><br/>
        <b>Zgloszone usterki: ${faultMessagesSize}</b><br/>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="/app/message/msg?type=FAULT" />">Zgloszone usterki</a><br/>
    </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${normalMessagesSize>0}">
    <b><a href="<c:url value="/app/message/msg?type=NORMAL" />">Wiadomosci</a></b><br/>
    <b>Nie przeczytanych wiadomosci: ${normalMessagesSize}</b><br/>
</c:when>
    <c:otherwise>
<a href="<c:url value="/app/message/msg?type=NORMAL" />">Wiadomosci</a><br/>
    </c:otherwise>
</c:choose>
<c:if test="${admin}">
    <a href="<c:url value="/admin/users" />">Uzytkownicy</a><br/>
</c:if>

<a href="<c:url value="/logout" />">Wyloguj</a><br/>
<h3>Lista aukcji</h3>
<c:choose>
    <c:when test="${listApartments.size() > 0}">
<c:forEach items="${listApartments}" var="apartment">
    <span id="dashboard">
    <div>
        <ul class="gallery">
        <li><img src="/app/image/get/${apartment.id}" alt="${apartment.title}" ></li>
        </ul>
    </div>
        </span>
    <span id="dashboard">
    <h1>${apartment.title}</h1><br/>
        </span>
    <span id="content">
    <h3>${apartment.content}</h3><br/>
        </span>
    <span id="dashboard"><h1><b>${apartment.price}PLN</b></h1></span>
    <span id="dashboard">
    <a href="<c:url value="/app/apartment/details/${apartment.id}" />">Szczegoly</a>
    <a href="<c:url value="/app/message/send/${apartment.id}/${apartment.ownerUser.id}?type=NORMAL"/>">Zadaj pytanie sprzedajacemu</a><br/>
        </span>
<div id="dashboard">
  <b>  -----------------------------------------------------------------------</b>
</div>
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
<script defer src="${pageContext.request.contextPath}/js/imagesDashboard.js"></script>
<script defer src="${pageContext.request.contextPath}/js/hideShowText.js"></script>
</body>