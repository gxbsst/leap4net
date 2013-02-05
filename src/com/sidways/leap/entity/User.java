package com.sidways.leap.entity;

/**
 * @author Kim 2012-7-9
 */
public class User extends Entity {

	private String username;

	private String password;
	
	private Boolean activate;
	
	private Role role;

	public User() {
		super();
		this.activate = false;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getActivate() {
		return activate;
	}

	public void setActivate(Boolean activate) {
		this.activate = activate;
	}
}
