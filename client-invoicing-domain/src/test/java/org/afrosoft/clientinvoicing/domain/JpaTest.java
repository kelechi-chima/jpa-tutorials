package org.afrosoft.clientinvoicing.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.joda.time.DateMidnight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class JpaTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(JpaTest.class);
	
	private static final String CLIENT_NAME = "Virgin Money Giving Ltd";
	private static final String CONTACT_FIRSTNAME = "James";
	private static final String CONTACT_SURNAME = "Cousins";
	private static final String CONTACT_EMAIL = "james.cousins@virginmoneygiving.com";
	private static final String CONTACT_TELEPHONE = "01603 123456";
	private static final String ADDRESS_LINE_1 = "Discovery House";
	private static final String ADDRESS_LINE_2 = "Whiting Road";
	private static final String ADDRESS_LINE_3 = "Norwich";
	private static final String POSTCODE = "NR4 6FS";
	
	private static final String PROJECT_NAME = "E-Commerce";
	private static final Date PROJECT_START_DATE = new DateMidnight(2013, 1, 5).toDate();
	private static final Date PROJECT_END_DATE = new DateMidnight(2013, 1, 5).plusMonths(3).toDate();
	
	private static final String EMPLOYEE_FIRSTNAME = "Kelechi";
	private static final String EMPLOYEE_SURNAME = "Chima";
	private static final Date EMPLOYEE_DOB = new DateMidnight(1982, 1, 5).toDate();
	private static final String EMPLOYEE_ADDRESS_LINE_1 = "7 Croft Road";
	private static final String EMPLOYEE_ADDRESS_LINE_2 = "Brighton";
	private static final String EMPLOYEE_ADDRESS_LINE_3 = "East Sussex";
	private static final String EMPLOYEE_POSTCODE = "BN1 5JJ";
	private static final Role EMPLOYEE_ROLE = Role.DEVELOPER;
	private static final BigDecimal EMPLOYEE_RATE = new BigDecimal(45);
	
	private static EntityManagerFactory factory;
	
	private static EntityManager entityManager;
	
	public static void main(String[] args) {
		try {
			factory = Persistence.createEntityManagerFactory("client-invoicing");
			entityManager = factory.createEntityManager();
		
			Long clientId = createClient();
			Long projectId = createProject(clientId);
			Long employeeId = createEmployee();
			
			findClientByName();
			findClientByEmail();
			findClientsWithProjects();
			updateClientContact(clientId);
			updateClientAddress(clientId);
			findEmployeesWhoAreDevelopers();
			findEmployeesWhoAreOverThirty();
			findEmployeeAddressDetails();
			
			removeProject(projectId);
			removeClient(clientId);
			removeEmployee(employeeId);
		} catch (Exception ex) {
			LOG.error("Something went wrong", ex);
			
			if (entityManager != null) {
				entityManager.getTransaction().setRollbackOnly();
			}
		} finally {
			if (entityManager != null) {
				if (entityManager.getTransaction().isActive() && entityManager.getTransaction().getRollbackOnly()) {
					entityManager.getTransaction().rollback();
				}
				
				if (entityManager.isOpen()) {
					entityManager.close();
					factory.close();
					LOG.info("*** Closed entity manager and entity manager factory ***");
				}
			}
		}
  }

	private static Long createClient() {
	  Address address = createAddress();
	  Contact contact = createContact();
	  Client client = createClient(address, contact);
	  
	  entityManager.getTransaction().begin();
	  entityManager.persist(client);
	  entityManager.getTransaction().commit();
	  
	  Long id = client.getId();
	  Assert.notNull(id);
	  Assert.isTrue(id > 0);
	  
	  LOG.info("************** Created client with id '{}' **************", id);
	  
	  return id;
  }
	
	private static Long createProject(final Long clientId) {
		entityManager.getTransaction().begin();
		Client client = entityManager.find(Client.class, clientId);
		
		Project project = new Project();
		project.setName(PROJECT_NAME);
		project.setStartDate(PROJECT_START_DATE);
		project.setEndDate(PROJECT_END_DATE);
		project.setClient(client);
		
		entityManager.persist(project);
		entityManager.getTransaction().commit();
		
		Long projectId = project.getId();
		Assert.notNull(projectId);
		Assert.isTrue(projectId > 0);
		
		LOG.info("************** Created project with id '{}' **************", projectId);
		
		entityManager.getTransaction().begin();
		client.getProjects().add(project);
		entityManager.merge(client);
		entityManager.getTransaction().commit();
		
	  return projectId;
  }

	private static Long createEmployee() {
		Address address = new Address();
		address.setLine1(EMPLOYEE_ADDRESS_LINE_1);
		address.setLine2(EMPLOYEE_ADDRESS_LINE_2);
		address.setLine3(EMPLOYEE_ADDRESS_LINE_3);
		address.setPostcode(EMPLOYEE_POSTCODE);
		
		Employee employee = new Employee();
		employee.setFirstname(EMPLOYEE_FIRSTNAME);
		employee.setSurname(EMPLOYEE_SURNAME);
		employee.setDateOfBirth(EMPLOYEE_DOB);
		employee.setRole(EMPLOYEE_ROLE);
		employee.setRate(EMPLOYEE_RATE);
		employee.setAddress(address);
		
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		
		Long employeeId = employee.getId();
		Assert.notNull(employeeId);
		Assert.isTrue(employeeId > 0);
		
		LOG.info("************** Created employee with id '{}' **************", employeeId);
		
		entityManager.getTransaction().commit();
		
	  return employeeId;
  }
	
	private static void findClientByName() {
		Query query = entityManager.createQuery("SELECT c from Client c WHERE c.name LIKE :clientName");
		query.setParameter("clientName", CLIENT_NAME);
		query.setMaxResults(1);
		Client retrievedClient = (Client) query.getSingleResult();
		verifyClientFields(retrievedClient);
		
		LOG.info("************** Found client by name '{}' **************", CLIENT_NAME);
	}
	
	private static void findClientByEmail() {
		Query query = entityManager.createQuery("SELECT c from Client c WHERE c.contact.email LIKE :clientEmail");
		query.setParameter("clientEmail", CONTACT_EMAIL);
		query.setMaxResults(1);
		Client retrievedClient = (Client) query.getSingleResult();
		verifyClientFields(retrievedClient);
		
		LOG.info("************** Found client by contact email '{}' **************", CONTACT_EMAIL);
	}
	
	private static void updateClientContact(final Long id) {
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
		
		LOG.info("************** Updated client contact details **************");
	}
	
	private static void findClientsWithProjects() {
		// Both queries here produce the same result.
		// Using 'IN(c.projects)' generates better sql than 'IS NOT EMPTY'.
		// 'IN(c.projects)' produces equivalent sql to JOIN.
		
		// String query = "SELECT c FROM Client c WHERE c.projects IS NOT EMPTY";
		// String query = "SELECT c FROM Client c, IN(c.projects) p";
		String query = "SELECT c FROM Client c JOIN c.projects p";
		List<Client> clients = entityManager.createQuery(query, Client.class).getResultList();
		Assert.notEmpty(clients);
		
		LOG.info("************** Found clients with an associated project '{}'**************", clients);
  }
	
	private static void updateClientAddress(final Long id) {
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
		
		LOG.info("************** Updated client address details **************");
	}

	private static void findEmployeesWhoAreDevelopers() {
		String query = "SELECT e FROM Employee e WHERE e.role = org.afrosoft.clientinvoicing.domain.Role.DEVELOPER";
		List<Employee> employees = entityManager.createQuery(query, Employee.class).getResultList();
		Assert.notEmpty(employees);
		Assert.isTrue(employees.size() == 1);
		
		Employee employee = employees.get(0);
		verifyEmployeeFields(employee);
		
		LOG.info("************** Found employees who are developers **************");
	}

	private static void findEmployeesWhoAreOverThirty() {
		/*String query = "SELECT e FROM Employee e WHERE e.dateOfBirth > ?1";
		Date dateParam = new DateMidnight().minusYears(30).toDate();
		
		List<Employee> employees = entityManager.createQuery(query, Employee.class).
				setParameter(1, dateParam).
				getResultList();
		
		Assert.notEmpty(employees);
		Assert.isTrue(employees.size() == 1);
		
		LOG.info("************** Found employees who are over thirty **************");*/
	}
	
	private static void findEmployeeAddressDetails() {
		/*String query = "SELECT e.address.line1, e.address.line2, e.address.line3, e.address.line4, e.address.postcode " +
				"FROM Employee e WHERE e.address IS NOT EMPTY";
		@SuppressWarnings("unchecked")
    List<Object[]> result = entityManager.createQuery(query).getResultList();
    Assert.notEmpty(result);
    Assert.isTrue(result.size() == 1);
    
    Object[] employeeAddressDetails = result.get(0);
    Assert.isTrue(EMPLOYEE_ADDRESS_LINE_1.equals(employeeAddressDetails[0]));*/
	}
	
	private static void removeProject(final Long projectId) {
		entityManager.getTransaction().begin();

		Project project = entityManager.find(Project.class, projectId);
		
		entityManager.remove(project);
		entityManager.getTransaction().commit();
		
		LOG.info("************** Removed Project **************");
  }
	
	private static void removeClient(final Long clientId) {
		entityManager.getTransaction().begin();
		
		Client retrievedClient = entityManager.find(Client.class, clientId);
		
		entityManager.remove(retrievedClient);
		entityManager.getTransaction().commit();
		
		LOG.info("************** Removed client **************");
	}

	private static void removeEmployee(final Long employeeId) {
		entityManager.getTransaction().begin();
		
		Employee employee = entityManager.find(Employee.class, employeeId);
		
		entityManager.remove(employee);
		entityManager.getTransaction().commit();
		
		LOG.info("************** Removed employee **************");
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
	
	private static void verifyEmployeeFields(final Employee employee) {
		Assert.notNull(employee);
		Assert.isTrue(EMPLOYEE_FIRSTNAME.equals(employee.getFirstname()));
		Assert.isTrue(EMPLOYEE_SURNAME.equals(employee.getSurname()));
		Assert.isTrue(EMPLOYEE_ROLE.equals(employee.getRole()));
		Assert.isTrue(EMPLOYEE_RATE.equals(employee.getRate()));
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
