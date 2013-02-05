package com.sidways.leap.entity;

import java.util.Date;

/**
 * @author Kim 2012-7-9
 */
public abstract class Entity {

	private String id;

	private Date created;

	public Entity() {
		super();
		this.created = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
