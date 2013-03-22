package org.afrosoft.clientinvoicing.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("addClientRequestHandler")
public class AddClientRequestHandler implements HttpRequestHandler {

  private static final Logger LOG = LoggerFactory.getLogger(AddClientRequestHandler.class);
  
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
      LOG.error("/add_client.html called with unsupported http method '{}'", httpMethod);
      throw new ServletException("HTTP method not supported: " + httpMethod);
    }
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    request.getRequestDispatcher("/WEB-INF/jsp/add_client.jsp").forward(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    Client client = new Client();
    ClientParamBuilder.buildClient(client, request);
    
    LOG.info("About to persist new client");
    try {
      clientService.addClient(client);
      
      LOG.info("Client persisted with id '{}'", client.getId());
      
      response.sendRedirect("clients.html");
    } catch (Exception ex) {
      LOG.error("Something went wrong while adding new client.", ex);
      throw new ServletException(ex);
    }
  }

}
