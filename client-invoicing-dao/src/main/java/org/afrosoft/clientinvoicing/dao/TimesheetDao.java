package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Timesheet;

public interface TimesheetDao {

	Timesheet add(Timesheet timesheet, Employee employee);
	
	Timesheet update(Timesheet timesheet);
	
	List<Timesheet> findByEmployeeName(String firstName, String lastName);
	
	List<Timesheet> findByProjectName(String projectName);
	
	List<Timesheet> findByClientName(String clientName);
	
	void remove(Timesheet timesheet);
	
}
