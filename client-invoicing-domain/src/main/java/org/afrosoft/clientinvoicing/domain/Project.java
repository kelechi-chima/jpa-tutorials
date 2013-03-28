package org.afrosoft.clientinvoicing.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="findProjectByProjectName", 
			query="SELECT p FROM Project p WHERE p.name = :projectName"),
			
	@NamedQuery(name="findProjectsByClientName", 
			query="SELECT p FROM Client c JOIN c.projects p WHERE c.name = :clientName")
})
@Entity
public class Project {

	@Id
	@SequenceGenerator(name="PROJECT_ID_GEN", sequenceName="PROJECT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_ID_GEN")
	@Column(name="PROJECT_ID")
	private Long id;
	
	@Column(name="PROJECT_NAME")
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name="CLIENT_ID")
	private Client client;
	
	@ManyToMany(mappedBy="projects")
	private Set<Employee> employees;
	
	@OneToMany(mappedBy="project")
	private Set<Timesheet> timesheet;

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Timesheet> getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Set<Timesheet> timesheet) {
		this.timesheet = timesheet;
	}

	@Override
  public String toString() {
	  return new StringBuilder().
	  		append("Project[id=").append(id).
	  		append(", name=").append(name).
	  		append(", start date=").append(startDate).
	  		append(", end date=").append(endDate).
	  		append("]").toString();
  }
	
}
