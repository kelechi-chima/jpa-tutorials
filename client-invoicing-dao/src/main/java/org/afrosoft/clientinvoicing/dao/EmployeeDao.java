package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Role;

public interface EmployeeDao {

	Employee add(Employee employee);
	
	Employee update(Employee employee);
	
	Employee findByNationalInsuranceNo(String nationalInsuranceNo);
	
	List<Employee> findByFirstName(String firstName);
	
	List<Employee> findByLastName(String lastName);
	
	List<Employee> findByRole(Role employeeRole);
	
	void remove(Employee employee);
	
}
