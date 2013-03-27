<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List,
                 org.afrosoft.clientinvoicing.domain.Client,
                 org.afrosoft.clientinvoicing.domain.Project,
                 org.afrosoft.clientinvoicing.web.SessionKeys,
                 org.apache.commons.collections.CollectionUtils" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Client Projects</title>
</head>
<body>
  <h3><c:out value="${currentClient.name}"/> Projects</h3>
  <div>
	  <table border="1">
      <thead>
        <tr>
          <th>Project</th><th>Start Date</th><th>End Date</th>
        </tr>
      </thead>
      <c:if test="${not empty clientProjects}">
      <c:forEach items="${clientProjects}" var="project">
      <tr>
	      <td><c:out value="${project.name}"/></td>
	      <td><c:out value="${project.startDate}"/></td>
	      <td><c:out value="${project.endDate}"/></td>
	      <td><button type="button" onclick="document.location.href='edit_project.html?project_name=<c:out value="${project.name}"/>'">Edit</button></td>
      </tr>
      </c:forEach>
      </c:if>
		  <tr>
		  <td></td>
		  <td><button type="button" onclick="window.history.back()">Back</button></td>
		  <td><button type="button" onclick="document.location.href='add_project.html'">Add Project</button></td>
		  </tr>
	  </table>
  </div>
</body>
</html>