<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Add Project</title>
</head>
<body>
  <h3>Enter project details</h3>
  <div>
    <form id="add_project" name="add_project" action="add_project.html" method="post">
    <table>
	    <tr>
		    <td>Project name</td>
		    <td><input type="text" name="project_name" required="required"/></td>
	    </tr>
	    <tr>
		    <td>Project start date</td>
		    <td><input type="date" name="project_start_date" required="required"/></td>
	    </tr>
	    <tr>
		    <td>Project end date</td>
		    <td><input type="date" name="project_end_date" required="required"/></td>
	    </tr>
	    <tr>
	    <td></td>
	    <td>
		    <input type="button" value="Back" onclick="document.location.href='projects.html"/>
		    <input type="submit" value="Save"/>
	    </td>
	    </tr>
    </table>
    </form>
  </div>
</body>
</html>