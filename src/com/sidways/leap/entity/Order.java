package com.sidways.leap.entity;

import java.util.Date;


/**
 * @author Kim 2012-7-9
 */
public class Order extends Entity {
	
	public static enum State{
		PAY, UNPAY, REFUND;
	}

	private User owner;

	private String type;
	
	private Double price;
	
	private State state;
	
	private Date deadline;

	public Order() {
		super();
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
