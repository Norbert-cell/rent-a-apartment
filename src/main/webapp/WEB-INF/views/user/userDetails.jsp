<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>


Imie:<h3>${user.firstName}</h3><br/>
Jest juz z nami od: <h3>${user.created}</h3><br/>

<a href="<c:url value="/app/1" />">Wroc</a><br/>
