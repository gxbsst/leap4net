package com.sidways.leap.entity;

/**
 * @author Kim 2012-6-18
 */
public class Role extends Entity {

	private String role;

	public Role() {
		super();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public static Role normal(){
		Role role = new Role();
		role.setRole("USER_NORMAL");
		return role;
	}
}
