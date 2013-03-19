<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, 
                org.afrosoft.clientinvoicing.domain.Client,
                org.afrosoft.clientinvoicing.web.SessionKeys" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Clients</title>
</head>
<body>
  <h3>Clients</h3>
  <div>
    <table>
      <c:forEach items="${all_clients}" var="client">
      <tr>
        <td><c:out value="${client.name}"/></td>
        <td><button type="button" onclick="document.location.href='edit_client.html?client_name=<c:out value="${client.name}"/>'">Edit</button></td>
        <td><button type="button" onclick="document.location.href='projects.html?client_name=<c:out value="${client.name}"/>'">Projects</button></td>
      </tr>    
      </c:forEach>
      <tr>
        <td></td>
        <td></td>
        <td><button type="button" onclick="document.location.href='add_client.html'">Add Client</button></td>
      </tr>
    </table>
  </div>
</body>
</html>