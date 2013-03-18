package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ADDRESS_LINE1;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ADDRESS_LINE2;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ADDRESS_LINE_3;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_ADDRESS_LINE_4;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_NAME;
import static org.afrosoft.clientinvoicing.web.RequestParams.CLIENT_POSTCODE;
import static org.afrosoft.clientinvoicing.web.RequestParams.CONTACT_EMAIL;
import static org.afrosoft.clientinvoicing.web.RequestParams.CONTACT_FIRST_NAME;
import static org.afrosoft.clientinvoicing.web.RequestParams.CONTACT_SURNAME;
import static org.afrosoft.clientinvoicing.web.RequestParams.CONTACT_TELEPHONE;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.afrosoft.clientinvoicing.domain.Address;
import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Contact;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditClientServlet extends HttpServlet {

	private static final long serialVersionUID = 3240318407969220173L;
	
	private static final Logger LOG = LoggerFactory.getLogger(EditClientServlet.class);
	
	@PersistenceContext(unitName="client-invoicing")
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction tx;
	
	@Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
		req.removeAttribute(RequestKeys.EDIT_CLIENT_RESULT);
		
		String clientName = req.getParameter(RequestParams.CLIENT_NAME);
		
		if (StringUtils.isBlank(clientName)) {
			LOG.info("/edit_client.html called without a client name");
			resp.sendRedirect("clients.html");
		} else {
			@SuppressWarnings("unchecked")
			List<Client> clients = (List<Client>) req.getSession().getAttribute(SessionKeys.ALL_CLIENTS);
			
			if (CollectionUtils.isEmpty(clients)) {
				LOG.info("No clients in session");
				resp.sendRedirect("clients.html");
			} else {
				for (Client client : clients) {
					if (client.getName().equals(clientName)) {
						req.getSession().setAttribute(SessionKeys.CURRENT_CLIENT, client);
						req.getRequestDispatcher("/WEB-INF/jsp/edit_client.jsp").forward(req, resp);
						return;
					}
				}
				
				LOG.info("No matching client in session");
				resp.sendRedirect("clients.html");
			}
		}
  }

	@Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Client client = (Client) req.getSession().getAttribute(SessionKeys.CURRENT_CLIENT);
		
		if (client == null) {
			LOG.info("No current client in session");
			resp.sendRedirect("clients.html");
		} else {
			ClientParamBuilder.buildClient(client, req);
		  
			LOG.info("About to update client details");
			try {
		    tx.begin();
		    client = entityManager.merge(client);
		    tx.commit();
		    
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
