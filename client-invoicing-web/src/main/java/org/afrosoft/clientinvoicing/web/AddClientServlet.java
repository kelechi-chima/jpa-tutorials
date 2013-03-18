package org.afrosoft.clientinvoicing.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.afrosoft.clientinvoicing.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddClientServlet extends HttpServlet {

	private static final long serialVersionUID = -440911562266225799L;

	private static final Logger LOG = LoggerFactory.getLogger(AddClientServlet.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction tx;

	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  req.getRequestDispatcher("/WEB-INF/jsp/add_client.jsp").forward(req, resp);
  }

	@Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  Client client = new Client();
	  ClientParamBuilder.buildClient(client, req);
	  
	  LOG.info("About to persist new client");
	  try {
	    tx.begin();
	    entityManager.persist(client);
		  tx.commit();
		  
		  LOG.info("Client persisted with id '{}'", client.getId());
		  
		  resp.sendRedirect("clients.html");
    } catch (Exception ex) {
    	LOG.error("Something went wrong while persisting new client.", ex);
	    throw new ServletException(ex);
    }	  
	}	
	
}
