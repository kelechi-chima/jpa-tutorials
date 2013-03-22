package org.afrosoft.clientinvoicing.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.afrosoft.clientinvoicing.service.ProjectService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("addProjectRequestHandler")
public class AddProjectRequestHandler implements HttpRequestHandler {

  private static final Logger LOG = LoggerFactory.getLogger(AddProjectRequestHandler.class);
  
  private ProjectService projectService;
  
  @Autowired
  public void setProjectService(ProjectService projectService) {
    this.projectService = projectService;
  }
  
  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    if (HttpMethod.GET.name().equals(request.getMethod())) {
      doGet(request, response);
    } else if (HttpMethod.POST.name().equals(request.getMethod())) {
      doPost(request, response);
    } else {
      String httpMethod = request.getMethod();
      LOG.error("/add_project.html called with unsupported http method '{}'", httpMethod);
      throw new ServletException("HTTP method not supported: " + httpMethod);
    }
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/jsp/add_project.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Client currentClient = (Client) request.getSession().getAttribute(SessionKeys.CURRENT_CLIENT);
    
    if (currentClient == null) {
      LOG.info("Add project form posted without a client selected");
      response.sendRedirect("clients.html");
      return;
    }
    
    String projectName = request.getParameter(RequestParams.PROJECT_NAME);
    String startDate = request.getParameter(RequestParams.PROJECT_START_DATE);
    String endDate = request.getParameter(RequestParams.PROJECT_END_DATE);
    
    DateTime start = new DateTime(startDate);
    DateTime end = new DateTime(endDate);
    
    Project project = new Project();
    project.setName(projectName);
    project.setStartDate(start.toDate());
    project.setEndDate(end.toDate());
    
    try {
      projectService.addProject(project, currentClient);
      
      response.sendRedirect("projects.html");
    } catch (Exception ex) {
      LOG.error("Something went wrong while adding new project.", ex);
      throw new ServletException(ex);
    }
  }

}
