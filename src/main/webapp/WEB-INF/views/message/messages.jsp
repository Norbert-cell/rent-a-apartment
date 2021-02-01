<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${noMessages==0}">
  Brak wiadomosci
</c:if>
<c:forEach items="${senderUsers}" var="user">
  OD:  ${user.fullName}
    <a href="/app/message/normal/${user.id}">Zobacz</a>

</c:forEach>

<a href="<c:url value="/app/1" />">Wroc</a><br/>
