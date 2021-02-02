
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:if test="${noMessages==0}">
    Brak wiadomosci
</c:if>
<c:forEach items="${faultMessages}" var="sender">

    Od:${sender.fullName}
    <a href="/app/message/fault/${sender.id}">Zobacz</a>
</c:forEach>


<a href="<c:url value="/app/1" />">Wroc</a><br/>
