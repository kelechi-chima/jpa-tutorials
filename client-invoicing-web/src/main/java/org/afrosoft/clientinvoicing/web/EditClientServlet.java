package org.afrosoft.clientinvoicing.web;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditClientServlet extends HttpServlet {

	private static final long serialVersionUID = 3240318407969220173L;
	
	@PersistenceContext(unitName="client-invoicing")
	private EntityManager entityManager;

	@Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  if (entityManager == null) {
	  	throw new IllegalStateException("The entity manager was not injected into EditClientServlet");
	  }
	  
	  req.getRequestDispatcher("/WEB-INF/jsp/edit_client.jsp").forward(req, resp);
  }
	
}
