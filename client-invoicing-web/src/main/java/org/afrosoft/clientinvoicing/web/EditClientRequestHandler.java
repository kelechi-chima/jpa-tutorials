package org.afrosoft.clientinvoicing.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.service.ClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("editClientRequestHandler")
public class EditClientRequestHandler implements HttpRequestHandler {
  
  private static final Logger LOG = LoggerFactory.getLogger(EditClientRequestHandler.class);

  private ClientService clientService;
  
  @Autowired
  public void setClientService(ClientService clientService) {
    this.clientService = clientService;
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
      LOG.error("/edit_client.html called with unsupported http method '{}'", httpMethod);
      throw new ServletException("HTTP method not supported: " + httpMethod);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    request.removeAttribute(RequestKeys.EDIT_CLIENT_RESULT);
    
    String clientName = request.getParameter(RequestParams.CLIENT_NAME);
    
    if (StringUtils.isBlank(clientName)) {
      LOG.info("/edit_client.html called without a client name");
      response.sendRedirect("clients.html");
    } else {
      @SuppressWarnings("unchecked")
      List<Client> clients = (List<Client>) request.getSession().getAttribute(SessionKeys.ALL_CLIENTS);
      
      if (CollectionUtils.isEmpty(clients)) {
        LOG.info("No clients in session");
        response.sendRedirect("clients.html");
      } else {
        for (Client client : clients) {
          if (client.getName().equals(clientName)) {
            request.getSession().setAttribute(SessionKeys.CURRENT_CLIENT, client);
            request.getRequestDispatcher("/WEB-INF/jsp/edit_client.jsp").forward(request, response);
            return;
          }
        }
        
        LOG.info("No matching client in session");
        response.sendRedirect("clients.html");
      }
    }
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Client client = (Client) req.getSession().getAttribute(SessionKeys.CURRENT_CLIENT);
    
    if (client == null) {
      LOG.info("No current client in session");
      resp.sendRedirect("clients.html");
    } else {
      ClientParamBuilder.buildClient(client, req);
      
      LOG.info("About to update client details");
      try {
        client = clientService.updateClient(client);
        
        LOG.info("Successfully updated client: {}", client);
        
        req.setAttribute(RequestKeys.EDIT_CLIENT_RESULT, "Changes have been applied successfully");
        req.getRequestDispatcher("/WEB-INF/jsp/edit_client.jsp").forward(req, resp);
      } catch (Exception ex) {
        LOG.error("Something went wrong while updating client details", ex);
        throw new ServletException(ex);
      } 
    }
  }
  
}
