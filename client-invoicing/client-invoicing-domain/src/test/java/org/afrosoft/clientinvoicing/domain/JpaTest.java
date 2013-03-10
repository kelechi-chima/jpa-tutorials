package org.afrosoft.clientinvoicing.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTest {

	private static final Logger LOG = LoggerFactory.getLogger(JpaTest.class);
	
	public static void main(String[] args) {
	  EntityManagerFactory factory = Persistence.createEntityManagerFactory("client-invoicing");
	  EntityManager entityManager = factory.createEntityManager();
	  LOG.info("Created entity manager", entityManager);
	  
	  Address address = createAddress();
	  Contact contact = createContact();
	  Client client = createClient(address, contact);
	  entityManager.persist(client);
	  
	  LOG.info("Persisted client: {}", client);
  }

	private static Address createAddress() {
		Address address = new Address();
		address.setLine1("7 Croft Road");
		address.setLine2("Brighton");
		address.setLine3("East Sussex");
		address.setPostcode("BN1 5JJ");
	  return address;
  }

	private static Contact createContact() {
	  Contact contact = new Contact();
	  contact.setFirstname("Joe");
	  contact.setSurname("Bloggs");
	  contact.setEmail("joe.bloggs@gmail.com");
	  contact.setTelephone("01273 123456");
		return contact;
  }

	private static Client createClient(Address address, Contact contact) {
		Client client = new Client();
		client.setName("Big Hencho");
		client.setAddress(address);
		client.setContact(contact);
		return client;
  }
}
