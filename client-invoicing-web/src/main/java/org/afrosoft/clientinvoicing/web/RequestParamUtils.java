package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.*;

import javax.servlet.http.HttpServletRequest;

import org.afrosoft.clientinvoicing.domain.Address;
import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Contact;
import org.afrosoft.clientinvoicing.domain.Project;
import org.joda.time.DateTime;

public final class RequestParamUtils {

	public static Client buildClient(final Client client, final HttpServletRequest request) {
		Address address = new Address();
		address.setLine1(request.getParameter(CLIENT_ADDRESS_LINE1));
		address.setLine2(request.getParameter(CLIENT_ADDRESS_LINE2));
		address.setLine3(request.getParameter(CLIENT_ADDRESS_LINE_3));
		address.setLine4(request.getParameter(CLIENT_ADDRESS_LINE_4));
		address.setPostcode(request.getParameter(CLIENT_POSTCODE));

		Contact contact = new Contact();
		contact.setFirstName(request.getParameter(CONTACT_FIRST_NAME));
		contact.setLastName(request.getParameter(CONTACT_LAST_NAME));
		contact.setEmail(request.getParameter(CONTACT_EMAIL));
		contact.setTelephone(request.getParameter(CONTACT_TELEPHONE));

		client.setName(request.getParameter(CLIENT_NAME));
		client.setAddress(address);
		client.setContact(contact);
		return client;
	}
	
	public static Project buildProject(final Project project, final HttpServletRequest request) {
    DateTime startDate = new DateTime(request.getParameter(RequestParams.PROJECT_START_DATE));
    DateTime endDate = new DateTime(request.getParameter(RequestParams.PROJECT_END_DATE));
    
		project.setName(request.getParameter(PROJECT_NAME));
		project.setStartDate(startDate.toDate());
		project.setEndDate(endDate.toDate());
		return project;
	}
  
}
