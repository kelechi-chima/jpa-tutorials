package org.afrosoft.clientinvoicing.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.afrosoft.clientinvoicing.dao.ClientDao;
import org.afrosoft.clientinvoicing.domain.Client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test class for ClientDao.<p>
 * 
 * If a method in the DAO requires a transaction, then the test method defined here is 
 * annotated with Spring's @Transactional. Otherwise the test method is not annotated. 
 */
public class ClientDaoImplIntTest extends BaseDaoIntTest {

	private static final String TABLE_NAME = "client";
	private static final Long CLIENT_ID = 1L;
	
	@Autowired
	private ClientDao clientDao;
	
	@Test
	public void getAllClients() {
		List<Client> clients = clientDao.getAllClients();
		assertEquals(1, clients.size());
	}
	
	/**
	 * Verifies that a client record is added to the database upon commit.
	 * @see {@link #setupDatabase()}
	 */
	@Transactional
	@Test
	public void addClient() {
		clientDao.add(TestUtils.client());
		entityManager.flush();
		verifyRows(2, TABLE_NAME);
	}
	
	/**
	 * Verifies that changes to a client record are persisted in the database upon commit.<p>
	 * 
	 * This method updates the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
	public void updateClient() {
		String whereCondition = "contact_first_name = 'John' and contact_last_name = 'Doe'";
		
		verifyRows(0, TABLE_NAME, whereCondition);

		Client client = entityManager.find(Client.class, CLIENT_ID);		
		client.getContact().setFirstName("John");
		client.getContact().setLastName("Doe");
		clientDao.update(client);
		entityManager.flush();
		
		verifyRows(1, TABLE_NAME, whereCondition);
	}
	
	/**
	 * Verifies that a client record that is removed is really removed from the database upon commit.<p>
	 * 
	 * This method removes the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
	public void removeClient() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "project");
		
		Client client = entityManager.find(Client.class, CLIENT_ID);
		
		clientDao.remove(client);
		entityManager.flush();
		
		verifyRows(0, TABLE_NAME);
	}
	
}
