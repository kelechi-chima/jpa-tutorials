package org.afrosoft.clientinvoicing.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test class for EmployeeDao.<p>
 * 
 * If a method in the DAO requires a transaction, then the test method defined here is 
 * annotated with Spring's @Transactional. Otherwise the test method is not annotated. 
 */
public class EmployeeDaoImplIntTest extends BaseDaoIntTest {
	
	private static final String TABLE_NAME = "employee";
	private static final Long EMPLOYEE_ID = 1L;
	private static final String NATIONAL_INSURANCE_NO = "SC094577E";
	private static final String FIRST_NAME = "Fred";
	private static final String LAST_NAME = "Bloggs";
	private static final String ROLE = Role.DEVELOPER.name();
	
	@Autowired
	private EmployeeDao employeeDao;
	
	/**
	 * Verifies that an employee record is added to the database upon commit.
	 * @see {@link #setupDatabase()}
	 */
	@Transactional
	@Test
  public void addEmployee() {
		verifyRows(1, TABLE_NAME);
		
		employeeDao.add(TestUtils.employee());
		entityManager.flush();
		
		verifyRows(2, TABLE_NAME);
  }

	/**
	 * Verifies that changes to an employee record are persisted in the database upon commit.<p>
	 * 
	 * This method updates the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
  public void updateEmployee() {
	  String whereCondition = "first_name = 'Janet'";
	  
	  verifyRows(0, TABLE_NAME, whereCondition);
	  
	  Employee employee = entityManager.find(Employee.class, EMPLOYEE_ID);
	  employee.setFirstName("Janet");
	  employeeDao.update(employee);
	  entityManager.flush();
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
  }

	/**
	 * Verifies that we can retrieve an employee record by matching on the employee's national insurance number.<p>
	 * 
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
  public void findByNationalInsuranceNo() {
	  String whereCondition = "national_insurance_no = '" + NATIONAL_INSURANCE_NO + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  Employee employee = employeeDao.findByNationalInsuranceNo(NATIONAL_INSURANCE_NO);
	  assertNotNull(employee);
	  assertEquals(NATIONAL_INSURANCE_NO, employee.getNationalInsuranceNo());
  }

	/**
	 * Verifies that we can retrieve an employee record by matching on the employee's first name.<p>
	 * 
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
  public void findByFirstName() {
	  String whereCondition = "first_name LIKE '" + FIRST_NAME + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByFirstName(FIRST_NAME);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertTrue(employee.getFirstName().contains(FIRST_NAME));
  }

	/**
	 * Verifies that we can retrieve an employee record by matching on the employee's last name.<p>
	 *  
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
  public void findByLastName() {
	  String whereCondition = "last_name LIKE '" + LAST_NAME + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByLastName(LAST_NAME);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertTrue(employee.getLastName().contains(LAST_NAME));
  }

	/**
	 * Verifies that we can retrieve an employee record by matching on the employee's role.<p>
	 * 
	 * This method looks up the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Test
  public void findByRole() {
	  String whereCondition = "role = '" + ROLE + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByRole(Role.DEVELOPER);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertEquals(Role.DEVELOPER, employee.getRole());
  }

	/**
	 * Verifies that an employee record that is removed is really removed from the database upon commit.<p>
	 * 
	 * This method removes the test record inserted by the inherited method {@link #setupDatabase()}.
	 */
	@Transactional
	@Test
  public void removeEmployee() {
		verifyRows(1, TABLE_NAME);
		
		Employee employee = entityManager.find(Employee.class, EMPLOYEE_ID);
		employeeDao.remove(employee);
		entityManager.flush();
		
		verifyRows(0, TABLE_NAME);
  }

}
