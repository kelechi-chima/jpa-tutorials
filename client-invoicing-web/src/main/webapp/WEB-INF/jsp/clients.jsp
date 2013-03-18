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
    <%
    //List<String> clientNames = (List<String>)request.getAttribute(RequestKeys.CLIENT_NAMES);
    List<Client> clients = (List<Client>)session.getAttribute(SessionKeys.ALL_CLIENTS);
    //for (String clientName : clientNames) {
    	for (Client client : clients) {
    %>
	    <tr>
		    <td><%=client.getName()%></td>
		    <td><button type="button" onclick="document.location.href='edit_client.html?client_name=<%=client.getName()%>'">Edit</button></td>
		    <td><button type="button" onclick="document.location.href='view_project.html?client_name=<%=client.getName()%>'">View Projects</button></td>
	    </tr>
    <%
    }
    %>
      <tr>
        <td></td>
        <td></td>
        <td><button type="button" onclick="document.location.href='add_client.html'">Add Client</button></td>
      </tr>
    </table>
  </div>
</body>
</html>