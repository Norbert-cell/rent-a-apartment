<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <script defer src="../map.js" type="text/javascript" crossorigin="anonymous"></script>
    <script defer
            src="https://maps.googleapis.com/maps/api/js?key=red-truck-300815&callback=initMap">
    </script>
    <link href='<c:url value="./css/style.css"/>' rel="stylesheet" type="text/css">

</head>
<body>
    Tytuł: <h1>${apartment.title}</h1><br/>
    Opis: <h3>${apartment.content}</h3><br/>
    Powierzchnia: <h3>${apartment.apartmentArea}m2</h3><br/>
    Pokoje: <h3>${apartment.rooms}</h3><br/>
    Utworzono: <h3>${apartment.created}</h3><br/>
    Wlasciciel: <h3>${apartment.ownerUser.fullName}</h3><br/>
    Adres: <h3>${apartment.address.fullStreet}</h3><br/>
    Najemca: <h3>${apartment.tenantUser.fullName}</h3>
    <h3>Mapa google</h3>
    <div id="map"></div>
<iframe width="470" height="295" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" id="gmap_canvas" src="https://maps.google.com/maps?width=470&amp;height=295&amp;hl=en&amp;q=Rembieli%C5%84ska%20+()&amp;t=&amp;z=12&amp;ie=UTF8&amp;iwloc=B&amp;output=embed">

</iframe> <a href='http://maps-generator.com/pl'>http://maps-generator.com/pl</a> <script type='text/javascript' src='https://embedmaps.com/google-maps-authorization/script.js?id=d2f6de7289097b6b77a27749596e2b2192985d02'></script>
<a href="<c:url value="/app/" />">Wroc</a><br/>
</body>
</html>