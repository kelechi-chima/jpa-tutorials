package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Employee;
import org.afrosoft.clientinvoicing.domain.Timesheet;
import org.springframework.stereotype.Repository;

@Repository("timesheetDao")
public class TimesheetDaoImpl extends BaseDao implements TimesheetDao {

	@Override
	public Timesheet add(Timesheet timesheet, Employee employee) {
		return null;
	}

	@Override
	public Timesheet update(Timesheet timesheet) {
		return null;
	}

	@Override
	public List<Timesheet> findByEmployeeName(String firstName, String lastName) {
		return null;
	}

	@Override
	public List<Timesheet> findByProjectName(String projectName) {
		return null;
	}

	@Override
	public List<Timesheet> findByClientName(String clientName) {
		return null;
	}

	@Override
	public void remove(Timesheet timesheet) {
	}

}
