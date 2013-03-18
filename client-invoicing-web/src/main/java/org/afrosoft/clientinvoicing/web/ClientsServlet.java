package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.ACTION;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ID;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.afrosoft.clientinvoicing.domain.Client;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientsServlet extends HttpServlet {

	private static final long serialVersionUID = -4265798330171050954L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientsServlet.class);
	
	private static final String EDIT_ACTION = "edit";
	private static final String VIEW_PROJECTS_ACTION = "view_projects";
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (StringUtils.isNotBlank(req.getParameter(ACTION))) {
			doAction(req.getParameter(ACTION), req, resp);
		} else {
			//String query = "SELECT c.name FROM Client c";
			String query = "SELECT c FROM Client c";
			
			//@SuppressWarnings("unchecked")
      //List<String[]> clientNames = entityManager.createQuery(query).getResultList();
      
      List<Client> clients = entityManager.createQuery(query, Client.class).getResultList();
      
      LOG.info("Fetched client names, number of rows: {}", clients.size());
      
      req.getSession().setAttribute(SessionKeys.ALL_CLIENTS, clients);
			//req.setAttribute(RequestKeys.CLIENT_NAMES, clientNames);
			req.getRequestDispatcher("/WEB-INF/jsp/clients.jsp").forward(req, resp);
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
