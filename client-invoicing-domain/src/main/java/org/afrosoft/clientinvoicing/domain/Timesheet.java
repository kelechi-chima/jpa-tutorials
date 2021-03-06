package org.afrosoft.clientinvoicing.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "findTimesheetsByEmployeeName", 
			query = "SELECT t FROM Timesheet t JOIN t.employee e WHERE e.firstName LIKE :firstName AND e.lastName LIKE :lastName"),
			
	@NamedQuery(name = "findTimesheetsByProjectName", 
			query = "SELECT t FROM Timesheet t JOIN t.project p WHERE p.name LIKE :projectName"),
	
	@NamedQuery(name = "findTimesheetsByClientName", 
			query = "SELECT t FROM Timesheet t JOIN t.project p JOIN p.client c WHERE c.name LIKE :clientName"),			
})
@Entity
public class Timesheet {

	@Id
	@SequenceGenerator(name="TIMESHEET_ID_GEN", sequenceName="TIMESHEET_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIMESHEET_ID_GEN")
	@Column(name="TIMESHEET_ID")
	private Long id;
	
	private String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;
	
	@Column(name="HOURS_WORKED")
	private Double hoursWorked;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(Double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
  public String toString() {
	  return new StringBuilder().
	  		append("Timesheet[id=").append(id).
	  		append(", entry date=").append(entryDate).
	  		append(", description=").append(description).
	  		append(", hours worked=").append(hoursWorked).
	  		append("]").toString();
  }
	
}
