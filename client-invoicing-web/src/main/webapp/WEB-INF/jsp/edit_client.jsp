<%@ page import="org.afrosoft.clientinvoicing.web.RequestParams" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
</head>
<body>
  <p>You've chosen to edit Client <%= request.getParameter(RequestParams.CLIENT_ID_PARAM) %></p>
</body>
</html>