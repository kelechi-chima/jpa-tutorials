package org.afrosoft.clientinvoicing.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Contact {

	private String firstName;
	private String lastName;
	private String email;
	private String telephone;
	
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
  public String toString() {
	  return new StringBuilder().
	  		append("Contact[first name=").append(firstName).
	  		append(", last name=").append(lastName).
	  		append(", email=").append(email).
	  		append(", telephone=").append(telephone).
	  		append("]").toString();
  }
	
}
