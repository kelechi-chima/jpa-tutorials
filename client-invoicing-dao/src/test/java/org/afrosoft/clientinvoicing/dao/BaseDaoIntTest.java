package org.afrosoft.clientinvoicing.dao;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

/**
 * Base class for integration test classes.<p>
 * 
 * Defines methods to set up and tear down the test database, as well
 * as methods to verify the content of database tables.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("dev")
public abstract class BaseDaoIntTest {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/** 
	 * Injected here so we can explicitly flush database changes which  
	 * would otherwise be rolled back. This helps to verify updates. 
	 */
	@PersistenceContext(unitName = "client-invoicing", type = PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;

	/**
	 * Set up the test database before running each test method.<p>
	 * It executes sql commands contained in test sql resources.
	 */
	@Before
	public void setupDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("insert-test-data.sql"), false);
	}
	
	/**
	 * Clear down the test database after running each test method.<p>
	 * It executes sql commands contained in test sql resources.
	 */
	@After
	public void clearDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
	}
	
	/**
	 * Verifies the number of rows in the specified table that match the specified condition.
	 * @param expectedNo - the number of rows we expect to find
	 * @param tableName - the table to check against
	 * @param whereCondition - the sql where condition, e.g. "first_name = 'joe'"
	 */
	protected void verifyRows(int expectedNo, String tableName, String whereCondition) {
		assertEquals(expectedNo, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, tableName, whereCondition));
	}
	
	/**
	 * Verifies the number of rows in the specified table.
	 * @param expectedNo - the number of rows we expect to find
	 * @param tableName - the table to check against
	 */
	protected void verifyRows(int expectedNo, String tableName) {
		assertEquals(expectedNo, JdbcTestUtils.countRowsInTable(jdbcTemplate, tableName));
	}
	
}
