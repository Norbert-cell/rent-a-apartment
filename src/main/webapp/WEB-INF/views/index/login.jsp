<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head></head>
<body>
<h1>Login</h1>
<font color="red">${errorMessage}</font>
<form:form action="/login" method='POST'>
    <table>
        <tr>
            <td>Email:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Haslo:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
</form:form>
</body>
</html>