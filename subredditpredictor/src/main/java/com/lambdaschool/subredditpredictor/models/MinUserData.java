package com.lambdaschool.subredditpredictor.models;

import javax.validation.constraints.Email;

public class MinUserData {
	private String username;
	private String password;

	@Email
	private String primaryEmail;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
}
