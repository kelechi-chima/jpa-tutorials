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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("dev")
public abstract class BaseDaoIntTest {
	
	/** ID defined in insert-test-data.sql */
	protected Long rowID = 1L;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/** 
	 * Injected here so we can explicitly flush database changes which  
	 * would otherwise be rolled back. This helps to verify updates. 
	 */
	@PersistenceContext(unitName = "client-invoicing", type = PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;

	@Before
	public void setupDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("insert-test-data.sql"), false);
	}
	
	@After
	public void clearDatabase() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource("delete-test-data.sql"), false);
	}
	
	protected void verifyRows(int expectedNo, String tableName, String whereCondition) {
		assertEquals(expectedNo, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, tableName, whereCondition));
	}
	
	protected void verifyRows(int expectedNo, String tableName) {
		assertEquals(expectedNo, JdbcTestUtils.countRowsInTable(jdbcTemplate, tableName));
	}
	
}
