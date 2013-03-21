package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.ACTION;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ID;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.service.ClientService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;

public class ClientRequestHandler implements HttpRequestHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ClientRequestHandler.class);

  private static final String EDIT_ACTION = "edit";
  private static final String VIEW_PROJECTS_ACTION = "view_projects";
  
  private ClientService clientService;
  
  public void setClientService(ClientService clientService) {
    this.clientService = clientService;
  }

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (HttpMethod.GET.name().equals(request.getMethod())) {
      if (StringUtils.isNotBlank(request.getParameter(ACTION))) {
        doAction(request.getParameter(ACTION), request, response);
      } else {
        List<Client> clients = clientService.getAllClients();
        
        LOG.info("Fetched {} clients", clients.size());
        
        request.getSession().setAttribute(SessionKeys.ALL_CLIENTS, clients);
        request.getRequestDispatcher("/WEB-INF/jsp/clients.jsp").forward(request, response);
      } 
    }
  }
  
  private void doAction(final String action, final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    if (StringUtils.isNotBlank(req.getParameter(CLIENT_ID))) {
      if (EDIT_ACTION.equals(action)) {
        req.getRequestDispatcher("/edit_client.html").forward(req, resp);
      } else if (VIEW_PROJECTS_ACTION.equals(action)) {
        req.getRequestDispatcher("/WEB-INF/jsp/view_project.jsp").forward(req, resp);
      }
    } else {
      req.getRequestDispatcher("/WEB-INF/jsp/clients.jsp").forward(req, resp);
      resp.sendRedirect("index.html");
    }
  }

}
