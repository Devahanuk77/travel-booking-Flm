package com.miniproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "new_user_registration")
public class User {
	
	@Column(name ="first_name")
	private String firstName;
	@Column(name ="last_name")
	private String lastName;
	@Column(name ="mobile_number")
	private String mobileNumber;
	private String gender;
	@Id
	@Column(name ="email_id")
	private String emailId;
	private String password;
	
	
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String firstName, String lastName, String mobileNumber, String gender, String emailId,String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.emailId = emailId;
		this.password = password;
	}
	
	public User() {
	}
	@Override
	public String toString() {
		return "User [ firstName=" + firstName + ", lastName=" + lastName
				+ ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", password=" + password + ", emailId="
				+ emailId + "]";
	}
	
	
	
	
	

}
