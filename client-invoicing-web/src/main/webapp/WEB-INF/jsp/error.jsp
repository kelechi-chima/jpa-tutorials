<%@ page isErrorPage="true" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
</head>
<body>
<h3>A problem has occurred.</h3>
<%
if (exception.getCause() != null) {
	Throwable cause = exception.getCause();
%>
<p><%= cause.getMessage() %></p>
<%
} else {
%>
<p><%= exception.getMessage() %></p>
<%
}
%>
</body>
</html>