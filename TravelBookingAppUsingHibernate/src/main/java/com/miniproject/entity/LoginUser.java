package com.miniproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_login")
public class LoginUser {
	@Id
	@Column(name ="user_email")
	private String email;
	@Column(name ="user_password")
	private String UserPassword;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		this.UserPassword = userPassword;
	}
	public LoginUser(String email, String userPassword) {
		this.email = email;
		this.UserPassword = userPassword;
	}
	@Override
	public String toString() {
		return "LoginUser [email=" + email + ", UserPassword=" + UserPassword + "]";
	}
	public LoginUser() {
	
	}
	


	
	

}
