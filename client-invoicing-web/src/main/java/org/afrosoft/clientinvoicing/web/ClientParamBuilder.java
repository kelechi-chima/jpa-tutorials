package org.afrosoft.clientinvoicing.web;

import static org.afrosoft.clientinvoicing.web.RequestParams.*;

import javax.servlet.http.HttpServletRequest;

import org.afrosoft.clientinvoicing.domain.Address;
import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Contact;

public final class ClientParamBuilder {

	public static Client buildClient(final Client client, final HttpServletRequest req) {
		String clientName = req.getParameter(CLIENT_NAME);
		String contactFirstName = req.getParameter(CONTACT_FIRST_NAME);
		String contactSurname = req.getParameter(CONTACT_SURNAME);
		String contactEmail = req.getParameter(CONTACT_EMAIL);
		String contactTel = req.getParameter(CONTACT_TELEPHONE);
		String clientAddressLine1 = req.getParameter(CLIENT_ADDRESS_LINE1);
		String clientAddressLine2 = req.getParameter(CLIENT_ADDRESS_LINE2);
		String clientAddressLine3 = req.getParameter(CLIENT_ADDRESS_LINE_3);
		String clientAddressLine4 = req.getParameter(CLIENT_ADDRESS_LINE_4);
		String clientPostcode = req.getParameter(CLIENT_POSTCODE);

		Address address = new Address();
		address.setLine1(clientAddressLine1);
		address.setLine2(clientAddressLine2);
		address.setLine3(clientAddressLine3);
		address.setLine4(clientAddressLine4);
		address.setLine4(clientAddressLine4);
		address.setPostcode(clientPostcode);

		Contact contact = new Contact();
		contact.setFirstname(contactFirstName);
		contact.setSurname(contactSurname);
		contact.setEmail(contactEmail);
		contact.setTelephone(contactTel);

		client.setName(clientName);
		client.setAddress(address);
		client.setContact(contact);

		return client;
	}
  
}
