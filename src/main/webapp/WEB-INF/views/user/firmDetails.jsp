<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

Nazwa firmy: ${user.firmName}<br/>
Nip: ${user.nip}<br/>
Regon: ${user.regon}<br/>
Dolaczyl do nas: ${user.created}<br/>


<a href="<c:url value="/app/1" />">Wroc</a><br/>
