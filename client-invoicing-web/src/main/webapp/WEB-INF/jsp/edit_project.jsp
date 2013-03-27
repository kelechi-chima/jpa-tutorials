<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Edit Project</title>
</head>
<body>
  <h3>Edit project</h3>
  <div>
    <c:if test="${not empty editProjectResult}">
    <p><c:out value="${editProjectResult}"/></p>
    </c:if>
    <form id="edit_project" name="edit_project" action="edit_project.html" method="post">
    <table>
      <tr>
        <td>Project name</td>
        <td><input type="text" name="project_name" value="<c:out value='${currentProject.name}'/>" required="required"/></td>
      </tr>
      <tr>
        <td>Project start date</td>
        <td><input type="date" name="project_start_date" value="<c:out value='${currentProject.startDate}'/>" required="required"/></td>
      </tr>
      <tr>
        <td>Project end date</td>
        <td><input type="date" name="project_end_date" value="<c:out value='${currentProject.endDate}'/>" required="required"/></td>
      </tr>
      <tr>
      <td></td>
      <td>
        <input type="button" value="Back" onclick="document.location.href='projects.html'"/>
        <input type="submit" value="Save"/>
      </td>
      </tr>
    </table>
    </form>
  </div>
</body>
</html>