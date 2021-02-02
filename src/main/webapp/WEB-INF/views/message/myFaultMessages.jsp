<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

    <c:forEach items="${messages}" var="message">
        ${message.dateOfSendMsg}
        ${message.content}<br/>
    </c:forEach>
    <input hidden value="${ownerUserId}" name="ownerUserId">
<a href="<c:url value="/app/message/return/${msgAboutApartmentId}" />">Odpowiedz</a><br/>
<a href="<c:url value="/app/message/fault" />">Wroc</a><br/>