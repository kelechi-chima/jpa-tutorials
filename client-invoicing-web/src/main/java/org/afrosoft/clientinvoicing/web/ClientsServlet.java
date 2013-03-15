package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ClientsServlet extends HttpServlet {

	private static final long serialVersionUID = -4265798330171050954L;
	
	private static final String EDIT_ACTION = "edit";
	private static final String VIEW_PROJECTS_ACTION = "view_projects";

	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (StringUtils.isNotBlank(req.getParameter(ACTION_PARAM))) {
			doAction(req.getParameter(ACTION_PARAM), req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/clients.jsp").forward(req, resp);
		}
  }

	private void doAction(final String action, final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if (StringUtils.isNotBlank(req.getParameter(CLIENT_ID_PARAM))) {
			if (EDIT_ACTION.equals(action)) {
				//req.getRequestDispatcher("/WEB-INF/jsp/edit_client.jsp").forward(req, resp);
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
