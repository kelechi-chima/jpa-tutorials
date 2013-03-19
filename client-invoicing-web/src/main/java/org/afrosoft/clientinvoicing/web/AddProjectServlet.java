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
import org.afrosoft.clientinvoicing.domain.Project;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 5916848215855666836L;

	private static final Logger	LOG = LoggerFactory.getLogger(AddProjectServlet.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction tx;
	
	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  req.getRequestDispatcher("/WEB-INF/jsp/add_project.jsp").forward(req, resp);
	}

	@Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  Client currentClient = (Client) req.getSession().getAttribute(SessionKeys.CURRENT_CLIENT);
	  
		if (currentClient == null) {
			LOG.info("Add project form posted without a client selected");
			resp.sendRedirect("clients.html");
			return;
		}
		
		String projectName = req.getParameter(RequestParams.PROJECT_NAME);
		String startDate = req.getParameter(RequestParams.PROJECT_START_DATE);
		String endDate = req.getParameter(RequestParams.PROJECT_END_DATE);
		
		DateTime start = new DateTime(startDate);
		DateTime end = new DateTime(endDate);
		
		Project project = new Project();
		project.setName(projectName);
		project.setStartDate(start.toDate());
		project.setEndDate(end.toDate());
		
		try {
			tx.begin();
			currentClient = entityManager.merge(currentClient);
			currentClient.getProjects().add(project);
			project.setClient(currentClient);
			entityManager.persist(project);
			tx.commit();
			
			resp.sendRedirect("projects.html");
		} catch (Exception ex) {
			LOG.error("Something went wrong while persisting new project.", ex);
	    throw new ServletException(ex);
		}
  }

}
