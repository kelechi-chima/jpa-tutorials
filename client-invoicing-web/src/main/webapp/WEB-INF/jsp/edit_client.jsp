<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Edit Client</title>
</head>
<body>
  <h3>Edit client</h3>
  <div>
	  <c:if test="${not empty editClientResult}">
	  <p><c:out value="${editClientResult}"/></p>
	  </c:if>
    <form id="edit_client" name="edit_client" action="edit_client.html" method="post">
    <table>
      <tr>
        <td>Client name</td>
        <td><input type="text" name="client_name" value="<c:out value='${currentClient.name}'/>"/></td>
      </tr>
      <tr>
        <td>Contact first name</td>
        <td><input type="text" name="contact_first_name" value="<c:out value='${currentClient.contact.firstname}'/>"/></td>
      </tr>
      <tr>
        <td>Contact surname</td>
        <td><input type="text" name="contact_last_name" value="<c:out value='${currentClient.contact.lastName}'/>"/></td>
      </tr>
      <tr>
        <td>Contact email</td>
        <td><input type="email" name="contact_email" value="<c:out value='${currentClient.contact.email}'/>"/></td>
      </tr>
      <tr>
        <td>Contact telephone</td>
        <td><input type="tel" name="contact_tel" value="<c:out value='${currentClient.contact.telephone}'/>"/></td>
      </tr>
      <tr>
        <td>Address line 1</td>
        <td><input type="text" name="address_line_1" value="<c:out value='${currentClient.address.line1}'/>"/></td>
      </tr>
      <tr>
        <td>Address line 2</td>
        <td><input type="text" name="address_line_2" value="<c:out value='${currentClient.address.line2}'/>"/></td>
      </tr>
      <tr>
        <td>Address line 3</td>
        <td><input type="text" name="address_line_3" value="<c:out value='${currentClient.address.line3}'/>"/></td>
      </tr>
      <tr>
        <td>Address line 4</td>
        <td><input type="text" name="address_line_4" value="<c:out value='${currentClient.address.line4}'/>"/></td>
      </tr>
      <tr>
        <td>Postcode</td>
        <td><input type="text" name="postcode" value="<c:out value='${currentClient.address.postcode}'/>"/></td>
      </tr>
      <tr>
        <td></td>
        <td>
          <input type="button" value="Back" onclick="document.location.href='clients.html'"/>
          <input type="submit" value="Save"/>
        </td>
      </tr>
    </table>
    </form>
  </div>
</body>
</html>