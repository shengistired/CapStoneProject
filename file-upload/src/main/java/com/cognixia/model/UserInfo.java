/*
 * 
 * 
 * 
 * Done By: Odelia
 * 
 * 
 * 
 */
package com.cognixia.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserInfo {
	@Id
	@NotNull
	@Size(min=5, max=80)
	private String username;

	@NotNull
	@Size(min=5, max=80)
	private String password;

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
	
	

}
