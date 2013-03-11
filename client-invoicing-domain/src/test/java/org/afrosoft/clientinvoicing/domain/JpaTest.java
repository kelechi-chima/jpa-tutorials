package org.afrosoft.clientinvoicing.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.util.Assert;

public class JpaTest {
	
	private static final String CLIENT_NAME = "Virgin Money Giving Ltd";

	private static final String CONTACT_FIRSTNAME = "James";

	private static final String CONTACT_SURNAME = "Cousins";

	private static final String CONTACT_EMAIL = "james.cousins@virginmoneygiving.com";

	private static final String CONTACT_TELEPHONE = "01603 123456";

	private static final String ADDRESS_LINE_1 = "Discovery House";

	private static final String ADDRESS_LINE_2 = "Whiting Road";

	private static final String ADDRESS_LINE_3 = "Norwich";

	private static final String POSTCODE = "NR4 6FS";
	
	private static EntityManager entityManager;
	
	public static void main(String[] args) {
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("client-invoicing");
			entityManager = factory.createEntityManager();
		
			Long id = create();
			findByName();
			findByEmail();
			updateContactDetails(id);
			updateAddressDetails(id);
			remove(id);
		}
		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();		
			}	
		}
  }

	private static Long create() {
	  Address address = createAddress();
	  Contact contact = createContact();
	  Client client = createClient(address, contact);
	  
	  entityManager.getTransaction().begin();
	  entityManager.persist(client);
	  entityManager.getTransaction().commit();
	  
	  Long id = client.getId();
	  Assert.notNull(id);
	  Assert.isTrue(id > 0);
	  return id;
  }
	
	private static void findByName() {
		Query query = entityManager.createQuery("SELECT c from Client c WHERE c.name LIKE :clientName");
		query.setParameter("clientName", CLIENT_NAME);
		query.setMaxResults(1);
		Client retrievedClient = (Client) query.getSingleResult();
		verifyClientFields(retrievedClient);
	}
	
	private static void findByEmail() {
		Query query = entityManager.createQuery("SELECT c from Client c WHERE c.contact.email LIKE :clientEmail");
		query.setParameter("clientEmail", CONTACT_EMAIL);
		query.setMaxResults(1);
		Client retrievedClient = (Client) query.getSingleResult();
		verifyClientFields(retrievedClient);
	}
	
	private static void updateContactDetails(final Long id) {
		String newFirstname = "Hunar";
		String newSurname = "Chopra";
		
		entityManager.getTransaction().begin();
		
		Client retrievedClient = entityManager.find(Client.class, id);
		Contact retrievedContact = retrievedClient.getContact();
		retrievedContact.setFirstname(newFirstname);
		retrievedContact.setSurname(newSurname);
		
		entityManager.merge(retrievedClient);
		entityManager.getTransaction().commit();
		
		retrievedClient = entityManager.find(Client.class, id);
		retrievedContact = retrievedClient.getContact();
		Assert.isTrue(newFirstname.equals(retrievedContact.getFirstname()));
		Assert.isTrue(newSurname.equals(retrievedContact.getSurname()));
	}
	
	private static void updateAddressDetails(final Long id) {
		String newLine1 = "Hanover House";
		String newLine2 = "Ipswich Road";
		
		entityManager.getTransaction().begin();
		
		Client retrievedClient = entityManager.find(Client.class, id);
		Address retrievedAddress = retrievedClient.getAddress();
		retrievedAddress.setLine1(newLine1);
		retrievedAddress.setLine2(newLine2);
		
		entityManager.merge(retrievedClient);
		entityManager.getTransaction().commit();
		
		retrievedClient = entityManager.find(Client.class, id);
		retrievedAddress = retrievedClient.getAddress();
		Assert.isTrue(newLine1.equals(retrievedAddress.getLine1()));
		Assert.isTrue(newLine2.equals(retrievedAddress.getLine2()));
	}
	
	private static void remove(final Long id) {
		entityManager.getTransaction().begin();
		
		Client retrievedClient = entityManager.find(Client.class, id);
		
		entityManager.remove(retrievedClient);
		entityManager.getTransaction().commit();
	}
	
	private static void verifyClientFields(final Client retrievedClient) {
	  Assert.notNull(retrievedClient);
	  Assert.isTrue(CLIENT_NAME.equals(retrievedClient.getName()));
	  
	  Contact retrievedContact = retrievedClient.getContact();
	  Assert.isTrue(CONTACT_FIRSTNAME.equals(retrievedContact.getFirstname()));
	  Assert.isTrue(CONTACT_SURNAME.equals(retrievedContact.getSurname()));
	  Assert.isTrue(CONTACT_EMAIL.equals(retrievedContact.getEmail()));
	  Assert.isTrue(CONTACT_TELEPHONE.equals(retrievedContact.getTelephone()));
	  
	  Address retrievedAddress = retrievedClient.getAddress();
	  Assert.isTrue(ADDRESS_LINE_1.equals(retrievedAddress.getLine1()));
	  Assert.isTrue(ADDRESS_LINE_2.equals(retrievedAddress.getLine2()));
	  Assert.isTrue(ADDRESS_LINE_3.equals(retrievedAddress.getLine3()));
	  Assert.isTrue(POSTCODE.equals(retrievedAddress.getPostcode()));
  }

	private static Address createAddress() {
		Address address = new Address();
		address.setLine1(ADDRESS_LINE_1);
		address.setLine2(ADDRESS_LINE_2);
		address.setLine3(ADDRESS_LINE_3);
		address.setPostcode(POSTCODE);
	  return address;
  }

	private static Contact createContact() {
	  Contact contact = new Contact();
	  contact.setFirstname(CONTACT_FIRSTNAME);
	  contact.setSurname(CONTACT_SURNAME);
	  contact.setEmail(CONTACT_EMAIL);
	  contact.setTelephone(CONTACT_TELEPHONE);
		return contact;
  }

	private static Client createClient(final Address address, final Contact contact) {
		Client client = new Client();
		client.setName(CLIENT_NAME);
		client.setAddress(address);
		client.setContact(contact);
		return client;
  }
}
