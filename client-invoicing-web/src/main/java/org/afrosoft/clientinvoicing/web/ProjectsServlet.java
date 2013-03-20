package org.afrosoft.clientinvoicing.web;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectsServlet extends HttpServlet {

	private static final long serialVersionUID = 2668343174933283911L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectsServlet.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
		String clientName = (String) req.getParameter(RequestParams.CLIENT_NAME);
	  
		Client currentClient = getCurrentClient(clientName, req.getSession());

		if (currentClient == null) {
			resp.sendRedirect("clients.html");
			return;
		}

		List<Project> projects = entityManager.createNamedQuery("findProjectsByClientName", Project.class)
				.setParameter("clientName", currentClient.getName())
				.getResultList();

		if (CollectionUtils.isNotEmpty(projects)) {
			LOG.info("Fetched {} client projects", projects.size());

			req.getSession().setAttribute(SessionKeys.CLIENT_PROJECTS, projects);
		}

		req.getRequestDispatcher("WEB-INF/jsp/projects.jsp").forward(req, resp);
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
