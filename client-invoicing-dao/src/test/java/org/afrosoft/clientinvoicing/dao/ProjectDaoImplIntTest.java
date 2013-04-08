package org.afrosoft.clientinvoicing.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test class for ProjectDao.<p>
 * 
 * If a method in the DAO requires a transaction, then the test method defined here is 
 * annotated with Spring's @Transactional. Otherwise the test method is not annotated.<p>
 * 
 * As Project records require an associated Client record to already exist, an instance 
 * of ClientDao is also injected into this test class to help set up Client records. 
 */
public class ProjectDaoImplIntTest extends BaseDaoIntTest {

	private static final String TABLE_NAME = "project";
	private static final Long PROJECT_ID = 1L;
	private static final String PROJECT_NAME = "Project Name";
	private static final Long CLIENT_ID = 1L;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ClientDao clientDao;
	
	/**
	 * Verifies that a project record is added to the database upon commit.
	 * @see {@link #setupDatabase()}
	 */
	@Transactional
	@Test
	public void addProject() {
		verifyRows(1, TABLE_NAME);
		
		Client client = TestUtils.client();
		client = clientDao.add(client);
		entityManager.flush();
		
		projectDao.add(TestUtils.project(), client);
		entityManager.flush();
		
		verifyRows(2, TABLE_NAME);
	}
	
	/**
	 * Verifies that changes to a project record are persisted in the database upon commit.<p>
	 * 
	 * This method updates the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
	public void updateProject() {
		String whereCondition = "project_name = 'Project X'";
		
		verifyRows(0, TABLE_NAME, whereCondition);
		
		Project project = entityManager.find(Project.class, PROJECT_ID);
		project.setName("Project X");
		projectDao.update(project);
		entityManager.flush();
		
		verifyRows(1, TABLE_NAME, whereCondition);
	}
	
	/**
	 * Verifies that we can retrieve a project record by searching with the name of a client.<p>
	 * 
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
	public void findByClientName() {
		Client client = entityManager.find(Client.class, CLIENT_ID);
		
		List<Project> projects = projectDao.findByClientName(client.getName());
		assertNotNull(projects);
		assertFalse(projects.isEmpty());
	}
	
	/**
	 * Verifies that we can retrieve a project record by searching with the project name.<p>
	 * 
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
	public void findByProjectName() {
		Project project = projectDao.findByProjectName(PROJECT_NAME);
		assertNotNull(project);
		
		project = projectDao.findByProjectName("Fake Project");
		assertNull(project);
	}
	
	/**
	 * Verifies that a project record that is removed is really removed from the database upon commit.<p>
	 * 
	 * This method removes the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
	public void removeProject() {
		verifyRows(1, TABLE_NAME);
		
		Project project = entityManager.find(Project.class, PROJECT_ID);
		projectDao.remove(project);
		entityManager.flush();
		
		verifyRows(0, TABLE_NAME);
	}
	
}
