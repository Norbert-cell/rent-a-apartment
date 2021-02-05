<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<ul>
    <c:forEach items="${users}" var="user">
        <li>
            Nazwa: ${user.fullName}<br/>
            Utworzono: ${user.created}<br/>
            Rola: ${user.role}<br/>
            <td> <a href="<c:url value="/admin/role/${user.id}"/>">Zmien role</a></td>
        </li>
    </c:forEach>
</ul>