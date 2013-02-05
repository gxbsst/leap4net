package com.sidways.leap.entity;

import java.util.Date;

/**
 * @author Kim 2012-7-9
 */
public class Invitation extends Entity {

	private String code;

	private Date deadLine;
	
	private User owner;

	public Invitation() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
