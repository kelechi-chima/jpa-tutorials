package org.afrosoft.clientinvoicing.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Project;
import org.afrosoft.clientinvoicing.service.ProjectService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("editProjectRequestHandler")
public class EditProjectRequestHandler implements HttpRequestHandler {

	private static final Logger LOG = LoggerFactory.getLogger(EditProjectRequestHandler.class);
	
	private ProjectService projectService;

	@Autowired
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Override
	public void handleRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
		if (HttpMethod.GET.name().equals(request.getMethod())) {
			doGet(request, response);
		} else if (HttpMethod.POST.name().equals(request.getMethod())) {
			doPost(request, response);
		} else {
			String requestedUrl = request.getRequestURI();
      String httpMethod = request.getMethod();
      LOG.error("'{}' called with unsupported http method '{}'", requestedUrl, httpMethod);
      throw new ServletException("HTTP method not supported: " + httpMethod);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.removeAttribute(RequestKeys.EDIT_PROJECT_RESULT);
		String projectName = request.getParameter(RequestParams.PROJECT_NAME);
		
		if (StringUtils.isBlank(projectName)) {
			LOG.info("'{}' called without a project name", request.getRequestURI());
			response.sendRedirect("projects.html");
		} else {
			Project project = projectService.findByName(projectName);
			
			if (project != null) {
				request.getSession().setAttribute(SessionKeys.CURRENT_PROJECT, project);
				request.getRequestDispatcher("/WEB-INF/jsp/edit_project.jsp").forward(request, response);
			} else {
				LOG.info("Project not found with name '{}'", projectName);
				response.sendRedirect("projects.html");
			}
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Project project = (Project) request.getSession().getAttribute(SessionKeys.CURRENT_PROJECT);
		
		if (project == null) {
			LOG.info("No current project set in session");
			response.sendRedirect("projects.html");
		} else {
			RequestParamUtils.buildProject(project, request);
			
			LOG.info("About to update project");
			
			try {
				project = projectService.updateProject(project);

				LOG.info("Successfully updated project: {}", project);

				request.setAttribute(RequestKeys.EDIT_PROJECT_RESULT, "Changes have been applied successfully");
				request.getRequestDispatcher("/WEB-INF/jsp/edit_project.jsp").forward(request, response);
			} catch (Exception ex) {
        LOG.error("Something went wrong while updating project", ex);
        throw new ServletException(ex);
      }
		}
		
		
	}

}
