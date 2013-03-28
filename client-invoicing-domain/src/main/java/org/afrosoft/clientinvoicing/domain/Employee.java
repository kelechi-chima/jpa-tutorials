package org.afrosoft.clientinvoicing.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "findEmployeeByNationalInsuranceNo", 
			query = "SELECT e FROM Employee e WHERE e.nationalInsuranceNo = :nationalInsuranceNo"),
			
	@NamedQuery(name = "findEmployeesByFirstName", 
			query = "SELECT e FROM Employee e WHERE e.firstName LIKE :firstName"),
			
	@NamedQuery(name = "findEmployeesByLastName", 
			query = "SELECT e FROM Employee e WHERE e.lastName LIKE :lastName"),
			
	@NamedQuery(name = "findEmployeesByRole", 
			query = "SELECT e FROM Employee e WHERE e.role = :employeeRole")
})
@Entity
public class Employee {

	@Id
	@SequenceGenerator(name="EMPLOYEE_ID_GEN", sequenceName="EMPLOYEE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYEE_ID_GEN")
	@Column(name="EMPLOYEE_ID")
	private Long id;
	
	@Column(name="NATIONAL_INSURANCE_NO")
	private String nationalInsuranceNo;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DOB")
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private BigDecimal rate;
	
	@Embedded
	private Address address;
	
	@ManyToMany
	@JoinTable(name="EMP_PROJ", 
			joinColumns=@JoinColumn(name="EMPLOYEE_ID"), 
			inverseJoinColumns=@JoinColumn(name="PROJECT_ID"))
	private Set<Project> projects;
	
	@OneToMany(mappedBy="employee")
	private Set<Timesheet> timesheets;

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
	
	public String getNationalInsuranceNo() {
		return nationalInsuranceNo;
	}

	public void setNationalInsuranceNo(String nationalInsuranceNo) {
		this.nationalInsuranceNo = nationalInsuranceNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

	@Override
  public String toString() {
	  return new StringBuilder().
	  		append("Employee[id=").append(id).
	  		append(", first name=").append(firstName).
	  		append(", last name=").append(lastName).
	  		append(", dob=").append(dateOfBirth).
	  		append(", role=").append(role).
	  		append(", rate=").append(rate).
	  		append("]").toString();
  }

}
