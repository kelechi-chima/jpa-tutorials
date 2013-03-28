package org.afrosoft.clientinvoicing.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.afrosoft.clientinvoicing.domain.Address;
import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Role;
import org.joda.time.DateMidnight;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

public class EmployeeDaoImplIntTest extends BaseDaoIntTest {
	
	private static final String TABLE_NAME = "employee";
	private static final String NATIONAL_INSURANCE_NO = "SC094577E";
	private static final String FIRST_NAME = "Fred";
	private static final String LAST_NAME = "Bloggs";
	private static final String ROLE = Role.DEVELOPER.name();
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Transactional
	@Test
  public void addEmployee() {
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
		
		employeeDao.add(employee());
		entityManager.flush();
		
		assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
  }

	@Transactional
	@Test
  public void updateEmployee() {
	  String whereCondition = "first_name = 'Janet'";
	  
	  verifyRows(0, TABLE_NAME, whereCondition);
	  
	  Employee employee = entityManager.find(Employee.class, 1L);
	  employee.setFirstName("Janet");
	  employeeDao.update(employee);
	  entityManager.flush();
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
  }

	@Test
  public void findByNationalInsuranceNo() {
	  String whereCondition = "national_insurance_no = '" + NATIONAL_INSURANCE_NO + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  Employee employee = employeeDao.findByNationalInsuranceNo(NATIONAL_INSURANCE_NO);
	  assertNotNull(employee);
	  assertEquals(NATIONAL_INSURANCE_NO, employee.getNationalInsuranceNo());
  }

	@Test
  public void findByFirstName() {
	  String whereCondition = "first_name LIKE '" + FIRST_NAME + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByFirstName(FIRST_NAME);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertTrue(employee.getFirstName().contains(FIRST_NAME));
  }

	@Test
  public void findByLastName() {
	  String whereCondition = "last_name LIKE '" + LAST_NAME + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByLastName(LAST_NAME);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertTrue(employee.getLastName().contains(LAST_NAME));
  }

	@Test
  public void findByRole() {
	  String whereCondition = "role = '" + ROLE + "'";
	  
	  verifyRows(1, TABLE_NAME, whereCondition);
	  
	  List<Employee> result = employeeDao.findByRole(Role.DEVELOPER);
	  assertEquals(1, result.size());
	  
	  Employee employee = result.get(0);
	  assertEquals(Role.DEVELOPER, employee.getRole());
  }

	@Transactional
	@Test
  public void removeEmployee() {
		verifyRows(1, TABLE_NAME);
		
		Employee employee = entityManager.find(Employee.class, 1L);
		employeeDao.remove(employee);
		entityManager.flush();
		
		verifyRows(0, TABLE_NAME);
  }
	
	private Employee employee() {
		Employee e = new Employee();
		e.setNationalInsuranceNo("AX497822E");
		e.setFirstName("Jane");
		e.setLastName("Smith");
		e.setDateOfBirth(new DateMidnight(1975, 5, 15).toDate());
		e.setRole(Role.ANALYST);
		e.setRate(new BigDecimal(30.75));
		e.setAddress(address());
		return e;
	}
	
	private Address address() {
		Address a = new Address();
		a.setLine1("line1");
		a.setLine2("line2");
		a.setLine3("line3");
		a.setLine4("line4");
		a.setPostcode("postcode");
		return a;
	}

}
