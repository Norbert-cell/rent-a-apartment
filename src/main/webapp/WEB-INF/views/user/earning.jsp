<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<h3><b>WYDATKI</b></h3>

Liczba mieszkan:${apartments}<br/>
Wynajete mieszkania:${rentedApartments}<br/>
Aktywane aukcje:${apartments-rentedApartments}<br/>
Przychod z wynajetych mieszkan:${earningFromRentedApartment}<br/>
Calkowite koszty utrzymania wszystkich mieszkan:${costsApartments}<br/>
Koszt utrzymania nie wynajetych mieszkan:${costsUnrentedApartments}<br/>
Zarobek lub strata: ${earningFromRentedApartment - costsApartments}<br/>
Calkowity mozliwy zarobek po odjeciu oplat:${count}<br/>

<a href="<c:url value="/app/1" />">Wroc</a><br/>
