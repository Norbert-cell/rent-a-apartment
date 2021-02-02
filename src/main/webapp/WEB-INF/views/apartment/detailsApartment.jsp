<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div>
    <ul class="slider">
<c:forEach items="${apartment.images}" var="image">
    <li>
        <img src="/app/image/${image.id}" alt="${apartment.title}">
    </li>
</c:forEach>
    </ul>
</div>
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Powierzchnia: <h3>${apartment.apartmentArea}m2</h3><br/>
    Pokoje: <h3>${apartment.rooms}</h3><br/>
    Utworzono: <h3>${apartment.created}</h3><br/>
    Wlasciciel: <h3>${apartment.ownerUser.fullName}</h3><br/>
    <span id="addressForMap">
    Adres: <h3>${apartment.address.fullStreet}</h3><br/>
        </span>
    Najemca: <h3>${apartment.tenantUser.fullName}</h3>
    <a href="<c:url value="/app/message/send/${apartment.id}" />">Wyslij wiadomosc do wlasciciela</a><br/>
    <h3>Mapa google</h3>
    <div id="map"></div>
<iframe width="470" height="295" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" id="gmap_canvas" src="https://maps.google.com/maps?width=470&height=295&hl=en&q=${apartment.address.fullStreet},${apartment.address.city}&amp;t=&amp;z=12&amp;ie=UTF8&amp;iwloc=B&amp;output=embed">

</iframe> <a href='http://maps-generator.com/pl'>http://maps-generator.com/pl</a> <script type='text/javascript' src='https://embedmaps.com/google-maps-authorization/script.js?id=d2f6de7289097b6b77a27749596e2b2192985d02'></script>
<a href="<c:url value="/app/1" />">Wroc</a><br/>
<script defer src="../js/image.js" type="application/javascript" crossorigin="anonymous"></script>
<script defer src="../js/map.js" type="text/javascript" crossorigin="anonymous"></script>
<script defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC8cZWN_XE6ZcBaXKv6CJ1pqw8Xg96N7as&callback=initMap">
</script>
</body>