<%@ page session="true" import="org.afrosoft.clientinvoicing.domain.Client, 
                                org.afrosoft.clientinvoicing.web.SessionKeys,
                                org.afrosoft.clientinvoicing.web.RequestKeys,
                                org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Edit Client</title>
</head>
<body>
  <h3>Edit client</h3>
  <div>
  <%
  String editClientResult = (String)request.getAttribute(RequestKeys.EDIT_CLIENT_RESULT);
  if (StringUtils.isNotBlank(editClientResult)) {
  %>	
  <p><%= editClientResult %></p>	
  <%
  }
  
  Client client = (Client)session.getAttribute(SessionKeys.CURRENT_CLIENT);
  %>
    <form id="edit_client" name="edit_client" action="edit_client.html" method="post">
    <table>
      <tr>
        <td>Client name</td>
        <td><input type="text" name="client_name" value="<%=client.getName()%>"/></td>
      </tr>
      <tr>
        <td>Contact first name</td>
        <td><input type="text" name="contact_firstname" value="<%=client.getContact().getFirstname()%>"/></td>
      </tr>
      <tr>
        <td>Contact surname</td>
        <td><input type="text" name="contact_surname" value="<%=client.getContact().getSurname()%>"/></td>
      </tr>
      <tr>
        <td>Contact email</td>
        <td><input type="email" name="contact_email" value="<%=client.getContact().getEmail()%>"/></td>
      </tr>
      <tr>
        <td>Contact telephone</td>
        <td><input type="tel" name="contact_tel" value="<%=client.getContact().getTelephone()%>"/></td>
      </tr>
      <tr>
        <td>Address line 1</td>
        <td><input type="text" name="address_line_1" value="<%=client.getAddress().getLine1()%>"/></td>
      </tr>
      <tr>
        <td>Address line 2</td>
        <td><input type="text" name="address_line_2" value="<%=client.getAddress().getLine2()%>"/></td>
      </tr>
      <tr>
        <td>Address line 3</td>
        <td><input type="text" name="address_line_3" value="<%=client.getAddress().getLine3()%>"/></td>
      </tr>
      <tr>
        <td>Address line 4</td>
        <td><input type="text" name="address_line_4" value="<%=client.getAddress().getLine4()%>"/></td>
      </tr>
      <tr>
        <td>Postcode</td>
        <td><input type="text" name="postcode" value="<%=client.getAddress().getPostcode()%>"/></td>
      </tr>
      <tr>
        <td></td>
        <td>
          <input type="button" value="Cancel" onclick="document.location.href='clients.html'"/>
          <input type="button" value="Save" onclick="document.forms[0].submit()"/>
        </td>
      </tr>
    </table>
    </form>
  </div>
</body>
</html>