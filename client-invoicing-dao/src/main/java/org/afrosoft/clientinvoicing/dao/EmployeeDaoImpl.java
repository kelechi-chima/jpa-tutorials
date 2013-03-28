package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Role;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {
	
	@Override
	public Employee add(Employee employee) {
		entityManager.persist(employee);
		
		logger.info("Added employee with id '{}'", employee.getId());
		
		return employee;
	}

	@Override
	public Employee update(Employee employee) {
		employee = entityManager.merge(employee);
		
		logger.info("Updated employee: {}", employee);
		
		return employee;
	}

	@Override
	public Employee findByNationalInsuranceNo(String nationalInsuranceNo) {
		List<Employee> result = entityManager.createNamedQuery("findEmployeeByNationalInsuranceNo", Employee.class).
				setParameter("nationalInsuranceNo", nationalInsuranceNo).
				getResultList();
		
		Employee employee = null;
		
		if (CollectionUtils.isNotEmpty(result)) {
			employee = result.get(0);
			logger.info("Found employee with national insurance no '{}'", nationalInsuranceNo);
		}
		
		return employee;
	}

	@Override
	public List<Employee> findByFirstName(String firstName) {
		List<Employee> result = entityManager.createNamedQuery("findEmployeesByFirstName", Employee.class).
			setParameter("firstName", "%" + firstName + "%").
			getResultList();
		
		logger.info("Number of employees found by first name '{}' is ''", firstName, result.size());
		
		return result;
	}

	@Override
	public List<Employee> findByLastName(String lastName) {
		List<Employee> result = entityManager.createNamedQuery("findEmployeesByLastName", Employee.class).
				setParameter("lastName", "%" + lastName + "%").
				getResultList();
		
		logger.info("Number of employees found by last name '{}' is ''", lastName, result.size());
		
		return result;
	}

	@Override
	public List<Employee> findByRole(Role employeeRole) {
		List<Employee> result = entityManager.createNamedQuery("findEmployeesByRole", Employee.class).
			setParameter("employeeRole", employeeRole).
			getResultList();
		
		logger.info("Number of employees found by role '{}' is ''", employeeRole, result.size());
		
		return result;
	}

	@Override
	public void remove(Employee employee) {
		entityManager.remove(employee);
		
		logger.info("Removed employee: {}", employee);
	}

}
