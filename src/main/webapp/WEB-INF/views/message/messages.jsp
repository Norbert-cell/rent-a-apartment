<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Odebrane wiadomosci</h3>
<c:if test="${noMessages==0}">
  Brak wiadomosci
</c:if>
<c:forEach items="${senderUsers}" var="user">
  OD:  ${user.fullName}
    <a href="/app/message/msg/${user.id}?type=NORMAL">Zobacz</a><br/>
</c:forEach>

<a href="<c:url value="/app/1" />">Wroc</a><br/>
