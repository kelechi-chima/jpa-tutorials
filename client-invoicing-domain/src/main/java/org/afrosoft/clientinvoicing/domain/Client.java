package org.afrosoft.clientinvoicing.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@NamedQuery(name="findAllClients",
						query="SELECT c FROM Client c")
@Entity
public class Client {

	@Id
	@SequenceGenerator(name="CLIENT_ID_GEN", sequenceName="CLIENT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENT_ID_GEN")
	@Column(name="CLIENT_ID")
	private Long id;
	
	@Column(name="CLIENT_NAME")
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="firstName", column=@Column(name="contact_first_name")),
		@AttributeOverride(name="lastName", column=@Column(name="contact_last_name")),
		@AttributeOverride(name="email", column=@Column(name="contact_email")),
		@AttributeOverride(name="telephone", column=@Column(name="contact_telephone"))
	})
	private Contact contact;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy="client", cascade={CascadeType.MERGE})
	private Set<Project> projects;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Contact getContact() {
		return contact;
	}
	
	public void setContact(Contact contact) {
		this.contact = contact;
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
	
	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
	}

	@Override
  public String toString() {
	  return new StringBuilder().
	  		append("Client[id=").append(id).
	  		append(", name=").append(name).
	  		append(", contact=").append(contact).
	  		append(", address=").append(address).
	  		append("]").toString();
  }
	
}
