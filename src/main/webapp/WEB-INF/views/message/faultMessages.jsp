
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:if test="${noMessages==0}">
    Brak wiadomosci
</c:if>
<c:forEach items="${senderUsers}" var="sender">

    Od:${sender.fullName}
    <a href="/app/message/msg/${sender.id}?type=FAULT">Zobacz</a>
</c:forEach>


<a href="<c:url value="/app/1" />">Wroc</a><br/>
