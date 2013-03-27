package org.afrosoft.clientinvoicing.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.afrosoft.clientinvoicing.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("projectRequestHandler")
public class ProjectRequestHandler implements HttpRequestHandler {
  
  private static final Logger LOG = LoggerFactory.getLogger(ProjectRequestHandler.class);

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
    } else {
      String httpMethod = request.getMethod();
      LOG.error("/projects.html called with unsupported http method '{}'", httpMethod);
      throw new ServletException("HTTP method not supported: " + httpMethod);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String clientName = (String) request.getParameter(RequestParams.CLIENT_NAME);
    
    Client currentClient = getCurrentClient(clientName, request.getSession());

    if (currentClient == null) {
      response.sendRedirect("clients.html");
      return;
    }

    List<Project> projects = projectService.findByClientName(currentClient);

    LOG.info("Number of client projects found: {}", projects.size());

    request.getSession().setAttribute(SessionKeys.CLIENT_PROJECTS, projects);
    request.getRequestDispatcher("WEB-INF/jsp/projects.jsp").forward(request, response);
  }

  private Client getCurrentClient(final String clientName, final HttpSession session) {
    Client currentClient = (Client) session.getAttribute(SessionKeys.CURRENT_CLIENT);
    
    if (currentClient != null) {
      if (StringUtils.isNotBlank(clientName) && currentClient.getName().equals(clientName)) {
        return currentClient;
      } else if (StringUtils.isBlank(clientName)) {
        return currentClient;
      }
    }
    
    @SuppressWarnings("unchecked")
    List<Client> clients = (List<Client>)session.getAttribute(SessionKeys.ALL_CLIENTS);
    
    if (CollectionUtils.isEmpty(clients)) {
      return null;
    }
    
    for (Client client : clients) {
      if (client.getName().equals(clientName)) {
        session.setAttribute(SessionKeys.CURRENT_CLIENT, client);
        return client;
      }
    }
    
    return null;
  }
  
}
