package org.afrosoft.clientinvoicing.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.afrosoft.clientinvoicing.domain.Address;
import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("dev")
public class ClientRepositoryImplIntTest {

	/** ID defined in insert-test-data.sql */
	private static final Long TEST_CLIENT_ID = 1L;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** 
	 * Injected here so we can explicitly flush database changes which  
	 * would otherwise be rolled back. This helps to verify updates. 
	 */
	@PersistenceContext(unitName="client-invoicing", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	@Before
	public void setupDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("insert-test-data.sql"), false);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "client"));
	}
	
	@After
	public void clearDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
	}
	
	@Test
	public void getAllClients() {
		List<Client> clients = clientRepository.getAllClients();
		assertEquals(1, clients.size());
	}
	
	@Transactional
	@Test
	public void addClient() {
		clientRepository.addClient(client());
		entityManager.flush();
		assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "client"));
	}
	
	@Transactional
	@Test
	public void updateClient() {
		String whereCondition = "contact_first_name = 'John' and contact_last_name = 'Doe'";
		
		int rowCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", whereCondition);
		assertEquals(0, rowCount);
		
		Client client = clientRepository.addClient(client());
		entityManager.flush();
		
		client.getContact().setFirstname("John");
		client.getContact().setSurname("Doe");
		
		clientRepository.updateClient(client);
		entityManager.flush();
		
		rowCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client", whereCondition);
		assertEquals(1, rowCount);
	}
	
	@Transactional
	@Test
	public void removeClient() {
		Client client = entityManager.find(Client.class, TEST_CLIENT_ID);
		assertNotNull(client);
		
		clientRepository.removeClient(client);
		entityManager.flush();
		
		assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "client"));
	}
	
	private Client client() {
		Client client = new Client();
		client.setName("Another fictional client");
		client.setContact(contact());
		client.setAddress(address());
		return client;
	}
	
	private Contact contact() {
		Contact contact = new Contact();
		contact.setFirstname("Jane");
		contact.setSurname("Doe");
		contact.setEmail("jane.doe@anotherfictionalclient.com");
		contact.setTelephone("019875425892");
		return contact;
	}
	
	private Address address() {
		Address address = new Address();
		address.setLine1("Another Fictional Client House");
		address.setLine2("Another Fictional Client Road");
		address.setLine3("Another Fictional Client City");
		address.setLine4("Another Fictional Client County");
		address.setPostcode("Postcode");
		return address;
	}
	
}
