<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Add Client</title>
</head>
<body>
  <h3>Enter client details</h3>
  <div>
    <form id="add_client" name="add_client" action="add_client.html" method="post">
    <table>
      <tr>
        <td>Client name</td>
        <td><input type="text" name="client_name"/></td>
      </tr>
      <tr>
        <td>Contact first name</td>
        <td><input type="text" name="contact_firstname"/></td>
      </tr>
      <tr>
        <td>Contact surname</td>
        <td><input type="text" name="contact_surname"/></td>
      </tr>
      <tr>
        <td>Contact email</td>
        <td><input type="email" name="contact_email"/></td>
      </tr>
      <tr>
        <td>Contact telephone</td>
        <td><input type="tel" name="contact_tel"/></td>
      </tr>
      <tr>
        <td>Address line 1</td>
        <td><input type="text" name="address_line_1"/></td>
      </tr>
      <tr>
        <td>Address line 2</td>
        <td><input type="text" name="address_line_2"/></td>
      </tr>
      <tr>
        <td>Address line 3</td>
        <td><input type="text" name="address_line_3"/></td>
      </tr>
      <tr>
        <td>Address line 4</td>
        <td><input type="text" name="address_line_4"/></td>
      </tr>
      <tr>
        <td>Postcode</td>
        <td><input type="text" name="postcode"/></td>
      </tr>
      <tr>
        <td></td>
        <td>
	        <input type="button" value="Cancel" onclick="document.location.href='clients.html'"/>
	        <input type="button" value="Add Client" onclick="document.forms[0].submit()"/>
        </td>
      </tr>
    </table>
    </form>
  </div>
</body>
</html>